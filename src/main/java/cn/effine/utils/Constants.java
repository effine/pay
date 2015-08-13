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
	
	public static String notifyurl = PropertiesUtils.getValue("notifyurl");
	public static String orderQuery = PropertiesUtils.getValue("orderquery");
	
}
