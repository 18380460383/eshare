package com.kzmen.sczxjf.ui.activity.menu;

import android.os.Bundle;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.ListViewActivity;

public class ShopAddAddressActivity extends ListViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "选择地址");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_shop_add_address);
    }
}
