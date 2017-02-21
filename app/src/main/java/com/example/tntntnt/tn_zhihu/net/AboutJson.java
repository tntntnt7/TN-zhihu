package com.example.tntntnt.tn_zhihu.net;

import android.util.Log;

import com.example.tntntnt.tn_zhihu.api.RecyclerMA;
import com.example.tntntnt.tn_zhihu.bean.BeanBanner;
import com.example.tntntnt.tn_zhihu.bean.BeanMAItemA;
import com.example.tntntnt.tn_zhihu.bean.BeanMAItemB;
import com.example.tntntnt.tn_zhihu.ui.adapter.MAAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tntnt on 2017/2/21.
 */

public class AboutJson {
    //private String jsonString = null;
//    private void initData(int i) {
//        switch (i){
//            case 0:
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Gson gson = new Gson();
//
//                            jsonString = AboutNet.getJSONString("http://news-at.zhihu.com/api/4/news/latest");
//                            JSONObject jsonObject = new JSONObject(jsonString);
//                            /**日期*/
//                            String date = jsonObject.getString("date");
//                            //Log.d("Data", "date: " + date);
//                            mListRMA.add(new BeanMAItemA(date));
//                            //初始化今日日期
//                            dayByDay = new Integer(date);
//                            Log.d("DAYBYDAY2", "is " + dayByDay);
//
//                            /**当日stories*/
//                            JSONArray jsonArrayStories = jsonObject.getJSONArray("stories");
//                            String stories = jsonArrayStories.toString();
//                            //Log.d("Data", "stories: " + stories);
//
//                            for (int i = 0; i < jsonArrayStories.length(); i++){
//                                String story = jsonArrayStories.get(i).toString();
//                                //添加除images之外的内容
//                                mListRMA.add(gson.fromJson(story, BeanMAItemB.class));
//                                //Log.d("Data", "story: " + story);
//                                //添加images数组里面的imageUri，虽然images数组里面只有一个imageUri...
//                                JSONObject oneStroy = jsonArrayStories.getJSONObject(i);
//                                JSONArray img = oneStroy.getJSONArray("images");
//                                String imge = img.getString(0);
//                                Log.d("Data", "image: " + imge);
//                                ((BeanMAItemB)mListRMA.get(i + 1)).setImgUri(imge);
//                            }
//
//                            Log.d("List", "mListRMA.size() = " + mListRMA.size());
//                            Log.d("Data", "stories num = " + jsonArrayStories.length());
//
//                            /**当日top_stories*/
//                            JSONArray jsonArrayTopStories = jsonObject.getJSONArray("top_stories");
//                            String topStories = jsonArrayTopStories.toString();
//                            //Log.d("Data", "topStories: " + topStories);
//                            for (int i = 0; i < jsonArrayTopStories.length(); i++){
//                                String topStory = jsonArrayTopStories.get(i).toString();
//                                mListBanner.add(gson.fromJson(topStory, BeanBanner.class));
//                                //Log.d("Data", "BeanBanner: " + (gson.fromJson(topStory, BeanBanner.class)).toString());
//                            }
//                            Log.d("List", "mListBanner.size() = " + mListBanner.size());
//
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    maAdapter = new MAAdapter(mListRMA, getApplicationContext(), mListBanner);
//
//                                    mMARecycler.setAdapter(maAdapter);
//
//                                    maAdapter.notifyDataSetChanged();
//
//                                    mSwipeRefresh.setRefreshing(false);
//                                }
//                            });
//                        } catch (IOException e){
//                            e.printStackTrace();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//                break;
//            case 1:
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Gson gson = new Gson();
//
//                            /**
//                             * before/ + dayByDay 显示dayByDay前一天的内容
//                             */
//                            jsonString = AboutNet.getJSONString("http://news-at.zhihu.com/api/4/news/before/" + dayByDay);
//                            JSONObject jsonObject = new JSONObject(jsonString);
//                            /**日期*/
//                            String date = jsonObject.getString("date");
//                            //Log.d("Data", "date: " + date);
//                            mListRMA.add(new BeanMAItemA(date));
//                            //
//                            dayByDay = new Integer(date);
//
//                            /**当日stories*/
//                            JSONArray jsonArrayStories = jsonObject.getJSONArray("stories");
//                            String stories = jsonArrayStories.toString();
//                            //Log.d("Data", "stories: " + stories);
//
//                            //记录当前mListRMA大小以便正确位置添加数据
//                            int mListRMA_lastSize = mListRMA.size();
//                            for (int i = 0; i < jsonArrayStories.length(); i++){
//                                String story = jsonArrayStories.get(i).toString();
//                                //添加除images之外的内容
//                                mListRMA.add(gson.fromJson(story, BeanMAItemB.class));
//                                //Log.d("Data", "story: " + story);
//                                //添加images数组里面的imageUri，虽然images数组里面只有一个imageUri...
//                                JSONObject oneStroy = jsonArrayStories.getJSONObject(i);
//                                JSONArray img = oneStroy.getJSONArray("images");
//                                String imge = img.getString(0);
//                                Log.d("Data", "image: " + imge);
//                                ((BeanMAItemB)mListRMA.get(i + 1 + mListRMA_lastSize - 1)).setImgUri(imge);
//                            }
//
//                            Log.d("List", "mListRMA.size() = " + mListRMA.size());
//                            Log.d("Data", "stories num = " + jsonArrayStories.length());
//
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    maAdapter.notifyDataSetChanged();
//                                }
//                            });
//
//                        } catch (IOException e){
//                            e.printStackTrace();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//                break;
//        }
//    }

