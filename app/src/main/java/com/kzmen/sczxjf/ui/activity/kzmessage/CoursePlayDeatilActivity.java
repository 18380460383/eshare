package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.CourseListTstBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.consta.PlayState;
import com.kzmen.sczxjf.cusinterface.PlayMessage;
import com.kzmen.sczxjf.cusinterface.PlayPopuInterface;
import com.kzmen.sczxjf.popuwidow.Kz_PlayListPopu;
import com.kzmen.sczxjf.test.bean.Music;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.EToastUtil;
import com.kzmen.sczxjf.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;



public class CoursePlayDeatilActivity extends SuperActivity implements PlayMessage{

    @InjectView(R.id.tv_media_start_time)
    TextView tvMediaStartTime;
    @InjectView(R.id.tv_media_end_time)
    TextView tvMediaEndTime;
    @InjectView(R.id.sb_play)
    SeekBar sbPlay;
    @InjectView(R.id.lv_goodask)
    MyListView lvGoodask;
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.iv_share)
    ImageView ivShare;
    @InjectView(R.id.iv_play_list)
    ImageView ivPlayList;
    @InjectView(R.id.iv_play_pre)
    ImageView ivPlayPre;
    @InjectView(R.id.iv_play_play)
    ImageView ivPlayPlay;
    @InjectView(R.id.iv_play_next)
    ImageView ivPlayNext;
    @InjectView(R.id.iv_play_best)
    ImageView ivPlayBest;

    private List<Music> listPlay;
    private Kz_PlayListPopu playPop;
    private int bufferPercent=0;
    private int playPos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "初级课程");
        initView();
        initData();
        checkService();
    }

    private void initView() {
        sbPlay.setOnSeekBarChangeListener(new SeekBarChangeEvent());
    }
    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            this.progress = progress * (AppContext.getPlayService().mPlayer.getDuration())
                    / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            AppContext.getPlayService().mPlayer.seekTo(progress);
        }
    }

    @Override
    public void connectSuccess() {
        initPlayList();
    }
    private void initPlayList() {
        Music music = new Music();
        music.setType(Music.Type.ONLINE);
        music.setPath("http://192.168.0.102:8000/static/mp3/Dawn.mp3");
        listPlay.add(music);
        Music music1 = new Music();
        music1.setType(Music.Type.ONLINE);
        music1.setPath("http://192.168.0.102:8000/static/mp3/Fade.mp3");
        listPlay.add(music1);
        Music music2 = new Music();
        music2.setType(Music.Type.ONLINE);
        music2.setPath("http://192.168.0.102:8000/static/mp3/鬼迷心窍.mp3");
        listPlay.add(music2);
        AppContext.getPlayService().setMusicList(listPlay);
        AppContext.getPlayService().setPlayMessage(this);
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_small_talk_deatil);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppContext.getPlayService().stop();
    }

    private List<CourseListTstBean> beanlist;
    private CommonAdapter<CourseListTstBean> adapter2;

    private void initData() {
        listPlay = new ArrayList<>();
        beanlist = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            CourseListTstBean bean = new CourseListTstBean();
            if (i % 3 == 0) {
                bean.setType(0);
            } else {
                bean.setType(1);
            }
            bean.setName("测试" + i);
            bean.setTime("03:0" + i);
            beanlist.add(bean);
        }
        adapter2 = new CommonAdapter<CourseListTstBean>(CoursePlayDeatilActivity.this, R.layout.kz_good_ask_item, beanlist) {
            @Override
            protected void convert(ViewHolder viewHolder, CourseListTstBean item, int position) {
                viewHolder.setText(R.id.tv_user_name, "" + item.getName());
            }
        };
        lvGoodask.setAdapter(adapter2);
        setListViewHeightBasedOnChildren(lvGoodask);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获得adapter
        CommonAdapter<CourseListTstBean> adapter = (CommonAdapter) listView.getAdapter();
        if (adapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            //计算总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //计算分割线高度
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        //给listview设置高度
        listView.setLayoutParams(params);
    }

    WindowManager.LayoutParams params;


    public void showPopFormBottom(View view) {
        playPop= new Kz_PlayListPopu(this,listPlay);
//        设置Popupwindow显示位置（从底部弹出）
        playPop.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        params = getWindow().getAttributes();
        //当弹出Popupwindow时，背景变半透明
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        playPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        playPop.setPopuInterface(new PlayPopuInterface() {
            @Override
            public void onItemClic(int position, Music music) {
                playPostion(position);
            }
        });
        playPop.setPlayPos(playPos);
    }

    @OnClick({R.id.iv_share,R.id.iv_play_list, R.id.iv_play_pre, R.id.iv_play_play, R.id.iv_play_next, R.id.iv_play_best})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_play_list:
                showPopFormBottom(view);
                break;
            case R.id.iv_play_pre:
                if(playPos<1){
                    EToastUtil.show(CoursePlayDeatilActivity.this,"当前是第一首");
                }else{
                    playPos--;
                    playPostion(playPos);
                }
                break;
            case R.id.iv_play_play:
                playPause();

                break;
            case R.id.iv_play_next:
                if(playPos>=listPlay.size()-1){
                    EToastUtil.show(CoursePlayDeatilActivity.this,"当前是最后一首");
                }else{
                    playPos++;
                    playPostion(playPos);
                }
                break;
            case R.id.iv_play_best:
                break;
            case R.id.iv_share:
                break;
        }
    }
    private void playPause(){
        AppContext.getPlayService().playPause();
    }
    private void playStart(){
        AppContext.getPlayService().playStart();
    }
    private void playPostion(int position){
        AppContext.getPlayService().play(position);
    }
    @Override
    public void prePercent(int percent) {
        bufferPercent=percent;
        sbPlay.setSecondaryProgress(percent);
    }

    @Override
    public void time(String start, String end, int pos) {
        tvMediaStartTime.setText(start);
        tvMediaEndTime.setText(end);
        sbPlay.setProgress(pos);
        sbPlay.setSecondaryProgress(bufferPercent);
    }
    @Override
    public void playposition(int position) {
        playPos=position;
        if(playPos==0){
            Glide.with(this).load(R.drawable.btn_player_prev_unclick).into(ivPlayPre);
        }else if(playPos>=listPlay.size()-1){
            Glide.with(this).load(R.drawable.btn_player_next_unclick).into(ivPlayNext);
        }else{
            Glide.with(this).load(R.drawable.btn_player_prev).into(ivPlayPre);
            Glide.with(this).load(R.drawable.btn_player_next).into(ivPlayNext);
        }
    }
    @Override
    public void state(int state) {
        if(isDestroyed()){
            return;
        }
        switch (state){
            case PlayState.PLAY_PLAYING:
                Glide.with(this).load(R.drawable.btn_player_pause).into(ivPlayPlay);
                break;
            case PlayState.PLAY_PAUSE:
                Glide.with(this).load(R.drawable.btn_player_play).into(ivPlayPlay);
                break;
        }
    }
}