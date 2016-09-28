package com.itheima.laosiji.Bean;

import java.util.List;

/**
 * Created by Marlboro丶 on 2016/9/27.
 */
public class RecommendBean {

    /**
     * response : searchrecommend
     * searchKeywords : ["女裙","帽子","时尚女裙","时尚秋装","韩版外套","情女装","女鞋","韩版棉袄"]
     */

    private String response;
    private List<String> searchKeywords;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<String> getSearchKeywords() {
        return searchKeywords;
    }

    public void setSearchKeywords(List<String> searchKeywords) {
        this.searchKeywords = searchKeywords;
    }
}
