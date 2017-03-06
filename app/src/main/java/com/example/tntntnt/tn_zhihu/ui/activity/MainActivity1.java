package com.example.tntntnt.tn_zhihu.ui.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
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
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.example.tntntnt.tn_zhihu.util.ThemeState;

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

    List<RecyclerMA> listRMA = new ArrayList<>();
    List<BeanBanner> listBanner = new ArrayList<>();

    /**drawer*/
    private DrawerLayout mDrawer;
    private View drawerHeader;

    protected Fragment createFragment(List<RecyclerMA> listRMA, List<BeanBanner> listBanner) {
        return new MainFragment(listRMA, listBanner);
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
    public void onResume(){
        super.onResume();

        if (ThemeState.readState(this)) {
            mMode.setTitle(R.string.ma_menu_change_mode_day);
        } else {
            mMode.setTitle(R.string.ma_menu_change_mode_night);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ThemeState.readState(this)){
            setTheme(R.style.TN_Theme_NIGHT);
        } else {
            setTheme(R.style.TN_Theme_DAY);
        }
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
                        switchTheme();
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
        drawerHeader = findViewById(R.id.drawer_header);
    }

    private void switchTheme() {
        /**
         * 这里setTheme能改变主题
         * 但是只有新刷新出来的view才能应用新主题
         */
        if (!ThemeState.readState(this)){
            setTheme(R.style.TN_Theme_NIGHT);
            mMode.setTitle(R.string.ma_menu_change_mode_day);
        } else {
            setTheme(R.style.TN_Theme_DAY);
            mMode.setTitle(R.string.ma_menu_change_mode_night);
        }

        refreshUI();

        /**
         * MainActivity2若是被创建，依靠ThemeState的判断其主题与MainActivity1的相同
         */
        ThemeState.changeState(this);
    }

    /**
     * 改变当前存在的View的颜色（设置与上面设置的新主题相同），
     * 而未出现的view(recycler的待刷新出来的item)则已经随从上面设置的新主题
     */
    private void refreshUI() {
        Resources.Theme theme = getTheme();
        TypedValue clockBackground = new TypedValue();
        TypedValue clockTextColor = new TypedValue();
        TypedValue clockTextColor_item_1 = new TypedValue();
        TypedValue cardViewBackgroundColor = new TypedValue();
        TypedValue colorPrimary = new TypedValue();
        TypedValue colorAccent = new TypedValue();
        TypedValue colorPrimaryDark = new TypedValue();

        theme.resolveAttribute(R.attr.clockBackground, clockBackground, true);
        theme.resolveAttribute(R.attr.clockTextColor, clockTextColor, true);
        theme.resolveAttribute(R.attr.clockTextColor_item_1, clockTextColor_item_1, true);
        theme.resolveAttribute(R.attr.cardViewBackgroundColor, cardViewBackgroundColor, true);
        theme.resolveAttribute(R.attr.colorPrimary, colorPrimary, true);
        theme.resolveAttribute(R.attr.colorAccent, colorAccent, true);
        theme.resolveAttribute(R.attr.colorPrimaryDark, colorPrimaryDark, true);

        //修改drawerHeader颜色
        drawerHeader.setBackgroundColor(colorPrimary.data);

        //修改toolBar颜色
        mToolbar.setBackgroundColor(colorPrimary.data);
        //修改状态栏颜色
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(colorPrimaryDark.data);

        //修改recycler背景颜色
        MainFragment.setRecyclerBackgroundColoor(clockBackground.data);
        //修改item颜色
        MainFragment.setItemBackgroundColor(
                clockTextColor_item_1.data,
                cardViewBackgroundColor.data,
                clockTextColor.data);
        //修改三个fb颜色
        MainFragment.setMyFBColor(colorAccent.data);
    }
}
