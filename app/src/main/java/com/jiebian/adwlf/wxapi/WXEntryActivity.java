package com.jiebian.adwlf.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.jiebian.adwlf.AppContext;
import com.jiebian.adwlf.Constants;
import com.jiebian.adwlf.bean.TokenBean;
import com.jiebian.adwlf.control.CustomProgressDialog;
import com.jiebian.adwlf.ui.activity.personal.DetialActivity;
import com.jiebian.adwlf.ui.activity.personal.YaoActivity;
import com.jiebian.adwlf.net.NetworkDownload;
import com.jiebian.adwlf.util.EshareLoger;
import com.jiebian.adwlf.util.TLog;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.wechat.utils.WXAppExtendObject;
import cn.sharesdk.wechat.utils.WXMediaMessage;
import cn.sharesdk.wechat.utils.WechatHandlerActivity;

/**
 * weixin返回信息
 */
public class WXEntryActivity extends WechatHandlerActivity implements IWXAPIEventHandler {

    private CustomProgressDialog progressDialog;
    protected boolean _isVisible;
    private IWXAPI api;
    public boolean isPdShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContext.getInstance().setOneActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog = new CustomProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        initWeixin();
    }

    private void initWeixin() {
        if(api == null) {
            api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        }
        api.registerApp(Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        TLog.log("code " + baseResp.errCode);
        if(baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {

            // 认证登录
            if(baseResp.errCode == BaseResp.ErrCode.ERR_OK) {

                // 认证成功
                TLog.log(" id " + ((SendAuth.Resp) baseResp).code
                        + ((SendAuth.Resp) baseResp).country + ((SendAuth.Resp) baseResp).lang);

                AppContext.getInstance().weixinCode = ((SendAuth.Resp)baseResp).code;
                getToken(AppContext.getInstance().weixinCode);

            } else {
                // 认证失败
                Toast.makeText(this, "认证失败", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        } else if(baseResp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX){
            Intent weixinShare=new Intent();
//            dismissProgressDialog();
            // 分享结果
            if(baseResp.errCode == BaseResp.ErrCode.ERR_OK) {
                // 分享成功
                weixinShare.setAction(Constants.WEIXIN_SHARE);
                weixinShare.putExtra(Constants.WEIXIN_SHARE_KEY,Constants.WEIXIN_SHARE_VALUE_SUCCEED);
                sendBroadcast(weixinShare);
                TLog.log(" weixin shared result " + baseResp.errCode);
                DetialActivity act = AppContext.getInstance().detialActivity;
                YaoActivity yaoact = AppContext.getInstance().yaoActivity;
                if(act != null) {
                    act.afterShare();
                }
                if(yaoact != null) {
                    yaoact.onShareSuccess();
                    yaoact.dismissProgressDialog();
                }
                if(AppContext.getInstance().mBaseWebAct != null) {
                    EshareLoger.logI("BaseWeb不为空success");
                    AppContext.getInstance().mBaseWebAct.onShareSuccess();
                }

            } else {
                weixinShare.setAction(Constants.WEIXIN_SHARE);
                weixinShare.putExtra(Constants.WEIXIN_SHARE_KEY,Constants.WEIXIN_SHARE_VALUE_FAILURE);
                sendBroadcast(weixinShare);
                // 认证失败
                if(AppContext.getInstance().yaoActivity != null) {
                    AppContext.getInstance().yaoActivity.dismissProgressDialog();
                    AppContext.getInstance().yaoActivity.onShareCancel();
                }
                if(AppContext.getInstance().mBaseWebAct != null) {
                    EshareLoger.logI("BaseWeb不为空fail");
                    AppContext.getInstance().mBaseWebAct.onShareCancel();
                }

            }

            this.finish();
        }


    }


    // 请求网络 获取微信token
    private void getToken(String code) {
        EshareLoger.logI("getToken code = " + code);
        RequestParams params = new RequestParams();
        params.put("appid", Constants.APP_ID);
        params.put("secret", Constants.SECRET);
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        NetworkDownload.byteGet(this, Constants.URL_GET_TOKEN, params, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                try {
                    TokenBean token = TokenBean.parseJson(new JSONObject(new String(bytes)));
                    AppContext.getInstance().accessToken = token.access_token;
                    AppContext.getInstance().openid = token.openid;
                    getUserInfo();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(WXEntryActivity.this, "微信认证失败", Toast.LENGTH_SHORT).show();
                WXEntryActivity.this.finish();
            }
        });
    }


    // 获取微信信息
    private void getUserInfo() {
        System.out.println("idid" + AppContext.getInstance().openid);
        RequestParams params = new RequestParams();
        params.put("access_token", AppContext.getInstance().accessToken);
        params.put("openid", AppContext.getInstance().openid);
        NetworkDownload.byteGet(this, Constants.URL_GET_USERINFO, params, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                    String json = new String(bytes);
                    Intent weixinacc=new Intent();
                    weixinacc.setAction(Constants.WEIXIN_ACCREDIT);
                    weixinacc.putExtra(Constants.WEIXIN_ACCREDIT_KEY,json);
                    sendBroadcast(weixinacc);
                    WXEntryActivity.this.finish();
            }
            @Override
            public void onFailure() {
                Toast.makeText(WXEntryActivity.this, "微信认证失败", Toast.LENGTH_SHORT).show();
                WXEntryActivity.this.finish();
            }
        });
    }





    @Override
    protected void onRestart() {
        super.onRestart();
        this.finish();
    }

  /*  *//**
     * 处理微信发出的向第三方应用请求app message
     * <p>
     * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
     * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
     * 做点其他的事情，包括根本不打开任何页面
     */
    public void onGetMessageFromWXReq(WXMediaMessage msg) {
        Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
        startActivity(iLaunchMyself);
    }
    /**
     * 处理微信向第三方应用发起的消息
     * <p>
     * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
     * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
     * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
     * 回调。
     * <p>
     * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
     */
    public void onShowMessageFromWXReq(WXMediaMessage msg) {
        Toast.makeText(this, "aaaaaaaaaaaaa", Toast.LENGTH_SHORT).show();
        Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
        startActivity(iLaunchMyself);
        if (msg != null && msg.mediaObject != null
                && (msg.mediaObject instanceof WXAppExtendObject)) {
            WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
            Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
        }
    }
}
