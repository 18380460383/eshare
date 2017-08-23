package com.kzmen.sczxjf.ui.activity.menu;

import android.os.Bundle;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;

public class FriendOfmineAcitivty extends SuperActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "我的好友");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_friend_ofmine_acitivty);
    }
}
