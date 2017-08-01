package com.jiebian.adwlf.bean;

import com.jiebian.adwlf.ebean.EType;
import com.jiebian.adwlf.ebean.Industry;

import java.util.List;

/**
 * Created by Administrator on 2015/12/16.
 */
public class IIData {
    private   List<EType> etypelist;
    private   List<Industry> industryList;
    private static IIData iidata;
    private IIData(){};

    public static IIData getIIData(){
        if(iidata==null) {
            iidata = new IIData();
        }
            return iidata;

    }
    public static void setNull(){
        iidata=null;
    }

    public List<EType> getEtypelist() {
        return etypelist;
    }

    public void setEtypelist(List<EType> etypelist) {
        this.etypelist = etypelist;
    }

    public  List<Industry> getIndustryList() {
        return industryList;
    }

    public  void setIndustryList(List<Industry> industryList) {
        this.industryList = industryList;
    }
}
