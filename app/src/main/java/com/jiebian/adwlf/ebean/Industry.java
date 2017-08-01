package com.jiebian.adwlf.ebean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/4.
 */
public class Industry implements Serializable{

    /**
     * iid : 8
     * item : 运动
     */

    private int iid;
    private String item;
    private boolean Opt_for=false;

    public boolean isOpt_for() {
        return Opt_for;
    }

    public void setOpt_for(boolean opt_for) {
        Opt_for = opt_for;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getIid() {
        return iid;
    }

    public String getItem() {
        return item;
    }

    @Override
    public String toString() {
        return "Industry{" +
                "iid=" + iid +
                ", item='" + item + '\'' +
                ", Opt_for=" + Opt_for +
                '}';
    }
}
