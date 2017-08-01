package com.jiebian.adwlf.bean;

/**
 * 创建者：Administrator
 * 时间：2016/7/8
 * 功能描述：
 */
public class ReporterAdressBean {


    public ReporterAdressBean(int region_id, int parent_id, String region_name, int region_type, int agency_id, int status, double price, int listorders) {
        this.region_id = region_id;
        this.parent_id = parent_id;
        this.region_name = region_name;
        this.region_type = region_type;
        this.agency_id = agency_id;
        this.status = status;
        this.price = price;
        this.listorders = listorders;
    }

    /**
     * region_id : 6
     * parent_id : 1
     * region_name : 广东
     * region_type : 1
     * agency_id : 0
     * status : 0
     * price : 0.0
     * listorders : 38
     */

    private int region_id;
    private int parent_id;
    private String region_name;
    private int region_type;
    private int agency_id;
    private int status;
    private double price;
    private int listorders;

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public void setRegion_type(int region_type) {
        this.region_type = region_type;
    }

    public void setAgency_id(int agency_id) {
        this.agency_id = agency_id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setListorders(int listorders) {
        this.listorders = listorders;
    }

    public int getRegion_id() {
        return region_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public int getRegion_type() {
        return region_type;
    }

    public int getAgency_id() {
        return agency_id;
    }

    public int getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }

    public int getListorders() {
        return listorders;
    }

    @Override
    public String toString() {
        return "ReporterAdressBean{" +
                "region_id=" + region_id +
                ", parent_id=" + parent_id +
                ", region_name='" + region_name + '\'' +
                ", region_type=" + region_type +
                ", agency_id=" + agency_id +
                ", status=" + status +
                ", price=" + price +
                ", listorders=" + listorders +
                '}';
    }
}
