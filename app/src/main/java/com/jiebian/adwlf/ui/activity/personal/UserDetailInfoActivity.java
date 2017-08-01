package com.jiebian.adwlf.ui.activity.personal;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jiebian.adwlf.AppContext;
import com.jiebian.adwlf.Constants;
import com.jiebian.adwlf.R;
import com.jiebian.adwlf.adapter.GridAdapter;
import com.jiebian.adwlf.bean.InterestBean;
import com.jiebian.adwlf.bean.UserDetailInfo;
import com.jiebian.adwlf.bean.WeixinInfo;
import com.jiebian.adwlf.bean.request.ChangeInfo;
import com.jiebian.adwlf.bean.request.School;
import com.jiebian.adwlf.bean.returned.ItemReturn;
import com.jiebian.adwlf.bean.returned.UserDetailReturn;
import com.jiebian.adwlf.bean.user.User_For_pe;
import com.jiebian.adwlf.ebean.Province;
import com.jiebian.adwlf.net.EnWebUtil;
import com.jiebian.adwlf.ui.activity.BaseWebActivity;
import com.jiebian.adwlf.ui.activity.basic.SuperActivity;
import com.jiebian.adwlf.utils.AppUtils;
import com.jiebian.adwlf.utils.JsonUtils;
import com.jiebian.adwlf.net.NetworkDownload;
import com.jiebian.adwlf.util.BitmapManager;
import com.jiebian.adwlf.utils.DESUtils;
import com.jiebian.adwlf.util.EshareLoger;
import com.jiebian.adwlf.util.StringUtils;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author wu
 */
public class UserDetailInfoActivity extends SuperActivity {

    @InjectView(R.id.title_name)
    public TextView title;
    @InjectView(R.id.title_back)
    public ImageView title_back;
    @InjectView(R.id.title_save)
    public TextView title_save;
    @InjectView(R.id.head_image)
    public ImageView head_image;
    @InjectView(R.id.personal_name)
    public TextView personal_name;
    @InjectView(R.id.personal_sex)
    public View personal_sex;
    @InjectView(R.id.personal_age)
    public View personal_age;
    @InjectView(R.id.personal_province)
    public View personal_province;
    @InjectView(R.id.personal_school)
    public View personal_school;
    @InjectView(R.id.school)
    public TextView schooltext;
    @InjectView(R.id.personal_industry)
    public View personal_industry;
    @InjectView(R.id.personal_profession)
    public View personal_profession;
    @InjectView(R.id.sex)
    public TextView sex;
    @InjectView(R.id.age)
    public TextView age;
    @InjectView(R.id.province)
    public TextView province;
    @InjectView(R.id.industry)
    public TextView industry;
    @InjectView(R.id.profession)
    public TextView profession;
    @InjectView(R.id.personal_interest_txt)
    TextView personalInterestTxt;
    @InjectView(R.id.personal_quoted_price)
    LinearLayout personalQuotedPrice;
    @InjectView(R.id.personal_attestation)
    LinearLayout personalAttestation;
    @InjectView(R.id.personal_attestation_txt)
    TextView getPersonalAttestationTxt;

    @InjectView(R.id.personal_quoted_price_txt)
    EditText personalQuotedPriceTxt;
    @InjectView(R.id.personal_emali)
    LinearLayout emaliLL;
    @InjectView(R.id.personal_emali_txt)
    TextView personalEmalitTxt;
    private User_For_pe peUser;
    private UserDetailInfo mUserDetailInfo = new UserDetailInfo();
    private ItemReturn staList = new ItemReturn();
    private ItemReturn interList = new ItemReturn();
    private ItemReturn industryList = new ItemReturn();
    private ItemReturn professionList = new ItemReturn();
    private int sid = 1;
    private int cid = 1;
    private GridAdapter gridAdapter;
    private AlertDialog.Builder choice;
    private String[] detail;
    BitmapManager bmpManager;
    private School school1;
    private Handler handler = new Handler();
    private List<Province> list;
    private AlertDialog show;
    private static final int REQUESTSCHOOL=1;
    private static final int FANSRES=11;
    public  static final String FANSNUM="fansnum";
    public  static final String MONEY="money";
    private static final int DECIMAL_DIGITS=1;
    private BroadcastReceiver receiver;
    private boolean RECEIVERSTART;


