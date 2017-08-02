package com.cardholder.adwlf.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cardholder.adwlf.EnConstants;
import com.cardholder.adwlf.R;
import com.cardholder.adwlf.net.EnWebUtil;
import com.cardholder.adwlf.ui.activity.basic.SuperActivity;
import com.cardholder.adwlf.utils.AESUtils;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/11/2.
 */
public class Invoice extends SuperActivity {
    @InjectView(R.id.invoice_money_num)
    EditText invoiceMoneyNum;
    @InjectView(R.id.invoice_e_name)
    EditText invoiceEName;
    @InjectView(R.id.invoice_address)
    EditText invoiceAddress;
    @InjectView(R.id.invoice_linkman)
    EditText invoiceLinkman;
    @InjectView(R.id.invoice_phone)
    EditText invoicePhone;
    @InjectView(R.id.invoice_ok)
    Button invoiceOk;

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.invoice_title, "索要发票");
        getData();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_invoice);
    }
    @OnClick({R.id.invoice_ok})
    public void onClick(View view) {
        String eName = invoiceEName.getText().toString();
        String money = invoiceMoneyNum.getText().toString();
        String linkman = invoiceLinkman.getText().toString();
        String phone = invoicePhone.getText().toString();
        String address = invoiceAddress.getText().toString();
        double integer=0.00;
        try{
             integer = Double.valueOf(money);
        }catch (Exception e){

        }

        if(integer<1000){
            Toast.makeText(this, "发票金额最少1000元", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(eName) ||
                TextUtils.isEmpty(money) ||
                TextUtils.isEmpty(linkman) ||
                TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(address)
                ) {
            Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
        } else {
            RequestParams requestParams=new RequestParams();
            requestParams.put("title", eName);
            requestParams.put("money", money);
            requestParams.put("recipients", linkman);
            requestParams.put("tel", phone);
            requestParams.put("address", address);
            showProgressDialog(null);
            EnWebUtil.getInstance().post(this, EnConstants.URL_POST_INVOICE, requestParams, new EnWebUtil.AesListener() {
                @Override
                public void onSuccess(String result) {
                    dismissProgressDialog();
                    try {
                        JSONObject object = new JSONObject(result);
                        if("0".equals(object.optString("errcode"))){
                            Toast.makeText(Invoice.this,"索要发票成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Invoice.this,object.optString("errmsg"),Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFail(int code, String result) {
                    dismissProgressDialog();
                        Toast.makeText(Invoice.this,"请求失败",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onWebError() {
                    dismissProgressDialog();
                    Toast.makeText(Invoice.this,"请求失败",Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public void getData() {
        EnWebUtil.getInstance().post(this, EnConstants.URL_POST_INVOICE_MAXNUM, new RequestParams(), new EnWebUtil.AesListener() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    String data = AESUtils.decrypt(object.optString("data"));
                    JSONObject object1=new JSONObject(data);
                    double invoice_money = object1.optDouble("invoice_money");
                    invoiceMoneyNum.setHint("当前发票最大额度" + invoice_money + "元");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(int code, String result) {

            }

            @Override
            public void onWebError() {

            }
        });

    }
}
