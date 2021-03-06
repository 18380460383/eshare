package com.kzmen.sczxjf.ui.fragment.personal;

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

import com.bumptech.glide.Glide;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.UIManager;
import com.kzmen.sczxjf.bean.kzbean.UserBean;
import com.kzmen.sczxjf.ui.activity.kzmessage.MainTabActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.PersonMessActivity;
import com.kzmen.sczxjf.ui.activity.menu.FriendOfmineAcitivty;
import com.kzmen.sczxjf.ui.activity.menu.MyAskActivity;
import com.kzmen.sczxjf.ui.activity.menu.MyCollectionAcitivity;
import com.kzmen.sczxjf.ui.activity.menu.MyIntegralActivity;
import com.kzmen.sczxjf.ui.activity.menu.MyPackageAcitivity;
import com.kzmen.sczxjf.ui.activity.menu.ShopOfIntegralActivity;
import com.kzmen.sczxjf.ui.activity.menu.SpecialPowerActivity;
import com.kzmen.sczxjf.ui.fragment.basic.SuperFragment;
import com.kzmen.sczxjf.util.TextViewUtil;
import com.kzmen.sczxjf.util.glide.GlideCircleTransform;
import com.kzmen.sczxjf.utils.AppUtils;
import com.kzmen.sczxjf.utils.BitmapUtils;

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
    @InjectView(R.id.ll_package)
    LinearLayout ll_package;
    @InjectView(R.id.ll_jifen)
    LinearLayout ll_jifen;
    @InjectView(R.id.c_menu_attestation_mark_for_e)
    TextView cMenuAttestationMarkForE;
    @InjectView(R.id.c_menu_attestation_mark_for_m)
    TextView cMenuAttestationMarkForM;
    @InjectView(R.id.iv_close)
    ImageView iv_close;
    @InjectView(R.id.tv_jifen)
    TextView tvJifen;
    @InjectView(R.id.tv_package)
    TextView tvPackage;

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
        view = setContentView(inflater, container, R.layout.fragment_menu);
        ButterKnife.inject(this, view);
        setRecriver();
        AppContext instance = AppContext.getInstance();
        if (!TextUtils.isEmpty(instance.getUserLogin().getUid())) {
            setUserInfo();
            //getBanner();
            setDatauser();
        }

        return view;
    }

    private void setRecriver() {
        bannerReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //getBanner();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.FRAGMENT_MONEY);
        getContext().registerReceiver(bannerReceiver, filter);
    }

    @OnClick({R.id.c_menu_user_head_iv, R.id.c_menu_collect_onc,
            /*R.id.c_menu_order_onc,*/ R.id.c_menu_friend_onc, R.id.c_menu_activity_onc, R.id.c_menu_balance,
            R.id.c_menu_credits_exchange_onc, R.id.c_menu_setting_onc, R.id.c_menu_feedback_onc, R.id.c_menu_creative_collection_rl
            , R.id.iv_close, R.id.ll_package, R.id.ll_jifen})
    public void Listener(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.c_menu_user_head_iv:
                //TODO 点击头像
               /* if (!AppContext.getInstance().getPersonageOnLine()) {
                    //TODO 登陆
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 2);
                } else {
                    UIManager.showPersonInfoActivity((Activity) getContext());
                    Log.i("info", "跳");
                }*/
                intent = new Intent(getContext(), PersonMessActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_jifen:
                intent = new Intent(getContext(), MyIntegralActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_package:
                intent = new Intent(getContext(), MyPackageAcitivity.class);
                startActivity(intent);
                break;
            case R.id.c_menu_collect_onc:
                //TODO 点击收藏
                Intent intent3 = new Intent(getContext(), MyCollectionAcitivity.class);
                getContext().startActivity(intent3);
                break;
            case R.id.c_menu_friend_onc:
                //TODO 点击好友
                getContext().startActivity(new Intent(getContext(), FriendOfmineAcitivty.class));
                break;
            case R.id.c_menu_activity_onc:
                //TODO 点击活动
                getContext().startActivity(new Intent(getContext(), SpecialPowerActivity.class));
                break;
            case R.id.c_menu_credits_exchange_onc:
                getContext().startActivity(new Intent(getContext(), ShopOfIntegralActivity.class));
                break;
            case R.id.c_menu_setting_onc:
                //TODO 点击设置
                UIManager.showSetActivity((Activity) getContext());
                break;
            case R.id.c_menu_feedback_onc:
                //TODO 点击意见反馈
               // FeedbackAPI.openFeedbackActivity(getActivity());
                break;
            case R.id.c_menu_balance:
                //TODO 点击转发记录
              /*  Intent intent2 = new Intent(getContext(), RecordActivity.class);
                intent2.putExtra(RecordActivity.FLAG, 1);
                getContext().startActivity(intent2);*/
                break;
            case R.id.c_menu_creative_collection_rl:
                Intent intentn = new Intent(getContext(), MyAskActivity.class);
                getContext().startActivity(intentn);
                break;
            case R.id.iv_close:
                ((MainTabActivity) getActivity()).closeDraw();
                break;

        }
        if (menuBack != null) {
            menuBack.startActivity();
        }
    }


   /* private void getBanner() {
        if (!DOINGGETBALANCE) {
            DOINGGETBALANCE = true;
            EnWebUtil.getInstance().post(getActivity(), new String[]{"JiebianInfo", "findOneJiebianBalance"}, new RequestParams(), new EnWebUtil.AesListener2() {
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
*/
    public void setUserInfo() {
        cMenuUserHeadIv.setImageBitmap(BitmapUtils.toRoundBitmap(AppUtils.readBitMap(getContext(), R.drawable.image_def)));
        setDatauser();
    }

    private UserBean peUser;

    public void setDatauser() {
        //getBanner();
        peUser = AppContext.getInstance().getUserLogin();
        Log.e("tstmenu",peUser.toString());
        if (!TextUtils.isEmpty(peUser.getUsername())) {
            cMenuUserNameTv.setText(peUser.getUsername());
        } else if (!TextUtils.isEmpty(peUser.getPhone())) {
            String userphone = peUser.getPhone();
            String s = userphone.substring(0, 3) + "****" + userphone.substring(7, 11);
            cMenuUserNameTv.setText(s);
        }
        SpannableStringBuilder colorText = TextViewUtil.getColorText(peUser.getHotnum() + "天", "#ff8307");
        SpannableStringBuilder str = new SpannableStringBuilder("连续登陆：");
        cMenuUserLandingNumTv.setText(str.append(colorText));
        setDate();
        Glide.with(getActivity()).load(peUser.getAvatar()).transform(new GlideCircleTransform(getActivity())).into(cMenuUserHeadIv);
        tvJifen.setText(peUser.getScore());
        tvPackage.setText(peUser.getBalance() + "");
    }

    private void setDate() {
       // UserBean peUser1 = AppContext.getInstance().getUserLogin();
    }

    public void setHeadImage(final Bitmap bitmap) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (bitmap != null && cMenuUserHeadIv != null) {
                  //  cMenuUserHeadIv.setImageBitmap(BitmapUtils.toRoundBitmap(bitmap));
                }
            }
        }, 3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public interface MenuBack {
        void startActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(bannerReceiver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data.getIntExtra("loginstate", 0) == 1) {
            setUserInfo();
            //getBanner();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void lazyLoad() {

    }
}
