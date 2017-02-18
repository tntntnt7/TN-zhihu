package com.example.tntntnt.tn_zhihu.ui.activity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tntntnt.tn_zhihu.R;
import com.example.tntntnt.tn_zhihu.api.RecyclerMA;
import com.example.tntntnt.tn_zhihu.bean.BeanBanner;
import com.example.tntntnt.tn_zhihu.bean.BeanMAItemA;
import com.example.tntntnt.tn_zhihu.bean.BeanMAItemB;
import com.example.tntntnt.tn_zhihu.net.AboutNet;
import com.example.tntntnt.tn_zhihu.ui.adapter.MAAdapter;
import com.example.tntntnt.tn_zhihu.util.NetConnectionState;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**ma*/
    private Toolbar mToolbar;
    private MenuItem mMode;
    private DrawerLayout mDrawer;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mMARecycler;
    private MAAdapter maAdapter;

    /**data*/
    private List<RecyclerMA> mListRMA = new ArrayList<>();
    private List<BeanBanner> mListBanner = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity_main);

        initData();
        initView();


    }

    String jsonString = null;
    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Gson gson = new Gson();

                    jsonString = AboutNet.getJSONString("http://news-at.zhihu.com/api/4/news/latest");
                    JSONObject jsonObject = new JSONObject(jsonString);
                    /**日期*/
                    String date = jsonObject.getString("date");
                    Log.d(">>>", date);
                    mListRMA.add(new BeanMAItemA(date));

                    /**当日stories*/
                    JSONArray jsonArrayStories = jsonObject.getJSONArray("stories");
                    String stories = jsonArrayStories.toString();
                    Log.d("stories", stories);


                    for (int i = 0; i < jsonArrayStories.length(); i++){
                        String story = jsonArrayStories.get(i).toString();
                        mListRMA.add(gson.fromJson(story, BeanMAItemB.class));
                        Log.d("story", story);
                        JSONObject oneStroy = jsonArrayStories.getJSONObject(i);
                        JSONArray img = oneStroy.getJSONArray("images");
                        String imge = img.getString(0);
                        Log.d("IMAGEURL>>", imge);
                        ((BeanMAItemB)mListRMA.get(i + 1)).setImgUri(imge);
                    }


                    Log.d(">>>", "" + mListRMA.size());
                    Log.d(">>>", "" + jsonArrayStories.length());

                    /**当日top_stories*/
                    JSONArray jsonArrayTopStories = jsonObject.getJSONArray("top_stories");
                    String topStories = jsonArrayTopStories.toString();
                    Log.d("topStories", topStories);
                    for (int i = 0; i < jsonArrayTopStories.length(); i++){
                        String topStory = jsonArrayTopStories.get(i).toString();
                        mListBanner.add(gson.fromJson(topStory, BeanBanner.class));
                        Log.d("BeanBanner", ((BeanBanner)(gson.fromJson(topStory, BeanBanner.class))).toString());
                    }

                    Log.d(">>>", "" + mListBanner.size());


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /**MARecycler*/
                            mMARecycler = (RecyclerView)findViewById(R.id.ma_recycle_view);
                            mMARecycler.setLayoutManager(new LinearLayoutManager(getApplication()));

                            MAAdapter maAdapter = new MAAdapter(mListRMA, getApplicationContext(), mListBanner);

                            mMARecycler.setAdapter(maAdapter);

                              maAdapter.notifyDataSetChanged();
                        }
                    });

                } catch (IOException e){
                    e.fillInStackTrace();
                } catch (JSONException e) {
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
                            //getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            recreate();
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            //getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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
        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //触出发下拉刷新时会运行这里
                if (NetConnectionState.isConnected(MainActivity.this)){
                    //TODO 刷新数据
                    //mMARecycler.setAdapter(new MAAdapter(mListRMA, MainActivity.this));
                    initData();
                    mSwipeRefresh.setRefreshing(false);
                } else {
                    mSwipeRefresh.setRefreshing(false);
                    //TODO 改用snakeBar
                    Toast.makeText(MainActivity.this, "未连接网络", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        /**MARecycler*/
//        mMARecycler = (RecyclerView)findViewById(R.id.ma_recycle_view);
//        mMARecycler.setLayoutManager(new LinearLayoutManager(this));
//
//        maAdapter = new MAAdapter(mListRMA, this, mListBanner);
//
//        mMARecycler.setAdapter(maAdapter);

    }

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
}
