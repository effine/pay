/**
 * @author effine
 * @Date 2015年9月23日  下午5:12:00
 * @email verphen#gmail.com
 * @site http://www.effine.cn
 */

package cn.effine.test;

import java.util.HashMap;
import java.util.Map;

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
	public Map<String, Object> ajaxEncryption() {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("a", 1);
		jsonMap.put("b", "");
		jsonMap.put("c", null);
		jsonMap.put("d", "wuzhuti.cn");
		return jsonMap;
	}
}
