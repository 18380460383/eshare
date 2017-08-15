package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.test.DBService;
import com.kzmen.sczxjf.test.Question;
import com.kzmen.sczxjf.test.adapter.AnserQuesAdapter;
import com.kzmen.sczxjf.test.bean.AnserItemBean;
import com.kzmen.sczxjf.test.bean.QuestionBean;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.EToastUtil;
import com.kzmen.sczxjf.view.MyListView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class TestDetailActivity extends SuperActivity {
    //数据库的名称
    private String DB_NAME = "question.db";
    //数据库的地址
    private String DB_PATH = "/data/data/com.kzmen.sczxjf/databases/";
    //总的题目数据
    private int count;
    //当前显示的题目
    private int corrent;
    //问题
    private TextView tv_title;

    //下一题
    private Button btn_down;

    private MyListView lv_question;
    private AnserQuesAdapter anserQuesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "题目[1/20]");
        initFile();
        initView();
        initDB();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_test_detail);
    }
    /**
     * 初始化View
     */
    private void initView() {

        lv_question = (MyListView) findViewById(R.id.lv_question);
        tv_title = (TextView) findViewById(R.id.tv_title);


        btn_down = (Button) findViewById(R.id.btn_down);

    }

    private List<QuestionBean> questionBeanList;
    private List<AnserItemBean> itemBeanList;

    /**
     * 初始化数据库服务
     */
    private void initDB() {
        DBService dbService = new DBService();
        final List<Question> list = dbService.getQuestion();
        questionBeanList = dbService.getQuestion1();
        itemBeanList = new ArrayList<>();
        if (questionBeanList != null) {
            itemBeanList = questionBeanList.get(0).getAnswerList();
            anserQuesAdapter = new AnserQuesAdapter(this, itemBeanList);
            anserQuesAdapter.setRightAnswer(questionBeanList.get(0).getAnswer());
            Log.e("tst", itemBeanList.toString());
        }
        if (anserQuesAdapter != null) {
            lv_question.setAdapter(anserQuesAdapter);
            lv_question.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    anserQuesAdapter.setClickCount();
                    if(anserQuesAdapter.getClickCount()==1){
                        anserQuesAdapter.setSelect(i);
                        anserQuesAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        count = list.size();
        corrent = 0;

        Question q = list.get(0);
        tv_title.setText(q.question);
        //下一题
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle(R.id.kz_tiltle, "题目["+corrent+"/20]");
                //判断是否为最后一题
                if (corrent < count - 1) {
                    corrent++;
                    Question q = list.get(corrent);
                    tv_title.setText(q.question);


                    itemBeanList.clear();
                    itemBeanList.addAll(questionBeanList.get(corrent).getAnswerList());
                    Log.e("tst", itemBeanList.toString());
                    EToastUtil.show(TestDetailActivity.this, "" + itemBeanList.size());
                    anserQuesAdapter.setRightAnswer(questionBeanList.get(corrent).getAnswer());
                    anserQuesAdapter.reseat();
                    anserQuesAdapter.notifyDataSetChanged();

                } else if (corrent == count - 1) {
                    new AlertDialog.Builder(TestDetailActivity.this).setTitle("提示").setMessage("已经到达最后一道题，是否提交？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(TestDetailActivity.this,TestResultActivity.class));
                                    finish();
                                }
                            }).setNegativeButton("取消", null).show();
                }
            }
        });
    }

    /**
     * 判断是否答题正确
     *
     * @param list
     * @return
     */
    private List<Integer> checkAnswer(List<Question> list) {
        List<Integer> wrongList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            //判断对错
            if (list.get(i).answer != list.get(i).selectedAnswer) {
                wrongList.add(i);
            }
        }
        return wrongList;
    }

    /**
     * 将数据库拷贝到相应目录
     */
    private void initFile() {
        //判断数据库是否拷贝到相应的目录下
        if (new File(DB_PATH + DB_NAME).exists() == false) {
            File dir = new File(DB_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }

            //复制文件
            try {
                InputStream is = getBaseContext().getAssets().open(DB_NAME);
                OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);

                //用来复制文件
                byte[] buffer = new byte[1024];
                //保存已经复制的长度
                int length;

                //开始复制
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

                //刷新
                os.flush();
                //关闭
                os.close();
                is.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
