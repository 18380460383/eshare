package com.jiebian.adwlf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jiebian.adwlf.R;
import com.jiebian.adwlf.ebean.Industry;

import java.util.List;

/**
 * Created by Administrator on 2015/12/14.
 */
public class IndustryAdapter extends BaseAdapter {
    private List<Industry> list;
    private Context context;
    private LayoutInflater from;

    public IndustryAdapter(List<Industry> list, Context context) {
        this.list = list;
        this.context = context;
        this.from = LayoutInflater.from(context);
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
        IndustryHodler hodler=null;
        if(convertView==null){
            convertView=from.inflate(R.layout.item_industry,null);
            hodler=new IndustryHodler();
            hodler.name= (TextView) convertView.findViewById(R.id.industry_name);
            hodler.ck= (CheckBox) convertView.findViewById(R.id.industry_ck);
            convertView.setTag(hodler);
        }else{
            hodler= (IndustryHodler) convertView.getTag();
        }
        final Industry eType = list.get(position);
        hodler.ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    eType.setOpt_for(true);
                }else{
                    eType.setOpt_for(false);
                }
            }
        });
        hodler.name.setText(eType.getItem());
        hodler.ck.setChecked(eType.isOpt_for());

        return convertView;
    }
    static class IndustryHodler{
        TextView name;
        CheckBox ck;
    }
}
