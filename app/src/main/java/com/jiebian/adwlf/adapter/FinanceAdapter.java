package com.jiebian.adwlf.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiebian.adwlf.R;
import com.jiebian.adwlf.ebean.Finance;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2015/11/2.
 */
public class FinanceAdapter extends BaseAdapter{
    private List<Finance> list;
    private Context context;
    private LayoutInflater from;

    public FinanceAdapter(Context context, List<Finance> list){
        this.list=list;
        this.context=context;
        this.from = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
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
        ViewHealder healder=null;
        if(convertView==null){
            convertView= from.inflate(R.layout.item_financial_information, null);
            healder=new ViewHealder();
            healder.title= (TextView) convertView.findViewById(R.id.financial_title);
            healder.time= (TextView) convertView.findViewById(R.id.financial_time);
            healder.changNum= (TextView) convertView.findViewById(R.id.financial_change);
            convertView.setTag(healder);
        }else{
            healder= (ViewHealder) convertView.getTag();
        }

        Finance msg=list.get(position);
        healder.title.setText(msg.getRemark());
        Date d = new Date(msg.getCreate_time());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        healder.time.setText(sdf.format(d));
        healder.changNum.setText(msg.getChange_type().equals("1")?"-"+msg.getChange_money():"+"+msg.getChange_money());
        Resources resources = context.getResources();
        healder.changNum.setTextColor(msg.getChange_type().equals("1")? resources.getColor(R.color.text_hei):resources.getColor(R.color.orange));
        return convertView;
    }
    static class ViewHealder{
        TextView title;
        TextView time;
        TextView changNum;

    }
}
