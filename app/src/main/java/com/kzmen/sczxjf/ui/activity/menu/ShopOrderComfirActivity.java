package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.ShopOrderAddressBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.vondear.rxtools.view.RxToast;

import java.util.HashMap;
import java.util.Map;

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
                startActivityForResult(new Intent(ShopOrderComfirActivity.this, ShopAddAddressActivity.class), 2000);
                break;
            case R.id.tv_confire:
                commitOrder();
                break;
        }
    }
    private void commitOrder(){
        Map<String,String> params=new HashMap<>();
        params.put("data[gid]",  "");
        params.put("data[num]",   "10");
        params.put("data[aid]",   "10");
        OkhttpUtilManager.postNoCacah(this, "Goods/UserAddOrder", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {

            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
            }
        });
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
                if(msg!=null){
                    llAddress.setVisibility(View.VISIBLE);
                    llNoaddress.setVisibility(View.GONE);
                    tvUserName.setText(msg.getUserName());
                    tvAddress.setText(msg.getUserAddress());
                    tvPhone.setText(msg.getUserPhone());
                }else{
                    llAddress.setVisibility(View.GONE);
                    llNoaddress.setVisibility(View.VISIBLE);
                }
                break;
            case 2000:
                if(msg!=null){
                    llAddress.setVisibility(View.VISIBLE);
                    llNoaddress.setVisibility(View.GONE);
                    tvUserName.setText(msg.getUserName());
                    tvAddress.setText(msg.getUserAddress());
                    tvPhone.setText(msg.getUserPhone());
                }else{
                    llAddress.setVisibility(View.GONE);
                    llNoaddress.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
