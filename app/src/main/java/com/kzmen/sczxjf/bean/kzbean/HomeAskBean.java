package com.kzmen.sczxjf.bean.kzbean;

/**
 * Created by pjj18 on 2017/9/6.
 */

public class HomeAskBean {

    /**
     * id : 2
     * content : 个人信用有什么作用？
     * isopen : 1
     * views : 0
     * isopen_str : 1元偷偷听
     * answer_media : http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3
     * answer_media_time : 1
     * answer_content :
     * nickname : 手机用户****5398
     * avatar :
     * teacher : 0
     */

    private String id;
    private String content;
    private String isopen;
    private String views;
    private String isopen_str;
    private String answer_media;
    private String answer_media_time;
    private String answer_content;
    private String nickname;
    private String avatar;
    private String teacher;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsopen() {
        return isopen;
    }

    public void setIsopen(String isopen) {
        this.isopen = isopen;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getIsopen_str() {
        return isopen_str;
    }

    public void setIsopen_str(String isopen_str) {
        this.isopen_str = isopen_str;
    }

    public String getAnswer_media() {
        return answer_media;
    }

    public void setAnswer_media(String answer_media) {
        this.answer_media = answer_media;
    }

    public String getAnswer_media_time() {
        return answer_media_time;
    }

    public void setAnswer_media_time(String answer_media_time) {
        this.answer_media_time = answer_media_time;
    }

    public String getAnswer_content() {
        return answer_content;
    }

    public void setAnswer_content(String answer_content) {
        this.answer_content = answer_content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "HomeAskBean{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", isopen='" + isopen + '\'' +
                ", views='" + views + '\'' +
                ", isopen_str='" + isopen_str + '\'' +
                ", answer_media='" + answer_media + '\'' +
                ", answer_media_time='" + answer_media_time + '\'' +
                ", answer_content='" + answer_content + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
