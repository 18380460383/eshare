package com.cardholder.adwlf.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cardholder.adwlf.R;
import com.cardholder.adwlf.bean.ShopBean;
import com.cardholder.adwlf.connector.BackgroundImageLoadingListenerForWaterfall;
import com.cardholder.adwlf.control.ImageLoaderOptionsControl;
import com.cardholder.adwlf.ui.activity.personal.ShopDetailsActivity;
import com.cardholder.adwlf.view.PointListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/1/22.
 */
public class ShopAdapter /*extends BaseAdapter implements View.OnClickListener{*/extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ImageLoader  imageLoader = ImageLoader.getInstance();
    private static final int TYPE_HEADER = 0, TYPE_ITEM = 1, TYPE_FOOT = 2;
    private DisplayImageOptions options;
    public List<ShopBean> mDatas;
    private Context context;
    private int headViewid;
    private int headViewSize;
    private boolean isAddHead;
    private int footViewid;
    private int footViewSize;
    private boolean isAddFoot;
    private View mHeaderView;
    private ViewPager shopBanner;
    private TextView myIntegralNum;
    private TextView intoExchangeList;
    private PointListView shopPointListView;
    private int itemNum=0;
    private HeadBack headBack;

    public ShopAdapter(Context context,List<ShopBean> mDatas) {
        this.mDatas = mDatas;
        this.context=context;
    }

    public void addHeadView(int view,HeadBack headBack) {
        headViewid = view;
        headViewSize = 1;
        isAddHead=true;
        this.headBack=headBack;
    }

    public void addFootView(int view) {
        footViewid = view;
        footViewSize = 1;
        isAddFoot=true;
    }

    @Override
    public MyViewHolder1 onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = null;
        switch (i) {
            case TYPE_HEADER:
                mHeaderView= LayoutInflater.from(viewGroup.getContext()).inflate(headViewid, viewGroup, false);
                view =mHeaderView;
                if(headBack!=null){
                    headBack.setHeadView(mHeaderView);
                }
                break;

            case TYPE_ITEM:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shop_gridview, viewGroup, false);

                break;

            case TYPE_FOOT:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(footViewid, viewGroup, false);
                break;
        }
        return new MyViewHolder1(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
       itemNum=i;
        switch (holder.getItemViewType()) {
            case TYPE_HEADER:
                // 获取cardview的布局属性，记住这里要是布局的最外层的控件的布局属性，如果是里层的会报cast错误
                StaggeredGridLayoutManager.LayoutParams clp = (StaggeredGridLayoutManager.LayoutParams)mHeaderView.getLayoutParams();
                // 最最关键一步，设置当前view占满列数，这样就可以占据两列实现头部了
                if(clp!=null)
                    clp.setFullSpan(true);
                break;
           default:

               final int pos = getRealPosition(holder);
               final ShopBean shopBean = mDatas.get(pos);
               final View itemView = holder.itemView;
               itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent = new Intent(context, ShopDetailsActivity.class);
                       intent.putExtra(ShopDetailsActivity.SHOPID, shopBean.getId());
                       context.startActivity(intent);
                   }
               });
               if(holder instanceof MyViewHolder1) {
                   ((MyViewHolder1) holder).title.setText(shopBean.getTitle());
                   ((MyViewHolder1) holder).integral.setText(shopBean.getScore());
                   if (null == options) {
                       options = ImageLoaderOptionsControl.getOptions();
                   }
                   if (imageLoader == null) {
                       imageLoader = ImageLoader.getInstance();
                   }
                   imageLoader.loadImage(shopBean.getImage(),options,new BackgroundImageLoadingListenerForWaterfall(((MyViewHolder1) holder).imageView,((MyViewHolder1) holder).image_argb));

               }
               if(pos-1>=mDatas.size()){
                   notifyDataSetChanged();
               }
                   break;
        }
    }
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }


    @Override
    public int getItemViewType(int position) {

        int type = TYPE_ITEM;
        if (headViewSize==1 && position == 0) {
            type = TYPE_HEADER;
        } else if (footViewSize==1 && position == getItemCount()-1) {
            //最后一个位置
            type = TYPE_FOOT;
        }
        return type;
    }

    @Override
    public int getItemCount() {
        return mDatas.size()+headViewSize+footViewSize;
    }


