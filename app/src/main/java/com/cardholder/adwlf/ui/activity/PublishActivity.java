package com.cardholder.adwlf.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cardholder.adwlf.R;
import com.cardholder.adwlf.adapter.PublishAdapter;
import com.cardholder.adwlf.base.PublishItem;
import com.cardholder.adwlf.net.NetworkDownload;
import com.cardholder.adwlf.util.EIntentUtil;
import com.cardholder.adwlf.util.EToastUtil;
import com.cardholder.adwlf.util.EshareLoger;
import com.cardholder.adwlf.util.PublishUtils;
import com.cardholder.adwlf.utils.FileUtils;
import com.cardholder.adwlf.view.DataView;
import com.cardholder.adwlf.view.EshareDialogFragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 说明：
 * note：
 * Created by FuPei
 * on 2015/12/8 at 14:27
 */
public class PublishActivity extends Activity {

    /**请求添加图库选择*/
    public static final int REQUEST_CODE_IMG = 1001;
    /**请求添加文字*/
    public static final int REQUEST_CODE_TEXT = 1002;
    /**请求裁剪图片*/
    public static final int REQUEST_CODE_CROP = 1003;
    /**请求拍照*/
    public static final int REQUEST_CODE_TAKE = 1004;
    /**截图的存放路径*/
    public static final String PATH_IMG = FileUtils.getImagePath().getAbsolutePath();
    /**web网页保存的文件*/
    public static final String PATH_WEB = PATH_IMG + "index.html";
    /**图片中转文件*/
    public static final Uri URI_TMP = Uri.fromFile(new File(PATH_IMG + "tmp.jpg"));

