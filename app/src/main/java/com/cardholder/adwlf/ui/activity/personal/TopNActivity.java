package com.cardholder.adwlf.ui.activity.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cardholder.adwlf.AppContext;
import com.cardholder.adwlf.Constants;
import com.cardholder.adwlf.R;
import com.cardholder.adwlf.adapter.TopNAdapter;
import com.cardholder.adwlf.bean.returned.Rating;
import com.cardholder.adwlf.bean.user.User_For_pe;
import com.cardholder.adwlf.control.CustomProgressDialog;
import com.cardholder.adwlf.ui.activity.basic.ListViewActivity;
import com.cardholder.adwlf.utils.JsonUtils;
import com.cardholder.adwlf.net.NetworkDownload;
import com.cardholder.adwlf.util.TLog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 榜单界面
 */
public class TopNActivity extends ListViewActivity{

    @InjectView(R.id.title_name)
    public TextView title;
    @InjectView(R.id.title_back)
    public ImageView title_back;
    @InjectView(R.id.listview)
    PullToRefreshListView mPullRefreshListView;
    @InjectView(R.id.topn_tv_myrank)
    public TextView tv_myrank;
    @InjectView(R.id.topn_tv_money)
    public TextView tv_money;
    @InjectView(R.id.topn_iv_rank)
    public ImageView iv_rank;
    private TopNAdapter adapter;
    private Rating listReturn = null;
    private Rating r=null;
    private CustomProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.top_title,"榜单");
        initDate();
        request();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.layout_topn);
    }

    public void initDate() {
        dialog = new CustomProgressDialog(this);
        dialog.setText("正在加载排行榜。。。");
        adapter = new TopNAdapter(this, listReturn);
        mPullRefreshListView.getRefreshableView().setAdapter(adapter);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mPullRefreshListView.setOnRefreshListener(this);
    }

    public void request() {
        dialog.show();
        getDate();
    }
    /**
     * 请求网络数据 获取明细列表
     */
    public void getDate() {
         User_For_pe peUser = AppContext.getInstance().getPEUser();
        if(peUser == null) {
            onrequestDone();
            TLog.log("userinfo null");
            return;
        }
        RequestParams params = new RequestParams();
         User_For_pe peUser1 = AppContext.getInstance().getPEUser();
        params.add("uid", peUser1.getUid());
        params.add("token", peUser.getToken());

        NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_RATING, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                r = JsonUtils.getBean(jsonObject.optJSONObject("data"), Rating.class);
                if(r.getList()==null||r.getList().size()<1||r.getMyinfo()==null){
                    Toast.makeText(TopNActivity.this,"网络加载过慢",Toast.LENGTH_SHORT).show();
                }else {
                    System.out.println(r);
                    adapter.setData(r);
                    double earn_money = r.getMyinfo().getEarn_money();
                    tv_money.setText(earn_money + "");
                    tv_myrank.setText(r.getMyinfo().getLastDayRank() + r.getMyinfo().getRank() + r.getMyinfo().getLastRank());
                    tv_myrank.setText(r.getMyinfo().getRank());
                    if ("-".equals(r.getMyinfo().getLastDayRank())) {
                        iv_rank.setBackgroundResource(R.mipmap.topn_rank_down);
                    } else {
                        iv_rank.setBackgroundResource(R.mipmap.topn_rank_up);
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
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        getDate();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        mPullRefreshListView.onRefreshComplete();
    }

    @OnClick(R.id.title_back)
    public void onClick(View view){
        this.finish();
    }

}
