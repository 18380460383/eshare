package com.jiebian.adwlf.ui.activity.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.jiebian.adwlf.R;
import com.jiebian.adwlf.adapter.UserSpoorAdapter;
import com.jiebian.adwlf.bean.Spoor;
import com.jiebian.adwlf.ui.activity.basic.ListViewActivity;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 创建者：Administrator
 * 时间：2016/4/19
 * 功能描述：
 */
public class UserSpoor extends ListViewActivity {

    @InjectView(R.id.user_spoor_radio_button1)
    RadioButton userSpoorRadioButton1;
    @InjectView(R.id.user_spoor_radio_button2)
    RadioButton userSpoorRadioButton2;
    private UserSpoorAdapter userSpoorAdapter;
    private ArrayList<Spoor> spoors;

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_user_spoor);
        setTitle(R.id.user_spoor_title, getResources().getString(R.string.userspoor_title_cstr));
    }

    @Override
    protected void onResume() {
        super.onResume();
        userSpoorRadioButton1.setChecked(true);
    }
    @OnClick({R.id.user_spoor_radio_button1,R.id.user_spoor_radio_button2})
    public void Listiener(View view){
        ((RadioButton)view).setChecked(true);
    }

    @Override
    public void onCreateDataForView() {
        spoors = new ArrayList<>();
        spoors.add(new Spoor());
        spoors.add(new Spoor());
        spoors.add(new Spoor());
        spoors.add(new Spoor());
        spoors.add(new Spoor());
        spoors.add(new Spoor());
        spoors.add(new Spoor());
        spoors.add(new Spoor());
        spoors.add(new Spoor());
        spoors.add(new Spoor());
        spoors.add(new Spoor());
        spoors.add(new Spoor());
        spoors.add(new Spoor());
        spoors.add(new Spoor());
        userSpoorAdapter = new UserSpoorAdapter(spoors, this);
        setmPullRefreshListView((PullToRefreshListView) findViewById(R.id.listview), userSpoorAdapter);
        setImageLoader(userSpoorAdapter.getImageLoader());
        setPullToRefreshListView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
