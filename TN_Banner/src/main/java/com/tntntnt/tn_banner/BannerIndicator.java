package com.tntntnt.tn_banner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by tntnt on 2017/3/19.
 */

public class BannerIndicator extends LinearLayout {

    private Paint mPaintLight;
    private Paint mPaintNormal;
    private float xOffset;

    private int tabNum;


    private float radius = 15;

    private ViewPager mViewPager;

    @Override
    protected void onDraw(Canvas canvas){
        canvas.save();

        initIndicator(canvas);

        //绘制指示器圆点
        canvas.translate(xOffset, 0);
        canvas.drawCircle(getWidth() / tabNum / 2, getHeight() / 2, radius, mPaintLight);

        canvas.restore();
    }

    /**
     * 初始化圆点
     * @param canvas
     */
    public void initIndicator(Canvas canvas){
        for (int i = 0; i < tabNum; i++) {
            canvas.drawCircle(getWidth() / tabNum / 2, getHeight() / 2, radius, mPaintNormal);
            canvas.translate(getWidth() / tabNum, 0);
        }
        canvas.translate(-getWidth() / tabNum * tabNum, 0);
    }

    public BannerIndicator(Context context) {
        this(context, null);
    }

    public BannerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        this.setOrientation(HORIZONTAL);

        //如不设置则无效，若要改变背景色必须在代码中设置，在xml中设置无效
        this.setBackgroundColor(Color.TRANSPARENT);

        mPaintLight = new Paint();
        mPaintLight.setColor(Color.WHITE);
        mPaintLight.setAntiAlias(true);
        mPaintLight.setStyle(Paint.Style.FILL);

        mPaintNormal = new Paint();
        mPaintNormal.setColor(Color.GRAY);
        mPaintNormal.setAntiAlias(true);
        mPaintNormal.setStyle(Paint.Style.FILL);
    }

    /**
     * indicator的滑动指示
     * @param position
     * @param positionOffset
     */
    public void scroll(int position, float positionOffset) {
        xOffset = position * getWidth() / tabNum + positionOffset * getWidth() / tabNum;
        invalidate();//重绘
    }

    /**
     * 设置指定的viewPager
     * @param viewPager
     */
    public void setViewPager(ViewPager viewPager, int position){
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setCurrentItem(position);
    }

    /**
     * 圆点半径
     * @param radius
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }


    public void setTabNum(int num){
        tabNum = num;
    }
}