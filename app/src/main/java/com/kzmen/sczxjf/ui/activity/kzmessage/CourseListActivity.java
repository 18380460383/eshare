package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.ui.activity.basic.ListViewActivity;
import com.kzmen.sczxjf.util.glide.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class CourseListActivity extends ListViewActivity {

    @InjectView(R.id.msg_center_lv)
    PullToRefreshListView mPullRefreshListView;
    private int page;
    private List<String>listData;
    private CommonAdapter<String>adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "课程");
        initData();
    }

    private void initData() {
        listData = new ArrayList<>();
        page = 1;
        adapter =new CommonAdapter<String>(CourseListActivity.this,R.layout.kz_course_list_item,listData) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                ((TextView)(viewHolder.getView(R.id.tv_title))).setText(listData.get(position)+getString(R.string.tst));
                Glide.with(CourseListActivity.this).load(R.drawable.icon_user1).transform(new GlideRoundTransform(CourseListActivity.this, 3)).into((ImageView) viewHolder.getView(R.id.iv_user_img));
            }
        };
        setmPullRefreshListView(mPullRefreshListView, adapter);
        mPullRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(CourseListActivity.this,CourseDetailAcitivity.class);
                startActivity(intent);
            }
        });
        setADD();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_course_list);
    }

    /**
     * 下拉刷新
     *
     * @param refreshView
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page=0;
        getData(page);
    }

    /**
     * 上滑加载更多
     *
     * @param refreshView
     */
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page++;
        getData(page);
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
    private void getData(int page){
        listData.clear();
        for (int i = page; i <page+10 ; i++) {
            listData.add("测试"+i);
        }

        Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mPullRefreshListView==null){
                    return;
                }
                mPullRefreshListView.onRefreshComplete();
                adapter.notifyDataSetChanged();
            }
        }, 1000);
    }
}
