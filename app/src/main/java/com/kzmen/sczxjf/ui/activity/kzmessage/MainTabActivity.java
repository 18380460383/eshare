package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.UserBean;
import com.kzmen.sczxjf.bean.kzbean.UserMessageBean;
import com.kzmen.sczxjf.control.ScreenControl;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.activity.personal.LoginActivity;
import com.kzmen.sczxjf.ui.activity.personal.MsgCenterActivity;
import com.kzmen.sczxjf.ui.fragment.kzmessage.KzMessageFragment;
import com.kzmen.sczxjf.ui.fragment.personal.CMenuFragment;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 主页面tab页面
 */
public class MainTabActivity extends SuperActivity implements DrawerLayout.DrawerListener {
    private static final int REDPOINT = 3;
    private static final int LOGIN = 4;
    @InjectView(R.id.main_headimage)
    ImageView headImage;
    @InjectView(R.id.framelayout)
    FrameLayout framelayout;
    @InjectView(R.id.id_drawerlayout)
    DrawerLayout idDrawerlayout;
    @InjectView(R.id.id_drawer)
    LinearLayout menu;
    @InjectView(R.id.iv_history)
    ImageView ivHistory;
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.ll_msg)
    LinearLayout ll_msg;
    @InjectView(R.id.ll_search)
    LinearLayout ll_search;
    private ServiceConnection mPlayServiceConnection;
    // protected Handler mHandler = new Handler(Looper.getMainLooper());
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
        checkService();
        //setAccBroadcastReceiver();
        initUserMessage();
        AppContext.maintabeactivity = this;
        supportFragmentManager = getSupportFragmentManager();
        initDate();
        //TODO 启动版本更新控制器
        //UpgradeControl.getUpgradeControl(this).update();
        idDrawerlayout.setDrawerListener(this);
        //TODO 设置一个空监听省去两层 事件拦截防止点击菜单栏照成Fragment控件相应
        menu.setOnClickListener(null);
        int i = new ScreenControl().getscreenWide();
        DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) menu.getLayoutParams();
        layoutParams.width = (int) (i * 0.7);
        menu.setLayoutParams(layoutParams);

        back.setVisibility(View.INVISIBLE);
        getCachTst();
        setOnloading(R.id.ll_content);
        mLayout.onLoading();
        //mLayout.onDone();
        Glide.with(this).load(AppContext.getInstance().getUserLogin().getAvatar()).into(headImage);
    }

    private void initUserMessage() {
        OkhttpUtilManager.postNoCacah(this, "User/get_user_info", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst","获取用户信息："+data);
                try {
                    JSONObject object=new JSONObject(data);
                    Gson gson=new Gson();
                    UserMessageBean bean=gson.fromJson(object.getString("data"),UserMessageBean.class);
                    Log.e("tst",bean.toString());
                    AppContext.userMessageBean=bean;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst","获取用户信息："+msg);
            }
        });
    }

    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // 数据下载完成，转换状态，显示内容视图
            switch (msg.what){
                case 0:
                    mLayout.onError();
                    break;
                case 1:
                    mLayout.onDone();
                    break;
                default:
                    mLayout.onEmpty();
                    break;
            }
        }
    };
    public boolean setScroll(int id) {
        return setOnScroll(id);
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_main_tab2);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.main_headimage, R.id.iv_history,R.id.ll_msg,R.id.ll_search})
    public void onclick(View view) {
        Intent intent=null;
        switch (view.getId()) {
            case R.id.main_headimage:
                //TODO 左侧打开菜单
                idDrawerlayout.openDrawer(GravityCompat.START);
               /* if (AppContext.getInstance().getPersonageOnLine()) {
                    idDrawerlayout.openDrawer(GravityCompat.START);
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("state", 8);
                    startActivityForResult(intent, LOGIN);
                }*/
                break;
            case R.id.iv_history:
                intent = new Intent(this, com.kzmen.sczxjf.ui.activity.kzmessage.LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_msg:
                intent= new Intent(this, CourseSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_search:
                intent= new Intent(this, CourseSearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * peng
     * 加载页面数据
     */
    private void initDate() {
        onLoginSuccess(AppContext.getInstance().getUserLogin());
    }


    /**
     * 登录成功
     */
    private void onLoginSuccess(UserBean login) {
        //TODO 登陆信息正常后初始化界面
        //TODO 加载菜单
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        kzMessageFragment = new KzMessageFragment();
        fragmentTransaction.replace(R.id.framelayout, kzMessageFragment);
        fragmentTransaction.commit();
        /*if (AppContext.getInstance().getPersonageOnLine()) {
            setHeadImageAndMenu(login);
        }*/
        setHeadImageAndMenu(login);
    }

    public void setHeadImageAndMenu(UserBean login) {
        Glide.with(this).load(login.getAvatar()).placeholder(R.drawable.icon_image_normal).into(headImage);
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentcmenu.setMenuBack(new CMenuFragment.MenuBack() {
            @Override
            public void startActivity() {
                idDrawerlayout.closeDrawer(GravityCompat.START);
            }
        });
        fragmentTransaction.replace(R.id.id_drawer, fragmentcmenu);
        //fragmentcmenu.setHeadImage(bm);
        fragmentTransaction.commitAllowingStateLoss();
     //   putJPusID();

    }


    /**
     * 上传极光ID
     */
  /*  private void putJPusID() {
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
    }*/

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
                setHeadImageAndMenu(AppContext.getInstance().getUserLogin());
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
        if (AppContext.getPlayService() != null) {
            AppContext.getPlayService().stop();
        }
    }

    @Override
    public boolean isCanExit() {
        return false;
    }

    public void extP() {
        headImage.setImageResource(R.drawable.userhead);
    }

    public void closeDraw() {
        idDrawerlayout.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    public void getCachTst() {

    }
}
