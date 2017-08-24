package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.view.banner.BannerLayout;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class ShopDetailActivity extends SuperActivity {

    @InjectView(R.id.bl_main_banner)
    BannerLayout blMainBanner;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.tv_leve)
    TextView tvLeve;
    @InjectView(R.id.iv_like)
    ImageView ivLike;
    @InjectView(R.id.tv_buy)
    TextView tvBuy;
    private String url1 = "http://192.168.0.101:8000/static/mp3/2.jpg";
    private List<String> urlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "商品详情");
        initView();
    }

    private void initView() {
        setOnloading(R.id.ll_content);
        mLayout.onLoading();
        mHandler.sendEmptyMessageDelayed(1, 5 * 1000);

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 数据下载完成，转换状态，显示内容视图
            initData();
            mLayout.onDone();
        }
    };

    private void initData() {
        urlList.add(url1);
        urlList.add(url1);
        urlList.add(url1);
        urlList.add(url1);
        urlList.add(url1);
        blMainBanner.setViewUrls(urlList);
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_shop_detail);
    }

    @OnClick({R.id.iv_like, R.id.tv_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_like:
                break;
            case R.id.tv_buy:
                startActivity(new Intent(ShopDetailActivity.this, ShopOrderComfirActivity.class));
                finish();
                break;
        }
    }
}
