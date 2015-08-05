
/**
 * @author effine
 * @Date 2015年8月5日  下午3:49:11
 * @email verphen#gmail.com
 * @site http://www.effine.cn
 */

package cn.effine.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

import cn.effine.utils.StringUtils;
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
		QRCodeLinks.append("appid=" + WechatPropertiesUtils.getValue("appid"));
		
		// 商户号
		QRCodeLinks.append("&mch_id=" + WechatPropertiesUtils.getValue("mch_id"));
		
		// 系统当前时间戳
		QRCodeLinks.append("&time_stamp=" + System.currentTimeMillis());
		
		// 随机字符串
		QRCodeLinks.append("&nonce_str=" + StringUtils.getRandomString(32));
		
		// 商品ID(课程ID或者订单号)
		// TODO effine 用订单号作为商品ID
		QRCodeLinks.append("&product_id=" + crsId);
		
		// 签名
		QRCodeLinks.append("&sign=" + StringUtils.getRandomString(32));
		
		return QRCodeLinks.toString();
	}
}


