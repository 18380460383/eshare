package com.jiebian.adwlf.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiebian.adwlf.R;
import com.jiebian.adwlf.bean.returned.HomeType;
import com.jiebian.adwlf.ui.activity.personal.InformationActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 创建者：Administrator
 * 时间：2016/8/3
 * 功能描述：
 */
public class InformationTypeAdapter extends RecyclerView.Adapter<InformationTypeAdapter.InformationTypeHolder> {
    List<HomeType> list;
    Context context;
    public InformationTypeAdapter(List<HomeType> list,Context context) {
        this.list=list;
        this.context=context;
    }

    @Override
    public InformationTypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        InformationTypeHolder holder = new InformationTypeHolder(LayoutInflater.from(
                context).inflate(R.layout.item_type_information, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(InformationTypeHolder holder, int position) {
        final HomeType homeType = list.get(position);
        holder.textview.setText(homeType.getName());
        ImageLoader.getInstance().displayImage(homeType.getDefault_url(), holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,InformationActivity.class);
                intent.putExtra(InformationActivity.InformationTitle,homeType.getName());
                intent.putExtra(InformationActivity.type,homeType.getTid());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
   class InformationTypeHolder extends RecyclerView.ViewHolder{
       TextView textview;
       ImageView image;
       public InformationTypeHolder(View itemView) {
           super(itemView);
           textview= (TextView) itemView.findViewById(R.id.type_text);
           image= (ImageView) itemView.findViewById(R.id.type_image);
       }
   }


    }
