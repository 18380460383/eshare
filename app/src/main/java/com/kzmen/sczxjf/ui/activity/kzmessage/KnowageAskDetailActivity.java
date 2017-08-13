package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.view.CircleImageView;
import com.kzmen.sczxjf.view.MyListView;

import butterknife.InjectView;


/**
 * 问答详情
 */
public class KnowageAskDetailActivity extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.iv_ask_user_image)
    CircleImageView ivAskUserImage;
    @InjectView(R.id.tv_ask_content)
    TextView tvAskContent;
    @InjectView(R.id.tv_ask_timeleft)
    TextView tvAskTimeleft;
    @InjectView(R.id.tv_ask_sign)
    TextView tvAskSign;
    @InjectView(R.id.lv_aks)
    MyListView lvAks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "问答详情");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_ask_detail);
    }

}
