package com.cardholder.adwlf.ui.activity.basic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cardholder.adwlf.AppContext;
import com.cardholder.adwlf.R;
import com.cardholder.adwlf.control.CustomProgressDialog;
import com.cardholder.adwlf.net.NetworkDownload;
import com.cardholder.adwlf.ui.activity.personal.LoginActivity;

import butterknife.ButterKnife;

/**
 * 创建者：杨操
 * 时间：2016/4/6
 * 功能描述：超级Activity,本项目所有的Activity的父类
 */
public abstract class SuperActivity extends FragmentActivity {
    /**标题栏*/
    public View title;
    /**标题控件*/
    public TextView titleNameView;
    /**网络请求对话框*/
    private CustomProgressDialog progressDialog;
    /**退出应用广播Action*/
    public static final String EXIT="com.brocast.exit";
    /**退出应用广播对象*/
    private BroadcastReceiver exitReceiver;
    /**判断退出应用广播是否注册*/
    private boolean isStartExitReceiver =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断此界面是不是对外直接开放的
                //如果不是
                if(!isShareActivity()) {
                    if (!AppContext.getInstance().getPersonageOnLine()) {
                        Intent intent = new Intent(this, LoginActivity.class);
                        this.startActivity(intent);
                        this.finish();
                    }else{
                        initActivity();
                    }
                }
                //如果是
                else{
                    initActivity();
                }
        setInnerAct();
    }

    public void setInnerAct(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
                /*window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);*/
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        /*    int statusBarHeight = MyUtil.getStatusBarHeight(BaseActivity.this);
            view.setPadding(0, statusBarHeight, 0, 0);*/
        }
    }
    private void initActivity() {
        setThisContentView();
        if (isCanExit()) {
            setExitBroadcastReceiver();
        }
        progressDialog = new CustomProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        onCreateDataForView();
    }

    public abstract void onCreateDataForView();

    public abstract void setThisContentView();
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
        AppContext.getInstance().setOneActivity(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
       // MobclickAgent.onPause(this);
        AppContext.getInstance().setOldinstance(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkDownload.stopRequest(this);
        if(isStartExitReceiver) {
            unregisterReceiver(exitReceiver);
            isStartExitReceiver=false;
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
    }
    /**
     * 设置界面的标题栏
     * @param id 标题栏id
     * @param name 标题栏名
     */
    public void setTitle(int id,String name){
        if(title==null&&id!=0){
            title = findViewById(id);
            PercentRelativeLayout viewById = (PercentRelativeLayout) title.findViewById(R.id.back);
            viewById.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            titleNameView = (TextView) findViewById(R.id.title_name);
            if(!TextUtils.isEmpty(name)){
                titleNameView.setText(name);
            }

        }
    }
    /**
     * 设置界面的标题栏
     * @param id 标题栏id
     */
    public void setTitle(int id){
        setTitle(id,"");
    }

    /**
     * 杨操
     * 显示进度条
     * @param text 对话框提示字段
     */
    public void showProgressDialog(String text) {
        Log.i("tag", "对话框显示");
        if(text!=null&&!TextUtils.isEmpty(text.trim())) {
            progressDialog.setText(text);
        }
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }

    }

    /**
     * 杨操
     * 关闭进度条
     */
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 杨操
     * 设置退出广播
     */
    private void setExitBroadcastReceiver(){
        isStartExitReceiver=true;
        exitReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SuperActivity.this.finish();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(EXIT);
        registerReceiver(exitReceiver, filter);
    }

    /**
     * 杨操
     * 判断是否支持群发广播退出界面的功能
     * 默认支持
     */
    public boolean isCanExit() {
        return true;
    }
    /**
     * 用户没登录是否可以使用此页面（用于浏览器启动界面时做判断）
     * 默认不能在没登录时使用
     * */
    protected boolean isShareActivity(){
        return false;
    }

    /**
     * 杨操
     * 获取values文件夹的string.xml文件的配置信息
     * @param stringId 文字ID
     * @return 字符串
     */
    protected String getStringForeValues(int stringId){
        return getResources().getString(stringId);
    }
    /**
    *设置当前界面时企业界面
     *
     *  默认不能在没登录时使用
    */
    protected boolean isEnterpriseActivity(){
        return false;
    }

}
