package com.jiebian.adwlf.ui.activity.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.jiebian.adwlf.Constants;
import com.jiebian.adwlf.adapter.PushRoleAdapter;
import com.jiebian.adwlf.base.BaseListActivity;
import com.jiebian.adwlf.bean.PushRoleBean;
import com.jiebian.adwlf.util.EshareLoger;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.List;

/**
 * Describe:查看转发记录
 * Created by FuPei on 2016/2/25.
 */
public class ListResourceActivity extends BaseListActivity<PushRoleBean.PushEntity> {

    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EshareLoger.logI("pid = " + pid);
    }

    @Override
    public BaseAdapter getActAdatper(List<PushRoleBean.PushEntity> entity) {
        return new PushRoleAdapter(this, entity);
    }

    @Override
    public RequestParams getParam() {
        RequestParams params = new RequestParams();
        pid = "223";
        //pid = getIntent().getStringExtra(Generalization_Details.PID);
        params.put("pid", pid);
        EshareLoger.logI("getParam():\npid = " + pid);
        params.put("limit", "5");
        return params;
    }

    @Override
    public String getUrl() {
        return Constants.URL_PUSH_ROLES;
    }

    @Override
    public void onClickBack() {
        finish();
    }

    @Override
    public String getActTitle() {
        return "转发列表";
    }

    @Override
    public String getPageKey() {
        return "page";
    }

    @Override
    public List<PushRoleBean.PushEntity> getData(JSONObject jsonObject) {

        PushRoleBean msgs = new Gson().fromJson(jsonObject.toString(), PushRoleBean.class);
        EshareLoger.logI("size= " + msgs.getData().size());
        return msgs.getData();
    }

    @Override
    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EshareLoger.logI("position = " + position);
            }
        };
    }

}
