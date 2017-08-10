package com.kzmen.sczxjf.popuwidow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.cusinterface.PlayPopuInterface;
import com.kzmen.sczxjf.test.bean.Music;
import com.kzmen.sczxjf.view.MyListView;

import java.util.List;

/**
 * Created by pjj18 on 2017/8/10.
 */

public class Kz_PlayListPopu extends PopupWindow {
    private Context mContext;
    private View view;
    private TextView tv_cancle;
    private MyListView tv_play_list;
    private List<Music> listData;
    private CommonAdapter<Music> adapter;
    private PlayPopuInterface popuInterface;
    private int playPos = -1;

    public PlayPopuInterface getPopuInterface() {
        return popuInterface;
    }

    public void setPopuInterface(PlayPopuInterface popuInterface) {
        this.popuInterface = popuInterface;
    }

    public int getPlayPos() {
        return playPos;
    }

    public void setPlayPos(int playPos) {
        this.playPos = playPos;
    }

    public Kz_PlayListPopu(Context mContext, final List<Music> listData) {
        this.listData = listData;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.kz_course_play_list, null);
        tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
        tv_play_list = (MyListView) view.findViewById(R.id.tv_play_list);
        tv_cancle.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });
        adapter = new CommonAdapter<Music>(mContext, R.layout.kz_course_detail_list_item, listData) {
            @Override
            protected void convert(ViewHolder viewHolder, Music item, int position) {
                viewHolder.setText(R.id.tv_title, item.getPath());
                if (position == playPos) {
                    viewHolder.getView(R.id.iv_play_state).setVisibility(View.VISIBLE);
                } else {
                    viewHolder.getView(R.id.iv_play_state).setVisibility(View.INVISIBLE);
                }
            }
        };
        tv_play_list.setAdapter(adapter);
        tv_play_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (popuInterface != null) {
                    popuInterface.onItemClic(position, listData.get(position));
                }
                dismiss();
            }
        });
        // 设置外部可点击
        this.setOutsideTouchable(false);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.playlist);
    }
}
