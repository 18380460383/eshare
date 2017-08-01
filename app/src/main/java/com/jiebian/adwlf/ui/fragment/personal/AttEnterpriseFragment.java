package com.jiebian.adwlf.ui.fragment.personal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiebian.adwlf.AppContext;
import com.jiebian.adwlf.Constants;
import com.jiebian.adwlf.R;
import com.jiebian.adwlf.ui.activity.basic.SuperActivity;
import com.jiebian.adwlf.bean.InterestBean;
import com.jiebian.adwlf.ui.fragment.basic.SuperFragment;
import com.jiebian.adwlf.utils.AppUtils;
import com.jiebian.adwlf.utils.JsonUtils;
import com.jiebian.adwlf.net.NetworkDownload;
import com.jiebian.adwlf.ui.activity.personal.AttestationDetailActivity;
import com.jiebian.adwlf.util.EIntentUtil;
import com.jiebian.adwlf.util.EToastUtil;
import com.jiebian.adwlf.util.EshareLoger;
import com.jiebian.adwlf.util.ImageLoadHelper;
import com.jiebian.adwlf.util.TextViewUtil;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Describe:企业认证界面
 * Created by FuPei on 2016/2/18.
 */
public class AttEnterpriseFragment extends SuperFragment {

    /**
     * 选择图片请求的CODE
     */
    private final int CODE_REQUEST_PHOTO = 1;

    @InjectView(R.id.et_name)
    public EditText et_name;
    @InjectView(R.id.et_ename)
    public EditText et_ename;
    @InjectView(R.id.et_address)
    public EditText et_address;
    @InjectView(R.id.tv_belong)
    public TextView tv_belong;
    @InjectView(R.id.iv_logo)
    public ImageView iv_logo;
    @InjectView(R.id.et_num)
    public EditText et_num;
    @InjectView(R.id.cb_state_num)
    public CheckBox cb_state_num;
    @InjectView(R.id.cb_state_phone)
    public CheckBox cb_state_phone;
    @InjectView(R.id.et_phone)
    public EditText et_phone;
    @InjectView(R.id.cb_state_earn)
    public CheckBox cb_state_earn;
    @InjectView(R.id.et_earn)
    public EditText et_earn;
    @InjectView(R.id.tv_commit)
    public TextView tv_commit;
    @InjectView(R.id.tv_state)
    public TextView tv_state;
    @InjectView(R.id.ly_content)
    public LinearLayout ly_content;

    private String url_logo;

    private AttestationDetailActivity detailActivity;

    /**
     * 图片的链接地址
     */
    private String path_logo;

    /**
     * 类型的id
     */
    private String rid;

    /**
     * 父Activity
     */
    SuperActivity baseActivity;

