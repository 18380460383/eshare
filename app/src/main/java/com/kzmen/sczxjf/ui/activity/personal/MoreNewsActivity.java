package com.kzmen.sczxjf.ui.activity.personal;


import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.C_Home_Adapter;
import com.kzmen.sczxjf.bean.returned.PersonalUserNews;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.ui.activity.basic.ListViewActivity;
import com.kzmen.sczxjf.utils.JsonUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 创建者：Administrator
 * 时间：2016/5/3
 * 功能描述：
 */
public class MoreNewsActivity extends ListViewActivity {
    @InjectView(R.id.listview)
    PullToRefreshListView listview;
    @InjectView(R.id.edittext_search)
    EditText edittextSearch;
    @InjectView(R.id.button_search)
    TextView buttonSearch;
    private C_Home_Adapter adapter;
    private ArrayList<PersonalUserNews.NewsEntity> news;
    private int page=1;

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.more_title, "搜索");
        news = new ArrayList<>();
        adapter = new C_Home_Adapter(this, news, new ArrayList<PersonalUserNews.ProjectEntity>());
        setmPullRefreshListView(listview, adapter);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = edittextSearch.getText().toString();
                if(!TextUtils.isEmpty(s)){
                    page=1;
                    listview.setRefreshing();
                    //getNews(s);
                }
            }
        });
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.more_news);
    }
    private void getNews(String s) {
        RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getPEUser().getUid());
        params.put("page", page+"");
        params.put("limit",5+"");
        params.put("keyword", s);
        NetworkDownload.jsonGetForCode1(this, Constants.URL_Get_PERSONL_USER_NEWS, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                List<PersonalUserNews.NewsEntity> newsdata = JsonUtils.getBeanList(jsonObject.optJSONObject("data").optJSONArray("news"),PersonalUserNews.NewsEntity.class);
                System.out.println("sdafsadfsafasdfsadfas"+news);
                if (news != null) {
                    if (page == 1) {
                        news.clear();
                        adapter.notifyDataSetChanged();
                    }
                    page++;

                    if (newsdata != null&&newsdata.size()>0) {
                        news.addAll(newsdata);
                    }else{
                        Toast.makeText(MoreNewsActivity.this,"暂无匹配项",Toast.LENGTH_SHORT).show();
                    }
                }

                onrequestDone();
            }


            @Override
            public void onFailure() {
                onrequestDone();
            }
        });
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        super.onPullUpToRefresh(refreshView);
        String s = edittextSearch.getText().toString();
        getNews(s);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        page=1;
        String s = edittextSearch.getText().toString();
        getNews(s);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }
}
