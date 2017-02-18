package com.example.tntntnt.tn_zhihu.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

/**
 * Created by tntnt on 2017/2/17.
 */

public class MyImageView extends ImageView {
    public MyImageView(Context context) {
        super(context);
        init();
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
//
//    @Override
//    public void onDraw(Canvas canvas){
//        canvas.drawCircle(getWidth(), getHeight(), getWidth() / 2, new Paint());
//    }

    public void init(){
        //获取Outline
        ViewOutlineProvider viewOutlineProvider =
                new ViewOutlineProvider() {
                    @Override
                    public void getOutline(View view, Outline outline) {
                        //修改outline为特定形状
                        outline.setOval(0, 0,
                                view.getWidth(),
                                view.getHeight());
                    }
                };

        //重新设置形状
        setOutlineProvider(viewOutlineProvider);
    }

}
