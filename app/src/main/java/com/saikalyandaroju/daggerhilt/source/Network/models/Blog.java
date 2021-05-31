package com.saikalyandaroju.daggerhilt.source.Network.models;

import androidx.annotation.NonNull;

public class Blog {
    private int pk;
    private String title,body,image,category;

    public Blog(int id, String title, String body, String category, String image) {
        this.pk = id;
        this.title = title;
        this.body = body;
        this.category = category;
        this.image=image;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return pk;
    }

    public void setId(int id) {
        this.pk = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
