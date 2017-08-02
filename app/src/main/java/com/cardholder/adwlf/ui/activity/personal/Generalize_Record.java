package com.cardholder.adwlf.ui.activity.personal;


import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.cardholder.adwlf.AppContext;
import com.cardholder.adwlf.Constants;
import com.cardholder.adwlf.R;
import com.cardholder.adwlf.adapter.RecordEMsgAdapter;
import com.cardholder.adwlf.bean.IIData;
import com.cardholder.adwlf.ebean.GeneralizeMsg;
import com.cardholder.adwlf.net.NetworkDownload;
import com.cardholder.adwlf.ui.activity.BeasActivity;
import com.cardholder.adwlf.utils.AppUtils;
import com.cardholder.adwlf.utils.JsonUtils;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/10/30.
 */
public class Generalize_Record extends BeasActivity implements PullToRefreshBase.OnRefreshListener2{
    private List<GeneralizeMsg> list;
    private RecordEMsgAdapter adapter;
    private PullToRefreshListView listview;
    private int pag=1;
    private float y1 = 0;
    @InjectView(R.id.bj_ll)
    LinearLayout bjLL;
    @InjectView(R.id.bj_null_iv)
    ImageView iv;
    @InjectView(R.id.bi_title)
    TextView bj_title;
    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_financial_information);
        ButterKnife.inject(this);
    }

    @Override
    public int setTitleId() {
        return R.id.fin_info_title;
    }

    @Override
    public CharSequence setTitleName() {
        return "推广记录";
    }

    @Override
    public void initView() {
        list=new ArrayList<GeneralizeMsg>();
        adapter=new RecordEMsgAdapter(this,list);
        listview= (PullToRefreshListView) findViewById(R.id._record_listview);
        listview.setAdapter(adapter);
        listview.setOnRefreshListener(this);
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                System.out.println(scrollState + "滑动状态");
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    listview.setMode(PullToRefreshBase.Mode.MANUAL_REFRESH_ONLY);
                } else {
                    listview.setMode(PullToRefreshBase.Mode.BOTH);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        listview.getLoadingLayoutProxy().setRefreshingLabel("正在获取数据");
        listview.getLoadingLayoutProxy().setPullLabel("数据更新");
        listview.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IIData.setNull();
               /* Intent intent = new Intent(Generalize_Record.this,Generalization_Details.class);
                intent.putExtra("pid",list.get(position-1).getPid());
                intent.putExtra("st",list.get(position-1).getState());
                startActivity(intent);*/
            }
        });

    }

    @Override
    public void getData() {
        showProgressDialog("正在获取推广记录。。。");
        RequestParams requestParams = new RequestParams();
        requestParams.put("uid", AppContext.getInstance().getPEUser().getUid());
        requestParams.put("page", pag + "");
        requestParams.put("limit", 10 + "");
        NetworkDownload.jsonGetForCode1(this, Constants.URL_SERVER_PROJECT_LIST, requestParams, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                dismissProgressDialog();
                JSONArray data1 = jsonObject.optJSONArray("data");
                if (null != data1) {
                    List<GeneralizeMsg> data = JsonUtils.getBeanList(data1, GeneralizeMsg.class);
                    if(pag==1){
                        list.clear();
                    }
                    list.addAll(data);
                    listview.onRefreshComplete();
                    adapter.notifyDataSetChanged();
                }
                pag++;
                listview.onRefreshComplete();
                AppUtils.setNullListView(adapter, bjLL, iv, R.mipmap.bj_recordrelay, bj_title, "你还没有做任何推广哦!\n" + "赶快去发布推广吧！", 0);
            }

            @Override
            public void onFailure() {
                AppUtils.setNullListView(adapter, bjLL, iv, R.mipmap.bj_recordrelay, bj_title, "你还没有做任何推广哦！\n" + "赶快去发布推广吧！", 0);
                dismissProgressDialog();
                listview.onRefreshComplete();
            }
        });

    }



    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pag=1;
        getData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getData();
    }
}
