package com.kzmen.sczxjf.ui.activity.menu;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.UserMessageBean;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;

import butterknife.InjectView;

/**
 * 我的积分
 * 目前布局写好
 */
public class MyIntegralActivity extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.tv_jif)
    TextView tvJif;
    @InjectView(R.id.lv_integral)
    ExpandableListView lvIntegral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "我的积分");
        initView();
    }
    private void initView() {
        UserMessageBean userMessageBean = AppContext.getInstance().getUserMessageBean();
        setUserInfo(userMessageBean);
    }

    private void setUserInfo(UserMessageBean userMessageBean) {
        if (userMessageBean != null) {
            tvJif.setText(userMessageBean.getScore());
        }
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_my_integral);
    }
}
