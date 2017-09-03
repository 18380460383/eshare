package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.TestListItemBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.ListViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;

public class TestListActivity extends ListViewActivity {
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.lv_test_list)
    PullToRefreshListView lvTestList;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;
    private int page = 0;
    private CommonAdapter<TestListItemBean> adapter;
    private List<TestListItemBean> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "测评");
        data_list = new ArrayList<>();
        page = 1;
        adapter = new CommonAdapter<TestListItemBean>(this, R.layout.kz_test_list_item, data_list) {
            @Override
            protected void convert(ViewHolder viewHolder, TestListItemBean item, int position) {
                viewHolder.setText(R.id.tv_title, item.getTitle())
                        .setText(R.id.tv_count, "参与人数" + item.getViews())
                        .glideImage(R.id.iv_image, item.getImage());
                if (item.getIscepin().equals("0")) {
                    viewHolder.getView(R.id.ll_right_join).setVisibility(View.GONE);
                    viewHolder.getView(R.id.ll_no_join).setVisibility(View.VISIBLE);
                } else {
                    viewHolder.getView(R.id.ll_right_join).setVisibility(View.VISIBLE);
                    viewHolder.getView(R.id.ll_no_join).setVisibility(View.GONE);
                }
            }
        };
        setmPullRefreshListView(lvTestList, adapter);
        setADD();
        lvTestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                if (position % 3 != 0) {
                    intent = new Intent(TestListActivity.this, TestResultActivity.class);
                } else {
                    intent = new Intent(TestListActivity.this, TestDetailActivity.class);
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_test_list);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    /**
     * 下拉刷新
     *
     * @param refreshView
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page++;
        getList();
    }

    /**
     * 上滑加载更多
     *
     * @param refreshView
     */
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page = 0;
        getList();
    }

    public void getList() {
        data_list.clear();
       /* for (int i = page; i <10 ; i++) {
            data_list.add("测试"+i);
        }*/
        Map<String, String> params = new HashMap<>();
        params.put("data[limit]", "" + 10);
        params.put("data[page]", "" + page);
        OkhttpUtilManager.postNoCacah(this, "Evaluation/getEvaluationList", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                if (lvTestList == null) {
                    return;
                }
                Log.e("tst", data);
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    data_list = gson.fromJson(object.getString("data"), new TypeToken<List<TestListItemBean>>() {
                    }.getType());
                    if (data_list.size() == 0) {
                        lvTestList.setEmptyView(llMain);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lvTestList.onRefreshComplete();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                if (lvTestList == null) {
                    return;
                }
                Log.e("tst", msg);

            }
        });
    }

    /**
     * 设置PullToRefreshListView自动加载
     */
    public void setADD() {
        final Handler h = new Handler();
        ViewTreeObserver vto = lvTestList.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                System.out.println("tag" + "开始加载");
                lvTestList.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lvTestList.onRefreshComplete();
                        lvTestList.setRefreshing(true);
                    }
                }, 1000);

            }
        });
    }
}