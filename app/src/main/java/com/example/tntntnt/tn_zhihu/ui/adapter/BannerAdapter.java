package com.example.tntntnt.tn_zhihu.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tntntnt.tn_zhihu.R;
import com.example.tntntnt.tn_zhihu.bean.BeanBanner;
import com.sivin.ImageViewWithTitle;

import java.util.List;

/**
 * Created by tntnt on 2017/2/17.
 */

public class BannerAdapter extends com.sivin.BannerAdapter<BeanBanner> {

    private Context mContext;

    public BannerAdapter(List<BeanBanner> datas, Context context) {
        super(datas);
        mContext = context;


        Log.d(">banner", "create me");
    }

    @Override
    protected void bindTips(TextView tv, BeanBanner beanBanner) {

    }

    @Override
    public void bindImage(ImageViewWithTitle imageViewWithTitle, BeanBanner beanBanner) {

        imageViewWithTitle.setTextView(beanBanner.getTitle());
        imageViewWithTitle.setTextSize(22);
        imageViewWithTitle.setTextViewColor(Color.WHITE);
        imageViewWithTitle.setTextPadding(40, 0, 40, 80);
        //imageViewWithTitle.setPadding(40, 0, 40, 80);

        Glide.with(mContext)
                .load(beanBanner.getImage())
                .placeholder(R.drawable.img_empty)
                .error(R.drawable.img_broken)
                .into(imageViewWithTitle.getImageView());


        Log.d(">banner", beanBanner.getTitle());
        Log.d(">bannerimg", beanBanner.getImage());
    }
}
