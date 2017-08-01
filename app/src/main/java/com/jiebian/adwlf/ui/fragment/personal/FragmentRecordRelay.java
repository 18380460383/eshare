package com.jiebian.adwlf.ui.fragment.personal;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiebian.adwlf.AppContext;
import com.jiebian.adwlf.Constants;
import com.jiebian.adwlf.R;
import com.jiebian.adwlf.adapter.RecordRelayAdapter;
import com.jiebian.adwlf.bean.user.User_For_pe;
import com.jiebian.adwlf.ui.fragment.basic.SuperFragment;
import com.jiebian.adwlf.ui.activity.basic.SuperActivity;
import com.jiebian.adwlf.bean.RecordRelayBean;
import com.jiebian.adwlf.bean.Token7NiuBean;
import com.jiebian.adwlf.bean.UserInfo;
import com.jiebian.adwlf.bean.returned.RecordRelayReturn;
import com.jiebian.adwlf.control.CustomProgressDialog;
import com.jiebian.adwlf.utils.AppUtils;
import com.jiebian.adwlf.net.NetworkDownload;
import com.jiebian.adwlf.ui.activity.personal.DetialActivity;
import com.jiebian.adwlf.util.TLog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 转发明细
 * @author wu
 */
public class FragmentRecordRelay extends SuperFragment implements PullToRefreshBase.OnRefreshListener2 ,Serializable{

