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
import com.kzmen.sczxjf.bean.kzbean.CouseQuestionBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.consta.PlayState;
import com.kzmen.sczxjf.interfaces.PlayDetailOperate;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.CircleImageView;
import com.kzmen.sczxjf.view.MyListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by pjj18 on 2017/9/10.
 */

public class KzCoursePlayDetailAdapter extends BaseAdapter {
    private List<CouseQuestionBean> listData;
    private Context mContext;
    private PlayDetailOperate playDetailOperate;
    private String playPosition = "-1";
    AnimationDrawable animationDrawable;
    private int playPos = -1;
    private int playZuiPos = -1;
    private int playType = 1;


    private int dzPos = -1;
    private int dzZuiPos = -1;

    public KzCoursePlayDetailAdapter(List<CouseQuestionBean> listData, Context mContext, PlayDetailOperate playDetailOperate) {
        this.listData = listData;
        this.mContext = mContext;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.kz_good_ask_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final CouseQuestionBean item = listData.get(position);
        viewHolder.tvTitle.setText(item.getContent());
        Glide.with(mContext).load(item.getAnswer_avatar()).into(viewHolder.ivAnswerImg);
        Glide.with(mContext).load(item.getAvatar()).into(viewHolder.ivUserHead);
        viewHolder.tvTitle.setText(item.getAnswer_avatar());
        viewHolder.tvUserName.setText(item.getUsername());
        viewHolder.tvTime.setText(item.getDatetime());
        viewHolder.tvZans.setText(item.getZans());
        viewHolder.tvViews.setText(item.getViews() + "人听过");
        viewHolder.tv_ask_listen_state2.setText(item.getMedia_button());
        viewHolder.tvContent.setText(item.getAnswer_content());
        viewHolder.tvTitle.setText(item.getContent());
        viewHolder.llListens.setBackgroundResource(R.drawable.bg_play_blue);
        viewHolder.ivZans.setBackgroundResource(R.drawable.btn_like);
        if (item.getIszan() == 1) {
            viewHolder.ivZans.setBackgroundResource(R.drawable.btn_like_current);
        }
        if (item.getIsopen().equals(KzConstanst.IS_TRUE)) {
            viewHolder.llListens.setBackgroundResource(R.drawable.bg_play_green);
        }
        final AnimationDrawable animationDrawable = (AnimationDrawable) viewHolder.iv_anim
                .getDrawable();
        if (item.getAnswer_media().equals(media_name) && item.getQid().equals(playPosition)) {
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

        viewHolder.llListens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPos = position;
                playType = 2;
                if (item.getIsopen().equals(KzConstanst.IS_TRUE)) {
                    //doPay(item.getMedia_money());
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("data[type]", "2");
                    params.put("data[aid]", item.getAnswer_id());
                    OkhttpUtilManager.setOrder(mContext, KzConstanst.addEavesdropOrder, params);
                } else {
                    if (playDetailOperate != null) {
                        playPosition = item.getQid();
                        media_name = item.getAnswer_media();
                        playDetailOperate.doPlay("" + item.getQid(), item.getAnswer_media());
                    }
                }
            }
        });

        viewHolder.imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showInputFormBottom(v);
                if (playDetailOperate != null) {
                    playDetailOperate.doInput(v, item.getQid());
                }
            }
        });
        viewHolder.ivZans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dzPos = position;
                if (playDetailOperate != null) {
                    playDetailOperate.doZan("2", item.getQid(), item.getIszan() == 1 ? "0" : "1");
                }
            }
        });

        if (TextUtil.isEmpty(item.getAnswer_media())) {
            viewHolder.llTxt.setVisibility(View.VISIBLE);
            viewHolder.llMedia.setVisibility(View.GONE);

        } else {
            viewHolder.llTxt.setVisibility(View.GONE);
            viewHolder.llMedia.setVisibility(View.VISIBLE);
        }

        viewHolder.lvAddQuestion.setVisibility(View.GONE);
        if (item.getZhuijia_list() != null) {
            viewHolder.lvAddQuestion.setVisibility(View.VISIBLE);
            viewHolder.lvAddQuestion.setAdapter(new CommonAdapter<CouseQuestionBean.ZhuijiaListBean>(mContext, R.layout.kz_question_list_item, item.getZhuijia_list()) {
                @Override
                protected void convert(com.kzmen.sczxjf.commonadapter.ViewHolder viewHolder, final CouseQuestionBean.ZhuijiaListBean item, final int pos) {
                    viewHolder.setText(R.id.tv_user_name, "" + item.getContent())
                            .glideImage(R.id.iv_user_head, item.getAvatar())
                            .glideImage(R.id.iv_answer_img, item.getAnswer_avatar())
                            .setText(R.id.tv_user_name, item.getUsername())
                            .setText(R.id.tv_time, "" + item.getDatetime())
                            .setText(R.id.tv_zans, "" + item.getZans())
                            .setText(R.id.tv_views, "" + item.getViews() + "人听过")
                            .setText(R.id.tv_ask_listen_state2, item.getMedia_button())
                            .setText(R.id.tv_content, item.getAnswer_content());
                    viewHolder.getView(R.id.ll_listens).setBackgroundResource(R.drawable.bg_play_blue);
                    final AnimationDrawable animationDrawable = (AnimationDrawable) ((ImageView) viewHolder.getView(R.id.iv_anim))
                            .getDrawable();
                    if (item.getIsopen().equals(KzConstanst.IS_FASLE)) {
                        viewHolder.getView(R.id.ll_listens).setBackgroundResource(R.drawable.bg_play_green);
                    }
                    viewHolder.getView(R.id.iv_zans).setBackgroundResource(R.drawable.btn_like);
                    if (item.getIszan() == 1) {
                        viewHolder.getView(R.id.iv_zans).setBackgroundResource(R.drawable.btn_like_current);
                    }
                    viewHolder.getView(R.id.ll_listens).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            playType = position;
                            playZuiPos = pos;
                            playType = 2;
                            if (item.getIsopen().equals(KzConstanst.IS_TRUE)) {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("data[type]", "2");
                                params.put("data[aid]", item.getAnswer_id());
                                OkhttpUtilManager.setOrder(mContext, KzConstanst.addEavesdropOrder, params);
                            } else {
                                if (playDetailOperate != null) {
                                    playPosition = item.getQid();
                                    media_name = item.getAnswer_media();
                                    playDetailOperate.doPlay("" + item.getQid(), item.getAnswer_media());
                                }
                            }
                        }
                    });
                    viewHolder.getView(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (playDetailOperate != null) {
                                playDetailOperate.doInput(v, item.getQid());
                            }
                        }
                    });
                    viewHolder.getView(R.id.iv_zans).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dzPos = position;
                            dzZuiPos = pos;
                            if (playDetailOperate != null) {
                                playDetailOperate.doZan("2", item.getQid(), item.getIszan() == 1 ? "0" : "1");
                            }
                        }
                    });
                    if (TextUtil.isEmpty(item.getAnswer_media())) {
                        viewHolder.getView(R.id.ll_txt).setVisibility(View.VISIBLE);
                        viewHolder.getView(R.id.ll_media).setVisibility(View.GONE);
                    } else {
                        viewHolder.getView(R.id.ll_txt).setVisibility(View.GONE);
                        viewHolder.getView(R.id.ll_media).setVisibility(View.VISIBLE);
                    }

                    if (item.getAnswer_media().equals(media_name) && item.getQid().equals(playPosition)) {
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
                }
            });
        }
        return convertView;
    }

    private String media_name;
    private int state;

    public void setState(int state) {
        this.state = state;
    }

    public void setMediaName(String media_name) {
        this.media_name = media_name;
    }

    public String getMedia_name() {
        return media_name;
    }

    public int getState() {
        return state;
    }

    public String getPlayPosition() {
        return playPosition;
    }

    public void setPlayPosition(String playPosition) {
        this.playPosition = playPosition;
    }

    private boolean isup = false;

    public void setUpdata(boolean isup) {
        this.isup = isup;
    }

    public void updateOpen() {
        if (playType == 1) {
            listData.get(playPos).setIsopen("0");
            listData.get(playPos).setMedia_button("点击播放");
        } else {
            listData.get(playPos).getZhuijia_list().get(playZuiPos).setIsopen("0");
            listData.get(playPos).getZhuijia_list().get(playZuiPos).setMedia_button("点击播放");
        }
        this.notifyDataSetChanged();
    }

    public void upZan() {
        if (dzZuiPos == -1 && dzPos == -1) {
            return;
        }
        if (dzZuiPos == -1 && dzPos != -1) {
            int zan = listData.get(dzPos).getIszan();
            listData.get(dzPos).setIszan(zan == 1 ? 0 : 1);
        } else {
            int zan = listData.get(dzPos).getZhuijia_list().get(dzZuiPos).getIszan();
            listData.get(dzPos).getZhuijia_list().get(dzZuiPos).setIszan(zan == 1 ? 0 : 1);
        }
        this.notifyDataSetChanged();
    }

    static class ViewHolder {
        @InjectView(R.id.iv_user_head)
        CircleImageView ivUserHead;
        @InjectView(R.id.tv_user_name)
        TextView tvUserName;
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @InjectView(R.id.tv_time)
        TextView tvTime;
        @InjectView(R.id.tv_zans)
        TextView tvZans;
        @InjectView(R.id.iv_zans)
        ImageView ivZans;
        @InjectView(R.id.imageView4)
        ImageView imageView4;
        @InjectView(R.id.iv_anim)
        ImageView iv_anim;
        @InjectView(R.id.iv_answer_img)
        CircleImageView ivAnswerImg;
        @InjectView(R.id.tv_ask_listen_state2)
        TextView tv_ask_listen_state2;
        @InjectView(R.id.ll_listens)
        LinearLayout llListens;
        @InjectView(R.id.tv_views)
        TextView tvViews;
        @InjectView(R.id.ll_media)
        LinearLayout llMedia;
        @InjectView(R.id.tv_content)
        TextView tvContent;
        @InjectView(R.id.ll_txt)
        LinearLayout llTxt;
        @InjectView(R.id.lv_add_question)
        MyListView lvAddQuestion;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
