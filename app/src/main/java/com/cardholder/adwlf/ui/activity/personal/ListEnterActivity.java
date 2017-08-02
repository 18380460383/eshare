package com.cardholder.adwlf.ui.activity.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cardholder.adwlf.AppContext;
import com.cardholder.adwlf.Constants;
import com.cardholder.adwlf.adapter.ListEnterAdapter;
import com.cardholder.adwlf.base.BaseListActivity;
import com.cardholder.adwlf.bean.ListEnterEntity;
import com.cardholder.adwlf.ui.activity.BaseWebActivity;
import com.cardholder.adwlf.ui.activity.SearchEnterActivity;
import com.cardholder.adwlf.util.EshareLoger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:企业列表
 * Created by FuPei on 2016/3/10.
 */
public class ListEnterActivity extends BaseListActivity<ListEnterEntity> {

    @Override
    public BaseAdapter getActAdatper(List<ListEnterEntity> entity) {
        return new ListEnterAdapter(this, entity);
    }

    @Override
    public RequestParams getParam() {
        RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getPEUser().getUid());
//        params.put("uid", "69");
        params.put("limit", 10);
        return params;
    }

    @Override
    public String getUrl() {
        return Constants.URL_LIST_ENTER;
    }

    @Override
    public void onClickBack() {
        finish();
    }

    @Override
    public String getActTitle() {
        return "企业列表";
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
                String url = getListData().get(position - 1).getUrl();
                Intent intent = new Intent(ListEnterActivity.this, BaseWebActivity.class);
                intent.putExtra(BaseWebActivity.EXTRA_URL, url);
                startActivity(intent);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("没有数据");
        setEmptyView(textView);
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListEnterActivity.this, SearchEnterActivity.class);
                startActivity(intent);
            }
        });
    }
}
