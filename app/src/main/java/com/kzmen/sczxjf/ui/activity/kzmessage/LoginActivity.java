package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;

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

    @OnClick({R.id.iv_show, R.id.tv_login, R.id.ll_login_weix,R.id.tv_forgetpass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_show:
                break;
            case R.id.tv_login:
                startActivity(new Intent(LoginActivity.this,MainTabActivity.class));
                finish();
                break;
            case R.id.ll_login_weix:
                break;
            case R.id.tv_forgetpass:
                startActivity(new Intent(LoginActivity.this,ForgetPassActivity.class));
                break;
        }
    }
}
