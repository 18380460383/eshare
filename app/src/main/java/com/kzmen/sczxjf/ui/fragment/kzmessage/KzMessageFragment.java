package com.kzmen.sczxjf.ui.fragment.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.KzActivGridAdapter;
import com.kzmen.sczxjf.adapter.KzMainColumnAdapter;
import com.kzmen.sczxjf.adapter.MainBaseAdapter;
import com.kzmen.sczxjf.bean.kzbean.MainColumnItemBean;
import com.kzmen.sczxjf.consta.PlayState;
import com.kzmen.sczxjf.cusinterface.PlayMessage;
import com.kzmen.sczxjf.test.bean.Music;
import com.kzmen.sczxjf.ui.activity.kzmessage.ActivListActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.AskListActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.CaseListActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.CourseListActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.CoursePlayDeatilActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.KnowageAskIndexActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.TestListActivity;
import com.kzmen.sczxjf.util.EToastUtil;
import com.kzmen.sczxjf.util.glide.GlideCircleTransform;
import com.kzmen.sczxjf.util.glide.GlideRoundTransform;
import com.kzmen.sczxjf.view.ExPandGridView;
import com.kzmen.sczxjf.view.MyListView;
import com.kzmen.sczxjf.view.banner.BannerLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * 卡掌门--掌信端
 */
public class KzMessageFragment extends Fragment implements PlayMessage {

    MyListView lv_main;
    @InjectView(R.id.bl_main_banner)
    BannerLayout blMainBanner;
    @InjectView(R.id.gv_column)
    ExPandGridView gvColumn;
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

    @InjectView(R.id.tv_xiaojiang_title1)
    TextView tvXiaojiangTitle1;
    @InjectView(R.id.iv_xiaojiang_play1)
    ImageView ivXiaojiangPlay1;
    @InjectView(R.id.tv_xiaojiang_title2)
    TextView tvXiaojiangTitle2;
    @InjectView(R.id.iv_xiaojiang_play2)
    ImageView ivXiaojiangPlay2;
    @InjectView(R.id.ll_more_course)
    LinearLayout llMoreCourse;
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
    @InjectView(R.id.tv_ask_title2)
    TextView tvAskTitle2;
    @InjectView(R.id.iv_ask_head1)
    ImageView ivAskHead1;
    @InjectView(R.id.tv_ask_listen_state1)
    TextView tvAskListenState1;
    @InjectView(R.id.tv_ask_listen_type2)
    TextView tvAskListenType2;
    @InjectView(R.id.tv_ask_listen_name2)
    TextView tvAskListenName2;
    @InjectView(R.id.tv_ask_listen_count2)
    TextView tvAskListenCount2;
    @InjectView(R.id.ll_more_ask)
    LinearLayout llMoreAsk;
    @InjectView(R.id.gv_more_activ)
    ExPandGridView gvMoreActiv;
    @InjectView(R.id.ll_more_activ)
    LinearLayout llMoreActiv;
    @InjectView(R.id.sb_play)
    SeekBar sb_play;
    @InjectView(R.id.iv_course_play)
    ImageView ivCoursePlay;
    @InjectView(R.id.iv_user_head2)
    ImageView ivUserHead2;
    @InjectView(R.id.tv_user_identity2)
    TextView tvUserIdentity2;
    @InjectView(R.id.tv_user_name2)
    TextView tvUserName2;
    @InjectView(R.id.ll_user_head2)
    LinearLayout llUserHead2;
    @InjectView(R.id.tv_course_ex2)
    TextView tvCourseEx2;
    @InjectView(R.id.tv_title2)
    TextView tvTitle2;
    @InjectView(R.id.tv_media_start_time2)
    TextView tvMediaStartTime2;
    @InjectView(R.id.tv_media_end_time1)
    TextView tvMediaEndTime1;
    @InjectView(R.id.sb_play1)
    SeekBar sbPlay1;
    @InjectView(R.id.iv_course_play2)
    ImageView ivCoursePlay2;
    @InjectView(R.id.tv_xiaojiang_title3)
    TextView tvXiaojiangTitle3;
    @InjectView(R.id.iv_xiaojiang_play3)
    ImageView ivXiaojiangPlay3;
    @InjectView(R.id.tv_xiaojiang_title4)
    TextView tvXiaojiangTitle4;
    @InjectView(R.id.iv_xiaojiang_play4)
    ImageView ivXiaojiangPlay4;
    @InjectView(R.id.ll_more_course2)
    LinearLayout llMoreCourse2;
    private View view = null;
    private BannerLayout bl_main_banner;
    private List<String> urlList;
    private GridView gv_column;
    private List<String> listTst;
    private List<MainColumnItemBean> columnItemBeanList;
    private List<String> listTstActiv;
    private KzMainColumnAdapter kzMainColumnAdapter;
    private KzActivGridAdapter kzActivGridAdapter;
    private List<Music> mMusicList;

