package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class IndexActivity extends AppCompatActivity {

    @InjectView(R.id.tv_login)
    TextView tvLogin;
    @InjectView(R.id.tv_register)
    TextView tvRegister;
    @InjectView(R.id.ll_login_weix)
    LinearLayout llLoginWeix;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                intent = new Intent(IndexActivity.this, MainTabActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            finish();
        }
    }
}
