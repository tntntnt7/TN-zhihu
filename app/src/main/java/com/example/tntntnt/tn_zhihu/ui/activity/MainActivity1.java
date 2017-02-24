package com.example.tntntnt.tn_zhihu.ui.activity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.tntntnt.tn_zhihu.R;
import com.example.tntntnt.tn_zhihu.ui.fragment.MainFragment;
import com.example.tntntnt.tn_zhihu.ui.view.MyFloatingActionButton;
import com.example.tntntnt.tn_zhihu.util.NetConnectionState;

/**
 * 代替MainActivity
 */
public class MainActivity1 extends AppCompatActivity {

    /**ma*/
    private Toolbar mToolbar;
    private MenuItem mMode;
    private DrawerLayout mDrawer;

    protected Fragment createFragment() {
        return new MainFragment();
    }

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

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (Util.isOnBackgroundThread()){
            Glide.with(this).pauseRequests();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity_main);

        initView();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null){
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    private void initView() {
        /**mToolbar部分*/
        mToolbar = (Toolbar)findViewById(R.id.ma_1_fragment_toolbar);
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
    }
}
