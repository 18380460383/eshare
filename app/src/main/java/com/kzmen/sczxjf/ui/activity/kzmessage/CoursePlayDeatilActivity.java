package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.CourseListTstBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.popuwidow.Kz_PlayListPopu;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class CoursePlayDeatilActivity extends SuperActivity {

    @InjectView(R.id.tv_media_start_time)
    TextView tvMediaStartTime;
    @InjectView(R.id.tv_media_end_time)
    TextView tvMediaEndTime;
    @InjectView(R.id.sb_play)
    SeekBar sbPlay;
    @InjectView(R.id.lv_goodask)
    MyListView lvGoodask;
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.iv_share)
    ImageView ivShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "初级课程");
        initData();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_small_talk_deatil);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    private List<CourseListTstBean> beanlist;
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
        adapter2 = new CommonAdapter<CourseListTstBean>(CoursePlayDeatilActivity.this, R.layout.kz_good_ask_item, beanlist) {
            @Override
            protected void convert(ViewHolder viewHolder, CourseListTstBean item, int position) {
                viewHolder.setText(R.id.tv_user_name, "" + item.getName());
            }
        };
        lvGoodask.setAdapter(adapter2);
        setListViewHeightBasedOnChildren(lvGoodask);
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

    WindowManager.LayoutParams params;

    public void showPopFormBottom(View view) {
        Kz_PlayListPopu takePhotoPopWin = new Kz_PlayListPopu(this);
//        设置Popupwindow显示位置（从底部弹出）
        takePhotoPopWin.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        params = getWindow().getAttributes();
        //当弹出Popupwindow时，背景变半透明
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        takePhotoPopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    @OnClick(R.id.iv_share)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_share:
                showPopFormBottom(view);
                break;
        }
    }
}
