package com.cardholder.adwlf.bean.returned;

/**
 * 创建者：Administrator
 * 时间：2016/8/4
 * 功能描述：
 */
public class HomeType {

    /**
     * tid : 6
     * name : 信贷
     * type_color : #FFBC4D
     * default_url : http://ipx.api.jiebiannews.com/Public/images/观点@2x.png
     * selected_url : http://ipx.api.jiebiannews.com/Public/images/观点_点击@2x.png
     */

    private String tid;
    private String name;
    private String type_color;
    private String default_url;
    private String selected_url;

    public void setTid(String tid) {
        this.tid = tid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType_color(String type_color) {
        this.type_color = type_color;
    }

    public void setDefault_url(String default_url) {
        this.default_url = default_url;
    }

    public void setSelected_url(String selected_url) {
        this.selected_url = selected_url;
    }

    public String getTid() {
        return tid;
    }

    public String getName() {
        return name;
    }

    public String getType_color() {
        return type_color;
    }

    public String getDefault_url() {
        return default_url;
    }

    public String getSelected_url() {
        return selected_url;
    }
}
