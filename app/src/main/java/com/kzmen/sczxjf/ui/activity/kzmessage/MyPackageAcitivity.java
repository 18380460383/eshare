package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzmen.sczxjf.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 我的钱包
 */
public class MyPackageAcitivity extends AppCompatActivity {

    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.iv_setting)
    ImageView ivSetting;
    @InjectView(R.id.tv_money)
    TextView tvMoney;
    @InjectView(R.id.tv_get_money)
    TextView tvGetMoney;
    @InjectView(R.id.tv_add_money)
    TextView tvAddMoney;
    @InjectView(R.id.tv_money_edu)
    TextView tvMoneyEdu;
    @InjectView(R.id.iv_know)
    ImageView ivKnow;
    @InjectView(R.id.tv_rule)
    TextView tvRule;
    @InjectView(R.id.lv_integral)
    ExpandableListView lvIntegral;
    @InjectView(R.id.tv_more_list)
    TextView tvMoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_package_acitivity);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.tv_get_money)
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_get_money:
                intent = new Intent(MyPackageAcitivity.this, GetBalanceActivity.class);
                startActivity(intent);
                break;
        }
    }
}
