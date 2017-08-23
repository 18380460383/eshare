package com.kzmen.sczxjf.ui.activity.menu;

import android.os.Bundle;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;

/**
 * 我的积分
 * 目前布局写好
 */
public class MyIntegralActivity extends SuperActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle,"我的积分");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_my_integral);
    }
}
