package com.kzmen.sczxjf.ui.activity.menu;

import android.os.Bundle;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;

public class ShopOrderComfirActivity extends SuperActivity {

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
}
