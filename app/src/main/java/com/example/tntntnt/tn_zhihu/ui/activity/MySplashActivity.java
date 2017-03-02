package com.example.tntntnt.tn_zhihu.ui.activity;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.tntntnt.tn_zhihu.R;
import com.example.tntntnt.tn_zhihu.api.RecyclerMA;
import com.example.tntntnt.tn_zhihu.bean.BeanBanner;
import com.example.tntntnt.tn_zhihu.bean.BeanMAItemA;
import com.example.tntntnt.tn_zhihu.net.AboutJson;
import com.example.tntntnt.tn_zhihu.ui.adapter.MAAdapter;
import com.example.tntntnt.tn_zhihu.util.ListAddList;
import com.example.tntntnt.tn_zhihu.util.NetConnectionState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MySplashActivity extends AppCompatActivity {

    private static final int SHOW_TIME_MIN = 1000;// 最小显示时间
    private long mStartTime;// 开始时间
    private long mRunningTime1;//正在运行的时间
    private long mRunningTime2;
    private long mRunningTime3;


    private List<RecyclerMA> mListRMA = new ArrayList<>();
    private List<BeanBanner> mListBanner = new ArrayList<>();

    private Intent intent;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setNavigationBarColor(Color.BLACK);
//        getWindow().setStatusBarColor(Color.BLACK);
        setContentView(R.layout.activity_my_splash);
        mStartTime = System.currentTimeMillis();

        imageAnim();

        intent = new Intent(this, MainActivity1.class);

        if (NetConnectionState.isConnected(this)){
            initData();
        } else {
            go();
        }
    }

    private void imageAnim() {
        mImageView = (ImageView)findViewById(R.id.sa_img);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1, 0.8f, 1,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(SHOW_TIME_MIN);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);

        mImageView.startAnimation(animationSet);
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<RecyclerMA> fake_mListRMA = new ArrayList<>();
                    final List<BeanBanner> fake_mListBanner = new ArrayList<>();

                    BeanMAItemA beanMAItemA = AboutJson.decodeBeanMAItemA(0);
                    fake_mListRMA.add(beanMAItemA);
                    mRunningTime1 = System.currentTimeMillis();
                    Log.d("sag", "rt1 " + (mRunningTime1 - mStartTime));
                    // 每一次网络请求都检查时间，要是大于SHOW_TIME_MIN则直接结束initDate()，
                    // 忽略之后的网络请求，直接执行go()
                    // (前提是每次网络请求都成功，不管花了多少时间)
                    // TODO 所以有没有一种办法，计算initData()的执行时间，若超过指定时间则终止方法，直接go()
                    if (checkTime(mStartTime, mRunningTime1)){
                        go();
                        return;
                    }

                    List<RecyclerMA> itemAList = (AboutJson.decodeRecyclerMA(0, 0));
                    ListAddList.add(fake_mListRMA, itemAList);
                    mRunningTime2 = System.currentTimeMillis();
                    Log.d("sag", "rt2 " + (mRunningTime2 - mRunningTime1));
                    if (checkTime(mRunningTime1, mRunningTime2)){
                        go();
                        return;
                    }

                    List<RecyclerMA> bannerList = (AboutJson.decodeRecyclerMA(0, 1));
                    ListAddList.addBanner(fake_mListBanner, bannerList);
                    mRunningTime3 = System.currentTimeMillis();
                    Log.d("sag", "rt3 " + (mRunningTime3 - mRunningTime2));
                    //若是已经执行到这里，那就不管bannerList网络请求的这一步花了多少时间，
                    //继续执行下去

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mListRMA = fake_mListRMA;
                            mListBanner = fake_mListBanner;
                            intent.putExtra(MainActivity1.LIST_RMA, (Serializable) mListRMA);
                            intent.putExtra(MainActivity1.LIST_BANNER, (Serializable) mListBanner);
                            go();
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private boolean checkTime(long sTime, long eTime){
        if (eTime - sTime > SHOW_TIME_MIN){
            return true;
        }
        return false;
    }

    private void go() {
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
