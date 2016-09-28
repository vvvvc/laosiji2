package com.itheima.laosiji.Bean;

/**
 * Created by Administrator
 * Time  9/28/2016 8:42 PM
 */
public class OrderBean {

	/**
	 * orderId : 529326
	 * paymentType : 1
	 * price : 450
	 */

	private OrderInfoBean orderInfo;
	/**
	 * orderInfo : {"orderId":"529326","paymentType":1,"price":450}
	 * response : orderSubmit
	 */

	private String response;

	public OrderInfoBean getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfoBean orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public static class OrderInfoBean {
		private String orderId;
		private int paymentType;
		private int price;

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public int getPaymentType() {
			return paymentType;
		}

		public void setPaymentType(int paymentType) {
			this.paymentType = paymentType;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}
	}
}
