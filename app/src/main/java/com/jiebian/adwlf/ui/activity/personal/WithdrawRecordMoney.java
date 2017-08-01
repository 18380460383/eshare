package com.jiebian.adwlf.ui.activity.personal;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jiebian.adwlf.AppContext;
import com.jiebian.adwlf.Constants;
import com.jiebian.adwlf.R;
import com.jiebian.adwlf.adapter.RecordMoneyAdapter;
import com.jiebian.adwlf.bean.user.User_For_pe;
import com.jiebian.adwlf.ui.activity.basic.ListViewActivity;

import com.jiebian.adwlf.bean.RecordMoneyBean;
import com.jiebian.adwlf.bean.UserInfo;

import com.jiebian.adwlf.utils.AppUtils;
import com.jiebian.adwlf.utils.JsonUtils;
import com.jiebian.adwlf.net.NetworkDownload;
import com.jiebian.adwlf.util.TLog;
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
 * 个人记录提现明细
 *
 * @author wu
 */
public class WithdrawRecordMoney extends ListViewActivity {

    private static final int SIZE = 50;
    @InjectView(R.id.listview)
    PullToRefreshListView mPullRefreshListView;
    @InjectView(R.id.bj_ll)
    LinearLayout bjLL;
    @InjectView(R.id.bj_null_iv)
    ImageView iv;
    @InjectView(R.id.bi_title)
    TextView bj_title;
    private RecordMoneyAdapter adapter;
    private List<RecordMoneyBean> rows = new ArrayList<>();
    private int currentPage = 1;

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.withdraw_recodr_title,"提现记录");
        initDate();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_withdraw_record_money);
    }


    public void initDate() {

        adapter = new RecordMoneyAdapter(this, rows);
        setmPullRefreshListView(mPullRefreshListView,adapter);
        mPullRefreshListView.getRefreshableView().setAdapter(adapter);
        setADD();

    }


    /**
     * 请求网络数据 获取明细列表
     */
    public void getDate() {
         User_For_pe peUser = AppContext.getInstance().getPEUser();
        if (peUser == null) {
            onrequestDone();
            TLog.log("userinfo null");
            return;
        }
        RequestParams params = new RequestParams();
        params.add("uid", peUser.getUid());
        params.add("token", peUser.getToken());
        params.add("page", currentPage + "");
        params.add("size", SIZE + "");
        mPullRefreshListView.setRefreshing();
        NetworkDownload.jsonGetForCode1(AppContext.getInstance(), Constants.URL_GET_WITHDRAW_DEPOSIT, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                JSONArray data = jsonObject.optJSONArray("data");
                if (null != data) {
                    List<RecordMoneyBean> beanList = JsonUtils.getBeanList(data, RecordMoneyBean.class);
                    if (currentPage == 1) {
                        rows.clear();
                    }
                    if (currentPage == 1) {
                        rows.clear();
                    }
                    rows.addAll(beanList);
                    currentPage++;
                    onrequestDone();
                }
            }

            @Override
            public void onFailure() {
                onrequestDone();
            }
       });

    }

    @Override
    public void onrequestDone() {
        super.onrequestDone();
        AppUtils.setNullListView(adapter, bjLL, iv, R.mipmap.bj_record_money, bj_title, "你目前还没有提现记录哦！", 0);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        currentPage = 1;
        getDate();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getDate();
    }


}
