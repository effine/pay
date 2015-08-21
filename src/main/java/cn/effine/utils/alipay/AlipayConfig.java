/**
 * @author effine
 * @Date 2015年8月19日  上午10:07:08
 * @email verphen#gmail.com
 * @site http://www.effine.cn
 */

package cn.effine.utils.alipay;

import cn.effine.utils.PropertiesUtils;

/* *
 *author : wlc
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = PropertiesUtils.getAlipayValue("ali_partner");
	
	// 收款支付宝账号
	public static String seller_email = PropertiesUtils.getAlipayValue("ali_seller_email");
	// 商户的私钥
	public static String key = PropertiesUtils.getAlipayValue("ali_key");

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = PropertiesUtils.getAlipayValue("ali_log_path");

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = PropertiesUtils.getAlipayValue("ali_input_charset");
	
	// 签名方式 不需修改
	public static String sign_type = PropertiesUtils.getAlipayValue("ali_sign_type");

}
