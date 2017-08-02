package com.cardholder.adwlf.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cardholder.adwlf.R;
import com.cardholder.adwlf.ebean.GeneralizeMsg;

import java.util.List;

/**
 * Created by Administrator on 2015/11/2.
 */
public class RecordEMsgAdapter extends BaseAdapter{
    private List<GeneralizeMsg> list;
    private LayoutInflater from;

    public RecordEMsgAdapter(Context context,List<GeneralizeMsg> list){
        this.list=list;
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
            convertView= from.inflate(R.layout.item_g_record, null);
            healder=new ViewHealder();
            healder.title= (TextView) convertView.findViewById(R.id.g_title);
            healder.state= (TextView) convertView.findViewById(R.id.state);
            healder.cost= (TextView) convertView.findViewById(R.id.g_cost);
            healder.people_num= (TextView) convertView.findViewById(R.id.g_people_num);
            healder.data= (TextView) convertView.findViewById(R.id.data);
            healder.platform= (TextView) convertView.findViewById(R.id.g_fuwu_cost);
            healder.platformtitle= (TextView) convertView.findViewById(R.id.fuwufei_title);
            convertView.setTag(healder);
        }else{
            healder= (ViewHealder) convertView.getTag();
        }
        GeneralizeMsg msg=list.get(position);
        healder.title.setText(msg.getProjectname());
        healder.state.setText(msg.getState());
        healder.cost.setText("¥"+msg.getTotalcost());
        healder.people_num.setText(msg.getRelay_num()+"");
        healder.data.setText(msg.getDatetime().substring(0,10));
        String platform = msg.getPlatform();
        if(!TextUtils.isEmpty(platform)){
            healder.platform.setVisibility(View.VISIBLE);
            healder.platformtitle.setVisibility(View.VISIBLE);
            healder.platform.setText("¥"+platform);
        }else{
            healder.platform.setVisibility(View.GONE);
            healder.platformtitle.setVisibility(View.GONE);
        }

        return convertView;
    }
    static class ViewHealder{
        TextView title;
        TextView state;
        TextView cost;
        TextView people_num;
        TextView data;
        TextView platform;
        TextView platformtitle;
    }
}
