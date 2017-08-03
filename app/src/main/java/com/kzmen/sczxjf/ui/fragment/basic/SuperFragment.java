package com.kzmen.sczxjf.ui.fragment.basic;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;

/**
 * Created by 杨操 on 2016/1/14.
 */
public abstract class SuperFragment extends Fragment  {
    private  Activity activity;
    private View inflate;

    public View getInflate() {
        return inflate;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;
    }


    public Context getContext() {
        FragmentActivity activity = getActivity();
        if(activity==null){
            if(activity==null){
                this.activity= AppContext.getInstance().getOneActivity();
            }
            return this.activity;
        }else{
            return  activity;
        }


    }

    public void setActivity(Activity context) {
        this.activity = context;
    }
    /**
     * 显示进度条
     * @param text 对话框提示字段
     */
    public void showProgressDialog(String text) {
        ((SuperActivity)activity).showProgressDialog(text);

    }

    /**
     * 关闭进度条
     */
    public void dismissProgressDialog() {
        ((SuperActivity)activity).dismissProgressDialog();
    }
    protected  View setContentView(LayoutInflater inflater,ViewGroup container,int id){
        inflate = inflater.inflate(id, null);
        return inflate;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
