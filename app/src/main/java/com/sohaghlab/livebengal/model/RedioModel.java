package com.sohaghlab.livebengal.model;

public class RedioModel {
    String title,image,fmcode,url,key;

    public RedioModel() {
    }

    public RedioModel(String title, String image, String fmcode, String url, String key) {
        this.title = title;
        this.image = image;
        this.fmcode = fmcode;
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

    public String getFmcode() {
        return fmcode;
    }

    public void setFmcode(String fmcode) {
        this.fmcode = fmcode;
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
