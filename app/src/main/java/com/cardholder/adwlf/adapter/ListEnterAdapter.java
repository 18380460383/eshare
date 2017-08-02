package com.cardholder.adwlf.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cardholder.adwlf.R;
import com.cardholder.adwlf.bean.ListEnterEntity;
import com.cardholder.adwlf.util.ImageUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Describe:企业列表适配器
 * Created by FuPei on 2016/3/10.
 */
public class ListEnterAdapter extends BaseAdapter {

    private List<ListEnterEntity> data;
    private LayoutInflater mInflater;
    private Context context;

    public ListEnterAdapter(Context context, List<ListEnterEntity> data) {
        this.data = data;
        this.context = context;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final HoldView holdView;
        if(convertView == null) {
            holdView = new HoldView();
            convertView = mInflater.inflate(R.layout.item_list_enter, null);
            holdView.iv_logo = (ImageView) convertView.findViewById(R.id.item_iv_logo);
            holdView.tv_name = (TextView) convertView.findViewById(R.id.item_tv_name);
            convertView.setTag(holdView);
        } else {
            holdView = (HoldView) convertView.getTag();
        }
        holdView.tv_name.setText(data.get(position).getCompany_name());
        String url = data.get(position).getLogo();
        if(!TextUtils.isEmpty(url)) {
            ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                    holdView.iv_logo.setImageResource(R.mipmap.image_def);
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    holdView.iv_logo.setImageBitmap(ImageUtils.getRoundedCornerBitmap(bitmap, 15));
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        }
        return convertView;
    }

    private class HoldView {
        public TextView tv_name;
        public ImageView iv_logo;
    }
}
