package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.Kz_CourseDetaiListAdapter;
import com.kzmen.sczxjf.adapter.Kz_Course_FragmentAdapter;
import com.kzmen.sczxjf.bean.kzbean.CourseDetailBean;
import com.kzmen.sczxjf.bean.kzbean.CourseListBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.dialog.ShareDialog;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.popuwidow.Kz_CourseAskPopu;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.EToastUtil;
import com.kzmen.sczxjf.view.ExpandViewPager;
import com.kzmen.sczxjf.view.ExpandableTextView;
import com.kzmen.sczxjf.view.MyListView;
import com.kzmen.sczxjf.view.loading.LoadingView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    NestedScrollView svMain;
    @InjectView(R.id.kz_tiltle)
    LinearLayout llTitle;
    @InjectView(R.id.iv_collect)
    ImageView ivCollect;
    @InjectView(R.id.tv_time)
    TextView tvTime;
    @InjectView(R.id.textView4)
    TextView textView4;
    @InjectView(R.id.ll_hudong)
    LinearLayout llHudong;
    @InjectView(R.id.ll_xiaojianghudong)
    LinearLayout llXiaojianghudong;
    @InjectView(R.id.expandable_text)
    TextView expandableText;
    @InjectView(R.id.ex_message)
    TextView exMessage;
    @InjectView(R.id.expand_text_view)
    ExpandableTextView expandTextView;
    @InjectView(R.id.tv_course_stage)
    TextView tvCourseStage;
    @InjectView(R.id.tv_course_section)
    TextView tvCourseSection;
    @InjectView(R.id.tv_views)
    TextView tvViews;
    @InjectView(R.id.tv_questions_desc)
    TextView tvQuestionsDesc;
    @InjectView(R.id.btn_error)
    Button btnError;
    @InjectView(R.id.tv_loading_message)
    TextView tvLoadingMessage;
    @InjectView(R.id.loadView)
    LoadingView loadView;
    @InjectView(R.id.ll_loading)
    LinearLayout llLoading;
    private String[] titles = new String[]{"阶段一", "阶段二", "阶段三", "阶段四", "阶段五"};
    private ShareDialog shareDialog;
    private Kz_Course_FragmentAdapter adapter;
    // private CustomLoadingLayout mLayout; //SmartLoadingLayout对象
    private String cid = "";
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 数据下载完成，转换状态，显示内容视图
            switch (msg.what) {
                case 0:
                    mLayout.onError();
                    break;
                case 1:
                    mLayout.onDone();
                    break;
                default:
                    mLayout.onEmpty();
                    break;
            }
        }
    };
    private CourseDetailBean courseDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "课程详情");
        setOnloading(R.id.ll_content);
        mLayout.onLoading();
        initData();
    }

    private void initView() {
        if (courseDetailBean != null) {
            if (courseDetailBean.getIszan().equals("1")) {
                ivCollect.setBackgroundResource(R.drawable.btn_collect_current);
            }
            Glide.with(this).load(courseDetailBean.getBanner()).placeholder(R.drawable.icon_image_normal)
                    .into(ivUserBg);
            tvTime.setText(courseDetailBean.getCourse_time() + "分钟语音");
            if (courseDetailBean.getIsquestion().equals("0")) {
                llHudong.setVisibility(View.GONE);
            }
            if (courseDetailBean.getIsxiaojiang().equals("0")) {
                llXiaojianghudong.setVisibility(View.GONE);
            }

            tvCourseStage.setText("共" + courseDetailBean.getCourse_stage() + "阶段，");
            tvCourseSection.setText(courseDetailBean.getCourse_section() + "节课程");
            tvViews.setText(courseDetailBean.getViews() + "人学习");
            tvAsk.setText(courseDetailBean.getQuestions_button());
            tvQuestionsDesc.setText(courseDetailBean.getQuestions_desc());
            expandTextView.setText(courseDetailBean.getDescribe());
        }


        adapter = new Kz_Course_FragmentAdapter(getSupportFragmentManager(), CourseDetailAcitivity.this, titles);
        adapter.setTitles(titles);
        infoViewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(infoViewpager);
        //设置Tablayout
        //设置TabLayout模式 -该使用Tab数量比较多的情况
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置自定义Tab--加入图标的demo
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
            //tab.getCustomView().setBackgroundColor(Color.argb(255,255,0,0));
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

       /* ExpandableTextView expTv1 = (ExpandableTextView) findViewById(R.id.expand_text_view);
        expTv1.setText(getString(R.string.tst));*/
    }

    private List<CourseListBean> beanlist;
    private Kz_CourseDetaiListAdapter adapter1;
    private CommonAdapter<CourseListBean> adapter2;

    private void initData() {
        beanlist = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("data[cid]", cid);
        OkhttpUtilManager.postNoCacah(this, "Course/getCourseShow", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", data);
                mHandler.sendEmptyMessage(1);//CourseDetailBean
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    courseDetailBean = gson.fromJson(object.getString("data"), CourseDetailBean.class);
                    Log.e("tst", courseDetailBean.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                initView();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", msg);
                mHandler.sendEmptyMessage(0);
            }
        });
        adapter2 = new CommonAdapter<CourseListBean>(CourseDetailAcitivity.this, R.layout.kz_good_ask_item, beanlist) {
            @Override
            protected void convert(ViewHolder viewHolder, CourseListBean item, int position) {
                viewHolder.setText(R.id.tv_user_name, "" + item.getName());
                if (position % 3 == 0) {
                    viewHolder.getView(R.id.ll_txt).setVisibility(View.VISIBLE);
                    viewHolder.getView(R.id.ll_media).setVisibility(View.GONE);
                } else {
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
        cid = getIntent().getExtras().getString("cid");
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
        playPop = new Kz_CourseAskPopu(this);
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
        CommonAdapter<CourseListBean> adapter = (CommonAdapter) listView.getAdapter();
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
