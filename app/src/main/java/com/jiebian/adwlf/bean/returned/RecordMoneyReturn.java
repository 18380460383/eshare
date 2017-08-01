package com.jiebian.adwlf.bean.returned;

import com.jiebian.adwlf.bean.RecordMoneyBean;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

/**
 * @author wu
 * @version 1.0
 */
public class RecordMoneyReturn {


    public String statuscode;

    public List<RecordMoneyBean> msg;


    public static RecordMoneyReturn parseJson(JSONObject jsonObj) {
        Gson gson = new Gson();
        RecordMoneyReturn bean = gson.fromJson(jsonObj.toString(), RecordMoneyReturn.class);

        return bean;
    }

}