    @Override
    public void onCreateDataForView() {
        initView();
        initDate();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_personal_info);
        Log.i("info", "布局");
    }

    /**
     * 加载微信的头像和名字
     */
    private void initDate() {
        bmpManager = new BitmapManager(BitmapFactory.decodeResource(AppContext.getInstance().getResources(),
                R.mipmap.image_def));
         peUser = AppContext.getInstance().getPEUser();
        if(peUser != null) {
            personal_name.setText(peUser.getUsername());
            if(!StringUtils.isEmpty(peUser.getImageurl())) {
                ImageLoader.getInstance().displayImage(peUser.getImageurl(), head_image);
            }
        }
        getInterestList();
    }

    /**
     * 初始化Dialog和标签数据
     */
    private void initView() {
        InputFilter lengthfilter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // 删除等特殊字符，直接返回
                if ("".equals(source.toString())) {
                    return null;
                }
                if(dest.toString().contains(".")){
                    int index = dest.toString().indexOf(".");
                    int len = dend - index;
                    //小数位只能2位
                    if(len > DECIMAL_DIGITS){
                        CharSequence newText = dest.subSequence(dstart, dend);
                        return newText;
                    }
                }
                return null;
            }
        };
        personalQuotedPriceTxt.setFilters(new InputFilter[]{lengthfilter});
        choice = new AlertDialog.Builder(this);
        gridAdapter = new GridAdapter(this, interList);
        title.setText("个人信息");
        showProgressDialog("正在加载信息。。。");
    }

    @OnClick({R.id.title_back, R.id.personal_sex, R.id.personal_age, R.id.personal_province,
            R.id.personal_industry, R.id.personal_profession, R.id.title_save,
            R.id.personal_school,R.id.personal_interest,R.id.head_image,R.id.personal_attestation,
            R.id.personal_emali})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                this.finish();
                break;
            case R.id.personal_sex:
                sexClick();
                break;
            case R.id.personal_age:
                ageClick();
                break;
            case R.id.personal_province:
                provinceClick();
                break;
            case R.id.personal_industry:
                industryClick();
                break;
            case R.id.personal_profession:
                professionClick();
                break;
            case R.id.title_save:
                save();
                break;
            case R.id.personal_school:
                startActivityForResult(new Intent(this,SchoolAct.class),REQUESTSCHOOL);
                break;
            case R.id.personal_interest:
                interestClick();
                break;
            case R.id.head_image:
                    updateWeixinMsg();
                break;
            case R.id.personal_attestation:
                if(TextUtils.isEmpty(mUserDetailInfo.roles_money)){
                    Toast.makeText(UserDetailInfoActivity.this, "请完善个人信息", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(this, AttestationActivity.class));
                }
                break;
            case R.id.personal_emali:
                startActivity(new Intent(this,SchoolAct.class));
                break;
        }
    }

    private void updateWeixinMsg() {
        AlertDialog.Builder b = new AlertDialog.Builder(UserDetailInfoActivity.this).setTitle("绑定微信号")
                .setMessage("绑定微信后可获取微信个人信息").setNeutralButton("绑定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setAccBroadcastReceiver();
                        IWXAPI api = WXAPIFactory.createWXAPI(UserDetailInfoActivity.this, Constants.APP_ID);
                        api.registerApp(Constants.APP_ID);
                        SendAuth.Req req = new SendAuth.Req();
                        req.scope = "snsapi_userinfo";
                        req.state = "none";
                        if (!api.sendReq(req)) {
                            Toast.makeText(UserDetailInfoActivity.this, "请安装微信App", Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        b.setCancelable(false);
        b.create();
        b.show();
    }

    private void interestClick() {
        AlertDialog.Builder choice = new AlertDialog.Builder(this);
        choice.setTitle("请选择兴趣");
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_style_e_type, null);
        ListView lv= (ListView) inflate.findViewById(R.id.dialog_lv);
        lv.setVisibility(View.VISIBLE);
        lv.setAdapter(gridAdapter);
        choice.setView(inflate);
        choice.show();

    }

    private void sexClick() {
        detail = new String[]{"男","女"};
        choice.setTitle("请选择性别");
        choice.setSingleChoiceItems(detail, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sex.setText(detail[which]);
                mUserDetailInfo.gender = detail[which];
                dialog.dismiss();
            }
        });
        choice.show();
    }


    private void ageClick() {
        // 固定写死数组提高效率
        detail = new String[]{"8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
                "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41",
                "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53",
                "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65",
                "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77",
                "78", "79", "80"};
        choice.setTitle("请选择年龄");
        choice.setSingleChoiceItems(detail, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                age.setText(detail[which]);
                mUserDetailInfo.age = Integer.parseInt(detail[which]);
                dialog.dismiss();
            }
        });
        choice.show();
    }



    private void provinceClick() {
        showProgressDialog(null);
        list=new ArrayList<>();
        getStateList();
    }
    private void provinceDone() {
        choice.setTitle("请选择省份");
        choice.setSingleChoiceItems(staList.toArea(), -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                province.setText(staList.get(which).item);
                sid = staList.get(which).sid;
                dialog.dismiss();
            }
        });
        choice.show();
    }






    private void industryClick() {
        showProgressDialog(null);
        getIndustryList();
    }
    private void industryDone() {
        choice.setTitle("请选择行业");
        choice.setSingleChoiceItems(industryList.toArea(), -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                industry.setText(industryList.get(which).item);
                mUserDetailInfo.did = industryList.get(which).did;
                dialog.dismiss();
            }
        });
        choice.show();
    }

    private void professionClick() {
        showProgressDialog(null);
        getProfessionList();
    }
    private void professionDone() {
        choice.setTitle("请选择职业");
        choice.setSingleChoiceItems(professionList.toArea(), -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                profession.setText(professionList.get(which).item);
                mUserDetailInfo.profession = professionList.get(which).item;
                if ("学生".equals(professionList.get(which).item)) {
                    personal_school.setVisibility(View.VISIBLE);
                } else {
                    personal_school.setVisibility(View.GONE);
                    school1 = new School();
                }
                dialog.dismiss();
            }
        });
        choice.show();
    }


    /**
     * 获取主界面的信息
     */
    private void requestInfo() {
        RequestParams params = new RequestParams();
        params.put("uid", peUser.getUid());
        params.put("token", peUser.getToken());
        NetworkDownload.byteGet(this, Constants.URL_GET_INFO_DETAIL, params, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String info = new String(responseBody);
                try {
                     String json = DESUtils.ebotongDecrypto(info);
                    System.out.println("调用新接口返回的主界面信息：" +json);
                    JSONObject jsonObj = new JSONObject(json);
                    UserDetailReturn udreturn = UserDetailReturn.parseJson(jsonObj);
                    UserDetailInfo userDetailInfo = UserDetailInfo.parseJson(udreturn.data);
                    System.out.println(userDetailInfo);
                    setDate(userDetailInfo);
                    dismissProgressDialog();
                } catch (JSONException e) {
                    dismissProgressDialog();
                    Toast.makeText(UserDetailInfoActivity.this, "获取用户信息发生错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure() {
                dismissProgressDialog();
            }
        });

    }
    public  void againrequest(){
        requestInfo();
    }

    /**
     * 根据请求的数据，设置显示的结果
     * @param userDetailInfo 接口返回的数据
     */
    private void setDate(UserDetailInfo userDetailInfo) {
        if(userDetailInfo != null ) {
            mUserDetailInfo = userDetailInfo;
            if(null==mUserDetailInfo.school||mUserDetailInfo.school.size()<1){
                school1 = new School();
            }else if(mUserDetailInfo.school.size()>0){
                school1=mUserDetailInfo.school.get(0);
                school1.setId(school1.getSid());
            }
            if("学生".equals(mUserDetailInfo.profession)){
                personal_school.setVisibility(View.VISIBLE);
                schooltext.setText(school1 == null? "": school1.getSc_name());
            } else {
                personal_school.setVisibility(View.GONE);
            }
            sex.setText(mUserDetailInfo.gender);
            age.setText(mUserDetailInfo.age + "");
            if(TextUtils.isEmpty(mUserDetailInfo.province)) {
                if(TextUtils.isEmpty(mUserDetailInfo.city)){
                    province.setText("");
                }else
                province.setText(mUserDetailInfo.city + "-" + mUserDetailInfo.area);
            }else{
                province.setText(mUserDetailInfo.province + "-" + mUserDetailInfo.city + "-" + mUserDetailInfo.area);
            }
            if(mUserDetailInfo.industry != null && mUserDetailInfo.industry.size()!=0) {
                industry.setText(mUserDetailInfo.industry.get(0).item);
            }
            profession.setText(mUserDetailInfo.profession);
            if(!TextUtils.isEmpty(mUserDetailInfo.roles_money)){

            }
            if(gridAdapter != null) {
                gridAdapter.setChecked(mUserDetailInfo.interest);
                if(mUserDetailInfo.interest == null)
                    return;
                for(InterestBean bean : mUserDetailInfo.interest) {
                    for(int i=0;i<interList.msg.size();i++) {
                        if(interList.msg.get(i).iid == bean.iid) {
                            interList.msg.get(i).checked = true;
                            break;
                        }
                    }
                }
                setInterestTxt();
            }
        }

        List<UserDetailInfo.Roles> roles = userDetailInfo.roles;
        int size = roles.size();
        String name="";
        for(int i=0;i<size;i++){
            if("1".equals(roles.get(i).state)){
                personalQuotedPrice.setVisibility(View.VISIBLE);
                personalAttestation.setVisibility(View.GONE);
                if(i>0){
                    name=name+",";
                }
                name=name+roles.get(i).name;
            }
        }
        if(TextUtils.isEmpty(name)){
            personalQuotedPriceTxt.setEnabled(false);
            getPersonalAttestationTxt.setText("未认证");
        }else {
            getPersonalAttestationTxt.setText(name);
        }

        if(!TextUtils.isEmpty(userDetailInfo.roles_money)){
            personalQuotedPrice.setVisibility(View.VISIBLE);
            personalQuotedPriceTxt.setText((userDetailInfo.roles_money) + "");
        }
        if(!TextUtils.isEmpty(userDetailInfo.emali)){
            emaliLL.setVisibility(View.VISIBLE);
            personalEmalitTxt.setText(userDetailInfo.emali);
        }else{
            emaliLL.setVisibility(View.GONE);
        }
    }

    public void setInterestTxt() {
        StringBuffer sb=new StringBuffer();
            List<InterestBean> msg = interList.msg;
            for(int i=0;i< msg.size();i++) {
                if(msg.get(i).checked) {
                   sb.append(msg.get(i).item+ ",");
                }
            }

        if (sb.length() > 2) {
            sb.delete(sb.length() - 1, sb.length());
        }
        personalInterestTxt.setText(sb);
    }

    /**
     * 获取省市区
     */
    private void getStateList() {
        analysisAddressMes(null);
    }
    private void analysisAddressMes(final JSONObject bytes) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                        final List<Province> list=AppUtils.getAddressMes(null,UserDetailInfoActivity.this,0);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                setAdressList(list);
                            }
                        });
            }
        }
        ).start();
    }

    private void setAdressList(List<Province> list) {
        this.list.addAll(list);
        dismissProgressDialog();
        showD();
    }
    private void showD() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.style_address_dialog, null);
        dialog.setView(inflate);
        Context context = dialog.getContext();

       final Spinner pros = (Spinner) inflate.findViewById(R.id.dialog_pro);
       final  Spinner citys = (Spinner) inflate.findViewById(R.id.dialog_city);
       final Spinner areas = (Spinner) inflate.findViewById(R.id.dialog_area);
        Button no= (Button) inflate.findViewById(R.id.dialog_ad_dont);
        Button ok= (Button) inflate.findViewById(R.id.dialog_ad_ok);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=pros.getSelectedItem()){
                    mUserDetailInfo.province=pros.getSelectedItem().toString();
                }else{
                    mUserDetailInfo.province="";
                }
                if(null!= citys.getSelectedItem()){
                    mUserDetailInfo.city=citys.getSelectedItem().toString();
                }else{
                    mUserDetailInfo.city="";
                }
                if(null!=areas.getSelectedItem()){
                    mUserDetailInfo.area=areas.getSelectedItem().toString();
                }else{
                    mUserDetailInfo.area="";
                }

                province.setText(mUserDetailInfo.province+"-"+mUserDetailInfo.city+"-"+mUserDetailInfo.area);
                show.dismiss();
            }
        });
        int size = this.list.size();
        List<String> strlist=new ArrayList<>();
        for(int i=0;i<size;i++){
            strlist.add(this.list.get(i).getItem());

        }
        pros.setAdapter(new ArrayAdapter<String>(this,R.layout.simple_spinner_item,strlist));
        pros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                citys.setAdapter(new ArrayAdapter<String>(UserDetailInfoActivity.this,
                        R.layout.simple_spinner_item,
                        AppUtils.getTown(pros.getSelectedItem().toString(), list)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        citys.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areas.setAdapter(new ArrayAdapter<String>(UserDetailInfoActivity.this,
                        R.layout.simple_spinner_item,
                        AppUtils.getArea(pros.getSelectedItem().toString(), citys.getSelectedItem().toString(),list)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dialog.setTitle("请选择地址");
        show = dialog.show();
    }



    /**
     * 获取标签显示的数据
     */
    private void getInterestList() {
        NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_INTEREST, null, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                List<InterestBean> data = JsonUtils.getBeanList(jsonObject.optJSONArray("data"), InterestBean.class);
                interList = new ItemReturn();
                interList.msg = data;
                gridAdapter.setData(interList);
                requestInfo();
            }

            @Override
            public void onFailure() {
                dismissProgressDialog();
            }
        });
    }

    /**
     * 获取行业的信息
     */
    private void getIndustryList() {
        NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_INDUSTRY, null, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                List<InterestBean> data = JsonUtils.getBeanList(jsonObject.optJSONArray("data"), InterestBean.class);
                industryList = new ItemReturn();
                industryList.msg = data;
                industryDone();
                dismissProgressDialog();
            }

            @Override
            public void onFailure() {
                dismissProgressDialog();
            }
        });

    }

    /**
     * 获取专业列表
     */
    private void getProfessionList() {
        NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_PROFESSION, null, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                List<InterestBean> data = JsonUtils.getBeanList(jsonObject.optJSONArray("data"), InterestBean.class);
                professionList = new ItemReturn();
                professionList.msg = data;
                professionDone();
                dismissProgressDialog();
            }

            @Override
            public void onFailure() {
                dismissProgressDialog();
            }
        });
    }


        /**
         * 保存数据
         */
    private void save() {
        if(TextUtils.isEmpty(sex.getText().toString())){
                Toast.makeText(this,"请选择性别",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(age.getText().toString())){
            Toast.makeText(this,"请选择年龄",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(province.getText().toString())){
            Toast.makeText(this,"请选择地址",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(industry.getText().toString())){
            Toast.makeText(this,"请选择行业",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(profession.getText().toString())){
            Toast.makeText(this,"请选择职业",Toast.LENGTH_SHORT).show();
        }else if(personal_school.getVisibility()==View.VISIBLE&&TextUtils.isEmpty(schooltext.getText().toString())){
            Toast.makeText(this,"请选择学校",Toast.LENGTH_SHORT).show();
        }else if(null!=gridAdapter&&!isRowsnum(gridAdapter)){

            Toast.makeText(this,"请选择你的兴趣",Toast.LENGTH_SHORT).show();
        }else {
            showProgressDialog(null);
            int agenum = 0;
            try {
                agenum = Integer.parseInt(age.getText().toString());
            } catch (Exception e) {
            }
            mUserDetailInfo.age = agenum;
            ChangeInfo changeInfo = mUserDetailInfo.toChangeInfo();
            if (gridAdapter != null) {
                changeInfo.setIid(gridAdapter.getRows());
            }
            changeInfo.school = school1.getId();
            changeInfo.xi = school1.getXi();
            changeInfo.banj = school1.getBanj();
            changeInfo.nianj = school1.getNianj();
            changeInfo.zhuany = school1.getZhuany();
            RequestParams b = new RequestParams();
            b.put("uid" , changeInfo.uid+"");
            b.put("token" , changeInfo.token);
            b.put("gender",changeInfo.gender);
            b.put("profession" , changeInfo.profession);
            b.put("city" ,changeInfo.city);
            b.put("area", changeInfo.area);
            b.put("age",changeInfo.age+"");
            b.put("did" , changeInfo.did+"");
            b.put("iid_array" ,changeInfo.iid_array);
            b.put("school" ,changeInfo.school);
            b.put("xi",changeInfo.xi);
            b.put("zhuany", changeInfo.zhuany);
            b.put("nianj" , changeInfo.nianj);
            b.put("banj",changeInfo.banj);
            b.put("province", changeInfo.province);
            if(personalQuotedPrice.getVisibility()==View.VISIBLE){
                b.put("roles_money", personalQuotedPriceTxt.getText().toString());
            }
            EnWebUtil.getInstance().post(this, new String[]{"JiebianInfo", "saveJiebianInfo"}, b, new EnWebUtil.AesListener2() {
                @Override
                public void onSuccess(String errorCode, String errorMsg, String data) {
                    Toast.makeText(UserDetailInfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    dismissProgressDialog();
                    requestInfo();
                }
                @Override
                public void onFail(String result) {
                    dismissProgressDialog();
                    Toast.makeText(UserDetailInfoActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean isRowsnum(GridAdapter gridAdapter) {
        List<InterestBean> rows = gridAdapter.getRows();
        for(InterestBean bean : rows) {
            if(bean.checked) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESTSCHOOL&&resultCode==RESULT_OK) {
            if (data != null) {
                School s = (School) data.getSerializableExtra("school");
                if(null!= s) {
                    school1 = s;
                }
                schooltext.setText(school1.getSc_name());
            }
        }else if(requestCode==FANSRES&&resultCode==RESULT_OK){
            System.out.println("设置粉丝");
            String stringExtra1 = data.getStringExtra(FANSNUM);
            personalQuotedPrice.setVisibility(View.VISIBLE);
            String stringExtra = data.getStringExtra(MONEY);
            personalQuotedPriceTxt.setText(stringExtra + "");
            mUserDetailInfo.roles_money=stringExtra;
            try {
                mUserDetailInfo.fannum=Integer.valueOf(stringExtra1);
            }catch (Exception e){
            }

        }
    }

    private void response(Intent data) throws JSONException {
        String json  = data.getExtras().getString(Constants.WEIXIN_ACCREDIT_KEY);
        final  WeixinInfo info = WeixinInfo.parseJson(new JSONObject(json));
        if (info != null) {
            RequestParams requestParams=new RequestParams();
            AppContext.getInstance().saveInfo(info);
            AppContext.getInstance().saveInfo(info);
            requestParams.put("weixin[platform]", "android");
            requestParams.put("weixin[unionid]", info.unionid);
            requestParams.put("weixin[imageurl]", info.headimgurl);
            requestParams.put("weixin[username]", info.nickname);
            requestParams.put("weixin[city]", info.city + "");
            requestParams.put("weixin[country]", info.country + "");
            requestParams.put("weixin[sex]", info.sex + "");
            requestParams.put("weixin[province]", info.province + "");
            requestParams.put("weixin[source]", AppContext.getInstance().getChannel());
            EnWebUtil.getInstance().post(null, new String[]{"OnwAccount", "saveOwnWeixin"}, requestParams, new EnWebUtil.AesListener() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject jsonObject=new JSONObject(result);
                         String errcode = jsonObject.optString("errcode");
                        Toast.makeText(UserDetailInfoActivity.this, jsonObject.optString("errmsg"), Toast.LENGTH_SHORT).show();
                        if(errcode.equals("0")){
                            User_For_pe peUser = AppContext.getInstance().getPEUser();
                            peUser.setWeixin(info.unionid);
                            peUser.setUsername(info.nickname);
                            peUser.setImageurl(info.headimgurl);
                            AppContext.getInstance().setPEUser(peUser);
                        }
                    } catch (JSONException e) {
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
        }else{
            dismissProgressDialog();
            Toast.makeText(UserDetailInfoActivity.this, "微信认证失败", Toast.LENGTH_SHORT).show();
        }
    }
    private void setAccBroadcastReceiver(){
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    response(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.WEIXIN_ACCREDIT);
        registerReceiver(receiver, filter);
        RECEIVERSTART =true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(RECEIVERSTART){
            unregisterReceiver(receiver);
        }

    }
}
