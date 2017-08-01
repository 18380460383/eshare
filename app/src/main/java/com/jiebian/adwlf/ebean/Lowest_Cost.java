package com.jiebian.adwlf.ebean;

/**
 * Created by Administrator on 2015/11/3.
 */
public class Lowest_Cost {

    /**
     * cost_zd : 10
     * freemedia_num_zd : 10
     * freemedia_cast_zd : 1
     * freemedia_money : 5
     * media_num_zd : 0
     * media_cast_zd : 100
     * media_money : 1
     * screenshot : 0.5
     * isscreenshot : 0
     */
    private String int_tip_alert;
    private String app_server;
    private String cost_zd;
    private int freemedia_num_zd;
    private String freemedia_cast_zd;
    private String freemedia_money;
    private int media_num_zd;
    private int media_cast_zd;
    private String media_money;
    private double screenshot;
    private int isscreenshot;
    private int user_num;
    private int fans_num;
    private String  freemedia_cast_zd_alert;

    public String getInt_tip_alert() {
        return int_tip_alert;
    }

    public void setInt_tip_alert(String int_tip_alert) {
        this.int_tip_alert = int_tip_alert;
    }

    public String getApp_server() {
        return app_server;
    }

    public void setApp_server(String app_server) {
        this.app_server = app_server;
    }

    public String getFreemedia_cast_zd_alert() {
        return freemedia_cast_zd_alert;
    }

    public void setFreemedia_cast_zd_alert(String freemedia_cast_zd_alert) {
        this.freemedia_cast_zd_alert = freemedia_cast_zd_alert;
    }

    public int getUser_num() {
        return user_num;
    }

    public void setUser_num(int user_num) {
        this.user_num = user_num;
    }

    public int getFans_num() {
        return fans_num;
    }

    public void setFans_num(int fans_num) {
        this.fans_num = fans_num;
    }

    public void setCost_zd(String cost_zd) {
        this.cost_zd = cost_zd;
    }

    public void setFreemedia_num_zd(int freemedia_num_zd) {
        this.freemedia_num_zd = freemedia_num_zd;
    }

    public void setFreemedia_cast_zd(String freemedia_cast_zd) {
        this.freemedia_cast_zd = freemedia_cast_zd;
    }

    public void setFreemedia_money(String freemedia_money) {
        this.freemedia_money = freemedia_money;
    }

    public void setMedia_num_zd(int media_num_zd) {
        this.media_num_zd = media_num_zd;
    }

    public void setMedia_cast_zd(int media_cast_zd) {
        this.media_cast_zd = media_cast_zd;
    }

    public void setMedia_money(String media_money) {
        this.media_money = media_money;
    }

    public void setScreenshot(double screenshot) {
        this.screenshot = screenshot;
    }

    public void setIsscreenshot(int isscreenshot) {
        this.isscreenshot = isscreenshot;
    }

    public String getCost_zd() {
        return cost_zd;
    }

    public int getFreemedia_num_zd() {
        return freemedia_num_zd;
    }

    public String getFreemedia_cast_zd() {
        return freemedia_cast_zd;
    }

    public String getFreemedia_money() {
        return freemedia_money;
    }

    public int getMedia_num_zd() {
        return media_num_zd;
    }

    public int getMedia_cast_zd() {
        return media_cast_zd;
    }

    public String getMedia_money() {
        return media_money;
    }

    public double getScreenshot() {
        return screenshot;
    }

    public int getIsscreenshot() {
        return isscreenshot;
    }

    @Override
    public String toString() {
        return "Lowest_Cost{" +
                "cost_zd=" + cost_zd +
                ", freemedia_num_zd=" + freemedia_num_zd +
                ", freemedia_cast_zd=" + freemedia_cast_zd +
                ", freemedia_money='" + freemedia_money + '\'' +
                ", media_num_zd=" + media_num_zd +
                ", media_cast_zd=" + media_cast_zd +
                ", media_money='" + media_money + '\'' +
                ", screenshot=" + screenshot +
                ", isscreenshot=" + isscreenshot +
                ", user_num=" + user_num +
                ", fans_num=" + fans_num +
                '}';
    }
}
