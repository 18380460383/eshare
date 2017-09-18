package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kzmen.sczxjf.KzConstanst;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.CollectionAnswerBean;
import com.kzmen.sczxjf.consta.PlayState;
import com.kzmen.sczxjf.interfaces.PlayDetailOperate;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.kzmessage.KnowageAskDetailActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by pjj18 on 2017/9/15.
 */

public class KzCollectionAnswerAdapter extends BaseAdapter {
    private List<CollectionAnswerBean> listData;
    private Context mContext;
    private AnimationDrawable animationDrawable;
    private int state = -1;
    private PlayDetailOperate playDetailOperate;
    private int playPosition = -1;
    private CollectionAnswerBean bean;

    public KzCollectionAnswerAdapter(Context mContext, List<CollectionAnswerBean> listData, PlayDetailOperate playDetailOperate) {
        this.mContext = mContext;
        this.listData = listData;
        this.playDetailOperate = playDetailOperate;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.kz_fragment_collection_answer_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bean = listData.get(position);
        viewHolder.tvContent.setText(listData.get(position).getContent());
        Glide.with(mContext).load(bean.getAvatar()).into(viewHolder.ivAnswerImg);
        viewHolder.tvAnswerName.setText(bean.getUsername());
        if (bean.getTeacher().equals("1")) {
            viewHolder.tvAnswerDes.setText(bean.getTid_title());
        } else {
            viewHolder.tvSeprate.setVisibility(View.GONE);
            viewHolder.tvAnswerDes.setVisibility(View.GONE);
        }
        if (bean.getIsopen().equals("1")) {
            viewHolder.llListen.setBackgroundResource(R.drawable.bg_play_orange);
        } else {
            viewHolder.llListen.setBackgroundResource(R.drawable.bg_play_blue);
        }
        viewHolder.tvAskState.setText(bean.getMedia_button());
        viewHolder.tvMediaTime.setText(bean.getMedia_time() + "\"");

        viewHolder.llListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playDetailOperate != null) {
                    playPosition = position;
                    if (listData.get(position).getIsopen().equals("1")) {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("data[type]", "2");
                        params.put("data[aid]", listData.get(position).getQid());
                        OkhttpUtilManager.setOrder(mContext, KzConstanst.addEavesdropOrder, params);
                    } else {
                        playDetailOperate.doPlay(bean.getQid(), bean.getMedia());
                        playPosition = position;
                    }
                }
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, KnowageAskDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("qid", bean.getQid());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        animationDrawable = (AnimationDrawable) viewHolder.ivAnim
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

    public void updateOpen() {
        if (this.playPosition != -1) {
            listData.get(playPosition).setIsopen("0");
            listData.get(playPosition).setMedia_button("点击播放");
            this.notifyDataSetChanged();
        }
    }

    public int getPlayPosition() {
        return playPosition;
    }


    static class ViewHolder {
        @InjectView(R.id.tv_content)
        TextView tvContent;
        @InjectView(R.id.iv_answer_img)
        ImageView ivAnswerImg;
        @InjectView(R.id.tv_answer_name)
        TextView tvAnswerName;
        @InjectView(R.id.tv_seprate)
        TextView tvSeprate;
        @InjectView(R.id.tv_answer_des)
        TextView tvAnswerDes;
        @InjectView(R.id.iv_anim)
        ImageView ivAnim;
        @InjectView(R.id.tv_ask_state)
        TextView tvAskState;
        @InjectView(R.id.ll_listen)
        LinearLayout llListen;
        @InjectView(R.id.tv_media_time)
        TextView tvMediaTime;
        @InjectView(R.id.iv_collect)
        ImageView ivCollect;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