    private String url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
    private String url1 = "http://192.168.0.102:8000/static/mp3/2.jpg";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_kz_message, container, false);
        }
        initView(view);
        ButterKnife.inject(this, view);
        return view;
    }

    private void initView(View vew) {
        lv_main = (MyListView) vew.findViewById(R.id.lv_main);
        View headview = LayoutInflater.from(getActivity()).inflate(R.layout.kz_main_fragment_head, null, false);
        ButterKnife.inject(this, headview);
        blMainBanner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                EToastUtil.show(getActivity(), "" + position);
            }
        });
        lv_main.setAdapter(new MainBaseAdapter(getActivity()));
        lv_main.addHeaderView(headview);
        initData();
        sb_play.setOnSeekBarChangeListener(new SeekBarChangeEvent());
    }

    private void initData() {
        mMusicList = new ArrayList<>();
        urlList = new ArrayList<>();
        listTst = new ArrayList<>();
        columnItemBeanList = new ArrayList<>();
        listTstActiv = new ArrayList<>();
        urlList.add(url1);
        urlList.add(url1);
        urlList.add(url1);
        urlList.add(url1);
        urlList.add(url1);
        blMainBanner.setViewUrls(urlList);

        columnItemBeanList.add(new MainColumnItemBean("课程", R.drawable.menu_lesson));
        columnItemBeanList.add(new MainColumnItemBean("问答", R.drawable.menu_interlocution));
        columnItemBeanList.add(new MainColumnItemBean("测评", R.drawable.menu_evaluation));
        columnItemBeanList.add(new MainColumnItemBean("活动", R.drawable.menu_activity));
        columnItemBeanList.add(new MainColumnItemBean("案例", R.drawable.menu_case));

        kzMainColumnAdapter = new KzMainColumnAdapter(getActivity(), columnItemBeanList);
        gvColumn.setAdapter(kzMainColumnAdapter);
        listTstActiv.add("测试1");
        listTstActiv.add("测试1");
        listTstActiv.add("测试1");
        listTstActiv.add("测试1");
        kzActivGridAdapter = new KzActivGridAdapter(getActivity(), listTstActiv);
        gvMoreActiv.setAdapter(kzActivGridAdapter);
        gvColumn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(getActivity(), CourseListActivity.class);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), KnowageAskIndexActivity.class);
                        break;
                    case 2:
                        intent = new Intent(getActivity(), TestListActivity.class);
                        break;
                    case 3:
                        intent = new Intent(getActivity(), ActivListActivity.class);
                        break;
                    case 4:
                        intent = new Intent(getActivity(), CaseListActivity.class);
                        break;
                }
                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
        Glide.with(getActivity()).load(R.drawable.icon_user1).transform(new GlideRoundTransform(getActivity(), 10)).into(ivUserHead);
        Glide.with(getActivity()).load(R.drawable.icon_user).transform(new GlideCircleTransform(getActivity())).into(ivAskHead1);
        Glide.with(getActivity()).load(R.drawable.icon_user).transform(new GlideCircleTransform(getActivity())).into(ivAskHead2);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        if (AppContext.getPlayService() != null) {
            AppContext.getPlayService().stop();
        }
    }

    private int bufferPercent = 0;

    @OnClick({R.id.iv_course_play, R.id.iv_xiaojiang_play1, R.id.iv_xiaojiang_play2, R.id.ll_more_course, R.id.ll_more_ask, R.id.ll_more_activ, R.id.iv_course_play2, R.id.ll_more_course2})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_course_play:
                setMusilList();
                playPause();
                break;
            case R.id.iv_xiaojiang_play1:
                mMusicList.clear();
                Music musicp = new Music();
                musicp.setType(Music.Type.ONLINE);
                musicp.setPath("http://192.168.0.102:8000/static/mp3/Dawn.mp3");
                mMusicList.add(musicp);
                AppContext.getPlayService().setMusicList(mMusicList);
                playStart();
                break;
            case R.id.iv_xiaojiang_play2:
                intent = new Intent(getActivity(), CoursePlayDeatilActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_more_course:
                intent = new Intent(getActivity(), CourseListActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_more_ask:
                intent = new Intent(getActivity(), AskListActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_more_activ:
                intent = new Intent(getActivity(), ActivListActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_course_play2:
                setMusilList();
                playPause();
                break;
            case R.id.ll_more_course2:
                intent = new Intent(getActivity(), ActivListActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void setMusilList(){
        mMusicList.clear();
        Music music = new Music();
        music.setType(Music.Type.ONLINE);
        music.setPath("http://192.168.0.102:8000/static/mp3/Dawn.mp3");
        mMusicList.add(music);
        Music music1 = new Music();
        music1.setType(Music.Type.ONLINE);
        music1.setPath("http://192.168.0.102:8000/static/mp3/Fade.mp3");
        mMusicList.add(music1);
        Music music2 = new Music();
        music2.setType(Music.Type.ONLINE);
        music2.setPath("http://192.168.0.102:8000/static/mp3/鬼迷心窍.mp3");
        mMusicList.add(music2);
        AppContext.getPlayService().setMusicList(mMusicList);
        AppContext.getPlayService().setPlayMessage(this);
    }
    private void playPause() {
        AppContext.getPlayService().playPause();
    }

    private void playStart() {
        AppContext.getPlayService().playStart();
    }

    private void playPostion(int position) {
        AppContext.getPlayService().play(position);
    }

    @Override
    public void prePercent(int percent) {
        bufferPercent = percent;
        sb_play.setSecondaryProgress(percent);
    }

    @Override
    public void time(String start, String end, int pos) {
        tvMediaStartTime.setText(start);
        tvMediaEndTime.setText(end);
        sb_play.setProgress(pos);
        // sb_play.setSecondaryProgress(bufferPercent);
    }

    @Override
    public void playposition(int position) {

    }

    @Override
    public void state(int state) {
        switch (state) {
            case PlayState.PLAY_PLAYING:
                Glide.with(this).load(R.drawable.btn_player_pause).into(ivCoursePlay);
                break;
            case PlayState.PLAY_PAUSE:
                Glide.with(this).load(R.drawable.btn_player_play).into(ivCoursePlay);
                break;
        }
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
}
