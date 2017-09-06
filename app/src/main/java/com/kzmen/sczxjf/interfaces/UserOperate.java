package com.kzmen.sczxjf.interfaces;

/**
 * Created by pjj18 on 2017/9/6.
 */

public interface UserOperate {
    void onOperateSuccess(String type,String state);
    void onError(String type);
}
