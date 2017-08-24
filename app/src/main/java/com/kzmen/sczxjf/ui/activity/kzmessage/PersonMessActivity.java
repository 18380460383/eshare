package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.EToastUtil;
import com.kzmen.sczxjf.view.wheelview.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PersonMessActivity extends SuperActivity {


    @InjectView(R.id.tv_user_name)
    TextView tvUserName;
    @InjectView(R.id.tv_day_sign)
    TextView tvDaySign;
    @InjectView(R.id.iv_user_head)
    ImageView ivUserHead;
    @InjectView(R.id.tv_nickname)
    TextView tvNickname;
    @InjectView(R.id.ll_nickname)
    LinearLayout llNickname;
    @InjectView(R.id.tv_realname)
    TextView tvRealname;
    @InjectView(R.id.ll_realname)
    LinearLayout llRealname;
    @InjectView(R.id.tv_sex)
    TextView tvSex;
    @InjectView(R.id.ll_sex)
    LinearLayout llSex;
    @InjectView(R.id.tv_birth)
    TextView tvBirth;
    @InjectView(R.id.ll_birth)
    LinearLayout llBirth;
    @InjectView(R.id.tv_hangye)
    TextView tvHangye;
    @InjectView(R.id.ll_type)
    LinearLayout llType;
    @InjectView(R.id.tv_phone)
    TextView tvPhone;
    @InjectView(R.id.ll_phone)
    LinearLayout llPhone;
    @InjectView(R.id.tv_wx)
    TextView tvWx;
    @InjectView(R.id.ll_wx)
    LinearLayout llWx;
    @InjectView(R.id.tv_qq)
    TextView tvQq;
    @InjectView(R.id.ll_qq)
    LinearLayout llQq;
    @InjectView(R.id.tv_mail)
    TextView tvMail;
    @InjectView(R.id.ll_mail)
    LinearLayout llMail;
    @InjectView(R.id.tv_area)
    TextView tvArea;
    @InjectView(R.id.ll_area)
    LinearLayout llArea;
    private OptionsPickerView pvOptions;
    private List<String> sexList;
    private List<String> hangyeList;
    private List<String> ageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle,"个人信息");
        initData();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_account);
        ButterKnife.inject(this);
    }


    private void initData() {
        sexList = new ArrayList<>();
        hangyeList = new ArrayList<>();
        ageList = new ArrayList<>();
        sexList.add("男");
        sexList.add("女");

        hangyeList.add("计算器");
        hangyeList.add("hg5");
        hangyeList.add("php");

        for (int i = 0; i < 10; i++) {
            ageList.add("" + (19 + i));
        }
    }

    @OnClick({R.id.ll_nickname, R.id.ll_sex, R.id.ll_birth})
    public void onViewClicked(View view) {
        Intent intent=null;
        switch (view.getId()) {
            case R.id.ll_nickname:
                intent=new Intent(PersonMessActivity.this,UpdateNickNameAcitivy.class);
                startActivity(intent);
                break;
            case R.id.ll_sex:
                pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = sexList.get(options1);
                        EToastUtil.show(PersonMessActivity.this, tx);
                           }
                          })
                        .setTitleText("性别选择")
                        .setContentTextSize(20)//设置滚轮文字大小
                        .setDividerColor(Color.GREEN)//设置分割线的颜色
                        .setSelectOptions(0, 1)//默认选中项
                        .setBgColor(Color.WHITE)
                        .setTitleBgColor(Color.DKGRAY)
                        .setTitleColor(Color.LTGRAY)
                        .setCancelColor(Color.YELLOW)
                        .setSubmitColor(Color.YELLOW)
                        .setTextColorCenter(Color.BLACK)
                        .setBackgroundId(0x20000000) //设置外部遮罩颜色
                        .build();
                pvOptions.setPicker(sexList);//一级选择器
                pvOptions.show();
                break;
            case R.id.ll_birth:
                pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = ageList.get(options1);
                        EToastUtil.show(PersonMessActivity.this, tx);
                    }
                })
                        .setTitleText("年龄选择")
                        .setContentTextSize(20)//设置滚轮文字大小
                        .setDividerColor(Color.GREEN)//设置分割线的颜色
                        .setSelectOptions(0, 1)//默认选中项
                        .setBgColor(Color.WHITE)
                        .setTitleBgColor(Color.DKGRAY)
                        .setTitleColor(Color.LTGRAY)
                        .setCancelColor(Color.YELLOW)
                        .setSubmitColor(Color.YELLOW)
                        .setTextColorCenter(Color.BLACK)
                        .setBackgroundId(0x20000000) //设置外部遮罩颜色
                        .build();
                pvOptions.setPicker(ageList);//一级选择器
                pvOptions.show();
                break;

        }
    }
}
