package com.kzmen.sczxjf.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.mp3record.MP3Recorder;

public class Mp3RecorderActivity extends AppCompatActivity {
    private Button bt_start;
    private Button bt_end;
    private MP3Recorder recorder;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case MP3Recorder.MSG_REC_STARTED:
                    showMsg("开始录音");
                    break;
                case MP3Recorder.MSG_REC_STOPPED:
                    showMsg("结束录音");
                    break;
                case MP3Recorder.MSG_ERROR_CREATE_FILE:
                    showMsg("创建文件时扑街了");
                    break;
                case MP3Recorder.MSG_ERROR_REC_START:
                    showMsg("初始化录音器时扑街了");
                    break;
                case MP3Recorder.MSG_ERROR_WRITE_FILE:
                    showMsg("写文件时挂了");
                    break;
                case MP3Recorder.MSG_ERROR_AUDIO_ENCODE:
                    showMsg("编码时挂了");
                    break;
            }
            return false;
        }
    });

    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_mp3_recorder);
        setContentView(R.layout.activity_main_tab);
        /*initRecorder();
        initView();*/
    }

  /*  private void initRecorder() {

    }

    private void initView() {
        bt_start = (Button) findViewById(R.id.bt_start);
        bt_end = (Button) findViewById(R.id.bt_end);
        bt_start.setOnClickListener(this);
        bt_end.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                recorder = new MP3Recorder(8000);
                recorder.setFilePath("test111", "mp3");//录音保存目录
                recorder.setHandle(handler);
                recorder.start();//开始录音
                break;
            case R.id.bt_end:
                recorder.stop();//录音结束
                *//*String savePAth = Environment.getExternalStorageDirectory() + "/text.mp3";
                showMsg(savePAth);
                Log.e("tst",savePAth);
                File file1 = new File(savePAth);
                if (!file1.exists()) {
                    file1.mkdir();
                }
                if (!file1.exists()) {
                    try {
                        file1.createNewFile();
                    } catch (Exception e) {
                        Log.e("tst","---test------创建文件");
                        e.printStackTrace();
                    }
                }*//*
                break;

        }
    }*/

}
