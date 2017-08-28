package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.percent.PercentRelativeLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.vondear.rxtools.RxRegUtils;
import com.vondear.rxtools.view.RxToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;


public class RegisterActivity extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.et_phone)
    EditText etPhone;
    @InjectView(R.id.ev_yz)
    EditText evYz;
    @InjectView(R.id.tv_yz)
    TextView tvYz;
    @InjectView(R.id.et_yq)
    EditText etYq;
    @InjectView(R.id.tv_register)
    TextView tvRegister;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.et_pass)
    EditText etPass;
    @InjectView(R.id.iv_show)
    ImageView ivShow;
    private String yzGet="3214";
    private String phone;
    private String yz;
    private String password;
    private String yq;
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
        setTitle(R.id.kz_tiltle, "注册");
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==11){
                    if(!RxRegUtils.isMobile(s.toString())){
                        RxToast.normal("手机号码格式不正确");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @OnClick({R.id.tv_yz, R.id.tv_register})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_yz:
                timer.start();
                break;
            case R.id.tv_register:
                if(isAllRight()){
                    Map<String,String>params=new HashMap<>();
                    params.put("phone",phone);
                    params.put("code",yz);
                    params.put("pwd",password);
                    params.put("invite_code",yq);
                    OkhttpUtilManager.postNoCacah(RegisterActivity.this, "register", params, new OkhttpUtilResult() {
                        @Override
                        public void onSuccess(int type, String data) {

                        }

                        @Override
                        public void onError(int code, String msg) {

                        }
                    });
                }
               // intent = new Intent(RegisterActivity.this, BindWXAcitivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
    private boolean isAllRight(){
        phone=etPhone.getText().toString();
        yz=evYz.getText().toString();
        password=etPass.getText().toString();
        yq=etYq.getText().toString();
        if(!RxRegUtils.isMobile(phone)){
            RxToast.normal("手机号码格式不正确");
            return false;
        }
        if(yz!=yzGet){
            RxToast.normal("验证码不正确");
            return false;
        }
        if(password.length()<6 || password.length()>16){
            RxToast.normal("密码长度不正确");
            return false;
        }
        if(yq==null || yq.length()==0){
            RxToast.normal("邀请码不能为空");
            return false;
        }

        return  true;
    }
}
