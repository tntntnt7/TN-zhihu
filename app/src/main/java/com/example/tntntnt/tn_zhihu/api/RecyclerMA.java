package com.example.tntntnt.tn_zhihu.api;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * mainActivity中的recyclerLayout子项接口
 *
 * Created by tntnt on 2017/2/17.
 */

public interface RecyclerMA extends Parcelable {
    abstract int getViewType();
}
