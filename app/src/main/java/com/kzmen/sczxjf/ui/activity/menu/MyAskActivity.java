package com.kzmen.sczxjf.ui.activity.menu;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.fragment.kzmessage.CourseCollectionFragment;

import butterknife.InjectView;

/**
 * 我的问答
 */
public class MyAskActivity extends SuperActivity {
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.tab_layout)
    TabLayout tabLayout;
    @InjectView(R.id.info_viewpager)
    ViewPager infoViewpager;
    private String[] titles = new String[]{"课程提问", "回答提问", "我的偷听"};
    private MyFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "我的收藏");
        initView();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_my_ask);
    }

    private void initView() {
        adapter = new MyFragmentAdapter(getSupportFragmentManager());
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
        }
    }

    public class MyFragmentAdapter extends FragmentStatePagerAdapter {
        private String[] titles;
        private LayoutInflater mInflater;

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = new CourseCollectionFragment();
            Bundle bundle = null;
            switch (position) {
                case 0:
                    bundle = new Bundle();
                    bundle.putString("type", "1");
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putString("type", "2");
                    break;
                case 2:
                    bundle = new Bundle();
                    bundle.putString("type", "3");
                    break;
            }
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = null;
            try {
                fragment = (Fragment) super.instantiateItem(container, position);
            } catch (Exception e) {

            }
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
  /*      //此方法用来显示tab上的名字
        @Override
        public CharSequence getPageTitle(int position) {

            return titles[position % titles.length];
        }*/


        /**
         * 添加getTabView的方法，来进行自定义Tab的布局View
         *
         * @param position
         * @return
         */
        public View getTabView(int position) {
            mInflater = LayoutInflater.from(MyAskActivity.this);
            View view = mInflater.inflate(R.layout.kz_tab_item_layout_sign, null);
            TextView tv = (TextView) view.findViewById(R.id.textView);
            TextView tv_sign = (TextView) view.findViewById(R.id.tv_sign);
            tv.setText(titles[position]);
            ImageView img = (ImageView) view.findViewById(R.id.imageView);
            if(position>=2){
                tv_sign.setVisibility(View.GONE);
            }else{
                tv_sign.setVisibility(View.VISIBLE);
            }
            return view;
        }

        public void setTitles(String[] titles) {
            this.titles = titles;
        }

    }
}
