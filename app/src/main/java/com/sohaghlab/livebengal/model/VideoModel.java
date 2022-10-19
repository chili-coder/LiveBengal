package com.sohaghlab.livebengal.model;

public class VideoModel {
    String title,image,url,key;

    public VideoModel() {
    }

    public VideoModel(String title, String image, String url, String key) {
        this.title = title;
        this.image = image;
        this.url = url;
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
