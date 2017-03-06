package com.example.tntntnt.tn_zhihu.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by tntnt on 2017/3/6.
 */

public class ThemeState {
    private static final String DAY_NIGHT = "day_night";

    private boolean dayNight;

    public static void changeState(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putBoolean(DAY_NIGHT, !readState(context));
        editor.commit();
    }

    public static boolean readState(Context context){
        SharedPreferences pref = context.getSharedPreferences("data", MODE_PRIVATE);
        boolean state = pref.getBoolean(DAY_NIGHT, false);
        return state;
    }
}
