package com.example.tntntnt.tn_zhihu.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tntntnt.tn_zhihu.R;
import com.example.tntntnt.tn_zhihu.api.RecyclerMA;
import com.example.tntntnt.tn_zhihu.bean.BeanBanner;
import com.example.tntntnt.tn_zhihu.bean.BeanMAItemA;
import com.example.tntntnt.tn_zhihu.net.AboutJson;
import com.example.tntntnt.tn_zhihu.ui.activity.MainActivity1;
import com.example.tntntnt.tn_zhihu.ui.activity.MainActivity2;
import com.example.tntntnt.tn_zhihu.ui.adapter.MAAdapter;
import com.example.tntntnt.tn_zhihu.ui.adapter.OneDayAdapter;
import com.example.tntntnt.tn_zhihu.util.AllAboutDate;
import com.example.tntntnt.tn_zhihu.util.ListAddList;
import com.example.tntntnt.tn_zhihu.util.NetConnectionState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tntnt on 2017/2/22.
 */

public class OneDayFragment extends Fragment {

    private MainActivity2 mainActivity2;

    private static String mDateString;
    private List<RecyclerMA> mList;
    private OneDayAdapter mAdapter;

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static OneDayFragment newInstance(String dateString){
        mDateString = dateString;
        return new OneDayFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Log.d("onedayF", "onCreate");

        mainActivity2 = (MainActivity2)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_main_one_day_fragment, container, false);

        Log.d("onedayF", "onCreateView");

        initView(view);

        if (NetConnectionState.isConnected(mainActivity2)){
            initData();
        } else {
            Toast.makeText(mainActivity2, "未连接网络", Toast.LENGTH_SHORT).show();}

        return view;
    }

    private void initView(View view){
        mToolbar = (Toolbar)view.findViewById(R.id.ma_one_day_toolbar);
        mainActivity2.setSupportActionBar(mToolbar);
        mainActivity2.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //左边箭头（似乎这个位置叫做navigation），直接finish
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.clear();
                getActivity().finish();
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.ma_one_day_swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });

        mRecyclerView = (RecyclerView)view.findViewById(R.id.ma_one_day_recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mainActivity2));


        Log.d("onedayF", "initView");
    }

    private void initData() {

        Log.d("onedayF", "initData");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int dayInt = Integer.parseInt(mDateString);
                    Log.d("DDDAT", "" + dayInt);

                    final BeanMAItemA beanMAItemA = AboutJson.decodeBeanMAItemA(dayInt);

                    List<RecyclerMA> itemAList1 = (AboutJson.decodeRecyclerMA(dayInt, 0));
                    mList = itemAList1;
                    Log.d("onedayF", "mList.size" + mList.size());

                    mainActivity2.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mToolbar.setTitle(AllAboutDate.getDateString(beanMAItemA.getTitle()));
                            mAdapter = new OneDayAdapter(mList, mainActivity2);
                            mRecyclerView.setAdapter(mAdapter);


                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
