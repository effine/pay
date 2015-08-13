package cn.effine.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.effine.model.WechatPay;
import cn.effine.model.WxPayResult;
import cn.effine.utils.Constants;
import cn.effine.utils.GetWxOrderno;
import cn.effine.utils.RequestHandler;
import cn.effine.utils.Sha1Util;
import cn.effine.utils.WechatUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 微信支付
 */
@Controller
@RequestMapping("wechat")
public class WechatContrller {
	
	/**
	 * 获取支付二维码
	 *
	 * @param info
	 *            商品信息
	 * @return 展示二维码页面
	 */
	@RequestMapping("qrcode")
	public String generateQRCode(HttpServletRequest request, HttpServletResponse response, Model model, String info){
		 //扫码支付
	    WechatPay webPay = new WechatPay();
	    
	    webPay.setBody("商品信息: " + info);
	    webPay.setOrderId(WechatUtils.getRandomNum());
	    webPay.setSpbillCreateIp("127.0.0.1");
	    webPay.setTotalFee("0.01");
	    // 获得二维码内部链接
		String QRCodeLinks =  WechatUtils.getCodeurl(webPay);
		model.addAttribute("name","effine");
		model.addAttribute("qrcode", QRCodeLinks);
		
		int width = 285;
		int height = 285;
		String format = "png";
		Hashtable hints= new Hashtable();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(QRCodeLinks, BarcodeFormat.QR_CODE, width, height,hints);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		
		String path = "/home/data/picture/pay/QRCode/" + System.currentTimeMillis() + ".png";
		File outputFile = new File(path);
		try {
			MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("wechatCode", path.replace("/home/data", ""));
		return "pay/showQRCode";
	}
	
	/**
	 * 支付回调方法
	 * 微信公众平台设置回调URL: 微信公众平台[https://mp.weixin.qq.com] --> 微信支付 --> 开发配置  --> 扫码支付 -- 修改"支付回调URL"
	 * 
	 * @param openid
	 *            用户标识(用户在商户appid下的唯一标识)
	 * @param productid
	 *            商品ID(商户定义的商品id 或者订单号)
	 * @return 用户完成支付的预支付交易ID（prepay_id）
	 */
	@RequestMapping("callback")
	@Deprecated
	protected void callback(HttpServletRequest request, HttpServletResponse response){
		
		//把如下代码贴到的你的处理回调的servlet 或者.do 中即可明白回调操作
		System.out.print("----------- 微信支付回调数据开始");
		
		//示例报文
//		String xml = "<xml><appid><![CDATA[wxb4dc385f953b356e]]></appid><bank_type><![CDATA[CCB_CREDIT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1228442802]]></mch_id><nonce_str><![CDATA[1002477130]]></nonce_str><openid><![CDATA[o-HREuJzRr3moMvv990VdfnQ8x4k]]></openid><out_trade_no><![CDATA[1000000000051249]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[1269E03E43F2B8C388A414EDAE185CEE]]></sign><time_end><![CDATA[20150324100405]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1009530574201503240036299496]]></transaction_id></xml>";
		String inputLine;
		String notityXml = "";
		String resXml = "";

		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("接收到的报文：" + notityXml);
		
		Map m = WechatUtils.parseXmlToList2(notityXml);
		WxPayResult model = new WxPayResult();
		model.setAppid(m.get("appid").toString());
		model.setBankType(m.get("bank_type").toString());
		model.setCashFee(m.get("cash_fee").toString());
		model.setFeeType(m.get("fee_type").toString());
		model.setIsSubscribe(m.get("is_subscribe").toString());
		model.setMchId(m.get("mch_id").toString());
		model.setNonceStr(m.get("nonce_str").toString());
		model.setOpenid(m.get("openid").toString());
		// 商户订单
		model.setOutTradeNo(m.get("out_trade_no").toString());
		model.setResultCode(m.get("result_code").toString());
		model.setReturnCode(m.get("return_code").toString());
		model.setSign(m.get("sign").toString());
		model.setTimeEnd(m.get("time_end").toString());
		model.setTotalFee(m.get("total_fee").toString());
		model.setTradeType(m.get("trade_type").toString());
		// 微信订单
		model.setTransactionId(m.get("transaction_id").toString());
		
		HttpSession session = request.getSession();
		session.setAttribute("out_trade_no", m.get("out_trade_no"));
		session.setAttribute("transaction_id", m.get("transaction_id"));
		
		if("SUCCESS".equals(model.getResultCode())){
			//支付成功
			resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
			+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
		}else{
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
			+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		}

		System.out.println("微信支付回调数据结束");

		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			out.write(resXml.getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("------------微信支付回调结束");
		System.out.println("---111---------transaction_id=" + session.getAttribute("transaction_id"));
		System.out.println("----1111--------out_trade_no=" + session.getAttribute("out_trade_no"));
		
	}

	/**
	 * 查询订单状态，确认完成支付
	 *
	 * @param transaction_id
	 *            微信订单号(根据实际更换)
	 * 
	 * @param out_trade_no
	 *            商户订单号(根据实际更换)
	 * 
	 * @return
	 */
	@RequestMapping("query")
	public void orderQuery(HttpServletRequest request, HttpServletResponse response){
		
		String transaction_id = "1000520290201508120606897590";
		String out_trade_no = "1249621001";
		
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", Constants.appid);  
		packageParams.put("mch_id", Constants.partner);  
		packageParams.put("nonce_str", WechatUtils.getRandomNum());  
		packageParams.put("out_trade_no", out_trade_no);  
		packageParams.put("transaction_id", transaction_id);  
		
		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init(Constants.appid, Constants.appsecret, Constants.partnerkey);
		String sign = reqHandler.createSign(packageParams);
		String xml="<xml>"+
				"<appid>"+ Constants.appid + "</appid>"+
				"<mch_id>"+ Constants.partner + "</mch_id>"+
				"<nonce_str>"+ WechatUtils.getRandomNum() +"</nonce_str>"+
				"<out_trade_no>"+out_trade_no+"</out_trade_no>"+
				"<sign>" +sign+ "</sign>"+
				"<transaction_id>"+transaction_id+"</transaction_id>"+
		 		"</xml>";
		String allParameters = "";
		try {
			allParameters =  reqHandler.genPackage(packageParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//订单查询接口URL
		try {
			Map map = new GetWxOrderno().getOrderNo(Constants.orderQuery, xml);
			System.out.println("map="+map);
			if(map.get("trade_state").equals("")){
				System.out.println("-------订单出错");
			}else{
				//String return_code  = (String) map.get("return_code");
				System.out.println("map="+map);
				//交易状态
				String back_trade_state  = (String) map.get("trade_state");
				//此买家是否关注公众号Y-关注，N-未关注
				String back_is_subscribe="";
				back_is_subscribe  = (String) map.get("is_subscribe");
				//交易类型
				String back_trade_type="";
				back_trade_type  = (String) map.get("trade_type");
				//总金额
				String back_total_fee="";
				back_total_fee  = (String) map.get("total_fee");
				//微信订单号
				String back_transaction_id="";
				back_transaction_id  = (String) map.get("transaction_id");
				//商户订单号
				String back_out_trade_no="";
				back_out_trade_no  = (String) map.get("out_trade_no");
				//交易结束时间
				String back_time_end="";
				back_time_end  = (String) map.get("time_end");
				//买家openid
				String back_openid="";
				back_openid  = (String) map.get("openid");
				request.setAttribute("back_trade_state", back_trade_state);
				request.setAttribute("back_is_subscribe", back_is_subscribe);
				request.setAttribute("back_trade_type", back_trade_type);
				request.setAttribute("back_total_fee", back_total_fee);
				request.setAttribute("back_transaction_id", back_transaction_id);
				request.setAttribute("back_out_trade_no", back_out_trade_no);
				request.setAttribute("back_time_end", back_time_end);
				request.setAttribute("back_openid", back_openid);
				System.out.println("----------------------订单查询成功");
				System.out.println("back_trade_state="+ back_trade_state);
				System.out.println("back_is_subscribe="+ back_is_subscribe);
				System.out.println("back_trade_type="+ back_trade_type);
				System.out.println("back_total_fee="+ back_total_fee);
				System.out.println("back_transaction_id="+ back_transaction_id);
				System.out.println("back_out_trade_no="+ back_out_trade_no);
				System.out.println("back_time_end="+ back_time_end);
				System.out.println("back_openid="+ back_openid);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * 获取请求预支付id报文
	 * @return
	 */
	@Deprecated
	@SuppressWarnings("static-access")
	public static String getPackage(WechatPay model) {
		
		String openId = model.getOpenId();
		// 1 参数
		// 订单号
		String orderId = model.getOrderId();
		// 附加数据 原样返回
		String attach = "";
		// 总金额以分为单位，不带小数点
		String totalFee = WechatUtils.getMoney(model.getTotalFee());
		
		// 订单生成的机器 IP
		String spbill_create_ip = model.getSpbillCreateIp();
		// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
		String notify_url = Constants.notifyurl;
		String trade_type = "JSAPI";

		// ---必须参数
		// 商户号
		String mch_id = Constants.partner;
		// 随机字符串
		String nonce_str = WechatUtils.getRandomNum();

		// 商品描述根据情况修改
		String body = model.getBody();

		// 商户订单号
		String out_trade_no = orderId;

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", Constants.appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("attach", attach);
		packageParams.put("out_trade_no", out_trade_no);

		// 这里写的金额为1 分到时修改
		packageParams.put("total_fee", totalFee);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);

		packageParams.put("trade_type", trade_type);
		packageParams.put("openid", openId);

		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(Constants.appid, Constants.appsecret, Constants.partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + Constants.appid + "</appid>" + "<mch_id>"
				+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" + "<sign>" + sign + "</sign>"
				+ "<body><![CDATA[" + body + "]]></body>" 
				+ "<out_trade_no>" + out_trade_no
				+ "</out_trade_no>" + "<attach>" + attach + "</attach>"
				+ "<total_fee>" + totalFee + "</total_fee>"
				+ "<spbill_create_ip>" + spbill_create_ip
				+ "</spbill_create_ip>" + "<notify_url>" + notify_url
				+ "</notify_url>" + "<trade_type>" + trade_type
				+ "</trade_type>" + "<openid>" + openId + "</openid>"
				+ "</xml>";
		String prepay_id = "";
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		
		
		prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);

		System.out.println("获取到的预支付ID：" + prepay_id);
		
		
		//获取prepay_id后，拼接最后请求支付所需要的package
		
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String timestamp = Sha1Util.getTimeStamp();
		String packages = "prepay_id="+prepay_id;
		finalpackage.put("appId", Constants.appid);  
		finalpackage.put("timeStamp", timestamp);  
		finalpackage.put("nonceStr", nonce_str);  
		finalpackage.put("package", packages);  
		finalpackage.put("signType", "MD5");
		//要签名
		String finalsign = reqHandler.createSign(finalpackage);
		
		String finaPackage = "\"appId\":\"" + Constants.appid + "\",\"timeStamp\":\"" + timestamp
		+ "\",\"nonceStr\":\"" + nonce_str + "\",\"package\":\""
		+ packages + "\",\"signType\" : \"MD5" + "\",\"paySign\":\""
		+ finalsign + "\"";

		System.out.println("V3 jsApi package:"+finaPackage);
		return finaPackage;
	}
}

