package com.cardholder.adwlf.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cardholder.adwlf.R;
import com.cardholder.adwlf.UIManager;
import com.cardholder.adwlf.bean.AdBean;
import com.cardholder.adwlf.connector.BackgroundImageLoadingListener;
import com.cardholder.adwlf.control.BannerSkip;
import com.cardholder.adwlf.control.ImageLoaderOptionsControl;
import com.cardholder.adwlf.ui.activity.WebviewActivity;
import com.cardholder.adwlf.util.StringUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 广告
 * viewpager
 */

public class AdPagerAdapter extends PagerAdapter{

    public List<AdBean> list = new ArrayList<>();

    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private Context context;

    public AdPagerAdapter(Context context, List<AdBean> list) {
        this.context=context;
        if(list != null) {
            this.list.addAll(list);
        }
        imageLoader=ImageLoader.getInstance();
    }


    public void resetDate(Context context,List<ImageView> mListViews, List<AdBean> list) {
        this.context=context;
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }




    @Override
    public int getCount() {
        if(list.size()==1){
            return 1;
        }else {
            return Integer.MAX_VALUE;
        }
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int size = list.size();
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.ad_item, null);

       final  ImageView child = (ImageView) inflate;
        if(/*position%size != 0 && */(position)%size < size ) {
             AdBean bean = list.get((position)%size);
            if(!StringUtils.isEmpty(bean.imageurl)) {
                if(null== options){
                    options = ImageLoaderOptionsControl.getOptions();
                }
                imageLoader.displayImage(bean.imageurl, child, options,new BackgroundImageLoadingListener(child));
                child.setOnClickListener(new ADOnClickListener(position%size/*-1*/));
            }
       } /*else{
            child.setBackgroundResource(R.mipmap.banner);
            child.setOnClickListener(new ADOnClickListener(-1));
        }*/
        int childCount = container.getChildCount();
        if(childCount>size){
            container.removeView(container.getChildAt(0));
        }
        container.addView(child);
        return child;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        object=null;
    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }

    /**
     * 广告点击监听
     */
    public class ADOnClickListener implements View.OnClickListener {
        int tag;
        public ADOnClickListener(int tag) {
            this.tag = tag;
        }

        @Override
        public void onClick(View v) {
            if(tag == -1) {
                UIManager.showGuideActivity((Activity) context);
                return;
            }
            AdBean adBean = list.get(tag);
            if(!TextUtils.isEmpty(adBean.className)){
                BannerSkip bannerSkip = new BannerSkip(context, adBean.className, adBean.parameter);
                bannerSkip.goSkip();
            }else if(!TextUtils.isEmpty(adBean.linkurl)){
                Intent intent = new Intent(context, WebviewActivity.class);
                intent.putExtra("url", list.get(tag).linkurl);
                context.startActivity(intent);
            }
        }
    }
}
