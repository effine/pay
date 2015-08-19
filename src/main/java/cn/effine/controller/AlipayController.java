
/**
 * @author effine
 * @Date 2015年8月19日  上午10:07:08
 * @email verphen#gmail.com
 * @site http://www.effine.cn
 */

package cn.effine.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.effine.utils.alipay.AlipayNotify;
import cn.effine.utils.alipay.DateInterface;

/**
 * 支付宝支付 
 */
@Controller
@RequestMapping("alipay")
public class AlipayController {

	/**
	 * 调用支付宝
	 * @param subject 商品名称
	 * @param total_fee 商品价格  已舍弃，改为从数据库查询计算
	 * @param crsId 课程id
	 * @param useBean 是否使用云豆  1 使用  0 不使用
	 */
	@RequestMapping("aliSubmit")
	@ResponseBody
	public void aliSubmit(HttpServletRequest request, HttpServletResponse response){
		//对接支付宝
		DateInterface d = new DateInterface();
		//String message = d.intoParameter(orderNum, subject, total_fee, "");
		String message = d.intoParameter("", "", "", "");
	}
	
	@RequestMapping("callback")
	public String callback(HttpServletRequest request, HttpServletResponse response){
		
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数//
		//商户订单号 eg 2015080715131704519

		String out_trade_no =request.getParameter("out_trade_no");

		//支付宝交易号 2015080700001000510061511244

		String trade_no = request.getParameter("trade_no");

		//交易状态 TRADE_SUCCESS
		String trade_status = request.getParameter("trade_status");

		//计算得出通知验证结果 false
		boolean verify_result = AlipayNotify.verify(params);
		
		if(verify_result){//验证成功
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				//处理自己的业务逻辑(根据订单号，更新订单支付状态)
			}

		}
		// 回调URL
		return "callback";
	}
}


