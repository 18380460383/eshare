package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.consta.PlayState;
import com.kzmen.sczxjf.interfaces.MainAskListClick;
import com.kzmen.sczxjf.util.glide.GlideCircleTransform;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by pjj18 on 2017/8/21.
 */

public class Kz_MainAskAdapter extends BaseAdapter {
    private List<String> listData;
    private Context mContext;
    private AnimationDrawable animationDrawable;
    private int state = -1;
    private MainAskListClick mainAskListClick;
    private int playPosition = -1;

    public Kz_MainAskAdapter(Context mContext, List<String> listData, MainAskListClick mainAskListClick) {
        this.mContext = mContext;
        this.listData = listData;
        this.mainAskListClick = mainAskListClick;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.kz_main_ask_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvAskTitle1.setText(listData.get(position));
        Glide.with(mContext).load(R.drawable.icon_user).transform(new GlideCircleTransform(mContext)).into(viewHolder.ivAskHead2);
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.ll_listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainAskListClick != null) {
                    mainAskListClick.onPosClick(position);
                    playPosition = position;
                }
            }
        });
        animationDrawable = (AnimationDrawable) finalViewHolder.iv_anim
                .getDrawable();
        if (position == playPosition) {
            switch (state) {
                case PlayState.PLAY_PLAYING:
                    animationDrawable.start();
                    break;
                case PlayState.PLAY_PAUSE:
                    animationDrawable.stop();
                    break;
            }
        } else {
            animationDrawable.stop();
        }
        return convertView;
    }

    public void state(int state) {
        this.state = state;
        notifyDataSetChanged();
    }

    public void setPlayPosition(int playPosition) {
        this.playPosition = playPosition;
    }

    public int getPlayPosition() {
        return playPosition;
    }

    class ViewHolder {
        @InjectView(R.id.tv_ask_title1)
        TextView tvAskTitle1;
        @InjectView(R.id.iv_ask_head2)
        ImageView ivAskHead2;
        @InjectView(R.id.tv_ask_listen_state2)
        TextView tvAskListenState2;
        @InjectView(R.id.tv_ask_listen_type1)
        TextView tvAskListenType1;
        @InjectView(R.id.tv_ask_listen_name1)
        TextView tvAskListenName1;
        @InjectView(R.id.tv_ask_listen_count1)
        TextView tvAskListenCount1;
        @InjectView(R.id.ll_listen)
        LinearLayout ll_listen;
        @InjectView(R.id.iv_anim)
        ImageView iv_anim;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
