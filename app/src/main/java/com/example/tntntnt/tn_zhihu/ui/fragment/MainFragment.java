package com.example.tntntnt.tn_zhihu.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.tntntnt.tn_zhihu.R;
import com.example.tntntnt.tn_zhihu.api.RecyclerMA;
import com.example.tntntnt.tn_zhihu.bean.BeanBanner;
import com.example.tntntnt.tn_zhihu.bean.BeanMAItemA;
import com.example.tntntnt.tn_zhihu.net.AboutJson;
import com.example.tntntnt.tn_zhihu.ui.activity.MainActivity;
import com.example.tntntnt.tn_zhihu.ui.activity.MainActivity1;
import com.example.tntntnt.tn_zhihu.ui.activity.MainActivity2;
import com.example.tntntnt.tn_zhihu.ui.adapter.MAAdapter;
import com.example.tntntnt.tn_zhihu.ui.view.MyFloatingActionButton;
import com.example.tntntnt.tn_zhihu.util.Anim;
import com.example.tntntnt.tn_zhihu.util.ListAddList;
import com.example.tntntnt.tn_zhihu.util.NetConnectionState;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by tntnt on 2017/2/23.
 */

@SuppressLint("ValidFragment")
public class MainFragment extends Fragment {

    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private MainActivity1 mainActivity1;


    /**ma*/
//    private Toolbar mToolbar;
//    private MenuItem mMode;
//    private DrawerLayout mDrawer;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mMARecycler;
    private MAAdapter maAdapter;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity1);

    private MyFloatingActionButton mMyFB;
    private FloatingActionButton fbHome;
    private FloatingActionButton fbLeft;
    private FloatingActionButton fbTop;

    private DatePicker mDatePicker;

    /**data*/
    private List<RecyclerMA> mListRMA = new ArrayList<>();
    private List<BeanBanner> mListBanner = new ArrayList<>();
    private int dayByDay;

    @SuppressLint("ValidFragment")
    public MainFragment(List<RecyclerMA> listRMA, List<BeanBanner> listBanner){
        mListRMA = listRMA;
        mListBanner = listBanner;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mainActivity1 = (MainActivity1)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_main_1_main_fragment, container, false);


        initView(view);


        if (NetConnectionState.isConnected(mainActivity1)){
            if (mListRMA.size() != 0 && mListBanner.size() != 0){
                Log.d("sa_mf", "得到传过来的数据");
                Log.d("sa_mf", "mListRAM.size = " + mListRMA.size());
                Log.d("sa_mf", "mListBanner.size = " + mListBanner.size());
                maAdapter = new MAAdapter(mListRMA, mainActivity1, mListBanner);
                mMARecycler.setAdapter(maAdapter);
            } else {
                Log.d("sa_mf", "我要重新加载");
                mSwipeRefresh.setRefreshing(true);
                initData(0);
            }
        } else {
            Toast.makeText(mainActivity1, "未连接网络", Toast.LENGTH_SHORT).show();}

        initRecyclerListener();

        return view;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        mMARecycler.setLayoutManager(null);

        Log.d("DES", "onDestroyView1");
