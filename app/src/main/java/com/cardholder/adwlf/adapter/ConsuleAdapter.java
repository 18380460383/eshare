package com.cardholder.adwlf.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cardholder.adwlf.R;
import com.cardholder.adwlf.bean.returned.ConsuleList;
import com.cardholder.adwlf.connector.BackgroundImageLoadingListener;
import com.cardholder.adwlf.control.ImageLoaderOptionsControl;
import com.cardholder.adwlf.control.ScreenControl;
import com.cardholder.adwlf.view.MyGridView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


/**
 * Created by Administrator on 2016/2/16.
 */
public class ConsuleAdapter extends BaseAdapter{
    /**
     * 图文
     */
    private int ITEM_TYPE_ONE=0;
    /**
     * 多图
     */
    private int ITEM_TYPE_TOW=1;
    private ImageLoader imageLoader;
    private List<ConsuleList.ListEntity> consuleList;
    private DisplayImageOptions options;
    public ConsuleAdapter(List<ConsuleList.ListEntity> consuleList) {
        imageLoader = ImageLoader.getInstance();
        this.consuleList=consuleList;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
    @Override
    public int getCount() {
        return consuleList==null?0:consuleList.size();
    }

    @Override
    public Object getItem(int position) {
        return consuleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        setOptions();
        ViewHoder1 viewHoder1=null;
        ViewHoder2 viewHoder2=null;
        if(getItemViewType(position)==ITEM_TYPE_ONE){
            if(convertView==null){
                viewHoder1=new ViewHoder1();
                convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consule_one, null);
                viewHoder1.one_title= (TextView) convertView.findViewById(R.id.consule_one_title);
                viewHoder1.one_description= (TextView) convertView.findViewById(R.id.consule_one_description);
                viewHoder1.one_time= (TextView) convertView.findViewById(R.id.consule_one_time);
                viewHoder1.one_hits= (TextView) convertView.findViewById(R.id.consule_one_hits);
                viewHoder1.image= (ImageView) convertView.findViewById(R.id.consule_one_image);
                convertView.setTag(viewHoder1);


            }else{
                viewHoder1= (ViewHoder1) convertView.getTag();
            }
            ConsuleList.ListEntity listEntity = consuleList.get(position);
            System.out.println(listEntity.getTitle());
            viewHoder1.one_title.setText(listEntity.getTitle());
            viewHoder1.one_description.setText(listEntity.getDescription());
            viewHoder1.one_time.setText(listEntity.getDatetime());
            viewHoder1.one_hits.setText(listEntity.getHits());
            imageLoader.displayImage(listEntity.getImages().get(0), viewHoder1.image, options,new BackgroundImageLoadingListener(viewHoder1.image));
        }else if(getItemViewType(position)==ITEM_TYPE_TOW){
            if(convertView==null){
                viewHoder2=new ViewHoder2();
                convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consule_tow,null);
                final View view =convertView;
                viewHoder2.gv= (MyGridView) convertView.findViewById(R.id.consult_tow_gridView);
                viewHoder2.gv.setClickable(false);
                viewHoder2.gv.setPressed(false);
                viewHoder2.gv.setEnabled(false);
                viewHoder2.tow_title= (TextView) convertView.findViewById(R.id.consule_tow_title);
                viewHoder2.tow_time= (TextView) convertView.findViewById(R.id.consule_tow_time);
                viewHoder2.tow_hits= (TextView) convertView.findViewById(R.id.consule_tow_hits);
               convertView.setTag(viewHoder2);
            }else{
                viewHoder2= (ViewHoder2) convertView.getTag();
            }
            ConsuleList.ListEntity listEntity = consuleList.get(position);
            List<String> images = listEntity.getImages();
            viewHoder2.gv.setAdapter(new GDadapter(images));
            viewHoder2.tow_title.setText(listEntity.getTitle());
            viewHoder2.tow_time.setText(listEntity.getDatetime());
            viewHoder2.tow_hits.setText(listEntity.getHits());
        }
        return convertView;
    }

    private void setOptions() {
        if(null== options){
            options = ImageLoaderOptionsControl.getOptions();
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        ConsuleList.ListEntity listEntity = consuleList.get(position);
        int type = listEntity.getType();
        return type;
    }
    class ViewHoder1{
        TextView one_title;
        TextView one_description;
        TextView one_time;
        TextView one_hits;
        ImageView image;

    }class ViewHoder2{
        TextView tow_title;
        MyGridView gv;
        TextView tow_time;
        TextView tow_hits;
    }
    class GDadapter extends BaseAdapter{
        private  List<String> images;
        public GDadapter(List<String> images){
            this.images=images;
        }
        @Override
        public int getCount() {
            return images.size()>3?3:images.size();
        }

        @Override
        public Object getItem(int position) {
            return images.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ScreenControl s=new ScreenControl();
            convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tow_for_image,null);
            int h = (int) ((s.getscreenWide()-16)/3* 0.75);
            System.out.println("gao"+h);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, h);
            convertView.setLayoutParams(params);
            ((ImageView)convertView).setScaleType(ImageView.ScaleType.CENTER_CROP);
            setOptions();
            imageLoader.displayImage(images.get(position),(ImageView)convertView,options,new BackgroundImageLoadingListener((ImageView)convertView));
            return convertView;
        }
    }
    public void sethits(int position){

    }
}
