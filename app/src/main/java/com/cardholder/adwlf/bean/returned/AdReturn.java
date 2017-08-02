package com.cardholder.adwlf.bean.returned;

import com.cardholder.adwlf.bean.AdBean;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

/**
 * @author wu
 * @version 1.0
 */
public class AdReturn {


    public String statuscode;

    public List<AdBean> msg;


    public static AdReturn parseJson(JSONObject jsonObj) {
        Gson gson = new Gson();
        AdReturn bean = gson.fromJson(jsonObj.toString(), AdReturn.class);

        return bean;
    }

}


