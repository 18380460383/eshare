package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.ListViewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

public class ShopAddAddressActivity extends ListViewActivity {

    @InjectView(R.id.msg_center_lv)
    PullToRefreshListView mPullRefreshListView;
    @InjectView(R.id.ll_add_address)
    LinearLayout llAddAddress;
    private CommonAdapter<String> adapter;
    private List<String> data_list;
    private int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "选择地址");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_shop_add_address);
    }

    @OnClick(R.id.ll_add_address)
    public void onClick() {
        startActivity(new Intent(ShopAddAddressActivity.this,ShopAddressEditActivity.class));
    }

    private void initData() {
        data_list = new ArrayList<>();
        page = 1;
        adapter=new CommonAdapter<String>(this,R.layout.kz_activi_list_item,data_list) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.tv_title,item);
            }
        };
        setmPullRefreshListView(mPullRefreshListView, adapter);
        setADD();
    }

    /**
     * 下拉刷新
     *
     * @param refreshView
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page = 1;
        getList();
    }

    /**
     * 上滑加载更多
     *
     * @param refreshView
     */
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page++;
        getList();
    }

    public void getList() {
       /* for (int i = page; i <10+page ; i++) {
            data_list.add("测试"+i);
        }*/
        OkhttpUtilManager.postNoCacah(this, "Goods/GetUserAddress", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                if (mPullRefreshListView == null) {
                    return;
                }
                adapter.notifyDataSetChanged();
                mPullRefreshListView.onRefreshComplete();
            }

            @Override
            public void onError(int code, String msg) {
                if (mPullRefreshListView == null) {
                    return;
                }
                mPullRefreshListView.onRefreshComplete();
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 设置PullToRefreshListView自动加载
     */
    public void setADD() {
        final Handler h = new Handler();
        ViewTreeObserver vto = mPullRefreshListView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                System.out.println("tag" + "开始加载");
                mPullRefreshListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshListView.onRefreshComplete();
                        mPullRefreshListView.setRefreshing(true);
                    }
                }, 1000);

            }
        });
    }
}
