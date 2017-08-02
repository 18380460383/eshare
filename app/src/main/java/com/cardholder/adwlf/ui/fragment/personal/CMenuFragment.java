package com.cardholder.adwlf.ui.fragment.personal;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.cardholder.adwlf.AppContext;
import com.cardholder.adwlf.Constants;
import com.cardholder.adwlf.R;
import com.cardholder.adwlf.UIManager;
import com.cardholder.adwlf.bean.user.User_For_pe;
import com.cardholder.adwlf.net.EnWebUtil;
import com.cardholder.adwlf.ui.activity.personal.ActListActivity;
import com.cardholder.adwlf.ui.activity.personal.C_Collect;
import com.cardholder.adwlf.ui.activity.personal.LoginActivity;
import com.cardholder.adwlf.ui.activity.personal.OriginalityCoollectActivity;
import com.cardholder.adwlf.ui.activity.personal.RecordActivity;
import com.cardholder.adwlf.ui.activity.personal.ShareFrindes;
import com.cardholder.adwlf.ui.activity.personal.ShopActivity;
import com.cardholder.adwlf.ui.fragment.basic.SuperFragment;
import com.cardholder.adwlf.util.TextViewUtil;
import com.cardholder.adwlf.utils.AppUtils;
import com.cardholder.adwlf.utils.BitmapUtils;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 创建者：杨操
 * 时间：2016/4/12
 * 功能描述：个人端菜单栏
 */
public class CMenuFragment extends SuperFragment {

    @InjectView(R.id.c_menu_user_head_iv)
    ImageView cMenuUserHeadIv;
    @InjectView(R.id.c_menu_user_name_tv)
    TextView cMenuUserNameTv;
    @InjectView(R.id.c_menu_attestation_mark)
    TextView cMenuAttestationMark;
    @InjectView(R.id.c_menu_user_landing_num_tv)
    TextView cMenuUserLandingNumTv;
    @InjectView(R.id.c_menu_integral)
    TextView cMenuIntegral;
    @InjectView(R.id.c_menu_balance)
    TextView cMenuBalance;
    @InjectView(R.id.c_menu_caifu)
    LinearLayout cMenuCaifu;

    @InjectView(R.id.c_menu_collect_onc)
    RelativeLayout cMenuCollect;
/*    @InjectView(R.id.c_menu_order_onc)
    RelativeLayout cMenuOrder;*/
    @InjectView(R.id.c_menu_friend_onc)
    RelativeLayout cMenuFriend;
    @InjectView(R.id.c_menu_activity_onc)
    RelativeLayout cMenuActivity;
    @InjectView(R.id.c_menu_credits_exchange_onc)
    RelativeLayout cMenuCreditsExchange;
    @InjectView(R.id.c_menu_creative_collection_rl)
    RelativeLayout cMenuCreativeCollection;
    @InjectView(R.id.c_menu_setting_onc)
    LinearLayout cMenuSetting;
    @InjectView(R.id.c_menu_feedback_onc)
    LinearLayout cMenuFeedback;
    @InjectView(R.id.c_menu_attestation_mark_for_e)
    TextView cMenuAttestationMarkForE;
    @InjectView(R.id.c_menu_attestation_mark_for_m)
    TextView cMenuAttestationMarkForM;
    private boolean DOINGGETBALANCE;
    private MenuBack menuBack;
    private View view;
    private BroadcastReceiver bannerReceiver;

    public void setMenuBack(MenuBack menuBack) {
        this.menuBack = menuBack;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = setContentView(inflater, container, R.layout.fragment_menu_c);
        ButterKnife.inject(this, view);
        setRecriver();
        AppContext instance = AppContext.getInstance();
        if(!TextUtils.isEmpty(instance.getPEUser().getUid())){
            setUserInfo();
            getBanner();
        }

        return view;
    }

