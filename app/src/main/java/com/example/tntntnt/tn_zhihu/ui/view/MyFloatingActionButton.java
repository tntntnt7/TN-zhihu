package com.example.tntntnt.tn_zhihu.ui.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.example.tntntnt.tn_zhihu.R;

import static android.support.design.R.styleable.FloatingActionButton;


/**
 * Created by tntnt on 2017/2/22.
 */

public class MyFloatingActionButton extends RelativeLayout {

    private FloatingActionButton fbHome;
    private FloatingActionButton fbLeft;
    private FloatingActionButton fbTop;


    private boolean mFlag;

    public MyFloatingActionButton(Context context) {
        super(context);
        initView(context);
    }

    public MyFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context){
        fbHome = new FloatingActionButton(context);
        fbLeft = new FloatingActionButton(context);
        fbTop = new FloatingActionButton(context);


        LayoutParams homeLp = new LayoutParams(200, 200);
        LayoutParams lp = new LayoutParams(200, 200);

        homeLp.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);

        addView(fbLeft, lp);
        addView(fbTop, lp);
        addView(fbHome, homeLp);

        fbHome.setImageResource(R.drawable.fb_home_open);
        fbTop.setImageResource(R.drawable.fb_top);
        fbLeft.setImageResource(R.drawable.fb_left);
    }

    public void setHomeClick(){
        if (mFlag){
            fbHome.setImageResource(R.drawable.fb_home_close);
            open();
        } else {
            close();
            fbHome.setImageResource(R.drawable.fb_home_open);
        }
    }

    public FloatingActionButton getFbTop() {
        return fbTop;
    }

    public FloatingActionButton getFbLeft() {
        return fbLeft;
    }

    public FloatingActionButton getFbHome() {
        return fbHome;
    }

    private void open(){
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(
                fbHome,
                "alpha",
                1F,
                0.5F
        );
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(
                fbTop,
                "alpha",
                0,
                1F
        );
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(
                fbTop,
                "translationY",
                -200F
        );
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(
                fbLeft,
                "alpha",
                0,
                1F
        );
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(
                fbLeft,
                "translationX",
                -200F
        );

        AnimatorSet set = new AnimatorSet();
        set.setDuration(100);
        set.setInterpolator(new AccelerateInterpolator());
        set.playTogether(animator0,
                animator1,
                animator2,
                animator3,
                animator4);

        set.start();
        mFlag = false;
    }

    private void close(){
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(
                fbHome,
                "alpha",
                0.5F,
                1F
        );
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(
                fbLeft,
                "translationX",
                0
        );
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(
                fbLeft,
                "alpha",
                1F,
                0
        );
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(
                fbTop,
                "translationY",
                0
        );
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(
                fbTop,
                "alpha",
                1F,
                0
        );

        AnimatorSet set = new AnimatorSet();
        set.setDuration(70);
        set.setInterpolator(new DecelerateInterpolator());
        set.playTogether(animator0,
                animator1,
                animator2,
                animator3,
                animator4
        );

        set.start();
        mFlag = true;
    }

}
