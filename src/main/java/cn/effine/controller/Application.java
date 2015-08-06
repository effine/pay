/**
 * @author effine
 * @Date 2015年8月6日  下午3:31:17
 * @email verphen#gmail.com
 * @site http://www.effine.cn
 */

package cn.effine.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class Application {
	@Bean
	MessageService mockMessageService() {
		return new MessageService() {
			public String getMessage() {
				return "Hello World!";
			}
		};
	}

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				Application.class);
		MessagePrinter printer = context.getBean(MessagePrinter.class);
		printer.printMessage();
	}
}
