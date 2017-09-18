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
import com.kzmen.sczxjf.KzConstanst;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.KnowageAskDetailBean;
import com.kzmen.sczxjf.consta.PlayState;
import com.kzmen.sczxjf.interfaces.PlayDetailOperate;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.vondear.rxtools.RxLogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by pjj18 on 2017/9/16.
 */

public class Kz_KnowageDetailAdapter extends BaseAdapter {
    private List<KnowageAskDetailBean.AnswerBean> answerList;
    private Context mContext;
    private PlayDetailOperate playDetailOperate;
    private int state;
    private String mediaName = "";
    private int zanPos = -1;
    private int collectPos = -1;
    private int playPos = -1;

    public Kz_KnowageDetailAdapter(Context mContext, List<KnowageAskDetailBean.AnswerBean> answerList, PlayDetailOperate playDetailOperate) {
        this.mContext = mContext;
        this.answerList = answerList;
        this.playDetailOperate = playDetailOperate;
    }

    @Override
    public int getCount() {
        return answerList.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.kz_ask_detai_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final KnowageAskDetailBean.AnswerBean item = answerList.get(position);
        Glide.with(mContext).load(item.getAvatar()).into(viewHolder.ivAnswerImg);
        viewHolder.tvAnswerName.setText(item.getUsername());
        viewHolder.tvAnswerDes.setText(item.getTid_title());
        viewHolder.tvPrice.setText(item.getMoney());
        viewHolder.tvAskState.setText(item.getMedia_button());
        viewHolder.tvTime.setText(item.getDatetime());
        viewHolder.tvLike.setText(item.getZans());
        viewHolder.tvViews.setText(item.getViews() + "人听过");
        viewHolder.tvMediaTime.setText(item.getMedia_time() + "\"");

        viewHolder.tvPrice.setVisibility(View.INVISIBLE);
        viewHolder.ivIsright.setVisibility(View.INVISIBLE);

        if (item.getOk().equals("1")) {
            viewHolder.tvPrice.setVisibility(View.VISIBLE);
            viewHolder.ivIsright.setVisibility(View.VISIBLE);

        }
        if (item.getMedia_status() == 0) {
            viewHolder.llListen.setBackgroundResource(R.drawable.bg_yellow);
        } else {
            viewHolder.llListen.setBackgroundResource(R.drawable.bg_play_blue);
        }
        viewHolder.ivCollect.setBackgroundResource(R.drawable.btn_collect);
        if (item.getIscollect() == 1) {
            viewHolder.ivCollect.setBackgroundResource(R.drawable.btn_collect_current);
        }
        viewHolder.ivLike.setBackgroundResource(R.drawable.btn_like);
        if (item.getIszan() == 1) {
            viewHolder.ivLike.setBackgroundResource(R.drawable.btn_like_current);
        }
        viewHolder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zanPos = position;
                if (playDetailOperate != null) {
                    int zan = item.getIszan();
                    playDetailOperate.doZan("4", item.getAid(), zan == 1 ? "0" : "1");
                }
            }
        });
        viewHolder.ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectPos = position;
                if (playDetailOperate != null) {
                    int iscollect = item.getIscollect();
                    playDetailOperate.doCollect("4", item.getAid(), iscollect == 1 ? "0" : "1");
                }
            }
        });
        viewHolder.llListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPos = position;
                if (item.getMedia_status() == 0) {
                    // RxToast.normal("当前回答不可偷听");
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("data[type]", "2");
                    params.put("data[aid]", item.getAid());
                    OkhttpUtilManager.setOrder(mContext, KzConstanst.addEavesdropOrder, params);
                } else/* if (knowageBean.getIsopen().equals("0"))*/ {
                    if (playDetailOperate != null) {
                        playDetailOperate.doPlay(item.getAid(), item.getMedia());
                    }
                }
            }
        });

        final AnimationDrawable animationDrawable = (AnimationDrawable) (viewHolder.ivAnim)
                .getDrawable();
        if (item.getAid().equals(mediaName)) {
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

    public void updatePlay() {
        if (playPos != -1) {
            RxLogUtils.e("tst", "播放更新");
            answerList.get(playPos).setMedia_status(1);
        }
        this.notifyDataSetChanged();
    }

    public void updateZan() {
        if (zanPos != -1) {
            RxLogUtils.e("tst", "点赞更新");
            int zan = answerList.get(zanPos).getIszan();
            answerList.get(zanPos).setIszan(zan == 1 ? 0 : 1);
        }
        this.notifyDataSetChanged();
    }

    public void updateCollect() {
        if (collectPos != -1) {
            RxLogUtils.e("tst", "收藏更新");
            int iscollect = answerList.get(collectPos).getIscollect();
            answerList.get(collectPos).setIscollect(iscollect == 1 ? 0 : 1);
        }
        this.notifyDataSetChanged();
    }

    public void setState(int state) {
        this.state = state;
        this.notifyDataSetChanged();
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_answer_img)
        ImageView ivAnswerImg;
        @InjectView(R.id.tv_answer_name)
        TextView tvAnswerName;
        @InjectView(R.id.tv_seprate)
        TextView tvSeprate;
        @InjectView(R.id.tv_answer_des)
        TextView tvAnswerDes;
        @InjectView(R.id.tv_price)
        TextView tvPrice;
        @InjectView(R.id.iv_isright)
        ImageView ivIsright;
        @InjectView(R.id.iv_anim)
        ImageView ivAnim;
        @InjectView(R.id.tv_ask_state)
        TextView tvAskState;
        @InjectView(R.id.ll_listen)
        LinearLayout llListen;
        @InjectView(R.id.tv_media_time)
        TextView tvMediaTime;
        @InjectView(R.id.tv_time)
        TextView tvTime;
        @InjectView(R.id.iv_collect)
        ImageView ivCollect;
        @InjectView(R.id.tv_like)
        TextView tvLike;
        @InjectView(R.id.iv_like)
        ImageView ivLike;
        @InjectView(R.id.tv_views)
        TextView tvViews;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
