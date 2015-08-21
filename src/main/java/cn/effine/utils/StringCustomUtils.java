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
public class StringCustomUtils {
	private StringCustomUtils() {
		// 禁止外部实例化该类
	}

	/**
	 * 生成指定长度的随机字符串(数字+字母)
	 * 
	 * @param length
	 *            指定字符串长度
	 * @return 指定长度字符串
	 */
	public static String getRandomString(int length) {
		char[] array = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		Random random = new Random(System.currentTimeMillis());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(array.length);
			sb.append(array[num]);
		}
		return sb.toString();
	}

	/**
	 * 生成指定长度的随机数字字符串
	 * 
	 * @param length
	 *            长度
	 * @return 指定长度的数字字符串
	 */
	public static String getRandomNum(int length) {
		Random random = new Random(System.currentTimeMillis());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
}