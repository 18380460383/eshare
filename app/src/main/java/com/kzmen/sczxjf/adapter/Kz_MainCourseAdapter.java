package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.consta.PlayState;
import com.kzmen.sczxjf.interfaces.MainCourseListClick;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.kzmen.sczxjf.R.id.sb_play;

/**
 * Created by pjj18 on 2017/8/21.
 */

public class Kz_MainCourseAdapter extends BaseAdapter {
    private List<String> listData;
    private Context mContext;
    private MainCourseListClick mainCourseListClick;
    private int playPosition = -1;

    public Kz_MainCourseAdapter(Context mContext, List<String> listData, MainCourseListClick mainCourseListClick) {
        this.mContext = mContext;
        this.listData = listData;
        this.mainCourseListClick = mainCourseListClick;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.kz_main_course_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.sbPlay.setTag(position);
        viewHolder.sbPlay.setOnSeekBarChangeListener(new SeekBarChangeEvent());
        viewHolder.tvTitle.setText(listData.get(position));
        Glide.with(mContext).load(R.drawable.icon_user).into(viewHolder.ivUserHead);
        viewHolder.llXiaojiang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainCourseListClick != null) {
                    mainCourseListClick.onClickXiaoJiang(position);
                }
            }
        });
        viewHolder.llXiaojiang2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainCourseListClick != null) {
                    mainCourseListClick.onClickXiaoJiang(position);
                }
            }
        });
        viewHolder.ivCoursePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainCourseListClick != null) {
                    mainCourseListClick.onPlay(position);
                    playPosition = position;
                }
            }
        });
        viewHolder.llMoreCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainCourseListClick != null) {
                    mainCourseListClick.onClickMore(position);
                }
            }
        });
        if(position==playPosition){
            viewHolder.tvMediaStartTime.setText(start);
            viewHolder.tvMediaEndTime.setText(end);
            viewHolder.sbPlay.setProgress(pos);
            switch (state) {
                case PlayState.PLAY_PLAYING:
                    Glide.with(mContext).load(R.drawable.btn_player_pause).into(viewHolder.ivCoursePlay);
                    break;
                case PlayState.PLAY_PAUSE:
                    Glide.with(mContext).load(R.drawable.btn_player_play).into(viewHolder.ivCoursePlay);
                    break;
            }
        }else{
            Glide.with(mContext).load(R.drawable.btn_player_play).into(viewHolder.ivCoursePlay);
        }
        return convertView;
    }

    private int percent;
    private String start;
    private String end;
    private int pos;
    private int state;

    public void prePercent(int percent) {
        this.percent = percent;
        notifyDataSetChanged();
    }

    public void time(String start, String end, int pos) {
        this.start = start;
        this.end = end;
        this.pos = pos;
        notifyDataSetChanged();
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
        @InjectView(R.id.iv_user_head)
        ImageView ivUserHead;
        @InjectView(R.id.tv_user_identity)
        TextView tvUserIdentity;
        @InjectView(R.id.tv_user_name)
        TextView tvUserName;
        @InjectView(R.id.ll_user_head)
        LinearLayout llUserHead;
        @InjectView(R.id.tv_course_ex)
        TextView tvCourseEx;
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @InjectView(R.id.tv_media_start_time)
        TextView tvMediaStartTime;
        @InjectView(R.id.tv_media_end_time)
        TextView tvMediaEndTime;
        @InjectView(sb_play)
        SeekBar sbPlay;
        @InjectView(R.id.iv_course_play)
        ImageView ivCoursePlay;
        @InjectView(R.id.tv_xiaojiang_title1)
        TextView tvXiaojiangTitle1;
        @InjectView(R.id.tv_xiaogjiangtime1)
        TextView tvXiaogjiangtime1;
        @InjectView(R.id.ll_xiaojiang1)
        LinearLayout llXiaojiang1;
        @InjectView(R.id.tv_xiaojiang_title2)
        TextView tvXiaojiangTitle2;
        @InjectView(R.id.tv_xiaogjiangtime2)
        TextView tvXiaogjiangtime2;
        @InjectView(R.id.ll_xiaojiang2)
        LinearLayout llXiaojiang2;
        @InjectView(R.id.ll_more_course)
        LinearLayout llMoreCourse;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            if (((int)seekBar.getTag()) == playPosition) {
                this.progress = progress * (AppContext.getPlayService().mPlayer.getDuration())
                        / seekBar.getMax();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            if (((int)seekBar.getTag()) == playPosition) {
                AppContext.getPlayService().mPlayer.seekTo(progress);
            }
        }
    }
}
