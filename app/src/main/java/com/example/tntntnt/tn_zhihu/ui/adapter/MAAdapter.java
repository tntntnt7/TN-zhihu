package com.example.tntntnt.tn_zhihu.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tntntnt.tn_zhihu.R;
import com.example.tntntnt.tn_zhihu.api.RecyclerMA;
import com.example.tntntnt.tn_zhihu.bean.BeanBanner;
import com.example.tntntnt.tn_zhihu.bean.BeanMAItemA;
import com.example.tntntnt.tn_zhihu.bean.BeanMAItemB;
import com.example.tntntnt.tn_zhihu.ui.activity.MainActivity;
import com.example.tntntnt.tn_zhihu.ui.activity.MainActivity2;
import com.example.tntntnt.tn_zhihu.util.AllAboutDate;
import com.sivin.Banner;

import java.util.List;

/**
 * Created by tntnt on 2017/2/17.
 */

public class MAAdapter extends RecyclerView.Adapter<MAAdapter.MAHolder> {

    public final String STORY_URL = "http://daily.zhihu.com/story/";

    public Context mContext;


    private List<RecyclerMA> list;
    public List<BeanBanner> listB;

    public MAAdapter(List<RecyclerMA> list, Context context, List<BeanBanner> listB){
        this.list = list;
        mContext = context;
        this.listB = listB;

    }

    @Override
    public MAHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);
                return new MAHolder(view, 0);
            case BeanMAItemA.viewType:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_1, parent, false);
                return new MAHolder(view);
            case BeanMAItemB.viewType:
//                if (listB == null){
//                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_one_day_fragment, parent, false);
//                    return new MAHolder(view);
//                }
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_2, parent, false);
                return new MAHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(MAHolder holder, int position) {
        if (position == 0){
            holder.onBindHead(0);
        } else {
            holder.onBind(list.get(position - 1), position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position){
        if (position == 0){
            return 0;
        } else {
            return (list.get(position - 1)).getViewType();
        }
    }


    /**
     * ViewHolder
     */
    class MAHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RecyclerMA recyclerMA;

        //banner
        private Banner banner;

        //item_1
        private TextView title;

        //item_2
        private TextView mTitle;
        private ImageView mImageView;

        public MAHolder(View itemView, int i){
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.id_banner);
        }

        public MAHolder(View itemView) {
            super(itemView);
            switch (itemView.getId()){
                case R.id.item_1_textView:
                    title = (TextView)itemView.findViewById(R.id.item_1_textView);
                case R.id.item_2:
                    mTitle = (TextView)itemView.findViewById(R.id.item_2_textView);
                    mImageView = (ImageView)itemView.findViewById(R.id.item_2_imageView);
                default:
            }

            itemView.setOnClickListener(this);
        }

        public void onBindHead(Object object){
            if ((int)object == 0 && listB != null){
                banner.setBannerAdapter(new BannerAdapter(listB, mContext));
                banner.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        goNews(STORY_URL + listB.get(position).getId());
                    }
                });
                banner.notifiDataHasChanged();
            }
        }

        public void onBind(Object object, int position){
            if (object instanceof RecyclerMA){
                recyclerMA = (RecyclerMA) object;
                if (recyclerMA instanceof BeanMAItemA){
                    if (position == 1){
                        title.setText("今日热闻");
                    } else {
                        String dateTitle = ((BeanMAItemA) recyclerMA).getTitle();
                        title.setText(AllAboutDate.getWeek(dateTitle));
                    }
                } else if (recyclerMA instanceof BeanMAItemB) {
                    mTitle.setText(((BeanMAItemB) recyclerMA).getTitle());
                    Glide.with(mContext)
                            .load(((BeanMAItemB) recyclerMA).getImgUri())
                            .placeholder(R.drawable.img_empty)
                            .error(R.drawable.img_broken)
                            .into(mImageView);
                }
            }
        }

        @Override
        public void onClick(View v) {
            /**
             * 只针对cardView点击
             */
            if (v.getId() == R.id.item_2){
                if (recyclerMA instanceof BeanMAItemB){
                    MainActivity2.newInstance(mContext, STORY_URL + ((BeanMAItemB) recyclerMA).getId(), 0);
                }
            }
        }

        public void goNews(String url){
            Log.d("GO", "news");
            MainActivity2.newInstance(mContext, url, 0);
        }
    }
}
