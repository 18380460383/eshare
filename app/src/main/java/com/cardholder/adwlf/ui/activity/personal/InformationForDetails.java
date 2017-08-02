package com.cardholder.adwlf.ui.activity.personal;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.percent.PercentRelativeLayout;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.cardholder.adwlf.AppContext;
import com.cardholder.adwlf.Constants;
import com.cardholder.adwlf.R;
import com.cardholder.adwlf.adapter.InformationForDetailsAdapter;
import com.cardholder.adwlf.bean.returned.Comment;
import com.cardholder.adwlf.bean.returned.NewsDetials;
import com.cardholder.adwlf.control.DownPDF;
import com.cardholder.adwlf.control.ScreenControl;
import com.cardholder.adwlf.control.ShareControl;
import com.cardholder.adwlf.net.NetworkDownload;
import com.cardholder.adwlf.sql.DBManager;
import com.cardholder.adwlf.ui.activity.basic.ListViewActivity;
import com.cardholder.adwlf.util.EshareLoger;
import com.cardholder.adwlf.util.TextViewUtil;
import com.cardholder.adwlf.utils.AppUtils;
import com.cardholder.adwlf.utils.FileUtils;
import com.cardholder.adwlf.utils.JsonUtils;
import com.cardholder.adwlf.view.DragImageView;
import com.cardholder.adwlf.view.HistogramView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;

/**
 * 创建者：楊操
 * 时间：2016/4/22
 * 功能描述：1.1版资讯详情
 */
public class InformationForDetails extends ListViewActivity {
    public static final String NID = "nid";
    public static final String TITLENAME = "titlename";
    public static final int LOGINCODE = 6;
    @InjectView(R.id.home_details_listview)
    PullToRefreshListView lsitview;
    @InjectView(R.id.comment_ll)
    RelativeLayout commentLl;
    @InjectView(R.id.comment_editext)
    EditText editText;
    @InjectView(R.id.comment_button)
    Button commenting;
    @InjectView(R.id.c_home_details_menu)
    ImageView menu;
    @InjectView(R.id.large_bitmap)
    DragImageView largeBitmap;
    @InjectView(R.id.large_bitmap_rl)
    RelativeLayout largeBitmapRl;
    @InjectView(R.id.colse_large)
    ImageView colseLarge;
    @InjectView(R.id.save_image)
    ImageView saveIimage;


    List<Comment> commentList;
    List<Comment> commentBestList;
    @InjectView(R.id.video_fullView)
    FrameLayout video_fullView;
    private InformationForDetailsAdapter adapter;
    ImageView informationImage;
    TextView informationTitle;
    TextView informationTime;
    TextView informationDigest;
    TextView cHoneDetailsSeeMore;
    TextView cHoneDetailsSeePdf;
    TextView cHomeDetailsStandpointTxt;
    TextView cHomeDetailsStandpointHintTxt;
    RadioButton viewpoint1;
    RadioButton viewpoint2;
    RadioButton viewpoint3;
    HistogramView histogramview;
    WebView webview;
    LinearLayout relagLL;
    private View head;
    private int shareCid = 0;
    private int page = 1;
    private List<Integer> like;
    private String OPT;

