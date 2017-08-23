package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.GetBalanceActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 我的钱包
 */
public class MyPackageAcitivity extends SuperActivity {


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

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle,"我的钱包");
    }

    @Override
    public void setThisContentView() {
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
