/**
 * @author effine
 * @Date 2015年10月8日  下午2:02:32
 * @email verphen#gmail.com
 * @site http://www.effine.cn
 */

package cn.effine.test;

import cn.effine.utils.QRCodeUtils;

public class QRCodeTest {

	public static void main(String[] args) {
		String url = "https://www.baidu.com/";
		QRCodeUtils.generateQRCode(200, 200, url, "d:/");
		QRCodeUtils.generateQRCode(300, 300, "张亚飞", "d:/");
	}
}
