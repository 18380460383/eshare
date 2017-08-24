package com.kzmen.sczxjf.ui.activity.menu;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 地址编辑
 */

public class ShopAddressEditActivity extends SuperActivity {

    @InjectView(R.id.tv_user_name)
    TextView tvUserName;
    @InjectView(R.id.ll_edit_user)
    LinearLayout llEditUser;
    @InjectView(R.id.tv_user_phone)
    TextView tvUserPhone;
    @InjectView(R.id.ll_edit_phone)
    LinearLayout llEditPhone;
    @InjectView(R.id.tv_user_address)
    TextView tvUserAddress;
    @InjectView(R.id.ll_edit_address)
    LinearLayout llEditAddress;
    @InjectView(R.id.tv_user_street)
    TextView tvUserStreet;
    @InjectView(R.id.ll_edit_street)
    LinearLayout llEditStreet;
    @InjectView(R.id.tv_user_ybian)
    TextView tvUserYbian;
    @InjectView(R.id.ll_edit_ybian)
    LinearLayout llEditYbian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "地址编辑");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_shop_address_edit);
    }

    @OnClick({R.id.ll_edit_user, R.id.ll_edit_phone, R.id.ll_edit_address, R.id.ll_edit_street, R.id.ll_edit_ybian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_edit_user:
                break;
            case R.id.ll_edit_phone:
                break;
            case R.id.ll_edit_address:
                break;
            case R.id.ll_edit_street:
                break;
            case R.id.ll_edit_ybian:
                break;
        }
    }
}
