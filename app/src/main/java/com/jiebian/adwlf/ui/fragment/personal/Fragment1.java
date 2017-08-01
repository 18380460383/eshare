package com.jiebian.adwlf.ui.fragment.personal;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiebian.adwlf.AppContext;
import com.jiebian.adwlf.Constants;
import com.jiebian.adwlf.R;
import com.jiebian.adwlf.UIManager;
import com.jiebian.adwlf.adapter.AdPagerAdapter;
import com.jiebian.adwlf.adapter.C_Home_Adapter;
import com.jiebian.adwlf.adapter.InformationTypeAdapter;
import com.jiebian.adwlf.bean.AdBean;
import com.jiebian.adwlf.bean.UserInfo;
import com.jiebian.adwlf.bean.returned.AdReturn;
import com.jiebian.adwlf.bean.returned.HomeType;
import com.jiebian.adwlf.bean.returned.PersonalUserNews;
import com.jiebian.adwlf.net.NetworkDownload;
import com.jiebian.adwlf.sql.DBManager;
import com.jiebian.adwlf.ui.fragment.basic.ListViewFragment;
import com.jiebian.adwlf.util.EshareLoger;
import com.jiebian.adwlf.utils.AppUtils;
import com.jiebian.adwlf.utils.JsonUtils;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * 互动页面
 * @author wu
 */
public class Fragment1 extends ListViewFragment implements PullToRefreshBase.OnRefreshListener2 {

