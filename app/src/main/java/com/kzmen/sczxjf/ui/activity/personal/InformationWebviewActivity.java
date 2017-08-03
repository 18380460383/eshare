package com.kzmen.sczxjf.ui.activity.personal;

import android.graphics.Bitmap;
import android.view.View;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.bean.returned.NewsDetials;
import com.kzmen.sczxjf.control.ShareControl;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.ui.activity.WebviewActivity;
import com.kzmen.sczxjf.utils.AppUtils;
import com.kzmen.sczxjf.utils.JsonUtils;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;

/**
 * 创建者：杨操
 * 时间：2016/7/11
 * 功能描述：单独的纯网页资讯
 */
public class InformationWebviewActivity extends WebviewActivity {
    public final static String NID="webinfo_nid";
    private String stringExtra;
    private Bitmap bmp;

    @Override
    public void onCreateDataForView() {
        super.onCreateDataForView();
        stringExtra = getIntent().getStringExtra(NID);
        getData(stringExtra);

    }

    private void getData(String stringExtraNID) {
        RequestParams params = new RequestParams();
        params.add("uid", AppContext.getInstance().getPEUser().getUid());
        params.add("nid", stringExtraNID);
        NetworkDownload.jsonGet(null, Constants.URL_Get_PERSONL_USER_NEWS_DETILS, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                if (jsonObject.optInt("code") == 1) {
                    final NewsDetials data = JsonUtils.getBean(jsonObject.optJSONObject("data"), NewsDetials.class);
                    if (data != null) {
                        setBitmap(data);
                        menu.setVisibility(View.VISIBLE);
                        menu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                shareNews(data);
                            }
                        });
                    }
                } else if (jsonObject.optInt("code") == 99) {
                }

                dismissProgressDialog();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void setBitmap(NewsDetials data) {
        ImageLoader.getInstance().loadImage(data.getSharepic(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if (bitmap == null) {
                } else {
                    bmp = Bitmap.createScaledBitmap(bitmap, 80, 80, true);
                }
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }

    /**
     * 分享资讯
     */
    private void shareNews( NewsDetials data) {
        String title = data.getTitle();
        if (title.length() > 128) {
            title = title.substring(0, 127);
        }
        ShareControl shareControl=new ShareControl(this);
        shareControl.setBack(new ShareControl.shareonCompleteBack() {
            @Override
            public void onComplete() {
                afterShare();
            }
        });

        String sharepic = data.getSharepic();
        System.out.println("sharepic"+sharepic);
        OnekeyShare onekeyShare = shareControl.getOnekeyShare(title, title, bmp, data.getRelay_url(),data.getImage(),data.getSharepic(), null);
        // 参考代码配置章节，设置分享参数
        onekeyShare.addHiddenPlatform(QQ.NAME);
        onekeyShare.show(this);
    }
    private void afterShare() {
        Map<String,String> map=new HashMap<>();
        map.put("uid",AppContext.getInstance().getPEUser().getUid());
        map.put("nid",stringExtra);
        RequestParams parm = AppUtils.getParm(map);
        NetworkDownload.jsonPostForCode1(this, Constants.URL_POST_SHARENEWS, parm, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                System.out.println("分享" + jsonObject.toString());
            }
            @Override
            public void onFailure() {

            }
        });
    }

}