    @InjectView(R.id.title_name)
    TextView tv_name;
    @InjectView(R.id.title_back)
    ImageView iv_back;
    /**ListView*/
    private ListView lv;
    /**列表的全部数据*/
    private List<PublishItem> listdata;
    /**添加图片*/
    private Button iv_img;
    /**添加文字*/
    private Button iv_text;
    /**发布模板*/
    private Button iv_send;
    /**信息适配器*/
    private PublishAdapter mAdapter;
    /** Html工具类 */
    private PublishUtils util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setData();
        setListener();
    }

    private void initView() {
        setContentView(R.layout.activity_publish);
        ButterKnife.inject(this);
        lv = (ListView) findViewById(R.id.activity_publish_lv);
        iv_img = (Button) findViewById(R.id.pubish_iv_img);
        iv_text = (Button) findViewById(R.id.publish_iv_text);
        iv_send = (Button) findViewById(R.id.publish_iv_preview);
        //设置无数据显示
        new DataView(PublishActivity.this, lv, new DataView.onLoadUi() {
            @Override
            public String getText() {
                return "点击下方“添加图片”或“添加文字”\n即可开始编辑内容";
            }

            @Override
            public int getImage() {
                return R.mipmap.bj_recordrelay;
            }
        });
    }

    private void setData() {
        listdata = new ArrayList<>();
        util = new PublishUtils();
        util.setData(listdata);
        tv_name.setText("新建推广链接");
        File file = new File(PATH_IMG);
        //创建保存截图的路径
        if(!file.exists()) {
            file.mkdirs();
        }
        mAdapter = new PublishAdapter(PublishActivity.this ,listdata);
        lv.setAdapter(mAdapter);

    }

    private void setListener() {

        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(PublishItem.Type.IMG);
            }
        });

        iv_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(PublishItem.Type.TEXT);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EshareDialogFragment fragment = new EshareDialogFragment();
                fragment.setViewUI("还没有提交模板呢，确认退出？", "确定", "取消", new EshareDialogFragment.EshareDialogClickListener() {
                    @Override
                    public void onClick() {
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        fragment.dismiss();
                    }
                });
                fragment.show(getFragmentManager(), "say");
            }
        });

        iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRightInfo = false;
                for(PublishItem item : listdata) {
                    if(item.getValue() == null) {
                        isRightInfo = false;
                        break;
                    } else {
                        isRightInfo = true;
                    }
                }
                if(isRightInfo) {
                    try {
                        util.makeHtmlFile(new File(PATH_WEB));
                        EshareLoger.logI("生成的网页信息:\n");
                        EshareLoger.logI(util.toHtml());
                        /*Intent intent = new Intent(PublishActivity.this, PreviewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(PreviewActivity.EXTRA_DATA, util.toJson());
                        bundle.putString(PreviewActivity.EXTRA_PATH, PATH_WEB);
                        intent.putExtras(bundle);
                        EshareLoger.logI("转化的json为：\n" + util.toJson());
                        startActivity(intent);*/
                    } catch (IOException e) {
                        EToastUtil.show(PublishActivity.this, "预览失败，请重试");
                    } /*catch (JSONException e) {
                        EshareLoger.logI("跳转预览时，传递json数据报错");
                        EToastUtil.show(PublishActivity.this, "预览失败，请重试");
                    }*/
                } else {
                    EshareLoger.logI("编辑模板时信息没有填写完整");
                    EToastUtil.show(PublishActivity.this, "请填写完整信息!");
                }
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final EshareDialogFragment frag = new EshareDialogFragment();
                frag.setViewUI("确定要删除此项吗?", "确定", "取消", new EshareDialogFragment.EshareDialogClickListener() {
                    @Override
                    public void onClick() {
                        frag.dismiss();
                        listdata.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancel() {
                        frag.dismiss();
                    }
                });
                frag.show(getFragmentManager(), "publish");
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_TAKE) {
            EshareLoger.logI("路径 :" + mAdapter.getCurrent_path());
            if(!TextUtils.isEmpty(mAdapter.getCurrent_path())){
                startActivityForResult(EIntentUtil.cropImageUri(URI_TMP,
                        Uri.fromFile(new File(mAdapter.getCurrent_path()))), REQUEST_CODE_CROP);
            }else{
                Toast.makeText(PublishActivity.this, "获取图片路劲出错", Toast.LENGTH_SHORT).show();
            }

        }
        //选择的图库
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_IMG) {
            Uri uri = data.getData();
            EshareLoger.logI("选择图片返回" + EIntentUtil.getPath(PublishActivity.this, uri));
            startActivityForResult(EIntentUtil.cropImageUri(uri,
                    Uri.fromFile(new File(mAdapter.getCurrent_path()))), REQUEST_CODE_CROP);
        } else if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_TEXT) {
            //选择修改文本信息
            EshareLoger.logI("选择添加文字返回");
           /* PublishItem item = (PublishItem) data.getSerializableExtra(AddTextActivity.EXTRA_TEXT);
            if(item.getType() == PublishItem.Type.TEXT ) {
                listdata.remove(mAdapter.getCurrent_index());
                listdata.add(mAdapter.getCurrent_index(), item);
                mAdapter.notifyDataSetChanged();
            }*/
        } else if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_CROP) {
            //处理选择裁剪指定的图片后的事件
            EshareLoger.logI("裁剪图片返回:" + mAdapter.getCurrent_path());
            PublishItem item = listdata.get(mAdapter.getCurrent_index());
            item.setValue(mAdapter.getCurrent_path());
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 添加一个对象到列表
     */
    public void addItem(PublishItem.Type type) {
        String id = "id" + System.currentTimeMillis();
        listdata.add(new PublishItem(id, null, type));
        mAdapter.notifyDataSetChanged();
        lv.smoothScrollToPosition(listdata.size());
    }

    @Override
    public void onBackPressed() {
        final EshareDialogFragment fragment = new EshareDialogFragment();
        fragment.setViewUI("还没有提交模板呢，确认退出？", "确定", "取消", new EshareDialogFragment.EshareDialogClickListener() {
            @Override
            public void onClick() {
                finish();
            }

            @Override
            public void onCancel() {
                fragment.dismiss();
            }
        });
        fragment.show(getFragmentManager(), "say");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkDownload.stopRequest(this);
    }
}
