package com.jiebian.adwlf.ui.activity.personal;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jiebian.adwlf.AppContext;
import com.jiebian.adwlf.Constants;
import com.jiebian.adwlf.R;
import com.jiebian.adwlf.bean.DetialBean;
import com.jiebian.adwlf.bean.Token7NiuBean;
import com.jiebian.adwlf.bean.UserInfo;
import com.jiebian.adwlf.bean.WeixinInfo;
import com.jiebian.adwlf.bean.returned.DetialReturn;
import com.jiebian.adwlf.bean.user.User_For_pe;
import com.jiebian.adwlf.net.EnWebUtil;
import com.jiebian.adwlf.ui.activity.BaseWebActivity;
import com.jiebian.adwlf.ui.activity.basic.SuperActivity;
import com.jiebian.adwlf.utils.AppUtils;
import com.jiebian.adwlf.net.NetworkDownload;
import com.jiebian.adwlf.ui.fragment.personal.Fragment1;
import com.jiebian.adwlf.ui.fragment.personal.FragmentList;
import com.jiebian.adwlf.ui.fragment.personal.FragmentRecordRelay;
import com.jiebian.adwlf.util.EshareLoger;
import com.jiebian.adwlf.util.StringUtils;
import com.jiebian.adwlf.util.TLog;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author wu
 */
public class DetialActivity extends SuperActivity {

    @InjectView(R.id.md_webview)
    public WebView mWebView;
    @InjectView(R.id.relay_btn)
    public Button relay_btn;
    @InjectView(R.id.status_text)
    public TextView status_text;
    @InjectView(R.id.money_text)
    public TextView money_text;
    @InjectView(R.id.title_name)
    public TextView title;
    @InjectView(R.id.title_back)
    public ImageView title_back;
    @InjectView(R.id.detial_to_e_detial)
    public TextView toEDetial;
    @InjectView(R.id.webview_progressbar_detial)
    public ProgressBar webviewProgressBar;
    DetialBean detialBean = null;
    private int pid;

    private int currentButtonState = 0;

    private String type = "";
    // 需要计算时间
    private boolean needCompute = true;

    private IWXAPI api;
    SoftReference<Bitmap> bitmap = null;
    private static Fragment fragment;
    private AlertDialog.Builder b;
    private AlertDialog walertDialog;
    private boolean isStartReceiver;
    private static final String TAG="DetialActivity";
    private boolean isShareReturn=false;

    public Fragment getFragment() {
        return fragment;
    }

    public static void setFragment(Fragment fra) {
        fragment = fra;
    }

    /*即将开始*/
    public static final String TYPE_START = "allforbuy";
    /*正在进行*/
    public static final String TYPE_ING = "allbuying";
    /*已错过*/
    public static final String TYPE_END = "allmiss";
    private Handler handler;
    private boolean canShowTime;
    private User_For_pe info;
    private BroadcastReceiver receiver;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        AppContext.getInstance().detialActivity = this;
        canShowTime = true;
        initView();
        pid = getIntent().getIntExtra("pid", 0);

