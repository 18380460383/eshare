package com.jiebian.adwlf.ui.activity.personal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jiebian.adwlf.AppContext;
import com.jiebian.adwlf.Constants;
import com.jiebian.adwlf.R;
import com.jiebian.adwlf.bean.returned.OrderForm;
import com.jiebian.adwlf.bean.returned.ShippingAddress;
import com.jiebian.adwlf.bean.returned.ShopDetailsBean;
import com.jiebian.adwlf.bean.user.User_For_pe;
import com.jiebian.adwlf.connector.BackgroundImageLoadingListener;
import com.jiebian.adwlf.control.ImageLoaderOptionsControl;
import com.jiebian.adwlf.net.NetworkDownload;
import com.jiebian.adwlf.ui.activity.basic.SuperActivity;
import com.jiebian.adwlf.utils.AppUtils;
import com.jiebian.adwlf.utils.JsonUtils;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/1/26.
 */
public class BuyShopSetActivity extends SuperActivity {


    public  static final String ORDER = "order";
    public static final String ADDRESS = "address";
    public static final String RETURNADDRESS = "returnAddress";
    private static final int SHOPADRRESSCODE = 10;
    private static final String SERIALIZABLEOBJECT = "object";
    @InjectView(R.id.imageView)
    ImageView imageView;
    @InjectView(R.id.address_name)
    TextView addressName;
    @InjectView(R.id.phone_number)
    TextView phoneNumber;
    @InjectView(R.id.choose_address_image)
    ImageView addAddressImage;
    @InjectView(R.id.address_text)
    TextView addressText;
    @InjectView(R.id.address_rl)
    RelativeLayout addressRl;
    @InjectView(R.id.add_address)
    ImageView addAddress;
    @InjectView(R.id.no_address_rl)
    RelativeLayout noAddressRl;
    @InjectView(R.id.buy_shop_set_shopimage)
    ImageView buyShopSetShopimage;
    @InjectView(R.id.buy_shop_set_shopname)
    TextView buyShopSetShopname;
    @InjectView(R.id.by_shop_setnum)
    TextView byShopSetnum;
    @InjectView(R.id.textview1)
    TextView textview1;
    @InjectView(R.id.buy_for_integral)
    TextView buyForIntegral;
    @InjectView(R.id.textview2)
    TextView textview2;
    @InjectView(R.id.postage)
    TextView postage;
    @InjectView(R.id.textview4)
    TextView textview4;
    @InjectView(R.id.buy_spinner)
    Spinner buySpinner;
    @InjectView(R.id.textview3)
    TextView textview3;
    @InjectView(R.id.buy_money)
    TextView buyMoney;
    @InjectView(R.id.coupon_money)
    TextView favorableMoney;
    @InjectView(R.id.buy_shop_set_submit)
    Button buyShopSetSubmit;
    @InjectView(R.id.favorable)
    TextView favorable;
    @InjectView(R.id.coupon_rl)
    RelativeLayout couponRL;
    private static  ShopDetailsBean detailsdata;
    private String shopid;
    private ShippingAddress addressS;
    private String aid;
    private BroadcastReceiver play;

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_buyshop_set);
        registerBroadcastReceiver();
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.buy_shop_set_title, "确认页面");
        getData();
    }

    private void registerBroadcastReceiver() {
        play=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.SHOP_PLAY_OK);
        registerReceiver(play, filter);

    }

    @OnClick({R.id.no_address_rl,R.id.address_rl,R.id.buy_shop_set_submit})
   public void onclic(View view){
        switch (view.getId()){
            case R.id.no_address_rl:
               /* Intent intent = new Intent(this, AddShopAdrress.class);
                intent.putExtra(ADDRESS, addressS);
                startActivityForResult(intent, SHOPADRRESSCODE);*/
                break;
            case R.id.address_rl:
                /*Intent intent1 = new Intent(this, AddShopAdrress.class);
                intent1.putExtra(ADDRESS, addressS);
                startActivityForResult(intent1, SHOPADRRESSCODE);*/
                break;
            case R.id.buy_shop_set_submit:
                userAddOrder();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if(requestCode==SHOPADRRESSCODE&&resultCode==RESULT_OK){
                    Bundle extras = data.getExtras();
                    if(null!=extras){
                        ShippingAddress serializable = (ShippingAddress) extras.getSerializable(RETURNADDRESS);
                        addressRl.setVisibility(View.VISIBLE);
                        noAddressRl.setVisibility(View.GONE);
                        setAddressData(serializable);
                    }
                }
    }
    private void userAddOrder() {
        if(TextUtils.isEmpty(aid)){
            Toast.makeText(BuyShopSetActivity.this, "请编写地址后再提交", Toast.LENGTH_SHORT).show();
        }else {
            Map<String, String> map = new HashMap<>();
            map.put("uid", AppContext.getInstance().getPEUser().getUid());
            map.put("gid", detailsdata.getId());
            map.put("num",  "1");
            map.put("aid", aid);
            RequestParams params = AppUtils.getParm(map);
            showProgressDialog(null);
            NetworkDownload.jsonPostForCode1(this, Constants.URL_POST_USERADDORDER, params, new NetworkDownload.NetworkDownloadCallBackJson() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                    dismissProgressDialog();
                    System.out.println("订单" + jsonObject);
                    Intent intent = new Intent(BuyShopSetActivity.this, ShopOrderActivity.class);
                    OrderForm data = JsonUtils.getBean(jsonObject.optJSONObject("data"), OrderForm.class);
                    intent.putExtra(ORDER, data);
                    intent.putExtra(ShopOrderActivity.CLASS_SOURCE, "BuyShopSetActivity");
                    startActivity(intent);
                     User_For_pe peUser = AppContext.getInstance().getPEUser();
                    peUser.setScore(data.getScore());
                    AppContext.getInstance().setPEUser(peUser);
                }

                @Override
                public void onFailure() {
                    dismissProgressDialog();

                }
            });
        }
    }

        public void getData(){
        showProgressDialog(null);
            if(detailsdata==null){
                getShopId();
                getShopDetails();
            }else{
                setData(detailsdata);
            }
        getAddress();
        }

    private void getAddress() {
        RequestParams params = new RequestParams();
        params.add("uid", AppContext.getInstance().getPEUser().getUid());
        NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_GOODSADDRESS, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                dismissProgressDialog();
                System.out.println("收货地址" + addressS);
                JSONObject data = jsonObject.optJSONObject("data");
                addressS = JsonUtils.getBean(data, ShippingAddress.class);
                if (data == null) {
                    setAddressData(null);
                } else {
                    setAddressData(JsonUtils.getBean(data, ShippingAddress.class));
                }
            }

            @Override
            public void onFailure() {
                dismissProgressDialog();
            }
        });
    }

    private void setAddressData(ShippingAddress address) {
        if(address==null){
            addressRl.setVisibility(View.GONE);
            noAddressRl.setVisibility(View.VISIBLE);
        }else{
            addressS =address;
            addressRl.setVisibility(View.VISIBLE);
            noAddressRl.setVisibility(View.GONE);
            aid = address.getId();
            addressName.setText(address.getNickname());
            phoneNumber.setText(address.getTel());
            addressText.setText(AppUtils.getAdrress(address.getProvince(),address.getCity(),address.getArea()).append(address.getAddress()));
        }

    }


    public void getShopDetails() {
        RequestParams params = new RequestParams();
        params.add("uid", AppContext.getInstance().getPEUser().getUid());
        params.add("id", shopid);
        NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_GOODSDETLIS, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                dismissProgressDialog();
                System.out.println("商品详情：" + jsonObject.toString());
                detailsdata = JsonUtils.getBean(jsonObject.optJSONObject("data"), ShopDetailsBean.class);
                setData(detailsdata);
            }

            @Override
            public void onFailure() {
                dismissProgressDialog();
            }
        });
    }
    private void getShopId() {
        Intent intent = getIntent();
        Bundle extras=null;
        if(intent!=null){
            extras= intent.getExtras();
            if(extras!=null){
                shopid =extras.getString(ShopDetailsActivity.SHOPID);
            }
        }

    }
    private void setData(ShopDetailsBean data) {
        buyShopSetShopname.setText(data.getTitle());
        System.out.println("图片路径"+data.getImage());
        ImageLoader.getInstance().displayImage(data.getImage(), buyShopSetShopimage, ImageLoaderOptionsControl.getOptions(),new BackgroundImageLoadingListener(buyShopSetShopimage));
        buyShopSetShopimage.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                int width = buyShopSetShopimage.getWidth();
                buyShopSetShopimage.setLayoutParams(new LinearLayout.LayoutParams(width, width));
            }
        });
        buyForIntegral.setText(data.getScore()+"");
        postage.setText(data.getPostage());
        buyMoney.setText(data.getMoney());
        if(data.getCoupon()!=null&&data.getCoupon().size()>0){
            couponRL.setVisibility(View.VISIBLE);
            favorable.setVisibility(View.VISIBLE);
        }
    }
    public static void setDetailsData(ShopDetailsBean shopdata) {
        detailsdata = shopdata;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(play);
    }
}