    private static final int SIZE = 10;
    @InjectView(R.id.listview)
    public PullToRefreshListView mPullRefreshListView;
    private RecordRelayAdapter adapter;
    private RecordRelayReturn listReturn = null;
    private List<RecordRelayBean>  rows=new ArrayList<>();
    private View rootView;
    private int currentPage = 1;
    private int pid;
    @InjectView(R.id.bj_ll)
    LinearLayout bjLL;
    @InjectView(R.id.bj_null_iv)
    ImageView iv;
    @InjectView(R.id.bi_title)
    TextView bj_title;
    private CustomProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragmentlist, container, false);
            ButterKnife.inject(this, rootView);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        initDate();
        request();
        return rootView;
    }

    public void initDate() {

        dialog = new CustomProgressDialog(getContext());
        dialog.setText("正在加载。。。");
        Bundle arguments = getArguments();
        adapter = new RecordRelayAdapter(getContext(), rows, this, arguments.getString("flg"));
        mPullRefreshListView.getRefreshableView().setAdapter(adapter);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullRefreshListView.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    mPullRefreshListView.setMode(PullToRefreshBase.Mode.MANUAL_REFRESH_ONLY);
                } else {
                    mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        mPullRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("正在获取数据");
        mPullRefreshListView.getLoadingLayoutProxy().setPullLabel("数据更新");
        mPullRefreshListView.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
        mPullRefreshListView.setOnRefreshListener(this);
        setListener();
    }

    public void setListener() {
        mPullRefreshListView.getRefreshableView().setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        RecordRelayBean bean = (RecordRelayBean) adapter.getItem(position - 1);
                        if (bean != null) {
                            Intent intent = new Intent(FragmentRecordRelay.this.getContext(), DetialActivity.class);
                            intent.putExtra("pid", bean.pid);
                            intent.putExtra("fromRecord", true);
                            FragmentRecordRelay.this.getContext().startActivity(intent);
                            DetialActivity.setFragment(FragmentRecordRelay.this);
                            TLog.log(bean.projectname);
                        }
                    }
                }
        );
    }

    public void request() {
        if(rows!=null&&rows.size()<1){
            getDate();
        }

    }

    /**
     * 请求网络数据 获取明细列表
     */
    public void getDate() {
         User_For_pe peUser = AppContext.getInstance().getPEUser();
        if(peUser == null) {
            onrequestDone();
            TLog.log("userinfo null");
            return;
        }
        mPullRefreshListView.setRefreshing();
        RequestParams params = new RequestParams();
        params.put("uid", peUser.getUid());
        params.put("token", peUser.getToken());
        params.put("page", currentPage);
        params.put("size", SIZE);
        NetworkDownload.jsonGetForCode1(AppContext.getInstance(), Constants.URL_GET_RELAYLIST, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                System.out.println("asdfdasfasdfas"+jsonObject.toString());
                if (currentPage == 1) {
                    rows.clear();
                }
                RecordRelayReturn listReturn = RecordRelayReturn.parseJson(jsonObject);
                rows.addAll(listReturn.data);
                currentPage++;
                onrequestDone();
            }

            @Override
            public void onFailure() {
                onrequestDone();
            }

        });

    }

    private void onrequestDone() {
        if(dialog.isShowing()) {
            dialog.dismiss();
        }
        adapter.notifyDataSetChanged();
        mPullRefreshListView.onRefreshComplete();
        AppUtils.setNullListView(adapter, bjLL, iv, R.mipmap.bj_recordrelay, bj_title, "你目前还没有转发过任何推送哦！" + "\n" + "赶快去转发赚钱吧！", 0);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        currentPage=1;
        getDate();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getDate();
    }

    // 选择图片上传
    public void getLocalImage(int pid) {
        this.pid = pid;
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, 1001);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            ((SuperActivity)getContext()).showProgressDialog(null);
            requestToken(uri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void requestToken(final Uri uri) {

        NetworkDownload.jsonGetForCode1(FragmentRecordRelay.this.getContext(), Constants.URL_POST_QINIUTOKEN, null, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                Token7NiuBean token = new Token7NiuBean();
                token.msg = jsonObject.optString("data");
                uploadImage(uri, token);
            }

            @Override
            public void onFailure() {
                ((SuperActivity) FragmentRecordRelay.this.getContext()).dismissProgressDialog();
            }
        });
    }

    private void uploadImage(Uri uri, Token7NiuBean token) {
        if(token == null) {
            ((SuperActivity)getContext()).dismissProgressDialog();
            Toast.makeText(FragmentRecordRelay.this.getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            return;
        }
        // 名称
        String key = AppContext.getInstance().getTime() + "User";
        try {
            key = key + AppContext.getInstance().getPEUser().getUid() + "Project";
        } catch (Exception e) {}

        UploadManager uploadManager = AppContext.getInstance().getUploadManager();
        uploadManager.put(uriToFile(uri), key, token.msg, new UpCompletionHandler() {
            @Override
            public void complete(String name, ResponseInfo responseInfo, JSONObject res) {
                try {
                    if (responseInfo != null && responseInfo.isOK()) {
                        String imageUrl = res.optString("key", "");
                        uploadScreenshot(imageUrl);
                    } else {
                        TLog.log(responseInfo == null ? "responseInfo null" : responseInfo.toString());
                        ((SuperActivity) getContext()).dismissProgressDialog();
                        Toast.makeText(FragmentRecordRelay.this.getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ((SuperActivity) getContext()).dismissProgressDialog();
                    Toast.makeText(FragmentRecordRelay.this.getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
               }
            }
        }, null);
    }

    private void uploadScreenshot(String imageUrl) {
        TLog.log("imageUrl " + imageUrl);
         User_For_pe peUser = AppContext.getInstance().getPEUser();
        if(peUser == null) {
            onrequestDone();
            TLog.log("userinfo null");
            return;
        }

        Map<String,String> map=new HashMap<>();
        map.put("uid", peUser.getUid());
        map.put("token", peUser.getToken());
        map.put("pid", pid + "");
        map.put("imageurl","http://7xnffx.com1.z0.glb.clouddn.com/" + imageUrl);
        RequestParams requestParams = AppUtils.getParm(map);

        NetworkDownload.bytePost(FragmentRecordRelay.this.getContext(), Constants.URL_UP_IMAGE_URL, requestParams, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ((SuperActivity) FragmentRecordRelay.this.getContext()).dismissProgressDialog();
                TLog.log("uploadScreenshot success");
                // 刷新状态
                changeState(pid);
            }

            @Override
            public void onFailure() {
                ((SuperActivity) FragmentRecordRelay.this.getContext()).dismissProgressDialog();
            }
        });
    }

    public  void changeState(int pid) {
        int size = rows.size();
        for(int i=0;i<size;i++){
            RecordRelayBean recordRelayBean = rows.get(i);
            if(recordRelayBean.pid==pid){
                recordRelayBean.ischeck=0;
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    private String  uriToFile(Uri uri) {
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            Cursor actualimagecursor = ((Activity)getContext()).managedQuery(uri, proj, null, null, null);
            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            return actualimagecursor.getString(actual_image_column_index);
        } catch (Exception e) {
        }
        return "";
    }

}
