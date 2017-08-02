package com.cardholder.adwlf.ebean;


/**
 * Created by Administrator on 2015/11/5.
 */
public class Detalis {

    /**
     * pid : 63
     * uid : 63235
     * totalcost : 400
     * currentcost : 400
     * company_name : mmmmm
     * projectname : 测试新ui
     * imageurl : http://hdq1.360netnews.com/Uploads/push/hdq_5631ed6e237f8.jpg
     * projecturl : eieieirjdjjdjr
     * state : forcheck
     * age : 1
     * gender : 不限
     * province : 四川
     * city : 成都
     * area : 锦江区
     * startdate : 2015-10-29 17:56:58
     * enddate : 2038-01-19 00:00:00
     * isenddate : 0
     * buyingdate : 0000-00-00 00:00:00
     * isneedscreenshot : 1
     * media_select : 0
     * media_push : 0
     * media_relay : 0
     * media_relayfan : 0
     * media_hits : 0
     * media_cast : 100
     * media_money : 0
     * freemedia_select : 266
     * freemedia_push : 0
     * freemedia_relay : 0
     * freemedia_relayfan : 0
     * freemedia_cast : 1
     * freemedia_hits : 0
     * freemedia_money : 0
     * datetime : 2015-10-29 17:57:03
     * linear : {"time":["2015-10-29 17:56:58","2019-07-13 14:56:58","2023-03-27 11:56:58","2026-12-09 08:56:58","2030-08-23 05:56:58","2034-05-07 02:56:58","2038-01-19 00:00:00"],"seconds":["","","","","","",""],"relay":[0,0,0,0,0,0,0],"push":[0,0,0,0,0,0,0],"hits":[0,0,0,0,0,0,0],"fans":[0,0,0,0,0,0,0]}
     * iid : {"1":"美食","2":"萌宠","3":"汽车","7":"时尚","6":"娱乐","5":"房产"}
     * did : {"1":"餐饮","2":"互联网","3":"汽车","5":"房产"}
     */
    private String  platform;
    private String pid;
    private String uid;
    private String totalcost;
    private String currentcost;
    private String company_name;
    private String projectname;
    private String imageurl;
    private String projecturl;
    private String state;
    private String age;
    private String gender;
    private String province;
    private String city;
    private String area;
    private String startdate;
    private String enddate;
    private String isenddate;
    private String buyingdate;
    private String isneedscreenshot;
    private String media_select;
    private int media_push;
    private int media_relay;
    private int media_relayfan;
    private int media_hits;
    private String media_cast;
    private String media_money;
    private int freemedia_select;
    private int freemedia_push;
    private int freemedia_relay;
    private int freemedia_relayfan;
    private String freemedia_cast;
    private int freemedia_hits;
    private String freemedia_money;
    private String datetime;
    private LinearEntity linear;
    private String  isfans;
    private int  push_role_select;
    private int  push_role_relay;

    public int getPush_role_select() {
        return push_role_select;
    }

    public void setPush_role_select(int push_role_select) {
        this.push_role_select = push_role_select;
    }

    public int getPush_role_relay() {
        return push_role_relay;
    }

