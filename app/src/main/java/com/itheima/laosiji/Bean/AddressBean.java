package com.itheima.laosiji.Bean;

import java.util.List;

/**
 * Created by Marlboro丶 on 2016/9/26.
 */
public class AddressBean {

    /**
     * addressList : [{"addressArea":"abc","addressDetail":"abc","city":"abc","id":133,"isDefault":0,"name":"abc","phoneNumber":"123","province":"abc","zipCode":"12345"},{"addressArea":"洪山区","addressDetail":"文华路文华学院","city":"武汉市","id":134,"isDefault":0,"name":"张瑞丽","phoneNumber":"18986104910","province":"湖北","zipCode":"1008611"},{"addressArea":"洪山区","addressDetail":"街道口地铁c口","city":"武汉市","id":139,"isDefault":0,"name":"itcast","phoneNumber":"027-81970008","province":"湖北","zipCode":"430070"},{"addressArea":"洪山区","addressDetail":"街道口","city":"武汉","id":146,"isDefault":1,"name":"肖文","phoneNumber":"15801477821","province":"湖北","zipCode":"430070"},{"addressArea":"不要告诉你","addressDetail":"84639806@qq.com","city":"自贡","id":147,"isDefault":1,"name":"Billy","phoneNumber":"18682036558","province":"四川","zipCode":"643109"},{"addressArea":"不要告诉你","addressDetail":"84639806@qq.com","city":"自贡","id":148,"isDefault":1,"name":"Billy","phoneNumber":"18682036558","province":"四川","zipCode":"643109"},{"addressArea":"不要告诉你","addressDetail":"84639806@qq.com","city":"自贡","id":149,"isDefault":1,"name":"Billy","phoneNumber":"18682036558","province":"四川","zipCode":"643109"},{"addressArea":"不要告诉你","addressDetail":"84639806@qq.com","city":"自贡","id":150,"isDefault":1,"name":"Billy","phoneNumber":"18682036558","province":"四川","zipCode":"643109"},{"addressArea":"不要告诉你","addressDetail":"84639806@qq.com","city":"自贡","id":151,"isDefault":1,"name":"Billy","phoneNumber":"18682036558","province":"上海","zipCode":"643109"},{"addressArea":"不要告诉你","addressDetail":"84639806@qq.com","city":"自贡","id":152,"isDefault":1,"name":"Billy","phoneNumber":"18682036558","province":"四川","zipCode":"643109"},{"addressArea":"XI","addressDetail":"84639806@qq.com","city":"aaaaaaaaaaaaaaaaaaa","id":153,"isDefault":1,"name":"Billy","phoneNumber":"18682036558","province":"上海","zipCode":"643109"},{"addressArea":"不要告诉你","addressDetail":"84639806@qq.com","city":"自贡","id":154,"isDefault":1,"name":"Billy","phoneNumber":"18682036558","province":"四川","zipCode":"643109"},{"addressArea":"不要告诉你","addressDetail":"84639806@qq.com","city":"自贡","id":155,"isDefault":1,"name":"Billy","phoneNumber":"18682036558","province":"SH","zipCode":"643109"},{"addressArea":"不要告诉你","addressDetail":"84639806@qq.com","city":"自贡","id":156,"isDefault":1,"name":"Billy","phoneNumber":"18682036558","province":"四川","zipCode":"643109"},{"addressArea":"不要告诉你","addressDetail":"84639806@qq.com","city":"自贡","id":157,"isDefault":1,"name":"Billy","phoneNumber":"18682036558","province":"四川","zipCode":"643109"},{"addressArea":"不要告诉你","addressDetail":"84639806@qq.com","city":"自贡","id":158,"isDefault":1,"name":"aaa","phoneNumber":"18682036558","province":"四川","zipCode":"643109"},{"addressArea":"不要告诉你","addressDetail":"84639806@qq.com","city":"自贡","id":159,"isDefault":1,"name":"3535353539876435367","phoneNumber":"18682036558","province":"四川","zipCode":"643109"},{"addressArea":"浦东","addressDetail":"航头","city":"上海","id":160,"isDefault":1,"name":"小明","phoneNumber":"110","province":"上海","zipCode":"643109"},{"addressArea":"不要告诉你","addressDetail":"84639806@qq.com","city":"自贡","id":161,"isDefault":1,"name":"Billy","phoneNumber":"18682036558","province":"四川","zipCode":"643109"},{"addressArea":"不要告诉你","addressDetail":"84639806@qq.com","city":"自贡","id":162,"isDefault":1,"name":"Billy","phoneNumber":"18682036558","province":"四川","zipCode":"643109"},{"addressArea":"不要告诉你","addressDetail":"84639806@qq.com","city":"自贡","id":163,"isDefault":1,"name":"Billy","phoneNumber":"18682036558","province":"四川","zipCode":"643109"}]
     * response : addresssave
     */

    private String response;
    /**
     * addressArea : abc
     * addressDetail : abc
     * city : abc
     * id : 133
     * isDefault : 0
     * name : abc
     * phoneNumber : 123
     * province : abc
     * zipCode : 12345
     */

    private List<AddressListBean> addressList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<AddressListBean> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<AddressListBean> addressList) {
        this.addressList = addressList;
    }

    public static class AddressListBean {
        private String addressArea;
        private String addressDetail;
        private String city;
        private int id;
        private int isDefault;
        private String name;
        private String phoneNumber;
        private String province;
        private String zipCode;

        public String getAddressArea() {
            return addressArea;
        }

        public void setAddressArea(String addressArea) {
            this.addressArea = addressArea;
        }

        public String getAddressDetail() {
            return addressDetail;
        }

        public void setAddressDetail(String addressDetail) {
            this.addressDetail = addressDetail;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
    }
}
