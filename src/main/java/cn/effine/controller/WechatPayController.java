
/**
 * @author effine
 * @Date 2015年8月5日  下午3:49:11
 * @email verphen#gmail.com
 * @site http://www.effine.cn
 */

package cn.effine.controller;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

import cn.effine.utils.StringCustomUtils;
import cn.effine.utils.WechatPropertiesUtils;

public class WechatPayController {
	/**
	 * 生成支付二维码
	 *
	 * @param crsId
	 *            课程ID
	 * @return 二维码URL
	 */
	@RequestMapping("qrcode")
	public String generateQRCode(HttpServletRequest request, HttpServletResponse response, int crsId) {
		// 二维码内容链接
		StringBuilder QRCodeLinks = new StringBuilder();
		QRCodeLinks.append("weixin：//wxpay/bizpayurl?");
		
		// 公众账号ID
		String appid = WechatPropertiesUtils.getValue("appid");
		QRCodeLinks.append("appid=" + appid);
		
		// 商户号
		String mch_id = WechatPropertiesUtils.getValue("mch_id");
		QRCodeLinks.append("&mch_id=" + mch_id);
		
		// 系统当前时间戳
		String time_stamp = String.valueOf(System.currentTimeMillis());
		QRCodeLinks.append("&time_stamp=" + time_stamp);
		
		// 随机字符串
		String nonce_str = StringCustomUtils.getRandomString(32);
		QRCodeLinks.append("&nonce_str=" + nonce_str);
		
		// 商品ID(课程ID或者订单号)
		// TODO effine 用订单号作为商品ID
		QRCodeLinks.append("&product_id=" + crsId);
		
		// 签名
		SortedMap<String, String> params = new TreeMap<String, String>();
		params.put("appid", appid);
		params.put("mch_id", mch_id);
		params.put("nonce_str", nonce_str);
		params.put("product_id", String.valueOf(crsId));
		params.put("time_stamp", time_stamp);
		
		String sign = null;
		QRCodeLinks.append("&sign=" + sign);
		
		return QRCodeLinks.toString();
	}
}


