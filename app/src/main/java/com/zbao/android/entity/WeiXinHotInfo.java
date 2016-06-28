package com.zbao.android.entity;

import java.util.List;

/**
 * Created by zhangbao on 16/6/28.
 */
public class WeiXinHotInfo {


    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2016-03-31","title":"小本生意做什么挣钱十七大小本生意推荐","description":"创业最前线","picUrl":"","url":""}]
     */

    private int code;

    private String msg;
    /**
     * ctime : 2016-03-31
     * title : 小本生意做什么挣钱十七大小本生意推荐
     * description : 创业最前线
     * picUrl :
     * url :
     */
    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
