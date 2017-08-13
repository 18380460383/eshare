package com.kzmen.sczxjf.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;


import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.AppManager;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.UIManager;
import com.kzmen.sczxjf.bean.Advertisement;
import com.kzmen.sczxjf.bean.user.User_For_pe;
import com.kzmen.sczxjf.net.EnWebUtil;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.MainTabActivity;
import com.kzmen.sczxjf.util.ELocationlistener;
import com.kzmen.sczxjf.util.EshareLoger;
import com.kzmen.sczxjf.utils.AppUtils;
import com.kzmen.sczxjf.utils.JsonUtils;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.control.AdvertisementControl;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wu
 */
public class LogoActivity extends SuperActivity {
    private ELocationlistener listener = new ELocationlistener() {
        @Override
        public void onFinshLocation(EshareLocationInfo info) {
            setLocation(info);
        }
    };
    /**
     * 上传地理位置
     * @param info
     */
    private void setLocation(ELocationlistener.EshareLocationInfo info) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", AppContext.getInstance().getPEUser().getUid());
        map.put("token", AppContext.getInstance().getPEUser().getToken());
        map.put("location", info.address);
        map.put("x", info.x);
        map.put("y", info.y);
        map.put("device_type", "Android");
        map.put("device_version", Build.MODEL);
        map.put("app_version", AppUtils.getAppVersionName(this));
        RequestParams requestParams = AppUtils.getParm(map);
        NetworkDownload.bytePost(AppContext.getInstance(), Constants.URL_SET_LOCATING, requestParams, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                EshareLoger.logI("发送位置信息成功,result = ");
            }

            @Override
            public void onFailure() {
                EshareLoger.logI("发送位置信息失败了: ");
            }
        });
    }


    @Override
    public void onCreateDataForView() {
         AppContext instance = AppContext.getInstance();
        if(TextUtils.isEmpty(instance.getPEUser().getUid())){
           instance.setPersonageOnLine(false);

        }

    }

    @Override
    public void setThisContentView() {
        // 防止第三方跳转时出现双实例
        Activity aty = AppManager.getActivity(MainTabActivity.class);
        if (aty != null && !aty.isFinishing()) {
            finish();
        }
        setContentView(R.layout.activity_logo);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                redirectTo();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 跳转到...
     */
    private void redirectTo() {
        if(AppContext.getInstance().getPersonageOnLine()){
            OnLinelogin();
        }
        AdvertisementControl advertisementControl = AdvertisementControl.getAdvertisementControl();
        Advertisement advertisement = advertisementControl.getAdvertisement();
        long l = System.currentTimeMillis();
        Long aLong = null;
        Long aLong1=null;


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(!TextUtils.isEmpty(advertisement.getStartdate())&&!TextUtils.isEmpty(advertisement.getEnddate())){
             aLong = Long.valueOf(advertisement.getStartdate());
             aLong1 = Long.valueOf(advertisement.getEnddate());
        }
        if (advertisementControl.isHaveAdvertisement()&&aLong!=null&&aLong1!=null&&aLong*1000 <l&& aLong1*1000 >l) {
             System.out.println(sdf.format(new Date(aLong*1000))+"end"+sdf.format(new Date(aLong1*1000)));
                //startActivity(new Intent(this, AdvertActivity.class));
           // finish();
        } else {
            // 判断是否跳过新手引导界面
            if (AppContext.getInstance().isFirst()) {
                // 第一次登录
                UIManager.showFirstGuideActivity(this);
                AppContext.getInstance().setFirst();
                finish();
            }
           else{
                startActivity(new Intent(this, MainTabActivity.class));
                finish();
            }
        }

    }
    private void OnLinelogin() {
        AppContext instance = AppContext.getInstance();
        if (TextUtils.isEmpty(instance.getCpassword())) {
            try {
                loginForWeixin();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            RequestParams params = new RequestParams();
            params.put("on_phone",instance.getPEUser().getOn_phone());
            params.put("on_pwd", instance.getCpassword());
            EnWebUtil.getInstance().post(null, new String[]{"OwnAccount", "loginApp"}, params, new EnWebUtil.AesListener2() {
                @Override
                public void onSuccess(String errorCode, String errorMsg, String data) {
                    if ("0".equals(errorCode)) {
                        try {
                            User_For_pe bean = JsonUtils.getBean(new JSONObject(data), User_For_pe.class);
                            AppContext.getInstance().setPEUser(bean);
                            AppContext.getInstance().setPersonageOnLine(true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFail(String result) {
                }
            });
        }
    }
    @Override
    protected boolean isShareActivity() {
        return true;
    }
    private void loginForWeixin() throws JSONException {

         User_For_pe peUser = AppContext.getInstance().getPEUser();
        if(TextUtils.isEmpty(peUser.getWeixin())){
            return;
        }
        RequestParams requestParams = new RequestParams();
        requestParams.put("platform", "android");
        requestParams.put("weixin", peUser.getWeixin());
        requestParams.put("imageurl", peUser.getImageurl());
        requestParams.put("username", peUser.getUsername());
        requestParams.put("third_city", peUser.getCity());
        requestParams.put("third_country", "");
        requestParams.put("third_sex", "");
        requestParams.put("third_province", peUser.getProvince());
        requestParams.put("source", AppContext.getInstance().getChannel());
        EnWebUtil.getInstance().post(null, new String[]{"OwnAccount", "loginAppByWeixin"}, requestParams, new EnWebUtil.AesListener2() {
            @Override
            public void onSuccess(String errorCode, String errorMsg, String data) {
                if ("0".equals(errorCode)) {
                    try {
                        User_For_pe bean = JsonUtils.getBean(new JSONObject(data), User_For_pe.class);
                        AppContext.getInstance().setPEUser(bean);
                        AppContext.getInstance().setPersonageOnLine(true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFail(String result) {

            }
        });
    }

}
