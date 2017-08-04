package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.EnConstants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.user.User_For_pe;
import com.kzmen.sczxjf.control.ScreenControl;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.activity.personal.LoginActivity;
import com.kzmen.sczxjf.ui.activity.personal.MsgCenterActivity;
import com.kzmen.sczxjf.ui.fragment.kzmessage.KzMessageFragment;
import com.kzmen.sczxjf.ui.fragment.personal.CMenuFragment;
import com.kzmen.sczxjf.util.EshareLoger;
import com.kzmen.sczxjf.utils.AppUtils;
import com.kzmen.sczxjf.utils.BitmapUtils;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 主页面tab页面
 */
public class MainTabActivity extends SuperActivity implements DrawerLayout.DrawerListener {
    private static final int REDPOINT = 3;
    private static final int LOGIN = 4;
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.main_headimage)
    ImageView headImage;
    @InjectView(R.id.framelayout)
    FrameLayout framelayout;
    @InjectView(R.id.id_drawerlayout)
    DrawerLayout idDrawerlayout;
    @InjectView(R.id.id_drawer)
    LinearLayout menu;

    /**
     * 当前dialog是否显示在界面上
     */
    private boolean isDialogShowing = false;
    /**
     * 弹出对话框
     */
    private final int CODE_DIALOG = 1;
    /**
     * 判断返回键两次点击时常
     */
    private long exitTime = 0;

    private CMenuFragment fragmentcmenu = new CMenuFragment();
    private static FragmentManager supportFragmentManager;
    private BroadcastReceiver receiver;
    private KzMessageFragment kzMessageFragment;

    @Override
    public void onCreateDataForView() {
        setAccBroadcastReceiver();
        AppContext.maintabeactivity = this;
        supportFragmentManager = getSupportFragmentManager();
        initDate();
        //TODO 启动版本更新控制器
        //UpgradeControl.getUpgradeControl(this).update();
        idDrawerlayout.setDrawerListener(this);
        back.setVisibility(View.GONE);
        //TODO 设置一个空监听省去两层 事件拦截防止点击菜单栏照成Fragment控件相应
        menu.setOnClickListener(null);
        int i = new ScreenControl().getscreenWide();
        DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) menu.getLayoutParams();
        layoutParams.width = (int) (i * 0.7);
        menu.setLayoutParams(layoutParams);
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_main_tab2);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.main_headimage})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.main_headimage:
                //TODO 左侧打开菜单
                if (AppContext.getInstance().getPersonageOnLine()) {
                    idDrawerlayout.openDrawer(GravityCompat.START);
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("state", 8);
                    startActivityForResult(intent, LOGIN);
                }
                break;
        }
    }

    /**
     * peng
     * 加载页面数据
     */
    private void initDate() {
        onLoginSuccess(AppContext.getInstance().getPEUser());

    }


    /**
     * 登录成功
     */
    private void onLoginSuccess(User_For_pe login) {
        //TODO 登陆信息正常后初始化界面
        //TODO 加载菜单
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        kzMessageFragment = new KzMessageFragment();
        fragmentTransaction.replace(R.id.framelayout, kzMessageFragment);
        fragmentTransaction.commit();
        if (AppContext.getInstance().getPersonageOnLine()) {
            setHeadImageAndMenu(login);
        }
    }

    public void setHeadImageAndMenu(User_For_pe login) {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentcmenu.setMenuBack(new CMenuFragment.MenuBack() {
            @Override
            public void startActivity() {
                idDrawerlayout.closeDrawer(GravityCompat.START);
            }
        });
        fragmentTransaction.replace(R.id.id_drawer, fragmentcmenu);

        fragmentTransaction.commitAllowingStateLoss();

        final double v = new ScreenControl().getscreenHigh() / 16 * 1.5 - 60;
        ImageLoader.getInstance().loadImage(login.getImageurl(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                headImage.setImageBitmap(BitmapUtils.toRoundBitmap(AppUtils.readBitMap(MainTabActivity.this, R.mipmap.image_def)));
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                headImage.setImageBitmap(BitmapUtils.toRoundBitmap(AppUtils.readBitMap(MainTabActivity.this, R.mipmap.image_def)));
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if (bitmap == null) {
                } else {
                    Bitmap bm = BitmapUtils.toRoundBitmap(bitmap);
                    headImage.setImageBitmap(bm);
                    fragmentcmenu.setHeadImage(bm);
                }
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                headImage.setImageBitmap(BitmapUtils.toRoundBitmap(AppUtils.readBitMap(MainTabActivity.this, R.mipmap.image_def)));
            }
        });

        putJPusID();

    }


    /**
     * 上传极光ID
     */
    private void putJPusID() {
        String registrationID = JPushInterface.getRegistrationID(getApplicationContext());
        Map<String, String> map = new HashMap<>();
        new RequestParams();
        map.put("uid", AppContext.getInstance().getPEUser().getUid());
        map.put("form", "android");

        map.put("jpushid", registrationID);
        RequestParams requestParams = AppUtils.getParm(map);
        NetworkDownload.jsonPost(this, Constants.URL_PUT_JPUSID, requestParams, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void onBackPressed() {
        //TODO 如果显示了冻结窗口，拦截back按键，返回微信登陆
        if (isDialogShowing) {
            returnLogin();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == CODE_DIALOG) {
            isDialogShowing = false;
            returnLogin();
        } else if (resultCode == RESULT_OK && requestCode == REDPOINT) {
            int anInt = data.getExtras().getInt(MsgCenterActivity.MSGNUM);
        } else if (resultCode == RESULT_OK && requestCode == LOGIN) {
            if (data.getIntExtra("loginstate", 0) == 1) {
                setHeadImageAndMenu(AppContext.getInstance().getPEUser());
            }
        }
    }

    /**
     * 失败后返回微信引导页
     */
    public void returnLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("justLogin", true);
        this.startActivity(intent);
        this.finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppContext.getInstance().setNoLine();
        NetworkDownload.stopRequest(this);
        unregisterReceiver(receiver);
    }


    /**
     * peng
     * 重写方法实现双击返回键退出应用设置前提先关闭菜单栏
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (idDrawerlayout.isDrawerOpen(GravityCompat.START)) {
                idDrawerlayout.closeDrawer(GravityCompat.START);
            } else {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    AppContext.getInstance().setNoLine();
                    //OffersManager.getInstance(this).onAppExit();
                    finish();
                    System.exit(0);
                }
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        if (AppContext.getInstance().getPersonageOnLine() && fragmentcmenu != null) {
            fragmentcmenu.setDatauser();
        }
    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    public boolean isCanExit() {
        return false;
    }

    private void setAccBroadcastReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                ImageLoader.getInstance().loadImage(AppContext.getInstance().getPEUser().getImageurl(), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {
                        headImage.setImageBitmap(BitmapUtils.toRoundBitmap(AppUtils.readBitMap(MainTabActivity.this, R.mipmap.image_def)));
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                        headImage.setImageBitmap(BitmapUtils.toRoundBitmap(AppUtils.readBitMap(MainTabActivity.this, R.mipmap.image_def)));
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        if (bitmap == null) {
                        } else {
                            Bitmap bm = BitmapUtils.toRoundBitmap(bitmap);
                            headImage.setImageBitmap(bm);
                            fragmentcmenu.setHeadImage(bm);
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {
                        headImage.setImageBitmap(BitmapUtils.toRoundBitmap(AppUtils.readBitMap(MainTabActivity.this, R.mipmap.image_def)));
                    }
                });
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.WEIXIN_ACCREDIT);
        registerReceiver(receiver, filter);
        BroadcastReceiver loginReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                EshareLoger.logI("ON LOGIN SUCCESS");
            }
        };
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction(EnConstants.BROCAST_LOGIN_SUCCESS);
        registerReceiver(loginReceiver, filter2);
    }

    public void extP() {
        headImage.setImageResource(R.mipmap.userhead);
    }

    public void closeDraw() {
        idDrawerlayout.closeDrawer(GravityCompat.START);
    }
}
