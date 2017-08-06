package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.EditText;
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
    @InjectView(R.id.tv_registe)
    TextView tvRegiste;
    @InjectView(R.id.et_phone)
    EditText etPhone;
    @InjectView(R.id.et_yz)
    EditText etYz;
    @InjectView(R.id.tv_yz)
    TextView tvYz;
    @InjectView(R.id.tv_login)
    TextView tvLogin;
    private int timeCount=60*1000;
    private CountDownTimer timer = new CountDownTimer(timeCount, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tvYz.setText(""+(millisUntilFinished / 1000)+"");
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

    @OnClick({R.id.tv_yz, R.id.tv_login, R.id.tv_registe})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_registe:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                break;
            case R.id.tv_yz:
                timer.start();
                break;
            case R.id.tv_login:
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
