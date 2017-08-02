package com.cardholder.adwlf.bean.entitys;

/**
 * 创建者：杨操
 * 时间：2016/5/31
 * 功能描述：写手案例列表 单个javaBean
 */
public class EnWriterCaseBean {

    /**
     * id : b90cd2b211d911e69cc5000c297ae6a4
     * manuscript_title : 精彩案例
     * create_time : 2016年05月04日
     * case_desc :
     */

    private String id;
    private String manuscript_title;
    private String create_time;
    private String case_desc;

    public void setId(String id) {
        this.id = id;
    }

    public void setManuscript_title(String manuscript_title) {
        this.manuscript_title = manuscript_title;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setCase_desc(String case_desc) {
        this.case_desc = case_desc;
    }

    public String getId() {
        return id;
    }

    public String getManuscript_title() {
        return manuscript_title;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getCase_desc() {
        return case_desc;
    }
}
