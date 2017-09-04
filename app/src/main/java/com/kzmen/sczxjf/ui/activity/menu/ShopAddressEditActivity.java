package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.ShopOrderAddressBean;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.EToastUtil;

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
    public static final int REQ_NAME = 1001;
    public static final int REQ_PHONE = 1002;
    public static final int REQ_ADDRESS = 1003;
    public static final int REQ_STREET = 1004;
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    private ShopOrderAddressBean bean = new ShopOrderAddressBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        //setTitle(R.id.kz_tiltle, "编辑地址");
        titleName.setText("编辑地址");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_shop_address_edit);
    }

    @OnClick({R.id.ll_edit_user, R.id.ll_edit_phone, R.id.ll_edit_address, R.id.ll_edit_street, R.id.ll_edit_ybian,R.id.back})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_edit_user:
                intent = new Intent(ShopAddressEditActivity.this, ShopNameEditActivity.class);
                startActivityForResult(intent, REQ_NAME);
                break;
            case R.id.ll_edit_phone:
                intent = new Intent(ShopAddressEditActivity.this, ShopPhoneEditAcitivity.class);
                startActivityForResult(intent, REQ_PHONE);
                break;
            case R.id.ll_edit_address:
                intent = new Intent(ShopAddressEditActivity.this, ShopAreaActivity.class);
                startActivityForResult(intent, REQ_ADDRESS);
                break;
            case R.id.ll_edit_street:
                intent = new Intent(ShopAddressEditActivity.this, ShopCountryDetailActivity.class);
                startActivityForResult(intent, REQ_STREET);
                break;
            case R.id.ll_edit_ybian:
                break;
            case R.id.back:
                bean.setUserName(tvUserName.getText().toString());
                bean.setUserPhone(tvUserPhone.getText().toString());
                bean.setUserAddress(tvUserAddress.getText().toString());
                bean.setUserCountry(tvUserStreet.getText().toString());
                bean.setUserYB(tvUserYbian.getText().toString());
                Intent mIntent = new Intent();
                mIntent.putExtra("data", bean);
                // 设置结果，并进行传送
                this.setResult(1000, mIntent);
                this.finish();
                break;
        }
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String msg = data.getStringExtra("data");
        // 根据上面发送过去的请求吗来区别
        switch (requestCode) {
            case REQ_NAME:
                tvUserName.setText(msg);
                EToastUtil.show(ShopAddressEditActivity.this, "niccc" + msg);
                break;
            case REQ_PHONE:
                tvUserPhone.setText(msg);
                EToastUtil.show(ShopAddressEditActivity.this, "phone  " + msg);
                break;
            case REQ_ADDRESS:
                tvUserAddress.setText(msg);
                String yb=data.getStringExtra("yb");
                tvUserYbian.setText(yb);
                EToastUtil.show(ShopAddressEditActivity.this, "address  " + msg+"   yb   "+yb);
                break;
            case REQ_STREET:
                tvUserStreet.setText(msg);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        bean.setUserName(tvUserName.getText().toString());
        bean.setUserPhone(tvUserPhone.getText().toString());
        bean.setUserAddress(tvUserAddress.getText().toString());
        bean.setUserCountry(tvUserStreet.getText().toString());
        bean.setUserYB(tvUserYbian.getText().toString());
        Intent mIntent = new Intent();
        mIntent.putExtra("data", bean);
        // 设置结果，并进行传送
        this.setResult(1000, mIntent);
        this.finish();
    }
}
