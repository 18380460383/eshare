package com.jiebian.adwlf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiebian.adwlf.R;
import com.jiebian.adwlf.bean.returned.PersonalUserNews;
import com.jiebian.adwlf.connector.BackgroundImageLoadingListener;
import com.jiebian.adwlf.control.ImageLoaderOptionsControl;
import com.jiebian.adwlf.control.ScreenControl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 创建者：Administrator
 * 时间：2016/4/20
 * 功能描述：
 */
public class CollectAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater = null;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private List<PersonalUserNews.NewsEntity> rows;
    private double ratioWide = 0.4;

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public CollectAdapter(Context ctx, List<PersonalUserNews.NewsEntity> rows) {
        imageLoader = ImageLoader.getInstance();
        options= ImageLoaderOptionsControl.getOptions();
        mContext = ctx;
        mInflater = LayoutInflater.from(ctx);
        this.rows = rows;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CollectHodler collectHodler = null;
        if (convertView == null) {
            collectHodler=new CollectHodler();
            convertView = mInflater.inflate(R.layout.item_c_collect, null);
            int i = new ScreenControl().getscreenWide();
            convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (i * ratioWide)));
            collectHodler.title = (TextView) convertView.findViewById(R.id.collect_title);
            collectHodler.share_tv = (TextView) convertView.findViewById(R.id.item_information_for_collect_share_tv);
            collectHodler.image= (ImageView) convertView.findViewById(R.id.collect_image);
            convertView.setTag(collectHodler);
        } else {
            collectHodler = (CollectHodler) convertView.getTag();
        }
        PersonalUserNews.NewsEntity newsEntity = rows.get(position);
        imageLoader.displayImage(newsEntity.getImage(),collectHodler.image,options,new BackgroundImageLoadingListener(collectHodler.image));
        collectHodler.title.setText(newsEntity.getTitle());
        collectHodler.share_tv.setText(newsEntity.getRelay()+"");
        return convertView;
    }

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int position) {
        return rows.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    class CollectHodler {
        ImageView image;
        TextView title;
        TextView share_tv;
    }

}
