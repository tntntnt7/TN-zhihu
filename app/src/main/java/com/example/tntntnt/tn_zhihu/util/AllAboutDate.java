package com.example.tntntnt.tn_zhihu.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by tntnt on 2017/2/20.
 */

public class AllAboutDate {

    public static String[] WEEK = {
            "星期日",
            "星期一",
            "星期二",
            "星期三",
            "星期四",
            "星期五",
            "星期六",
    };

    public static String getWeek(String jsonDate){
        String answer;

        List<Integer> date = splitDateString(jsonDate);

        int year = date.get(0);
        int month = date.get(1);
        int day = date.get(2);

        String week = go(year, month, day);
        answer = month + "月" + day + "日 " + week;

        return answer;
    }

    public static String getDateString(String jsonDate){
        String answer;

        List<Integer> date = splitDateString(jsonDate);

        int year = date.get(0);
        int month = date.get(1);
        int day = date.get(2);

        answer = year + "年" +  month + "月" + day + "日";

        return answer;
    }


    public static String go(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        return WEEK[dayIndex - 1];
    }



    public static List<Integer> splitDateString(String dateString){
        List<Integer> date = new ArrayList<>();

        int dateNum = new Integer(dateString);

        int year = dateNum / 10000;
        int yl = dateNum - year * 10000;

        int month = yl / 100;
        int day = yl - month * 100;

        date.add(year);
        date.add(month);
        date.add(day);

        return date;
    }
}
