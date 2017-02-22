package com.example.tntntnt.tn_zhihu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.tntntnt.tn_zhihu.R;
import com.example.tntntnt.tn_zhihu.ui.fragment.OneDayFragment;
import com.example.tntntnt.tn_zhihu.ui.fragment.StoryFragment;


/**
 * 用来承担StoryFragment和OneDayFragment
 */
public class MainActivity2 extends SingleFragmentActivity {

    public static final String KEY_STORY_URL = "story_url";
    public static int flag;

    public static void newInstance(Context context, String url, int flag){
        if (flag == 0){
            Intent intent = new Intent(context, MainActivity2.class);
            intent.putExtra(KEY_STORY_URL, url);
            context.startActivity(intent);
            Log.d("fragment", "ma2成立啦！");
        }
    }

    @Override
    protected Fragment createFragment() {
        if (flag == 0){
            String url = getIntent().getStringExtra(KEY_STORY_URL);
            return StoryFragment.newInstance(url);
        } else if (flag == 1){
            //TODO 返回OneDayFragment
            return new OneDayFragment();
        }

        return null;
    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (flag == 0){
            //加载menu文件到布局
            getMenuInflater().inflate(R.menu.menu_main_activity_2, menu);
        } else if (flag == 1){
            //TODO 直接在OneDayFragment内部处理
        }

        return super.onCreateOptionsMenu(menu);
    }
}