    private void setRecriver() {
        bannerReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                getBanner();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.FRAGMENT_MONEY);
        getContext().registerReceiver(bannerReceiver, filter);
    }

    @OnClick({R.id.c_menu_user_head_iv,R.id.c_menu_collect_onc,
            /*R.id.c_menu_order_onc,*/ R.id.c_menu_friend_onc, R.id.c_menu_activity_onc,R.id.c_menu_balance,
            R.id.c_menu_credits_exchange_onc, R.id.c_menu_setting_onc, R.id.c_menu_feedback_onc,R.id.c_menu_creative_collection_rl})
    public void Listener(View view) {
        switch (view.getId()) {
            case R.id.c_menu_user_head_iv:
                //TODO 点击头像
                if(!AppContext.getInstance().getPersonageOnLine()){
                    //TODO 登陆
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent,2);
                }else {
                    UIManager.showPersonInfoActivity((Activity) getContext());
                    Log.i("info","跳");
                }
                break;

            case R.id.c_menu_collect_onc:
                //TODO 点击收藏
                getContext().startActivity(new Intent(getContext(), C_Collect.class));
                break;
            /*case R.id.c_menu_order_onc:
                //TODO 点击转发记录
                Intent intent = new Intent(getContext(), RecordActivity.class);
                intent.putExtra(RecordActivity.FLAG,0);
                getContext().startActivity(intent);
                break;*/
            case R.id.c_menu_friend_onc:
                //TODO 点击好友
                getContext().startActivity(new Intent(getContext(), ShareFrindes.class));
                break;
            case R.id.c_menu_activity_onc:
                //TODO 点击活动
                getContext().startActivity(new Intent(getContext(), ActListActivity.class));
                break;
            case R.id.c_menu_credits_exchange_onc:
                getContext().startActivity(new Intent(getContext(), ShopActivity.class));
                break;
            case R.id.c_menu_setting_onc:
                //TODO 点击设置
                UIManager.showSetActivity((Activity) getContext());
                break;
            case R.id.c_menu_feedback_onc:
                //TODO 点击意见反馈
                /*FeedbackAgent agent = new FeedbackAgent(getContext());
                agent.sync();
                agent.startFeedbackActivity();*/
                FeedbackAPI.openFeedbackActivity(getActivity());
                break;
            case R.id.c_menu_balance:
                //TODO 点击转发记录
                Intent intent2 = new Intent(getContext(), RecordActivity.class);
                intent2.putExtra(RecordActivity.FLAG, 1);
                getContext().startActivity(intent2);
                break;
            case R.id.c_menu_creative_collection_rl:
                Intent intent3 = new Intent(getContext(), OriginalityCoollectActivity.class);
                getContext().startActivity(intent3);
                break;

        }
        if(menuBack!=null){
            menuBack.startActivity();
        }
    }


    private void getBanner() {
        if (!DOINGGETBALANCE) {
            DOINGGETBALANCE = true;
            EnWebUtil.getInstance().post(getActivity(), new String[]{"JiebianInfo","findOneJiebianBalance"}, new RequestParams(), new EnWebUtil.AesListener2() {
                @Override
                public void onSuccess(String errorCode, String errorMsg, String data) {
                    DOINGGETBALANCE = false;
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        User_For_pe peUser = AppContext.getInstance().getPEUser();
                        peUser.setScore(jsonObject.getString("score"));
                        peUser.setBalance(Double.valueOf(jsonObject.getString("balance")));
                        setDate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFail(String result) {
                    DOINGGETBALANCE = false;
                }
            });
        }
    }

    public  void setUserInfo() {

        cMenuUserHeadIv.setImageBitmap(BitmapUtils.toRoundBitmap(AppUtils.readBitMap(getContext(), R.mipmap.image_def)));
        setDatauser();
    }

    public void setDatauser( ) {
        getBanner();
         User_For_pe peUser = AppContext.getInstance().getPEUser();
        System.out.println("用户菜单数据"+peUser);
        if(!TextUtils.isEmpty(peUser.getUsername())){
            cMenuUserNameTv.setText(peUser.getUsername());
        }else if(!TextUtils.isEmpty(peUser.getOn_phone())){
            String userphone = peUser.getOn_phone();
            String s = userphone.substring(0, 3) + "****" + userphone.substring(7, 11);
            cMenuUserNameTv.setText(s);
        }

     /*   //用户认证状态是否存在(要判断角色数是否大于2，因为角色信息可能从shardperfence取的，会过时)
        System.out.println("aaaaa"+userInfo.extras);
        if (AppContext.getInstance().getUserInfo().extras != null && AppContext.getInstance().getUserInfo().extras.size() > 2) {
            //是否认证了媒体用户
            if (AppContext.getInstance().getUserInfo().extras.get(2).state != null) {
                cMenuAttestationMark.setVisibility(View.VISIBLE);
                //tv_certified.setText(AppContext.getInstance().getUserInfo().extras.get(2).name);
            } else {
                cMenuAttestationMark.setVisibility(View.GONE);
            }
            //判断是否认证了企业用户
            if (AppContext.getInstance().getUserInfo().extras.get(0).state != null) {
                cMenuAttestationMarkForE.setVisibility(View.VISIBLE);
                cMenuAttestationMark.setVisibility(View.GONE);
            } else {
                cMenuAttestationMarkForE.setVisibility(View.GONE);
            }

            //是否认证了媒体用户
            if (AppContext.getInstance().getUserInfo().extras.get(1).state != null) {
                cMenuAttestationMarkForM.setVisibility(View.VISIBLE);
                cMenuAttestationMark.setVisibility(View.GONE);
            } else {
                cMenuAttestationMarkForM.setVisibility(View.GONE);
            }


        }*/
        SpannableStringBuilder colorText = TextViewUtil.getColorText(peUser.getHotnum() + "天", "#ff8307");
        SpannableStringBuilder str = new SpannableStringBuilder("连续登陆：");
        cMenuUserLandingNumTv.setText(str.append(colorText));
    }
    private void setDate() {
        User_For_pe peUser1 = AppContext.getInstance().getPEUser();
        cMenuIntegral.setText(peUser1.getScore());
        cMenuBalance.setText(peUser1.getBalance()+"");
         User_For_pe peUser = AppContext.getInstance().getPEUser();
    }
    public void setHeadImage(final Bitmap bitmap){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(bitmap!=null&&cMenuUserHeadIv!=null){
                    cMenuUserHeadIv.setImageBitmap(BitmapUtils.toRoundBitmap(bitmap));
                }
            }
        }, 3000);


    }
    public interface  MenuBack{
        void startActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(bannerReceiver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==2&&resultCode==Activity.RESULT_OK&&data.getIntExtra("loginstate",0)==1){
            setUserInfo();
            getBanner();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
