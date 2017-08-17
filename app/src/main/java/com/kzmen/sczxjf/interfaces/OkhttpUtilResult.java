package com.kzmen.sczxjf.interfaces;

/**
 * Created by Administrator on 2017/8/17.
 */

public interface OkhttpUtilResult {
    void onSuccess(int type,String data);
    void onError(int code,String msg);
}
