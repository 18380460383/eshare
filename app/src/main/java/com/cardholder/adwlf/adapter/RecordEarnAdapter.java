package com.cardholder.adwlf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cardholder.adwlf.R;
import com.cardholder.adwlf.bean.EarnBean;

import java.util.List;

/**
 * 说明：收入明细适配
 * note：
 * Created by FuPei
 * on 2015/12/1 at 15:37
 */
public class RecordEarnAdapter extends BaseAdapter{
    private List<EarnBean> listdata;
    private Context mContext;
    private LayoutInflater mInflater;
    public RecordEarnAdapter(Context context, List<EarnBean> list) {
        this.listdata = list;
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
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
        GroupViewHolder group = null;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_earn, null);
            group = new GroupViewHolder();
            group.record_title = (TextView) convertView.findViewById(R.id.item_earn_title);
            group.transpond_price = (TextView) convertView.findViewById(R.id.item_earn_money);
            group.transpond_time = (TextView) convertView.findViewById(R.id.item_earn_time);
            convertView.setTag(group);
        } else {
            group = (GroupViewHolder) convertView.getTag();
        }
        EarnBean bean = listdata.get(position);
        group.record_title.setText(bean.title);
        group.transpond_price.setText("+"+(bean.money/100 )+ "￥");
        group.transpond_time.setText(bean.datetime);
        return convertView;
    }

    private class GroupViewHolder {
        TextView record_title;
        TextView transpond_price;
        TextView transpond_time;
    }

}
