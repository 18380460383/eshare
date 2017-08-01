package com.jiebian.adwlf.ui.activity.personal;

import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiebian.adwlf.Constants;
import com.jiebian.adwlf.R;
import com.jiebian.adwlf.bean.request.School;
import com.jiebian.adwlf.ui.activity.basic.SuperActivity;
import com.jiebian.adwlf.utils.JsonUtils;
import com.jiebian.adwlf.net.NetworkDownload;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Administrator on 2015/11/6.
 */
public class SchoolAct extends SuperActivity {
    @InjectView(R.id.school_into)
    ImageView schoolInto;
    @InjectView(R.id.into_school)
    RelativeLayout intoSchool;
    @InjectView(R.id.school_name)
    TextView name;
    @InjectView(R.id.school_xi)
    EditText schoolXi;
    @InjectView(R.id.school_zhuany)
    EditText schoolZhuany;
    @InjectView(R.id.school_nianj)
    EditText schoolNianj;
    @InjectView(R.id.school_banj)
    EditText schoolBanj;
    @InjectView(R.id.school_submit)
    Button schoolSubmit;
    private List<String> list;
    private ArrayAdapter<String> adapter;
    private AlertDialog show;
    private School bean;
    private List<School> msg;


    @Override
    public void onCreateDataForView() {
        setTitle(R.id.school_title,"填写学校信息");
        initView();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_school);
        bean = new School();
    }
    public void initView() {
        list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(SchoolAct.this, android.R.layout.simple_list_item_1, list);
        schoolSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name.getText().toString())){
                    Toast.makeText(SchoolAct.this,"请选择学校",Toast.LENGTH_SHORT).show();
                }else if(
                        TextUtils.isEmpty(schoolXi.getText().toString())||
                        TextUtils.isEmpty(schoolBanj.getText().toString())||
                        TextUtils.isEmpty(schoolNianj.getText().toString())||
                        TextUtils.isEmpty(schoolZhuany.getText().toString())
                        ){
                    Toast.makeText(SchoolAct.this,"请填写完必填信息",Toast.LENGTH_SHORT).show();
                }else{
                    bean.setBanj(schoolBanj.getText().toString());
                    bean.setNianj(schoolNianj.getText().toString());
                    bean.setZhuany(schoolZhuany.getText().toString());
                    bean.setXi(schoolXi.getText().toString());
                    Intent i=new Intent();
                    i.putExtra("school",bean);
                    setResult(RESULT_OK, i);
                    finish();
                }
            }
        });
        intoSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView view = new ListView(SchoolAct.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(SchoolAct.this).setView(view);
                view.setAdapter(adapter);
                view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        name.setText(list.get(position));
                        bean.setId(msg.get(position).getId());
                        bean.setSc_name(list.get(position));
                        show.dismiss();
                    }
                });
                show = builder.show();
            }
        });
        requestInfo();
    }
    private void requestInfo() {
        NetworkDownload.jsonPostForCode1(this, Constants.URL_GET_SCHOOL, null, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                msg = JsonUtils.getBeanList(jsonObject.optJSONArray("data"), School.class);
                list.addAll(getList());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {

            }
        });
    }


    public List<String> getList() {
        List<String> list=new ArrayList<>();
        int size = msg.size();
        for(int i=0;i<size;i++){
            list.add(msg.get(i).getSc_name());
        }
        return list;
    }
}
