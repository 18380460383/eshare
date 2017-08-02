package com.cardholder.adwlf.ui.activity.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.cardholder.adwlf.AppContext;
import com.cardholder.adwlf.Constants;
import com.cardholder.adwlf.R;
import com.cardholder.adwlf.adapter.C_Home_Adapter;
import com.cardholder.adwlf.bean.returned.PersonalUserNews;
import com.cardholder.adwlf.net.NetworkDownload;
import com.cardholder.adwlf.ui.activity.basic.ListViewActivity;
import com.cardholder.adwlf.util.EshareLoger;
import com.cardholder.adwlf.utils.JsonUtils;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by FuPei on 2016/2/16.
 */
public class InformationActivity extends ListViewActivity {

    public static final String InformationTitle = "title";
    public static final String type = "type";
    private C_Home_Adapter adapter;
    private List<PersonalUserNews.NewsEntity> newsEntityList;
    private List<PersonalUserNews.ProjectEntity> interactionList;
    @InjectView(R.id.actlist_lv)
    PullToRefreshListView mPullRefreshListView;
    private int page = 1;
    private String stringType;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        Intent intent = getIntent();
        stringType = intent.getStringExtra(type);
        setTitle(R.id.act_title, intent.getStringExtra(InformationTitle));
        initDate();
        setADD();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_actlist);
    }

    /**
     * 初始化数据
     */
    public void initDate() {

        // 只初始化一次
        if (adapter == null) {
            newsEntityList = new ArrayList<>();
            interactionList = new ArrayList<>();
            adapter = new C_Home_Adapter(this, newsEntityList, interactionList);
            emptyView = new TextView(this);

            emptyView.setVisibility(View.GONE);
            emptyView.setGravity(Gravity.CENTER);
            mPullRefreshListView.getRefreshableView().setEmptyView(emptyView);
            setmPullRefreshListView(mPullRefreshListView, adapter);
            setImageLoader(adapter.getImageLoader());
        }
    }

    private void getNews() {
        RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getPEUser().getUid());
        params.put("page", page + "");
        params.put("limit", 5 + "");
        params.put("type", stringType);
        NetworkDownload.jsonGetForCode1(this, Constants.URL_Get_PERSONL_USER_NEWS, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                List<PersonalUserNews.ProjectEntity> interactiondata = JsonUtils.getBeanList(jsonObject.optJSONObject("data").optJSONArray("project"), PersonalUserNews.ProjectEntity.class);
                List<PersonalUserNews.NewsEntity> newsdata = JsonUtils.getBeanList(jsonObject.optJSONObject("data").optJSONArray("news"), PersonalUserNews.NewsEntity.class);
                if (interactiondata != null || newsdata != null) {
                    if (page == 1) {
                        newsEntityList.clear();
                        interactionList.clear();
                        adapter.notifyDataSetChanged();
                    }
                    page++;
                    System.out.println(jsonObject.toString());
                    if (newsdata != null) {
                        newsEntityList.addAll(newsdata);
                    }
                    if (interactiondata != null) {
                        interactionList.addAll(interactiondata);
                    }
                }else{
                    emptyView.setText("暂无相关数据");
                    emptyView.setVisibility(View.VISIBLE);
                }

                onrequestDone();

            }
            @Override
            public void onFailure() {
                emptyView.setText("网络连接失败");
                emptyView.setVisibility(View.VISIBLE);
                onrequestDone();
            }
        });
    }
    /**
     * 刷新
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page = 1;
        getNews();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        EshareLoger.logI("上滑加载更多");
        getNews();
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }
}
