package com.kzmen.sczxjf.ui.fragment.kzmessage;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshStaggeredGridLayout;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.Kz_ShopAdapter;
import com.kzmen.sczxjf.bean.kzbean.JiFenShopListItemBean;
import com.kzmen.sczxjf.control.CustomProgressDialog;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.fragment.basic.ListViewFragment;
import com.kzmen.sczxjf.utils.JsonUtils;

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
 * A simple {@link Fragment} subclass.
 */
public class GoodsCollectionFragment extends ListViewFragment implements Serializable {

    @InjectView(R.id.shop_list)
    PullToRefreshStaggeredGridLayout shopList;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;
    private View inflate;
    ImageView bjNullIv;
    TextView biTitle;
    LinearLayout bjLl;
    private String type = "1";
    private int page = 1;
    private List<JiFenShopListItemBean> list;
    private Kz_ShopAdapter adapter;
    private CustomProgressDialog dialog;

    /**
     * 标志位，标志已经初始化完成
     */
    public GoodsCollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection_goods, container, false);
        ButterKnife.inject(this, view);
        initView(view);
        list = new ArrayList<>();
        adapter = new Kz_ShopAdapter(getActivity(), list);
        isPrepared = true;
        lazyLoad();
        return view;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page = 1;
        list.clear();
        getData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page++;
        getData();
    }

    private void refre() {
        shopList.onRefreshComplete();
        adapter.notifyDataSetChanged();
    }

    public void setPullToRefreshListView() {
        shopList.getRefreshableView().setAdapter(adapter);
        shopList.setMode(PullToRefreshBase.Mode.BOTH);
        shopList.getLoadingLayoutProxy().setRefreshingLabel("正在获取数据");
        shopList.getLoadingLayoutProxy().setPullLabel("数据更新");
        shopList.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
        shopList.setOnRefreshListener(this);
        final Handler h = new Handler();
        ViewTreeObserver vto = shopList.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                System.out.println("tag" + "开始加载");
                shopList.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        shopList.onRefreshComplete();
                        shopList.setRefreshing(true);
                    }
                }, 500);

            }
        });
    }

    private void setNullListView(LinearLayout bjLL, ImageView iv, int rid, TextView bj_title, String str, int itemnum) {
        if (adapter != null && adapter.getItemCount() > itemnum) {
            llMain.setVisibility(View.GONE);
        } else {
            llMain.setVisibility(View.VISIBLE);
        }
    }

    private void initView(final View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("type");
        } else {
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.listview_null_bj, null);
        bjLl = (LinearLayout) inflate.findViewById(R.id.bj_ll);
        bjNullIv = (ImageView) inflate.findViewById(R.id.bj_null_iv);
        biTitle = (TextView) inflate.findViewById(R.id.bi_title);
        setEmpty();
        setPullToRefreshListView();
    }

    private void setEmpty() {
        setNullListView(bjLl, bjNullIv, R.drawable.no_g_start, biTitle, "暂无数据", 0);
    }

    private void getData() {
        Map<String, String> params = new HashMap<>();
        params.put("data[type]", type);
        params.put("data[page]", "" + page);
        params.put("data[limit]", "20");
        OkhttpUtilManager.postNoCacah(getActivity(), "User/getCollectList", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    List<JiFenShopListItemBean> listdata = JsonUtils.getBeanList(object.optJSONArray("data"), JiFenShopListItemBean.class);
                    if (listdata != null && listdata.size() > 0) {
                        page++;
                        list.addAll(listdata);
                    } else {
                        setEmpty();
                    }
                    refre();
                } catch (JSONException e) {
                    e.printStackTrace();
                    refre();
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                refre();
                setEmpty();
            }
        });
    }
}
