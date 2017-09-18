package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.TextUtil;

import butterknife.InjectView;

public class WebAcitivity extends SuperActivity {
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.wv_content)
    WebView wvContent;
    private WebView wv_content;
    private String title;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        if(!TextUtil.isEmpty(title)){
            titleName.setText(title);
            wvContent.loadUrl(url);
            WebSettings settings = wvContent.getSettings();
            settings.setLoadWithOverviewMode(true);
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
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_web_acitivity);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            title=bundle.getString("title");
            url=bundle.getString("url");
        }
    }
}