    public static final String URL_LATEST = "http://news-at.zhihu.com/api/4/news/latest";
    public static final String URL_DATE = "http://news-at.zhihu.com/api/4/news/before/";

    public static Gson gson = new Gson();


    /**
     *
     * @param latestOrDate 0代表获取最新（当日）消息，非0（如20170221）即日期
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public static BeanMAItemA decodeBeanMAItemA(int latestOrDate) throws IOException, JSONException {
        String URL_ME;
        if (latestOrDate == 0){
            URL_ME = URL_LATEST;
        } else {
            URL_ME = URL_DATE + latestOrDate;
        }

        BeanMAItemA itemA = new BeanMAItemA("");
        String jsonString = AboutNet.getJSONString(URL_ME);
        JSONObject jsonObject = new JSONObject(jsonString);
        /**日期*/
        String date = jsonObject.getString("date");
        itemA.setTitle(date);

        return itemA;
    }

    /**
     *
     * @param latestOrDate 0代表获取最新（当日）消息，非0（如20170221）即日期
     * @param flag 0代表获取stories列表，1代表获取top_stories列表
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public static List<RecyclerMA> decodeRecyclerMA(int latestOrDate, int flag) throws IOException, JSONException {
        String URL_ME;
        if (latestOrDate == 0){
            URL_ME = URL_LATEST;
        } else {
            URL_ME = URL_DATE + latestOrDate;
        }

        List<RecyclerMA> recyclerMAList = new ArrayList<>();
        String jsonString = AboutNet.getJSONString(URL_ME);
        JSONObject jsonObject = new JSONObject(jsonString);

        switch (flag){
            case 0:
                /**当日stories*/
                JSONArray jsonArrayStories = jsonObject.getJSONArray("stories");
                String stories = jsonArrayStories.toString();
                //Log.d("Data", "stories: " + stories);

                for (int i = 0; i < jsonArrayStories.length(); i++){
                    String story = jsonArrayStories.get(i).toString();
                    //添加除images之外的内容
                    recyclerMAList.add(gson.fromJson(story, BeanMAItemB.class));
                    //Log.d("Data", "story: " + story);
                    //添加images数组里面的imageUri，虽然images数组里面只有一个imageUri...
                    JSONObject oneStroy = jsonArrayStories.getJSONObject(i);
                    JSONArray img = oneStroy.getJSONArray("images");
                    String imge = img.getString(0);
                    Log.d("Data", "image: " + imge);
                    ((BeanMAItemB)(recyclerMAList.get(i))).setImgUri(imge);
                }
                return recyclerMAList;
            case 1:
                JSONArray jsonArrayTopStories = jsonObject.getJSONArray("top_stories");
                String topStories = jsonArrayTopStories.toString();
                //Log.d("Data", "topStories: " + topStories);
                for (int i = 0; i < jsonArrayTopStories.length(); i++){
                    String topStory = jsonArrayTopStories.get(i).toString();
                    recyclerMAList.add(gson.fromJson(topStory, BeanBanner.class));
                    //Log.d("Data", "BeanBanner: " + (gson.fromJson(topStory, BeanBanner.class)).toString());
                }
                return recyclerMAList;
        }

        return recyclerMAList;
    }
}
