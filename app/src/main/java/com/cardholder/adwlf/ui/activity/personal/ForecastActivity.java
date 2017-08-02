package com.cardholder.adwlf.ui.activity.personal;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cardholder.adwlf.AppContext;
import com.cardholder.adwlf.Constants;
import com.cardholder.adwlf.R;
import com.cardholder.adwlf.adapter.ForecastAdapter;
import com.cardholder.adwlf.bean.ProjectBean;
import com.cardholder.adwlf.control.CustomProgressDialog;
import com.cardholder.adwlf.ui.activity.basic.ListViewActivity;
import com.cardholder.adwlf.utils.JsonUtils;
import com.cardholder.adwlf.net.NetworkDownload;
import com.cardholder.adwlf.util.EshareLoger;
import com.cardholder.adwlf.util.TLog;
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
 * 说明：预测界面
 * note：
 * Created by FuPei
 * on 2015/11/30 at 14:24
 */
public class ForecastActivity extends ListViewActivity {

    @InjectView(R.id.title_back)
    public ImageView iv_back;
    @InjectView(R.id.title_name)
    public TextView tv_title;
    @InjectView(R.id.forecast_lv)
    public PullToRefreshListView lv;
    private List<ProjectBean> listdata;
    private ForecastAdapter mAdapter;
    private CustomProgressDialog dialog;
    @Override
    public void onCreateDataForView() {
        setTitle(R.id.forecast_title, "赚钱信息早知道");

        initData();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_forecast);
    }

    void initData() {
       showProgressDialog("正在加载。。。");
        listdata = new ArrayList<>();
        mAdapter = new ForecastAdapter(ForecastActivity.this, listdata);
        setmPullRefreshListView(lv,mAdapter);
        lv.setMode(PullToRefreshBase.Mode.DISABLED);
        getData();
    }


    public void getData() {
        RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getPEUser().getUid());
        params.put("token", AppContext.getInstance().getPEUser().getToken());
        params.put("type", "forecast");
        NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_PROJECT, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                List<ProjectBean> data = JsonUtils.getBeanList(jsonObject.getJSONArray("data"), ProjectBean.class);
                EshareLoger.logI("data = " + data.toString());
                listdata.clear();
                listdata.addAll(data);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
            TLog.log("onItemClick");
            ProjectBean bean = listdata.get(position - 1);
            if (bean != null) {
                Intent intent = new Intent(ForecastActivity.this, ForecastDetailActivity.class);
                intent.putExtra("bean", bean);
                intent.putExtra("pid", bean.pid);
                EshareLoger.logI("发送的pid = " + bean.pid);
                startActivity(intent);
            }

    }
}
