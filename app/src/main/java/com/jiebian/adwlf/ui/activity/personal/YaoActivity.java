package com.jiebian.adwlf.ui.activity.personal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiebian.adwlf.AppContext;
import com.jiebian.adwlf.Constants;
import com.jiebian.adwlf.R;
import com.jiebian.adwlf.base.BaseActivity;
import com.jiebian.adwlf.bean.YaoBean;
import com.jiebian.adwlf.net.NetworkDownload;
import com.jiebian.adwlf.util.EToastUtil;
import com.jiebian.adwlf.util.EshareLoger;
import com.jiebian.adwlf.util.TLog;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.apache.http.Header;
import org.json.JSONException;


/**
 * 说明：摇一摇界面
 * Created by FuPei
 * on 2015/12/28 at 10:30
 */
public class YaoActivity extends BaseActivity {

    /**
     * 摇一摇链接前缀
     */
//    private final String URL_YAO = "http://xiangyixia.360netnews.com/wx_yaoyiyao/11.php?type=2&openid=";
    private final String URL_YAO = "http://192.168.0.111:9012/wx_yaoyiyao/11.php?type=2&uid=";
    private com.tencent.smtt.sdk.WebView mWebView;
    private ImageView iv_back;
    private TextView tv_title;
    private AlertDialog walertDialog;
    private IWXAPI api;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initWeixin();
        setListener();
        initData();
    }

    private void initView() {
        setProgressBarVisibility(true);
        setContentView(R.layout.activity_yao);
        AppContext.getInstance().yaoActivity = this;
        mWebView = (WebView) findViewById(R.id.activity_yao_wb);
        iv_back = (ImageView) findViewById(R.id.title_back);
        tv_title = (TextView) findViewById(R.id.title_name);
    }

    private void initData() {
        EshareLoger.logI("uid:" + AppContext.getInstance().getPEUser().getUid());
        tv_title.setText("摇一摇");

        String openId = AppContext.getInstance().getPEUser().getWeixin();
        if (null != openId) {
            final String url = URL_YAO + AppContext.getInstance().getPEUser().getUid();
            initWebView();
            EshareLoger.logI("摇一摇的url:" + url);
            mWebView.loadUrl(url);
            mWebView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                    webView.loadUrl(s);
                    return true;
                }
            });
        } else {
            EshareLoger.logI("没有openId");
        }
    }

    private void initWeixin() {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        }
        api.registerApp(Constants.APP_ID);
    }

    private void setListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });
        mWebView.addJavascriptInterface(new YaoJavaScriptObj(), "Android");
    }

    /**
     * 初始化webView
     */
    private void initWebView() {
        com.tencent.smtt.sdk.WebSettings s = mWebView.getSettings();
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setVerticalScrollBarEnabled(true);
        mWebView.setHorizontalScrollbarOverlay(true);
        s.setBuiltInZoomControls(true);
        s.setLayoutAlgorithm(com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true);
        s.setGeolocationEnabled(true);
        s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
        s.setDomStorageEnabled(true);
        s.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //处理WebView跳转返回
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * javascript调用监听
     */
    public class YaoJavaScriptObj {

        @JavascriptInterface
        public void ShakeShare(String data) {
            EshareLoger.logI("点击了来分享:" + data);
            try {
                share(data);
            } catch (JSONException e) {
                EshareLoger.logI("分享发生异常");
            }
        }
    }

    /**
     * 回退操作
     */
    private void onBack() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    /**
     * 分享
     */
    private void share(String data) throws JSONException {
        YaoBean bean = YaoBean.parseJson(data);
        String type = bean.shareType;
        if (TextUtils.isEmpty(AppContext.getInstance().getPEUser().getWeixin())) {
            AlertDialog.Builder b = new AlertDialog.Builder(YaoActivity.this).setTitle("请绑定微信号")
                    .setMessage("绑定微信后才可转发").setNeutralButton("绑定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            IWXAPI api = WXAPIFactory.createWXAPI(YaoActivity.this, Constants.APP_ID);
                            api.registerApp(Constants.APP_ID);
                            SendAuth.Req req = new SendAuth.Req();
                            req.scope = "snsapi_userinfo";
                            req.state = "none";
                            if (!api.sendReq(req)) {
                                Toast.makeText(YaoActivity.this, "请安装微信App", Toast.LENGTH_LONG).show();
                            }
                            walertDialog.dismiss();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            walertDialog.dismiss();
                            YaoActivity.this.finish();
                        }
                    });
            b.setCancelable(false);
            walertDialog = b.create();
            b.show();
        } else {
            if (Constants.SHARE_TYPE_WEIXIN.equals(type)) {
                shareToWeiXin(bean);
            } else {
                EToastUtil.show(YaoActivity.this, "暂无分享途径");
            }
        }
    }

    /**
     * 分享成功后调用
     */
    public void onShareSuccess() {
        EshareLoger.logI("分享成功调用了");
        mWebView.loadUrl("javascript:ShareSuccess()");
    }

    /**
     * 取消了分享
     */
    public void onShareCancel() {
        EshareLoger.logI("取消调用了");
        mWebView.loadUrl("javascript:nowShareFun()");
    }

    /**
     * 获取图片
     *
     * @param bean 网页js返回的信息
     */
    private void shareToWeiXin(final YaoBean bean) {
        NetworkDownload.byteGet(YaoActivity.this, bean.imgUrl, null, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                bitmap = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = bean.Link;
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title = bean.Title;
                msg.description = bean.desc;
                if (bitmap != null) {
                    msg.setThumbImage(bitmap);
                } else {
                    TLog.log("bitmap null");
                }
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = 1;
                api.sendReq(req);
                showProgressDialog("正在分享");
            }

            @Override
            public void onFailure() {
                EshareLoger.logI("获取图片失败");
            }
        });
    }


}
