package com.cardholder.adwlf.bean.entitys;

import java.util.List;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/5/27.
 */
public class EnterPriseInfoEntity {

    /**
     * enterprise_name : 成都网立方互动科技有限公司
     * id_code : 51082000000
     * id_src_back : http://ipx.2016.360netnews.com/Uploads/2016-05-09/573071ed8223b.jpg
     * status : 230
     * organization_code : /Uploads/2016-05-09/573071ed8223b.jpg
     * license_number : 51082000000
     * menu : [{"id":"a8764250-fc74-11e5-8c0d-000c297a","def_style":"rwtg","menu_name":"软文推广","count":"13635","hot_style":"icon-m-rwtgbg","url":"http://ipx.2016.360netnews.com/enterprise/spread/soft.shtml","css_style":"icon-m-rwtg"},{"id":"a876ab78-fc74-11e5-8c0d-000c297d","def_style":"wx","menu_name":"微信","count":"20928","hot_style":"icon-m-wxbg","url":"http://ipx.2016.360netnews.com/enterprise/spread/weixin_public.shtml","css_style":"icon-m-wx"},{"id":"a876ad58-fc74-11e5-8c0d-000c297f","def_style":"wb","menu_name":"新浪微博","count":"14728","hot_style":"icon-m-wbbg","url":"http://ipx.2016.360netnews.com/enterprise/spread/weibo.shtml","css_style":"icon-m-wb"},{"id":"a876ae2a-fc74-11e5-8c0d-000c297g","def_style":"bbs","menu_name":"论坛推广","count":"572","hot_style":"icon-m-bbsbg","url":"http://ipx.2016.360netnews.com/enterprise/spread/bbs.shtml","css_style":"icon-m-bbs"},{"id":"a876a7d6-fc74-11e5-8c0d-000c297b","def_style":"sns","menu_name":"SNS","count":"462","hot_style":"icon-m-snsbg","url":"http://ipx.2016.360netnews.com/enterprise/spread/sns.shtml","css_style":"icon-m-sns"},{"id":"a876ac72-fc74-11e5-8c0d-000c297e","def_style":"blg","menu_name":"博客推广","count":"230","hot_style":"icon-m-blgbg","url":"http://ipx.2016.360netnews.com/enterprise/spread/blog.shtml","css_style":"icon-m-blg"},{"id":"a876aa38-fc74-11e5-8c0d-000c297c","def_style":"wzdx","menu_name":"文章代写","count":"183","hot_style":"icon-m-wzdxbg","url":"http://ipx.2016.360netnews.com/enterprise/writer/choose.shtml","css_style":"icon-m-wzdx"}]
     * license_src : http://ipx.2016.360netnews.com/Uploads/2016-05-09/573071ead771d.jpg
     * avatar :
     * id_src_front : http://ipx.2016.360netnews.com/Uploads/2016-05-09/573071ead771d.jpg
     * enterprise_addr : 四川省成都市府城大道399号天府新谷
     * email :
     * contact_qq :
     * name : 雷开新2
     * qrcode :
     * login_name : leikaixin
     * enterprise_type : 4
     * mobile_no : 15208330231
     */

    private String enterprise_name;
    private String id_code;
    private String id_src_back;
    private String status;
    private String organization_code;
    private String license_number;
    private String license_src;
    private String avatar;
    private String id_src_front;
    private String enterprise_addr;
    private String email;
    private String contact_qq;
    private String name;
    private String qrcode;
    private String login_name;
    private String enterprise_type;
    private String mobile_no;
    /**
     * id : a8764250-fc74-11e5-8c0d-000c297a
     * def_style : rwtg
     * menu_name : 软文推广
     * count : 13635
     * hot_style : icon-m-rwtgbg
     * url : http://ipx.2016.360netnews.com/enterprise/spread/soft.shtml
     * css_style : icon-m-rwtg
     */

    private List<MenuBean> menu;

    public String getEnterprise_name() {
        return enterprise_name;
    }

    public void setEnterprise_name(String enterprise_name) {
        this.enterprise_name = enterprise_name;
    }

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    public String getId_src_back() {
        return id_src_back;
    }

    public void setId_src_back(String id_src_back) {
        this.id_src_back = id_src_back;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrganization_code() {
        return organization_code;
    }

    public void setOrganization_code(String organization_code) {
        this.organization_code = organization_code;
    }

    public String getLicense_number() {
        return license_number;
    }

    public void setLicense_number(String license_number) {
        this.license_number = license_number;
    }

    public String getLicense_src() {
        return license_src;
    }

    public void setLicense_src(String license_src) {
        this.license_src = license_src;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId_src_front() {
        return id_src_front;
    }

    public void setId_src_front(String id_src_front) {
        this.id_src_front = id_src_front;
    }

    public String getEnterprise_addr() {
        return enterprise_addr;
    }

    public void setEnterprise_addr(String enterprise_addr) {
        this.enterprise_addr = enterprise_addr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_qq() {
        return contact_qq;
    }

    public void setContact_qq(String contact_qq) {
        this.contact_qq = contact_qq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getEnterprise_type() {
        return enterprise_type;
    }

    public void setEnterprise_type(String enterprise_type) {
        this.enterprise_type = enterprise_type;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public List<MenuBean> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuBean> menu) {
        this.menu = menu;
    }

    public static class MenuBean {
        private String id;
        private String def_style;
        private String menu_name;
        private String count;
        private String hot_style;
        private String url;
        private String css_style;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDef_style() {
            return def_style;
        }

        public void setDef_style(String def_style) {
            this.def_style = def_style;
        }

        public String getMenu_name() {
            return menu_name;
        }

        public void setMenu_name(String menu_name) {
            this.menu_name = menu_name;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getHot_style() {
            return hot_style;
        }

        public void setHot_style(String hot_style) {
            this.hot_style = hot_style;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCss_style() {
            return css_style;
        }

        public void setCss_style(String css_style) {
            this.css_style = css_style;
        }
    }
}
