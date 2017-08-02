package com.cardholder.adwlf.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cardholder.adwlf.AppContext;
import com.cardholder.adwlf.Constants;
import com.cardholder.adwlf.R;
import com.cardholder.adwlf.adapter.ListEnterAdapter;
import com.cardholder.adwlf.base.BaseActivity;
import com.cardholder.adwlf.bean.ListEnterEntity;
import com.cardholder.adwlf.util.EToastUtil;
import com.cardholder.adwlf.util.EshareLoger;
import com.cardholder.adwlf.util.ListViewHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Describe:
 * Created by FuPei on 2016/3/15.
 */
public class SearchEnterActivity extends BaseActivity implements ListViewHelper.ListHelper<ListEnterEntity> {

    @InjectView(R.id.listview)
    public PullToRefreshListView listView;
    @InjectView(R.id.title_back)
    public ImageView iv_back;
    @InjectView(R.id.title_name)
    public TextView tv_title;
    @InjectView(R.id.et_search)
    public EditText et_search;
    @InjectView(R.id.btn_search)
    public TextView btn_search;

    private ListViewHelper<ListEnterEntity> helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_enter);
        initView();
        initListener();
    }

    private void initView() {
        tv_title.setText("搜索");
        helper = new ListViewHelper<>(this, listView, this);
        helper.setEmptText("暂无搜索结果");
        helper.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
    }

    private void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_search.getText().toString().length() > 0) {
                    helper.refresh();
                } else {
                    EToastUtil.show(SearchEnterActivity.this, "请输入信息!");
                }
            }
        });
    }

    @Override
    public BaseAdapter getActAdatper(List<ListEnterEntity> entity) {
        return new ListEnterAdapter(this, entity);
    }

    @Override
    public RequestParams getParam() {
        RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getPEUser().getUid());
        params.put("limit", 10);
        params.put("keywords", et_search.getText().toString());
        return params;
    }

    @Override
    public String getUrl() {
        return Constants.URL_LIST_ENTER;
    }

    @Override
    public String getPageKey() {
        return "page";
    }

    @Override
    public List<ListEnterEntity> getData(JSONObject json) {
        EshareLoger.logI("getData:\n" + json.toString());
        List<ListEnterEntity> list = new ArrayList<>();
        if (json.optString("code").equals("1")) {
            JSONArray array = json.optJSONArray("data");
            if (array != null && array.length() > 0) {
                list = new Gson().fromJson(array.toString(), new TypeToken<List<ListEnterEntity>>() {
                }.getType());
                return list;
            }
        }
        return list;
    }

    @Override
    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EshareLoger.logI("点击了:" + position);
                String url = helper.getListData().get(position - 1).getUrl();
                Intent intent = new Intent(SearchEnterActivity.this, BaseWebActivity.class);
                intent.putExtra(BaseWebActivity.EXTRA_URL, url);
                intent.putExtra(BaseWebActivity.EXTRA_TITLE, "企业详情");
                startActivity(intent);
            }
        };
    }
}
