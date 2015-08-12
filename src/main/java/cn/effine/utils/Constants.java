/**
 * @author effine
 * @Date 2015年8月11日  上午10:24:33
 * @email verphen#gmail.com
 * @site http://www.effine.cn
 */

package cn.effine.utils;

public class Constants {
	// 微信支付商户开通后 微信会提供appid和appsecret和商户号partner
	public static String appid = PropertiesUtils.getValue("appid");
	public static String appsecret = PropertiesUtils.getValue("AppSecret");
	public static String partner = PropertiesUtils.getValue("mch_id");
	
	// 这个参数partnerkey是在商户后台配置的一个32位的key,微信商户平台-账户设置-安全设置-api安全
	public static String partnerkey = PropertiesUtils.getValue("api_secret_key");
	
	// openId 是微信用户针对公众号的标识，授权的部分这里不解释
	public static String openId = "";
	
	// 微信支付成功后通知地址 必须要求80端口并且地址不能带参数
	public static String notifyurl = "http://www.effine.net/wechat/callback"; // Key
	
	// 微信订单查询
	public static String orderQuery = "https://api.mch.weixin.qq.com/pay/orderquery";
	
}
