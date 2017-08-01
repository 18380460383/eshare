package com.jiebian.adwlf.bean;

import com.jiebian.adwlf.utils.EnDataUtils;

/**
 * 创建者：Administrator
 * 时间：2016/6/30
 * 功能描述：
 */
public class EnInviteShareBean {


    /**
     * picUrl : http://192.168.0.163:9013/./Public/images/company/91/shareqrcode.png
     * picTitle : 成功邀请一个好友赠送100优惠券
     * weixin : {"title":"创新营销 跨界变革 !","des":"一个专注于互联网营销者学习、分享、交流互动的平台！","litpic":"http://api.jiebiannews.com/Public/images/Icon120.png","share":"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1beb00cf4c98140e&redirect_uri=http://api.jiebiannews.com/weixin/reg_netnews.php?uid=#XEXUID#&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect","replaceUid":"uid=#XEXUID#"}
     * qq : {"title":"创新营销 跨界变革 !","des":"一个专注于互联网营销者学习、分享、交流互动的平台！","litpic":"http://api.jiebiannews.com/Public/images/Icon120.png","share":"http://login.360netnews.com/index.php/index/register.html?uid=#XEXUID#&aregister=615","replaceUid":"uid=#XEXUID#"}
     * weibo : {"title":"创新营销 跨界变革 !","des":"一个专注于互联网营销者学习、分享、交流互动的平台！","litpic":"http://api.jiebiannews.com/Public/images/Icon120.png","share":"http://login.360netnews.com/index.php/index/register.html?uid=#XEXUID#&aregister=615","replaceUid":"uid=#XEXUID#"}
     * sms : {"title":"创新营销 跨界变革 !","des":"一个专注于互联网营销者学习、分享、交流互动的平台！","litpic":"http://api.jiebiannews.com/Public/images/Icon120.png","share":"http://login.360netnews.com/index.php/index/register.html?uid=#XEXUID#&aregister=615","replaceUid":"uid=#XEXUID#"}
     */

    private String picUrl;
    private String picTitle;
    /**
     * title : 创新营销 跨界变革 !
     * des : 一个专注于互联网营销者学习、分享、交流互动的平台！
     * litpic : http://api.jiebiannews.com/Public/images/Icon120.png
     * share : https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1beb00cf4c98140e&redirect_uri=http://api.jiebiannews.com/weixin/reg_netnews.php?uid=#XEXUID#&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect
     * replaceUid : uid=#XEXUID#
     */

    private WeixinEntity weixin;
    /**
     * title : 创新营销 跨界变革 !
     * des : 一个专注于互联网营销者学习、分享、交流互动的平台！
     * litpic : http://api.jiebiannews.com/Public/images/Icon120.png
     * share : http://login.360netnews.com/index.php/index/register.html?uid=#XEXUID#&aregister=615
     * replaceUid : uid=#XEXUID#
     */

    private QqEntity qq;
    /**
     * title : 创新营销 跨界变革 !
     * des : 一个专注于互联网营销者学习、分享、交流互动的平台！
     * litpic : http://api.jiebiannews.com/Public/images/Icon120.png
     * share : http://login.360netnews.com/index.php/index/register.html?uid=#XEXUID#&aregister=615
     * replaceUid : uid=#XEXUID#
     */

    private WeiboEntity weibo;
    /**
     * title : 创新营销 跨界变革 !
     * des : 一个专注于互联网营销者学习、分享、交流互动的平台！
     * litpic : http://api.jiebiannews.com/Public/images/Icon120.png
     * share : http://login.360netnews.com/index.php/index/register.html?uid=#XEXUID#&aregister=615
     * replaceUid : uid=#XEXUID#
     */

    private SmsEntity sms;

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setPicTitle(String picTitle) {
        this.picTitle = picTitle;
    }

    public void setWeixin(WeixinEntity weixin) {
        this.weixin = weixin;
    }

    public void setQq(QqEntity qq) {
        this.qq = qq;
    }

    public void setWeibo(WeiboEntity weibo) {
        this.weibo = weibo;
    }

    public void setSms(SmsEntity sms) {
        this.sms = sms;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getPicTitle() {
        return picTitle;
    }

    public WeixinEntity getWeixin() {
        return weixin;
    }

    public QqEntity getQq() {
        return qq;
    }

    public WeiboEntity getWeibo() {
        return weibo;
    }

    public SmsEntity getSms() {
        return sms;
    }

    public static class WeixinEntity {
        private String title;
        private String des;
        private String litpic;
        private String share;
        private String replaceUid;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public void setLitpic(String litpic) {
            this.litpic = litpic;
        }

        public void setShare(String share) {
            this.share = share;
        }

        public void setReplaceUid(String replaceUid) {
            this.replaceUid = replaceUid;
        }

        public String getTitle() {
            return title;
        }

        public String getDes() {
            return des;
        }

        public String getLitpic() {
            return litpic;
        }

        public String getShare() {
             return share.replace(getReplaceUid(), "uid="+ EnDataUtils.getUID());
        }

        public String getReplaceUid() {
            return replaceUid;
        }
    }

    public static class QqEntity {
        private String title;
        private String des;
        private String litpic;
        private String share;
        private String replaceUid;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public void setLitpic(String litpic) {
            this.litpic = litpic;
        }

        public void setShare(String share) {
            this.share = share;
        }

        public void setReplaceUid(String replaceUid) {
            this.replaceUid = replaceUid;
        }

        public String getTitle() {
            return title;
        }

        public String getDes() {
            return des;
        }

        public String getLitpic() {
            return litpic;
        }

        public String getShare() {
             return share.replace(getReplaceUid(), "uid="+ EnDataUtils.getUID());
        }

        public String getReplaceUid() {
            return replaceUid;
        }
    }

    public static class WeiboEntity {
        private String title;
        private String des;
        private String litpic;
        private String share;
        private String replaceUid;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public void setLitpic(String litpic) {
            this.litpic = litpic;
        }

        public void setShare(String share) {
            this.share = share;
        }

        public void setReplaceUid(String replaceUid) {
            this.replaceUid = replaceUid;
        }

        public String getTitle() {
            return title;
        }

        public String getDes() {
            return des;
        }

        public String getLitpic() {
            return litpic;
        }

        public String getShare() {
             return share.replace(getReplaceUid(), "uid="+ EnDataUtils.getUID());
        }

        public String getReplaceUid() {
            return replaceUid;
        }
    }

    public static class SmsEntity {
        private String title;
        private String des;
        private String litpic;
        private String share;
        private String replaceUid;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public void setLitpic(String litpic) {
            this.litpic = litpic;
        }

        public void setShare(String share) {
            this.share = share;
        }

        public void setReplaceUid(String replaceUid) {
            this.replaceUid = replaceUid;
        }

        public String getTitle() {
            return title;
        }

        public String getDes() {
            return des;
        }

        public String getLitpic() {
            return litpic;
        }

        public String getShare() {
             return share.replace(getReplaceUid(), "uid="+ EnDataUtils.getUID());
        }

        public String getReplaceUid() {
            return replaceUid;
        }

        @Override
        public String toString() {
            return
                    title +
                    des +
                    share;
        }
    }
}
