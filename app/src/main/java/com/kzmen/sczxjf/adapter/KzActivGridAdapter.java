package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kzmen.sczxjf.R;

import java.util.List;

/**
 * Created by pjj18 on 2017/8/4.
 */

public class KzActivGridAdapter extends BaseAdapter {
    private List<String> listData;
    private Context mContext;

    public KzActivGridAdapter(Context mContext, List<String> listData) {
        this.mContext = mContext;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.kz_image_item, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_image = (ImageView) view.findViewById(R.id.iv_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(mContext).load("http://cocopeng.com/img/bg-01.jpg").into(viewHolder.iv_image);
        return view;
    }

    class ViewHolder {
        private ImageView iv_image;
    }
}
