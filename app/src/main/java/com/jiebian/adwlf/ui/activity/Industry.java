package com.jiebian.adwlf.ui.activity;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jiebian.adwlf.Constants;
import com.jiebian.adwlf.R;
import com.jiebian.adwlf.adapter.EtypeAdapter;
import com.jiebian.adwlf.adapter.IndustryAdapter;
import com.jiebian.adwlf.bean.IIData;
import com.jiebian.adwlf.ebean.EType;
import com.jiebian.adwlf.ui.activity.basic.SuperActivity;
import com.jiebian.adwlf.utils.JsonUtils;
import com.jiebian.adwlf.net.NetworkDownload;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/12/14.
 */
public class Industry extends SuperActivity {
    @InjectView(R.id.industry_listview)
    ListView industryListview;
    @InjectView(R.id.indutry_ok)
    TextView indutrySubmit;
    private IndustryAdapter industryadapter;
    private EtypeAdapter etypeadapter;
    private List<EType> etypelist;
    private List<com.jiebian.adwlf.ebean.Industry> industryList;
    private int flag;

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.get_industry_title,"选择行业");
        initView();
        getData();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_industry);
        ButterKnife.inject(this);
         flag = getIntent().getExtras().getInt("flag");
        industryList=new ArrayList<>();
        etypelist=new ArrayList<>();

    }


    public void initView() {
        indutrySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etypelist.size()>0){
                    IIData.getIIData().setEtypelist(etypelist);
                }
                if(industryList.size()>0){
                    IIData.getIIData().setIndustryList(industryList);
                }
                finish();
            }
        });
        switch (flag){
            case 0:
                initeType();
                break;
            case 1:
                initeIndustry();
                break;
        }
    }

    private void initeType() {
        etypeadapter=new EtypeAdapter(etypelist,this);
        industryListview.setAdapter(etypeadapter);

    }private void initeIndustry() {
        industryadapter=new IndustryAdapter(industryList,this);
        industryListview.setAdapter(industryadapter);
    }

    public void getData() {
        switch (flag){
            case 0:
                getETypeData();
                break;
            case 1:
                getIndustry();
                break;
        }


    }

    private void getETypeData() {
        List<EType> etypelist = IIData.getIIData().getEtypelist();
        if(null!= etypelist&&etypelist.size()>0){
            this.etypelist.addAll(etypelist);
            etypeadapter.notifyDataSetChanged();
        }else {
            NetworkDownload.jsonGetForCode1(this, Constants.URL_SERVER_INDUSTRY, null, new NetworkDownload.NetworkDownloadCallBackJson() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                    JSONArray array = jsonObject.optJSONArray("data");
                    Industry.this.etypelist.addAll(JsonUtils.getBeanList(array, EType.class));
                    etypeadapter.notifyDataSetChanged();
                }
                @Override
                public void onFailure() {

                }
            });
        }
    }
    private void getIndustry(){
        List<com.jiebian.adwlf.ebean.Industry> industryList = IIData.getIIData().getIndustryList();
        if(null!= industryList&&industryList.size()>0){
            this.industryList.addAll(industryList);
        }else{
            NetworkDownload.jsonGetForCode1(this, Constants.URL_SERVER_INTEREST, null, new NetworkDownload.NetworkDownloadCallBackJson() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                    Industry.this.industryList.addAll(JsonUtils.getBeanList(jsonObject.optJSONArray("data"), com.jiebian.adwlf.ebean.Industry.class));
                    industryadapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure() {

                }
            });
        }
    }

}
