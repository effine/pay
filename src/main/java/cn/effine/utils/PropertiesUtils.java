
/**
 * @author effine
 * @Date 2015年8月4日  下午3:30:00
 * @email verphen#gmail.com
 * @site http://www.effine.cn
 */

package cn.effine.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;

import org.apache.commons.lang3.StringUtils;

/**
 * Properties文件操作类
 */
public class PropertiesUtils {
	private PropertiesUtils() {
		// 构造方法私有化，外部不能实例化该类 
	}
	
	private static Properties payProperties;
	private static Properties wechatProperties;
	private static Properties alipayProperties;
	
	
	static {
		payProperties = new Properties();
		wechatProperties = new Properties();
		try {
			// 解析文件pay.properties
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("pay.properties");
			payProperties.load(is);
			is.close();
			
			// 解析文件wechat.properties
			InputStream wechatIs = Thread.currentThread().getContextClassLoader().getResourceAsStream("wechat.properties");
			wechatProperties.load(wechatIs);
			wechatIs.close();
			
			// 解析文件alipay.properties
			InputStream alipayIs = Thread.currentThread().getContextClassLoader().getResourceAsStream("wechat.properties");
			alipayProperties.load(alipayIs);
			alipayIs.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取pay.properties文件属性
	 * 
	 * @param key
	 *            文件key
	 * @return 文件key对应value
	 */
	public static String getPayValue(String key) {
		return payProperties.getProperty(key);
	}
	
	/**
	 * 获取wechat.properties文件属性
	 * 
	 * @param key
	 *            文件key
	 * @return 文件key对应value
	 */
	public static String getWechatValue(String key) {
		return wechatProperties.getProperty(key);
	}
	
	/**
	 * 获取alipay.properties文件属性
	 * 
	 * @param key
	 *            文件key
	 * @return 文件key对应value
	 */
	public static String getAlipayValue(String key) {
		return alipayProperties.getProperty(key);
	}
	
	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public static String createSign(SortedMap<String, String> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set<?> set = packageParams.entrySet();
		Iterator<?> iterator = set.iterator();
		while (iterator.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String,String> entry = (Entry<String, String>) iterator.next();
			String key = entry.getKey();
			String value = entry.getValue();
			if (StringUtils.isNotBlank(value)) {
				sb.append(key + "=" + value + "&");
			}
		}
		// wtbc4f4xyzmypfxg4nqqtllx4vj1bx52; 这个参数是在商户后台配置的一个32位的key,微信商户平台-账户设置-安全设置-api安全
		sb.append("key=" + "wtbc4f4xyzmypfxg4nqqtllx4vj1bx52");
		return MD5Utils.MD5Encode(sb.toString(), "utf-8").toUpperCase();
	}
	
	  /**
	   *description:获取扫码支付连接
	   *@param url
	   *@param xmlParam
	   *@return
	   * @author ex_yangxiaoyi
	   * @see
	   */
	/*
	  public static String getCodeUrl(String url,String xmlParam){
		  DefaultHttpClient client = new DefaultHttpClient();
		  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
		  HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
		  String code_url = "";
	     try {
			 httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			 HttpResponse response = httpclient.execute(httpost);
		     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
		    if(jsonStr.indexOf("FAIL")!=-1){
		    	return code_url;
		    }
		    Map map = doXMLParse(jsonStr);
		    code_url  = (String) map.get("code_url");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_url;
	  }
	  */
}