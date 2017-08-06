package com.kzmen.sczxjf.test;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.test.bean.Music;
import com.kzmen.sczxjf.test.server.PlayService;

import java.util.ArrayList;
import java.util.List;

import static com.kzmen.sczxjf.AppContext.getPlayService;

public class Mp3ServerAcitivity extends AppCompatActivity implements View.OnClickListener {
    private ServiceConnection mPlayServiceConnection;
    protected Handler mHandler = new Handler(Looper.getMainLooper());
    private Button bt_start;
    private Button bt_next;
    private Button bt_pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3_server_acitivity);
        initView();
        checkService();
    }

    private void initView() {
        bt_pause = (Button) findViewById(R.id.bt_pause);
        bt_next = (Button) findViewById(R.id.bt_next);
        bt_start = (Button) findViewById(R.id.bt_start);
        bt_start.setOnClickListener(this);
        bt_pause.setOnClickListener(this);
        bt_next.setOnClickListener(this);
    }

    private void checkService() {
        if (getPlayService() == null) {
            startService();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bindService();
                }
            }, 1000);
        }
    }

    private void startService() {
        Intent intent = new Intent(this, PlayService.class);
        startService(intent);
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, PlayService.class);
        mPlayServiceConnection = new PlayServiceConnection();
        bindService(intent, mPlayServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                play();
                break;
            case R.id.bt_next:
                next();
                break;
            case R.id.bt_pause:
               List<Music> mMusicList = new ArrayList<>();
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
                music2.setPath("http://192.168.0.102:8000/static/mp3/Faded.mp3");
                mMusicList.add(music2);
                AppContext.getPlayService().setMusicList(mMusicList);
                break;
        }
    }

    private class PlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            final PlayService playService = ((PlayService.PlayBinder) service).getService();
            AppContext.setPlayService(playService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }

    private void play() {
        AppContext.getPlayService().playPause();
    }

    private void next() {
        AppContext.getPlayService().next();
    }


}
