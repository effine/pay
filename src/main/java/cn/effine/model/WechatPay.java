package cn.effine.model;

public class WechatPay {
	private String orderId;	// 商户系统生成的订单号
	private int totalFee;	// 支付金额（单位分，不存在小数）
	private String spbillCreateIp;// 订单生成的机器 IP
	private String notifyUrl;// 这里notify_url是
								// 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等
	private String body;// 商品描述根据情况修改
	private String openId;// 微信用户对一个公众号唯一

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
}