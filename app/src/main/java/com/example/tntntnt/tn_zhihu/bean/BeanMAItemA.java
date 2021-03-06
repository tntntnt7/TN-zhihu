package com.example.tntntnt.tn_zhihu.bean;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.example.tntntnt.tn_zhihu.api.RecyclerMA;
import com.example.tntntnt.tn_zhihu.api.RecyclerMAD;

/**
 * mainActivity中的recyclerLayout的子项（日期项）
 *
 * Created by tntnt on 2017/2/17.
 */

public class BeanMAItemA implements RecyclerMA {

    public String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BeanMAItemA(String title){
        this.title = title;
    }

    public static final int viewType = 1;

    @Override
    public int getViewType() {
        return viewType;
    }

}