    public void setPush_role_relay(int push_role_relay) {
        this.push_role_relay = push_role_relay;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getIsfans() {
        return isfans;
    }

    public void setIsfans(String isfans) {
        this.isfans = isfans;
    }

    public static class LinearEntity {
        private java.util.List<String> time;
        private java.util.List<String> seconds;
        private java.util.List<Integer> relay;
        private java.util.List<Integer> push;
        private java.util.List<Integer> hits;
        private java.util.List<Integer> fans;

        public  void setTime(java.util.List<String> time) {
            this.time = time;
        }

        public void setSeconds(java.util.List<String> seconds) {
            this.seconds = seconds;
        }

        public void setRelay(java.util.List<Integer> relay) {
            this.relay = relay;
        }

        public void setPush(java.util.List<Integer> push) {
            this.push = push;
        }

        public void setHits(java.util.List<Integer> hits) {
            this.hits = hits;
        }

        public void setFans(java.util.List<Integer> fans) {
            this.fans = fans;
        }

        public java.util.List<String> getTime() {
            return time;
        }

        public java.util.List<String> getSeconds() {
            return seconds;
        }

        public java.util.List<Integer> getRelay() {
            return relay;
        }

        public java.util.List<Integer> getPush() {
            return push;
        }

        public java.util.List<Integer> getHits() {
            return hits;
        }

        public java.util.List<Integer> getFans() {
            return fans;
        }

        @Override
        public String toString() {
            return "LinearEntity{" +
                    "time=" + time +
                    ", seconds=" + seconds +
                    ", relay=" + relay +
                    ", push=" + push +
                    ", hits=" + hits +
                    ", fans=" + fans +
                    '}';
        }
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(String totalcost) {
        this.totalcost = totalcost;
    }

    public String getCurrentcost() {
        return currentcost;
    }

    public void setCurrentcost(String currentcost) {
        this.currentcost = currentcost;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getProjecturl() {
        return projecturl;
    }

    public void setProjecturl(String projecturl) {
        this.projecturl = projecturl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getIsenddate() {
        return isenddate;
    }

    public void setIsenddate(String isenddate) {
        this.isenddate = isenddate;
    }

    public String getBuyingdate() {
        return buyingdate;
    }

    public void setBuyingdate(String buyingdate) {
        this.buyingdate = buyingdate;
    }

    public String getIsneedscreenshot() {
        return isneedscreenshot;
    }

    public void setIsneedscreenshot(String isneedscreenshot) {
        this.isneedscreenshot = isneedscreenshot;
    }

    public String getMedia_select() {
        return media_select;
    }

    public void setMedia_select(String media_select) {
        this.media_select = media_select;
    }

    public int getMedia_push() {
        return media_push;
    }

    public void setMedia_push(int media_push) {
        this.media_push = media_push;
    }

    public int getMedia_relay() {
        return media_relay;
    }

    public void setMedia_relay(int  media_relay) {
        this.media_relay = media_relay;
    }

    public int getMedia_relayfan() {
        return media_relayfan;
    }

    public void setMedia_relayfan(int media_relayfan) {
        this.media_relayfan = media_relayfan;
    }

    public int getMedia_hits() {
        return media_hits;
    }

    public void setMedia_hits(int media_hits) {
        this.media_hits = media_hits;
    }

    public String getMedia_cast() {
        return media_cast;
    }

    public void setMedia_cast(String media_cast) {
        this.media_cast = media_cast;
    }

    public String getMedia_money() {
        return media_money;
    }

    public void setMedia_money(String media_money) {
        this.media_money = media_money;
    }

    public int getFreemedia_select() {
        return freemedia_select;
    }

    public void setFreemedia_select(int freemedia_select) {
        this.freemedia_select = freemedia_select;
    }

    public int getFreemedia_push() {
        return freemedia_push;
    }

    public void setFreemedia_push(int freemedia_push) {
        this.freemedia_push = freemedia_push;
    }

    public int getFreemedia_relay() {
        return freemedia_relay;
    }

    public void setFreemedia_relay(int freemedia_relay) {
        this.freemedia_relay = freemedia_relay;
    }

    public int getFreemedia_relayfan() {
        return freemedia_relayfan;
    }

    public void setFreemedia_relayfan(int freemedia_relayfan) {
        this.freemedia_relayfan = freemedia_relayfan;
    }

    public String getFreemedia_cast() {
        return freemedia_cast;
    }

    public void setFreemedia_cast(String freemedia_cast) {
        this.freemedia_cast = freemedia_cast;
    }

    public int getFreemedia_hits() {
        return freemedia_hits;
    }

    public void setFreemedia_hits(int freemedia_hits) {
        this.freemedia_hits = freemedia_hits;
    }

    public String getFreemedia_money() {
        return freemedia_money;
    }

    public void setFreemedia_money(String freemedia_money) {
        this.freemedia_money = freemedia_money;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public LinearEntity getLinear() {
        return linear;
    }

    public void setLinear(LinearEntity linear) {
        this.linear = linear;
    }


    @Override
    public String toString() {
        return "Detalis{" +
                "pid='" + pid + '\'' +
                ", uid='" + uid + '\'' +
                ", totalcost=" + totalcost +
                ", currentcost=" + currentcost +
                ", company_name='" + company_name + '\'' +
                ", projectname='" + projectname + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", projecturl='" + projecturl + '\'' +
                ", state='" + state + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", startdate='" + startdate + '\'' +
                ", enddate='" + enddate + '\'' +
                ", isenddate='" + isenddate + '\'' +
                ", buyingdate='" + buyingdate + '\'' +
                ", isneedscreenshot='" + isneedscreenshot + '\'' +
                ", media_select='" + media_select + '\'' +
                ", media_push='" + media_push + '\'' +
                ", media_relay='" + media_relay + '\'' +
                ", media_relayfan='" + media_relayfan + '\'' +
                ", media_hits='" + media_hits + '\'' +
                ", media_cast=" + media_cast +
                ", media_money='" + media_money + '\'' +
                ", freemedia_select='" + freemedia_select + '\'' +
                ", freemedia_push='" + freemedia_push + '\'' +
                ", freemedia_relay='" + freemedia_relay + '\'' +
                ", freemedia_relayfan='" + freemedia_relayfan + '\'' +
                ", freemedia_cast=" + freemedia_cast +
                ", freemedia_hits='" + freemedia_hits + '\'' +
                ", freemedia_money='" + freemedia_money + '\'' +
                ", datetime='" + datetime + '\'' +
                ", linear=" + linear +
                '}';
    }
}
