package com.jiebian.adwlf.bean.entitys;

import com.jiebian.adwlf.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：Administrator
 * 时间：2016/6/2
 * 功能描述：
 */
public class EnCarBean {

    /**
     * id : 2ffce2bb899b7cdf78dc6346223a7e82
     * uid : 73
     * title : 90909909909900
     * price : 8420.00
     * create_time : 1464774643
     * car_media_type : 7
     * pid :
     * car_media_text : 微博
     * media_info : [{"id":"3b50b2ec0d4911e68e3d000c297ae6a4","mid":"","price":"320.00","real_price":"620.000000","pay_price":620,"media_name":"\u6210\u90fd\u7f51\u4e8b","media_logo":"http:\/\/tp2.sinaimg.cn\/3202789665\/180\/5662217580\/0","name":"\u666f\u745e","avatar":null,"uid":"64824","celebrity":"2"},{"id":"3b50b4900d4911e68e3d000c297ae6a4","mid":"","price":"7500.00","real_price":"7800.000000","pay_price":7800,"media_name":"\u6211\u53eb\u4e09\u9897\u7259","media_logo":"\/static\/images\/weibo_avatar_defaulat.png","name":"\u666f\u745e","avatar":null,"uid":"64824","celebrity":"2"}]
     * media_type_text : 微博
     * celebrity : 2
     */

    private String id;
    private String uid;
    private String title;
    private double price;
    private String create_time;
    private int car_media_type;
    private String pid;
    private String car_media_text;
    private List<MedaInfo> media_info;
    private String media_type_text;
    private String celebrity;
    private String position;
    private String activity_addr;
    private String activity_time;
    private String end_time;
    private boolean isSelect=false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getActivity_time() {
        return activity_time;
    }

    public void setActivity_time(String activity_time) {
        this.activity_time = activity_time;
    }

    public String getActivity_addr() {
        return activity_addr;
    }

    public void setActivity_addr(String activity_addr) {
        this.activity_addr = activity_addr;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setCar_media_type(int car_media_type) {
        this.car_media_type = car_media_type;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setCar_media_text(String car_media_text) {
        this.car_media_text = car_media_text;
    }

    public void setMedia_info(List<MedaInfo> media_info) {
        this.media_info = media_info;
    }

    public void setMedia_type_text(String media_type_text) {
        this.media_type_text = media_type_text;
    }

    public void setCelebrity(String celebrity) {
        this.celebrity = celebrity;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getCreate_time() {
        return create_time;
    }

    public int getCar_media_type() {
        return car_media_type;
    }

    public String getPid() {
        return pid;
    }

    public String getCar_media_text() {
        return car_media_text;
    }

    public List<MedaInfo> getMedia_info() {
       return media_info;
    }

    public String getMedia_type_text() {
        return media_type_text;
    }

    public String getCelebrity() {
        return celebrity;
    }
   public  class MedaInfo{

        /**
         * id : 3b50b2ec0d4911e68e3d000c297ae6a4
         * mid :
         * price : 320.00
         * real_price : 620.000000
         * pay_price : 620
         * media_name : 成都网事
         * media_logo : http://tp2.sinaimg.cn/3202789665/180/5662217580/0
         * name : 景瑞
         * avatar : null
         * uid : 64824
         * celebrity : 2
         */

        private String id;
        private String mid;
        private String price;
        private String real_price;
        private String pay_price;
        private String media_name;
        private String media_logo;
        private String name;
        private Object avatar;
        private String uid;
        private String celebrity;

        public void setId(String id) {
            this.id = id;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setReal_price(String real_price) {
            this.real_price = real_price;
        }

        public void setPay_price(String pay_price) {
            this.pay_price = pay_price;
        }

        public void setMedia_name(String media_name) {
            this.media_name = media_name;
        }

        public void setMedia_logo(String media_logo) {
            this.media_logo = media_logo;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setCelebrity(String celebrity) {
            this.celebrity = celebrity;
        }

        public String getId() {
            return id;
        }

        public String getMid() {
            return mid;
        }

        public String getPrice() {
            return price;
        }

        public String getReal_price() {
            return real_price;
        }

        public String getPay_price() {
            return pay_price;
        }

        public String getMedia_name() {

                return media_name;

        }

        public String getMedia_logo() {
            return media_logo;
        }

        public String getName() {
                return name;
        }

        public Object getAvatar() {
            return avatar;
        }

        public String getUid() {
            return uid;
        }

        public String getCelebrity() {
            return celebrity;
        }

       @Override
       public String toString() {
           return "MedaInfo{" +
                   "id='" + id + '\'' +
                   ", mid='" + mid + '\'' +
                   ", price='" + price + '\'' +
                   ", real_price='" + real_price + '\'' +
                   ", pay_price='" + pay_price + '\'' +
                   ", media_name='" + media_name + '\'' +
                   ", media_logo='" + media_logo + '\'' +
                   ", name='" + name + '\'' +
                   ", avatar=" + avatar +
                   ", uid='" + uid + '\'' +
                   ", celebrity='" + celebrity + '\'' +
                   '}';
       }
   }

    @Override
    public String toString() {
        return "EnCarBean{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", create_time='" + create_time + '\'' +
                ", car_media_type=" + car_media_type +
                ", pid='" + pid + '\'' +
                ", car_media_text='" + car_media_text + '\'' +
                ", media_info=" + media_info +
                ", media_type_text='" + media_type_text + '\'' +
                ", celebrity='" + celebrity + '\'' +
                ", position='" + position + '\'' +
                ", activity_addr='" + activity_addr + '\'' +
                ", activity_time='" + activity_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }
}
