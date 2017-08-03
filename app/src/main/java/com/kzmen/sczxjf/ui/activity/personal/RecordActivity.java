package com.kzmen.sczxjf.ui.activity.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.RecordPagerAdapter;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.fragment.personal.FragmentRecordEarn;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 个人记录
 * @author wu
 */
public class RecordActivity extends SuperActivity {
    public static final String FLAG="flag";
    @InjectView(R.id.title_name)
    public TextView title;
    @InjectView(R.id.title_back)
    public ImageView title_back;
/*    @InjectView(R.id.record_pager)
    public ViewPager record_pager;*/
    /*@InjectView(R.id.record_relay)
    public Button record_relay;
    @InjectView(R.id.record_earn)
    public Button record_earn;*/
    private List<Fragment> listViews;
    private RecordPagerAdapter adapter;
//    FragmentRecordRelay fragmentListRelay;
    FragmentRecordEarn fragmentListEarn;
    private int currentTab = -1;
    private Intent intent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*        if (null != savedInstanceState) {
            System.out.println("取数据");
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentRecordRelay fragmentListRelay = (FragmentRecordRelay) supportFragmentManager.getFragment(savedInstanceState, "fragmentListRelay");
            //FragmentRecordEarn fragmentListEarn = (FragmentRecordEarn) supportFragmentManager.getFragment(savedInstanceState, "fragmentListEarn");
            if (null != fragmentListRelay) {
                this.fragmentListRelay = fragmentListRelay;
            }
            if (null != fragmentListEarn) {
                this.fragmentListEarn = fragmentListEarn;
            }
        }*/


       // initPager();
    }

    @Override
    public void onCreateDataForView() {
        title.setText("收入记录");
        if (fragmentListEarn == null) {
            fragmentListEarn = new FragmentRecordEarn();
        }
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.record_pager, fragmentListEarn);
        fragmentTransaction.commit();

    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_record);
    }

   /* private void initPager() {
        listViews = new ArrayList<>();
        // 添加使用说明
//        if (fragmentListRelay == null) {
//            fragmentListRelay = new FragmentRecordRelay();
//        }

        Bundle args = new Bundle();
        intent = getIntent();
        if (null != intent) {
            args.putString("flg", intent.getStringExtra("flg"));
        }
//        fragmentListRelay.setArguments(args);
        if (fragmentListEarn == null) {
            fragmentListEarn = new FragmentRecordEarn();
        }


        fragmentListEarn.setArguments(args);
//        listViews.add(fragmentListRelay);
        listViews.add(fragmentListEarn);
        // 初始化
        adapter = new RecordPagerAdapter(getSupportFragmentManager(), listViews);
        record_pager.setAdapter(adapter);
        record_pager.addOnPageChangeListener(new RecordOnPageChangeListener());
        int intExtra = getIntent().getIntExtra(FLAG, 0);
        setCurrentTab(intExtra);
        currentTab = intExtra;
    }

    *//**
     * 页卡切换监听
     *//*
    public class RecordOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            setCurrentTab(arg0);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
*/

   /* public void setCurrentTab(int index) {
        if (currentTab == index)
            return;
        if (index == 0) {
            record_relay.setEnabled(false);
            record_earn.setEnabled(true);
            record_pager.setCurrentItem(0);
        } else if (index == 1) {
            record_earn.setEnabled(false);
            record_relay.setEnabled(true);
            record_pager.setCurrentItem(1);
        }
        currentTab = index;
    }*/

    @OnClick({R.id.title_back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                this.finish();
                break;
        }
    }

/*    private void tabClick(int index) {
        record_pager.setCurrentItem(index);
    }*/

/*    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragmentListRelay.onActivityResult(requestCode, resultCode, data);
    }*/

   /* @Override
    protected void onSaveInstanceState(Bundle outState) {
        System.out.println("存数据");
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (fragmentListRelay != null && supportFragmentManager.findFragmentByTag("fragmentListRelay") != null) {
            System.out.println("fragmentListRelay");
            supportFragmentManager.putFragment(outState, "fragmentListRelay", fragmentListRelay);
            if (fragmentListEarn != null && supportFragmentManager.findFragmentByTag("fragmentListEarn") != null) {
                System.out.println("fragmentListEarn");
                supportFragmentManager.putFragment(outState, "fragmentListEarn", fragmentListEarn);
            }
            super.onSaveInstanceState(outState);
        }
    }*/
}
