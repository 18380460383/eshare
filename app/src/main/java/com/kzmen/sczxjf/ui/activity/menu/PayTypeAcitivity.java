package com.kzmen.sczxjf.ui.activity.menu;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.OrderBean;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.TextUtil;
import com.vondear.rxtools.view.RxToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

import static com.kzmen.sczxjf.R.id.tv_acount_price;
import static com.kzmen.sczxjf.R.id.tv_count;

public class PayTypeAcitivity extends SuperActivity {
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.iv_reduce)
    ImageView ivReduce;
    @InjectView(tv_count)
    TextView tvCount;
    @InjectView(R.id.iv_add)
    ImageView ivAdd;
    @InjectView(R.id.ll_vip_recharge)
    LinearLayout llVipRecharge;
    @InjectView(R.id.tv_acount)
    TextView tvAcount;
    @InjectView(tv_acount_price)
    TextView tvAcountPrice;
    @InjectView(R.id.tv_acount_message)
    TextView tvAcountMessage;
    @InjectView(R.id.rb_acount)
    RadioButton rbAcount;
    @InjectView(R.id.rl_acount)
    RelativeLayout rlAcount;
    @InjectView(R.id.rb_weix)
    RadioButton rbWeix;
    @InjectView(R.id.ll_weix)
    LinearLayout llWeix;
    @InjectView(R.id.rb_ali)
    RadioButton rbAli;
    @InjectView(R.id.ll_ali)
    LinearLayout llAli;
    @InjectView(R.id.tv_sure)
    TextView tvSure;
    @InjectView(R.id.vv_vip)
    View vv_vip;
    private String title = "";
    private String price="";
    private int chargeYear=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        if (TextUtil.isEmpty(title)) {
            setTitle(R.id.kz_tiltle, "支付方式");
            llVipRecharge.setVisibility(View.GONE);
            vv_vip.setVisibility(View.GONE);
            //tvPrice.setText(price);
        } else {
            setTitle(R.id.kz_tiltle, title);
        }
        tvPrice.setText(price);
        if(orderBean!=null){
            tvPrice.setText(orderBean.getMoney());
            tvAcountPrice.setText("￥"+orderBean.getBalance());
            tvAcountMessage.setVisibility(Integer.valueOf(orderBean.getMoney())>Integer.valueOf(orderBean.getBalance())?View.VISIBLE:View.GONE);
        }
    }
    private OrderBean orderBean;
    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_pay_type_acitivity);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderBean= (OrderBean) bundle.getSerializable("orderBean");
            title = bundle.getString("title");
            price=bundle.getString("price");
        }
    }

    @OnClick({R.id.iv_reduce, R.id.iv_add, R.id.rl_acount, R.id.ll_weix, R.id.ll_ali,R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_reduce:
                if(chargeYear>1){
                    chargeYear--;
                    tvCount.setText(chargeYear+"年");
                }
                break;
            case R.id.iv_add:
                chargeYear++;
                tvCount.setText(chargeYear+"年");
                break;
            case R.id.rl_acount:
                resetRB();
                rbAcount.setChecked(!rbAcount.isChecked());
                RxToast.normal("余额支付");
                doPay("1");
                break;
            case R.id.ll_weix:
                resetRB();
                rbWeix.setChecked(!rbWeix.isChecked());
                RxToast.normal("微信支付");
                doPay("3");
                break;
            case R.id.ll_ali:
                resetRB();
                rbAli.setChecked(!rbAli.isChecked());
                RxToast.normal("支付宝支付");
                doPay("2");
                break;
            case R.id.tv_sure:
                break;
        }
    }
    private void doPay(String payment){
        if(orderBean==null){
            return;
        }
        Map<String,String>params=new HashMap<>();
        params.put("payment",payment);
        params.put("order",orderBean.getOrder());
        params.put("source",orderBean.getSource());
    }
    private void resetRB(){
        rbAcount.setChecked(false);
        rbWeix.setChecked(false);
        rbAli.setChecked(false);
    }
}
