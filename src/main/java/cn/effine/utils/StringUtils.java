/**
 * @author effine
 * @Date 2015年8月5日  下午3:54:05
 * @email verphen#gmail.com
 * @site http://www.effine.cn
 */

package cn.effine.utils;

import java.util.Random;

/**
 * 字符串操作
 */
public class StringUtils {
	private StringUtils(){
		// 禁止外部实例化该类
	}
	
	/**
	 * 获取指定长度的随机字符串(数字+字母)
	 * 
	 * @param length
	 *            指定字符串长度
	 * @return 长度为length的字符串
	 */
	public static String getRandomString(int length) {
		char[] base = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(base.length);
			sb.append(base[num]);
		}
		return sb.toString();
	}
}