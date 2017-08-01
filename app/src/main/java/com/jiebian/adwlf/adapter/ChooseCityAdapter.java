package com.jiebian.adwlf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiebian.adwlf.R;
import com.jiebian.adwlf.bean.TextViewColorBean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/1.
 */
public class ChooseCityAdapter extends BaseAdapter {
    private Context context;
    private final LayoutInflater from;
    private List<TextViewColorBean> list;


    public ChooseCityAdapter(Context context,List<TextViewColorBean> list) {
        this.context = context;
        this.list=list;
        from = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv=null;
        if(convertView==null){
            convertView=  from.inflate(R.layout.item_textview, null);
            tv= (TextView) convertView.findViewById(R.id.adapter_textview);
            convertView.setTag(tv);
        }else{
            tv= (TextView) convertView.getTag();
        }
        if(list.get(position).isc()){
            tv.setTextColor(context.getResources().getColor(R.color.orange));
            tv.setBackgroundColor(context.getResources().getColor(R.color.umeng_fb_white));
        }else{
            tv.setTextColor(context.getResources().getColor(R.color.item_textview_textcolor));
            tv.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }
        tv.setText(list.get(position).getStr());
        return convertView;
    }
}
