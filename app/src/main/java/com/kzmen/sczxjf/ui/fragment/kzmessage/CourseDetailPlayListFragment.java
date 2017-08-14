package com.kzmen.sczxjf.ui.fragment.kzmessage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.CourseListTstBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.ui.fragment.basic.SuperFragment;
import com.kzmen.sczxjf.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pjj18 on 2017/8/14.
 */

public class CourseDetailPlayListFragment extends SuperFragment {
    MyListView lvPlayList;

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
        initView(view);
        initData();
        return view;
    }

    private int tabPos = 0;

    private void initView(final View view) {
        lvPlayList = (MyListView) view.findViewById(R.id.lv_play_list);
        Bundle bundle = getArguments();
        if (bundle != null) {
            tabPos = bundle.getInt("tabPos");
        } else {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private List<CourseListTstBean> beanlist;
    private CommonAdapter<CourseListTstBean> adapter2;

    private void initData() {
        beanlist = new ArrayList<>();
        for (int i = tabPos; i < 6 + tabPos; i++) {
            CourseListTstBean bean = new CourseListTstBean();
            if (i % 3 == 0) {
                bean.setType(0);
            } else {
                bean.setType(1);
            }
            bean.setName("测试" + i);
            bean.setTime("03:0" + i);
            beanlist.add(bean);
        }
        adapter2 = new CommonAdapter<CourseListTstBean>(getActivity(), R.layout.kz_course_detail_list_item, beanlist) {
            @Override
            protected void convert(ViewHolder viewHolder, CourseListTstBean item, int position) {
                if (item.getType() == 0) {
                    viewHolder.getView(R.id.iv_play_state).setVisibility(View.INVISIBLE);
                    viewHolder.getView(R.id.v_cricle_point).setVisibility(View.VISIBLE);
                    viewHolder.getView(R.id.tv_title).setVisibility(View.VISIBLE);
                    viewHolder.setText(R.id.tv_title, item.getName());
                    viewHolder.getView(R.id.ll_play_state).setVisibility(View.INVISIBLE);
                    viewHolder.getView(R.id.tv_second).setVisibility(View.INVISIBLE);
                } else {
                    viewHolder.getView(R.id.v_cricle_point).setVisibility(View.INVISIBLE);
                    viewHolder.getView(R.id.iv_play_state).setVisibility(View.INVISIBLE);
                    viewHolder.setText(R.id.tv_second, item.getName());
                    viewHolder.getView(R.id.tv_second).setVisibility(View.VISIBLE);
                    viewHolder.getView(R.id.tv_title).setVisibility(View.INVISIBLE);
                    viewHolder.getView(R.id.ll_play_state).setVisibility(View.VISIBLE);
                }
            }
        };
        lvPlayList.setAdapter(adapter2);
    }

}
