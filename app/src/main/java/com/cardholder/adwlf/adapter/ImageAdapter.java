package com.cardholder.adwlf.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cardholder.adwlf.R;
import com.cardholder.adwlf.view.SquareImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Describe:
 * Created by FuPei on 2016/2/19.
 */
public class ImageAdapter extends BaseAdapter {

    private List<String> mData;
    private Context mContext;
    private boolean candelete;

    public ImageAdapter(Context context, List<String> data) {
        mData = data;
        mContext = context;
        candelete = true;
    }

    public void setCanDelete(boolean can) {
        this.candelete = can;
        notifyDataSetChanged();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HoldView holdView;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_image, null);
            holdView = new HoldView();
            holdView.iv_image = (SquareImageView) convertView.findViewById(R.id.item_iv_image);
            holdView.iv_delete = (ImageView) convertView.findViewById(R.id.item_iv_delete);
            convertView.setTag(holdView);
        } else {
            holdView = (HoldView) convertView.getTag();
        }
        String url = mData.get(position);

        if (candelete) {
            if (url.startsWith("drawable")) {
                holdView.iv_delete.setVisibility(View.GONE);
            } else {
                holdView.iv_delete.setVisibility(View.VISIBLE);
            }
        } else {
            holdView.iv_delete.setVisibility(View.GONE);
        }
//        ImageLoader.getInstance().displayImage(mData.get(position), holdView.iv_image,new BackgroundImageLoadingListener(holdView.iv_image));
        if(url.startsWith("drawable")) {
            holdView.iv_image.setImageResource(R.drawable.icon_show_add);
        } else {
            ImageLoader.getInstance().loadImage(mData.get(position), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                    if (!s.startsWith("drawable")) {
                        holdView.iv_image.setImageResource(R.mipmap.image_def);
                    }
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    holdView.iv_image.setImageBitmap(bitmap);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        }
        holdView.iv_image.setFocusable(false);
        holdView.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (candelete) {
                    mData.remove(position);
                    mData.add(position, "drawable://" + R.drawable.icon_show_add);
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }

    public class HoldView {
        public SquareImageView iv_image;
        public ImageView iv_delete;
    }
}
