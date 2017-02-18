package com.example.tntntnt.tn_zhihu.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tntntnt.tn_zhihu.R;
import com.example.tntntnt.tn_zhihu.api.RecyclerMA;
import com.example.tntntnt.tn_zhihu.bean.BeanBanner;
import com.example.tntntnt.tn_zhihu.bean.BeanMAItemA;
import com.example.tntntnt.tn_zhihu.bean.BeanMAItemB;
import com.sivin.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tntnt on 2017/2/17.
 */

public class MAAdapter extends RecyclerView.Adapter<MAAdapter.MAHolder> {

    public Context mContext;

    public List<Object> objectList = new ArrayList<>();

    private List<RecyclerMA> list;
    public List<BeanBanner> listB;

    public MAAdapter(List<RecyclerMA> list, Context context, List<BeanBanner> listB){
        this.list = list;
        mContext = context;
        this.listB = listB;

        objectList.add(0);//占位，随便添加一个元素到objectList
        for (int i = 0; i < list.size(); i++){
            objectList.add(list.get(i));
        }
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_2, parent, false);
                return new MAHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(MAHolder holder, int position) {
        if (position == 0){
            holder.onBindHead(objectList.get(position));
        } else {
            holder.onBind(objectList.get(position));
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

            //return ((RecyclerMA)(objectList.get(2))).getViewType();
        } else {
            return ((RecyclerMA)(objectList.get(position))).getViewType();
        }
//        if (position > 0){
//            return ((RecyclerMA)(objectList.get(position))).getViewType();
//        } else {
//            return 2;
//        }
    }


    /**
     *
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
//                case R.id.id_banner:
//                    banner = (Banner)itemView.findViewById(R.id.id_banner);
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
            if ((int)object == 0){
                banner.setBannerAdapter(new BannerAdapter(listB, mContext));
                banner.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        goNews();
                    }
                });
                banner.notifiDataHasChanged();
            }
        }

        public void onBind(Object object){
            if (object instanceof RecyclerMA){
                recyclerMA = (RecyclerMA) object;
                if (recyclerMA instanceof BeanMAItemA){
                    title.setText(((BeanMAItemA) recyclerMA).getTitle());
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
                goNews();
            }
        }

        public void goNews(){
            //TODO 进入新闻详情页
            Log.d("GO", "news");
        }
    }
}