        initDate();
    }

    @Override
    public void onCreateDataForView() {

    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.detial);
    }

    private void initView() {
        title.setText("详情");
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    if (webviewProgressBar.getVisibility() == View.GONE) {
                        webviewProgressBar.setVisibility(View.VISIBLE);
                    }
                    webviewProgressBar.setProgress(newProgress);
                } else {
                    webviewProgressBar.setVisibility(View.GONE);
                }
            }
        });
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


    // 初始化数据
    private void initDate() {
        //TODO 上传点击量
        Map<String, String> map = new HashMap();
        map.put("uid", AppContext.getInstance().getPEUser().getUid());
        map.put("pid", pid + "");
        RequestParams requestParams = AppUtils.getParm(map);
        //增加点击次数
        NetworkDownload.bytePost(this, Constants.URL_SERVER_UP_LOAD_HITS, requestParams, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure() {

            }
        });
        info = AppContext.getInstance().getPEUser();
        if (info == null) {
            TLog.log("userinfo null");
            return;
        }
        request();
    }


    /**
     * 获取互动详情
     */
    private void request() {

        info = AppContext.getInstance().getPEUser();
        if (info == null) {
            TLog.log("userinfo null");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("uid", info.getUid());
        params.put("token", info.getToken());
        params.put("pid", pid);
        NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_C_D, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                DetialReturn detail = DetialReturn.parseJson(jsonObject);
                DetialBean listReturn = detail.data.get(0);
                setDate(listReturn);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    /**
     * 设置详情数据
     */
    private void setDate(DetialBean listReturn) {
        if (listReturn != null) {
            toEDetial.setVisibility(View.VISIBLE);
            detialBean = listReturn;
            if (null != status_text) {
                if (!StringUtils.isEmpty(type)) {
                    status_text.setText(type);
                }
                // 需要计算时间时
                long l1 = Long.parseLong(detialBean.enddate) * 1000;
                long l = System.currentTimeMillis();
                if (l1> l) {
                    status_text.setText(AppContext.getInstance().getTimeMillis(detialBean.nowdate, detialBean.enddate));
                }
                if (detialBean.ismoney == 0 && "进行中".equals(type)) {
                    status_text.setText("已转完");
                    showFreeDialog();
                }

            }
            money_text.setText(detialBean.reward);

            setButtonStatus();
            // 加载网页
            runService(detialBean.projecturl);
            getImage();

        }
    }

    private void showFreeDialog() {
        b = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("有偿转发的名额已经被抢光啦！")
                .setPositiveButton("无偿转发", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        relayClicked();
                    }
                })
                .setNegativeButton("下次努力", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        b.create();
        b.show();
    }

    /**
     * 设置按钮状态
     */
    private void setButtonStatus() {
        Log.i(TAG, detialBean.isrelay + "");
        //判断是否转发
        if (detialBean.isrelay == 1) {
            Log.i(TAG, "已经转发了该活动");
            //是否需要截图
            if (detialBean.isneedscreenshot == 1) {
                Log.i(TAG, "需要截图");
                // 需要上传
                if (detialBean.isscreenshot == 0) {
                    Log.i(TAG, "还没截图");
                    if (detialBean.sec > 0) {
                        uploadImageTime();

                    } else {
                        relay_btn.setEnabled(true);
                        relay_btn.setBackgroundResource(R.mipmap.btn_upload);
//                    relay_btn.setText("上传截图");
                        currentButtonState = 1;
                    }

                } else {
                    Log.i(TAG, "已截图完成");
                    relay_btn.setEnabled(false);
                    relay_btn.setBackgroundResource(R.mipmap.btn_haveload);

                }
            } else {
                Log.i(TAG, "不需要截图");
                relay_btn.setBackgroundResource(R.mipmap.btn_haverelay);
                relay_btn.setEnabled(false);
                relay_btn.setText("");
                currentButtonState = 2;
            }
        } else {
            // 还没转发，显示转发
            relay_btn.setEnabled(true);
            relay_btn.setBackgroundResource(R.drawable.btn_relay);
            currentButtonState = 3;
            relay_btn.setText("");
        }
        Log.i(TAG, "type = " + type);
        if ("进行中".equals(type)) {

        } else if ("已结束".equals(type)) {
            relay_btn.setBackgroundResource(R.mipmap.hadmiss);
            relay_btn.setEnabled(false);
        } else if ("进行中".equals(type)) {

        } else if ("即将开抢".equals(type)) {
            relay_btn.setEnabled(false);
        }

    }

    private void uploadImageTime() {
        relay_btn.setTextColor(getResources().getColor(R.color.gray));
        handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = detialBean.sec; i > 0; i--) {
                    final int a = i;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(canShowTime) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                relay_btn.setText(getCountDown(a)+"");
                            }
                        });
                    } else {
                        break;
                    }

                }
                if(canShowTime){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            relay_btn.setEnabled(true);
                            relay_btn.setBackgroundResource(R.mipmap.btn_upload);
                            currentButtonState = 1;
                        }
                    });
                }



            }
        }).start();
    }

    private String getCountDown(int time) {
        int h, m, s;
        h = time / 3600;
        m = (time - h * 3600) / 60;
        s = (time - h * 3600) % 60;
        return "上传截图" + "(" + unitFormat(h) + ":" + unitFormat(m) + ":" + unitFormat(s) + ")";
    }

    @OnClick({R.id.title_back, R.id.relay_btn, R.id.detial_to_e_detial})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                this.finish();
                break;
            case R.id.relay_btn:
                relayClicked();
                break;
            case R.id.detial_to_e_detial:
                Intent intent = new Intent(this, BaseWebActivity.class);
                intent.putExtra(BaseWebActivity.EXTRA_URL, detialBean.url);
                startActivity(intent);
                break;
        }
    }

    /**
     * 转发按钮点击事件
     */
    private void relayClicked() {
        if (currentButtonState == 1) {
            getLocalImage();
        } else if (currentButtonState == 2) {
            // 不可点击
        } else if (currentButtonState == 3) {
            share();
        }
    }


    private void initWeixin() {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        }
        api.registerApp(Constants.APP_ID);
    }

    private void getImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (detialBean == null)
                        return;
                    Bitmap bmp = ImageLoader.getInstance().loadImageSync(detialBean.imageurl);
                    bitmap = new SoftReference<>(Bitmap.createScaledBitmap(bmp, 120, 120, true));
                    bmp.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void share() {
        setAccBroadcastReceiver();
        if (TextUtils.isEmpty(AppContext.getInstance().getPEUser().getWeixin())) {
            AlertDialog.Builder b = new AlertDialog.Builder(DetialActivity.this).setTitle("请绑定微信号")
                    .setMessage("绑定微信后才可转发").setNeutralButton("绑定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            IWXAPI api = WXAPIFactory.createWXAPI(DetialActivity.this, Constants.APP_ID);
                            api.registerApp(Constants.APP_ID);
                            SendAuth.Req req = new SendAuth.Req();
                            req.scope = "snsapi_userinfo";
                            req.state = "none";
                            if (!api.sendReq(req)) {
                                Toast.makeText(DetialActivity.this, "请安装微信App", Toast.LENGTH_LONG).show();
                            }
                            walertDialog.dismiss();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            walertDialog.dismiss();
                            DetialActivity.this.finish();
                        }
                    });
            b.setCancelable(false);
            walertDialog = b.create();
            b.show();
        } else {
            // 跳转
            initWeixin();
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = detialBean.projecturl + "&shareType=1&shareTime=" + System.currentTimeMillis();
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = detialBean.projectname;
            if (bitmap != null && bitmap.get() != null) {
                msg.setThumbImage(bitmap.get());
            } else {
                TLog.log("bitmap null");
            }
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = 1;
            api.sendReq(req);
            showProgressDialog(null);
        }
    }


    /**
     * 微信分享后回调
     */
    public void afterShare() {
        isShareReturn = true;
        EshareLoger.logI("分享后回调接口");
        // 提交服务器转发成功
        requestShareDone();
        if (relay_btn != null) {
            relay_btn.setEnabled(false);
        }
    }


    /**
     * 上传转发成功状态
     */
    private void requestShareDone() {
        if(fragment instanceof Fragment1){
            ((Fragment1) fragment).removeGeneralize(pid);
        }
        if(fragment instanceof FragmentList){
            ((FragmentList) fragment).removeGeneralize(pid);
        }
        Map<String, String> map = new HashMap<>();
        map.put("uid", info.getUid());
        map.put("token", info.getToken());
        map.put("pid", pid + "");
        RequestParams params = AppUtils.getParm(map);
        NetworkDownload.jsonPostForCode1(this, Constants.URL_RELAY_SUCCESS, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                request();
            }

            @Override
            public void onFailure() {

            }
        });
    }


    @Override
    protected void onStop() {
        canShowTime = false;
        dismissProgressDialog();
        super.onStop();
    }

    //region 上传图片
    // 选择图片上传
    private void getLocalImage() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, 1001);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            showProgressDialog(null);
            requestToken(uri);
            sendMsgUpdate();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void requestToken(final Uri uri) {
        NetworkDownload.jsonGetForCode1(this, Constants.URL_POST_QINIUTOKEN, null, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                Token7NiuBean token = new Token7NiuBean();
                token.msg = jsonObject.optString("data");
                uploadImage(uri, token);
            }

            @Override
            public void onFailure() {
                dismissProgressDialog();
            }
        });
    }

    private void uploadImage(Uri uri, Token7NiuBean token) {
        if (token == null) {
            dismissProgressDialog();
            Toast.makeText(DetialActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            return;
        }
        TLog.log("uri: " + uri);
        // 名称
        String key = AppContext.getInstance().getTime() + "User";
        try {
            key = key + AppContext.getInstance().getPEUser().getUid()+ "Project";
        } catch (Exception e) {
        }
        UploadManager uploadManager = AppContext.getInstance().getUploadManager();
        uploadManager.put(uriToFile(uri), key, token.msg, new UpCompletionHandler() {
            @Override
            public void complete(String name, ResponseInfo responseInfo, JSONObject res) {
                try {
                    if (responseInfo != null && responseInfo.isOK()) {
                        String imageUrl = res.optString("key", "");
                        uploadScreenshot(imageUrl);
                    } else {
                        TLog.log(responseInfo == null ? "responseInfo null" : responseInfo.toString());
                        dismissProgressDialog();
                        Toast.makeText(DetialActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    dismissProgressDialog();
                    Toast.makeText(DetialActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                }
            }
        }, null);
    }

    private void uploadScreenshot(String imageUrl) {
        new RequestParams();
        Map<String, String> map = new HashMap<>();
        map.put("uid", info.getUid());
        map.put("token", info.getToken());
        map.put("pid", pid + "");
        map.put("imageurl", "http://7xnffx.com1.z0.glb.clouddn.com/" + imageUrl);
        RequestParams requestParams = AppUtils.getParm(map);
        NetworkDownload.jsonPostForCode1(this, Constants.URL_UP_IMAGE_URL, requestParams, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                Toast.makeText(DetialActivity.this, "上传图片成功", Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
                request();
                if (null != fragment) {
                    if (fragment instanceof FragmentRecordRelay) {
                        ((FragmentRecordRelay) fragment).changeState(pid);
                    }
                }
            }

            @Override
            public void onFailure() {

            }
        });

    }

    private String uriToFile(Uri uri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            return actualimagecursor.getString(actual_image_column_index);
        } catch (Exception e) {

        }
        return "";
    }

    private void runService(String url) {
        if (!url.contains("http://") && !url.contains("https://")) {
            url = "http://" + url;
        }
        mWebView.loadUrl(url);
    }

    /**
     * 发送广播更新界面三的数据
     */
    private void sendMsgUpdate() {
        Intent intent = new Intent();
        intent.setAction(Constants.FRAGMENT_MONEY);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        mWebView.removeAllViews();
        mWebView.setVisibility(View.GONE);
        mWebView.destroy();
        super.onDestroy();
        if(isStartReceiver){
            unregisterReceiver(receiver);
            isStartReceiver=false;
        }


    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
    private void response(Intent data) throws JSONException {
        String json  = data.getExtras().getString(Constants.WEIXIN_ACCREDIT_KEY);
        final  WeixinInfo info = WeixinInfo.parseJson(new JSONObject(json));
        if (info != null) {
            RequestParams requestParams=new RequestParams();
            AppContext.getInstance().saveInfo(info);
            requestParams.put("weixin[platform]", "android");
            requestParams.put("weixin[unionid]", info.unionid);
            requestParams.put("weixin[imageurl]", info.headimgurl);
            requestParams.put("weixin[username]", info.nickname);
            requestParams.put("weixin[city]", info.city + "");
            requestParams.put("weixin[country]", info.country + "");
            requestParams.put("weixin[sex]", info.sex + "");
            requestParams.put("weixin[province]", info.province + "");
            requestParams.put("weixin[source]", AppContext.getInstance().getChannel());
            EnWebUtil.getInstance().post(null, new String[]{"OnwAccount", "saveOwnWeixin"}, requestParams, new EnWebUtil.AesListener() {
                @Override
                public void onSuccess(String result) {
                    User_For_pe peUser = AppContext.getInstance().getPEUser();
                    peUser.setWeixin(info.unionid);
                    peUser.setUsername(info.nickname);
                    peUser.setImageurl(info.headimgurl);
                    AppContext.getInstance().setPEUser(peUser);
                    DetialActivity.this.info=peUser;
                }

                @Override
                public void onFail(int code, String result) {

                }

                @Override
                public void onWebError() {

                }
            });
        }else{
            dismissProgressDialog();
            Toast.makeText(DetialActivity.this, "微信认证失败", Toast.LENGTH_SHORT).show();
        }
    }
    private void setAccBroadcastReceiver(){
        isStartReceiver = true;
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(Constants.WEIXIN_ACCREDIT)){
                    try {
                        response(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else if(intent.getAction().equals(Constants.WEIXIN_SHARE)&&intent.getExtras().getInt(Constants.WEIXIN_SHARE_KEY,0)==1){
                    afterShare();
                }

            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.WEIXIN_ACCREDIT);
        filter.addAction(Constants.WEIXIN_SHARE);
        registerReceiver(receiver, filter);
    }
}
