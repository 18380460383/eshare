package com.cardholder.adwlf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cardholder.adwlf.R;
import com.cardholder.adwlf.bean.RecordRelayBean;
import com.cardholder.adwlf.bean.returned.RecordRelayReturn;
import com.cardholder.adwlf.ui.fragment.personal.FragmentRecordRelay;
import com.cardholder.adwlf.util.TLog;

import java.util.List;

/**
 * 提现明细adapter
 *
 */
public class RecordRelayAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater = null;
    private RecordRelayReturn listBean;
    private List<RecordRelayBean> rows ;
    FragmentRecordRelay fragmentRecordRelay;
    private String str;


    public RecordRelayAdapter(Context ctx, List<RecordRelayBean> rows,
                              FragmentRecordRelay fragmentRecordRelay,String str) {
        mContext = ctx;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.fragmentRecordRelay = fragmentRecordRelay;
        this.rows = rows;
        this.str=str;


    }

/*    private void setAdapterData(RecordRelayReturn listBean, String str) {
        if(listBean != null && listBean.msg!= null ) {
           *//* if("earned".equals(str)){
                List<RecordRelayBean> list=new ArrayList<>();
                List<RecordRelayBean> msg = listBean.msg;
                int size = msg.size();
                for(int i=0;i<size;i++){
                    if(msg.get(i).ischeck==1){
                        list.add(msg.get(i));
                    }
                }
                this.rows.addAll(list);
            }else if("outhstr".equals(str)){
                List<RecordRelayBean> list=new ArrayList<>();
                List<RecordRelayBean> msg = listBean.msg;
                int size = msg.size();
                for(int i=0;i<size;i++){
                    if(msg.get(i).ischeck==0){
                        list.add(msg.get(i));
                    }
                }
                this.rows.addAll(list);
            }else{*//*
                this.rows.addAll(listBean.data);
//            }

        }
    }*/

    // 重新设置列表数据
   /* public void setData(RecordRelayReturn listBean) {
        this.rows.clear();
        setAdapterData(listBean, str);
        notifyDataSetChanged();
    }*/


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        GroupViewHolder group = null;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.record_item, null);
            group = new GroupViewHolder();
            group.record_title = (TextView) convertView.findViewById(R.id.record_title);
            group.transpond_price = (TextView) convertView.findViewById(R.id.transpond_price);
            group.transpond_time = (TextView) convertView.findViewById(R.id.transpond_time);
            group.icon_upload = (ImageView) convertView.findViewById(R.id.icon_upload);
            group.transpond_state = (ImageView) convertView.findViewById(R.id.transpond_state);
            convertView.setTag(group);
        } else {
            group = (GroupViewHolder) convertView.getTag();
        }
        final RecordRelayBean bean = rows.get(position);
        group.record_title.setText(bean.projectname);
        group.transpond_price.setText(bean.relay_income);
        group.transpond_time.setText(bean.relay_date);
        group.icon_upload.setTag(position);
        if(bean.ischeck == 1) {
            // 审核通过
            group.icon_upload.setVisibility(View.INVISIBLE);
            group.transpond_state.setBackgroundResource(R.mipmap.icon_passed);
        } else if(bean.ischeck == 0){
            // 待审核
            if(bean.isneedscreenshot == 1 && bean.isscreenshot == 0) {
                // 需要上传图片 && 还没上传
                group.icon_upload.setVisibility(View.VISIBLE);
                group.transpond_state.setBackgroundResource(R.mipmap.icon_not_upload);
            } else{
                // 待审核 不需要上传图片 or  已经上传过图片
                group.icon_upload.setVisibility(View.INVISIBLE);
                group.transpond_state.setBackgroundResource(R.mipmap.icon_unaudited);
            }
        }else if(bean.ischeck == 2){
            //未通过
            group.icon_upload.setVisibility(View.VISIBLE);
            group.transpond_state.setBackgroundResource(R.mipmap.nopus);
            group.transpond_state.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentRecordRelay.getLocalImage(bean.pid);
                }
            });
        }

        final GroupViewHolder finalGroup = group;
        group.icon_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TLog.log("position " + finalGroup.icon_upload.getTag());
                fragmentRecordRelay.getLocalImage(bean.pid);
            }
        });

        return convertView;
    }



    private class GroupViewHolder {
        TextView record_title;
        TextView transpond_price;
        TextView transpond_time;
        ImageView icon_upload;
        ImageView transpond_state;
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
