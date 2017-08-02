package com.cardholder.adwlf.ui.activity.personal;

import android.content.Intent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.cardholder.adwlf.AppContext;
import com.cardholder.adwlf.Constants;
import com.cardholder.adwlf.R;
import com.cardholder.adwlf.adapter.ActivitysAdapter;
import com.cardholder.adwlf.bean.returned.Activitys;
import com.cardholder.adwlf.net.NetworkDownload;
import com.cardholder.adwlf.ui.activity.BaseWebActivity;
import com.cardholder.adwlf.ui.activity.basic.ListViewActivity;
import com.cardholder.adwlf.util.EshareLoger;
import com.cardholder.adwlf.utils.AppUtils;
import com.cardholder.adwlf.utils.JsonUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import butterknife.InjectView;


/**
 * 说明：活动列表界面
 * Created by FuPei
 * on 2016/1/12 at 9:29
 */
public class ActListActivity extends ListViewActivity {

    @InjectView(R.id.actlist_lv)
    PullToRefreshListView actlistLv;
    private List<Activitys> actList;
    private ActivitysAdapter adapter;

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_actlist);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.act_title, getResources().getString(R.string.actListactivity_title_cstr));
        actList = new ArrayList<Activitys>();
        adapter = new ActivitysAdapter(actList, this);
        setmPullRefreshListView(actlistLv, adapter);
        actlistLv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        actlistLv.setRefreshing();
        setADD();
    }

    private void initData() {
        NetworkDownload.jsonGetForCode1(this, Constants.URL_ACTIVITY_LIST, null, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                JSONArray data = jsonObject.optJSONArray("data");
                if (null != data) {
                    actList.addAll(JsonUtils.getBeanList(data, Activitys.class));
                    EshareLoger.logI("actlist:" + actList);
                    adapter.notifyDataSetChanged();
                    onrequestDone();
                } else {
                    Toast.makeText(ActListActivity.this, "获取的活动数据为空", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure() {
                onrequestDone();
            }
        });
    }

    private void postActHits(String actId) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", AppContext.getInstance().getPEUser().getUid());
        map.put("id", actId);
        RequestParams params = AppUtils.getParm(map);
        NetworkDownload.jsonPostForCode1(null, Constants.URL_POST_ACTHIT, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                System.out.println("提交活动点击成功");
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        intent.setAction(Constants.FRAGMENT_MONEY);
        sendBroadcast(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EshareLoger.logI("position:" + position + ", size=" + actList.size());
        Activitys activitys = actList.get(position - 1);
        System.out.println(activitys.toString());
        postActHits(activitys.getId());
        if("4".equals(activitys.getId())){
            /*OffersManager.getInstance(this).onAppLaunch();
            OffersManager.getInstance(this).setCustomUserId(AppContext.getInstance().getUserInfo().uid + "");
            OffersManager.getInstance(this).setUsingServerCallBack(true);
            OffersManager.getInstance(this).showOffersWall();*/
        }else {
            Intent intent = new Intent(ActListActivity.this, BaseWebActivity.class);
            if (activitys.getLinkurl().endsWith("?")) {
                intent.putExtra(BaseWebActivity.EXTRA_URL, "1".equals(activitys.getType()) ? activitys.getLinkurl() + "type=2&uid=" + AppContext.getInstance().getPEUser().getUid() : activitys.getLinkurl());

            } else {
                intent.putExtra(BaseWebActivity.EXTRA_URL, "1".equals(activitys.getType()) ? activitys.getLinkurl() + "?type=2&uid=" + AppContext.getInstance().getPEUser().getUid() : activitys.getLinkurl());
            }
            intent.putExtra(BaseWebActivity.EXTRA_TITLE, activitys.getTitle());
            ActListActivity.this.startActivity(intent);
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        initData();
    }
}
