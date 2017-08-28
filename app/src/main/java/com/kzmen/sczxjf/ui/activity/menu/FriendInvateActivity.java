package com.kzmen.sczxjf.ui.activity.menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;

/**
 * 邀请好友
 */
public class FriendInvateActivity extends SuperActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "注册卡掌门");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_friend_invate);
    }
}
