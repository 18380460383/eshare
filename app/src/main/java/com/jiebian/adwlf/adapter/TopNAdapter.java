package com.jiebian.adwlf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiebian.adwlf.R;
import com.jiebian.adwlf.bean.returned.Rating;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by worm on 2015/10/25.
 */
public class TopNAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater = null;

    private Rating mTopNReturn;
    private List<Rating.ListEntity> rows = new ArrayList<>();

    public TopNAdapter(Context context,Rating topNReturn) {
        this.mContext = context;
        this.mTopNReturn = topNReturn;

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        if (mTopNReturn != null && mTopNReturn.getList() != null) {
            this.rows.addAll(mTopNReturn.getList());
        }
    }


    // 重新设置列表数据
    public void setData(Rating listBean) {
        this.rows.clear();
        if(listBean != null && listBean.getList()!= null) {
            this.rows.addAll(listBean.getList());
        }
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        if (rows != null && position < rows.size()){
            return rows.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.topn_item,null);
            viewHolder = new ViewHolder();
            viewHolder.topn_rank = (TextView) convertView.findViewById(R.id.topn_rank);
            viewHolder.earn_money = (TextView) convertView.findViewById(R.id.earn_money);
            viewHolder.username = (TextView) convertView.findViewById(R.id.username);
            viewHolder.relaynum = (TextView) convertView.findViewById(R.id.relaynum);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(position < 3) {
            viewHolder.topn_rank.setTextColor(mContext.getResources().getColor(R.color.orange));
        } else {
            viewHolder.topn_rank.setTextColor(mContext.getResources().getColor(R.color.no3_black));
        }

        final Rating.ListEntity topnBean = rows.get(position);
        viewHolder.topn_rank.setText((position+1) + "");
        viewHolder.username.setText(topnBean.getUsername());
        viewHolder.earn_money.setText("￥" + new java.text.DecimalFormat("#.00").format(topnBean.getEarn_money()));
        viewHolder.relaynum.setText(topnBean.getRelaynum());

        return convertView;
    }

    @Override
    public int getCount() {
        return rows.size();
    }

    public class ViewHolder{
        TextView topn_rank;
        TextView earn_money;
        TextView username;
        TextView relaynum;
    }

}
