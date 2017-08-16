package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.Kz_CourseDetaiListAdapter;
import com.kzmen.sczxjf.adapter.Kz_Course_FragmentAdapter;
import com.kzmen.sczxjf.bean.kzbean.CourseListTstBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.dialog.ShareDialog;
import com.kzmen.sczxjf.popuwidow.Kz_CourseAskPopu;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.EToastUtil;
import com.kzmen.sczxjf.view.ExpandViewPager;
import com.kzmen.sczxjf.view.MyListView;
import com.kzmen.sczxjf.view.MyScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 课程详情
 */
public class CourseDetailAcitivity extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.iv_share)
    ImageView ivShare;
    @InjectView(R.id.iv_user_bg)
    ImageView ivUserBg;
    @InjectView(R.id.tab_layout)
    TabLayout tabLayout;
    @InjectView(R.id.info_viewpager)
    ExpandViewPager infoViewpager;
    @InjectView(R.id.lv_goodask)
    MyListView lvGoodask;
    @InjectView(R.id.tv_ask)
    TextView tvAsk;
    @InjectView(R.id.sv_main)
    MyScrollView svMain;
    @InjectView(R.id.kz_tiltle)
    LinearLayout llTitle;
    private String[] titles = new String[]{"阶段一", "阶段二", "阶段三", "阶段四", "阶段五"};
    private ShareDialog shareDialog;
    private Kz_Course_FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "课程详情");
        if (!setLl_title()) {
            EToastUtil.show(this, "设置标题错误");
        }
        if (!setOnScroll(R.id.sv_main)) {
            EToastUtil.show(this, "设置滑动失败");
        }
        initView();
        initData();
    }

    private void initView() {
        adapter = new Kz_Course_FragmentAdapter(getSupportFragmentManager(), CourseDetailAcitivity.this, titles);
        adapter.setTitles(titles);
        infoViewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(infoViewpager);

        //设置Tablayout
        //设置TabLayout模式 -该使用Tab数量比较多的情况
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //设置自定义Tab--加入图标的demo
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        final int currentSelectedPosition = infoViewpager.getCurrentItem();
        infoViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                View view = infoViewpager.getChildAt(currentSelectedPosition);
                int height = view.getMeasuredHeight();
                ViewGroup.LayoutParams layoutParams = (LinearLayout.LayoutParams) infoViewpager.getLayoutParams();
                layoutParams.height = height;
                infoViewpager.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<CourseListTstBean> beanlist;
    private Kz_CourseDetaiListAdapter adapter1;
    private CommonAdapter<CourseListTstBean> adapter2;

    private void initData() {
        beanlist = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
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
        adapter2 = new CommonAdapter<CourseListTstBean>(CourseDetailAcitivity.this, R.layout.kz_good_ask_item, beanlist) {
            @Override
            protected void convert(ViewHolder viewHolder, CourseListTstBean item, int position) {
                viewHolder.setText(R.id.tv_user_name, "" + item.getName());
                if(position%3==0){
                    viewHolder.getView(R.id.ll_txt).setVisibility(View.VISIBLE);
                    viewHolder.getView(R.id.ll_media).setVisibility(View.GONE);
                }else{
                    viewHolder.getView(R.id.ll_txt).setVisibility(View.GONE);
                    viewHolder.getView(R.id.ll_media).setVisibility(View.VISIBLE);
                }
            }
        };
        lvGoodask.setAdapter(adapter2);
        setListViewHeightBasedOnChildren(lvGoodask);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_course_detail_acitivity);
    }

    @OnClick({R.id.iv_share, R.id.tv_ask})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_share:
                shareDialog = new ShareDialog(this);
                shareDialog.setCancelButtonOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareDialog.dismiss();
                    }
                });
                shareDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {
                        HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
                        EToastUtil.show(CourseDetailAcitivity.this, "" + item.get("ItemText"));
                        shareDialog.dismiss();
                    }
                });
                break;
            case R.id.tv_ask:
              /*  intent = new Intent(CourseDetailAcitivity.this, KnowageAskIndexActivity.class);
                startActivity(intent);*/
                showPopFormBottom(view);
                break;
        }
    }
    private Kz_CourseAskPopu playPop;
    WindowManager.LayoutParams params;
    public void showPopFormBottom(View view) {
        playPop= new Kz_CourseAskPopu(this);
//        设置Popupwindow显示位置（从底部弹出）
        playPop.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        params = getWindow().getAttributes();
        //当弹出Popupwindow时，背景变半透明
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        playPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });

    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获得adapter
        CommonAdapter<CourseListTstBean> adapter = (CommonAdapter) listView.getAdapter();
        if (adapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            //计算总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //计算分割线高度
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        //给listview设置高度
        listView.setLayoutParams(params);
    }
}
