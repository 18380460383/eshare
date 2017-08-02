package com.cardholder.adwlf.ui.activity.personal;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cardholder.adwlf.R;
import com.cardholder.adwlf.ui.activity.basic.SuperActivity;
import com.cardholder.adwlf.ui.fragment.personal.AttEnterpriseFragment;
import com.cardholder.adwlf.ui.fragment.personal.AttMediaFragment;

import butterknife.InjectView;

/**
 * Describe:认证详情界面
 * Created by FuPei on 2016/2/18.
 */
public class AttestationDetailActivity extends SuperActivity {

    /**
     * 类型种类
     */
    public final static String EXTRA_TYPE = "type";

    /**
     * 类型的id
     */
    public final static String EXTRA_ID = "id";

    /**
     * 显示界面的类型
     */
    public enum Type{
        media, enterprise
    }

    @InjectView(R.id.title_back)
    public ImageView iv_back;
    @InjectView(R.id.title_name)
    public TextView tv_name;
    @InjectView(R.id.tv_right)
    public TextView tv_right;

    /**
     * 数据类别
     */
    private Type type;

    /**
     * 类型id
     */
    private String id;


    @Override
    public void onCreateDataForView() {
        initData();
        initView();
        initListener();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_fragment);
    }

    private void initData() {
        id = getIntent().getStringExtra(EXTRA_ID);
    }

    private void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_right.getText().toString().equals("提交") && tv_right.getVisibility() == View.VISIBLE) {
                    new AlertDialog.Builder(AttestationDetailActivity.this).setTitle("系统提示")//设置对话框标题
                            .setMessage("还没有提交修改的信息呢，确认退出?")//设置显示的内容
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    finish();
                                }

                            }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮

                        @Override
                        public void onClick(DialogInterface dialog, int which) {//响应事件
                            dialog.dismiss();
                        }
                    }).show();
                } else {
                    finish();
                }
            }
        });
    }

    private void initView() {

        tv_right.setVisibility(View.GONE);
        type = (Type) getIntent().getSerializableExtra(EXTRA_TYPE);
        if(type == Type.media) {
            AttMediaFragment fragment = new AttMediaFragment();
            fragment.setRoleId(id);
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, fragment).commit();
            tv_name.setText("认证媒体人");
        } else {
            AttEnterpriseFragment fragment = new AttEnterpriseFragment();
            fragment.setRoleId(id);
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, fragment).commit();
            tv_name.setText("认证企业用户");
        }
    }

    /**
     * 设置标题栏右边点击事件
     * @param listener
     */
    public void setRightOnclick(View.OnClickListener listener) {
        tv_right.setOnClickListener(listener);
    }

    /**
     * 设置是否显示右边图标
     * @param show
     * @param text
     */
    public void setRightShow(boolean show, String text) {
        if(show) {
            tv_right.setText(text);
            tv_right.setVisibility(View.VISIBLE);
        } else {
            tv_right.setVisibility(View.GONE);
        }
    }
}
