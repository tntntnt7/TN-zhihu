package com.example.tntntnt.tn_zhihu.bean;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.example.tntntnt.tn_zhihu.api.RecyclerMA;

/**
 * Created by tntnt on 2017/2/17.
 */

public class BeanBanner implements RecyclerMA {

    public String image;
    public String type;
    public String id;
    public String ga_prefix;
    public String title;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString(){
        return "I have image: " + image
                + ",\ntype: " + type
                + ",\nid: " + id
                + ",\nga_prefix: " + ga_prefix
                + ",\ntitle: " + title;
    }


    public static final int viewType = 0;

    @Override
    public int getViewType() {
        return viewType;
    }

}
