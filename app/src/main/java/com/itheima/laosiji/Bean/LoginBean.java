package com.itheima.laosiji.Bean;

/**
 * Created by John on 2016/9/26.
 */
public class LoginBean {

    /**
     * response : login
     * userInfo : {"userid":"20428"}
     */

    private String response;
    /**
     * userid : 20428
     */

    private UserInfoBean userInfo;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {
        private String userid;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
