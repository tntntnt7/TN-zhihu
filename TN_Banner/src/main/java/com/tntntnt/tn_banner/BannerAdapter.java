package com.tntntnt.tn_banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tntnt on 2017/3/20.
 */

public abstract class BannerAdapter<T> extends PagerAdapter {
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }


    /**
     * bean列表
     */
    private List<T> mList;

    /**
     * 根据mList的大小生成相同数量的ImageViewWithTitle
     */
    private List<ImageViewWithTitle> viewList = new ArrayList<>();


    public BannerAdapter(List<T> list, Context context){
        mList = list;
        for (int i = 0; i < list.size(); i++) {
            viewList.add(new ImageViewWithTitle(context));
        }
    }

    /**
     * 返回mList，供Banner类使用
     * @return
     */
    public int getListSize(){
        return mList.size();
    }

    /**
     * 绑定数据，供Banner类使用
     * @param position
     */
    public void bindT(final int position){
        bind(viewList.get(position), mList.get(position));
//        itemClick(viewList.get(position), mList.get(position));
        viewList.get(position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setItemClick(mList.get(position));
            }
        });
    }

    /**
     * 抽象方法，根据需要，自定义方法
     * @param imageViewWithTitle
     * @param t
     */
    public abstract void bind(ImageViewWithTitle imageViewWithTitle, T t);

   // public abstract void itemClick(ImageViewWithTitle imageViewWithTitle, T t);

    public abstract void setItemClick(T t);
}
