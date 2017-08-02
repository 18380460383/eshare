package com.cardholder.adwlf.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cardholder.adwlf.R;
import com.cardholder.adwlf.base.PublishItem;
import com.cardholder.adwlf.ui.activity.PublishActivity;
import com.cardholder.adwlf.util.EBitmapUtil;
import com.cardholder.adwlf.util.EIntentUtil;
import com.cardholder.adwlf.util.EToastUtil;
import com.cardholder.adwlf.util.EshareLoger;
import com.cardholder.adwlf.util.StringUtils;
import com.cardholder.adwlf.view.SelectPicPopupWindow;

import java.io.File;
import java.util.List;

/**
 * 说明：
 * note：
 * Created by FuPei
 * on 2015/12/7 at 19:27
 */
public class PublishAdapter extends BaseAdapter {

    private List<PublishItem> data;
    private Context mContext;
    private LayoutInflater mInflater;
    private SelectPicPopupWindow menuWindow;
    /**当前点击选择图片的位置*/
    private int current_index;
    /**保存截图的路径*/
    private String current_path;

    public PublishAdapter(Context context, List<PublishItem> data) {
        this.mContext = context;
        this.data = data;
        mInflater = LayoutInflater.from(mContext);
        current_index = 0;
    }

    public int getCurrent_index() {
        return current_index;
    }

    public void setCurrent_index(int current_index) {
        this.current_index = current_index;
    }

    public String getCurrent_path() {
        return current_path;
    }

    public void setCurrent_path(String current_path) {
        this.current_path = current_path;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_publish, null);
            holder = new Holder();
            holder.iv = (ImageView) convertView.findViewById(R.id.adds_iv);
            holder.tv = (TextView) convertView.findViewById(R.id.adds_tv);
            holder.ly = (LinearLayout) convertView.findViewById(R.id.adds_ly);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final PublishItem item = data.get(position);
        //如果类型是文字
        if(item.getType() == PublishItem.Type.TEXT) {
            holder.ly.setVisibility(View.GONE);
            holder.iv.setVisibility(View.GONE);
            holder.tv.setVisibility(View.VISIBLE);
            if(null == item.getValue()) {
                holder.tv.setText("编辑文字");
                holder.tv.setTextColor(mContext.getResources().getColor(item.DEFAULT_COLOR));
                holder.tv.setTextSize(item.DEFAULT_SIZE);
                holder.tv.setGravity(Gravity.LEFT);
                holder.tv.setTypeface(null);
            } else {
                holder.tv.setText(item.getValue());
                holder.tv.setTextColor(item.getTextColor());
                holder.tv.setTextSize(item.getTextSize());
                holder.tv.setGravity(item.getTextAlign());
                holder.tv.setTypeface(Typeface.create("", item.getTextType()));
            }
        } else {
            //类型是图片
            holder.tv.setVisibility(View.GONE);
            holder.ly.setVisibility(View.GONE);
            holder.iv.setVisibility(View.VISIBLE);
            if(null == item.getValue()) {
                holder.ly.setVisibility(View.VISIBLE);
                holder.iv.setVisibility(View.GONE);
            } else {
                holder.iv.setTag(item.getValue());
                EBitmapUtil.loadLocalImg(mContext, holder.iv, item.getValue());
            }
        }

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_index = position;
                getLocalImage();
            }
        });

        holder.ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_index = position;
                getLocalImage();
            }
        });

        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(mContext, AddTextActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(AddTextActivity.EXTRA_TEXT, item);
                intent.putExtras(bundle);
                current_index = position;
                ((Activity)(mContext)).startActivityForResult(intent, PublishActivity.REQUEST_CODE_TEXT);*/
            }
        });
        holder.ly.setOnLongClickListener(null);
        holder.tv.setOnLongClickListener(null);
        holder.iv.setOnLongClickListener(null);
        return convertView;
    }

    public class Holder {
        LinearLayout ly;
        ImageView iv;
        TextView tv;
    }

    /**
     * 操作图片
     */
    private void getLocalImage() {
        String time = System.currentTimeMillis() + "";
        current_path = PublishActivity.PATH_IMG + time;

        menuWindow = new SelectPicPopupWindow((Activity)mContext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v.getId() == R.id.btn_pick_photo) {
                    EshareLoger.logI("点击选择图片");
                    ((Activity) mContext).startActivityForResult(EIntentUtil.selectPhoto(), PublishActivity.REQUEST_CODE_IMG);
                } else if(v.getId() == R.id.btn_crop_photo) {
                    EshareLoger.logI("点击剪切图片");
                    String path = data.get(getCurrent_index()).getValue();
                    if(null != path && !StringUtils.isEmpty(path)) {
                        Uri uri = Uri.fromFile(new File(path));
                        ((Activity) mContext).startActivityForResult(EIntentUtil.cropImageUri(uri,
                                Uri.fromFile(new File(getCurrent_path()))), PublishActivity.REQUEST_CODE_CROP);
                    } else {
                        EToastUtil.show(mContext, "还没有图片的，伦家剪切不了。。。");
                    }
                } else if(v.getId() == R.id.btn_take_photo) {
                    EshareLoger.logI("点击拍照");
                    ((Activity) mContext).startActivityForResult(EIntentUtil.selectTake(PublishActivity.URI_TMP), PublishActivity.REQUEST_CODE_TAKE);
                }
                menuWindow.dismiss();
            }
        });

        //显示窗口
        menuWindow.showAtLocation(((Activity)mContext).findViewById(R.id.activity_addtext_buttom), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        if(data.get(current_index).getValue() == null) {
            menuWindow.setCropVisible(false);
        } else {
            menuWindow.setCropVisible(true);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        EshareLoger.logI("data :" + data.toString());
    }

}
