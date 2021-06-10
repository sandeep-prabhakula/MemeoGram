package com.example.memeshare;

public class MemeModel {
     private String imgurl;
     private String description;

    public MemeModel(String imgurl,String description) {
        this.imgurl = imgurl;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getImgurl() {
        return imgurl;
    }
}
