package com.example.tntntnt.tn_zhihu.bean;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.example.tntntnt.tn_zhihu.api.RecyclerMA;

import java.util.List;

/**
 * mainActivity中的recyclerLayout的子项（cardView项）
 *
 * Created by tntnt on 2017/2/17.
 */

public class BeanMAItemB implements RecyclerMA {

    //单独赋值
    public String imgUri;

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public String type;
    public String id;
    public String ga_prefix;
    public String title;

//    public static class img{
//        public String s;
//
//        public String getS() {
//            return s;
//        }
//
//        public void setS(String s) {
//            this.s = s;
//        }
//    }


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
        return "I have images: " + imgUri
                + ",\ntype: " + type
                + ",\nid: " + id
                + ",\nga_prefix: " + ga_prefix
                + ",\ntitle: " + title;
    }


    public static final int viewType = 2;

    @Override
    public int getViewType() {
        return viewType;
    }

}
