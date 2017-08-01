package com.jiebian.adwlf.ebean;

/**
 * Created by Administrator on 2015/11/2.
 */
public class Finance {


    /**
     * id : 0a6ce15427c96cf72be13c6d1652e225
     * uid : 73
     * create_time : 1464232399
     * change_type : 1
     * remark : 订单支付(订单号:20160526111319001)
     * old_money : 920066.69
     * new_money : 919916.69
     * change_money : 150.00
     * change_uid : 73
     * discount : 0.00
     */

    private String id;
    private String uid;
    private long create_time;
    private String change_type;
    private String remark;
    private double old_money;
    private double new_money;
    private double change_money;
    private String change_uid;
    private double discount;

    public void setId(String id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public void setChange_type(String change_type) {
        this.change_type = change_type;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setOld_money(double old_money) {
        this.old_money = old_money;
    }

    public void setNew_money(double new_money) {
        this.new_money = new_money;
    }

    public void setChange_money(double change_money) {
        this.change_money = change_money;
    }

    public void setChange_uid(String change_uid) {
        this.change_uid = change_uid;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public long getCreate_time() {
        return create_time*1000;
    }

    public String getChange_type() {
        return change_type;
    }

    public String getRemark() {
        return remark;
    }

    public double getOld_money() {
        return old_money;
    }

    public double getNew_money() {
        return new_money;
    }

    public double getChange_money() {
        return change_money;
    }

    public String getChange_uid() {
        return change_uid;
    }

    public double getDiscount() {
        return discount;
    }
}
