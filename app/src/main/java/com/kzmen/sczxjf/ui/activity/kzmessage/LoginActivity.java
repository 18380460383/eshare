package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.WeixinInfo;
import com.kzmen.sczxjf.bean.user.User_For_pe;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.EnWebUtil;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.TLog;
import com.kzmen.sczxjf.utils.JsonUtils;
import com.kzmen.sczxjf.utils.TextUtil;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;


public class LoginActivity extends SuperActivity {
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.et_phone)
    EditText etPhone;
    @InjectView(R.id.et_pass)
    EditText etPass;
    @InjectView(R.id.iv_show)
    ImageView ivShow;
    @InjectView(R.id.tv_login)
    TextView tvLogin;
    @InjectView(R.id.tv_forgetpass)
    TextView tv_forgetpass;
    @InjectView(R.id.activity_index)
    LinearLayout activityIndex;
    @InjectView(R.id.ll_login_weix)
    LinearLayout llLoginWeix;
    private BroadcastReceiver receiver;
    private boolean isStartShareReceiver = false;
    private String phone;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "登录");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_login2);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @OnClick({R.id.iv_show, R.id.tv_login, R.id.ll_login_weix, R.id.tv_forgetpass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_show:
                break;
            case R.id.tv_login:
                startActivity(new Intent(LoginActivity.this, MainTabActivity.class));
                //onLogin();
                break;
            case R.id.ll_login_weix:
                showProgressDialog("跳转微信登录中");
                getToken();
                break;
            case R.id.tv_forgetpass:
                startActivity(new Intent(LoginActivity.this, ForgetPassActivity.class));
                break;
        }
    }

    private void onLogin() {
        phone = etPhone.getText().toString();
        pass = etPass.getText().toString();
        Map<String, String> params = new HashMap<>();
        if (isAllRight()) {
            params.put("phone", phone);
            params.put("pwd", pass);
            OkhttpUtilManager.postNoCacah(this, "login", params, new OkhttpUtilResult() {
                @Override
                public void onSuccess(int type, String data) {
                    startActivity(new Intent(LoginActivity.this, MainTabActivity.class));
                    finish();
                }

                @Override
                public void onError(int code, String msg) {
                    RxToast.normal(msg);
                }
            });
        }
    }

    private boolean isAllRight() {
        if (TextUtil.isEmpty(phone)) {
            RxToast.normal("电话号码不能为空");
            return false;
        }
        if (TextUtil.isEmpty(pass)) {
            RxToast.normal("密码不能为空");
            return false;
        }
        return true;
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
            RequestParams requestParams1 = new RequestParams();
            requestParams1.put("weixin[platform]", "android");
            requestParams1.put("weixin[unionid]", info.unionid);
            requestParams1.put("weixin[imageurl]", info.headimgurl);
            requestParams1.put("weixin[username]", info.nickname);
            requestParams1.put("weixin[city]", info.city + "");
            requestParams1.put("weixin[country]", info.country + "");
            requestParams1.put("weixin[sex]", info.sex + "");
            requestParams1.put("weixin[province]", info.province + "");
            requestParams1.put("weixin[source]", AppContext.getInstance().getChannel());

            EnWebUtil.getInstance().post(this, new String[]{"OwnAccount", "loginAppByWeixin"}, requestParams1, new EnWebUtil.AesListener2() {
                @Override
                public void onSuccess(String errorCode, String errorMsg, String data) {
                    if ("0".equals(errorCode)) {
                        try {
                            User_For_pe bean = JsonUtils.getBean(new JSONObject(data), User_For_pe.class);
                            TLog.error("用户数据" + data);
                            onLoginSuccess(bean);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    }
                    dismissProgressDialog();
                }

                @Override
                public void onFail(String result) {
                    Toast.makeText(LoginActivity.this, "微信登陆失败", Toast.LENGTH_SHORT).show();
                    dismissProgressDialog();
                }
            });
        }
    }

    private void onLoginSuccess(User_For_pe data) {
        AppContext.getInstance().setPEUser(data);
        AppContext.getInstance().setPersonageOnLine(true);
        AppContext.getInstance().setFirst();
        dismissProgressDialog();
        Intent intent = new Intent();
        intent.putExtra("loginstate", 1);
        setResult(RESULT_OK, intent);
        if (AppContext.maintabeactivity != null) {
            AppContext.maintabeactivity.setHeadImageAndMenu(data);
        }
        finish();
    }
}
