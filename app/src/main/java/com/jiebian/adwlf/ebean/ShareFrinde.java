package com.jiebian.adwlf.ebean;

/**
 * Created by Administrator on 2015/11/30.
 */
public class ShareFrinde {
    private String uid;
    private String username;
    private String imageurl;
    private String earn_money;
    private String pinyin;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPinYin() {
        return pinyin;
    }

    public void setPinYin(String letter) {
        this.pinyin = letter;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String name) {
        this.username = name;
    }

    public String getImageUrl() {
        return imageurl;
    }

    public void setImageUrl(String headurl) {
        this.imageurl = headurl;
    }

    public String getEarnMoney() {
        return earn_money;
    }

    public void setEarnMoney(String transpond) {
        this.earn_money = transpond;
    }

    @Override
    public String toString() {
        return "ShareFrinde{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", earn_money='" + earn_money + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }
}
