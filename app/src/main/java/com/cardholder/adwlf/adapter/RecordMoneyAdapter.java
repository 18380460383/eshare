package com.cardholder.adwlf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cardholder.adwlf.R;
import com.cardholder.adwlf.bean.RecordMoneyBean;
import com.cardholder.adwlf.bean.returned.RecordMoneyReturn;

import java.util.List;

/**
 * 提现明细adapter
 *
 */
public class RecordMoneyAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater = null;
    private RecordMoneyReturn listBean;
    private List<RecordMoneyBean> rows;

    public RecordMoneyAdapter(Context ctx, List<RecordMoneyBean> rows) {
        mContext = ctx;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.rows = rows;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        GroupViewHolder group = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.withdraw_detail_item, null);
            group = new GroupViewHolder();
            group.time = (TextView) convertView.findViewById(R.id.withdraw_time);
            group.status = (TextView) convertView.findViewById(R.id.withdraw_state);
            group.money = (TextView) convertView.findViewById(R.id.withdraw_money);

            convertView.setTag(group);
        } else {
            group = (GroupViewHolder) convertView.getTag();
        }

        final RecordMoneyBean bean = rows.get(position);
        group.time.setText(bean.date);
        group.money.setText("￥" + bean.getMoney());

        if(bean.isdraw == 0) {
            group.status.setText("待审核");
            group.status.setTextColor(mContext.getResources().getColor(R.color.no3_black));
        } else if(bean.isdraw == 1) {
            group.status.setText("已审核");
            group.status.setTextColor(mContext.getResources().getColor(R.color.text_hei));
        } else if(bean.isdraw == 2) {
            group.status.setText("已打款");
            group.status.setTextColor(mContext.getResources().getColor(R.color.text_hei));
        } else if(bean.isdraw == 3){
            group.status.setText("审核失败");
            group.status.setTextColor(mContext.getResources().getColor(R.color.text_hei));
        } else if(bean.isdraw == 4){
            group.status.setText("打款失败");
            group.status.setTextColor(mContext.getResources().getColor(R.color.text_hei));
        }

        return convertView;
    }



    private class GroupViewHolder {
        TextView time;
        TextView money;
        TextView status;
    }

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int position) {
        if(rows != null && position < rows.size()) {
            return rows.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
