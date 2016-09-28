package com.itheima.laosiji.Bean;

import java.util.List;

/**
 * @ 创建者   zhou
 * @ 创建时间   2016/9/24 16:41
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2016/9/24$
 * @ 更新描述  ${TODO}
 */
public class MyCommentBean {
    /**
     * comment : [{"content":"裙子不错","time":2014,"title":"裙子","username":"赵日天"},{"content":"好裙子","time":2015,"title":"裙子","username":"李"}]
     * response : productComment
     */

    private String            response;
    /**
     * content : 裙子不错
     * time : 2014
     * title : 裙子
     * username : 赵日天
     */

    private List<CommentBean> comment;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public static class CommentBean {
        private String content;
        private int    time;
        private String title;
        private String username;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
