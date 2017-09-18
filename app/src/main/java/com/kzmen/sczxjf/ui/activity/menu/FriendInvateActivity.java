package com.kzmen.sczxjf.ui.activity.menu;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.UserMessageBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.vondear.rxtools.RxLogUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 邀请好友
 */
public class FriendInvateActivity extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.tv_yqm)
    TextView tvYqm;
    @InjectView(R.id.iv_qr)
    ImageView ivQr;
    @InjectView(R.id.ll_wx)
    LinearLayout llWx;
    @InjectView(R.id.ll_friend)
    LinearLayout llFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "注册卡掌门");
        initView();
        initData();
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("data[make]", "1");
        OkhttpUtilManager.postNoCacah(this, "User/getUserInviteCode", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                RxLogUtils.e("tst", data);
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxLogUtils.e("tst", msg);
            }
        });
    }

    private void initView() {
        UserMessageBean bean = AppContext.getInstance().getUserMessageBean();
        if (bean != null) {
            tvYqm.setText(bean.getInvite_code());
        }
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_friend_invate);
    }

    @OnClick({R.id.ll_wx, R.id.ll_friend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_wx:
                break;
            case R.id.ll_friend:
                break;
        }
    }
}
