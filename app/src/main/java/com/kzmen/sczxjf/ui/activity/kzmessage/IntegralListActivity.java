package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

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
    @InjectView(R.id.tv_mingc)
    TextView tvMingc;
    @InjectView(R.id.lv_integral_list)
    ListView lvIntegralList;
    private CommonAdapter<String> adapter;
    private List<String> data_list;
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
        mHandler.sendEmptyMessageDelayed(1,2*1000);
    }
    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mLayout.onDone();
            initView();
        }
    };

    private void initView() {
        data_list=new ArrayList<>();
        for (int i = 1; i <=50 ; i++) {
            data_list.add("测试姓名："+i);
        }
        adapter=new CommonAdapter<String>(this,R.layout.kz_integral_list_item,data_list) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.tv_name,item);
                viewHolder.setText(R.id.tv_mingc,""+position);
                if(position>3){
                    viewHolder.getView(R.id.iv_sign).setVisibility(View.INVISIBLE);
                }else{
                    viewHolder.getView(R.id.iv_sign).setVisibility(View.VISIBLE);
                }
                viewHolder.setText(R.id.tv_grade,""+(100-position));
            }
        };
        lvIntegralList.setAdapter(adapter);
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_integral_list);
    }
}
