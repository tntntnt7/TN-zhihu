package com.example.tntntnt.tn_zhihu.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.tntntnt.tn_zhihu.R;
import com.example.tntntnt.tn_zhihu.ui.activity.MainActivity2;

/**
 * Created by tntnt on 2017/2/22.
 */

public class StoryFragment extends Fragment {

    private Toolbar mToolbar;
    private WebView mWebView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static String mStoryUrl;

    MainActivity2 activity2;

    public static StoryFragment newInstance(String storyURL){
        mStoryUrl = storyURL;
        return new StoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        activity2 = (MainActivity2)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_main_2_story_fragment, container, false);

        initView(view);

        return view;
    }

    public void initView(View view){
        mToolbar = (Toolbar)view.findViewById(R.id.ma_2_toolbar);
        mToolbar.setTitle(R.string.ma_2_toolbar_title);
        Log.d("fragment", "storyFragment成立啦！");


        activity2.setSupportActionBar(mToolbar);
        activity2.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //左边箭头（似乎这个位置叫做navigation），直接finish
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("m2", "get2");
                getActivity().finish();
            }
        });
        //mToolbar.inflateMenu(R.menu.menu_main_activity_2);无效果
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_2_share:
                        Intent shareI = new Intent();
                        shareI.setAction(Intent.ACTION_SEND);
                        shareI.putExtra(Intent.EXTRA_TEXT, mStoryUrl);
                        shareI.setType("text/plain");
                        getActivity().startActivity(Intent.createChooser(shareI, "分享到"));
                        break;
                    case R.id.menu_2_open_in_chrome:
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(mStoryUrl));
                        i = Intent.createChooser(i, "通过其它应用打开：");
                        startActivity(i);
                        break;
                    case R.id.menu_2_copy_uri:
                        ClipboardManager cm = (ClipboardManager)(getActivity().getSystemService(Context.CLIPBOARD_SERVICE));
                        // 将文本放到系统剪贴板里
                        cm.setPrimaryClip(ClipData.newPlainText(null, mStoryUrl));
                        Toast.makeText(getActivity(), "已复制链接", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        return true;
                }
                return false;
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.ma_2_swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.TN_colorPrimary));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.loadUrl(mStoryUrl);
            }
        });

        mWebView = (WebView)view.findViewById(R.id.web_view);
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

//    无效果
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        //加载menu文件到布局
//        inflater.inflate(R.menu.menu_main_activity_2, menu);
//        activity2.getMenuInflater().inflate(R.menu.menu_main_activity_2, menu);
//    }
}
