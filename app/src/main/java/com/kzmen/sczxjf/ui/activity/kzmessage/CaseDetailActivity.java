package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.CaseDetailBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.smartlayout.widgit.CustomLoadingLayout;
import com.kzmen.sczxjf.smartlayout.widgit.SmartLoadingLayout;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
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
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_pre_time)
    TextView tvPreTime;
    @InjectView(R.id.tv_source)
    TextView tvSource;
    @InjectView(R.id.wv_content)
    WebView wvContent;
    private JCVideoPlayerStandard myJCVideoPlayerStandard;
    private String id;
    private CaseDetailBean bean;

    protected void setOnloading(int contentID) {
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
        ButterKnife.inject(this);
        setTitle(R.id.kz_tiltle, "案例");
        setOnloading(R.id.ll_content);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");
            RxToast.normal(id);
        }
        mLayout.onLoading();
        mHandler.sendEmptyMessageDelayed(1, 5 * 1000);
        initData();
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("data[id]", "" + id);
        OkhttpUtilManager.postNoCacah(this, "Newscase/getNewscaseShow", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst",data);
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    bean = gson.fromJson(object.getString("data"), CaseDetailBean.class);
                    mHandler.sendEmptyMessage(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                mHandler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 设置界面的标题栏
     *
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

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mLayout.onError();
                    break;
                case 1:
                    initView();
                    mLayout.onDone();
                    break;
            }

        }
    };

    private void initView() {
        myJCVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.player_list_video);
        myJCVideoPlayerStandard.setUp(bean.getVideo()
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, bean.getTitle());
       /* Glide.with(this)
                .load("http://img4.jiecaojingxuan.com/2016/11/23/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png@!640_360")
                .into(myJCVideoPlayerStandard.thumbImageView);*/
        // JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
        tvTitle.setText(bean.getTitle());
        tvSource.setText("资料来源："+bean.getSource());
        tvPreTime.setText("发布时间："+bean.getUpdate_time());
        wvContent.loadUrl(bean.getContent_url());
        wvContent.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
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
