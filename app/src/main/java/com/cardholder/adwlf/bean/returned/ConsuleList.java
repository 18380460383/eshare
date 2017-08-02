package com.cardholder.adwlf.bean.returned;

import java.util.List;

/**
 * Created by Administrator on 2016/2/18.
 */
public class ConsuleList {

    /**
     * id : 11
     * type : 2
     * title : 王思聪与雪梨现身西双版纳 疑带网红女友见家长
     * images : ["http://192.168.0.111:9012/Uploads/Picture/2016-02-18/56c520cfaed7f.jpg"]
     * url : http://192.168.0.111:9012/api.php/App/GetNewsContent/id/11/uid/93364
     */

    private List<BannerEntity> banner;
    /**
     * id : 5
     * type : 0
     * title : 31省份两会全召开 9省份GDP增速目标设为具体区间
     * description : 9省份GDP增速目标设为具体区间值
     * hits : 2
     * images : ["http://192.168.0.111:9012/Uploads/Picture/2016-02-18/56c51b4c3cbb4.jpg"]
     * datetime : 02-18 13:49
     */

    private List<ListEntity> list;

    public void setBanner(List<BannerEntity> banner) {
        this.banner = banner;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public List<BannerEntity> getBanner() {
        return banner;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public static class BannerEntity {
        private String id;
        private String type;
        private String title;
        private String url;
        private String images;

        public void setId(String id) {
            this.id = id;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public String getImages() {
            return images;
        }
    }

    public static class ListEntity {
        private String id;
        private int type;
        private String title;
        private String description;
        private String hits;
        private String datetime;
        private List<String> images;
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setHits(String hits) {
            this.hits = hits;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public String getId() {
            return id;
        }

        public int getType() {
            return type;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getHits() {
            return hits;
        }

        public String getDatetime() {
            return datetime;
        }

        public List<String> getImages() {
            return images;
        }
    }
}
