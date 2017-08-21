package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.smartlayout.widgit.CustomLoadingLayout;
import com.kzmen.sczxjf.smartlayout.widgit.SmartLoadingLayout;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 案例详情
 */
public class CaseDetailActivity extends AppCompatActivity {
    /**
     * 标题栏
     */
    public View title;
    /**
     * 标题控件
     */
    public TextView titleNameView;
    protected CustomLoadingLayout mLayout; //SmartLoadingLayout对象
    private JCVideoPlayerStandard myJCVideoPlayerStandard;
    protected void setOnloading(int contentID){
        mLayout = SmartLoadingLayout.createCustomLayout(this);
        mLayout.setLoadingView(R.id.my_loading_page);
        mLayout.setContentView(contentID);
        mLayout.setEmptyView(R.id.my_empty_page);
        mLayout.setErrorView(R.id.my_error_page);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_detail);
        setTitle(R.id.kz_tiltle, "案例");
        setOnloading(R.id.ll_content);
        mLayout.onLoading();
        mHandler.sendEmptyMessageDelayed(1,5*1000);
        initView();
    }
    /**
     * 设置界面的标题栏
     * @param id   标题栏id
     * @param name 标题栏名
     */
    public void setTitle(int id, String name) {
        if (title == null && id != 0) {
            title = findViewById(id);
            PercentRelativeLayout viewById = (PercentRelativeLayout) title.findViewById(R.id.back);
            viewById.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            titleNameView = (TextView) findViewById(R.id.title_name);
            if (!TextUtils.isEmpty(name)) {
                titleNameView.setText(name);
            }
        }
    }

    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mLayout.onDone();
            //initView();
        }
    };

    private void initView(){
        myJCVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.player_list_video);
        myJCVideoPlayerStandard.setUp("http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4"
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "测试");
        Glide.with(this)
                .load("http://img4.jiecaojingxuan.com/2016/11/23/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png@!640_360")
                .into(myJCVideoPlayerStandard.thumbImageView);
       // JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }


}