class MyViewHolder1 extends RecyclerView.ViewHolder{
    TextView title;
    TextView integral;
    TextView image_argb;
    ImageView imageView;
    public MyViewHolder1(View itemView) {
        super(itemView);
        if(itemView ==mHeaderView)
            return;
        title = (TextView) itemView.findViewById(R.id.shop_title);
        image_argb = (TextView) itemView.findViewById(R.id.image_argb);
        integral = (TextView) itemView.findViewById(R.id.shop_integral);
        imageView = (ImageView) itemView.findViewById(R.id.shop_image);
    }
}
    public interface HeadBack{
        void setHeadView(View head);
    }
}
   /* private ImageLoader  imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private List<ShopBean> list;
    private List<Map<Integer,ShopBean>> maplist;
    private LayoutInflater from;
    private int i=0;

    public ShopAdapter(Context context, List<ShopBean> list) {
        this.list=list;
        this.maplist =getMapList(list);
        from = LayoutInflater.from(context);
    }
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    @Override
    public int getCount() {
        return maplist==null?0:maplist.size();
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
        ShopBean shopBean = maplist.get(position).get(1);
        ShopBean shopBean2 = maplist.get(position).get(2);

        ShopViewHodler hodler = null;
        if (convertView == null) {
            LinearLayout view=new LinearLayout(parent.getContext());
            view.setOrientation(LinearLayout.HORIZONTAL);
            view.setGravity(Gravity.CENTER_HORIZONTAL);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(params);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0,  LinearLayout.LayoutParams.WRAP_CONTENT);
            params1.setMargins(dip2px(parent.getContext(), 3), dip2px(parent.getContext(), 3), dip2px(parent.getContext(), 3), dip2px(parent.getContext(), 3));
            params1.weight=1;
            LinearLayout ll1 = (LinearLayout) from.inflate(R.layout.item_shop_gridview, null);
            ll1.setLayoutParams(params1);
            LinearLayout ll2 = (LinearLayout) from.inflate(R.layout.item_shop_gridview, null);
            ll2.setLayoutParams(params1);
            ll1.setOnClickListener(this);
            ll2.setOnClickListener(this);
            ll2.setVisibility(View.INVISIBLE);
            hodler = new ShopViewHodler();
            hodler.title = (TextView) ll1.findViewById(R.id.shop_title);
            hodler.integral = (TextView) ll1.findViewById(R.id.shop_integral);
            hodler.imageView = (ImageView) ll1.findViewById(R.id.shop_image);
            hodler.title2 = (TextView) ll2.findViewById(R.id.shop_title);
            hodler.integral2 = (TextView) ll2.findViewById(R.id.shop_integral);
            hodler.imageView2 = (ImageView) ll2.findViewById(R.id.shop_image);
            view.addView(ll1);
            view.addView(ll2);
            convertView=view;
            convertView.setTag(hodler);
        } else {
            hodler = (ShopViewHodler) convertView.getTag();
        }
        hodler.title.setText(shopBean.getTitle());
        hodler.integral.setText(shopBean.getScore());
        if(i==0){
            i = new ScreenControl().getscreenWide();
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, i /2-8);
        hodler.imageView.setLayoutParams(layoutParams);
        if(null== options){
            options = ImageLoaderOptionsControl.getOptions();
        }
        if(imageLoader==null){
            imageLoader=ImageLoader.getInstance();
        }
        imageLoader.displayImage(shopBean.getImage(), hodler.imageView, options,new BackgroundImageLoadingListener(hodler.imageView));
        if(shopBean2!=null){
            ((LinearLayout)convertView).getChildAt(1).setVisibility(View.VISIBLE);
            hodler.title2.setText(shopBean2.getTitle());
            hodler.integral2.setText(shopBean2.getScore());
            hodler.imageView2.setLayoutParams(layoutParams);
            imageLoader.displayImage(shopBean2.getImage(), hodler.imageView2, options,new BackgroundImageLoadingListener(hodler.imageView2));
        }else{
            ((LinearLayout)convertView).getChildAt(1).setVisibility(View.INVISIBLE);
        }
       View ll1= ((LinearLayout)convertView).getChildAt(0);
       View ll2= ((LinearLayout)convertView).getChildAt(1);
        ll1.setTag(shopBean);
        ll2.setTag(shopBean2);
        return convertView;
    }
    private List<Map<Integer,ShopBean>> getMapList(List<ShopBean> list){
        List<Map<Integer,ShopBean>> mapList=new ArrayList<>();
        Map<Integer,ShopBean> map;
        int size = list.size();
        for(int i=0;i<size;){
            map=new HashMap<>();
            map.put(1,list.get(i++));
            if(i<size){
                map.put(2,list.get(i++));
            }
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public void onClick(View v) {
        ShopBean shopBean = (ShopBean) v.getTag();
        Intent intent = new Intent(v.getContext(), ShopDetailsActivity.class);
        intent.putExtra(ShopDetailsActivity.SHOPID, shopBean.getId());
        v.getContext().startActivity(intent);
    }

    class ShopViewHodler{
        TextView title;
        TextView integral;
        ImageView imageView;
        TextView title2;
        TextView integral2;
        ImageView imageView2;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        maplist.clear();
        maplist.addAll(getMapList(list));
    }
    *//*
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *//*
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    *//*
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
        *//*
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}*/
