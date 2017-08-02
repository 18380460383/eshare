package com.cardholder.adwlf.ebean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/4.
 */
public class EType implements Serializable{

    /**
     * did : 3
     * item : 汽车
     */

    private int did;
    private String item;
    private boolean opt_for=false;

    public void setDid(int did) {
        this.did = did;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getDid() {
        return did;
    }

    public String getItem() {
        return item;
    }

    public boolean isOpt_for() {
        return opt_for;
    }

    public void setOpt_for(boolean opt_for) {
        this.opt_for = opt_for;
    }

    @Override
    public String toString() {
        return "EType{" +
                "did=" + did +
                ", item='" + item + '\'' +
                ", opt_for=" + opt_for +
                '}';
    }
}
