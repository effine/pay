/**
 * @author effine
 * @Date 2015年8月6日  下午3:31:00
 * @email verphen#gmail.com
 * @site http://www.effine.cn
 */

package cn.effine.controller;

import org.springframework.beans.factory.annotation.Autowired;

public class MessagePrinter {
	final private MessageService service;

	@Autowired
	public MessagePrinter(MessageService service) {
		this.service = service;
	}

	public void printMessage() {
		System.out.println(this.service.getMessage());
	}
}
