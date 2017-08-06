package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.EToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class BindWXAcitivity extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.tv_bind_wx)
    TextView tvBindWx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "绑定微信");
        ButterKnife.inject(this);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_bind_wxacitivity);
    }

    @OnClick(R.id.tv_bind_wx)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bind_wx:
                EToastUtil.show(BindWXAcitivity.this, "微信登录");
                break;
        }
    }
}
