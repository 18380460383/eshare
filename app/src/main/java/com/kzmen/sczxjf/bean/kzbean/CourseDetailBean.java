package com.kzmen.sczxjf.bean.kzbean;

import java.util.List;

/**
 * Created by pjj18 on 2017/9/3.
 */

public class CourseDetailBean {
    private String cid;
    private String title;
    private String type;
    private String banner;
    private String isquestion;
    private String isxiaojiang;
    private String zans;
    private String views;
    private String share_title;
    private String share_des;
    private String share_image;
    private String share_linkurl;
    private String tid;
    private String tid_title;
    private String name;
    private String describe;
    private String course_time;
    private String course_stage;
    private String course_section;
    private String iscollect;
    private String iszan;
    private String questions_money;
    private String questions_button;
    private String questions_desc;
    private List<CourseStageBean>stage_list;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getIsquestion() {
        return isquestion;
    }

    public void setIsquestion(String isquestion) {
        this.isquestion = isquestion;
    }

    public String getIsxiaojiang() {
        return isxiaojiang;
    }

    public void setIsxiaojiang(String isxiaojiang) {
        this.isxiaojiang = isxiaojiang;
    }

    public String getZans() {
        return zans;
    }

    public void setZans(String zans) {
        this.zans = zans;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_des() {
        return share_des;
    }

    public void setShare_des(String share_des) {
        this.share_des = share_des;
    }

    public String getShare_image() {
        return share_image;
    }

    public void setShare_image(String share_image) {
        this.share_image = share_image;
    }

    public String getShare_linkurl() {
        return share_linkurl;
    }

    public void setShare_linkurl(String share_linkurl) {
        this.share_linkurl = share_linkurl;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTid_title() {
        return tid_title;
    }

    public void setTid_title(String tid_title) {
        this.tid_title = tid_title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCourse_time() {
        return course_time;
    }

    public void setCourse_time(String course_time) {
        this.course_time = course_time;
    }

    public String getCourse_stage() {
        return course_stage;
    }

    public void setCourse_stage(String course_stage) {
        this.course_stage = course_stage;
    }

    public String getCourse_section() {
        return course_section;
    }

    public void setCourse_section(String course_section) {
        this.course_section = course_section;
    }

    public String getIscollect() {
        return iscollect;
    }

    public void setIscollect(String iscollect) {
        this.iscollect = iscollect;
    }

    public String getIszan() {
        return iszan;
    }

    public void setIszan(String iszan) {
        this.iszan = iszan;
    }

    public String getQuestions_money() {
        return questions_money;
    }

    public void setQuestions_money(String questions_money) {
        this.questions_money = questions_money;
    }

    public String getQuestions_button() {
        return questions_button;
    }

    public void setQuestions_button(String questions_button) {
        this.questions_button = questions_button;
    }

    public String getQuestions_desc() {
        return questions_desc;
    }

    public void setQuestions_desc(String questions_desc) {
        this.questions_desc = questions_desc;
    }

    public List<CourseStageBean> getStage_list() {
        return stage_list;
    }

    public void setStage_list(List<CourseStageBean> stage_list) {
        this.stage_list = stage_list;
    }

    @Override
    public String toString() {
        return "CourseDetailBean{" +
                "cid='" + cid + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", banner='" + banner + '\'' +
                ", isquestion='" + isquestion + '\'' +
                ", isxiaojiang='" + isxiaojiang + '\'' +
                ", zans='" + zans + '\'' +
                ", views='" + views + '\'' +
                ", share_title='" + share_title + '\'' +
                ", share_des='" + share_des + '\'' +
                ", share_image='" + share_image + '\'' +
                ", share_linkurl='" + share_linkurl + '\'' +
                ", tid='" + tid + '\'' +
                ", tid_title='" + tid_title + '\'' +
                ", name='" + name + '\'' +
                ", describe='" + describe + '\'' +
                ", course_time='" + course_time + '\'' +
                ", course_stage='" + course_stage + '\'' +
                ", course_section='" + course_section + '\'' +
                ", iscollect='" + iscollect + '\'' +
                ", iszan='" + iszan + '\'' +
                ", questions_money='" + questions_money + '\'' +
                ", questions_button='" + questions_button + '\'' +
                ", questions_desc='" + questions_desc + '\'' +
                ", stage_list=" + stage_list +
                '}';
    }
}