//        if (Util.isOnMainThread()){
//            if (Glide.isSetup()){
//                Glide.with(mainActivity1).pauseRequests();
//            }
//        }

        Log.d("DES", "onDestroyView2");
    }


    private void initRecyclerListener() {

        mMARecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int mScrollThreshold;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                /**
                 * 滑动到底部的监听
                 */
                if (mMARecycler.computeVerticalScrollExtent() + mMARecycler.computeVerticalScrollOffset()
                        == mMARecycler.computeVerticalScrollRange()){
                    Log.d("mMARecycler", "滑动到底部");
                    if (NetConnectionState.isConnected(mainActivity1)){
                        initData(1);
                    } else {
                        Toast.makeText(mainActivity1, "未连接网络", Toast.LENGTH_SHORT).show();
                    }
                }

                boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
                if (isSignificantDelta) {
                    if (dy > 30) {
                        if (mMyFB.getVisibility() == View.VISIBLE){
                            Anim.hide(mMyFB, 500);
                        }
                    } else if (dy < 0){
                        if (mMyFB.getVisibility() == View.INVISIBLE){
                            Anim.show(mMyFB, 500);
                        }
                    }
                }
            }

            public void setScrollThreshold(int scrollThreshold){
                mScrollThreshold = scrollThreshold;
            }
        });

        //TODO 添加监听器修改toolbar标题
    }

    private void initData(final int i) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    switch (i){
                        case 0:
                            /**
                             * 用fake_mListRMA和fake_mListBanner分别替代mListRMA和mListBanner
                             * 相比于直接使用mListRMA和mListBanner，
                             * 既避免了两者.clear()之后recyclerView滑动崩溃的尴尬，
                             * 又解决了直接用mListRMA在以下这段代码中出现的迷之bug（mListView总会莫名其妙多出来一项（BeanMAItemA））
                             */
                            final List<RecyclerMA> fake_mListRMA = new ArrayList<>();
                            final List<BeanBanner> fake_mListBanner = new ArrayList<>();

                            BeanMAItemA beanMAItemA = AboutJson.decodeBeanMAItemA(0);
                            fake_mListRMA.add(beanMAItemA);
                            if (mListRMA.size() == 2){
                                mListRMA.remove(1);
                            }
                            dayByDay = new Integer(beanMAItemA.getTitle());

                            List<RecyclerMA> itemAList = (AboutJson.decodeRecyclerMA(0, 0));
                            ListAddList.add(fake_mListRMA, itemAList);

                            List<RecyclerMA> bannerList = (AboutJson.decodeRecyclerMA(0, 1));
                            ListAddList.addBanner(fake_mListBanner, bannerList);

                            mainActivity1.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mListRMA = fake_mListRMA;
                                    mListBanner = fake_mListBanner;
                                    maAdapter = new MAAdapter(mListRMA, mainActivity1, mListBanner);
                                    mMARecycler.setAdapter(maAdapter);

                                    mSwipeRefresh.setRefreshing(false);
                                }
                            });
                            break;
                        case 1:
                            BeanMAItemA beanMAItemA1 = AboutJson.decodeBeanMAItemA(dayByDay);
                            mListRMA.add(beanMAItemA1);

                            List<RecyclerMA> itemAList1 = (AboutJson.decodeRecyclerMA(dayByDay, 0));
                            ListAddList.add(mListRMA, itemAList1);

                            dayByDay = new Integer(beanMAItemA1.getTitle());

                            mainActivity1.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    maAdapter.notifyDataSetChanged();
                                    mMARecycler.smoothScrollBy(0, 140);
                                }
                            });
                            break;
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initView(View view) {

        /**swipeRefresh部分*/
        mSwipeRefresh = (SwipeRefreshLayout)view.findViewById(R.id.ma_1_fragment_swipe_refresh);
        mSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.TN_colorPrimary));
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //触出发下拉刷新时会运行这里
                if (NetConnectionState.isConnected(mainActivity1)){
                    //之前没有用fake_mListRMA和fake_mListBanner时的解决方法
                    //mListRMA.clear();
                    //mListBanner.clear();
                    //maAdapter.notifyDataSetChanged();//体验差，刷新的时候只能等，屏幕一片空白
                    initData(0);
                } else {
                    mSwipeRefresh.setRefreshing(false);
                    //TODO 改用snakeBar
                    Toast.makeText(mainActivity1, "未连接网络", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**MARecycler*/
        mMARecycler = (RecyclerView)view.findViewById(R.id.ma_1_fragment_recycle_view);
        mMARecycler.setLayoutManager(linearLayoutManager);

        /**mMyFB*/
        mMyFB = (MyFloatingActionButton)view.findViewById(R.id.ma_1_fragment_my_fb);
        mMyFB.setHomeClick();
        fbHome = mMyFB.getFbHome();
        fbTop = mMyFB.getFbTop();
        fbLeft = mMyFB.getFbLeft();

        fbHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyFB.setHomeClick();
                Log.d("FBH_mf", "点到了");
            }
        });
        fbTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyFB.setHomeClick();
                linearLayoutManager.smoothScrollToPosition(mMARecycler, null, 0);
            }
        });
        fbLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyFB.setHomeClick();

                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.setTargetFragment(MainFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_DATE){
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            int year = calendar.get(Calendar.YEAR);
            //Calendar.MONTH从0到11分别代表从一月到十二月，故+1
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            String monthString = (month < 10) ? "0" + month : "" + month;
            String dayString = (day < 10)? "0" + day : "" + day;
            String dateString = "" + year + monthString + dayString;


            Log.d("oneday", dateString);
            MainActivity2.newInstance(mainActivity1, dateString, 1);
        }
    }
}
