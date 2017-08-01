package com.jiebian.adwlf.ui.activity.personal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiebian.adwlf.AppContext;
import com.jiebian.adwlf.Constants;
import com.jiebian.adwlf.R;
import com.jiebian.adwlf.UIManager;
import com.jiebian.adwlf.bean.UserInfo;
import com.jiebian.adwlf.bean.user.User_For_pe;
import com.jiebian.adwlf.control.AdvertisementControl;
import com.jiebian.adwlf.net.NetworkDownload;
import com.jiebian.adwlf.ui.activity.WebviewActivity;
import com.jiebian.adwlf.ui.activity.basic.SuperActivity;
import com.jiebian.adwlf.utils.AppUtils;
import com.jiebian.adwlf.utils.JsonUtils;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;


/**
 * 进入应用的广告页
 * Created by 杨操 on 2016/1/7.
 */
public class AdvertActivity extends SuperActivity {
    @InjectView(R.id.advert_imageview)
    ImageView imageView;
    @InjectView(R.id.skip_textview)
    TextView textview;
    @InjectView(R.id.ad_title)
    TextView title;
    @InjectView(R.id.ad_sub_title)
    TextView subTitle;
    @InjectView(R.id.go_ad)
    RelativeLayout goAd;
    private boolean SKIP = false;
    private AdvertisementControl advertisementControl;
    private boolean ONE = true;
    private boolean GOAD = false;
    private boolean GOLOGIN = false;
    private int looktime = 0;
    private Handler handler = new Handler();

    @Override
    public void onCreateDataForView() {
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SKIP=true;
                redirectTo(0);
            }
        });
        new ImageView(this).setImageBitmap(null);
        advertisementControl = AdvertisementControl.getAdvertisementControl();
        goAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GOAD = true;
                SKIP = true;
                Intent intent = new Intent(AdvertActivity.this, WebviewActivity.class);
                intent.putExtra("url", advertisementControl.getAdvertisement().getLinkurl());
                intent.putExtra("title", advertisementControl.getAdvertisement().getTitle());
                startActivity(intent);
            }
        });

        if (advertisementControl.isHaveAdvertisement()) {
            Bitmap bitmap = BitmapFactory.decodeFile(advertisementControl.getImageFilePath());
            imageView.setImageBitmap(bitmap);
            String titletext = advertisementControl.getAdvertisement().getTitle();
            if (!TextUtils.isEmpty(titletext)) {
                this.title.setText(titletext);
            }
            String subTitle = advertisementControl.getAdvertisement().getSubtitle();
            if (!TextUtils.isEmpty(subTitle)) {
                this.subTitle.setText(subTitle);
            }
            ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    final String times = advertisementControl.getAdvertisement().getTimes();
                    if (ONE) {
                        ONE = false;
                        rockonByTime(times);
                    }
                }
            });
        }
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_advert);
    }


    private void rockonByTime(final String times) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer integer = Integer.valueOf(times);
                    Log.i("tag", integer + "");
                    for (int i = integer; i > 0; i--) {
                        final int a = i;
                        if (!SKIP) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    textview.setText("跳过广告 " + a + "s");
                                }
                            });

                            Thread.sleep(1000);
                            looktime += 1;
                            Log.i("tag", i + "");
                        } else {
                            break;
                        }
                    }
                    if (!GOAD && !GOLOGIN) {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                redirectTo(0);
                            }
                        });
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 跳转到...
     */
    private void redirectTo(int type) {
        GOLOGIN = true;
        // 判断是否跳过新手引导界面
        if (AppContext.getInstance().isFirst()) {
            // 第一次登录
            UIManager.showFirstGuideActivity(this);
            finish();
        } /*else if (AppContext.getInstance().getPersonageOnLine()) {
                OnLinelogin();
        } */else {
            startActivity(new Intent(this, MainTabActivity.class));
            finish();
        }
        sendLook(type);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (GOAD && SKIP && !GOLOGIN) {
            redirectTo(1);
        }
    }

    private void sendLook(int type) {
        Map<String, String> map = new HashMap<>();
             String uid = AppContext.getInstance().getPEUser().getUid();
        if(TextUtils.isEmpty(uid)){
            return;

        }
        map.put("uid", uid);
        map.put("id", advertisementControl.getAdvertisement().getId());
        map.put("look_time", looktime + "");
        map.put("type", type + "");
        RequestParams params = AppUtils.getParm(map);
        NetworkDownload.jsonPostForCode1(null, Constants.URL_POST_ADLOOK, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                System.out.println("提价成功");
            }

            @Override
            public void onFailure() {
                System.out.println("提价失败");

            }
        });
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }
}
