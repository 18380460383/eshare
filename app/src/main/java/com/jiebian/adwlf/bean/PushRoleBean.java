package com.jiebian.adwlf.bean;

import java.util.List;

/**
 * Describe:
 * Created by FuPei on 2016/2/25.
 */
public class PushRoleBean {

    /**
     * data : [{"uid":"31","username":"leo♪微信超級會員","relay":0,"imageurl":"http://wx.qlogo.cn/mmopen/2Tdbyf8rcyz3JaibicSWFYdXZlibsqgwgwRnkmBmKZlaSow8ussWuJg7jlVNcUWwvCqfHthIatJ6AKW9judGopRhib1wpnibcbSIA/0","rid":"1","roles_money":"0","name":"企业家"},{"uid":"69","username":"路过","relay":0,"imageurl":"http://wx.qlogo.cn/mmopen/Q3auHgzwzM6j3bCH5Kn2jX1oUU423JPySWBnzXjB8Vep3ZUv8ALbbUoVl7XgldIpiagUXUmQCKibeDYhsvc8vu5A/0","rid":"1","roles_money":"0","name":"企业家"}]
     * msg : 查询成功
     * code : 1
     */

    private String msg;
    private int code;
    /**
     * uid : 31
     * username : leo♪微信超級會員
     * relay : 0
     * imageurl : http://wx.qlogo.cn/mmopen/2Tdbyf8rcyz3JaibicSWFYdXZlibsqgwgwRnkmBmKZlaSow8ussWuJg7jlVNcUWwvCqfHthIatJ6AKW9judGopRhib1wpnibcbSIA/0
     * rid : 1
     * roles_money : 0
     * name : 企业家
     */

    private List<PushEntity> data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<PushEntity> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public List<PushEntity> getData() {
        return data;
    }

    public static class PushEntity {
        private String uid;
        private String username;
        private int relay;
        private String imageurl;
        private String rid;
        private String roles_money;
        private String name;

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setRelay(int relay) {
            this.relay = relay;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public void setRoles_money(String roles_money) {
            this.roles_money = roles_money;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUid() {
            return uid;
        }

        public String getUsername() {
            return username;
        }

        public int getRelay() {
            return relay;
        }

        public String getImageurl() {
            return imageurl;
        }

        public String getRid() {
            return rid;
        }

        public String getRoles_money() {
            return roles_money;
        }

        public String getName() {
            return name;
        }
    }
}
