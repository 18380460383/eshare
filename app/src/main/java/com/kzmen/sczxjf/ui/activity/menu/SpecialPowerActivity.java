package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.view.MyListView;

import butterknife.InjectView;
import butterknife.OnClick;

public class SpecialPowerActivity extends SuperActivity {

    @InjectView(R.id.iv_head)
    ImageView ivHead;
    @InjectView(R.id.iv_level_sign)
    ImageView ivLevelSign;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_level)
    TextView tvLevel;
    @InjectView(R.id.tv_date)
    TextView tvDate;
    @InjectView(R.id.tv_level_)
    TextView tvLevel_;
    @InjectView(R.id.ll_have_level)
    LinearLayout llHaveLevel;
    @InjectView(R.id.lv_power)
    MyListView lvPower;
    @InjectView(R.id.tv_charge_vip)
    TextView tvChargeVip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "我的特权");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_special_power);
    }

    @OnClick(R.id.tv_charge_vip)
    public void onViewClicked() {
        Intent intent=new Intent(SpecialPowerActivity.this,PayTypeAcitivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("price","100");
        bundle.putString("title","会员充值");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
