package com.jiebian.adwlf.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jiebian.adwlf.R;
import com.jiebian.adwlf.bean.Spoor;
import com.jiebian.adwlf.control.ScreenControl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Map;

/**
 * 用户足迹适配器
 * Created by 杨操 on 2016/4/19.
 */
public class UserSpoorAdapter extends BaseAdapter implements View.OnTouchListener{
    private List<Spoor> spoor;
    private Context context;
    private  ImageLoader imageLoader;
    private Map<String,Integer> map;
    private DisplayImageOptions options;
    private float x=0;
    private float y=0;
    private int i;
    private View thisView;
    private LinearLayout.LayoutParams layoutParams;
    private double wigthRatio=0.27;


    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public UserSpoorAdapter(List<Spoor> spoor, Context context) {
        this.spoor = spoor;
        this.context=context;
        imageLoader = ImageLoader.getInstance();

    }

    @Override
    public int getCount() {
        return spoor.size();
    }

    @Override
    public Object getItem(int position) {
        return spoor.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SpoorHodler spoorHodler=null;
        if(null==convertView){
            spoorHodler=new SpoorHodler();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_user_spoor, null);
            i = new ScreenControl().getscreenWide();
            convertView.setLayoutParams(new AbsListView.LayoutParams(i +100, AbsListView.LayoutParams.WRAP_CONTENT));
            spoorHodler.content = (LinearLayout) convertView.findViewById(R.id.item_user_spoor_content);
            spoorHodler.imageView = (ImageView) convertView.findViewById(R.id.item_user_spoor_delete);
            spoorHodler.imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (i * wigthRatio), (int) (i * wigthRatio)));
            spoorHodler.content.setOnTouchListener(this);
            convertView.setTag(spoorHodler);
        }else{
            spoorHodler= (SpoorHodler) convertView.getTag();
        }
        spoorHodler.content.setTag(spoor.get(position));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(i + (int) (i * wigthRatio), AbsListView.LayoutParams.WRAP_CONTENT);

        if(spoor.get(position).ismenushow()){

            params.setMargins(-(int)(i *wigthRatio),0,(int)(i *wigthRatio),0);
            spoorHodler.content.setLayoutParams(params);
            spoorHodler.content.requestLayout();
        }else{
            params.setMargins(0,0,0,0);
            spoorHodler.content.setLayoutParams(params);
            spoorHodler.content.requestLayout();
        }
            spoorHodler.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spoor.remove(getItem(position));
                    notifyDataSetChanged();
                    Log.i("tag","dsfa");
                }
            });

        return convertView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        this.thisView=v;
        Spoor tag = (Spoor)v.getTag();
        layoutParams = (LinearLayout.LayoutParams)v.getLayoutParams();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x =event.getX();
                y =event.getY();
                Log.i("tag","x="+ x +"y="+ y);
                break;
            case MotionEvent.ACTION_MOVE:
                synchronized (new Object()) {
                    float movex = event.getX();
                    Log.i("tag", "x=" + this.x + "y=" + y + "rawY=" + movex);
                    int leftMargin1 = layoutParams.leftMargin;
                    int rightMargin1 = layoutParams.rightMargin;
                    if (leftMargin1 == 0 && rightMargin1 == 0 && movex > x) {
                        break;
                    }
                    int leftMargin = leftMargin1 + (int) ((movex - x));
                    int rightMargin = rightMargin1 + (int) ((x - movex));
                    Log.i("tag", "leftMargin=" + leftMargin + "rightMargin=" + rightMargin);
                    if (leftMargin <= -(i * wigthRatio)) {
                        leftMargin = -(int) (i * 0.3);
                    } else if (leftMargin > 0) {
                        leftMargin = 0;
                    }

                    if (rightMargin >= (i * wigthRatio)) {
                        rightMargin = (int) (i * wigthRatio);
                    } else if (rightMargin < 0) {
                        rightMargin = 0;
                    }
                    layoutParams.setMargins(leftMargin, 0, rightMargin, 0);
                    v.setLayoutParams(layoutParams);
                    v.requestLayout();
                }
                break;
            case MotionEvent.ACTION_UP:
                synchronized (new Object()) {
                    if (layoutParams.rightMargin > (int) (i * wigthRatio * 0.5)) {
                        layoutParams.setMargins(-(int) (i * wigthRatio), 0, (int) (i * wigthRatio), 0);
                        v.setLayoutParams(layoutParams);
                        v.requestLayout();
                        tag.setIsmenushow(true);
                    } else {
                        layoutParams.setMargins(0, 0, 0, 0);
                        v.setLayoutParams(layoutParams);
                        v.requestLayout();
                        tag.setIsmenushow(false);
                    }
                }
                break;
        }

        return true;
    }

    static  class SpoorHodler{
        LinearLayout content;
        ImageView imageView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }

}
