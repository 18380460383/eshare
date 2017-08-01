package com.jiebian.adwlf.ui.activity.personal;

import android.graphics.Bitmap;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jiebian.adwlf.Constants;
import com.jiebian.adwlf.R;
import com.jiebian.adwlf.net.NetworkDownload;
import com.jiebian.adwlf.ui.activity.basic.SuperActivity;
import com.jiebian.adwlf.utils.DESUtils;
import com.jiebian.adwlf.util.EshareLoger;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;

/**
 * 说明：描述提现须知信息
 * note：
 * Created by FuPei
 * on 2015/11/22 at 11:11
 */
public class ExplainActivity extends SuperActivity {

    @InjectView(R.id.explain_wb)
    public WebView mWebView;
    @InjectView(R.id.explian_title)
    public View v_title;
    private PercentRelativeLayout v_back;
    public String url_explain = null;
    @Override
    public void onCreateDataForView() {
                setTitle(R.id.explian_title,"提现说明");
        initData();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_explain);
    }

    void initData() {
        initWebView();
        getShareUrl();
    }


    private void initWebView() {
        mWebView.setWebViewClient(new ExplainWebClient());
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



    /**
     * 监听Web加载的状态
     */
    private class ExplainWebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            EshareLoger.logI("加载完成了网页");
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            EshareLoger.logI("开始加载网页");
        }
    }

    public void getShareUrl() {
        NetworkDownload.byteGet(this, Constants.SERVER_API_CONFIG, null, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject json = null;
                try {
                    json = new JSONObject(DESUtils.ebotongDecrypto(new String(responseBody)));
                    url_explain = json.getJSONObject("pro").getString("tixian_help");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                EshareLoger.logI("获取的分享链接: " + url_explain);
                mWebView.loadUrl(url_explain);
            }

            @Override
            public void onFailure() {
                finish();
            }
        });
    }
}
