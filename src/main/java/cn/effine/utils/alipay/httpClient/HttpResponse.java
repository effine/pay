package cn.effine.utils.alipay.httpClient;

import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.Header;

import cn.effine.utils.alipay.AlipayConfig;

/* *
 *类名：HttpResponse
 *功能：Http返回对象的封装
 *详细：封装Http返回信息
 */
public class HttpResponse {

    /**
     * 返回中的Header信息
     */
	private Header[] responseHeaders;
	
    /**
     * String类型的result
     */
	private String stringResult;
	
	/**
     * btye类型的result
     */
	private byte[] byteResult;

	
	
	
	
	//get set
	
	public Header[] getResponseHeaders() {
		return responseHeaders;
	}

	public void setResponseHeaders(Header[] responseHeaders) {
		this.responseHeaders = responseHeaders;
	}

	public String getStringResult() throws UnsupportedEncodingException {
		if(stringResult != null){
			return stringResult;
		}
		if(byteResult != null){
			return new String(byteResult,AlipayConfig.input_charset);
		}
		return null;
	}

	public void setStringResult(String stringResult) {
		this.stringResult = stringResult;
	}

	public byte[] getByteResult() {
		if(byteResult != null){
			return byteResult;
		}
		if(stringResult != null){
			return stringResult.getBytes();
		}
		return null;
	}

	public void setByteResult(byte[] byteResult) {
		this.byteResult = byteResult;
	}
	
	

	
	
	
	
}
