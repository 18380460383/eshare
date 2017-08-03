package com.kzmen.sczxjf.ui.fragment.personal;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.utils.AppUtils;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.ui.activity.personal.AttestationDetailActivity;
import com.kzmen.sczxjf.util.EIntentUtil;
import com.kzmen.sczxjf.util.EToastUtil;
import com.kzmen.sczxjf.util.EshareLoger;
import com.kzmen.sczxjf.util.ImageLoadHelper;
import com.kzmen.sczxjf.util.TextViewUtil;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Describe:
 * Created by FuPei on 2016/2/18.
 */
public class AttMediaFragment extends Fragment {

    private final int CODE_REQUEST = 1;
    @InjectView(R.id.et_name)
    public EditText et_name;
    @InjectView(R.id.et_company)
    public EditText et_company;
    @InjectView(R.id.et_belong)
    public EditText et_belong;
    @InjectView(R.id.tv_type)
    public TextView tv_type;
    @InjectView(R.id.iv_logo)
    public ImageView iv_logo;
    @InjectView(R.id.et_count_wbo)
    public EditText et_count_wbo;
    @InjectView(R.id.et_count_wxin)
    public EditText et_count_wxin;
    @InjectView(R.id.tv_commit)
    public TextView tv_commit;
    @InjectView(R.id.cb_hide)
    public CheckBox cb_hide;
    @InjectView(R.id.tv_state)
    public TextView tv_state;

    private String path_logo;
    private String url_logo;
    private String rid;
    private AttestationDetailActivity detailActivity;
    /**
     * 媒体类型列表
     */
    private ArrayList<String> list_type;

    public void setRoleId(String id) {
        rid = id;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        detailActivity = (AttestationDetailActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_att_media, null);
        ButterKnife.inject(this, view);
        initListener();
        getAttestInfo();
        return view;
    }

    private void initListener() {
        iv_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(EIntentUtil.selectPhoto(), CODE_REQUEST);
            }
        });

        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInfo()) {
                    uploadPic();
                }
            }
        });
        tv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypeDialog();
            }
        });
        TextViewUtil.setHideTextListener(cb_hide, et_name);
    }

    private MediaPerson getData() {
        MediaPerson person = new MediaPerson();
        person.setNickname(et_name.getText().toString());
        person.setMediaplate(et_belong.getText().toString());
        person.setMediatype(tv_type.getText().toString());
        person.setPresscard(url_logo);
        person.setWechatfans(et_count_wxin.getText().toString());
        person.setWeibofans(et_count_wbo.getText().toString());
        person.setCompany(et_company.getText().toString());
        if(cb_hide.isChecked()) {
            person.setNickname_hide("1");
        }
        return person;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (getActivity().RESULT_OK == resultCode) {
            path_logo = EIntentUtil.getPath(getActivity(), data.getData());
            ImageLoader.getInstance().displayImage("file://" + path_logo, iv_logo);
        }
    }
    private void updateInfo(MediaPerson person) {
        Map<String, Object> params = new HashMap<>();
        String uid = AppContext.getInstance().getPEUser().getUid();
        params.put("uid", uid);
        params.put("rid", rid);
        params.put("nickname", person.getNickname());
        params.put("mediaplate", person.getMediaplate());
        params.put("mediatype", person.getMediatype());
        params.put("presscard", person.getPresscard());
        params.put("weibofans", person.getWeibofans());
        params.put("wechatfans", person.getWechatfans());
        params.put("company", person.getCompany());
        params.put("nickname_hide", person.getNickname_hide());
        NetworkDownload.jsonPost(getActivity(), Constants.URL_UPDATE_ROLE, AppUtils.getParmObj(params), new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                detailActivity.dismissProgressDialog();
                MediaPerson enterpriseEntity = new Gson().fromJson(jsonObject.optString("data"), MediaPerson.class);
                refreshUI(enterpriseEntity);
                EshareLoger.logI("onSuccess:" + jsonObject.toString());
            }

            @Override
            public void onFailure() {
                EshareLoger.logI("onFail");
            }
        });
    }

    private boolean checkInfo() {
        if (TextUtils.isEmpty(et_name.getText().toString()) ||
                TextUtils.isEmpty(et_company.getText().toString()) ||
                TextUtils.isEmpty(et_belong.getText().toString()) ||
                TextUtils.isEmpty(tv_type.getText().toString())) {
            EToastUtil.show(getActivity(), "请填写完整信息!");
            return false;
        }
        if(TextUtils.isEmpty(path_logo)) {
            if (TextUtils.isEmpty(url_logo)) {
                EToastUtil.show(detailActivity, "请上传图片!");
                return false;
            }
        }
        return true;
    }

    /**
     * 获取认证信息的详细数据
     */
    private void getAttestInfo() {
        detailActivity.showProgressDialog("获取信息中...");
        RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getPEUser().getUid());
        params.put("rid", rid);
