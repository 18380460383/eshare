package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.returned.Comment;
import com.kzmen.sczxjf.bean.returned.NewsDetials;
import com.kzmen.sczxjf.control.ImageLoaderOptionsControl;
import com.kzmen.sczxjf.control.ShareControl;
import com.kzmen.sczxjf.sql.DBManager;
import com.kzmen.sczxjf.ui.activity.personal.InformationForDetails;
import com.kzmen.sczxjf.util.TextViewUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 创建者：杨操
 * 时间：2016/4/26
 * 功能描述：
 */
public class InformationForDetailsAdapter extends BaseAdapter {
    private Context context;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private List<Comment> commentList;
    private List<Comment> commentBestList;
    private OnClickListenerBack back;
    private IWXAPI api;
    private NewsDetials.OpinionsEntity opinioncolor;
    private Handler handler;
    private Bitmap bmp;
    private boolean upzan=true;
    private String title;
    private String imagerl;
    private String bigimagerl;
    /**
     * 评论类型 0全部；1 加精
     */
    private int COMMENTTYPE=0;

    public void setImagerl(String imagerl) {
        this.imagerl = imagerl;
    }

    public void setBigimagerl(String bigimagerl) {
        this.bigimagerl = bigimagerl;
    }

    public void setTitle(String title) {
        if(title.length()>10){
            title=title.substring(0,9);
        }
        this.title = title;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    public InformationForDetailsAdapter(Context context, List<Comment> commentList,List<Comment> commentBestList) {
        handler = new Handler();
        this.commentList = commentList;
        this.commentBestList=commentBestList;
        this.context = context;
        options = ImageLoaderOptionsControl.getUserHead(context);
    }

    @Override
    public int getCount() {
        if(COMMENTTYPE==1){
            return commentBestList.size();
        }else{
            return  commentList.size();
        }
    }

    @Override
    public Object getItem(int position) {
            if(COMMENTTYPE==1){
                return commentBestList.get(position);
            }else{
                return  commentList.get(position);
            }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setBack(OnClickListenerBack back) {
        this.back = back;
    }

    public void setOpinioncolor(NewsDetials.OpinionsEntity opinioncolor) {
        this.opinioncolor = opinioncolor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentHoder commentHoder = null;
        if (convertView == null) {
            if (back != null) {
                back.commentShow();
            }
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
            commentHoder = new CommentHoder();
            commentHoder.headimage = (ImageView) convertView.findViewById(R.id.item_comment_head_image);
            commentHoder.option = (TextView) convertView.findViewById(R.id.item_comment_option);
            commentHoder.floor = (TextView) convertView.findViewById(R.id.item_comment_floor);
            commentHoder.time = (TextView) convertView.findViewById(R.id.item_comment_time);
            commentHoder.content = (TextView) convertView.findViewById(R.id.item_comment_content);
            commentHoder.zhannum = (TextView) convertView.findViewById(R.id.item_comment_zhannum);
            commentHoder.name = (TextView) convertView.findViewById(R.id.item_comment_name);
            commentHoder.zhan = (ImageView) convertView.findViewById(R.id.item_comment_zhichi);
            commentHoder.share = (ImageView) convertView.findViewById(R.id.item_comment_share);
            commentHoder.jubao = (ImageView) convertView.findViewById(R.id.item_comment_jubao);
            commentHoder.correlation = (ImageView) convertView.findViewById(R.id.item_comment_correlation);
            commentHoder.commentbestc = (ImageView) convertView.findViewById(R.id.comment_bestc);
            convertView.setTag(commentHoder);
        } else {
            commentHoder = (CommentHoder) convertView.getTag();
        }
        final Comment commentEntity;
        if(COMMENTTYPE==1){
            commentEntity= commentBestList.get(position);
        }else{
            commentEntity=  commentList.get(position);
        }

        System.out.println("评论"+commentEntity);

        synchronized (new Object()) {
//            imageLoader.loadImage(commentEntity.getImageurl(), options);
            imageLoader.displayImage(commentEntity.getImageurl(),commentHoder.headimage,options);
        }

        if (opinioncolor != null) {
            if ("A".equals(commentEntity.getOpt_id())) {
                commentHoder.option.setBackgroundColor(Color.parseColor(opinioncolor.getA().getOpt_color()));
            } else if ("B".equals(commentEntity.getOpt_id())) {
                commentHoder.option.setBackgroundColor(Color.parseColor(opinioncolor.getB().getOpt_color()));
            } else if ("C".equals(commentEntity.getOpt_id())) {
                commentHoder.option.setBackgroundColor(Color.parseColor(opinioncolor.getC().getOpt_color()));
            }
        }


        commentHoder.option.setText(commentEntity.getOpt_id() + "观点");
        if(COMMENTTYPE==0) {
            commentHoder.floor.setText(position + 1 + "楼");
        }
        commentHoder.time.setText(commentEntity.getDatetime());
        String to_username = commentEntity.getTo_username();
        if (TextUtils.isEmpty(to_username)) {
            commentHoder.content.setText(commentEntity.getContent());
        } else {
            commentHoder.content.setText(TextViewUtil.getColorText("@"+to_username,"#2C94D4").append(""+commentEntity.getContent()));
        }
        commentHoder.zhannum.setText(commentEntity.getLikes() + "");
        String username = commentEntity.getUsername();
        if(TextUtils.isEmpty(username)){
            commentEntity.setUsername("匿名用户");
            username="匿名用户";
        }
        commentHoder.name.setText(username);
                if (commentEntity.getIs_z() != 0) {
                    commentHoder.zhan.setImageResource(R.mipmap.in_like);
                }else{
                    commentHoder.zhan.setImageResource(R.mipmap.in_press_like);
                }
        commentHoder.commentbestc.setVisibility(commentEntity.getBestc()==0?View.GONE:View.VISIBLE);

        if (back != null) {
            commentHoder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!AppContext.getInstance().getPersonageOnLine()){
                        ((InformationForDetails)context).goTOLogin(6);
                    }else {
                        share(commentEntity);
                    }
                    back.shareComment(commentEntity.getCid());
                }
            });
            commentHoder.jubao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    back.jubao(commentEntity.getCid());
                    Toast.makeText(context, "你的举报信息已发至后台", Toast.LENGTH_SHORT).show();
                }
            });
            commentHoder.zhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toLike(commentEntity);

                }
            });
            commentHoder.correlation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    back.correlation(commentEntity.getUsername(), commentEntity.getUid(), commentEntity.getCid());
                }
            });
        }

        return convertView;
    }

    private void toLike(final Comment commentEntity) {
        if(commentEntity.getIs_z()==0){
            if(!AppContext.getInstance().getPersonageOnLine()){
                ((InformationForDetails)context).goTOLogin(5);
            }else {
                commentEntity.setIs_z(1);
                commentEntity.setLikes(commentEntity.getLikes() + 1);
                upzan = false;
                notifyDataSetChanged();
                back.like(commentEntity.getCid());
            }
        }else{
            Toast.makeText(context, "这条评论已经赞过了", Toast.LENGTH_SHORT).show();
        }
    }

    private void share(Comment commentEntity) {
        String content = title;
        if (content.length() > 128) {
            content = content.substring(0, 127);
        }
        ShareControl shareControl=new ShareControl(context);
        shareControl.setBack(new ShareControl.shareonCompleteBack() {
            @Override
            public void onComplete() {
                ((InformationForDetails)context).sharecommOK();
            }
        });
        OnekeyShare onekeyShare = shareControl.getOnekeyShare(content, commentEntity.getUsername()+"评论："+commentEntity.getContent(), bmp, commentEntity.getRelay_url(),bigimagerl,imagerl, null);
        onekeyShare.show(context);
/*        if (bmp == null) {
            Toast.makeText(context, "正在获取数据当中请稍后", Toast.LENGTH_SHORT).show();
        }*/
/*        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = commentEntity.getRelay_url();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        String content = title+commentEntity.getUsername()+"评论："+commentEntity.getContent();
        if (content.length() > 128) {
            content = content.substring(0, 127);
        }
        msg.title = content;
        msg.setThumbImage(bmp);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = 1;
        initWeixin();
        api.sendReq(req);
        Toast.makeText(context, "即将跳到微信", Toast.LENGTH_LONG).show();*/
    }

    private void initWeixin() {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(context, Constants.APP_ID);
        }
        api.registerApp(Constants.APP_ID);
    }


    class CommentHoder {
        ImageView headimage;
        TextView option;
        TextView floor;
        TextView time;
        TextView content;
        TextView zhannum;
        TextView name;
        ImageView zhan;
        ImageView share;
        ImageView jubao;
        ImageView correlation;
        ImageView commentbestc;

    }

    public interface OnClickListenerBack {

        /**
         * 点赞回调方法
         * @param cid
         */
        abstract void like(int cid);

        /**
         * “@”回调方法
         * @param likeUid 被“@”的用户的uid
         * @param cid     当前评论ID
         */
        abstract void correlation(String name, int likeUid, int cid);

        /**
         * 举报回调方法
         * @param cid 被举报评论的ID
         */
        abstract void jubao(int cid);

        abstract void commentShow();

        abstract void shareComment(int cid);
    }

    @Override
    public void notifyDataSetChanged() {
        if (upzan) {
            upzan=false;
            int size = commentList.size();
            DBManager dbManager = DBManager.getDBManager(context);
            for (int i = 0; i < size; i++) {
                Cursor query = dbManager.query(true, Constants.DB_USER_NEWS_COMMENT, null, "cid=?", new String[]{commentList.get(i).getCid() + ""}, null, null);
                if (query.getCount() > 0) {
                    if (query.moveToFirst()) {
                        int is_z = query.getInt(query.getColumnIndex("is_z"));
                        System.out.println(is_z);
                        commentList.get(i).setIs_z(is_z);
                    }
                }
            }
        }else{
            upzan=true;
        }
        super.notifyDataSetChanged();


    }
    public void setCommentType(int type){
        this.COMMENTTYPE=type;
        notifyDataSetChanged();
    }

}
