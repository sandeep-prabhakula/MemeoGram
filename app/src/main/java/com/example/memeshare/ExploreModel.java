package com.example.memeshare;

public class ExploreModel {
    private final String imageURL;
    private final String description;

    public ExploreModel(String imageURL,String description) {
        this.imageURL = imageURL;
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getDescription() {
        return description;
    }
}
