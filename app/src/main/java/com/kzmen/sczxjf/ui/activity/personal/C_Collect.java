package com.kzmen.sczxjf.ui.activity.personal;

import android.content.Intent;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.CollectAdapter;
import com.kzmen.sczxjf.bean.returned.PersonalUserNews;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.ui.activity.basic.ListViewActivity;
import com.kzmen.sczxjf.utils.JsonUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 创建者：Administrator
 * 时间：2016/4/21
 * 功能描述：
 */
public class C_Collect extends ListViewActivity {
    @InjectView(R.id.actlist_lv)
    PullToRefreshListView actlistLv;
    private int page=1;
    private List<PersonalUserNews.NewsEntity> newsEntityList;
    private CollectAdapter adapter;

    @Override
    public void setThisContentView() {
        //布局和活动列表一样
        setContentView(R.layout.activity_collect);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.act_title, getResources().getString(R.string.c_collect_title_cstr));
        newsEntityList = new ArrayList<>();
        adapter = new CollectAdapter(this, newsEntityList);
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        textView.setText("你还没有收藏任何东西");
        actlistLv.setEmptyView(textView);
        setmPullRefreshListView(actlistLv, adapter);
        setImageLoader(adapter.getImageLoader());
       setADD();
    }



    /**
     * 获取网络收藏数据
     */
    private void getCollectData() {
        final RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getPEUser().getUid());
        params.put("page", page+"");
        params.put("limit",5+"");
        NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_Collect, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                JSONArray data = jsonObject.optJSONArray("data");

                if(data!=null){
                    if(page==1){
                        newsEntityList.clear();
                    }
                    newsEntityList.addAll(JsonUtils.getBeanList(data, PersonalUserNews.NewsEntity.class));
                    page++;
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
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        page=1;
        getCollectData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        super.onPullUpToRefresh(refreshView);
        getCollectData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, InformationForDetails.class);
        intent.putExtra(InformationForDetails.NID,newsEntityList.get(position-1).getNid()+"");
        startActivity(intent);
    }
}
