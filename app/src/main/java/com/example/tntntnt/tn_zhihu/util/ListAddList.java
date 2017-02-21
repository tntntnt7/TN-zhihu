package com.example.tntntnt.tn_zhihu.util;

import com.example.tntntnt.tn_zhihu.api.RecyclerMA;
import com.example.tntntnt.tn_zhihu.bean.BeanBanner;

import java.util.List;

/**
 * Created by tntnt on 2017/2/21.
 */

public class ListAddList {

    public static void add(List<RecyclerMA> listA, List<RecyclerMA> listB, List<RecyclerMA> listC){
        for (RecyclerMA o: listB){
            listA.add(o);
        }
        for (RecyclerMA o: listC){
            listA.add(o);
        }
    }

    public static void add(List<RecyclerMA> listA, List<RecyclerMA> listB){
        for (RecyclerMA o: listB){
            listA.add(o);
        }
    }

    public static void addBanner(List<BeanBanner> listA, List<RecyclerMA> listB){
        for (int i = 0; i < listB.size(); i++){
            listA.add((BeanBanner)listB.get(i));
        }
    }
}