    /**
     * 打开输入框
     */
    private static final int SHOWINPUTBOX = 0;
    /**
     * 关闭输入框
     */
    private static final int CLOSINPUTBOX = 1;
    /**
     * "@"其他人
     */
    private static final int AITEINPUTBOX = 2;
    private Map<String, String> map;
    private NewsDetials data;
    String stringExtraNID;
    private IWXAPI api;
    private Bitmap bmp;
    private BroadcastReceiver receiver;
    private boolean sharenews;
    private boolean sharecomment;
    private TextView source;
    private String altename;
    private int mycollect;
    private int beforeLoginState = 0;
    private TextView hits;
    private RadioButton allcomment;
    private RadioButton bestcomment;
    private Handler handler;
    private View xCustomView;
    private ProgressDialog waitdialog = null;
    private WebChromeClient.CustomViewCallback xCustomViewCallback;
    private myWebChromeClient xwebchromeclient;


    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_c_home_details);
    }

    @Override
    public void onCreateDataForView() {
        //TODO 设置评论输入框的删除事件（一次性删除@对象）
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String str = editText.getText().toString();
                    System.out.println("开始删除" + str);
                    if (str.endsWith("@" + altename)) {
                        str = str.replace("@" + altename, "");
                        System.out.println("str" + str);
                        editText.setText(str);
                        Selection.setSelection(editText.getText(), str.length());
                        try {
                            altename = null;
                            map.remove("to_uid");
                            map.remove("to_cid");
                        } catch (Exception e) {

                        }
                        return true;
                    } else {

                        return false;
                    }
                } else {
                    return false;
                }
            }
        });
        Intent intent = getIntent();
        stringExtraNID = intent.getStringExtra(NID);
        handler = new Handler();
        colseLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                largeBitmapRl.setVisibility(View.GONE);
            }
        });
        setAccBroadcastReceiver();
        commenting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppContext.getInstance().getPersonageOnLine()) {
                    //TODO 登陆code=3
                    goTOLogin(3);
                } else {
                    sendComment(map);
                }
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data != null) {

                    showMenuDialog(data);
                }
            }
        });
        menu.setVisibility(View.GONE);
        head = LayoutInflater.from(this).inflate(R.layout.head_home_details, null);
        setHeadID();
        ButterKnife.inject(head);
        String stringExtra = intent.getStringExtra(TITLENAME);
        if (TextUtils.isEmpty(stringExtra)) {
            stringExtra = "资讯详情";
        }
        setTitle(R.id.c_home_details_title, stringExtra);
        lsitview.getRefreshableView().addHeaderView(head);
        commentList = new ArrayList<>();
        commentBestList = new ArrayList<>();
        adapter = new InformationForDetailsAdapter(this, commentList, commentBestList);
        setmPullRefreshListView(lsitview, adapter);
        getData();

    }

    /**
     * 评论
     *
     * @param map
     */
    private void sendComment(Map<String, String> map) {
        hintKbTwo();
        String value = editText.getText().toString().replace("@" + altename, "");
        if (map == null || map.size() < 1) {
            map = getCommentMap();
        }
        if (TextUtils.isEmpty(OPT)) {
            showOPTDialog();
        } else if (TextUtils.isEmpty(value)) {
            Toast.makeText(InformationForDetails.this, "请填写你的评论", Toast.LENGTH_SHORT).show();
        } else if (value.length() > 250) {
            Toast.makeText(InformationForDetails.this, "评论字数过多", Toast.LENGTH_SHORT).show();
        } else {
            map.put("opt_id", OPT);
            map.put("content", value);
            RequestParams parm = AppUtils.getParm(map);
            showProgressDialog(null);
            NetworkDownload.jsonPostForCode1(this, Constants.URL_POST_ADD_NEWS_COMMENT, parm, new NetworkDownload.NetworkDownloadCallBackJson() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                    JSONObject data = jsonObject.optJSONObject("data");
                       /* commentList.add(JsonUtils.getBean(data, Comment.class));
                        adapter.notifyDataSetChanged();*/
                    updateComment(commentList);
                    editText.setText("");
                    InformationForDetails.this.map.clear();
                    dismissProgressDialog();
                    Toast.makeText(InformationForDetails.this, "发表成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure() {
                    InformationForDetails.this.map.clear();
                    dismissProgressDialog();
                    Toast.makeText(InformationForDetails.this, "发表失败", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    /**
     * 设置head
     */
    private void setHeadID() {
        informationImage = (ImageView) head.findViewById(R.id.information_image);
      /*  DecimalFormat df = new DecimalFormat("0");//格式化小数
        double v = (new ScreenControl().getscreenWide()) * 0.38;
        df.format(v);
        Integer integer = Integer.valueOf(df.format(v));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,integer);
        informationImage.setLayoutParams(layoutParams);*/
        informationTitle = (TextView) head.findViewById(R.id.information_title);
        source = (TextView) head.findViewById(R.id.news_source);
        relagLL = (LinearLayout) head.findViewById(R.id.relat_ll);
        informationTime = (TextView) head.findViewById(R.id.information_time);
        informationDigest = (TextView) head.findViewById(R.id.information_digest);
        cHoneDetailsSeeMore = (TextView) head.findViewById(R.id.c_hone_details_see_more);
        cHoneDetailsSeePdf = (TextView) head.findViewById(R.id.c_hone_details_see_pdf);
        webview = (WebView) head.findViewById(R.id.information_details_content);
        ScreenControl screenControl = new ScreenControl();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenControl.getscreenWide(), LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(12, 0, 12, 0);
        webview.setLayoutParams(params);
        cHomeDetailsStandpointTxt = (TextView) head.findViewById(R.id.c_home_details_standpoint_txt);
        cHomeDetailsStandpointHintTxt = (TextView) head.findViewById(R.id.c_home_details_standpoint_hint_txt);
        viewpoint1 = (RadioButton) head.findViewById(R.id.viewpoint_1);
        viewpoint2 = (RadioButton) head.findViewById(R.id.viewpoint_2);
        viewpoint3 = (RadioButton) head.findViewById(R.id.viewpoint_3);
        histogramview = (HistogramView) head.findViewById(R.id.histogramview);
        hits = (TextView) head.findViewById(R.id.hits);
        allcomment = (RadioButton) head.findViewById(R.id.radiobutton_all);
        allcomment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    adapter.setCommentType(0);
                    allcomment.setTextColor(getResources().getColor(R.color.text_hei));
                } else {
                    allcomment.setTextColor(getResources().getColor(R.color.en_de_textview));
                }

            }
        });
        bestcomment = (RadioButton) head.findViewById(R.id.radiobutton_best);
        bestcomment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    adapter.setCommentType(1);
                    bestcomment.setTextColor(getResources().getColor(R.color.text_hei));
                } else {
                    bestcomment.setTextColor(getResources().getColor(R.color.en_de_textview));
                }

            }
        });
        head.setVisibility(View.GONE);
    }

    private void getData() {
        getDataForDB();
        getCommentBest();

    }

    /**
     * 网络获取资讯详情
     */
    private void getInformation() {
        RequestParams params = new RequestParams();
        params.add("uid", AppContext.getInstance().getPEUser().getUid());
        params.add("nid", stringExtraNID);
        if (data == null) {
            showProgressDialog(null);
        }
        NetworkDownload.jsonGet(null, Constants.URL_Get_PERSONL_USER_NEWS_DETILS, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                if (jsonObject.optInt("code") == 1) {
                    data = JsonUtils.getBean(jsonObject.optJSONObject("data"), NewsDetials.class);
                    if (data != null) {
                        setData(data, 1);
                        UpdateDBInformation(data);
                    }
                } else if (jsonObject.optInt("code") == 99) {
                    deleteNews();
                }

                dismissProgressDialog();
            }

            @Override
            public void onFailure() {
                Toast.makeText(InformationForDetails.this, "更新资讯信息失败", Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    private void deleteNews() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBManager dbManager = DBManager.getDBManager(InformationForDetails.this);
                int delete = dbManager.delete(Constants.DB_USER_NEWS, "nid=?", new String[]{stringExtraNID});
            }
        }).start();
    }

    /**
     * 本地数据库获取资讯详情
     */
    private void getDataForDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBManager dbManager = DBManager.getDBManager(InformationForDetails.this);
                Cursor query = dbManager.query(true, Constants.DB_USER_NEWS, null, "nid=?", new String[]{stringExtraNID}, null, "1");
                if (query.getCount() > 0) {
                    if (query.moveToFirst()) {
                        final NewsDetials data = new NewsDetials();
                        data.setNid(query.getInt(query.getColumnIndex("nid")));
                        data.setContent(query.getString(query.getColumnIndex("content")));
                        data.setTitle(query.getString(query.getColumnIndex("title")));
                        data.setImage(query.getString(query.getColumnIndex("image")));
                        data.setDescription(query.getString(query.getColumnIndex("description")));
                        data.setSource(query.getString(query.getColumnIndex("source")));
                        data.setDatetime(query.getString(query.getColumnIndex("datetime")));
                        data.setHits(query.getInt(query.getColumnIndex("hits")));
                        JSONObject opinions = null;
                        try {
                            String opinions1 = query.getString(query.getColumnIndex("opinions"));
                            if (!TextUtils.isEmpty(opinions1)) {
                                opinions = new JSONObject(opinions1);
                                data.setOpinions(JsonUtils.getBean(opinions, NewsDetials.OpinionsEntity.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        data.setOpt_id(query.getString(query.getColumnIndex("opt_id")));
                        data.setOpinion(query.getString(query.getColumnIndex("opinion")));
                        data.setRelay_url(query.getString(query.getColumnIndex("relay_url")));
                        mycollect = query.getInt(query.getColumnIndex("mycollect"));
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                InformationForDetails.this.data = data;
                                setData(data, 0);
                            }
                        });
                    }
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getInformation();
                        getDataForComment();
                    }
                }, 2000);
            }
        }).start();
    }

    /**
     * 本地获取评论详情
     */
    private void getDataForComment() {
        DBManager dbManager = DBManager.getDBManager(InformationForDetails.this);
        Cursor queryc = dbManager.query(true, Constants.DB_USER_NEWS_COMMENT, null, "nid =" + stringExtraNID, null, "datetime asc", null);
        if (queryc.getCount() > 0) {
            int i = 0;
            if (queryc.moveToFirst()) {
                do {
                    Comment c = new Comment();
                    c.setCid(queryc.getInt(queryc.getColumnIndex("cid")));
                    c.setUid(queryc.getInt(queryc.getColumnIndex("uid")));
                    c.setOpt_id(queryc.getString(queryc.getColumnIndex("opt_id")));
                    c.setContent(queryc.getString(queryc.getColumnIndex("content")));
                    c.setDatetime(queryc.getString(queryc.getColumnIndex("datetime")));
                    c.setLikes(queryc.getInt(queryc.getColumnIndex("like")));
                    c.setRelay(queryc.getInt(queryc.getColumnIndex("relay")));
                    c.setReport(queryc.getInt(queryc.getColumnIndex("report")));
                    c.setTo_username(queryc.getString(queryc.getColumnIndex("to_username")));
                    c.setUsername(queryc.getString(queryc.getColumnIndex("username")));
                    c.setImageurl(queryc.getString(queryc.getColumnIndex("imageurl")));
                    c.setRelay_url(queryc.getString(queryc.getColumnIndex("relay_url")));
                    c.setIs_z(queryc.getInt(queryc.getColumnIndex("is_z")));
                    commentList.add(c);
                    i++;
                } while (queryc.moveToNext() && i < 40);
            }

        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                if (commentList.size() < 1) {
                    lsitview.setRefreshing();
                }
                getComment();
            }
        });

    }

    /**
     * 设置资讯详情信息（head上面信息）
     *
     * @param data
     */
    private void setData(final NewsDetials data, int i) {
        //System.out.println(data);
        head.setVisibility(View.VISIBLE);
        if (data.getOpinions() == null || data.getOpinions().getA() == null) {
            return;
        }
        adapter.setTitle(data.getTitle());
        if (!TextUtils.isEmpty(data.getFiles())) {
            cHoneDetailsSeePdf.setVisibility(View.VISIBLE);
        } else {
            cHoneDetailsSeePdf.setVisibility(View.GONE);
        }
        adapter.setOpinioncolor(data.getOpinions());
        if (!TextUtils.isEmpty(data.getOpt_id())) {
            String opt_id = data.getOpt_id();
            OPT = opt_id;
            setRBFocusable(0, data.getOpt_id());

        }
        //TODO　设置 每项评论 点赞，@，举报，的回调事件处理
        adapter.setBack(new InformationForDetailsAdapter.OnClickListenerBack() {
            @Override
            public void like(int cid) {
                ArrayList<Integer> cid1 = new ArrayList<>();
                cid1.add(cid);
                toLike(1, cid1);
                if (like == null) {
                    like = new ArrayList<Integer>();
                }
                like.add(cid);
            }

            @Override
            public void correlation(String name, int likeUid, int cid) {
                if (!AppContext.getInstance().getPersonageOnLine()) {
                    //TODO 登陆code=2
                    goTOLogin(2);
                } else {
                    if (!TextUtils.isEmpty(altename)) {
                        Toast.makeText(InformationForDetails.this, "一次只能@一个人", Toast.LENGTH_SHORT).show();
                    } else {
                        aite(name, likeUid, cid);
                    }
                }
            }

            @Override
            public void jubao(int cid) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("nid", stringExtraNID);
                map.put("uid", AppContext.getInstance().getPEUser().getUid());
                map.put("cids", "[" + cid + "]");
                RequestParams parm = AppUtils.getParmBean(map);
                NetworkDownload.jsonPostForCode1(null, Constants.URL_POST_REPORT, parm, new NetworkDownload.NetworkDownloadCallBackJson() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }

            @Override
            public void commentShow() {
                showInputBox();

            }

            @Override
            public void shareComment(int cid) {
                shareCid = cid;
                sharecomment = true;
            }
        });
        double v = new ScreenControl().getscreenWide() * 0.4;
        informationImage.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) v));
        //TODO 获取分享的图片
        if (!data.getImage().equals(data.getSharepic())) {
            ImageLoader.getInstance().displayImage(data.getImage(), informationImage);
        }
        adapter.setImagerl(data.getSharepic());
        adapter.setBigimagerl(data.getImage());
        ImageLoader.getInstance().loadImage(data.getSharepic(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if (bitmap == null) {
                    //Toast.makeText(InformationForDetails.this, "图片加载失败", Toast.LENGTH_SHORT).show();
                } else {
                    if (data.getImage().equals(data.getSharepic())) {
                        informationImage.setImageBitmap(bitmap);
                    }
                    bmp = Bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    adapter.setBmp(bmp);
                    menu.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        source.setText(data.getSource());
        hits.setText("阅读量：" + data.getHits());
        informationTitle.setText(data.getTitle());
        informationTime.setText(data.getDatetime());
        informationDigest.setText(data.getDescription());
        setWebView();
        cHoneDetailsSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = data.getContent();
                webview.setVisibility(View.VISIBLE);
                ScreenControl s = new ScreenControl();
                String replace = content.replace("<video ", "<video width=100% ");
                System.out.println("hhhhhhhhhhhhhhhhhhhhhhh" + replace.substring(0, 100));
                webview.loadDataWithBaseURL(null, "<html><style>" +
                        " img {max-width: 96%;height: auto;}\n" +
                        "</style> <body>" + replace + "</body></html>", "text/html", "UTF-8", null);
                cHoneDetailsSeeMore.setVisibility(View.GONE);
                if (cHoneDetailsSeePdf.getVisibility() == View.GONE) {
                    relagLL.setVisibility(View.GONE);
                }
            }
        });
        cHoneDetailsSeePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownPDF downPDF = new DownPDF(InformationForDetails.this, data.getFiles(), data.getFilesname(), handler, cHoneDetailsSeePdf);
                downPDF.init();
            }
        });


        final NewsDetials.OpinionsEntity opinions = data.getOpinions();
        System.out.println(opinions);
        if (opinions == null || opinions.getC() == null || opinions.getB() == null || opinions.getA() == null) {
            return;
        }
        viewpoint1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppContext.getInstance().getPersonageOnLine()) {
                    //TODO 登陆code=1
                    goTOLogin(1);
                    viewpoint1.setChecked(false);
                } else {
                    OPT = "A";
                    opinions.getA().setOpt_nums(opinions.getA().getOpt_nums() + 1);
                    viewpoint1.setChecked(true);
                    showOptHitDialog("A");
                }
            }
        });
        viewpoint1.setText(opinions.getA().getOpt_id() + "." + opinions.getA().getOpt_title() + "");
        viewpoint2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppContext.getInstance().getPersonageOnLine()) {
                    //TODO 登陆code=1
                    goTOLogin(1);
                    viewpoint2.setChecked(false);
                } else {
                    OPT = "B";
                    opinions.getB().setOpt_nums(opinions.getB().getOpt_nums() + 1);
                    viewpoint2.setChecked(true);
                    showOptHitDialog("B");
                }
            }
        });
        viewpoint2.setText(opinions.getB().getOpt_id() + "." + opinions.getB().getOpt_title() + "");
        viewpoint3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppContext.getInstance().getPersonageOnLine()) {
                    //TODO 登陆code=1
                    goTOLogin(1);
                    viewpoint3.setChecked(false);
                } else {
                    OPT = "C";
                    opinions.getC().setOpt_nums(opinions.getC().getOpt_nums() + 1);
                    viewpoint3.setChecked(true);
                    showOptHitDialog("C");
                }
            }
        });
        viewpoint3.setText(opinions.getC().getOpt_id() + "." + opinions.getC().getOpt_title() + "");
        String opt_id = data.getOpt_id();
        if ("A".equals(opt_id)) {
            viewpoint1.setChecked(true);
        } else if ("B".equals(opt_id)) {
            viewpoint2.setChecked(true);
        } else if ("C".equals(opt_id)) {
            viewpoint3.setChecked(true);
        }

        setHistogramView(opinions);

    }

    /**
     * 选择观点后给予无法重选的提示
     *
     * @param a 观点
     */
    private void showOptHitDialog(final String a) {
        if (AppContext.getInstance().getOptNoShow()) {
            setRBFocusable(1, a);
        } else {
            final Dialog b = new Dialog(this);
            b.requestWindowFeature(Window.FEATURE_NO_TITLE);
            View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_opt_hint, null);

            final CheckBox noshow = (CheckBox) inflate.findViewById(R.id.noshowdialog);
            TextView text = (TextView) inflate.findViewById(R.id.dialog_hint_text);
            text.setText("是否确认你选择的观点：" + a);
            TextView ok = (TextView) inflate.findViewById(R.id.ok_opt);
            TextView cloas = (TextView) inflate.findViewById(R.id.cloas_opt);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRBFocusable(1, a);
                    b.dismiss();
                    if (noshow.isChecked()) {
                        AppContext.getInstance().setOPTNoShow();
                    }
                }
            });
            cloas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    b.dismiss();
                    setRBNotFocusable();
                }
            });
            b.setContentView(inflate);
           /* ScreenControl s = new ScreenControl();
            int height = (int) (s.getscreenHigh() * 0.4);
            b.getWindow().setLayout((int) (s.getscreenWide() * 0.84), height);*/
            b.show();
            b.setCancelable(false);
        }

    }

    /**
     * 选择观点的控件回归常态
     */
    private void setRBNotFocusable() {
        viewpoint1.setChecked(false);
        viewpoint2.setChecked(false);
        viewpoint3.setChecked(false);
        viewpoint1.setEnabled(true);
        viewpoint2.setEnabled(true);
        viewpoint3.setEnabled(true);
    }

    /**
     * 设置条形统计图
     *
     * @param opinions 观点数据对象
     */
    private void setHistogramView(NewsDetials.OpinionsEntity opinions) {
        try {
            int opt_nums = opinions.getA().getOpt_nums();
            int opt_nums1 = opinions.getB().getOpt_nums();
            int opt_nums2 = opinions.getC().getOpt_nums();
            int i = opt_nums + opt_nums1 + opt_nums2;
            String s;
            if (opt_nums == 0) {
                s = "0%";
            } else {
                s = opt_nums * 100 / i + "%";
            }
            String s1;
            if (opt_nums1 == 0) {
                s1 = "0%";
            } else {
                s1 = opt_nums1 * 100 / i + "%";
            }

            String s2;
            if (opt_nums2 == 0) {
                s2 = "0%";
            } else {
                s2 = opt_nums2 * 100 / i + "%";
            }
            if (histogramview.iscreat()) {
                histogramview.empty();
            }
            histogramview.setColumnTitle(new String[]{
                    s,
                    s1,
                    s2});
            histogramview.setColumnNum(new int[]{
                    opinions.getA().getOpt_nums(),
                    opinions.getB().getOpt_nums(),
                    opinions.getC().getOpt_nums()});
            histogramview.setColor(new String[]{
                    opinions.getA().getOpt_color(),
                    opinions.getB().getOpt_color(),
                    opinions.getC().getOpt_color()
            });
            histogramview.setYPortion(5);
            //TODO　按照不同的观点量分配最大Y值
            int num = 0;
            if (i < 10) {
                num = 10;
            } else if (i < 20) {

                num = 20;
            } else if (i < 30) {

                num = 30;
            } else if (i < 40) {

                num = 40;
            } else if (i < 50) {

                num = 50;
            } else if (i < 100) {

                num = 100;
            } else if (i < 200) {

                num = 200;
            } else if (i < 500) {
                num = 500;
            } else {
                num = (int) (i * 1.5);
            }
            histogramview.setYMAX(num);

            histogramview.creation();
        } catch (Exception e) {

        }
    }

    /**
     * 让用户不能更改观点
     * 更新本地数据库数据
     *
     * @param opt
     */
    private void setRBFocusable(int i, String opt) {
        viewpoint1.setEnabled(false);
        viewpoint2.setEnabled(false);
        viewpoint3.setEnabled(false);
        if (opt != null) {
            UpdateDBInformation(data);
            setHistogramView(data.getOpinions());
            if (i == 1 && AppContext.getInstance().getPersonageOnLine()) {
                Map<String, String> map = new HashMap<>();
                map.put("opt_id", opt);
                map.put("nid", data.getNid() + "");
                map.put("uid", AppContext.getInstance().getPEUser().getUid());
                RequestParams parm = AppUtils.getParm(map);
                NetworkDownload.jsonPostForCode1(this, Constants.URL_POST_ADD_NEWS_COMMENT, parm, new NetworkDownload.NetworkDownloadCallBackJson() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                    }

                    @Override
                    public void onFailure() {
                    }
                });
            }
        }

    }

    /**
     * 给某一条评论点赞
     *
     * @param cid 评论ID
     */
    private void toLike(final int a, final List<Integer> cid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBManager dbManager = DBManager.getDBManager(AppContext.getInstance());
                int size = cid.size();
                for (int i = 0; i < size; i++) {
                    ContentValues values = new ContentValues();
                    values.put("is_z", a);
                    int update = dbManager.update(Constants.DB_USER_NEWS_COMMENT, values, "cid=" + cid.get(i), null);
                }

            }
        }).start();
    }

    /**
     * 更新本地数据库资讯详情信息
     *
     * @param data
     */
    private void UpdateDBInformation(final NewsDetials data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBManager dbManager = DBManager.getDBManager(InformationForDetails.this);
                ContentValues values = new ContentValues();
                Gson g = new Gson();
                values.put("title", data.getTitle());
                values.put("image", data.getImage());
                values.put("content", data.getContent());
                values.put("datetime", data.getDatetime());
                values.put("description", data.getDescription());
                values.put("source", data.getSource());
                values.put("opt_id", data.getOpt_id());
                values.put("opinions", g.toJson(data.getOpinions()));
                dbManager.update(Constants.DB_USER_NEWS, values, "nid=?", new String[]{data.getNid() + ""});
            }
        }).start();
    }

    /**
     * 显示选择评论的Dialog
     */
    private void showOPTDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_in_opt, null);
        ImageView close = (ImageView) inflate.findViewById(R.id.close_dialog);
        final RadioButton radioButtonA = (RadioButton) inflate.findViewById(R.id.opt_a);
        final RadioButton radioButtonB = (RadioButton) inflate.findViewById(R.id.opt_b);
        final RadioButton radioButtonC = (RadioButton) inflate.findViewById(R.id.opt_c);
        TextView btn = (TextView) inflate.findViewById(R.id.opt_ok);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDetials.OpinionsEntity opinions = data.getOpinions();
                if (radioButtonA.isChecked()) {
                    OPT = "A";
                    viewpoint1.setChecked(true);

                    opinions.getA().setOpt_nums(opinions.getA().getOpt_nums() + 1);
                    setRBFocusable(0, "A");
                    dialog.dismiss();
                } else if (radioButtonB.isChecked()) {
                    OPT = "B";
                    viewpoint2.setChecked(true);
                    opinions.getB().setOpt_nums(opinions.getB().getOpt_nums() + 1);
                    setRBFocusable(0, "B");
                    dialog.dismiss();
                } else if (radioButtonC.isChecked()) {
                    OPT = "C";
                    viewpoint3.setChecked(true);
                    opinions.getC().setOpt_nums(opinions.getC().getOpt_nums() + 1);
                    setRBFocusable(0, "C");
                    dialog.dismiss();
                } else {
                    Toast.makeText(InformationForDetails.this, "请选择观点", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setContentView(inflate);
        Window window = dialog.getWindow();
        ScreenControl screenControl = new ScreenControl();
        window.setLayout(screenControl.getscreenWide() - 80, screenControl.getscreenHigh() / 2);
        dialog.show();
    }


    /**
     * 当用户“@”某人是执行的方法
     *
     * @param name    被"@"人的姓名（昵称）
     * @param likeUid 被"@"的uid
     * @param cid     被“@”的当前评论
     */
    private void aite(String name, int likeUid, int cid) {
        altename = name;
        getCommentMap();
        map.put("to_uid", likeUid + "");
        map.put("to_cid", cid + "");
        showInputBox();
        String s = editText.getText().toString();
        SpannableStringBuilder str = new SpannableStringBuilder(s);
        editText.setText(str.append(TextViewUtil.getColorText("@" + name, "#2C94D4")));
        Selection.setSelection(editText.getText(), str.length());
    }

    /**
     * 获取基本的评论上传参数
     */
    private Map<String, String> getCommentMap() {
        map = new HashMap<String, String>();
        map.put("nid", data.getNid() + "");
        map.put("uid", AppContext.getInstance().getPEUser().getUid());
        return map;
    }

    private void showInputBox() {
        commentLl.setVisibility(View.VISIBLE);
    }

    private void closeInputBox() {
        commentLl.setVisibility(View.GONE);
    }

    /**
     * 获取网络最新评论信息
     */
    private void getComment() {
        RequestParams params = new RequestParams();
        params.add("uid", AppContext.getInstance().getPEUser().getUid());
        params.add("nid", getIntent().getStringExtra(NID));
        params.add("page", page + "");
        params.add("limit", "10");
        NetworkDownload.jsonGetForCode1(null, Constants.URL_Get_PERSONL_USER_NEWS_COMMENT, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                JSONArray data = jsonObject.optJSONArray("data");
                if (data != null) {
                    List<Comment> beanList = JsonUtils.getBeanList(data, Comment.class);

                    if (beanList != null && beanList.size() > 1) {
                        if (page == 1) {
                            commentList.clear();
                        }

                        commentList.addAll(beanList);
                        lsitview.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                    }
                    if (beanList != null && beanList.size() > 0) {
                        page++;
                    }
                    updateComment(beanList);
                }
                onrequestDone();
            }


            @Override
            public void onFailure() {
                onrequestDone();
            }
        });
    }

    /**
     * 获取网络最新精彩评论信息
     */
    private void getCommentBest() {
        RequestParams params = new RequestParams();
        params.add("uid", AppContext.getInstance().getPEUser().getUid());
        params.add("nid", getIntent().getStringExtra(NID));
        params.add("page", page + "");
        params.add("limit", "10");
        NetworkDownload.jsonGetForCode1(null, Constants.URL_Get_PERSONL_USER_NEWS_COMMENT_BEST, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                JSONArray data = jsonObject.optJSONArray("data");
                if (data != null) {
                    List<Comment> beanList = JsonUtils.getBeanList(data, Comment.class);

                    if (beanList != null && beanList.size() > 1) {
                        if (page == 1) {
                            commentBestList.clear();
                        }

                        commentBestList.addAll(beanList);
                        lsitview.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                    }
                    if (beanList != null && beanList.size() > 0) {
                        page++;
                    }
                    updateComment(beanList);
                }
                onrequestDone();
            }


            @Override
            public void onFailure() {
                //Toast.makeText(InformationForDetails.this, "获取最新评论失败", Toast.LENGTH_SHORT).show();
                onrequestDone();
            }
        });
    }

    /**
     * 更新本地评论
     *
     * @param beanList 评论列表
     */
    private void updateComment(final List<Comment> beanList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBManager dbManager = DBManager.getDBManager(InformationForDetails.this);
                for (Comment c : beanList) {

                    Cursor queryc = dbManager.query(true, Constants.DB_USER_NEWS_COMMENT, null, "cid = ?", new String[]{+c.getCid() + ""}, null, null);
                    if (queryc.getCount() > 0) {
                        ContentValues values = new ContentValues();
                        values.put("nid", InformationForDetails.this.stringExtraNID);
                        values.put("cid", c.getCid());
                        values.put("uid", c.getUid());
                        values.put("opt_id", c.getOpt_id());
                        values.put("to_username", c.getTo_username());
                        values.put("username", c.getUsername());
                        values.put("content", c.getContent());
                        values.put("datetime", c.getDatetime());
                        values.put("imageurl", c.getImageurl());
                        values.put("like", c.getLikes());
                        values.put("relay_url", c.getRelay_url());
                        dbManager.update(Constants.DB_USER_NEWS_COMMENT, values, "cid= ?", new String[]{c.getCid() + ""});
                    } else {
                        ContentValues values1 = new ContentValues();
                        values1.put("nid", InformationForDetails.this.stringExtraNID);
                        values1.put("cid", c.getCid());
                        values1.put("uid", c.getUid());
                        values1.put("opt_id", c.getOpt_id());
                        values1.put("to_username", c.getTo_username());
                        values1.put("username", c.getUsername());
                        values1.put("content", c.getContent());
                        values1.put("datetime", c.getDatetime());
                        values1.put("imageurl", c.getImageurl());
                        values1.put("like", c.getLikes());
                        values1.put("relay_url", c.getRelay_url());
                        dbManager.insert(Constants.DB_USER_NEWS_COMMENT, "空值", values1);
                    }
                }
            }
        }).start();
    }

    private void setWebView() {
        try {
            webview.setVerticalScrollBarEnabled(false);
            WebSettings settings = webview.getSettings();
            settings.setJavaScriptEnabled(true);
            //settings.setSupportZoom(true);
            settings.setBuiltInZoomControls(true);
            settings.setDomStorageEnabled(true);
            webview.addJavascriptInterface(this, "imagelistner");
            xwebchromeclient = new myWebChromeClient();
            webview.setWebViewClient(new myWebViewClient());
            webview.setWebChromeClient(xwebchromeclient);
        }catch (Exception e){

        }

    }

    public void setPullToRefreshListView() {
        lsitview.getRefreshableView().setAdapter(adapter);
        lsitview.setMode(PullToRefreshBase.Mode.DISABLED);
        lsitview.getLoadingLayoutProxy().setRefreshingLabel("正在获取数据");
        lsitview.getLoadingLayoutProxy().setPullLabel("数据更新");
        lsitview.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
        lsitview.setOnRefreshListener(this);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        super.onPullUpToRefresh(refreshView);
        getComment();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        if (like != null && like.size() > 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("cids", like);
            for (int i : like) {
                EshareLoger.logI("map:" + i);
            }
            map.put("nid", stringExtraNID);
            map.put("uid", AppContext.getInstance().getPEUser().getUid());
            RequestParams parmObj = AppUtils.getParmObj(map);
            NetworkDownload.jsonPostForCode1(AppContext.getInstance(), Constants.URL_POST_ZAN, parmObj, new NetworkDownload.NetworkDownloadCallBackJson() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                    System.out.println("电站" + jsonObject.toString());
                    toLike(1, like);
                }

                @Override
                public void onFailure() {
                    toLike(0, like);
                }
            });
        }

        video_fullView.removeAllViews();
        webview.loadUrl("about:blank");
        webview.stopLoading();
        webview.setWebChromeClient(null);
        webview.setWebViewClient(null);
        webview.removeAllViews();
        webview.setVisibility(View.GONE);
        webview.destroy();
        webview = null;
    }

    /**
     * 显示分享和收藏的对话框
     *
     * @param data
     */
    private void showMenuDialog(NewsDetials data) {
/*        final Dialog dialog=new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_inf_share_collect, null);
        TextView share= (TextView) inflate.findViewById(R.id.dialog_share);
        TextView collect= (TextView) inflate.findViewById(R.id.dialog_collect);
        if(mycollect==1){
            collect.setText("已收藏");
            collect.setFocusable(false);
        }
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {*/

        shareNews();
        /*            dialog.dismiss();
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AppContext.getInstance().getPersonageOnLine()){
                    //TODO 登陆code=7
                    goTOLogin(7);
                }else {
                    collectNews();
                    dialog.dismiss();
                }
            }
        });
        dialog.setContentView(inflate);
        Window window = dialog.getWindow();
        ScreenControl s=new ScreenControl();
        window.setLayout(s.getscreenWide(), s.getscreenHigh() / 3);
        window.setGravity(Gravity.BOTTOM);
        dialog.show();*/

    }

    /**
     * 收藏资讯
     */
    private void collectNews() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", AppContext.getInstance().getPEUser().getUid());
        map.put("nid", stringExtraNID);
        RequestParams parm = AppUtils.getParm(map);
        NetworkDownload.jsonPostForCode1(this, Constants.URL_POST_COLLECT, parm, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                Toast.makeText(InformationForDetails.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                mycollect = 1;
                DBManager dbManager = DBManager.getDBManager(InformationForDetails.this);
                ContentValues values = new ContentValues();
                values.put("mycollect", 1);
                dbManager.update(Constants.DB_USER_NEWS, values, "nid=?", new String[]{stringExtraNID});
            }

            @Override
            public void onFailure() {
                //Toast.makeText(InformationForDetails.this, "收藏失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 分享资讯
     */
    private void shareNews() {
        String title = data.getTitle();
        if (title.length() > 128) {
            title = title.substring(0, 127);
        }
        ShareControl shareControl = new ShareControl(this);
        shareControl.setBack(new ShareControl.shareonCompleteBack() {
            @Override
            public void onComplete() {
                sharenews = true;
                afterShare();
            }
        });

        String sharepic = data.getSharepic();
        System.out.println("sharepic" + sharepic);
        OnekeyShare onekeyShare = shareControl.getOnekeyShare(title, title, bmp, data.getRelay_url(), data.getImage(), data.getSharepic(), null);
        // 参考代码配置章节，设置分享参数
// 构造一个图标
        Bitmap enableLogo = BitmapFactory.decodeResource(this.getResources(), R.drawable.collect);
        String label = "收藏";
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                if (!AppContext.getInstance().getPersonageOnLine()) {
                    //TODO 登陆code=7
                    goTOLogin(7);
                } else {
                    collectNews();
                }
            }
        };
        onekeyShare.addHiddenPlatform(QQ.NAME);
        onekeyShare.setCustomerLogo(enableLogo, label, listener);
        onekeyShare.show(this);
       /* sharenews =true;
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = data.getRelay_url();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title =title;
        msg.setThumbImage(bmp);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = 1;
        initWeixin();
        if(api.sendReq(req)){
            Toast.makeText(InformationForDetails.this, "即将跳到微信", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(InformationForDetails.this, "微信无响应", Toast.LENGTH_LONG).show();
        }*/

    }

    private void initWeixin() {
        api = null;
        if (api == null) {
            api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        }
        api.registerApp(Constants.APP_ID);
    }

    private void setAccBroadcastReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.WEIXIN_SHARE) && intent.getExtras().getInt(Constants.WEIXIN_SHARE_KEY, 0) == 1) {
                    //Toast.makeText(InformationForDetails.this,"转发成功",Toast.LENGTH_SHORT).show();
                    afterShare();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.WEIXIN_SHARE);
        registerReceiver(receiver, filter);
    }

    /**
     * 分享后的后台反馈信息
     */
    private void afterShare() {
        //TODO　分享的是资讯
        if (sharenews) {
            sharenews = false;
            Map<String, String> map = new HashMap<>();
            map.put("uid", AppContext.getInstance().getPEUser().getUid());
            map.put("nid", stringExtraNID);
            RequestParams parm = AppUtils.getParm(map);
            NetworkDownload.jsonPostForCode1(this, Constants.URL_POST_SHARENEWS, parm, new NetworkDownload.NetworkDownloadCallBackJson() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                    System.out.println("分享" + jsonObject.toString());
                }

                @Override
                public void onFailure() {

                }
            });
        } else if (sharecomment) {
            sharecomment = false;
            sharecommOK();
        }
    }

    public void sharecommOK() {
        //TODO　分享的是评论
        Map<String, String> map = new HashMap<>();
        map.put("uid", AppContext.getInstance().getPEUser().getUid());
        map.put("nid", stringExtraNID);
        map.put("cid", shareCid + "");
        RequestParams parm = AppUtils.getParm(map);
        NetworkDownload.jsonPostForCode1(this, Constants.URL_POST_SHARE_COMMENT, parm, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    public void goTOLogin(int beforeLoginState) {
        this.beforeLoginState = beforeLoginState;
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("state", beforeLoginState);
        startActivityForResult(intent, LOGINCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGINCODE && resultCode == RESULT_OK && data.getIntExtra("loginstate", 0) == 1) {
            switch (this.beforeLoginState) {
                case 1://选择观点
                    break;
                case 2://@别人
                    break;
                case 3://直接评论
                    sendComment(map);
                    break;
                case 4://分享资讯
                    //shareNews();
                    break;
                case 5://点赞
                    break;
                case 6://分享评论
                    break;
                case 7://收藏资讯
                    collectNews();
                    break;
            }
        }
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @JavascriptInterface
    public void openImage(String imageurl) {
        if (!TextUtils.isEmpty(imageurl)) {
            ImageLoader.getInstance().loadImage(imageurl, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    if (bitmap != null) {
                        final Bitmap a = bitmap;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                largeBitmapRl.setVisibility(View.VISIBLE);
                                largeBitmap.setVisibility(View.VISIBLE);
                                largeBitmap.setImageBitmap(a);
                                saveIimage.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        File downloadFile = FileUtils.getDownloadFile();
                                        if (FileUtils.bitmapToFile(downloadFile, a)) {
                                            Toast.makeText(InformationForDetails.this, "成功保存图片到" + downloadFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                        });

                    }
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }


    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        webview.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
        /*webview.loadUrl("javascript:(" +
                "function(){" +
                " var objs = document.getElementsByTagName(\"img\");" +
                "var objsvideo = document.getElementsByTagName(\"video\");" +
                "for(var i=0;i<objs.length;i++){" +
                "objs[i].onclick=function()  {" +
                "window.imagelistner.openImage(this.src);" +
                "}" +
                "}}" +

                "var fullscreen = function(elem) {" +
                "  var prefix = 'webkit';" +
                "    if ( elem[prefix + 'EnterFullScreen'] ) {" +
                "    return prefix + 'EnterFullScreen';" +
                "    } else if( elem[prefix + 'RequestFullScreen'] ) {" +
                "    return prefix + 'RequestFullScreen';" +
                "    };" +
                "  return false;" +
                "};" +
                "function autoFullScrenn(v){" +
                "  var ua   = navigator.userAgent.toLowerCase();  " +
                "  var Android = String(ua.match(/android/i)) == \"android\";" +
                "  // if(!Android) return;//非android系统不使用;" +
                "  var video  = v,doc = document;" +
                "  var fullscreenvideo = fullscreen(doc.createElement(\"video\"));" +
                "  if(!fullscreen) {" +
                "    alert(\"不支持全屏模式\");" +
                "    return;" +
                "  }" +
                "  video.addEventListener(\"webkitfullscreenchange\",function(e){" +
                "    if(!doc.webkitIsFullScreen){//退出全屏暂停视频" +
                "      this.pause();" +
                "      // this.pause();" +
                "    };" +
                "  }, false);" +
                "  video.addEventListener(\"click\", function(){" +
                "    this.play();" +
                "    video[fullscreenvideo]();" +
                "  }, false);" +
                "  video.addEventListener('ended',function(){" +
                "    doc.webkitCancelFullScreen(); //播放完毕自动退出全屏" +
                "  },false);};)()");*/
    }

    /**
     * 杨操
     * 重写方法实现拦截退出大图模式
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (largeBitmapRl.getVisibility() == View.VISIBLE) {
                largeBitmapRl.setVisibility(View.GONE);
            }
            else if (inCustomView()) {
                // webViewDetails.loadUrl("about:blank");
                hideCustomView();
                return true;
            } else {
                webview.loadUrl("about:blank");
                InformationForDetails.this.finish();
            }
            return true;
        }
        return false;
    }

    /**
     * 设置界面的标题栏
     *
     * @param id   标题栏id
     * @param name 标题栏名
     */
    public void setTitle(int id, String name) {
        if (title == null && id != 0) {
            title = findViewById(id);
            PercentRelativeLayout viewById = (PercentRelativeLayout) title.findViewById(R.id.back);
            viewById.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (largeBitmapRl.getVisibility() == View.VISIBLE) {
                        largeBitmapRl.setVisibility(View.GONE);
                    } else {
                        finish();
                    }
                }
            });
            titleNameView = (TextView) findViewById(R.id.title_name);
            if (!TextUtils.isEmpty(name)) {
                titleNameView.setText(name);
            }

        }
    }


    public class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);

            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();

        }
    }

    public class myWebChromeClient extends WebChromeClient {
        private View xprogressvideo;

        // 播放网络视频时全屏会被调用的方法
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            webview.setVisibility(View.INVISIBLE);
            // 如果一个视图已经存在，那么立刻终止并新建一个
            if (xCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            video_fullView.addView(view);
            xCustomView = view;
            xCustomViewCallback = callback;
            video_fullView.setVisibility(View.VISIBLE);
        }

        // 视频播放退出全屏会被调用的
        @Override
        public void onHideCustomView() {
            if (xCustomView == null)// 不是全屏播放状态
                return;

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            xCustomView.setVisibility(View.GONE);
            video_fullView.removeView(xCustomView);
            xCustomView = null;
            video_fullView.setVisibility(View.GONE);
            xCustomViewCallback.onCustomViewHidden();
            webview.setVisibility(View.VISIBLE);
        }

     /*   // 视频加载时进程loading
        @Override
        public View getVideoLoadingProgressView() {
            if (xprogressvideo == null) {
                LayoutInflater inflater = LayoutInflater
                        .from(InformationForDetails.this);
                xprogressvideo = inflater.inflate(
                        R.layout.video_loading_progress, null);
            }
            return xprogressvideo;
        }*/
    }

    /**
     * 判断是否是全屏
     *
     * @return
     */
    public boolean inCustomView() {
        return (xCustomView != null);
    }

    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView() {
        xwebchromeclient.onHideCustomView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        super.onResume();
        webview.onResume();
        webview.resumeTimers();

        /**
         * 设置为横屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        webview.onPause();
        webview.pauseTimers();
    }

/*    @Override
    protected void onDestroy() {
        super.onDestroy();
        super.onDestroy();
        video_fullView.removeAllViews();
        webview.loadUrl("about:blank");
        webview.stopLoading();
        webview.setWebChromeClient(null);
        webview.setWebViewClient(null);
        webview.destroy();
        webview = null;
    }*/
/*    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inCustomView()) {
                // webViewDetails.loadUrl("about:blank");
                hideCustomView();
                return true;
            } else {
                webView.loadUrl("about:blank");
                MainActivity.this.finish();
            }
        }
        return false;
    }
}*/
}
