package com.jiebian.adwlf.ui.activity.personal;


import android.content.SharedPreferences;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiebian.adwlf.AppContext;
import com.jiebian.adwlf.R;
import com.jiebian.adwlf.bean.returned.SetInfoReturn;
import com.jiebian.adwlf.ui.activity.basic.SuperActivity;
import com.jiebian.adwlf.util.EToastUtil;
import com.jiebian.adwlf.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;

/**
 * 说明：账号信息界面
 * note：
 * Created by FuPei
 * on 2015/11/30 at 13:26
 */
public class AccountActivity extends SuperActivity {

    public static final String KEY_ACCOUNT = "account";
    @InjectView(R.id.activity_account_tv_id)
    public TextView tv_id;
    @InjectView(R.id.activity_account_tv_nickname)
    public TextView tv_name;
    @InjectView(R.id.activity_account_tv_phone)
    public TextView tv_phone;
    @InjectView(R.id.activity_account_tv_withdraw)
    public TextView tv_withdraw;
    @InjectView(R.id.activity_account_tv_earn)
    public TextView tv_earn;
    @InjectView(R.id.activity_account_tv_count)
    public TextView tv_count;
    @InjectView(R.id.activity_account_tv_rate)
    public TextView tv_rate;
    @InjectView(R.id.employee_mode)
    LinearLayout employeeMode;
    private String info_set;
    private int num=0;
    @Override
    public void onCreateDataForView() {
        initData();
        setTitle(R.id.account_title,"账号信息");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_info_account);
    }
    void initData() {
        info_set = getIntent().getExtras().getString(KEY_ACCOUNT);
        try {
            initAccountInfo();
        } catch (JSONException e) {
            EToastUtil.show(AccountActivity.this, "网络很慢还请重新进入界面");
        }
    }

    /**
     * 设置账号的信息
     */
    public void initAccountInfo() throws JSONException {
      /*  employeeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                if(num==10){
                    Toast.makeText(AccountActivity.this,"开启员工模式",Toast.LENGTH_SHORT).show();
                    AppContext instance = AppContext.getInstance();
                    SharedPreferences.Editor edit = instance.sp.edit();
                    edit.putBoolean("employeemode",true);
                    edit.commit();
                }
            }
        });*/
        if(info_set.length() > 0) {
            JSONObject json = new JSONObject(info_set);
            json = json.getJSONObject("data");
            SetInfoReturn info = SetInfoReturn.parseJson(json);
            tv_id.setText(info.uid);
            //设置昵称显示，可能没有信息
            if (StringUtils.isEmpty(info.username)) {
                tv_name.setText("暂无");
            } else {
                tv_name.setText(info.username);
            }
            //设置手机号码，可能用户没有绑定
            if (StringUtils.isEmpty(info.on_phone)) {
                tv_phone.setText("暂未绑定手机号");
            } else {
                tv_phone.setText(info.on_phone);
            }
            tv_withdraw.setText(info.withdraw_ok / 100 + "");
            tv_earn.setText(info.earn_money / 100 + "");
            tv_count.setText(info.relay);
            tv_rate.setText(info.relay_perc);
        } else {
            EToastUtil.show(AccountActivity.this, "获取账号信息发生错误");
        }
    }
}
