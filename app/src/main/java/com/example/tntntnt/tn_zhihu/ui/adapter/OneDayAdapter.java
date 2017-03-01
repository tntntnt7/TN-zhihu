package com.example.tntntnt.tn_zhihu.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tntntnt.tn_zhihu.R;
import com.example.tntntnt.tn_zhihu.api.RecyclerMA;
import com.example.tntntnt.tn_zhihu.bean.BeanMAItemB;
import com.example.tntntnt.tn_zhihu.ui.activity.MainActivity2;

import java.util.List;

/**
 * Created by tntnt on 2017/3/1.
 */

public class OneDayAdapter extends RecyclerView.Adapter<OneDayAdapter.OneDayHolder> {

    public final String STORY_URL = "http://daily.zhihu.com/story/";

    public Context mContext;

    private List<RecyclerMA> list;

    public OneDayAdapter(List<RecyclerMA> list, Context context){
        this.list = list;
        mContext = context;

    }

    @Override
    public OneDayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_2, parent, false);
        return new OneDayHolder(view);
    }

    @Override
    public void onBindViewHolder(OneDayHolder holder, int position) {
        RecyclerMA recyclerMA = list.get(position);
        holder.onBind(recyclerMA);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * ViewHolder
     */
    class OneDayHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RecyclerMA recyclerMA;

        //item_2
        private TextView mTitle;
        private ImageView mImageView;


        public OneDayHolder(View itemView) {
            super(itemView);
            mTitle = (TextView)itemView.findViewById(R.id.item_2_textView);
            mImageView = (ImageView)itemView.findViewById(R.id.item_2_imageView);

            itemView.setOnClickListener(this);
        }

        public void onBind(Object object){
            if (object instanceof RecyclerMA){
                recyclerMA = (RecyclerMA) object;
                if (recyclerMA instanceof BeanMAItemB) {
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
    }
}