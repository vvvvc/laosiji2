package com.itheima.laosiji.Bean;

import java.util.List;

/**
 * Created by Marlboro丶 on 2016/9/27.
 */
public class SearchResultBean {

    /**
     * productList : [{"id":5,"marketPrice":98,"name":"时尚女裙","pic":"/images/product/detail/a1.jpg","price":108},{"id":3,"marketPrice":500,"name":"女裙","pic":"/images/product/detail/c1.jpg","price":300}]
     * response : search
     */

    private String response;
    /**
     * id : 5
     * marketPrice : 98
     * name : 时尚女裙
     * pic : /images/product/detail/a1.jpg
     * price : 108
     */

    private List<ProductListBean> productList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<ProductListBean> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductListBean> productList) {
        this.productList = productList;
    }

    public static class ProductListBean {
        private int id;
        private int marketPrice;
        private String name;
        private String pic;
        private int price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(int marketPrice) {
            this.marketPrice = marketPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
