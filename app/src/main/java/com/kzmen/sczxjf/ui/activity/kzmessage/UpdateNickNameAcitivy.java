package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;

public class UpdateNickNameAcitivy extends SuperActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle,"昵称修改");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_update_nick_name_acitivy);
    }
}
