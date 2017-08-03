package com.kzmen.sczxjf.ui.activity.personal;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.BeasActivity;

/**
 * Created by Group on 2015/11/11.
 */
public class SuggestActivity extends BeasActivity {
    private EditText txt;
    private Button btn;
    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_suggest);

    }

    @Override
    public int setTitleId() {
        return R.id.suggest_title;
    }

    @Override
    public CharSequence setTitleName() {
        return "意见反馈";
    }

    @Override
    public void initView() {
        txt= (EditText) findViewById(R.id.suggest_txt);
        btn= (Button) findViewById(R.id.suggest_submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(txt.getText().toString())){
                    Toast.makeText(SuggestActivity.this,"请填写意见",Toast.LENGTH_SHORT).show();
                }else{
                    /*RequestParams requestParams = new RequestParams();
                    Map<String,String> map=new HashMap<String, String>();
                    map.put("uid", AppContext.getInstance().getUserInfo().uid+"");
                    map.put("suggest", txt.getText().toString());
requestParams.add("data", AppUtils.getParm(map));

                    new AsyncHttpClient().post("http://hdq1.360netnews.com/api.php/App/feedBack", requestParams, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            try {
                                String json = new String(bytes);
                                Log.i("tag", json);
                                JSONObject body = new JSONObject(DESUtils.ebotongDecrypto(json));
                                if (body.optInt("code") == 1) {
                                    Toast.makeText(SuggestActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SuggestActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(SuggestActivity.this, "信息正在处理中", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                            Toast.makeText(SuggestActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        }
                    });*/
                }
            }
        });
    }
}
