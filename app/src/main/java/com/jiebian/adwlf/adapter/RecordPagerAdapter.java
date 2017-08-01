package com.jiebian.adwlf.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 记录
 * viewpager
 */

public class RecordPagerAdapter extends FragmentPagerAdapter {

    FragmentManager fm;
    List<Fragment> mListViews;


    public RecordPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mListViews = list;
    }



    @Override
    public int getCount() {
        return mListViews == null ? 0 : mListViews.size();
    }


    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return mListViews.get(position);
    }





}
