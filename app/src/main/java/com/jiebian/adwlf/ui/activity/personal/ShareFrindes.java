package com.jiebian.adwlf.ui.activity.personal;

import android.content.Intent;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiebian.adwlf.AppContext;
import com.jiebian.adwlf.Constants;
import com.jiebian.adwlf.R;
import com.jiebian.adwlf.adapter.FansFriendsAdapter;
import com.jiebian.adwlf.ebean.ShareFrinde;
import com.jiebian.adwlf.ui.activity.basic.ListViewActivity;
import com.jiebian.adwlf.utils.JsonUtils;
import com.jiebian.adwlf.net.NetworkDownload;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;

/**
 * 我分享的好友展示界面
 * Created by 杨操 on 2015/11/30.
 */
public class ShareFrindes extends ListViewActivity {
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    /**
     * 展示好友数量
     */
    @InjectView(R.id.fans_num)
    TextView fansNum;
    /**
     *展示好友列表
     */
    @InjectView(R.id.fans_listview)
    PullToRefreshListView fansListView;
    /**
     *展示前缀标签
     */
    @InjectView(R.id.e_fans_letter)
    ListView eFansLetter;
    /**
     *没有好友的文本提示信息
     */
    @InjectView(R.id.share_frindes_hint)
    TextView hint;
    /**
     *没有好友时显示的界面内容
     */
    @InjectView(R.id.share_frinde_rl)
    RelativeLayout rl;
    /**
     *邀请好友
     */
    @InjectView(R.id.yaoqing)
    TextView yaoqing;

    private List<ShareFrinde> fans;
    private List<ShareFrinde> myFans;
    private List<ShareFrinde> frendfans;
    private FansFriendsAdapter adapter;
    private String type="0";
    private String friendid=null;
    /**
     * 排序用到的Map
     */
    private Map<String,List<ShareFrinde>> map;
    private String[] a=new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M",
                                    "N","O","P","Q","R","S","T","U","V","W","X","Y","Z","^"};
    private boolean isFrendFrends=false;
    private int frendPosition=0;

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_fans);
    }



    @Override
    public void onCreateDataForView() {
        setTitle(R.id.fans_title, "我的好友");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        yaoqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShareFrindes.this, ShareActivity.class));
            }
        });
        fans=new ArrayList<>();
        myFans = new ArrayList<>();
        eFansLetter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        adapter = new FansFriendsAdapter(fans,this);

        setmPullRefreshListView(fansListView, adapter);

        setADD();
    }

    public void getData() {
        getFans();
    }

    private void getFans() {
        fansNum.setVisibility(View.GONE);

        RequestParams params = new RequestParams();
        params.add("uid", AppContext.getInstance().getPEUser().getUid());
        params.add("type",type);
        if("3".equals(type)){
            params.add("friend",friendid);

        }
        NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_SHAREFRINDE, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                List<ShareFrinde> data = JsonUtils.getBeanList(jsonObject.optJSONArray("data"), ShareFrinde.class);
                fansNum.setText("总好友数：" + (data.size() < 1 ? 0 : data.size()));
                fans.clear();
                if ("3".equals(type)) {
                    frendfans.clear();
                    frendfans.addAll(getFrinde(data));
                    fans.addAll(frendfans);
                } else {
                    myFans.clear();
                    myFans.addAll(getFrinde(data));
                    fans.addAll(myFans);
                }
                refresh(type);
                onrequestDone();
            }

            @Override
            public void onFailure() {
               onrequestDone();
                refresh(type);
            }
        });
    }

    private void refresh(String type) {
        fansListView.onRefreshComplete();
        adapter.notifyDataSetChanged();
        int count = adapter.getCount();
        System.out.println("长度"+count);
        if(count <1){
            if("3".equals(type)){
                hint.setText("你的此位好友目前还没有邀请好友哦" + "\n" + "赶快叫他（她）邀请好友赚钱吧！");
            }else
            hint.setText("你目前还没有邀请好友哦" + "\n" + "赶快去邀请好友赚钱吧！");
            rl.setVisibility(View.VISIBLE);
            fansNum.setVisibility(View.GONE);
            eFansLetter.setVisibility(View.GONE);
        }else{
            fansNum.setVisibility(View.VISIBLE);
            eFansLetter.setVisibility(View.VISIBLE);
            rl.setVisibility(View.GONE);
                a[26]="#";
            eFansLetter.setAdapter(new ArrayAdapter<String>(this,R.layout.item_letter, a));
            eFansLetter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String key = a[position];
                    Integer integer = adapter.getMap().get(key);
                    if(null!=integer){
                        fansListView.getRefreshableView().setSelection(integer);
                    }
                }
            });
        }
    }
    private List<ShareFrinde> getFrinde(List<ShareFrinde> data){
        a[26]="^";
        List<ShareFrinde>  frindes=new ArrayList<>();
        List<ShareFrinde> f;
        map=new HashMap<>();

        for(int i=0;i<a.length;i++){
            f=new ArrayList<>();
            for(int j=0;j<data.size();j++){
                ShareFrinde shareFrinde = data.get(j);
               if(shareFrinde.getPinYin().equals(a[i])){
                    f.add(shareFrinde);
                }
            }
            map.put (a[i],f);
        }
        int size = map.size();
        for(int g=0;g< 27;g++){
            List<ShareFrinde> collection = map.get(a[g]);
            System.out.println("aaa"+collection);
            if(null!=collection){
                frindes.addAll(collection);
            }
        }
        System.out.println(frindes);
        return frindes;

    }
    private void  getFriendFriendList(String frienduid,CharSequence name){
        isFrendFrends=true;
        fans.clear();
        adapter.notifyDataSetChanged();
        showProgressDialog(null);
        titleNameView.setText(name);
        yaoqing.setVisibility(View.INVISIBLE);
        friendid=frienduid;
        type="3";
        fansListView.setRefreshing();
        getFans();
    }

    @Override
    public void onBackPressed() {
        if(isFrendFrends){
            fans.clear();
            fans.addAll(myFans);
            titleNameView.setText("我邀请的好友");
            fansNum.setText("总好友数：" + myFans.size());
            type="0";
            refresh("0");
            yaoqing.setVisibility(View.VISIBLE);
            fansListView.getRefreshableView().setSelection(frendPosition);
            isFrendFrends=false;
        }else{
            finish();
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!isFrendFrends) {
            frendPosition = position;
            if (null == frendfans) {
                frendfans = new ArrayList<ShareFrinde>();
            } else
                frendfans.clear();

            ShareFrinde shareFrinde = fans.get(position-1);
            getFriendFriendList(shareFrinde.getUid(), shareFrinde.getUserName());
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        fansListView.setMode(PullToRefreshBase.Mode.DISABLED);
        getFans();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        super.onPullUpToRefresh(refreshView);
       onrequestDone();
    }
}
