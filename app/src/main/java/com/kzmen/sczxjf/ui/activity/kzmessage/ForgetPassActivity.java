package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.TextUtil;
import com.vondear.rxtools.RxRegUtils;
import com.vondear.rxtools.RxUtils;
import com.vondear.rxtools.view.RxToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

public class ForgetPassActivity extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.et_phone)
    EditText etPhone;
    @InjectView(R.id.ev_yz)
    EditText evYz;
    @InjectView(R.id.tv_yz)
    TextView tvYz;
    @InjectView(R.id.tv_next)
    TextView tvNext;
    private String phone;
    private String yzen;
    private String yzenGet;
    private int timeCount = 60 * 1000;
    private CountDownTimer timer = new CountDownTimer(timeCount, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tvYz.setText("(" + (millisUntilFinished / 1000) + ")");
            tvYz.setEnabled(false);
        }

        @Override
        public void onFinish() {
            tvYz.setEnabled(true);
            tvYz.setText("获取验证码");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "找回密码");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_forget_pass);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @OnClick({R.id.tv_yz, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_yz:
                yzen=evYz.getText().toString();
                if(isPhoneRigth()){
                    timer.start();
                    getYz();
                }
                break;
            case R.id.tv_next:
                if (isAllRight()) {
                    startActivity(new Intent(ForgetPassActivity.this, ResetPassActivity.class));
                    finish();
                }
                break;
        }
    }
    private boolean isPhoneRigth(){
        phone=etPhone.getText().toString();
        if(TextUtil.isEmpty(phone)){
            return false;
        }
        return RxRegUtils.isTel(phone);
    }
    public boolean isAllRight() {
        if (TextUtil.isEmpty(phone)) {
            RxToast.normal("电话号码不能为空");
            return false;
        }
        if (TextUtil.isEmpty(yzen)) {
            RxToast.normal("验证码不能为空");
            return false;
        } else {
            if (!yzen.equals(yzenGet)) {
                RxToast.normal("验证码不正确");
                return false;
            }
        }
        return true;
    }

    private void getYz() {
        if (TextUtil.isEmpty(phone)) {
            RxToast.normal("电话号码不能为空");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("type", "2");
        OkhttpUtilManager.postNoCacah(this, "get_phone_code", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                if (timer != null) {
                    timer.cancel();
                }
                tvYz.setText("获取验证码");
                yzenGet=data;
            }

            @Override
            public void onError(int code, String msg) {
                if (timer != null) {
                    timer.cancel();
                }
                yzenGet="-111111";
                tvYz.setText("获取验证码");
            }
        });
    }


}
