package com.jiebian.adwlf.ebean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/2.
 */
public class GeneralizeMsg implements Serializable {

    /**
     * pid : 64
     * uid : 63235
     * projectname : 但是大喊大叫
     * totalcost : 400
     * state : 待审核
     * datetime : 2015-10-29 18:33:33
     * relay_num : 266
     */
    private String platform;
    private String pid;
    private String uid;
    private String projectname;
    private String totalcost;
    private String state;
    private String datetime;
    private int relay_num;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public void setTotalcost(String totalcost) {
        this.totalcost = totalcost;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setRelay_num(int relay_num) {
        this.relay_num = relay_num;
    }

    public String getPid() {
        return pid;
    }

    public String getUid() {
        return uid;
    }

    public String getProjectname() {
        return projectname;
    }

    public String getTotalcost() {
        return totalcost;
    }

    public String getState() {
        return state;
    }

    public String getDatetime() {
        return datetime;
    }

    public int getRelay_num() {
        return relay_num;
    }

    @Override
    public String toString() {
        return "GeneralizeMsg{" +
                "pid='" + pid + '\'' +
                ", uid='" + uid + '\'' +
                ", projectname='" + projectname + '\'' +
                ", totalcost=" + totalcost +
                ", state='" + state + '\'' +
                ", datetime='" + datetime + '\'' +
                ", relay_num=" + relay_num +
                '}';
    }
}
