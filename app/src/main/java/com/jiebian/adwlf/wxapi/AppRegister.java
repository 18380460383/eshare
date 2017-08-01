package com.jiebian.adwlf.wxapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jiebian.adwlf.util.TLog;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信注册
 *
 */
public class AppRegister extends BroadcastReceiver implements IWXAPIEventHandler {

    @Override
    public void onReceive(Context context, Intent data) {
        final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
        if (api.handleIntent(data, this)) {
            // intent handled as wechat request/response
            return;
        }
        // process other intent as you like
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

        TLog.log("onResp  onResp " + baseResp.openId);

        switch (baseResp.getType()) {
            case ConstantsAPI.COMMAND_SENDAUTH:
                break;
            case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                // 处理微信主程序返回的SendMessageToWX.Resp
                break;

            default:
                break;
        }
    }
}
