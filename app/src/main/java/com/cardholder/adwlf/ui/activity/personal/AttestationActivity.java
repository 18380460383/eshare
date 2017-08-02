package com.cardholder.adwlf.ui.activity.personal;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.cardholder.adwlf.AppContext;
import com.cardholder.adwlf.Constants;
import com.cardholder.adwlf.R;
import com.cardholder.adwlf.net.NetworkDownload;
import com.cardholder.adwlf.ui.activity.basic.SuperActivity;
import com.cardholder.adwlf.util.EshareLoger;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Describe:认证用户界面
 * Created by FuPei on 2016/2/18.
 */
public class AttestationActivity extends SuperActivity{

    private final String ID_MEDIA = "2";
    private final String ID_ENTERPRISE = "1";

    @InjectView(R.id.iv_enterprise)
    public ImageView iv_enterprise;
    @InjectView(R.id.iv_media)
    public ImageView iv_media;


    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_attestation);
        setTitle(R.id.attestation_title,getResources().getString(R.string.attestationactivity_title_cstr));
    }

    @Override
    public void onCreateDataForView() {
        initData();
    }
    @OnClick({R.id.iv_enterprise,R.id.iv_media})
    public void Listener(View view) {
        switch (view.getId()){
            case R.id.iv_enterprise:
                goDetail(AttestationDetailActivity.Type.enterprise, ID_ENTERPRISE);
                break;
            case R.id.iv_media:
                goDetail(AttestationDetailActivity.Type.media, ID_MEDIA);
                break;
        }
    }

    private void initData() {
        showProgressDialog("获取认证信息中...");
        getRoleList();
    }

    /**
     * 进入详情界面
     * @param type
     * @param id
     */
    private void goDetail( AttestationDetailActivity.Type type, String id) {
        Intent intent = new Intent(AttestationActivity.this, AttestationDetailActivity.class);
        intent.putExtra(AttestationDetailActivity.EXTRA_TYPE, type);
        intent.putExtra(AttestationDetailActivity.EXTRA_ID, id);
        startActivity(intent);
    }

    /**
     * 获取认证列表信息
     */
    private void getRoleList() {
        RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getPEUser().getUid());
        NetworkDownload.jsonGet(this, Constants.URL_GET_ROLELIST, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                EshareLoger.logI("获取认证列表 onSuccess = " + jsonObject.toString());
                if (jsonObject.optString("code").equals("1")) {
                    JSONArray array = jsonObject.optJSONArray("data");
                    RoleEntity entity;
                    for (int i = 0; i < array.length(); i++) {
                        entity = new Gson().fromJson(array.get(i).toString(), RoleEntity.class);
                        judgeRole(entity);
                    }
                    refreshUI();
                    dismissProgressDialog();
                } else {
                    dismissProgressDialog();
                    finish();
                }
            }

            @Override
            public void onFailure() {
                dismissProgressDialog();
                finish();
                EshareLoger.logI("onFail");
            }
        });
    }

    /**
     * 判断角色信息，以便传输
     * @param entity
     */
    private void judgeRole(RoleEntity entity) {
        if(null != entity.getState()) {
            if(entity.getId().equals(ID_ENTERPRISE)) {
                //状态为1时，才为认证成功
                if(entity.getState().equals("1")) {
                    iv_enterprise.setTag(true);
                }
            } else if(entity.getId().equals(ID_MEDIA)) {
                if(entity.getState().equals("1")) {
                    iv_media.setTag(true);
                }
            }
        }
    }

    /**
     * 刷新UI
     */
    private void refreshUI() {
        if(null != iv_enterprise.getTag() && (boolean)iv_enterprise.getTag()) {
            iv_enterprise.setImageResource(R.mipmap.attest_inter_yes);
        } else {
            iv_enterprise.setImageResource(R.mipmap.attest_inter_no);
        }
        if(null != iv_media.getTag() && (boolean)iv_media.getTag()) {
            iv_media.setImageResource(R.mipmap.attest_media_yes);
        } else {
            iv_media.setImageResource(R.mipmap.attest_media_no);
        }
    }

    private class RoleEntity{

        private String id;
        private String name;
        private String state;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getState() {
            return state;
        }
    }
}
