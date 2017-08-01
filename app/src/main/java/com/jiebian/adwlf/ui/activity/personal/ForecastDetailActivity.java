package com.jiebian.adwlf.ui.activity.personal;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiebian.adwlf.AppContext;
import com.jiebian.adwlf.Constants;
import com.jiebian.adwlf.R;
import com.jiebian.adwlf.base.BaseActivity;
import com.jiebian.adwlf.bean.DetialBean;
import com.jiebian.adwlf.bean.ProjectBean;
import com.jiebian.adwlf.bean.UserInfo;
import com.jiebian.adwlf.bean.returned.DetialReturn;
import com.jiebian.adwlf.bean.user.User_For_pe;
import com.jiebian.adwlf.net.NetworkDownload;
import com.jiebian.adwlf.util.TLog;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.SoftReference;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 说明：预测信息详情界面
 * note：
 * Created by FuPei
 * on 2015/12/2 at 15:14
 */
public class ForecastDetailActivity extends BaseActivity {

    @InjectView(R.id.title_name)
    public TextView tv_title;
    @InjectView(R.id.title_back)
    public ImageView iv_back;
    @InjectView(R.id.forecast_wb)
    public WebView mWebView;
    @InjectView(R.id.forecast_btn_relay)
    public Button btn_relay;
    private String pid;
    private ProjectBean projectBean;
    private IWXAPI api;
    private DetialBean detialBean;
    private SoftReference<Bitmap> bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_detail);
        ButterKnife.inject(this);
        tv_title.setText("详情");
        setListener();
        initData();
        initWeixin();
        getImage();
        getData();
    }

    private void setListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_relay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
    }

    private void initData() {
        projectBean = (ProjectBean) getIntent().getSerializableExtra("bean");
        pid = getIntent().getIntExtra("pid", 0) + "";
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setVerticalScrollBarEnabled(true);
        mWebView.setHorizontalScrollbarOverlay(true);
        WebSettings s = mWebView.getSettings();
        s.setBuiltInZoomControls(true);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSavePassword(true);
        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true);
        s.setGeolocationEnabled(true);
        s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
        s.setDomStorageEnabled(true);
    }

    private void getData() {
         User_For_pe peUser = AppContext.getInstance().getPEUser();
        RequestParams params = new RequestParams();
        params.put("uid", peUser.getUid());
        params.put("token", peUser.getToken());
        params.put("pid", pid);
        NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_C_D, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                DetialReturn detail = DetialReturn.parseJson(jsonObject);
                detialBean = detail.data.get(0);
                runService(detialBean.projecturl);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void runService(String url) {
        if(!url.contains("http://")&&!url.contains("http//")&&!url.contains("https://")){
            url="http://"+url;
        }
        mWebView.loadUrl(url);
    }

    public void share() {
        // 跳转
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = detialBean.projecturl;

        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = detialBean.projectname;

        if(bitmap != null && bitmap.get() != null) {
            msg.setThumbImage(bitmap.get());
        } else {
            TLog.log("bitmap null");
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = 1;
        api.sendReq(req);
    }

    private void initWeixin() {
        if(api == null) {
            api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        }
        api.registerApp(Constants.APP_ID);
    }

    private void getImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(projectBean == null)
                        return;
                    Bitmap bmp = ImageLoader.getInstance().loadImageSync(projectBean.imageurl);
                    bitmap = new SoftReference<>(Bitmap.createScaledBitmap(bmp, 120, 120, true));
                    bmp.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkDownload.stopRequest(this);
    }
}
