package com.cardholder.adwlf.bean;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Describe:
 * Created by FuPei on 2016/2/19.
 */
public class PersonEntity implements Serializable{

    /**
     * id : 1
     * type : 2
     * uid : 69
     * username : 路过
     * imageurl : http://wx.qlogo.cn/mmopen/Q3auHgzwzM6j3bCH5Kn2jX1oUU423JPySWBnzXjB8Vep3ZUv8ALbbUoVl7XgldIpiagUXUmQCKibeDYhsvc8vu5A/0
     * roles_money : 0
     * rid : 2
     * nickname : 赵洪菲
     * company : 网立方
     * mediaplate : 时尚
     * mediatype : 网络
     * presscard : http://wx.qlogo.cn/mmopen/Q3auHgzwzM6j3bCH5Kn2jX1oUU423JPySWBnzXjB8VddQfaqjGFYT0uZtQsfe5kUkEJQE1PEp0gSuFNyianTYzA/0
     * weibofans : 100
     * wechatfans : 300
     * profession :职业
     */

    /*
    * "uid": "2242",
            "wechatfans": "",
            "weibofans": "",
            "nickname": "王月娇",
            "mediaplate": "新闻",
            "nickname_hide": null,
            "type": "2",
            "id": "12",
            "username": "360netnews",
            "fannum": "100-500",
            "rid": "2",
            "company": "腾讯大成网",
            "presscard": "http://7xnffx.com1.z0.glb.clouddn.com/1457071676228User2242Project",
            "imageurl": "http://wx.qlogo.cn/mmopen/2Tdbyf8rcyz7cnZCOUS10ZFD2ROSI0yWA5DQZ3icAURWiaFMOicUicFfpExUvPrc3zqDtsg6VblibCicuYMbJJnchUH6YJF8XeWFgE/0",
            "mediatype": "网络",
            "roles_money": 5
    * */
    private String id;
    private String type;
    private int uid;
    private String username;
    private String imageurl;
    private double roles_money;
    private String rid;
    private String nickname;
    private String company;
    private String industry;
    private String interest;
    private String mediaplate;
    private String mediatype;
    private String presscard;
    private String weibofans;
    private String wechatfans;
    private String address;
    private String tel;
    private String licence;
    private String turnover;
    private String headcount ;
    private String turnover_hidden;
    private String tel_hidden;
    private String headcount_hidden;
    private String fannum;
    private String form;
    private String nickname_hide;
    private String profession;

    public boolean isSelect() {
        return select;
    }

    public String getInterest() {

        return interest;
    }

    public PersonEntity setInterest(String interest) {
        this.interest = interest;
        return this;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getNickname_hide() {
        return nickname_hide;
    }

    public void setNickname_hide(String nickname_hide) {
        this.nickname_hide = nickname_hide;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getTel_hidden() {
        return tel_hidden;
    }

    public PersonEntity setTel_hidden(String tel_hidden) {
        this.tel_hidden = tel_hidden;
        return this;
    }

    public String getHeadcount_hidden() {
        return headcount_hidden;
    }

    public PersonEntity setHeadcount_hidden(String headcount_hidden) {
        this.headcount_hidden = headcount_hidden;
        return this;
    }

    public String getFannum() {
        return fannum;
    }

    public void setFannum(String fannum) {
        this.fannum = fannum;
    }

    /**
     * 没有被选择
     */
    private boolean select=false;

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    public String getHeadcount() {
        return headcount;
    }

    public void setHeadcount(String headcount) {
        this.headcount = headcount;
    }

    public String getTurnover_hidden() {
        return turnover_hidden;
    }

    public void setTurnover_hidden(String turnover_hidden) {
        this.turnover_hidden = turnover_hidden;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public boolean getSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setRoles_money(double roles_money) {
        this.roles_money = roles_money;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setMediaplate(String mediaplate) {
        this.mediaplate = mediaplate;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }

    public void setPresscard(String presscard) {
        this.presscard = presscard;
    }

    public void setWeibofans(String weibofans) {
        this.weibofans = weibofans;
    }

    public void setWechatfans(String wechatfans) {
        this.wechatfans = wechatfans;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getImageurl() {
        return imageurl;
    }

    public double getRoles_money() {
        return roles_money;
    }

    public String getRid() {
        return rid;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCompany() {
        return company;
    }

    public String getMediaplate() {
        return mediaplate;
    }

    public String getMediatype() {
        return mediatype;
    }

    public String getPresscard() {
        return presscard;
    }

    public String getWeibofans() {
        return weibofans;
    }

    public String getWechatfans() {
        return wechatfans;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
