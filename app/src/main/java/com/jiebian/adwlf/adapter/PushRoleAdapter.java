package com.jiebian.adwlf.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiebian.adwlf.R;
import com.jiebian.adwlf.bean.PushRoleBean;
import com.jiebian.adwlf.util.EBitmapUtil;
import com.jiebian.adwlf.util.TextViewUtil;
import com.jiebian.adwlf.view.SquareImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Describe:
 * Created by FuPei on 2016/2/25.
 */
public class PushRoleAdapter extends BaseAdapter {

    private List<PushRoleBean.PushEntity> mData;
    private LayoutInflater mInflater;

    public PushRoleAdapter(Context context, List<PushRoleBean.PushEntity> data) {
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_choose_resource, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final PushRoleBean.PushEntity entity = mData.get(position);
        holder.tvName.setText(entity.getUsername());
        holder.tvMoney.setText("推广费用:");
        holder.tvMoney.append(TextViewUtil.getColorText(entity.getRoles_money(), "#ff8307"));
        holder.tvState.setText(entity.getRelay() + "");
        if(entity.getRelay() == 0) {
            holder.tvState.setText("待转发");
        } else {
            holder.tvState.setText("已转发");
            holder.tvState.setTextColor(Color.parseColor("#ff8307"));
        }
        holder.tvType.setText("类型:" + entity.getName());

        ImageLoader.getInstance().displayImage(entity.getImageurl(), holder.ivLogo, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                holder.ivLogo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                holder.ivLogo.setScaleType(ImageView.ScaleType.FIT_XY);
                holder.ivLogo.setImageBitmap(EBitmapUtil.createFramedPhoto(view.getWidth(), view.getHeight(), bitmap, 10));
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_logo)
        SquareImageView ivLogo;
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_money)
        TextView tvMoney;
        @InjectView(R.id.tv_type)
        TextView tvType;
        @InjectView(R.id.tv_state)
        TextView tvState;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


}
