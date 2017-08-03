package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.control.ImageLoaderOptionsControl;
import com.kzmen.sczxjf.ebean.ShareFrinde;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 被邀请的好友和企业粉丝适配器
 * Created by 杨操 on 2015/11/30.
 */
public class FansFriendsAdapter extends BaseAdapter {
    private List<ShareFrinde> fanss;
    private Context context;
    private  ImageLoader imageLoader;
    private Map<String,Integer> map;
    private DisplayImageOptions options;

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public FansFriendsAdapter(List<ShareFrinde> fanss, Context context) {
        this.fanss = fanss;
        this.context=context;
        imageLoader = ImageLoader.getInstance();

    }

    @Override
    public int getCount() {
        return fanss.size();
    }

    @Override
    public Object getItem(int position) {
        return fanss.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FansHodler hodler=null;
        if(null==convertView){
            hodler=new FansHodler();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_fans,null);
            hodler.letter= (TextView) convertView.findViewById(R.id.fans_letter);
            hodler.heaadImage= (ImageView) convertView.findViewById(R.id.fans_head);
            hodler.nickName= (TextView) convertView.findViewById(R.id.fans_niclname);
            hodler.ratio= (TextView) convertView.findViewById(R.id.fans_ratio);
            convertView.setTag(hodler);
        }else{
            hodler= (FansHodler) convertView.getTag();
        }
        ShareFrinde f=fanss.get(position);
        if(position==0||!f.getPinYin().equals(fanss.get(position-1).getPinYin())){
            hodler.letter.setVisibility(View.VISIBLE);
            hodler.letter.setText(f.getPinYin().equals("^")?"#":f.getPinYin());
        }else{
            hodler.letter.setVisibility(View.GONE);
        }
        if(null==options){
            options = ImageLoaderOptionsControl.getCircleOptions(context);
        }
        imageLoader.displayImage(f.getImageUrl(), hodler.heaadImage,options);
        hodler.nickName.setText(f.getUserName());
        hodler.ratio.setText(TextUtils.isEmpty(f.getEarnMoney()) ? "":f.getEarnMoney());
        return convertView;
    }

    public Map<String, Integer> getMap() {
        return map;
    }

    static  class FansHodler{
        /**
         * 字母级
         */
        TextView letter;
        /**
         * 头像控件
         */
        ImageView heaadImage;
        /**
         * 昵称
         */
        TextView nickName;
        /**
         * 赚取额度
         */
        TextView ratio;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        map=new HashMap<String,Integer>();
        for(int i=0;i<fanss.size();i++) {
            ShareFrinde f = fanss.get(i);
            if (i == 0 || !f.getPinYin().equals(fanss.get(i - 1).getPinYin())) {
                if (f.getPinYin().equals("^")) {
                    map.put("#", i);
                } else
                    map.put(f.getPinYin(), i);
            }
        }
    }

}
