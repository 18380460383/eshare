package com.kzmen.sczxjf.ui.activity.personal;


import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.WebActivity;


/**
 * 说明：关于享e下界面
 * note：
 * Created by FuPei
 * on 2015/11/30 at 13:30
 */
public class AboutActivity extends WebActivity {

    @Override
    public void onCreateDataForView() {
        titlename="关于卡掌门";
        setTitle(R.id.about_title,"关于卡掌门");
        setWebViewProgressBar(R.id.about_progressbar);

    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_about);
    }

    @Override
    public int setWebViewId() {
        return R.id.activity_about_wb;
    }

}
