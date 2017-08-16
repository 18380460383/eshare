package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;

public class TestResultActivity extends SuperActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "测评结果");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_test_result);
    }
}
