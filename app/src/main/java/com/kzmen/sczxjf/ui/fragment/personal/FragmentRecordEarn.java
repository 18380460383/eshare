package com.kzmen.sczxjf.ui.fragment.personal;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.RecordEarnAdapter;
import com.kzmen.sczxjf.ui.fragment.basic.SuperFragment;
import com.kzmen.sczxjf.bean.EarnBean;
import com.kzmen.sczxjf.control.CustomProgressDialog;
import com.kzmen.sczxjf.utils.AppUtils;
import com.kzmen.sczxjf.utils.JsonUtils;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 说明：收入记录明细
 * note：
 * Created by FuPei
 * on 2015/11/30 at 16:56
 */
public class FragmentRecordEarn extends SuperFragment implements PullToRefreshBase.OnRefreshListener2,Serializable{

    /**每此请求信息的显示量*/
    private final int PAGE_LIMIT = 10;
    private View rootView;
    @InjectView(R.id.bj_ll)
    LinearLayout bjLL;
    @InjectView(R.id.bj_null_iv)
    ImageView iv;
    @InjectView(R.id.bi_title)
    TextView bj_title;
    @InjectView(R.id.listview)
    public PullToRefreshListView mlistview;
    private CustomProgressDialog dialog;
    private int currentPage = 1;
    private List<EarnBean> listdata = new ArrayList<>();
    private List<EarnBean> single_data;
    private RecordEarnAdapter mAdatper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragmentlist, container, false);
            ButterKnife.inject(this, rootView);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        initDate();
        if(listdata!=null&&listdata.size()<1){
            setADD();
        }

        return rootView;
    }

    private void request() {
        RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getPEUser().getUid());
        params.put("page", currentPage);
        params.put("limit", PAGE_LIMIT);
        NetworkDownload.jsonGetForCode1(AppContext.getInstance(), Constants.URL_GET_INCOME, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                single_data = JsonUtils.getBeanList(jsonObject.optJSONArray("data"), EarnBean.class);
                if (currentPage == 1) {
                    listdata.clear();
                }
                listdata.addAll(single_data);
                mAdatper.notifyDataSetChanged();
                if (mAdatper.getCount() < 1) {
                    AppUtils.setNullListView(mAdatper, bjLL, iv, R.mipmap.no_g_start, bj_title, "亲你还没有收录记录哦，赶快去转发吧！", 0);
                }
                mlistview.onRefreshComplete();
            }

            @Override
            public void onFailure() {
                mlistview.onRefreshComplete();
                AppUtils.setNullListView(mAdatper, bjLL, iv, R.mipmap.no_g_start, bj_title, "亲你还没有收录记录哦，赶快去转发吧！", 0);

            }
        });
    }

    private void initDate() {
        dialog = new CustomProgressDialog(getContext());
        dialog.setText("正在加载");
        mlistview.setOnRefreshListener(this);
        mlistview.setMode(PullToRefreshListView.Mode.BOTH);
        mlistview.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    mlistview.setMode(PullToRefreshBase.Mode.MANUAL_REFRESH_ONLY);
                } else {
                    mlistview.setMode(PullToRefreshBase.Mode.BOTH);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        mlistview.getLoadingLayoutProxy().setRefreshingLabel("正在获取数据");
        mlistview.getLoadingLayoutProxy().setPullLabel("数据更新");
        mlistview.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");

        mAdatper = new RecordEarnAdapter(getContext(), listdata);
        mlistview.setAdapter(mAdatper);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        currentPage = 1;
        request();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        currentPage++;
        request();
    }
    public void setADD(){
        final Handler h=new Handler();
        ViewTreeObserver vto = mlistview.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mlistview.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mlistview.onRefreshComplete();
                        mlistview.setRefreshing(true);
                    }
                }, 1000);

            }
        });
    }

}
