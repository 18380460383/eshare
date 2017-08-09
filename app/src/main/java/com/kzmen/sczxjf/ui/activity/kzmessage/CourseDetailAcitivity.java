package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.Kz_CourseDetaiListAdapter;
import com.kzmen.sczxjf.bean.kzbean.CourseListTstBean;
import com.kzmen.sczxjf.dialog.ShareDialog;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.EToastUtil;

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
    @InjectView(R.id.lv_course)
    ListView lvCourse;
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "课程详情");
        initData();
    }
    private List<CourseListTstBean>beanlist;
    private Kz_CourseDetaiListAdapter adapter;
    private void initData() {
        beanlist=new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            CourseListTstBean bean=new CourseListTstBean();
            if(i%3==0){
                bean.setType(0);
            }else{
                bean.setType(1);
            }
            bean.setName("测试"+i);
            bean.setTime("03:0"+i);
            beanlist.add(bean);
        }
        adapter=new Kz_CourseDetaiListAdapter(CourseDetailAcitivity.this,beanlist);
        lvCourse.setAdapter(adapter);
        setListViewHeightBasedOnChildren(lvCourse);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_course_detail_acitivity);
    }

    @OnClick(R.id.iv_share)
    public void onViewClicked(View view) {
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
        }
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获得adapter
        Kz_CourseDetaiListAdapter adapter = (Kz_CourseDetaiListAdapter) listView.getAdapter();
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
