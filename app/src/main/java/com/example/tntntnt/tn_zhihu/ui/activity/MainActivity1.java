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
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.tntntnt.tn_zhihu.R;
import com.example.tntntnt.tn_zhihu.api.RecyclerMA;
import com.example.tntntnt.tn_zhihu.bean.BeanBanner;
import com.example.tntntnt.tn_zhihu.ui.fragment.MainFragment;
import com.example.tntntnt.tn_zhihu.ui.view.MyFloatingActionButton;
import com.example.tntntnt.tn_zhihu.util.NetConnectionState;

import java.util.ArrayList;
import java.util.List;

/**
 * 代替MainActivity
 */
public class MainActivity1 extends AppCompatActivity {

    public static final String LIST_RMA = "listRMA";
    public static final String LIST_BANNER = "listBanner";

    /**ma*/
    private Toolbar mToolbar;
    private MenuItem mMode;
    private DrawerLayout mDrawer;

    List<RecyclerMA> listRMA = new ArrayList<>();
    List<BeanBanner> listBanner = new ArrayList<>();

    protected Fragment createFragment(List<RecyclerMA> listRMA, List<BeanBanner> listBanner) {
        //return  new MainFragment();
        return new MainFragment(listRMA, listBanner);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity_main);

        Bundle list = getIntent().getExtras();
        if (list != null){
            listRMA = (List<RecyclerMA>) list.getSerializable(LIST_RMA);
            listBanner = (List<BeanBanner>) list.getSerializable(LIST_BANNER);
            Log.d("sa_ma1", "listRMA.size() = " + listRMA.size() + "\nlistBanner.size() = " + listBanner.size());
        } else {
            Log.d("sa_ma1", "list is null");
        }


        initView();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null){
            fragment = createFragment(listRMA, listBanner);
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
