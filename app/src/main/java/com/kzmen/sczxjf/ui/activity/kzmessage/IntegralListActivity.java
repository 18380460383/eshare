package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.All_Rank;
import com.kzmen.sczxjf.bean.kzbean.RankListBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

import static com.kzmen.sczxjf.R.id.tv_mingc;

/**
 * 积分榜单
 */
public class IntegralListActivity extends SuperActivity {
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(tv_mingc)
    TextView tvMingc;
    @InjectView(R.id.lv_integral_list)
    ListView lvIntegralList;
    private CommonAdapter<All_Rank> adapter;
    private List<All_Rank> data_list;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "积分排行");
        setOnloading(R.id.ll_content);
        mLayout.onLoading();
       // mHandler.sendEmptyMessageDelayed(1,2*1000);
        initData();
    }

    private void initData() {
        OkhttpUtilManager.postNoCacah(this, "", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    JSONObject object=new JSONObject(data);
                    Gson gson=new Gson();
                    RankListBean bean=gson.fromJson(object.getString("data"),RankListBean.class);
                    Log.e("tst",bean.toString());
                    tvMingc.setText(bean.getMe_rank().getRank());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                mHandler.sendEmptyMessage(0);
            }
        });
    }

    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    mLayout.onError();
                    break;
                case 1:
                    mLayout.onDone();
                    break;
                case 2:
                    mLayout.onError();
                    break;
            }
            mLayout.onDone();
            initView();
        }
    };

    private void initView() {
        data_list=new ArrayList<>();

        adapter=new CommonAdapter<All_Rank>(this,R.layout.kz_integral_list_item,data_list) {
            @Override
            protected void convert(ViewHolder viewHolder, All_Rank item, int position) {
                viewHolder.setText(R.id.tv_name,item.getUsername())
                .setText(R.id.tv_mingc,item.getRank())
                .setText(R.id.tv_grade,item.getAll_score());
                viewHolder.setText(tv_mingc,""+position);
                if(position>3){
                    viewHolder.getView(R.id.iv_sign).setVisibility(View.INVISIBLE);
                }else{
                    viewHolder.getView(R.id.iv_sign).setVisibility(View.VISIBLE);
                }
            }
        };
        lvIntegralList.setAdapter(adapter);
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_integral_list);
    }
}
