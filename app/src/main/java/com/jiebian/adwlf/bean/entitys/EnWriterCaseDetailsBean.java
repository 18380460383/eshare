package com.jiebian.adwlf.bean.entitys;

/**
 * 创建者：杨操
 * 时间：2016/6/1
 * 功能描述： 写手案例详情javaBean
 */
public class EnWriterCaseDetailsBean {

    /**
     * id : 05095a256e95e892b6d7d94c0350ad12
     * uid : 49
     * manuscript_title : 新的
     * manuscript_content : 阿萨德发送到
     * enterprise_uid :
     * enterprise_title :
     * create_time : 1463726824
     * deal_time : 0
     * case_desc : 阿斯蒂芬
     * is_recommend : 0
     * case_url :
     * order_id :
     * is_show : 2
     */
    /**
     * 主键编号
    */
    private String id;

    /**
     * 用户主键编号
     */
    private String uid;
    /**
     * 稿件标题
     */
    private String manuscript_title;
    /**
     * 稿件内容
     */
    private String manuscript_content;
    /**
     * 企业编号
     */
    private String enterprise_uid;
    /**
     * 企业名称
     */
    private String enterprise_title;
    /**
     * 创建时间
     */
    private long create_time;
    /**
     * 处理时间
     */
    private long deal_time;
    /**
     * 案例描述
     */
    private String case_desc;
    /**
     * 精品案例
     */
    private String is_recommend;
    /**
     * 案例地址
     */
    private String case_url;
    /**
     * 订单编号
     */
    private String order_id;
    /**
     * 是否显示
     */
    private String is_show;

    public void setId(String id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setManuscript_title(String manuscript_title) {
        this.manuscript_title = manuscript_title;
    }

    public void setManuscript_content(String manuscript_content) {
        this.manuscript_content = manuscript_content;
    }

    public void setEnterprise_uid(String enterprise_uid) {
        this.enterprise_uid = enterprise_uid;
    }

    public void setEnterprise_title(String enterprise_title) {
        this.enterprise_title = enterprise_title;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public void setDeal_time(long deal_time) {
        this.deal_time = deal_time;
    }

    public void setCase_desc(String case_desc) {
        this.case_desc = case_desc;
    }

    public void setIs_recommend(String is_recommend) {
        this.is_recommend = is_recommend;
    }

    public void setCase_url(String case_url) {
        this.case_url = case_url;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getManuscript_title() {
        return manuscript_title;
    }

    public String getManuscript_content() {
        return manuscript_content;
    }

    public String getEnterprise_uid() {
        return enterprise_uid;
    }

    public String getEnterprise_title() {
        return enterprise_title;
    }

    public long getCreate_time() {
        return create_time;
    }

    public long getDeal_time() {
        return deal_time;
    }

    public String getCase_desc() {
        return case_desc;
    }

    public String getIs_recommend() {
        return is_recommend;
    }

    public String getCase_url() {
        return case_url;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getIs_show() {
        return is_show;
    }
}
