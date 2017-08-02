package com.cardholder.adwlf.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/1.
 */
public class TextViewColorBean {
    private String str;
    private boolean isc;

    public TextViewColorBean(String str, boolean isc) {
        this.str = str;
        this.isc = isc;
    }

    public boolean isc() {
        return isc;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void setIsc(boolean isc) {
        this.isc = isc;
    }

    @Override
    public String toString() {
        return "TextViewColorBean{" +
                "str='" + str + '\'' +
                ", isc=" + isc +
                '}';
    }

    public static List<TextViewColorBean> getListTextViewColorBean(List<String> list){
        List<TextViewColorBean> ListTextViewColorBean=new ArrayList<>();
        if(null!=list){
            int size = list.size();
            for(int i=0;i<size;i++){
                TextViewColorBean object = new TextViewColorBean(list.get(i),false);
                ListTextViewColorBean.add(object);
            }
        }

        return ListTextViewColorBean;
    }
}
