package com.cardholder.adwlf;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.cardholder.adwlf.bean.Advertisement;
import com.cardholder.adwlf.bean.Config;
import com.cardholder.adwlf.bean.MoneyBean;
import com.cardholder.adwlf.bean.UserInfo;
import com.cardholder.adwlf.bean.WeixinInfo;
import com.cardholder.adwlf.bean.user.User_For_pe;
import com.cardholder.adwlf.ebean.User;
import com.cardholder.adwlf.multidex.MultiDexApplication;
import com.cardholder.adwlf.ui.activity.BaseWebActivity;
import com.cardholder.adwlf.ui.activity.personal.DetialActivity;
import com.cardholder.adwlf.ui.activity.personal.MainTabActivity;
import com.cardholder.adwlf.ui.activity.personal.YaoActivity;
import com.cardholder.adwlf.util.PreferenceUtil;
import com.cardholder.adwlf.utils.FileUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;

import java.io.File;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.JPushInterface;



/**
 * @author wu
 * @version 1.0
 */
public class AppContext extends MultiDexApplication {

    private final String SHARED_USER = "eshare_context";
    private static AppContext instance;
    private  Activity oldinstance;
    private  Activity oneActivity;
    public static MainTabActivity maintabeactivity;
    private static Context _context;
    private static Resources _resource;
    public String weixinCode;
    public String accessToken;
    public String openid;
    public  User user;
    private UserInfo userInfo;
    public MoneyBean moneyBean;
    public DetialActivity detialActivity;
    public YaoActivity yaoActivity;
    public SharedPreferences sp;
    // 七牛sdk
    private Configuration config7niu;
    private UploadManager uploadManager;
    private Config appconfig=null;
    public BaseWebActivity mBaseWebAct;
    private User_For_pe peuser;


