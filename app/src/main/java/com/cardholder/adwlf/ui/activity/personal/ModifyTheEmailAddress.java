package com.cardholder.adwlf.ui.activity.personal;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.cardholder.adwlf.R;
import com.cardholder.adwlf.ui.activity.basic.SuperActivity;
import com.cardholder.adwlf.ui.fragment.personal.BindEmail;

/**
 * 创建者：Administrator
 * 时间：2016/7/22
 * 功能描述：修改邮箱
 */
public class ModifyTheEmailAddress extends SuperActivity {
    @Override
    public void onCreateDataForView() {
        setTitle(R.id.modify_the_email_address_title,"邮箱修改");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        BindEmail fragment = new BindEmail();
        Bundle args = new Bundle();
        args.putInt(BindEmail.TITLETYPE,BindEmail.TYPE_NUM_MODUIFY);
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.modify_the_email_addressbody, fragment);
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_modify_the_email_address);
    }
}
