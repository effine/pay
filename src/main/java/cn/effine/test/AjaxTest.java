/**
 * @author effine
 * @Date 2015年9月23日  下午5:12:00
 * @email verphen#gmail.com
 * @site http://www.effine.cn
 */

package cn.effine.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ajax返回值加密测试
 */
@Controller
public class AjaxTest {

	@RequestMapping("ajax")
	@ResponseBody
	public byte[] ajaxEncryption() {
		return "张亚飞".getBytes();
	}
}
