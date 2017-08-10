package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.view.CircleImageView;
import com.kzmen.sczxjf.view.MyListView;

import butterknife.InjectView;
import butterknife.OnClick;


/**
 * 问答详情
 */
public class AskDetailActivity extends SuperActivity {

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
    @InjectView(R.id.tv_answer)
    TextView tvAnswer;
    @InjectView(R.id.ll_answer)
    LinearLayout llAnswer;

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

    @OnClick(R.id.tv_answer)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_answer:
                break;
        }
    }
}