//        params.put("rid", "3");
        NetworkDownload.jsonGet(getActivity(), Constants.URL_ROLES_DETAIL, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                MediaPerson enterpriseEntity = new Gson().fromJson(jsonObject.optString("data"), MediaPerson.class);
                refreshUI(enterpriseEntity);
                list_type = getList_type(jsonObject.getJSONObject("data").optString("mediatype_arr"));
                detailActivity.dismissProgressDialog();
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
     * 刷新界面
     */
    private void refreshUI(MediaPerson entity) {
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

    private ArrayList<String> getList_type(String text) {
        ArrayList<String> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(text);
            for(int i = 0; i< array.length(); i++) {
                list.add(array.optString(i));
            }
        } catch (JSONException e) {

        }
        return list;
    }

    private void showTypeDialog() {
        AlertDialog.Builder choice = new AlertDialog.Builder(getActivity());
        choice.setTitle("请媒体类型");
        String[] types = new String[list_type.size()];
        types = list_type.toArray(types);
        choice.setItems(types, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_type.setText(list_type.get(which));
                dialog.dismiss();
            }
        }).show();
    }

    private void setEditUI() {
        setEnableEdit(false);
        tv_commit.setVisibility(View.GONE);
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
                            detailActivity.showProgressDialog("处理中...");
                            uploadPic();
                        }
                    }
                });
            }
        });
    }

    private void setEnableEdit(boolean canEdit) {
        TextViewUtil.setUIEnable(canEdit, et_name, et_company, tv_type, et_belong, iv_logo,
                et_count_wbo, et_count_wxin, cb_hide, iv_logo);
    }

    private void setLocalInfo(MediaPerson entity) {
        tv_state.setVisibility(View.VISIBLE);
        tv_commit.setVisibility(View.GONE);
        url_logo = entity.getPresscard();
        ImageLoader.getInstance().loadImage(url_logo, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                iv_logo.setImageResource(R.mipmap.image_def);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                iv_logo.setImageBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        et_name.setText(entity.getNickname());
        et_company.setText(entity.getCompany());
        et_belong.setText(entity.getMediaplate());
        tv_type.setText(entity.getMediatype());
        et_count_wxin.setText(entity.getWechatfans());
        et_count_wbo.setText(entity.getWeibofans());
    }

    private void uploadPic() {
        if(TextUtils.isEmpty(path_logo)) {
            if(!TextUtils.isEmpty(url_logo)) {
                updateInfo(getData());
            }
        } else {
            List<String> data = new ArrayList<>();
            data.add(path_logo);
            detailActivity.showProgressDialog("正在上传图片...");
            new ImageLoadHelper(getActivity(), data, new ImageLoadHelper.OnImageUpload() {
                @Override
                public void onFail() {
                    detailActivity.dismissProgressDialog();
                }

                @Override
                public void onSuccess(List<String> urls) {
                    url_logo = urls.get(0);
                    updateInfo(getData());
                }

                @Override
                public void onProgress(int i) {

                }
            }).startUpload(true);
        }

    }


    private class MediaPerson {

        private String nickname;
        private String mediaplate;
        private String mediatype;
        private String presscard;
        private String weibofans;
        private String wechatfans;
        private String company;
        private String state;
        private String nickname_hide;

        public String getNickname_hide() {
            return nickname_hide;
        }

        public MediaPerson setNickname_hide(String nickname_hide) {
            this.nickname_hide = nickname_hide;
            return this;
        }

        public String getCompany() {
            return company;
        }

        public MediaPerson setCompany(String company) {
            this.company = company;
            return this;
        }

        public String getState() {
            return state;
        }

        public MediaPerson setState(String state) {
            this.state = state;
            return this;
        }

        public String getNickname() {
            return nickname;
        }

        public String getMediaplate() {
            return mediaplate;
        }

        public String getMediatype() {
            return mediatype;
        }

        public String getPresscard() {
            return presscard;
        }

        public String getWeibofans() {
            return weibofans;
        }

        public String getWechatfans() {
            return wechatfans;
        }

        public MediaPerson setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public MediaPerson setMediaplate(String mediaplate) {
            this.mediaplate = mediaplate;
            return this;
        }

        public MediaPerson setMediatype(String mediatype) {
            this.mediatype = mediatype;
            return this;
        }

        public MediaPerson setPresscard(String presscard) {
            this.presscard = presscard;
            return this;
        }

        public MediaPerson setWeibofans(String weibofans) {
            this.weibofans = weibofans;
            return this;
        }

        public MediaPerson setWechatfans(String wechatfans) {
            this.wechatfans = wechatfans;
            return this;
        }
    }
}
