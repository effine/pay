/**
 * @author effine
 * @Date 2015年8月11日  上午10:24:33
 * @email verphen#gmail.com
 * @site http://www.effine.cn
 */

package cn.effine.utils;

public class Constants {
	// 微信支付商户开通后 微信会提供appid和appsecret和商户号partner
	public static String appid = null;
	public static String appsecret = null;
	public static String partner = null;
	
	// 这个参数partnerkey是在商户后台配置的一个32位的key,微信商户平台-账户设置-安全设置-api安全
	public static String partnerkey = null;

	// openId 是微信用户针对公众号的标识，授权的部分这里不解释
	public static String openId = "";

	public static String notifyurl = null;
	public static String orderQuery = null;

	// 加载类的时候初始化参数
	static {
		appid = PropertiesUtils.getWechatValue("appid");
		appsecret = PropertiesUtils.getWechatValue("AppSecret");
		partner = PropertiesUtils.getWechatValue("mch_id");
		partnerkey = PropertiesUtils.getWechatValue("api_secret_key");
		
		notifyurl = PropertiesUtils.getWechatValue("notifyurl");
		orderQuery = PropertiesUtils.getWechatValue("orderquery");
	}
}
