package com.example.memeshare;

public class SavedMemes {
    private String imageURL;
    private int id;

    public SavedMemes(String imageURL) {
        this.imageURL = imageURL;
    }

    public SavedMemes(String imageURL, int id) {
        this.imageURL = imageURL;
        this.id = id;
    }
    public SavedMemes(){}

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
