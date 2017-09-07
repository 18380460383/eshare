package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.view.MyListView;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;

/**
 * 知识问答
 */
public class KnowageAskIndexActivity extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.lv_aks)
    MyListView lvAks;
    @InjectView(R.id.lv_ask_item)
    MyListView lv_ask_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "知识问答");
        getAskItem();
    }
    private void getAskItem(){
        Map<String,String>params=new HashMap<>();
        params.put("data[limit]","2");
        OkhttpUtilManager.postNoCacah(this, "Interlocution/getInterlocutionType", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst",data);

            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst",msg);
            }
        });
    }
    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_knowage_ask);
    }


    /*@OnClick({R.id.tv_ask_xy, R.id.tv_ask_yx})
    public void onViewClicked(View view) {
        Intent intent=null;
        switch (view.getId()){
            case R.id.tv_ask_xy:
                intent=new Intent(KnowageAskIndexActivity.this,KnowageAskActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_ask_yx:
                intent=new Intent(KnowageAskIndexActivity.this,KnowageAskSheqActivity.class);
                startActivity(intent);
                break;
        }
    }*/
}
