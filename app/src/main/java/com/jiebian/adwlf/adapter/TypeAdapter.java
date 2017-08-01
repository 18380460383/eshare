package com.jiebian.adwlf.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiebian.adwlf.R;
import com.jiebian.adwlf.bean.TypeTextBean;

import java.util.List;

/**
 * 说明：
 * note：
 * Created by FuPei
 * on 2015/12/9 at 17:06
 */
public class TypeAdapter extends BaseAdapter {

    private List<TypeTextBean.StyleEntity> listdata;
    private Context mContext;
    private LayoutInflater mInflater;
    public TypeAdapter(Context mContext, List<TypeTextBean.StyleEntity> data) {
        this.listdata = data;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return listdata.size();
    }

    @Override
    public Object getItem(int position) {
        return listdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HoldView hold;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_type, null);
            hold = new HoldView();
            hold.tv_type = (TextView) convertView.findViewById(R.id.item_type_tv);
            convertView.setTag(hold);
        } else {
            hold = (HoldView) convertView.getTag();
        }
        final TextView type = hold.tv_type;
        final TypeTextBean.StyleEntity styleEntity = listdata.get(position);
        type.setText(styleEntity.getTitle());
        String value = styleEntity.getValue();

        if(null != value && value.equals("1")) {
            hold.tv_type.setBackgroundResource(R.drawable.shape_type_ok);
            hold.tv_type.setTextColor(Color.WHITE);
        } else {
            hold.tv_type.setBackgroundResource(R.drawable.shape_type);
            hold.tv_type.setTextColor(Color.BLACK);
        }
        return convertView;
    }

    private class HoldView {
        TextView tv_type;
    }
}
