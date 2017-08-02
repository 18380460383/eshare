package com.cardholder.adwlf.bean.entitys;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * 创建者：Administrator
 * 时间：2016/6/3
 * 功能描述：
 */
public class EnCoupon {
    /**
     * coupon_detail_id : ec577c3e271511e6b6fd000c297ae6a4
     * coupon_no :
     * coupon_title : asdfasdf
     * coupon_type_id : 38
     * create_time : 1452147023
     * end_time : 1467820800
     * id : be386060271611e6b6fd000c297ae6a4
     * lower_money : 1000.00
     * money : 100.00
     * order_money : 0.00
     * start_time : 1452096000
     * status : 2
     * uid : 73
     * use_time : 1460390400
     */

    private String coupon_detail_id;
    private String coupon_no;
    private String coupon_title;
    private String coupon_type_id;
    private int create_time;
    private Long end_time;
    private String id;
    private double lower_money;
    private String money;
    private String order_money;
    private Long start_time;
    private int status;
    private int uid;
    private Long use_time;
    private boolean ischeck=false;

    public boolean ischeck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public void setCoupon_detail_id(String coupon_detail_id) {
        this.coupon_detail_id = coupon_detail_id;
    }

    public void setCoupon_no(String coupon_no) {
        this.coupon_no = coupon_no;
    }

    public void setCoupon_title(String coupon_title) {
        this.coupon_title = coupon_title;
    }

    public void setCoupon_type_id(String coupon_type_id) {
        this.coupon_type_id = coupon_type_id;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLower_money(double lower_money) {
        this.lower_money = lower_money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setOrder_money(String order_money) {
        this.order_money = order_money;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setUse_time(Long use_time) {
        this.use_time = use_time;
    }

    public String getCoupon_detail_id() {
        return coupon_detail_id;
    }

    public String getCoupon_no() {
        return coupon_no;
    }

    public String getCoupon_title() {
        return coupon_title;
    }

    public String getCoupon_type_id() {
        return coupon_type_id;
    }

    public int getCreate_time() {
        return create_time;
    }

    public String getEnd_time() {
        Date d=new Date(end_time*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        return sdf.format(d);
    }

    public String getId() {
        return id;
    }

    public double getLower_money() {
        return lower_money;
    }

    public String getMoney() {
        return money;
    }

    public String getOrder_money() {
        return order_money;
    }

    public String getStart_time() {
        Date d=new Date(start_time*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        return sdf.format(d);
    }

    public int getStatus() {
        return status;
    }

    public int getUid() {
        return uid;
    }

    public String getUse_time() {
        Date d=new Date(use_time*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        return sdf.format(d);
    }


}
