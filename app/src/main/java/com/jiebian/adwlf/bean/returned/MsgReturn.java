package com.jiebian.adwlf.bean.returned;

import com.jiebian.adwlf.bean.MsgBean;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

/**
 * 请求消息列表对象
 */
public class MsgReturn {
    public String code;
    public List<MsgBean> data;
    public String msg;
    public static MsgReturn parseJson(JSONObject jsonObj) {
        Gson gson = new Gson();
        MsgReturn bean = gson.fromJson(jsonObj.toString(), MsgReturn.class);
        return bean;
    }
}
