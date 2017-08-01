package com.jiebian.adwlf.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiebian.adwlf.AppContext;
import com.jiebian.adwlf.Constants;
import com.jiebian.adwlf.R;
import com.jiebian.adwlf.bean.returned.PersonalUserNews;
import com.jiebian.adwlf.connector.BackgroundImageLoadingListenerFragment1;
import com.jiebian.adwlf.control.ImageLoaderOptionsControl;
import com.jiebian.adwlf.control.ScreenControl;
import com.jiebian.adwlf.net.NetworkDownload;
import com.jiebian.adwlf.sql.DBManager;
import com.jiebian.adwlf.ui.activity.WebviewActivity;
import com.jiebian.adwlf.ui.activity.personal.DetialActivity;
import com.jiebian.adwlf.ui.activity.personal.InformationForDetails;
import com.jiebian.adwlf.ui.activity.personal.InformationWebviewActivity;
import com.jiebian.adwlf.ui.activity.personal.LoginActivity;
import com.jiebian.adwlf.ui.fragment.personal.Fragment1;
import com.jiebian.adwlf.utils.AppUtils;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建者：Administrator
 * 时间：2016/4/20
 * 功能描述：
 */
public class C_Home_Adapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater = null;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private List<PersonalUserNews.NewsEntity> newsEntityList;
    private List<PersonalUserNews.ProjectEntity> interactionList;
    private double ratioWide=0.4;
    private Fragment1 fragment1;
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public C_Home_Adapter(Context ctx,List<PersonalUserNews.NewsEntity> newsEntityList,List<PersonalUserNews.ProjectEntity> interactionList) {
        imageLoader = ImageLoader.getInstance();
        options=ImageLoaderOptionsControl.getOptions();
        mContext = ctx;
        mInflater = LayoutInflater.from(ctx);
        this.newsEntityList=newsEntityList;
        this.interactionList=interactionList;
    }
    public C_Home_Adapter(Fragment1 fragment1,Context ctx,List<PersonalUserNews.NewsEntity> newsEntityList,List<PersonalUserNews.ProjectEntity> interactionList) {
        imageLoader = ImageLoader.getInstance();
        options=ImageLoaderOptionsControl.getFragment1Options();
        mContext = ctx;
        mInflater = LayoutInflater.from(ctx);
        this.newsEntityList=newsEntityList;
        this.interactionList=interactionList;
        this.fragment1=fragment1;
    }
    @Override
    public View getView( int position, View convertView, ViewGroup parent) {
        UserNewsHoderviewpoint viewpointHoder;
        UserNewsHoderHudong hudongHoder;
            switch (getItemViewType(position)){
                case 0:
                    if(convertView==null) {
                        hudongHoder = new UserNewsHoderHudong();
                        convertView = mInflater.inflate(R.layout.item_information_for_hudong, null);
                        hudongHoder.hudongImage = (ImageView) convertView.findViewById(R.id.hudong_image);
                        hudongHoder.hudiongImageType = (ImageView) convertView.findViewById(R.id.hudong_type_image);
                        hudongHoder.hudoingTitle = (TextView) convertView.findViewById(R.id.hudong_title);
                        hudongHoder.hudongShareTv = (TextView) convertView.findViewById(R.id.item_information_for_hudong_share_tv);
                        hudongHoder.hudongMoney = (TextView) convertView.findViewById(R.id.hudong_money);
                        DecimalFormat df = new DecimalFormat("0");//格式化小数
                        double v = new ScreenControl().getscreenWide() * ratioWide;
                        df.format(v);
                        Integer integer = Integer.valueOf(df.format(v));
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,integer);
                        hudongHoder.hudongImage.setLayoutParams(layoutParams);
                        convertView.setTag(hudongHoder);
                    }else{
                        hudongHoder= (UserNewsHoderHudong) convertView.getTag();
                    }
                    final PersonalUserNews.ProjectEntity interaction= (PersonalUserNews.ProjectEntity) getItem(position);
                    String imageurl = interaction.getImageurl();
                    if(!TextUtils.isEmpty(imageurl)){
                        imageLoader.displayImage(imageurl,hudongHoder.hudongImage,options,new BackgroundImageLoadingListenerFragment1(hudongHoder.hudongImage));
                    }else{
                        hudongHoder.hudongImage.setImageResource(R.mipmap.image_def);
                        hudongHoder.hudongImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    }
                    hudongHoder.hudiongImageType.setImageResource("0".equals(interaction.getType()) ? R.mipmap.h_money : R.mipmap.h_feng);
                    hudongHoder.hudoingTitle.setText(interaction.getProjectname());
                    hudongHoder.hudongShareTv.setText(AppUtils.dealWithNUm(interaction.getRelay()));
                    hudongHoder.hudongMoney.setText(interaction.getReward()+"");
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent intent = new Intent(mContext, DetialActivity.class);
                                intent.putExtra("pid", interaction.getPid());
                                DetialActivity.setFragment(fragment1);
                                mContext.startActivity(intent);
                        }
                    });
                    break;
                case 1:
                    if(convertView==null) {
                        viewpointHoder = new UserNewsHoderviewpoint();
                        convertView = mInflater.inflate(R.layout.item_information_for_viewpoint, null);
                        viewpointHoder.viewpointImage = (ImageView) convertView.findViewById(R.id.viewpoint_image);
                        viewpointHoder.viewpointImageType = (TextView) convertView.findViewById(R.id.viewpoint_type_image);
                        viewpointHoder.viewpointTitle = (TextView) convertView.findViewById(R.id.viewpoint_title);
                        viewpointHoder.viewpointBrowseTv = (TextView) convertView.findViewById(R.id.item_information_for_viewpoint_comment_tv);
                        viewpointHoder.viewpointHitsTv = (TextView) convertView.findViewById(R.id.item_information_for_viewpoint_hits_tv);
                        viewpointHoder.viewpointShareTv = (TextView) convertView.findViewById(R.id.item_information_for_viewpoint_share_tv);
                        viewpointHoder.addColl = (TextView) convertView.findViewById(R.id.add_coll);
                        DecimalFormat df = new DecimalFormat("0");//格式化小数
                        double v = new ScreenControl().getscreenWide() * ratioWide;
                        df.format(v);
                        Integer integer = Integer.valueOf(df.format(v));
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,integer);
                        int left = AppUtils.dip2px(mContext, 8);
                        layoutParams.setMargins(left,left,left,0);
                        viewpointHoder.viewpointImage.setLayoutParams(layoutParams);
                        convertView.setTag(viewpointHoder);
                    }else{
                        viewpointHoder= (UserNewsHoderviewpoint) convertView.getTag();
                    }
                    final PersonalUserNews.NewsEntity newsEntity= (PersonalUserNews.NewsEntity) getItem(position);
                    String image = newsEntity.getImage();
                    if(!TextUtils.isEmpty(image)){
                        imageLoader.displayImage(newsEntity.getImage(), viewpointHoder.viewpointImage, options, new BackgroundImageLoadingListenerFragment1(viewpointHoder.viewpointImage));
                    }else{
                        viewpointHoder.viewpointImage.setImageResource(R.mipmap.image_def);
                        viewpointHoder.viewpointImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    }
                    viewpointHoder.viewpointImageType.setText(newsEntity.getTypename());
                    viewpointHoder.viewpointImageType.setBackgroundResource(R.drawable.huang_kuang_juxing);
                    try {
                      /*  GradientDrawable myGrad = (GradientDrawable)viewpointHoder.viewpointImageType.getBackground();
                      myGrad.setColor(Color.parseColor(type_color));*/
                        String type_color = newsEntity.getType_color();
                        if(!TextUtils.isEmpty(type_color)){
                                if(!type_color.contains("#")){
                                    type_color="#"+type_color;
                                }
                        }

                        viewpointHoder.viewpointImageType.setTextColor(Color.parseColor(type_color));
                        //viewpointHoder.viewpointImageType.setTextColor(mContext.getResources().getColor(R.color.white));
                    }catch (Exception e){
                        viewpointHoder.viewpointImageType.setTextColor(mContext.getResources().getColor(R.color.orange));
                    }
                    viewpointHoder.viewpointTitle.setText(newsEntity.getTitle());
                    if(newsEntity.getJumpType()==1){
                        viewpointHoder.viewpointBrowseTv.setVisibility(View.INVISIBLE);
                    }else{
                        viewpointHoder.viewpointBrowseTv.setVisibility(View.VISIBLE);
                        viewpointHoder.viewpointBrowseTv.setText(AppUtils.dealWithNUm(newsEntity.getComment()) + "评论");
                    }
                    viewpointHoder.viewpointHitsTv.setText(AppUtils.dealWithNUm(newsEntity.getHits())+"浏览");
                    viewpointHoder.viewpointShareTv.setText(AppUtils.dealWithNUm(newsEntity.getRelay())+"转发");
                    if("0".equals(newsEntity.getIs_collect())){
                        viewpointHoder.addColl.setEnabled(true);
                        viewpointHoder.addColl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!AppContext.getInstance().getPersonageOnLine()) {
                                    //TODO 登陆code=7
                                   mContext.startActivity(new Intent(mContext, LoginActivity.class));
                                } else {
                                    collectNews(newsEntity);
                                }
                            }
                        });
                    }else{
                        viewpointHoder.addColl.setEnabled(false);
                    }
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(newsEntity.getJumpType()==1){
                                Intent intent = new Intent(mContext, InformationWebviewActivity.class);
                                intent.putExtra("title", newsEntity.getTypename()+"详情");
                                intent.putExtra("url", newsEntity.getJumpUrl());
                                intent.putExtra(InformationWebviewActivity.NID,newsEntity.getNid()+"");
                                mContext.startActivity(intent);
                            }else if(newsEntity.getJumpType()==0){
                                Intent intent = new Intent(mContext, InformationForDetails.class);
                                intent.putExtra(InformationForDetails.NID,newsEntity.getNid()+"");
                                intent.putExtra(InformationForDetails.TITLENAME, newsEntity.getTypename() + "详情");
                                mContext.startActivity(intent);
                            }
                        }
                    });
                    break;
            }

        return convertView;
    }

    @Override
    public int getCount() {
       return newsEntityList.size()+interactionList.size();
    }

    @Override
    public Object getItem(int position) {
        System.out.println(position+"-"+interactionList.size()+"-"+newsEntityList.size());
            if (position + 1 <= interactionList.size()) {
                return interactionList.get(position);
            } else {
                return newsEntityList.get(position - interactionList.size());
            }
    }
    @Override
    public int getItemViewType(int position) {
        if(interactionList!=null&&interactionList.size()>0&&position+1<=interactionList.size()){
            return 0;
        }else{
            return 1;
        }
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class UserNewsHoderviewpoint{
        ImageView viewpointImage;
        TextView viewpointImageType;
        TextView viewpointTitle;
        TextView viewpointBrowseTv;
        TextView viewpointHitsTv;
        TextView viewpointShareTv;
        TextView addColl;
    }
    class UserNewsHoderHudong{
        ImageView hudongImage;
        ImageView hudiongImageType;
        TextView hudoingTitle;
        TextView hudongShareTv;
        TextView hudongMoney;
    }
    /**
    * 收藏资讯
    */
    private void collectNews(final PersonalUserNews.NewsEntity newsEntity) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", AppContext.getInstance().getPEUser().getUid());
        map.put("nid", newsEntity.getNid() + "");
        RequestParams parm = AppUtils.getParm(map);
        NetworkDownload.jsonPostForCode1(mContext, Constants.URL_POST_COLLECT, parm, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                Toast.makeText(mContext, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                newsEntity.setIs_collect("1");
                DBManager dbManager = DBManager.getDBManager(mContext);
                ContentValues values = new ContentValues();
                values.put("mycollect", 1);
                dbManager.update(Constants.DB_USER_NEWS, values, "nid=?", new String[]{newsEntity.getNid()+""});
            }

            @Override
            public void onFailure() {
               // Toast.makeText(mContext, "收藏失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
