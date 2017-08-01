package com.jiebian.adwlf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jiebian.adwlf.R;
import com.jiebian.adwlf.bean.ProjectBean;
import com.jiebian.adwlf.util.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 说明：预测列表适配
 * note：
 * Created by FuPei
 * on 2015/12/2 at 15:39
 */
public class ForecastAdapter extends BaseAdapter {

    private List<ProjectBean> listdata;
    private Context mContext;
    private LayoutInflater mInflater;
    private ImageLoader imageLoader;

    public ForecastAdapter(Context context, List<ProjectBean> listdata) {
        this.listdata = listdata;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        imageLoader = ImageLoader.getInstance();
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
            hold = new HoldView();
            convertView = mInflater.inflate(R.layout.item_pro, null);
            hold.image = (ImageView) convertView.findViewById(R.id.pro_image);
            hold.title = (TextView) convertView.findViewById(R.id.pro_title);
            hold.status = (TextView) convertView.findViewById(R.id.pro_status);
            hold.precent = (TextView) convertView.findViewById(R.id.pro_precent);
            hold.progress = (ProgressBar) convertView.findViewById(R.id.pro_progress);
            hold.money = (TextView) convertView.findViewById(R.id.pro_money);
            convertView.setTag(hold);
        } else {
            hold = (HoldView) convertView.getTag();
        }
        ProjectBean projectBean = listdata.get(position);
        hold.title.setText(projectBean.projectname);
        hold.status.setText("即将开抢");
        hold.money.setText("保密");
        hold.precent.setText(projectBean.getPrecent() + "%");
        hold.progress.setProgress(projectBean.getPrecent());
        if(!StringUtils.isEmpty(projectBean.imageurl)) {
            imageLoader.displayImage(projectBean.imageurl, hold.image);
        }
        return convertView;
    }

    private class HoldView {
        ImageView image;
        TextView title;
        TextView status;
        TextView precent;
        ProgressBar progress;
        TextView money;
    }
}
