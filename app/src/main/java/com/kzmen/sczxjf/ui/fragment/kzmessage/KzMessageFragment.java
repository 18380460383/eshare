package com.kzmen.sczxjf.ui.fragment.kzmessage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.KzActivGridAdapter;
import com.kzmen.sczxjf.adapter.KzMainColumnAdapter;
import com.kzmen.sczxjf.util.EToastUtil;
import com.kzmen.sczxjf.view.ExPandGridView;
import com.kzmen.sczxjf.view.banner.BannerLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 卡掌门--掌信端
 */
public class KzMessageFragment extends Fragment {
    @InjectView(R.id.bl_main_banner)
    BannerLayout blMainBanner;
    @InjectView(R.id.gv_column)
    ExPandGridView gvColumn;
    @InjectView(R.id.iv_user_head)
    ImageView ivUserHead;
    @InjectView(R.id.ll_user_head)
    LinearLayout llUserHead;
    @InjectView(R.id.tv_course_ex)
    TextView tvCourseEx;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_user_identity)
    TextView tvUserIdentity;
    @InjectView(R.id.tv_user_name)
    TextView tvUserName;
    @InjectView(R.id.tv_media_time)
    TextView tvMediaTime;
    @InjectView(R.id.iv_media_state)
    ImageView ivMediaState;
    @InjectView(R.id.tv_xiaojiang_time1)
    TextView tvXiaojiangTime1;
    @InjectView(R.id.tv_xiaojiang_ex1)
    TextView tvXiaojiangEx1;
    @InjectView(R.id.tv_xiaojiang_time2)
    TextView tvXiaojiangTime2;
    @InjectView(R.id.tv_xiaojiang_ex2)
    TextView tvXiaojiangEx2;
    @InjectView(R.id.tv_more_course)
    TextView tvMoreCourse;
    @InjectView(R.id.ll_more_course)
    LinearLayout llMoreCourse;
    @InjectView(R.id.tv_ask_title1)
    TextView tvAskTitle1;
    @InjectView(R.id.iv_ask_head2)
    ImageView ivAskHead2;
    @InjectView(R.id.tv_ask_listen_state2)
    TextView tvAskListenState2;
    @InjectView(R.id.tv_ask_listen_count1)
    TextView tvAskListenCount1;
    @InjectView(R.id.tv_ask_listen_type1)
    TextView tvAskListenType1;
    @InjectView(R.id.tv_ask_listen_name1)
    TextView tvAskListenName1;
    @InjectView(R.id.tv_ask_title2)
    TextView tvAskTitle2;
    @InjectView(R.id.iv_ask_head1)
    ImageView ivAskHead1;
    @InjectView(R.id.tv_ask_listen_state1)
    TextView tvAskListenState1;
    @InjectView(R.id.tv_ask_listen_count2)
    TextView tvAskListenCount2;
    @InjectView(R.id.tv_ask_listen_type2)
    TextView tvAskListenType2;
    @InjectView(R.id.tv_ask_listen_name2)
    TextView tvAskListenName2;
    @InjectView(R.id.tv_more_ask)
    TextView tvMoreAsk;
    @InjectView(R.id.ll_more_ask)
    LinearLayout llMoreAsk;
    @InjectView(R.id.gv_more_activ)
    ExPandGridView gvMoreActiv;
    private View view = null;
    private BannerLayout bl_main_banner;
    private List<String> urlList;
    private GridView gv_column;
    private List<String>listTst;
    private List<String>listTstActiv;
    private KzMainColumnAdapter kzMainColumnAdapter;
    private KzActivGridAdapter kzActivGridAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_kz_message, container, false);
        }
        ButterKnife.inject(this, view);
        initView();
        return view;
    }

    private void initView() {
        blMainBanner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                EToastUtil.show(getActivity(), "" + position);
            }
        });
        initData();
    }

    private void initData() {
        urlList = new ArrayList<>();
        listTst=new ArrayList<>();
        listTstActiv=new ArrayList<>();
        urlList.add("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        urlList.add("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        urlList.add("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        urlList.add("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        urlList.add("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        blMainBanner.setViewUrls(urlList);
        listTst.add("课程");
        listTst.add("问答");
        listTst.add("测评");
        listTst.add("活动");
        listTst.add("案例");
        kzMainColumnAdapter=new KzMainColumnAdapter(getActivity(),listTst);
        gvColumn.setAdapter(kzMainColumnAdapter);
        listTstActiv.add("测试1");
        listTstActiv.add("测试1");
        listTstActiv.add("测试1");
        listTstActiv.add("测试1");
        kzActivGridAdapter=new KzActivGridAdapter(getActivity(),listTstActiv);
        gvMoreActiv.setAdapter(kzActivGridAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.ll_more_course, R.id.ll_more_ask})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.ll_more_course:
                EToastUtil.show(getActivity(), "更多课程");
                break;
            case R.id.ll_more_ask:
                EToastUtil.show(getActivity(), "更多问答");
                break;
        }
    }
}
