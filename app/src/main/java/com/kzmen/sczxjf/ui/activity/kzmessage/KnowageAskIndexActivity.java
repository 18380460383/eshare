package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.view.MyListView;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 知识问答
 */
public class KnowageAskIndexActivity extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.tv_ask_xy)
    TextView tvAskXy;
    @InjectView(R.id.tv_ask_yx)
    TextView tvAskYx;
    @InjectView(R.id.lv_aks)
    MyListView lvAks;

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
        setContentView(R.layout.activity_knowage_ask);
    }

    @OnClick(R.id.tv_ask_xy)
    public void onViewClicked(View view) {
        Intent intent=null;
        switch (view.getId()){
            case R.id.tv_ask_xy:
                intent=new Intent(KnowageAskIndexActivity.this,KnowageAskActivity.class);
                startActivity(intent);
                break;
        }
    }
}
