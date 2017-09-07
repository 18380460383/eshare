package com.kzmen.sczxjf.ui.fragment.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.CourseDetailBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.kzmessage.CoursePlayDeatilActivity;
import com.kzmen.sczxjf.ui.fragment.basic.SuperFragment;
import com.kzmen.sczxjf.view.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pjj18 on 2017/8/14.
 */

public class CourseDetailPlayListFragment extends SuperFragment {
    MyListView lvPlayList;
    private LinearLayout ll_main;
    private CourseDetailBean.StageListBean stageListBean;
    private LinearLayout ll_buy;
    private TextView tv_stage;
    private TextView tv_days;
    private TextView tv_price;
    private TextView tv_buy;

    @Override
    protected void lazyLoad() {

    }

    public CourseDetailPlayListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kz_course_detail_play_list, container, false);
        beanlist = new ArrayList<>();
        initView(view);
        initData();
        return view;
    }

    private int tabPos = 0;
    private String cid="";
    private void initView(final View view) {
        lvPlayList = (MyListView) view.findViewById(R.id.lv_play_list);
        ll_main = (LinearLayout) view.findViewById(R.id.ll_main);
        ll_buy = (LinearLayout) view.findViewById(R.id.ll_buy);
        tv_days = (TextView) view.findViewById(R.id.tv_days);
        tv_stage = (TextView) view.findViewById(R.id.tv_stage);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        tv_buy = (TextView) view.findViewById(R.id.tv_buy);
        tv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stageListBean == null) {
                    return;
                }
                /*Intent intent = new Intent(getActivity(), PayTypeAcitivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("price", stageListBean.getUnlock_money());
                startActivity(intent);*/
                Map<String,String>params=new HashMap<String, String>();
                params.put("data[cid]",cid);
                params.put("data[sid]",stageListBean.getSid());
                OkhttpUtilManager.setOrder(getActivity(),"Course/addCourseStageOrder",params);
            }
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            tabPos = bundle.getInt("tabPos");
            cid = bundle.getString("cid");
            stageListBean = (CourseDetailBean.StageListBean) bundle.getSerializable("stage");
            beanlist.addAll(stageListBean.getKejian_list());
            Log.e("tst", stageListBean.toString());
            if (stageListBean.getIsunlock() != 1) {
                ll_buy.setVisibility(View.GONE);
                ll_main.setBackgroundResource(R.color.white);
            } else {
                ll_main.setBackgroundResource(R.color.gloomy);
                tv_days.setText("" + tabPos);//stageListBean.getUnlock_day()
                tv_price.setText(stageListBean.getUnlock_money());
                tv_stage.setText("阶段1");
            }
        }
        if (tabPos == 0) {
            return;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private List<CourseDetailBean.StageListBean.KejianListBean> beanlist;
    private CommonAdapter<CourseDetailBean.StageListBean.KejianListBean> adapter2;

    private void initData() {
        adapter2 = new CommonAdapter<CourseDetailBean.StageListBean.KejianListBean>(getActivity(), R.layout.kz_course_detail_list_item, beanlist) {
            @Override
            protected void convert(ViewHolder viewHolder, CourseDetailBean.StageListBean.KejianListBean item, final int position) {
                viewHolder.getView(R.id.top_line).setVisibility(View.VISIBLE);
                viewHolder.getView(R.id.bottom_line).setVisibility(View.VISIBLE);
                if (position == 0) {
                    viewHolder.getView(R.id.top_line).setVisibility(View.INVISIBLE);
                }
                if (position == (beanlist.size() - 1)) {
                    viewHolder.getView(R.id.bottom_line).setVisibility(View.INVISIBLE);
                }
                if (position % 3 == 0) {
                    viewHolder.getView(R.id.iv_play_state).setVisibility(View.GONE);
                    viewHolder.getView(R.id.v_cricle_point).setVisibility(View.VISIBLE);
                    viewHolder.getView(R.id.tv_title).setVisibility(View.VISIBLE);
                    viewHolder.setText(R.id.tv_title, item.getChapter_name());
                    viewHolder.getView(R.id.ll_play_state).setVisibility(View.INVISIBLE);
                    viewHolder.getView(R.id.tv_second).setVisibility(View.INVISIBLE);
                } else {
                    viewHolder.getView(R.id.v_cricle_point).setVisibility(View.GONE);
                    viewHolder.getView(R.id.iv_play_state).setVisibility(View.GONE);
                    viewHolder.setText(R.id.tv_second, item.getTitle());
                    viewHolder.getView(R.id.tv_second).setVisibility(View.VISIBLE);
                    viewHolder.getView(R.id.tv_title).setVisibility(View.INVISIBLE);
                    viewHolder.getView(R.id.ll_play_state).setVisibility(View.VISIBLE);
                }
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), CoursePlayDeatilActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("stage", stageListBean);
                        bundle.putInt("position", position);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        };
        lvPlayList.setAdapter(adapter2);
    }

}
