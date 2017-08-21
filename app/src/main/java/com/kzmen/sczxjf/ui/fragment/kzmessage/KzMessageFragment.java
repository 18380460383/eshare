package com.kzmen.sczxjf.ui.fragment.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.KzActivGridAdapter;
import com.kzmen.sczxjf.adapter.KzMainColumnAdapter;
import com.kzmen.sczxjf.adapter.Kz_MainAskAdapter;
import com.kzmen.sczxjf.adapter.Kz_MainCourseAdapter;
import com.kzmen.sczxjf.bean.kzbean.MainColumnItemBean;
import com.kzmen.sczxjf.cusinterface.PlayMessage;
import com.kzmen.sczxjf.interfaces.MainAskListClick;
import com.kzmen.sczxjf.interfaces.MainCourseListClick;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.DataFactory;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.smartlayout.widgit.CustomLoadingLayout;
import com.kzmen.sczxjf.test.bean.Music;
import com.kzmen.sczxjf.test.bean.TstBean;
import com.kzmen.sczxjf.ui.activity.kzmessage.ActivListActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.CaseListActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.CourseListActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.CoursePlayDeatilActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.KnowageAskIndexActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.TestListActivity;
import com.kzmen.sczxjf.util.EToastUtil;
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
    @InjectView(R.id.bl_main_banner)
    BannerLayout blMainBanner;
    @InjectView(R.id.gv_column)
    ExPandGridView gvColumn;
    @InjectView(R.id.lv_course)
    MyListView lvCourse;
    @InjectView(R.id.ll_more_ask)
    LinearLayout llMoreAsk;
    @InjectView(R.id.gv_more_activ)
    ExPandGridView gvMoreActiv;
    @InjectView(R.id.ll_more_activ)
    LinearLayout llMoreActiv;
    @InjectView(R.id.ll_content)
    LinearLayout llContent;
    @InjectView(R.id.lv_ask)
    MyListView lvAsk;
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

    private String url = "http://cocopeng.com/img/bg-01.jpg";
    private String url2 = "http://cocopeng.com/img/bg-01.jpg";
    private String url1 = "http://192.168.0.101:8000/static/mp3/2.jpg";
    private String baseUrl1="www.cocopeng.com/";
    private String baseUrl2="http://192.168.0.101:8000/static/mp3/";
    protected CustomLoadingLayout mLayout; //SmartLoadingLayout对象

    private Kz_MainCourseAdapter kz_mainCourseAdapter;
    private List<String> listCourse;

    private Kz_MainAskAdapter kz_mainAskAdapter;
    private List<String> listAsk;

    private boolean isCourseClick=false;
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
        // lazyLoad();
        ButterKnife.inject(this, view);
        initView(view);
        return view;
    }

    private void initView(View vew) {
        blMainBanner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                EToastUtil.show(getActivity(), "" + position);
            }
        });
        initData();
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
        OkhttpUtilManager.get(getActivity(), "get/", "mainfragment", new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("onSuccess", type + "      " + data);
                List<TstBean> listBean = DataFactory.jsonToArrayList(data, TstBean.class);
                Log.e("onSuccess", "" + listBean.size() + "    " + listBean.toString());
            }

            @Override
            public void onError(int code, String msg) {
                Log.e("onError", msg);
                EToastUtil.show(getActivity(), "" + msg);
            }
        });
        listCourse = new ArrayList<>();
        listCourse.add("测试1");
        listCourse.add("测试2");
        kz_mainCourseAdapter = new Kz_MainCourseAdapter(getActivity(), listCourse, new MainCourseListClick() {
            @Override
            public void onPlay(int position) {
                isCourseClick=true;
                setMusilList();
                if(position==kz_mainCourseAdapter.getPlayPosition()){
                    playPause();
                }else{
                    playStart();
                }
            }

            @Override
            public void onClickXiaoJiang(int position) {
                startActivity(new Intent(getActivity(), CoursePlayDeatilActivity.class));
            }

            @Override
            public void onClickMore(int position) {
                startActivity(new Intent(getActivity(), CourseListActivity.class));
            }
        });
        lvCourse.setAdapter(kz_mainCourseAdapter);

        listAsk=new ArrayList<>();
        listAsk.add("问答测试1");
        listAsk.add("问答测试2");
        listAsk.add("问答测试3");
        kz_mainAskAdapter=new Kz_MainAskAdapter(getActivity(), listAsk, new MainAskListClick() {
            @Override
            public void onPosClick(int position) {
                isCourseClick=false;
                setMusic();
                if(position==kz_mainAskAdapter.getPlayPosition()){
                    playPause();
                }else{
                    playStart();
                }
            }
        });
        lvAsk.setAdapter(kz_mainAskAdapter);
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

    private void setMusic(){
        mMusicList.clear();
        Music musicp = new Music();
        musicp.setType(Music.Type.ONLINE);
        musicp.setPath(baseUrl2+"贫民百万歌星伴奏.mp3");
        mMusicList.add(musicp);
        AppContext.getPlayService().setMusicList(mMusicList);
        AppContext.getPlayService().setPlayMessage(this);
    }
    private void setMusilList() {
        mMusicList.clear();
        Music music = new Music();
        music.setType(Music.Type.ONLINE);
        music.setPath(baseUrl2+"贫民百万歌星伴奏.mp3");
        mMusicList.add(music);
        Music music1 = new Music();
        music1.setType(Music.Type.ONLINE);
        music1.setPath(baseUrl2+"fade.mp3");
        mMusicList.add(music1);
        Music music2 = new Music();
        music2.setType(Music.Type.ONLINE);
        music2.setPath(baseUrl2+"平凡之路.mp3");
        mMusicList.add(music2);
        Music music3 = new Music();
        music3.setType(Music.Type.ONLINE);
        music3.setPath(baseUrl2+"星语心愿.mp3");
        mMusicList.add(music3);
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
        if (kz_mainCourseAdapter != null && isCourseClick) {
            kz_mainCourseAdapter.prePercent(percent);
        }
    }

    @Override
    public void time(String start, String end, int pos) {
        if (kz_mainCourseAdapter != null && isCourseClick) {
            kz_mainCourseAdapter.time(start, end, pos);
        }
    }

    @Override
    public void playposition(int position) {

    }

    @Override
    public void state(int state) {
        if (kz_mainCourseAdapter != null && isCourseClick) {
            kz_mainCourseAdapter.state(state);
            kz_mainAskAdapter.setPlayPosition(-1);
            kz_mainAskAdapter.state(-1);
        }else if(kz_mainAskAdapter!=null){
            kz_mainAskAdapter.state(state);
            kz_mainCourseAdapter.setPlayPosition(-1);
            kz_mainCourseAdapter.state(-1);
        }
    }

    @OnClick({R.id.ll_more_ask, R.id.ll_more_activ})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_more_ask:
                intent = new Intent(getActivity(), KnowageAskIndexActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_more_activ:
                intent = new Intent(getActivity(), ActivListActivity.class);
                startActivity(intent);
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