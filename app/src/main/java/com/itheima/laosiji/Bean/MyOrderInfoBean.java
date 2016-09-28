package com.itheima.laosiji.Bean;

/**
 * @ 创建者   zhou
 * @ 创建时间   2016/9/25 15:35
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2016/9/25$
 * @ 更新描述  ${TODO}
 */
public class MyOrderInfoBean {


    /**
     * orderId : 903637
     * paymentType : 1
     * price : 450
     */

    private OrderInfoBean orderInfo;
    /**
     * orderInfo : {"orderId":"903637","paymentType":1,"price":450}
     * response : orderSubmit
     */

    private String        response;

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
        private int    paymentType;
        private int    price;

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
