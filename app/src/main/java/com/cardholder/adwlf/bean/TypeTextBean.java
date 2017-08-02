package com.cardholder.adwlf.bean;

import java.util.List;

/**
 * 说明：
 * note：
 * Created by FuPei
 * on 2015/12/9 at 16:58
 */
public class TypeTextBean {

    /**
     * id : 123
     * type : text
     * content : 我是一个文本信息
     * style : [{"type":1,"title":"加粗","value":"123"},{"type":2,"title":"颜色","value":"#fefefe"},{"type":3,"title":"字体","value":16},{"type":4,"title":"对齐","value":"left"}]
     */

    private String id;
    private String type;
    private String content;
    /**
     * type : 1
     * title : 加粗
     * value : 123
     */

    private List<StyleEntity> style;

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStyle(List<StyleEntity> style) {
        this.style = style;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public List<StyleEntity> getStyle() {
        return style;
    }

    public static class StyleEntity {
        private int type;
        private String title;
        private String value;

        public void setType(int type) {
            this.type = type;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getType() {
            return type;
        }

        public String getTitle() {
            return title;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "type:" + type + ", title:" + title + ", value:" +value;
        }
    }
}
