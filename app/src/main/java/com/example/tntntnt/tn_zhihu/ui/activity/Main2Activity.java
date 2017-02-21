package com.example.tntntnt.tn_zhihu.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tntntnt.tn_zhihu.R;

import java.net.URI;

public class Main2Activity extends AppCompatActivity {

    public static final String KEY_STORY_URL = "story_url";

    private Toolbar mToolbar;
    private WebView mWebView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String mStoryUrl;


    public static void newInstance(Context context, String url){
        Intent intent = new Intent(context, Main2Activity.class);
        intent.putExtra(KEY_STORY_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mStoryUrl = getIntent().getStringExtra(KEY_STORY_URL);

        initView();
    }

    public void initView(){
        mToolbar = (Toolbar)findViewById(R.id.ma_2_toolbar);
        mToolbar.setTitle(R.string.ma_2_toolbar_title);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //左边箭头（似乎这个位置叫做navigation），直接finish
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("m2", "get2");
                finish();
            }
        });
        //mToolbar.inflateMenu(R.menu.menu_main_activity_2);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_2_share:
                        Intent shareI = new Intent();
                        shareI.setAction(Intent.ACTION_SEND);
                        shareI.putExtra(Intent.EXTRA_TEXT, mStoryUrl);
                        shareI.setType("text/plain");
                        Main2Activity.this.startActivity(Intent.createChooser(shareI, "分享到"));
                        break;
                    case R.id.menu_2_open_in_chrome:
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(mStoryUrl));
                        i = Intent.createChooser(i, "通过其它应用打开：");
                        startActivity(i);
                        break;
                    case R.id.menu_2_copy_uri:
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        // 将文本放到系统剪贴板里
                        cm.setPrimaryClip(ClipData.newPlainText(null, mStoryUrl));
                        Toast.makeText(Main2Activity.this, "已复制链接", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        return true;
                }
                return false;
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.ma_2_swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.TN_colorPrimary));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.loadUrl(mStoryUrl);
            }
        });

        mWebView = (WebView)findViewById(R.id.web_view);
        mWebView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView webView, int newProgress){
                if (newProgress == 100){
                    mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            }
        });
        //启用javaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                return false;
            }
        });
        mWebView.loadUrl(mStoryUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载menu文件到布局
        getMenuInflater().inflate(R.menu.menu_main_activity_2, menu);
        return true;
    }
}
