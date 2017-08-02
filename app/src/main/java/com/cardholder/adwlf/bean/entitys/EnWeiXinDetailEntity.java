package com.cardholder.adwlf.bean.entitys;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/6/8.
 */
public class EnWeiXinDetailEntity {

    /**
     * title : 妈妈要吃火锅，女票要吃小龙虾怎么办？在线等挺急的
     * like_num : 109
     * read_num : 14122
     * time : 1464872398
     * subtitle : 文末有福利，一定要戳到最后！
     * url : http://mp.weixin.qq.com/s?timestamp=1464951295&src=3&ver=1&signature=hbZqOkbfz2fxpyxKruDFocJba66H5WKpKdUyjZQCnav0d-Bd2iBn0sjvjuPY*4wTC0aNY8qrLxDwR-U4*nngTIlrp6WjA-71CrYtFoH4xwS2xtPsfiyZtthUCkv2FVNDkRtdpVfNMEB1B1NEpS1Yl8LbkA*BSclDz9UNBkmWzO4=
     */

    private String title;
    private String like_num;
    private String read_num;
    private long time;
    private String subtitle;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLike_num() {
        return like_num;
    }

    public void setLike_num(String like_num) {
        this.like_num = like_num;
    }

    public String getRead_num() {
        return read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
