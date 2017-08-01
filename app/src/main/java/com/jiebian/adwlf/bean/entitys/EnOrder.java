package com.jiebian.adwlf.bean.entitys;

import android.text.TextUtils;

import java.util.List;

/**
 * 创建者：Administrator
 * 时间：2016/6/1
 * 功能描述：
 */
public class EnOrder {

    /**
     * report_url:报表页面链接
     * oid : 订单主键编号
     * uid : 用户主键编号
     * order_no: 订单号
     * order_type : 订单类型
     * all_order_type : ["7","9"]
     * order_money : 订单金额
     * order_last_money : 订单尾款(尾款+服务费)
     * create_time : 创建时间
     * order_status : 订单状态
     * order_status_name : 状态名
     * detail : {"title":"标题","position":"微信第几条","requires_title":"软文文章标题end_time软文截稿时间","activity_text":"找记者活动名","activity_time":"找记者活动时间","media":"媒体详情","activity_addr":"活动地点"}
     */

    private String oid;
    private String uid;
    private String order_no;
    private String order_type;
    private double order_money;
    private double order_last_money;
    private String create_time;
    private String order_status;
    private String order_status_name;
    private String report_url;
    private String pid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * title : 标题
     * position : 微信第几条
     * requires_title : 软文文章标题end_time软文截稿时间
     * activity_text : 找记者活动名
     * activity_time : 找记者活动时间
     * media:媒体详情
     */

    private DetailEntity detail;
    private List<String> all_order_type;

    public String getReport_url() {
        return report_url;
    }

    public void setReport_url(String report_url) {
        this.report_url = report_url;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public void setOrder_money(double order_money) {
        this.order_money = order_money;
    }

    public void setOrder_last_money(double order_last_money) {
        this.order_last_money = order_last_money;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public void setOrder_status_name(String order_status_name) {
        this.order_status_name = order_status_name;
    }

    public void setDetail(DetailEntity detail) {
        this.detail = detail;
    }

    public void setAll_order_type(List<String> all_order_type) {
        this.all_order_type = all_order_type;
    }

    public String getOid() {
        return oid;
    }

    public String getUid() {
        return uid;
    }

    public String getOrder_no() {
        return order_no;
    }

    public String getOrder_type() {
        return order_type;
    }

    public String getOrder_money() {
        String str="";
        if(order_last_money>0){
                str="尾款"+order_last_money;
        }
        return order_money+str;
    }

    public double getOrder_last_money() {
        return order_last_money;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getOrder_status_name() {
        return order_status_name;
    }

    public DetailEntity getDetail() {
        return detail;
    }

    public List<String> getAll_order_type() {
        return all_order_type;
    }

    public static class DetailEntity {
        private String title;
        private String position;
        private String requires_title;
        private String activity_text;
        private String activity_time;
        private String end_time;
        private String activity_addr;
        private List<String> media;

        public List<String> getMedia() {
            return media;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getActivity_addr() {
            return activity_addr;
        }

        public void setActivity_addr(String activity_addr) {
            this.activity_addr = activity_addr;
        }

        public void setMedia(List<String> media) {
            this.media = media;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public void setRequires_title(String requires_title) {
            this.requires_title = requires_title;
        }

        public void setActivity_text(String activity_text) {
            this.activity_text = activity_text;
        }

        public void setActivity_time(String activity_time) {
            this.activity_time = activity_time;
        }

        public String getTitle() {
            return title;
        }

        public String getPosition() {
            return position;
        }

        public String getRequires_title() {
            return requires_title;
        }

        public String getActivity_text() {
            return activity_text;
        }

        public String getActivity_time() {
            return activity_time;
        }

        @Override
        public String toString() {
            return "DetailEntity{" +
                    "title='" + title + '\'' +
                    ", position='" + position + '\'' +
                    ", requires_title='" + requires_title + '\'' +
                    ", activity_text='" + activity_text + '\'' +
                    ", activity_time='" + activity_time + '\'' +
                    ", end_time='" + end_time + '\'' +
                    ", activity_addr='" + activity_addr + '\'' +
                    ", media=" + media +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "EnOrder{" +
                "oid='" + oid + '\'' +
                ", uid='" + uid + '\'' +
                ", order_no='" + order_no + '\'' +
                ", order_type='" + order_type + '\'' +
                ", order_money=" + order_money +
                ", order_last_money=" + order_last_money +
                ", create_time='" + create_time + '\'' +
                ", order_status='" + order_status + '\'' +
                ", order_status_name='" + order_status_name + '\'' +
                ", report_url='" + report_url + '\'' +
                ", detail=" + detail +
                ", all_order_type=" + all_order_type +
                '}';
    }
}
