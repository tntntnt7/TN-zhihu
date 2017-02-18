package com.example.tntntnt.tn_zhihu.net;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tntnt on 2017/2/17.
 */

public class AboutNet {

    private static OkHttpClient client = new OkHttpClient();
    private static Response response;
    private static Request request;
    String s = null;
    private static String s2 = null;


    public static String getJSONString(String url) throws IOException {
        request = new Request.Builder().url(url).build();
        try {
            response = client.newCall(request).execute();
            s2 = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return s2;
        }
    }

    public String get(final Activity activity, final TextView textView, String url){
        request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                s = response.body().string();
                Log.d(">>>", s);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(s);
                    }
                });
            }
        });

        return s;
    }
}
