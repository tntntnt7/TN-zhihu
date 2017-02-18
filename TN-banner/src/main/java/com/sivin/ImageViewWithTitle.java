package com.sivin;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sivin.com.banner.R;


/**
 * Created by tntnt on 2017/2/15.
 */

public class ImageViewWithTitle extends RelativeLayout {

    private static final int RMP = LayoutParams.MATCH_PARENT;
    private static final int RWC = LayoutParams.WRAP_CONTENT;

    ImageView imageView;
    TextView textView;
    //这个view用于将imageView的下半部分添加阴影效果以便textView显示更加清晰
    View view;

    int textViewColor;
    float textSize;
    String textContent;

    public ImageViewWithTitle(Context context) {
        super(context);
        initView(context);
    }

    public ImageViewWithTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
        initView(context);
    }

    public ImageViewWithTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
        initView(context);
    }

    /**
     * 没有用处
     * @param attr
     */
    public void initAttr(AttributeSet attr){
        TypedArray t = getContext().obtainStyledAttributes(attr, R.styleable.iwt);

        textViewColor = t.getColor(R.styleable.iwt_tTextColor, 100);
        textSize = t.getDimension(R.styleable.iwt_tTextSize, 20);
        textContent = t.getString(R.styleable.iwt_tTextContent);

        t.recycle();
    }

    public void initView(Context context){

        imageView = new ImageView(context);

        addView(imageView, new LayoutParams(RMP, RMP));


        textView = new TextView(context);
        textView.setTextSize(textSize);
        textView.setTextColor(textViewColor);
        textView.setText(textContent);
        textView.setPadding(0, 0, 0, 50);
        textView.setGravity(Gravity.CENTER);



        view = new View(context);
        view.setBackgroundResource(R.drawable.shadow);
        LayoutParams viewLp = new LayoutParams(RMP, RMP);
        addView(view, viewLp);


        LayoutParams textViewLp = new LayoutParams(RMP, RWC);
        textViewLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, TRUE);
        textViewLp.addRule(RelativeLayout.CENTER_HORIZONTAL, TRUE);
        addView(textView, textViewLp);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView.setImageDrawable(imageView.getDrawable());
    }

    public void setImage(Bitmap bitmap){
        this.imageView.setImageBitmap(bitmap);
    }

    public void setImage(int r){
        this.imageView.setImageResource(r);
    }

    public void setTextView(String s) {
        textView.setText(s);
    }

    public String getTextContent(){
        return textView.getText().toString();
    }

    /**
     * 设置title颜色
     * @param color
     */
    public void setTextViewColor(int color){
        textView.setTextColor(color);
    }

    /**
     * title字体大小
     * @param size
     */
    public void setTextSize(float size){
        textView.setTextSize(size);
    }

    /**
     * title边距
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setTextPadding(int left, int top, int right, int bottom){
        textView.setPadding(left, top, right, bottom);
    }

}