    /**
     * 获得当前app运行的AppContext
     */
    public static AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        try {
            JPushInterface.setDebugMode(true);
            JPushInterface.init(this);
        }catch (Exception e){
        }
        new Runnable() {
            @Override
            public void run() {
                // put your logic here!
                // use the mContext instead of this here
                try {
                    user = new User();
                    sp = getSharedPreferences(SHARED_USER, Context.MODE_PRIVATE);
                    setChannel();
                    ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(instance)
                            .diskCache(new UnlimitedDiskCache(new File(FileUtils.getRootFile() + "/" + Constants.LOADER_PATH_IMG)))
                            .build();
                    ImageLoader.getInstance().init(configuration);
                    init();
                    //AdManager.getInstance(this).init("ab26e1420fdcd645", "05fe4480696a8707", false);
                    FeedbackAPI.initAnnoy(instance, "23399998");
                }catch (Exception e){

                }
            }
        }.run();


    }

    public SharedPreferences getSp() {
        if(sp==null){
            sp = getSharedPreferences(SHARED_USER,Context.MODE_PRIVATE);
        }
        return sp;
    }

    // 初始化网络请求
    private void init() {
        AppException instance = AppException.getInstance();
        instance.init(AppContext.instance);
    }
    // 获取7牛sdk
    public Configuration getConfig7niu() {
        if(config7niu == null) {
            config7niu = new Configuration.Builder()
                    .chunkSize(256 * 1024)
                    .putThreshhold(512 * 1024)
                    .connectTimeout(10)
                    .responseTimeout(60)
                    .build();
        }
        return config7niu;
    }
    public UploadManager getUploadManager() {
        if(uploadManager == null) {
            uploadManager = new UploadManager(getConfig7niu());
        }
        return uploadManager;
    }


    public boolean isFirst() {
        return PreferenceUtil.getPrefBoolean(this, "isFirst", true);
    }
    public void setFirst() {
        PreferenceUtil.setPrefBoolean(this, "isFirst", false);
    }
    public void setFirst(boolean first) {
        PreferenceUtil.setPrefBoolean(this, "isFirst", first);
    }
    public void saveInfo(WeixinInfo info) {
        PreferenceUtil.put(this, "info", info);
    }
    public WeixinInfo getInfo() {
        return PreferenceUtil.get(this, "info", WeixinInfo.class);
    }
    public String getTime() {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//        return format.format(new Date());
          return new Date().getTime() + "";
    }

    public String getTimeMillis(String nowTime,String endTime) {
        long time;
        try{
            time = Long.parseLong(endTime) * 1000;
            long now =  Long.parseLong(nowTime) * 1000;
            long left = time - now;
            System.out.println("time"+left);
            if(left<0) {
                return "已结束";
            }else if(left > 1000 * 60 * 60) {
                return "> 1小时";
            } else {
                int minite = (int)(left / 1000/60);
                if(minite<1)
                    minite = 1;
                return "< " + minite + " 分钟";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "> 1小时";
        }
    }

    public Context context() {
        if(_context == null) {
            _context = getApplicationContext();
        }
        return _context;
    }

    public Resources resources() {
        if(_resource == null) {
            _resource = context().getResources();
        }
        return _resource;
    }

    /**
     * 获取用户的信息
     * @return
     */
    public UserInfo getUserInfo() {
        if(null == this.userInfo || null == this.userInfo.weixin){
            this.userInfo = new UserInfo();
            this.userInfo.level=sp.getInt("level",0);
            this.userInfo.uid=sp.getInt("uid", 0);
            this.userInfo.weixin=sp.getString("weixin", null);
            this.userInfo.username=sp.getString("username", null);
            this.userInfo.token=sp.getString("token",null);
            this.userInfo.imageurl=sp.getString("imageurl", null);
            this.userInfo.on_phone = sp.getString("on_phone", null);
            this.userInfo.state = sp.getString("state", null);
            this.userInfo.score = sp.getString("score", null);
            this.userInfo.balance = sp.getInt("userbalance", 0);
            this.userInfo.hotnum = sp.getInt("hotnum", 0);
            this.userInfo.city=sp.getString("city_c", "");
            this.userInfo.province=sp.getString("province_c","");
            this.userInfo.emali=sp.getString("emali_c","");
            if(sp.getString("extras", null) != null) {
                List<UserInfo.ExtraEntity> list = new Gson().fromJson(
                        sp.getString("extras", null), new TypeToken<List<UserInfo.ExtraEntity>>() {
                }.getType());
//                try {
//                    JSONArray array = new JSONArray(sp.getString("extras", null));
//                    for(int i = 0; i < array.length(); i++ ) {
//                        list.add(new Gson().fromJson(array.get(i).toString(), UserInfo.ExtraEntity.class));
//                    }
//                    this.userInfo.setExtras(list);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                this.userInfo.setExtras(list);
            }
        }
        return this.userInfo;
    }

    public void setNoLine(){
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("online", false);
        edit.commit();
    }
    public  void setDownId(Long id){
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong("dowid", id);
        edit.commit();
    }
    public  Long getDownId(){
       return sp.getLong("dowid",0);
    }
    public void setMsgNum(int num){
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt("msgnum", num);
        edit.commit();
    }
    public  int getMsgNum(){
        return sp.getInt("msgnum", 0);
    }

    public static void setMaintabeactivity(MainTabActivity maintabeactivity) {
        AppContext.maintabeactivity = maintabeactivity;
    }

    private void setChannel(){
        ApplicationInfo appInfo = null;
        try {
            appInfo = this.getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
            String msg=appInfo.metaData.getInt("UMENG_CHANNEL")+"";
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("channel",msg);
            edit.commit();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    public  String getChannel(){
        String string = sp.getString("channel", "appstore");
        if("0".equals(string)){
            string="appstore";
        }
        return string;
    }
    public void setEnterpriseTDC(String path){
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("etdc", path);
        edit.commit();
    }
    public String getEnterpriseTDC(){
        return sp.getString("etdc",null);
    }
    public void setUserTDC(String path){
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("utdc", path);
        edit.commit();
    }
    public String getUserTDC(){
        return sp.getString("etdc",null);
    }

    public Config getAppConfig() {
        return appconfig;
    }

    public void setAppConfig(Config config) {
        this.appconfig = config;
    }
    public void setAdvertisement(Advertisement advertisement){
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("adtype", advertisement.getType());
        edit.putString("adtimes", advertisement.getTimes());
        edit.putString("adlinkurl", advertisement.getLinkurl());
        edit.putString("adimgurl", advertisement.getImgurl());
        edit.putString("id", advertisement.getId());
        edit.putString("startdate", advertisement.getStartdate());
        edit.putString("enddate", advertisement.getEnddate());
        edit.putString("title", advertisement.getTitle());
        edit.commit();
    }
    public Advertisement getAdvertisement(){
        Advertisement ad=new Advertisement();
        ad.setType(sp.getString("adtype", "3"));
        ad.setTimes(sp.getString("adtimes", "5"));
        ad.setLinkurl(sp.getString("adlinkurl", null));
        ad.setImgurl(sp.getString("adimgurl", null));
        ad.setId(sp.getString("id", null));
        ad.setStartdate(sp.getString("startdate", null));
        ad.setEnddate(sp.getString("enddate", null));
        ad.setTitle(sp.getString("title", null));
        return ad;
    }

    public void setOldinstance(Activity oldinstance) {
        this.oldinstance = oldinstance;
    }

    public void setOneActivity(Activity oneActivity) {
        if(this.oldinstance!=oneActivity){
            this.oldinstance=this.oneActivity;
        }
        this.oneActivity = oneActivity;
    }

    public  Activity getOldinstance() {
        if(oldinstance==null||oldinstance == oneActivity){
            return AppManager.getAppManager().getOldActivity();
        }else{
        return oldinstance;
        }
    }

    public  Activity getOneActivity() {
        return oneActivity;
    }
    public void setPersonageOnLine(Boolean online){
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("personageonline", online);
        edit.commit();
    }
    public Boolean getPersonageOnLine(){
        boolean personageonline = sp.getBoolean("personageonline", false);
        return personageonline;
    }
    public void setCpassword(String password){
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("cpassword", password);
        edit.commit();
    }
    public String getCpassword(){
       return sp.getString("cpassword",null);
    }
    public void setOPTNoShow(){
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("optnoshow", true);
        edit.commit();
    }
    public boolean getOptNoShow(){
        return sp.getBoolean("optnoshow", false);
    }
    public void setEnMoney(String money){
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("enmoney", money);
        edit.commit();
    }
    public String getEnMoney(){
        return sp.getString("enmoney", "0.00");
    }
    public void setPEUser(User_For_pe peuser){
        this.peuser=peuser;
        SharedPreferences.Editor edit = sp.edit();
        Gson g=new Gson();
         String s = g.toJson(peuser);
        edit.putString("peuser",s);
        edit.commit();
    }
    public User_For_pe getPEUser(){
        if(this.peuser==null){
             String peuserStr = sp.getString("peuser", null);
            if(peuserStr==null){
                this.peuser=new User_For_pe();
                return this.peuser;
            }else{
                Gson gson=new Gson();
                this.peuser= gson.fromJson(peuserStr, User_For_pe.class);
                return this.peuser;
            }
        }else{
            return this.peuser;
        }
    }

}
