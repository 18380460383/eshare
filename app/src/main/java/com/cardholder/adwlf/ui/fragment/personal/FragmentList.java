package com.cardholder.adwlf.ui.fragment.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cardholder.adwlf.AppContext;
import com.cardholder.adwlf.Constants;
import com.cardholder.adwlf.R;
import com.cardholder.adwlf.adapter.PageListAdapter;
import com.cardholder.adwlf.bean.user.User_For_pe;
import com.cardholder.adwlf.ui.fragment.basic.SuperFragment;
import com.cardholder.adwlf.bean.ProjectBean;
import com.cardholder.adwlf.bean.returned.PageListReturn;
import com.cardholder.adwlf.control.CustomProgressDialog;
import com.cardholder.adwlf.utils.AppUtils;
import com.cardholder.adwlf.utils.JsonUtils;
import com.cardholder.adwlf.net.NetworkDownload;
import com.cardholder.adwlf.ui.activity.personal.DetialActivity;
import com.cardholder.adwlf.util.EshareLoger;
import com.cardholder.adwlf.util.TLog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 发现界面的所有fragment集合
 * @author wu
 */
public class FragmentList extends SuperFragment implements PullToRefreshBase.OnRefreshListener2,Serializable{

    /*界面标签*/
    private String type = "";
    @InjectView(R.id.listview)
    public PullToRefreshListView mPullRefreshListView;
    private PageListAdapter adapter;
    private PageListReturn listReturn = null;
    @InjectView(R.id.bj_ll)
    LinearLayout bjLL;
    @InjectView(R.id.bj_null_iv)
    ImageView iv;
    @InjectView(R.id.bi_title)
    TextView bj_title;
    private CustomProgressDialog dialog;
    private List<ProjectBean> rows=new ArrayList<>();
    private int page=1;
    private List<ProjectBean> beanList;

    /**
     * 对每个界面设置不同标签，区分界面
     * @param type
     */
    public void setType(String type) {
        EshareLoger.logI("设置了type = " + type);
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View  rootView = inflater.inflate(R.layout.fragmentlist, container, false);
        ButterKnife.inject(this, rootView);

        initDate();
        if(type.equals(DetialActivity.TYPE_END)){
            request();
        }
        return rootView;
    }


    /**
     * 初始化数据
     */
    public void initDate() {
        if(dialog==null){
            dialog = new CustomProgressDialog(getContext());
        }

        dialog.setText("正在加载。。。");
        if(adapter==null){
            adapter = new PageListAdapter(getContext(), rows);
        }

        adapter.setType(type);
        mPullRefreshListView.getRefreshableView().setAdapter(adapter);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullRefreshListView.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                System.out.println(scrollState+"滑动状态");
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_FLING){
                    mPullRefreshListView.setMode(PullToRefreshBase.Mode.MANUAL_REFRESH_ONLY);
                }else{
                    mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        mPullRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("正在获取数据");
        mPullRefreshListView.getLoadingLayoutProxy().setPullLabel("数据更新");
        mPullRefreshListView.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
        mPullRefreshListView.setOnRefreshListener(this);
        mPullRefreshListView.setOnScrollListener(new PauseOnScrollListener(adapter.getImageLoader(), true, true));
        setListener();
    }

    /**
     * 设置监听
     */
    public void setListener() {
        //点击跳转
        mPullRefreshListView.getRefreshableView().setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ProjectBean bean = (ProjectBean) adapter.getItem(position);
                        if (bean != null) {
                            Intent intent = new Intent(FragmentList.this.getContext(), DetialActivity.class);
                            intent.putExtra("bean", bean);
                            intent.putExtra("type", adapter.getType());
                            intent.putExtra("pid", bean.pid);
                            FragmentList.this.getContext().startActivity(intent);
                            DetialActivity.setFragment(FragmentList.this);
                            TLog.log(bean.projectname);
                        }
                    }
                });
    }

    public void request() {
        if(getContext()==null||dialog==null||adapter==null||mPullRefreshListView==null){
            System.out.println("chu");
            return;
        }
            if(rows.size()<1){
                dialog.show();
                getDate();
            }else{
                page++;
                onrequestDone();
            }

    }


    public void  getDate() {
         User_For_pe peUser1 = AppContext.getInstance().getPEUser();
        if(peUser1 == null) {
            onrequestDone();
            TLog.log("userinfo null");
            return;
        }

        RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getPEUser().getUid());
        params.put("token", AppContext.getInstance().getPEUser().getToken());
        params.put("type", type);
        params.put("page", page + "");
         User_For_pe peUser = AppContext.getInstance().getPEUser();
        System.out.println("udi" + peUser.getUid() + "token"+peUser.getToken()+"type"+type+"page"+page);
        NetworkDownload.jsonGetForCode1(getContext(), Constants.URL_GET_PROJECT, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                beanList = JsonUtils.getBeanList(jsonObject.optJSONArray("data"), ProjectBean.class);
                if (page == 1) {
                    rows.clear();
                }
                rows.addAll(beanList);
                page++;
                onrequestDone();
            }

            @Override
            public void onFailure() {
                onrequestDone();
            }
        });
    }

    /**
     * 发送请求数据完毕后，执行的操作
     */
    private void onrequestDone() {
        if(dialog.isShowing()) {
            dialog.dismiss();
        }
        adapter.notifyDataSetChanged();
        mPullRefreshListView.onRefreshComplete();
        if(type.equals(DetialActivity.TYPE_END)) {
            AppUtils.setNullListView(adapter, bjLL, iv, R.mipmap.no_miss, bj_title, "主人太勤奋了没有错过任何推送哦！",0);
        }else if(type.equals(DetialActivity.TYPE_START)){
            AppUtils.setNullListView(adapter, bjLL, iv, R.mipmap.no_g_start, bj_title, "亲~还没有新的推送，再耐心等等吧！",0);
        }else if(type.equals(DetialActivity.TYPE_ING)){
            AppUtils.setNullListView(adapter, bjLL, iv, R.mipmap.no_g_start_in, bj_title, "亲~你来晚了没有正在进行的推送了！"+"\n"+"下次早点来！",0);
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
//        rows.clear();
        page=1;
        getDate();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getDate();
    }
    public void removeGeneralize(int pid){
        int size = rows.size();
        for(int i=0;i<size;i++){
            if(rows.get(i).pid==pid){
                rows.remove(i);
            }
        }
    }
}