    public ViewPager adPager;
    public LinearLayout linearLayout;
    private ImageView[] adImageViews; // 点集合
    @InjectView(R.id.listview)
    PullToRefreshListView mPullRefreshListView;
    public TextView tv_forecast;
    ImageView wuad;
    private C_Home_Adapter adapter;
    private AdPagerAdapter adPagerAdapter;
    private AdReturn adListBean = new AdReturn();
    private int mViewCount; //view个数
    private int currIndex;
    private int page=1;
    private List<PersonalUserNews.NewsEntity> newsEntityList;
    private List<PersonalUserNews.ProjectEntity> interactionLsit;
    /**当手指触控ViewPager 计时器滑动失效*/
    private  boolean slide=true;
    /**用于发送轮播消息的{@link Handler}*/
    private Handler h;
    /**用于判断{@link #setpageslide}函数是否还在通过{@link Handler}做延时操作*/
    private boolean lideRun;
    private String keyword;
    private Handler handler;
    private View head;
    private RecyclerView viewById;
    private ArrayList<HomeType> homeTypeList;
    private InformationTypeAdapter typeadapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment1, container, false);
        ButterKnife.inject(this, rootView);

        initDate();
        request();
        try {
            ShortcutBadger.setBadge(getContext().getApplicationContext(), 0);
        } catch (ShortcutBadgeException e) {
            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(this.getView()!=null){
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 初始化数据
     */
    public void initDate() {
        head = LayoutInflater.from(getContext()).inflate(R.layout.head_fragmenthome, null);
        head.setPadding(0,0,0,8);
        viewById = (RecyclerView) head.findViewById(R.id.type_recyclerview);

        adPager= (ViewPager) head.findViewById(R.id.main_ad);
        linearLayout= (LinearLayout) head.findViewById(R.id.ad_dot_layout);
        tv_forecast= (TextView) head.findViewById(R.id.fr1_tv_forecast);
        tv_forecast.setVisibility(View.GONE);
        wuad= (ImageView) head.findViewById(R.id.main_wuad);
        adPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                slide = false;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        slide = false;
                        System.out.println("不 可滑动");
                        break;
                    case MotionEvent.ACTION_UP:
                        slide = true;
                        System.out.println("可滑动");
                        break;
                }
                return false;
            }
        });
        // 只初始化一次
        if(adapter == null) {
            newsEntityList=new ArrayList<>();
            interactionLsit=new ArrayList<>();
            adapter = new C_Home_Adapter(this,getContext(), newsEntityList,interactionLsit);

            mPullRefreshListView.getRefreshableView().addHeaderView(head);
            setmPullRefreshListView(mPullRefreshListView, adapter);
            setImageLoader(adapter.getImageLoader());
            adapter.notifyDataSetChanged();
        }
    }

    private void setTypeRecyclerview() {
        LinearLayoutManager layout = new LinearLayoutManager(getActivity()){
            @Override
            public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec,int heightSpec) {
                View view=null;
                try {
                     view = recycler.getViewForPosition(0);
                }catch (Exception e){

                }
                if(view != null){
                    measureChild(view, widthSpec, heightSpec);
                    int measuredWidth = View.MeasureSpec.getSize(widthSpec);
                    int measuredHeight = view.getMeasuredHeight();
                    setMeasuredDimension(measuredWidth, measuredHeight);
                }
            }
        };
        layout.setOrientation(LinearLayout.HORIZONTAL);
        viewById.setLayoutManager(layout);
        typeadapter = new InformationTypeAdapter(homeTypeList, getActivity());
        viewById.setAdapter(typeadapter);


    }

    private void getType() {
        System.out.println("jsonObject");
        NetworkDownload.jsonGetForCode1(null, Constants.URL_GET_NEWS_TYPE, null, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                List<HomeType> data = JsonUtils.getBeanList(jsonObject.optJSONArray("data"), HomeType.class);
                if (data != null) {
                    homeTypeList = new ArrayList<>();
                    homeTypeList.addAll(data);
                    setTypeRecyclerview();
                    System.out.println("jsonObject" + homeTypeList.size());
                    //typeadapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }
    public void setListener() {
        tv_forecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIManager.showForecastActivity((Activity) Fragment1.this.getContext());
            }
        });
    }


    public void request() {
            getDate();
        if((adListBean!=null&&adListBean.msg==null)||adListBean.msg.size()<1) {
            if(adListBean==null){
                adListBean = new AdReturn();
            }
            getAdList();
        }
    }


    /**
     * 获取数据
     */
    public void getDate() {
            handler = new Handler();
            getDBData();
    }

    private void getNews() {
        RequestParams params = new RequestParams();
        params.put("uid",AppContext.getInstance().getPEUser().getUid());
        params.put("page", page+"");
        params.put("limit",5+"");
        params.put("keyword", keyword);
        NetworkDownload.jsonGetForCode1(getContext(), Constants.URL_Get_PERSONL_USER_NEWS, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                List<PersonalUserNews.ProjectEntity> interactiondata = JsonUtils.getBeanList(jsonObject.optJSONObject("data").optJSONArray("project"), PersonalUserNews.ProjectEntity.class);
                List<PersonalUserNews.NewsEntity> newsdata = JsonUtils.getBeanList(jsonObject.optJSONObject("data").optJSONArray("news"), PersonalUserNews.NewsEntity.class);
                if (interactiondata != null || newsdata != null) {
                    if (page == 1) {
                        newsEntityList.clear();
                        interactionLsit.clear();
                        adapter.notifyDataSetChanged();
                    }
                    page++;
                    System.out.println(jsonObject.toString());
                    if (newsdata != null) {
                        updateDB(newsdata);
                        newsEntityList.addAll(newsdata);
                    }
                    if (interactiondata != null) {
                        interactionLsit.addAll(interactiondata);
                    }
                }

                onrequestDone();
            }


            @Override
            public void onFailure() {
                onrequestDone();
            }
        });
    }

    private void getDBData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final DBManager dbManager = DBManager.getDBManager(getContext());
                Cursor query = dbManager.query(false, Constants.DB_USER_NEWS, null, null, null, "datetime desc",null);
                System.out.println("查出来有几个" + query.getCount());
                if (query.moveToFirst()) {
                    do{
                        PersonalUserNews.NewsEntity object = new PersonalUserNews.NewsEntity();
                        object.setNid(query.getInt(query.getColumnIndex("nid")));
                        object.setCollect(query.getInt(query.getColumnIndex("collect")));
                        object.setComment(query.getInt(query.getColumnIndex("comment")));
                        object.setHits(query.getInt(query.getColumnIndex("hits")));
                        object.setImage(query.getString(query.getColumnIndex("image")));
                        object.setType_color(query.getString(query.getColumnIndex("type_color")));
                        object.setRelay(query.getInt(query.getColumnIndex("relay")));
                        object.setTitle(query.getString(query.getColumnIndex("title")));
                        object.setTypename(query.getString(query.getColumnIndex("typename")));
                        object.setJumpUrl(query.getString(query.getColumnIndex("jumpurl")));
                        object.setJumpType(query.getInt(query.getColumnIndex("jumptype")));
                        newsEntityList.add(object);
                        adapter.notifyDataSetChanged();
                    }while (query.moveToNext());
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        setADD();
                    }
                });

            }
        }).start();
    }

    private void updateDB(final List<PersonalUserNews.NewsEntity> news) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final DBManager dbManager = DBManager.getDBManager(getContext());
                for (PersonalUserNews.NewsEntity data:
                        news) {
                    final PersonalUserNews.NewsEntity datafor=data;
                    Cursor query = dbManager.query(true, Constants.DB_USER_NEWS, null, "nid=?", new String[]{datafor.getNid() + ""}, null, "1");
                    if(query!=null&&query.getCount()>0){
                        ContentValues values = new ContentValues();
                        values.put("collect",datafor.getCollect());
                        values.put("relay",datafor.getRelay());
                        values.put("comment",datafor.getComment());
                        values.put("hits",datafor.getHits());
                        values.put("datetime",datafor.getDatetime());
                        values.put("jumpurl",datafor.getJumpUrl());
                        values.put("jumptype",datafor.getJumpType());
                        dbManager.update(Constants.DB_USER_NEWS, values,"nid=?",new String[]{datafor.getNid()+""});
                    }else{
                        ContentValues values = new ContentValues();
                        values.put("nid",datafor.getNid());
                        values.put("title",datafor.getTitle());
                        values.put("typename",datafor.getTypename());
                        values.put("collect",datafor.getCollect());
                        values.put("image",datafor.getImage());
                        values.put("relay",datafor.getRelay());
                        values.put("comment",datafor.getComment());
                        values.put("type_color",datafor.getType_color());
                        values.put("hits",datafor.getHits());
                        values.put("datetime",datafor.getDatetime());
                        values.put("jumpurl",datafor.getJumpUrl());
                        values.put("jumptype",datafor.getJumpType());
                        dbManager.insert(Constants.DB_USER_NEWS,"无效数据", values);
                    }
                }
            }
        }).start();

    }

    // 获取广告
    public void getAdList() {
        RequestParams parm =new RequestParams();
        parm.put("limit", "1");
        NetworkDownload.jsonGetForCode1(null, Constants.URL_GET_AD, parm, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {

                adListBean.msg = JsonUtils.getBeanList(jsonObject.optJSONArray("data"), AdBean.class);
                if (adListBean.msg.size() > 0) {
                    resetViewPager();
                    wuad.setVisibility(View.GONE);
                    adPager.setVisibility(View.VISIBLE);
                } else {
                   /* adPager.setVisibility(View.GONE);
                    wuad.setImageBitmap(AppUtils.readBitMap(getContext(), R.mipmap.banner));
                    wuad.setVisibility(View.VISIBLE);
                    wuad.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UIManager.showGuideActivity((Activity) Fragment1.this.getContext());
                        }
                    });*/
                }
            }

            @Override
            public void onFailure() {
               /* adPager.setVisibility(View.GONE);
                wuad.setImageBitmap(AppUtils.readBitMap(getContext(), R.mipmap.banner));
                wuad.setVisibility(View.VISIBLE);
                wuad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UIManager.showGuideActivity((Activity) Fragment1.this.getContext());
                    }
                });*/
            }
        });
    }

    /**
     * 请求列表完成后回调
     */



    /**
     * 刷新
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page=1;
        getType();
        getNews();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        EshareLoger.logI("上滑加载更多");
        getNews();
    }




    private void resetViewPager() {
        adPager.addOnPageChangeListener(new MyOnPageChangeListener());
        mViewCount=adListBean.msg.size();
        initImagePager();
        adPagerAdapter = new AdPagerAdapter(getContext(), adListBean.msg);
        adPager.setAdapter(adPagerAdapter);

        if(mViewCount>1){
            adPager.setCurrentItem((adListBean.msg.size()) * 100);
            setpageslide();
        }

    }

    /**
     * 初始化点
     */
    private void initImagePager() {
        if(mViewCount==1){
            return;
        }
        //TODO 点的数目,adbean数目+说明页
        adImageViews = new ImageView[mViewCount];
        for(int i = 0; i < mViewCount; i++)
        {
            if(i != 0) {
                ImageView v = (ImageView) linearLayout.getChildAt(0);
                ViewGroup.LayoutParams pa = v.getLayoutParams();
                ImageView view = new ImageView(linearLayout.getContext());
                view.setImageResource(R.drawable.guide_round);
                view.setLayoutParams(pa);
                linearLayout.addView(view, i);
            }
            adImageViews[i] = (ImageView) linearLayout.getChildAt(i);
            adImageViews[i].setPadding(10, 0, 10, 0);
            adImageViews[i].setEnabled(true);
            adImageViews[i].setTag(i);
            adImageViews[i].setVisibility(View.VISIBLE);
        }
        currIndex = 0;
        adImageViews[currIndex].setEnabled(false);

    }

    /**
     * 设置广告条自动滑动
     */
    private void setpageslide() {
        lideRun =true;
        if(h==null){
            h = new Handler();
        }
        Log.i("tag", "滚动中");
        if (adListBean.msg.size() > 0 && slide) {
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (adListBean.msg.size() > 0 && slide) {
                        int currentItem = adPager.getCurrentItem();
                        if (currentItem >= adPagerAdapter.getCount()) {
                            currentItem = 400;
                        }
                        currentItem++;
                        Fragment1.this.adPager.setCurrentItem(currentItem);
                        if (slide) {
                            setpageslide();
                        }
                    }else{
                        lideRun=false;
                    }
                }
            }, 8000);
        }else{
            lideRun =false;
        }

    }

    /**
     *  设置点被选中
     */
    private void setCurPoint(int index) {
        if (index < 0 || index > mViewCount  || currIndex == index||mViewCount==1) {
            return ;
        }
        adImageViews[currIndex].setEnabled(true);
        adImageViews[index].setEnabled(false);
        currIndex = index;
    }


    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            setCurPoint(arg0%(adListBean.msg.size()+1));

        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    public void removeGeneralize(int pid){
        for (PersonalUserNews.ProjectEntity in :interactionLsit) {
            if((in.getPid()+"").equals(pid+"")){
                interactionLsit.remove(in);
                adapter.notifyDataSetChanged();
                break;
            }
        }

        onrequestDone();
    }

    @Override
    public void onPause() {
        super.onPause();
        slide = false;
    }
    public void activityOnPause(){
        slide = false;
    } public void activityOnResume(){
        slide = true;
        if(adListBean.msg!=null&&adListBean.msg.size() > 0 &&!lideRun){
            setpageslide();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        slide = true;
        if(adListBean.msg!=null&&adListBean.msg.size() > 0 &&!lideRun){
            setpageslide();
        }

    }
    public void setSlide(boolean slide) {
            this.slide = slide;
            if(slide){
                if(adListBean.msg!=null&&adListBean.msg.size() > 0 &&!lideRun){
                    setpageslide();
                }
            }
    }

    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        System.out.println("position" + position+"id"+id);
        if(interactionLsit.size()==0||id>interactionLsit.size()){
            Intent intent = new Intent(getContext(), InformationForDetails.class);
            intent.putExtra(InformationForDetails.NID,newsEntityList.get((int)id-interactionLsit.size()).getNid()+"");
            getContext().startActivity(intent);
        }else{
            PersonalUserNews.ProjectEntity projectEntity = interactionLsit.get((int)id);
            Intent intent = new Intent(getContext(), DetialActivity.class);
            ProjectBean value = new ProjectBean();
            value.pid=projectEntity.getPid();
            intent.putExtra("pid", projectEntity.getPid());
            DetialActivity.setFragment(this);
            getContext().startActivity(intent);
        }

    }*/
}
