package com.example.tntntnt.tn_zhihu.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by tntnt on 2017/2/23.
 */

public class Anim {

    public static void hide(final View view, long d){
        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;
        float radius = view.getWidth();

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, radius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });
        anim.setDuration(d).start();
    }

    public static void show(final View view, long d){
        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;
        float radius = view.getWidth();

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, radius);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.VISIBLE);
            }
        });
        anim.setDuration(d).start();
    }
}
