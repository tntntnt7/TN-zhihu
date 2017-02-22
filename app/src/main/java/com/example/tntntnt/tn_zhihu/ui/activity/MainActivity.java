package com.example.tntntnt.tn_zhihu.ui.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Message;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tntntnt.tn_zhihu.R;
import com.example.tntntnt.tn_zhihu.api.RecyclerMA;
import com.example.tntntnt.tn_zhihu.bean.BeanBanner;
import com.example.tntntnt.tn_zhihu.bean.BeanMAItemA;
import com.example.tntntnt.tn_zhihu.bean.BeanMAItemB;
import com.example.tntntnt.tn_zhihu.net.AboutJson;
import com.example.tntntnt.tn_zhihu.net.AboutNet;
import com.example.tntntnt.tn_zhihu.ui.adapter.MAAdapter;
import com.example.tntntnt.tn_zhihu.ui.view.MyFloatingActionButton;
import com.example.tntntnt.tn_zhihu.util.AllAboutDate;
import com.example.tntntnt.tn_zhihu.util.ListAddList;
import com.example.tntntnt.tn_zhihu.util.NetConnectionState;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**ma*/
    private Toolbar mToolbar;
    private MenuItem mMode;
    private DrawerLayout mDrawer;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mMARecycler;
    private MAAdapter maAdapter;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

    private MyFloatingActionButton mMyFB;
    private FloatingActionButton fbHome;
    private FloatingActionButton fbLeft;
    private FloatingActionButton fbTop;

    private DatePicker mDatePicker;

    /**data*/
    private List<RecyclerMA> mListRMA = new ArrayList<>();
    private List<BeanBanner> mListBanner = new ArrayList<>();
    private int dayByDay;

    /**
     * menu中的theme夜间/白天模式切换的时候设置item的title
     */
    @Override
    public void onResume(){
        super.onResume();

        int uiMode = getResources().getConfiguration().uiMode;
        int currentMode = uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentMode == Configuration.UI_MODE_NIGHT_NO) {
            mMode.setTitle(R.string.ma_menu_change_mode_night);
        } else if (currentMode == Configuration.UI_MODE_NIGHT_YES) {
            mMode.setTitle(R.string.ma_menu_change_mode_day);
        } else {
            mMode.setTitle(R.string.ma_menu_change_mode_night);
        }
    }

    /**
     * 设置返回键
     * 当drawer打开时，按下返回键收回drawer
     */
    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity_main);

        initView();

        if (NetConnectionState.isConnected(this)){
            initData(0);
        } else {
            Toast.makeText(MainActivity.this, "未连接网络", Toast.LENGTH_SHORT).show();}

        initRecyclerListener();
    }


    private void initRecyclerListener() {

        mMARecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if (NetConnectionState.isConnected(getApplicationContext())){
                        initData(1);
                    } else {
                        Toast.makeText(MainActivity.this, "未连接网络", Toast.LENGTH_SHORT).show();
                    }
                }
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

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mListRMA = fake_mListRMA;
                                    mListBanner = fake_mListBanner;
                                    maAdapter = new MAAdapter(mListRMA, MainActivity.this, mListBanner);
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

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    maAdapter.notifyDataSetChanged();
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

    private void initView() {
        /**mToolbar部分*/
        mToolbar = (Toolbar)findViewById(R.id.ma_toolbar);
        mToolbar.setTitle(R.string.ma_toolbar_title);
        mToolbar.setTitleTextColor(Color.WHITE);
        //设置title字体大小
        if (mToolbar.getChildAt(0) instanceof TextView){
            ((TextView) mToolbar.getChildAt(0)).setTextSize(20);
        }
        //设置menu
        mToolbar.inflateMenu(R.menu.menu_main_activity);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_change_mode:
                        //设置theme的夜间模式
                        if (item.getTitle().equals(getResources().getString(R.string.ma_menu_change_mode_night))){
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            recreate();
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            recreate();
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
        mMode = mToolbar.getMenu().getItem(0);

        /**drawer部分*/
        mDrawer = (DrawerLayout)findViewById(R.id.ma_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        /**swipeRefresh部分*/
        mSwipeRefresh = (SwipeRefreshLayout)findViewById(R.id.ma_swipe_refresh);
        mSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.TN_colorPrimary));
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //触出发下拉刷新时会运行这里
                if (NetConnectionState.isConnected(MainActivity.this)){
                    //之前没有用fake_mListRMA和fake_mListBanner时的解决方法
                    //mListRMA.clear();
                    //mListBanner.clear();
                    //maAdapter.notifyDataSetChanged();//体验差，刷新的时候只能等，屏幕一片空白
                    initData(0);
                } else {
                    mSwipeRefresh.setRefreshing(false);
                    //TODO 改用snakeBar
                    Toast.makeText(MainActivity.this, "未连接网络", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**MARecycler*/
        mMARecycler = (RecyclerView)findViewById(R.id.ma_recycle_view);
        mMARecycler.setLayoutManager(linearLayoutManager);


        /**mMyFB*/
        mMyFB = (MyFloatingActionButton)findViewById(R.id.my_fb);
        fbHome = mMyFB.getFbHome();
        fbTop = mMyFB.getFbTop();
        fbLeft = mMyFB.getFbLeft();

        fbHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyFB.setHomeClick();
            }
        });
        fbTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyFB.setHomeClick();
                linearLayoutManager.smoothScrollToPosition(mMARecycler, null, 0);
            }
        });
//        fbLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mMyFB.setHomeClick();
//
//                //Date date = (Date)getArguments().getSerializable(ARG_DATE);
//
//                Calendar calendar = Calendar.getInstance();
//                //calendar.setTime(date);
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH);
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//                View view = LayoutInflater.from(getApplication())
//                        .inflate(R.layout.am_date_picker, null);
//
//                mDatePicker = (DatePicker)v.findViewById(R.id.date_picker);
//               // mDatePicker.init(year, month, day, null);
//
//
//                AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext())
//                        .setView(view)
//                        .setTitle("选择日期")
////                        .setTitle("选择日期：", new DialogInterface.OnClickListener(){
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                                int year = mDatePicker.getYear();
////                                int month = mDatePicker.getMonth();
////                                int day = mDatePicker.getDayOfMonth();
////
////                                String s = "" + year + month + day;
////                            }
////                        })
//                        .setPositiveButton("确定", null)
//                        .create();
//
//                alertDialog.show();
//
//                Log.d("picker", "" + year + month + day);
//            }
//        });
    }
}
