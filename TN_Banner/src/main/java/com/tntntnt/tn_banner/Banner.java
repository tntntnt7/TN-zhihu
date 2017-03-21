package com.tntntnt.tn_banner;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by tntnt on 2017/3/20.
 */

public class Banner extends RelativeLayout {

    private ViewPager mViewPager;
    private BannerIndicator mIndicator;

    /**
     * bean list的大小
     */
    private int mListSize;
    private BannerAdapter mBannerAdapter;

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        mViewPager = new ViewPager(getContext());
        mIndicator = new BannerIndicator(getContext());
        mIndicator.setRadius(10);

        LayoutParams lp_vp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LayoutParams lp_idc = new LayoutParams(150, 70);
        lp_idc.addRule(CENTER_HORIZONTAL, TRUE);
        lp_idc.addRule(ALIGN_PARENT_BOTTOM, TRUE);

        addView(mViewPager, lp_vp);
        addView(mIndicator, lp_idc);
    }


    /**
     * 设置adapter
     * @param bannerAdapter
     */
    public void setBannerAdapter(BannerAdapter bannerAdapter){
        mBannerAdapter = bannerAdapter;
        mListSize = mBannerAdapter.getListSize();
        bindIndicator();
        initData();

        mViewPager.setAdapter(mBannerAdapter);

        nowPosition = 0;
        startTick();
    }

    /**
     * 关联indicator
     */
    public void bindIndicator(){
        mIndicator.setTabNum(mListSize);
        mIndicator.setViewPager(mViewPager, 0);
    }

    /**
     * 初始化数据
     */
    public void initData(){
        for (int i = 0; i < mListSize; i++) {
            mBannerAdapter.bindT(i);
        }
    }


    /**
     * 自动轮播
     */
    public void startTick(){
        handler.post(runnable);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                nowPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public int nowPosition;
    public long tickTime = 5000;
    public Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (nowPosition < mListSize){
                mViewPager.setCurrentItem(nowPosition);
                nowPosition++;
                handler.postDelayed(runnable, tickTime);
            } else {
                nowPosition = 0;
                handler.post(runnable);
            }
        }
    };

    /**
     * 设置轮播间隔时间
     * @param t
     */
    public void setTickTime(long t){
        tickTime = t;
    }
}
