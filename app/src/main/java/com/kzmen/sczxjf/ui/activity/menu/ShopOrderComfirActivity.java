package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.ShopOrderAddressBean;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.EToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;

public class ShopOrderComfirActivity extends SuperActivity {

    @InjectView(R.id.ll_noaddress)
    LinearLayout llNoaddress;
    @InjectView(R.id.tv_user_name)
    TextView tvUserName;
    @InjectView(R.id.tv_phone)
    TextView tvPhone;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.ll_address)
    LinearLayout llAddress;
    @InjectView(R.id.iv_img)
    ImageView ivImg;
    @InjectView(R.id.tv_shop_name)
    TextView tvShopName;
    @InjectView(R.id.tv_count)
    TextView tvCount;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.tv_ydprice)
    TextView tvYdprice;
    @InjectView(R.id.tv_confire)
    TextView tvConfire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "订单确认");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_shop_order_comfir);
    }

    @OnClick({R.id.ll_noaddress, R.id.ll_address, R.id.tv_confire})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_noaddress:
                startActivityForResult(new Intent(ShopOrderComfirActivity.this, ShopAddressEditActivity.class), 1000);
                break;
            case R.id.ll_address:
                break;
            case R.id.tv_confire:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        ShopOrderAddressBean msg = (ShopOrderAddressBean) data.getSerializableExtra("data");
        // 根据上面发送过去的请求吗来区别
        switch (requestCode) {
            case 1000:
                EToastUtil.show(ShopOrderComfirActivity.this, "niccc" + msg.toString());
                break;
        }
    }
}
