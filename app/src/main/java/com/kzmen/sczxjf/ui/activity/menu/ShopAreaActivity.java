package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.EToastUtil;
import com.kzmen.sczxjf.view.wheelview.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class ShopAreaActivity extends SuperActivity {
    @InjectView(R.id.tv_provice)
    TextView tvProvice;
    @InjectView(R.id.ll_edit_provice)
    LinearLayout llEditProvice;
    @InjectView(R.id.tv_city)
    TextView tvCity;
    @InjectView(R.id.ll_edit_city)
    LinearLayout llEditCity;
    @InjectView(R.id.tv_user_name)
    TextView tvUserName;
    @InjectView(R.id.ll_country)
    LinearLayout llCountry;
    @InjectView(R.id.tv_save)
    TextView tvSave;
    private OptionsPickerView pvOptions;
    private List<String> proList;
    private List<String> cityList;
    private List<String> countryList;
    private List<String> tempList;
    private String provice="";
    private String city="";
    private String country="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "送货区域");
        proList = new ArrayList<>();
        cityList = new ArrayList<>();
        countryList = new ArrayList<>();
        tempList = new ArrayList<>();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            proList.add("省份" + i);
            cityList.add("城市" + i);
            countryList.add("区县" + i);
        }
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_shop_area);
    }

    @OnClick({R.id.ll_edit_provice, R.id.ll_edit_city, R.id.ll_country,R.id.tv_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_edit_provice:
                showSelector(0);
                break;
            case R.id.ll_edit_city:
                if(TextUtils.isEmpty(provice)){
                    EToastUtil.show(ShopAreaActivity.this,"请先选择省份");
                    return;
                }
                showSelector(1);
                break;
            case R.id.ll_country:
                if(TextUtils.isEmpty(city)){
                    EToastUtil.show(ShopAreaActivity.this,"请先选择城市");
                    return;
                }
                showSelector(2);
                break;
            case R.id.tv_save:
                if(TextUtils.isEmpty(country)){
                    EToastUtil.show(ShopAreaActivity.this,"请选择区县后再保存");
                    return;
                }
                Intent mIntent = new Intent();
                mIntent.putExtra("data",provice+city+country);
                // 设置结果，并进行传送
                this.setResult(1003, mIntent);
                this.finish();
                break;
        }
    }

    private void showSelector(final int type) {
        tempList.clear();
        String title = "";
        switch (type) {
            case 0:
                tempList.addAll(proList);
                title = "省份选择";
                break;
            case 1:
                tempList.addAll(cityList);
                title = "城市选择";
                break;
            case 2:
                tempList.addAll(countryList);
                title = "区县选择";
                break;

        }
        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = tempList.get(options1);
                EToastUtil.show(ShopAreaActivity.this, tx);
                switch (type) {
                    case 0:
                        tvProvice.setText(tx);
                        provice=tx;
                        break;
                    case 1:
                        city=tx;
                        tvCity.setText(tx);
                        break;
                    case 2:
                        country=tx;
                        tvUserName.setText(tx);
                        break;

                }
            }
        })
                .setTitleText(title)
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
        pvOptions.setPicker(tempList);//一级选择器
        pvOptions.show();
    }
}
