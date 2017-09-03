package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.view.MyListView;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/11.
 */

public class KnowageAskActivity extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.iv_share)
    ImageView ivShare;
    @InjectView(R.id.tv_ask)
    TextView tvAsk;
    @InjectView(R.id.lv_aks)
    MyListView lvAks;
    @InjectView(R.id.ll_ask)
    LinearLayout ll_ask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "知识问答");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_knowage_ask1);
    }


    @OnClick(R.id.ll_ask)
    public void onViewClicked(View view) {
        Intent intent=null;
        switch (view.getId()){
            case R.id.ll_ask:
                intent=new Intent(KnowageAskActivity.this,KnowageAskPreActivity.class);
                startActivity(intent);
                break;
        }
    }
}
