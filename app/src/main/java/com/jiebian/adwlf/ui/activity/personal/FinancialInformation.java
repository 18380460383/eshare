package com.jiebian.adwlf.ui.activity.personal;


import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiebian.adwlf.EnConstants;
import com.jiebian.adwlf.R;
import com.jiebian.adwlf.adapter.FinanceAdapter;
import com.jiebian.adwlf.ebean.Finance;
import com.jiebian.adwlf.net.EnWebUtil;
import com.jiebian.adwlf.ui.activity.basic.ListViewActivity;
import com.jiebian.adwlf.utils.AESUtils;
import com.jiebian.adwlf.utils.AppUtils;
import com.jiebian.adwlf.utils.JsonUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 财务记录
 * Created by Administrator on 2015/10/30.
 */
public class FinancialInformation extends ListViewActivity{
    @InjectView(R.id._record_listview)
     PullToRefreshListView listview;
    private List<Finance> list;
    private FinanceAdapter adapter;
    private  float y1 = 0;
    @InjectView(R.id.bj_ll)
    LinearLayout bjLL;
    @InjectView(R.id.bj_null_iv)
    ImageView iv;
    @InjectView(R.id.bi_title)
    TextView bj_title;
    private int page=1;

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.fin_info_title, "财务记录");
        initView();
    }

    @Override
    public void setThisContentView() {

        setContentView(R.layout.activity_financial_information);
    }


    public void initView() {
        list=new ArrayList<>();
        adapter=new FinanceAdapter(this,list);
        setmPullRefreshListView(listview, adapter);
        setADD();
    }

    public void getData() {

            RequestParams requestParams = new RequestParams();
            requestParams.put("page", page);
            requestParams.put("number", 10 + "");
        showProgressDialog(null);
        EnWebUtil.getInstance().post(this, EnConstants.URL_POST_FINANCIAL_RECORD, requestParams, new EnWebUtil.AesListener() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String data1 = AESUtils.decrypt(jsonObject.optString("data"));
                    System.out.println(data1);
                    List<Finance> data = JsonUtils.getBeanList(new JSONArray(data1), Finance.class);
                    if (page == 1) {
                        list.clear();
                    }
                    list.addAll(data);
                    listview.onRefreshComplete();
                    adapter.notifyDataSetChanged();
                    AppUtils.setNullListView(adapter, bjLL, iv, R.mipmap.bj_recordrelay, bj_title, "你还没有任何财务记录", 1);
                    page++;
                }catch (JSONException e){

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    dismissProgressDialog();
                }

            }

            @Override
            public void onFail(int code, String result) {
                dismissProgressDialog();
                listview.onRefreshComplete();
                adapter.notifyDataSetChanged();
                AppUtils.setNullListView(adapter, bjLL, iv, R.mipmap.bj_recordrelay, bj_title, "你还没有任何财务记录", 1);
            }

            @Override
            public void onWebError() {
                dismissProgressDialog();
                listview.onRefreshComplete();
                adapter.notifyDataSetChanged();
                AppUtils.setNullListView(adapter, bjLL, iv, R.mipmap.bj_recordrelay, bj_title, "你还没有任何财务记录", 1);
            }
        });


    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page=1;
        getData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            getData();
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }
}
