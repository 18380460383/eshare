package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.WeixinInfo;
import com.kzmen.sczxjf.bean.kzbean.UserBean;
import com.kzmen.sczxjf.bean.user.User_For_pe;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.EnWebUtil;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.TLog;
import com.kzmen.sczxjf.utils.JsonUtils;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;

import static com.alibaba.sdk.android.feedback.xblink.config.GlobalConfig.context;

public class IndexActivity extends SuperActivity {

    @InjectView(R.id.tv_login)
    TextView tvLogin;
    @InjectView(R.id.tv_register)
    TextView tvRegister;
    @InjectView(R.id.ll_login_weix)
    LinearLayout llLoginWeix;

    private BroadcastReceiver receiver;
    private boolean isStartShareReceiver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {

    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_index);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.tv_login, R.id.tv_register, R.id.ll_login_weix})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_login:
                intent = new Intent(IndexActivity.this, LoginActivity.class);
                break;
            case R.id.tv_register:
                intent = new Intent(IndexActivity.this, RegisterActivity.class);
                break;
            case R.id.ll_login_weix:
                showProgressDialog("跳转微信登录中");
                getToken();
                break;
        }
        if (intent != null) {
            startActivity(intent);
            // finish();
        }
    }



    public void getToken() {
        setAccBroadcastReceiver();
        IWXAPI api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "none";
        if (!api.sendReq(req)) {
            Toast.makeText(this, "请确定是否安装微信", Toast.LENGTH_LONG).show();
            dismissProgressDialog();
        }
    }

    private void setAccBroadcastReceiver() {
        isStartShareReceiver = true;
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    loginForWeixin(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.WEIXIN_ACCREDIT);
        registerReceiver(receiver, filter);
    }

    private void loginForWeixin(Intent data) throws JSONException {
        String json = data.getExtras().getString(Constants.WEIXIN_ACCREDIT_KEY);
        System.out.println("用户数据" + json);
        final WeixinInfo info = WeixinInfo.parseJson(new JSONObject(json));
        if (info != null) {
            showProgressDialog("登陆中");
            Map<String, String> params = new HashMap<>();
            params.put("data[weixin]", info.unionid + "");
            params.put("data[openid]", info.openid + "");
            params.put("data[username]", info.nickname + "");
            params.put("data[avatar]", info.headimgurl + "");
            params.put("data[third_country]", info.country + "");
            params.put("data[third_province]", info.province + "");
            params.put("data[third_city]", info.city + "");
            params.put("data[third_sex]", info.sex + "");
            OkhttpUtilManager.postNoCacah(this, "", params, new OkhttpUtilResult() {
                @Override
                public void onSuccess(int type, String data) {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(data);
                        Gson gson = new Gson();
                        UserBean bean = gson.fromJson(object.getString("data"), UserBean.class);
                        onLoginSuccess(bean);
                        startActivity(new Intent(IndexActivity.this, MainTabActivity.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorWrong(int code, String msg) {
                    Toast.makeText(IndexActivity.this, "微信登录失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void onLoginSuccess(UserBean bean) {
        AppContext.getInstance().setUserLogin(bean);
        AppContext.getInstance().setPersonageOnLine(true);
        AppContext.getInstance().setFirst();
        dismissProgressDialog();
        Intent intent = new Intent();
        intent.putExtra("loginstate", 1);
        setResult(RESULT_OK, intent);
        finish();
    }

}