    public void setRoleId(String id) {
        rid = id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_att_enterprise, null);
        ButterKnife.inject(this, view);
        baseActivity = (SuperActivity) getActivity();
        initListener();
        getAttestInfo();
        return view;
    }

    /**
     * 刷新界面
     */
    private void refreshUI(EnterpriseEntity entity) {
        String state = entity.getState();
        if ("1".equals(state)) {
            //已通过审核
            tv_state.setText("");
            tv_state.append(TextViewUtil.getColorText("审核已通过!", "#73C23E"));
            setLocalInfo(entity);
            setEditUI();
        } else if ("0".equals(state)) {
            //审核中
            detailActivity.setRightShow(false, "");
            tv_state.setText("您的资料已提交审核! 请等待...");
            setLocalInfo(entity);
            setEnableEdit(false);
        } else if ("2".equals(state)) {
            //未通过审核
            tv_state.setText("审核未通过!");
            setLocalInfo(entity);
            setEditUI();
        } else {
            initListener();
            tv_state.setVisibility(View.GONE);
            tv_commit.setVisibility(View.VISIBLE);
        }
    }

    private void setEditUI() {
        setEnableEdit(false);
        detailActivity.setRightShow(true, "编辑");
        detailActivity.setRightOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEnableEdit(true);
                initListener();
                tv_state.setVisibility(View.GONE);
                detailActivity.setRightShow(true, "提交");
                detailActivity.setRightOnclick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkInfo()) {
                            updatePic();
                        }
                    }
                });
            }
        });
    }

    /**
     * 设置本地信息
     * @param entity
     */
    private void setLocalInfo(EnterpriseEntity entity) {
        tv_commit.setVisibility(View.GONE);
        tv_state.setVisibility(View.VISIBLE);
        url_logo = entity.getLicence();
        ImageLoader.getInstance().loadImage(url_logo, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                iv_logo.setImageResource(R.mipmap.image_def);
                iv_logo.setScaleType(ImageView.ScaleType.CENTER);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                iv_logo.setImageBitmap(bitmap);
                iv_logo.setScaleType(ImageView.ScaleType.FIT_XY);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        et_name.setText(entity.getNickname());
        et_ename.setText(entity.getCompany());
        et_address.setText(entity.getAddress());
        tv_belong.setText(entity.getIndustry());
        et_earn.setText(entity.getTurnover());
        et_num.setText(entity.getHeadcount());
        et_phone.setText(entity.getTel());

        if (entity.getTurnover_hidden() != null && entity.getTurnover_hidden().equals("1")) {
            cb_state_earn.setChecked(true);
        } else {
            cb_state_earn.setChecked(false);
        }

        if (entity.getHeadcount_hidden() != null && entity.getHeadcount_hidden().equals("1")) {
            cb_state_num.setChecked(true);
        } else {
            cb_state_num.setChecked(false);
        }

        if (entity.getTel_hidden() != null && entity.getTel_hidden().equals("1")) {
            cb_state_phone.setChecked(true);
        } else {
            cb_state_phone.setChecked(false);
        }
    }

    /**
     * 设置界面是否可以被编辑
     */
    private void setEnableEdit(boolean canEdit) {
        EshareLoger.logI("子view的数量:" + ly_content.getChildCount());
        TextViewUtil.setUIEnable(canEdit, et_name, et_address, et_ename, tv_belong, iv_logo,
                et_earn, et_num, et_phone, cb_state_earn, cb_state_num, cb_state_phone);
    }



    /**
     * 获取认证信息的详细数据
     */
    private void getAttestInfo() {
        detailActivity.showProgressDialog("获取信息中...");
        RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getPEUser().getUid());
        params.put("rid", rid);
        NetworkDownload.jsonGet(getActivity(), Constants.URL_ROLES_DETAIL, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                detailActivity.dismissProgressDialog();
                EnterpriseEntity enterpriseEntity = new Gson().fromJson(jsonObject.optString("data"), EnterpriseEntity.class);
                refreshUI(enterpriseEntity);
                EshareLoger.logI("getAttestInfo:\n" + jsonObject.toString());
            }

            @Override
            public void onFailure() {
                EToastUtil.show(detailActivity, "获取信息失败...");
                detailActivity.dismissProgressDialog();
                EshareLoger.logI("getAttestInfo: onFailure");
            }
        });
    }

    /**
     * 获取封装好的参数数据
     *
     * @return
     */
    private EnterpriseEntity getLocalData() {
        EnterpriseEntity entity = new EnterpriseEntity();
        entity.setNickname(et_name.getText().toString());
        entity.setCompany(et_ename.getText().toString());
        entity.setAddress(et_address.getText().toString());
        entity.setIndustry(tv_belong.getText().toString());
        entity.setTurnover(et_earn.getText().toString());
        entity.setHeadcount(et_num.getText().toString());
        entity.setTel(et_phone.getText().toString());
        entity.setLicence(url_logo);
        if (cb_state_earn.isChecked()) {
            entity.setTurnover_hidden("1");
        }
        if (cb_state_num.isChecked()) {
            entity.setHeadcount_hidden("1");
        }
        if (cb_state_phone.isChecked()) {
            entity.setTel_hidden("1");
        }
        return entity;
    }

    private void initListener() {
        iv_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(EIntentUtil.selectPhoto(), CODE_REQUEST_PHOTO);
            }
        });

        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInfo()) {
                    updatePic();
                }
            }
        });

        tv_belong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIndustryList();
            }
        });

        TextViewUtil.setHideTextListener(cb_state_earn, et_earn);
        TextViewUtil.setHideTextListener(cb_state_num, et_num);
        TextViewUtil.setHideTextListener(cb_state_phone, et_phone);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (getActivity().RESULT_OK == resultCode) {
            path_logo = EIntentUtil.getPath(getActivity(), data.getData());
            ImageLoader.getInstance().displayImage("file://" + path_logo, iv_logo);
        }
    }

    /**
     * 上传信息到服务器
     *
     * @param entity
     */
    private void saveInfo(EnterpriseEntity entity) {
        Map<String, Object> params = new HashMap<>();
        String uid = AppContext.getInstance().getPEUser().getUid();
        params.put("uid", uid);
        params.put("rid", rid);
        try {
            params.putAll(entity.toMap());
            RequestParams parmObj = AppUtils.getParmObj(params);
            baseActivity.showProgressDialog("正在上传信息");
            NetworkDownload.jsonPost(getActivity(), Constants.URL_UPDATE_ROLE, parmObj, new NetworkDownload.NetworkDownloadCallBackJson() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                    EToastUtil.show(baseActivity, "上传信息成功!");
                    EnterpriseEntity enterpriseEntity = new Gson().fromJson(jsonObject.optString("data"), EnterpriseEntity.class);
                    refreshUI(enterpriseEntity);
                    baseActivity.dismissProgressDialog();
                }

                @Override
                public void onFailure() {
                    EToastUtil.show(baseActivity, "上传信息失败!");
                    baseActivity.dismissProgressDialog();
                }
            });
        } catch (JSONException e) {
            EshareLoger.logI("生成map异常");
        }
    }

    /**
     * 检查本地数据是否填写完整
     *
     * @return
     */
    private boolean checkInfo() {

        if (TextViewUtil.checkIsEmpty(et_name, et_ename, et_address, et_earn, et_num, et_phone)) {
            EToastUtil.show(getActivity(), "请填写完整的信息");
            return false;
        }
        if (TextUtils.isEmpty(tv_belong.getText().toString().trim())) {
            EToastUtil.show(getActivity(), "请填写完整的信息");
        }

        if (TextUtils.isEmpty(path_logo)) {
            if(TextUtils.isEmpty(url_logo)) {
                EToastUtil.show(getActivity(), "请选择要上传的图片!");
                return false;
            }
        }
        return true;
    }

    /**
     * 获取行业的信息
     */
    private void getIndustryList() {
        NetworkDownload.jsonGetForCode1(getActivity(), Constants.URL_GET_INDUSTRY, null, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                List<InterestBean> data = JsonUtils.getBeanList(jsonObject.optJSONArray("data"), InterestBean.class);
                industryDone(data);
            }

            @Override
            public void onFailure() {

            }
        });

    }

    /**
     * 弹出选择类型dialog
     *
     * @param data
     */
    private void industryDone(final List<InterestBean> data) {
        String[] types = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            types[i] = data.get(i).item;
        }
        AlertDialog.Builder choice = new AlertDialog.Builder(getActivity());
        choice.setTitle("请选择行业");
        choice.setSingleChoiceItems(types, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                et_belong.setText(data.get(which).item);
                tv_belong.setText(data.get(which).item);
                dialog.dismiss();
            }
        });
        choice.show();
    }

    /**
     * 上传图片
     */
    private void updatePic() {
        //判断是否使用的是之前已经上传过的图片
        if(TextUtils.isEmpty(path_logo)) {
            if(!TextUtils.isEmpty(url_logo)) {
                saveInfo(getLocalData());
            }
        } else {
            baseActivity.showProgressDialog("正在上传图片...");
            List<String> list = new ArrayList<>();
            list.add(path_logo);
            new ImageLoadHelper(getActivity(), list, new ImageLoadHelper.OnImageUpload() {
                @Override
                public void onFail() {
                    baseActivity.dismissProgressDialog();
                }

                @Override
                public void onSuccess(List<String> urls) {
                    EshareLoger.logI("url = " + urls.get(0));
                    url_logo = urls.get(0);
                    saveInfo(getLocalData());
                }

                @Override
                public void onProgress(int i) {

                }
            }).startUpload(true);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        detailActivity = (AttestationDetailActivity) activity;
    }

    private class EnterpriseEntity {

        private String state;
        private String nickname;
        private String company;
        private String address;
        private String industry;
        private String licence;
        private String turnover;
        private String headcount;
        private String tel;
        private String turnover_hidden;
        private String tel_hidden;
        private String headcount_hidden;

        public HashMap<String, String> toMap() throws JSONException {
            JSONObject json = new JSONObject(new Gson().toJson(this));
            HashMap<String, String> map = new HashMap<>();
            Iterator<String> keys = json.keys();
            String key, value;
            while (keys.hasNext()) {
                key = keys.next();
                value = json.getString(key);
                if(value.length() > 0) {
                    map.put(key, value);
                }
            }
            return map;
        }

        public String getState() {
            return state;
        }

        public EnterpriseEntity setState(String state) {
            this.state = state;
            return this;
        }

        public String getTurnover_hidden() {
            return turnover_hidden;
        }

        public EnterpriseEntity setTurnover_hidden(String turnover_hidden) {
            this.turnover_hidden = turnover_hidden;
            return this;
        }

        public String getTel_hidden() {
            return tel_hidden;
        }

        public EnterpriseEntity setTel_hidden(String tel_hidden) {
            this.tel_hidden = tel_hidden;
            return this;
        }

        public String getHeadcount_hidden() {
            return headcount_hidden;
        }

        public EnterpriseEntity setHeadcount_hidden(String headcount_hidden) {
            this.headcount_hidden = headcount_hidden;
            return this;
        }

        public String getNickname() {
            return nickname;
        }

        public EnterpriseEntity setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public String getCompany() {
            return company;
        }

        public EnterpriseEntity setCompany(String company) {
            this.company = company;
            return this;
        }

        public String getAddress() {
            return address;
        }

        public EnterpriseEntity setAddress(String address) {
            this.address = address;
            return this;
        }

        public String getIndustry() {
            return industry;
        }

        public EnterpriseEntity setIndustry(String industry) {
            this.industry = industry;
            return this;
        }

        public String getLicence() {
            return licence;
        }

        public EnterpriseEntity setLicence(String licence) {
            this.licence = licence;
            return this;
        }

        public String getTurnover() {
            return turnover;
        }

        public EnterpriseEntity setTurnover(String turnover) {
            this.turnover = turnover;
            return this;
        }

        public String getHeadcount() {
            return headcount;
        }

        public EnterpriseEntity setHeadcount(String headcount) {
            this.headcount = headcount;
            return this;
        }

        public String getTel() {
            return tel;
        }

        public EnterpriseEntity setTel(String tel) {
            this.tel = tel;
            return this;
        }
    }
}
