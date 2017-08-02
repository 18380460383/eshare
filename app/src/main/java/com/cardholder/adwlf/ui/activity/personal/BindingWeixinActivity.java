package com.cardholder.adwlf.ui.activity.personal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cardholder.adwlf.R;
import com.cardholder.adwlf.net.NetworkDownload;
import com.cardholder.adwlf.ui.activity.basic.SuperActivity;
import com.cardholder.adwlf.util.EshareLoger;
import com.cardholder.adwlf.utils.FileUtils;

import org.apache.http.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明：显示二维码关注微信界面
 * note：
 * Created by FuPei
 * on 2015/11/22 at 19:54
 */
public class BindingWeixinActivity extends SuperActivity {

    private TextView tv_download;
    private PercentRelativeLayout pl_back;
    private TextView hint;
    private TextView see;
    private ViewPager pager;
    private List<View> list;
    private ImageView binding_weixin_iv;
    private Bitmap bitmap_erweima = null;
    private LinearLayout bing_dian;
    private RelativeLayout bing_rl;

    @Override
    public void onCreateDataForView() {
        initView();
        initData();
        setListener();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_binding_weixin);
    }

    void initView() {
        binding_weixin_iv= (ImageView) findViewById(R.id.binding_weixin_iv);
        tv_download = (TextView) findViewById(R.id.binding_tv_download);
        tv_download.setVisibility(View.INVISIBLE);
        View view = findViewById(R.id.binding_weixin_title);
        pl_back = (PercentRelativeLayout) view.findViewById(R.id.title_back);
        hint= (TextView) findViewById(R.id.binding_weixihint);
        bing_dian= (LinearLayout) findViewById(R.id.bing_dian);
        bing_rl= (RelativeLayout) findViewById(R.id.bing_rl);
        hint.setText("为了加强您提现的保障，以及开通微信提现功能"+"\n"+"保存并用微信扫描二维码关注微信号，获取提现暗号");
        see= (TextView) findViewById(R.id.see);
        pager= (ViewPager) findViewById(R.id.hint_pager);
        ImageView iv=new ImageView(this);
        iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        iv.setImageResource(R.mipmap.bind_weixin1);
        list=new ArrayList<View>();
        list.add(iv);
        iv=new ImageView(this);
        iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        iv.setImageResource(R.mipmap.bind_weixin2);
        list.add(iv);
        iv=new ImageView(this);
        iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        iv.setImageResource(R.mipmap.bind_weixin3);
        list.add(iv);

        ImageView iv1=new ImageView(this);
         iv1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        iv1.setImageResource(R.mipmap.bind_weixin4);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bing_rl.setVisibility(View.GONE);
            }
        });
        list.add(iv1);
        pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(list.get(position));//添加页卡

                return list.get(position);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);//删除页卡
            }
        });
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setdian(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bing_rl.setVisibility(View.VISIBLE);
               pager.setCurrentItem(0);
               setdian(0);

            }
        });

    }

    void initData() {
        NetworkDownload.byteGet(this, getIntent().getStringExtra("imageurl"), null, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                bitmap_erweima = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                binding_weixin_iv.setImageBitmap(bitmap_erweima);
                tv_download.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    void setListener() {
        pl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bing_rl.getVisibility()==View.VISIBLE){
                    bing_rl.setVisibility(View.GONE);
                }else
                finish();
            }
        });
        tv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePicture();
            }
        });
    }

    /**
     * 保存二维码图片
     */
    public void savePicture() {

        File file = FileUtils.getFile();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bitmap_erweima != null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file.getPath());
                bitmap_erweima.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                sendBroadcast(intent);
                Toast.makeText(BindingWeixinActivity.this, "保存图片完成,路径为:" + file.getPath(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                EshareLoger.logI("保存图片出错");
            }
        } else {
            Toast.makeText(BindingWeixinActivity.this, "还没有图片呢" , Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        if(bing_rl.getVisibility()==View.VISIBLE){
            bing_rl.setVisibility(View.GONE);
        }else
            finish();
    }

    private void setdian(int i){
        for(int j=0;j<4;j++){
            if(j==i){
                bing_dian.getChildAt(j).setEnabled(false);
            }else
                bing_dian.getChildAt(j).setEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkDownload.stopRequest(this);
    }
}
