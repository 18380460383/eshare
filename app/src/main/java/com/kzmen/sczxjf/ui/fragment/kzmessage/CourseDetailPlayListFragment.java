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
import com.kzmen.sczxjf.bean.kzbean.KzCoursePlayBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.kzmessage.CoursePlayDeatilActivity;
import com.kzmen.sczxjf.ui.fragment.basic.SuperFragment;
import com.kzmen.sczxjf.utils.TextUtil;
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
    MyListView lv_xiaojiang_list;
    TextView tv_xiaoj;
    private LinearLayout ll_main;
    private CourseDetailBean.StageListBean stageListBean;
    private LinearLayout ll_buy;
    private TextView tv_stage;
    private TextView tv_days;
    private TextView tv_price;
    private TextView tv_buy;
    private List<KzCoursePlayBean> beanlist;
    private CommonAdapter<KzCoursePlayBean> adapter2;
    private List<CourseDetailBean.StageListBean.XiaojiangListBean> xiaojianglist;
    private CommonAdapter<CourseDetailBean.StageListBean.XiaojiangListBean> xjAdapter;

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
        xiaojianglist = new ArrayList<>();
        initView(view);
        initData();
        return view;
    }

    private int tabPos = 0;
    private String cid = "";

    private void initView(final View view) {
        lvPlayList = (MyListView) view.findViewById(R.id.lv_play_list);
        lv_xiaojiang_list = (MyListView) view.findViewById(R.id.lv_xiaojiang_list);
        ll_main = (LinearLayout) view.findViewById(R.id.ll_main);
        ll_buy = (LinearLayout) view.findViewById(R.id.ll_buy);
        tv_days = (TextView) view.findViewById(R.id.tv_days);
        tv_stage = (TextView) view.findViewById(R.id.tv_stage);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        tv_xiaoj = (TextView) view.findViewById(R.id.tv_xiaoj);
        tv_buy = (TextView) view.findViewById(R.id.tv_buy);
        tv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stageListBean == null) {
                    return;
                }
                Map<String, String> params = new HashMap<String, String>();
                params.put("data[cid]", cid);
                params.put("data[sid]", stageListBean.getSid());
                OkhttpUtilManager.setOrder(getActivity(), "Course/addCourseStageOrder", params);
            }
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            tabPos = bundle.getInt("tabPos");
            cid = bundle.getString("cid");
            stageListBean = (CourseDetailBean.StageListBean) bundle.getSerializable("stage");
            for (CourseDetailBean.StageListBean.KejianListBean bean : stageListBean.getKejian_list()) {
                KzCoursePlayBean kzCoursePlayBean = new KzCoursePlayBean(bean.getChapter_id(), bean.getChapter_id(), bean.getChapter_name(), 0 , "", "");
                beanlist.add(kzCoursePlayBean);
                for (CourseDetailBean.StageListBean.KejianListBean.KejianBean kejianBean : bean.getKejian()) {
                    KzCoursePlayBean kzCoursePlayBean1 = new KzCoursePlayBean(kejianBean.getId(), bean.getChapter_id(), kejianBean.getTitle(), 1, kejianBean.getMedia(), kejianBean.getMedia_time());
                    beanlist.add(kzCoursePlayBean1);
                }
            }
            xiaojianglist.addAll(stageListBean.getXiaojiang_list());
            if(xiaojianglist.size()>0){
                tv_xiaoj.setVisibility(View.VISIBLE);
            }else{
                tv_xiaoj.setVisibility(View.GONE);
            }
            Log.e("tst", stageListBean.toString());
            if (stageListBean.getIsunlock() == 1) {
                ll_buy.setVisibility(View.GONE);
                ll_main.setBackgroundResource(R.color.white);
            } else {
                ll_main.setBackgroundResource(R.color.gloomy);
                tv_days.setText("" + stageListBean.getUnlock_day());//stageListBean.getUnlock_day()
                tv_price.setText(""+stageListBean.getUnlock_money());//stageListBean.getUnlock_money()
                tv_stage.setText(""+stageListBean.getStage_name());
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


    private void initData() {
        adapter2 = new CommonAdapter<KzCoursePlayBean>(getActivity(), R.layout.kz_course_detail_list_item, beanlist) {
            @Override
            protected void convert(ViewHolder viewHolder, KzCoursePlayBean item, final int position) {
                viewHolder.getView(R.id.top_line).setVisibility(View.VISIBLE);
                viewHolder.getView(R.id.bottom_line).setVisibility(View.VISIBLE);
                if(TextUtil.isEmpty(item.getMedia_time())){
                    item.setMedia_time("0");
                }
                int musicTime = Integer.valueOf(item.getMedia_time());
                int min=musicTime / 60;
                int sec=musicTime % 60;
                String  show = (min<10?"0"+min:min)+ ":" + (sec<10?"0"+sec:sec);
                if (position == 0) {
                    viewHolder.getView(R.id.top_line).setVisibility(View.INVISIBLE);
                }
                if (position == (beanlist.size() - 1)) {
                    viewHolder.getView(R.id.bottom_line).setVisibility(View.INVISIBLE);
                }
                if(item.getType()==0){
                    viewHolder.getView(R.id.iv_play_state).setVisibility(View.GONE);
                    viewHolder.getView(R.id.v_cricle_point).setVisibility(View.VISIBLE);
                    viewHolder.getView(R.id.tv_title).setVisibility(View.VISIBLE);
                    viewHolder.setText(R.id.tv_title, item.getName());
                    viewHolder.getView(R.id.ll_play_state).setVisibility(View.INVISIBLE);
                    viewHolder.getView(R.id.tv_second).setVisibility(View.INVISIBLE);
                }else{
                    viewHolder.getView(R.id.v_cricle_point).setVisibility(View.GONE);
                    viewHolder.getView(R.id.iv_play_state).setVisibility(View.GONE);
                    viewHolder.setText(R.id.tv_second, item.getName())
                    .setText(R.id.tv_time,show);
                    viewHolder.getView(R.id.tv_second).setVisibility(View.VISIBLE);
                    viewHolder.getView(R.id.tv_title).setVisibility(View.INVISIBLE);
                    viewHolder.getView(R.id.ll_play_state).setVisibility(View.VISIBLE);
                }
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), CoursePlayDeatilActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("cid",cid);
                        bundle.putSerializable("stage", stageListBean);
                        bundle.putInt("position", position);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        };
        lvPlayList.setAdapter(adapter2);
        xjAdapter = new CommonAdapter<CourseDetailBean.StageListBean.XiaojiangListBean>(getActivity(), R.layout.kz_xiaojiang_list_item, xiaojianglist) {
            @Override
            protected void convert(ViewHolder viewHolder, CourseDetailBean.StageListBean.XiaojiangListBean item, final int position) {
                int musicTime = Integer.valueOf(item.getMedia_time());
                int min=musicTime / 60;
                int sec=musicTime % 60;
                String  show = (min<10?"0"+min:min)+ ":" + (sec<10?"0"+sec:sec);
                viewHolder.setText(R.id.tv_xiaojiang_title1, item.getTitle())
                        .setText(R.id.tv_xiaogjiangtime1, show);
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), CoursePlayDeatilActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("cid",cid);
                        bundle.putSerializable("stage", stageListBean);
                        bundle.putInt("position", position);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        };
        lv_xiaojiang_list.setAdapter(xjAdapter);
    }

}
