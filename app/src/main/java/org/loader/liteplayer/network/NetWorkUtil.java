package org.loader.liteplayer.network;

import android.util.Log;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.OkHttpRequestBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.json.JSONObject;
import org.loader.liteplayer.utils.LogUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author longyinzaitian
 * @date 2017/12/26.
 */
public class NetWorkUtil {
    private static final String TAG = "NetWorkUtil";

    public static void getHotSongRank(String topid, final NetWorkCallBack netWorkCallBack){
        String url = "http://route.showapi.com/213-4";
        PostFormBuilder builder = OkHttpUtils.post().url(url)
                .addParams("topid", topid);

        addSystemParams(builder);

        builder.build()
        .execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (responseBody == null){
                    return null;
                }

                String responseStr = responseBody.string();
                LogUtil.l(TAG, "getHotSongRank parseNetworkResponse response.body:"+responseStr);
                return responseStr;
            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e(TAG, "getHotSongRank request:" + request + ", exception:" + e.getMessage());
                if (netWorkCallBack != null){
                    netWorkCallBack.onError("服务器异常，稍候重试");
                }
            }

            @Override
            public void onResponse(Object response) {
                LogUtil.l(TAG, "getHotSongRank response:" + response);
                if (response == null){
                    return;
                }

                JSONObject jsonObject = null;
                if (netWorkCallBack != null){
                    try{
                        jsonObject = new JSONObject(response.toString());
                        if (checkResult(jsonObject)){
                            netWorkCallBack.onResponse(jsonObject);
                        }else {
                            netWorkCallBack.onError(jsonObject.optString("showapi_res_error"));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        LogUtil.e(TAG, "exception:" + e.getMessage());
                    }
                }
            }
        });
    }

    public static void getSongLrcById(String musicid, final NetWorkCallBack netWorkCallBack){
        String url = "http://route.showapi.com/213-2";
        PostFormBuilder builder = OkHttpUtils.post().url(url)
                .addParams("musicid", musicid);

        addSystemParams(builder);

        builder.build()
            .execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    LogUtil.l(TAG, "getSongLrcById parseNetworkResponse response.body:"+response.body().string());
                    return response.body().string();
                }

                @Override
                public void onError(Request request, Exception e) {
                    LogUtil.e(TAG, "getSongLrcById request:" + request + ", exception:" + e.getMessage());
                    if (netWorkCallBack != null){
                        netWorkCallBack.onError("服务器异常，稍候重试");
                    }
                }

                @Override
                public void onResponse(Object response) {
                    LogUtil.l(TAG, "getSongLrcById response:" + response);
                    JSONObject jsonObject = null;
                    if (netWorkCallBack != null){
                        try{
                            jsonObject = new JSONObject(response.toString());
                            if (checkResult(jsonObject)){
                                netWorkCallBack.onResponse(jsonObject);
                            }else {
                                netWorkCallBack.onError(jsonObject.optString("showapi_res_error"));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            LogUtil.e(TAG, "exception:" + e.getMessage());
                        }
                    }
                }
            });
    }

    public static void SearchSongByKeyword(String keyword, String page, final NetWorkCallBack netWorkCallBack){
        String url = "http://route.showapi.com/213-1";
        PostFormBuilder builder = OkHttpUtils.post().url(url)
                .addParams("keyword", keyword)
                .addParams("page", page);

        addSystemParams(builder);

        builder.build()
            .execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    LogUtil.l(TAG, "SearchSongByKeyword parseNetworkResponse response.boyd:"+response.body().string());
                    return response.body().string();
                }

                @Override
                public void onError(Request request, Exception e) {
                    LogUtil.e(TAG, "SearchSongByKeyword request:" + request + ", exception:" + e.getMessage());
                    if (netWorkCallBack != null){
                        netWorkCallBack.onError("服务器异常，稍候重试");
                    }
                }

                @Override
                public void onResponse(Object response) {
                    LogUtil.l(TAG, "SearchSongByKeyword response:" + response);
                    JSONObject jsonObject = null;
                    if (netWorkCallBack != null){
                        try{
                            jsonObject = new JSONObject(response.toString());
                            if (checkResult(jsonObject)){
                                netWorkCallBack.onResponse(jsonObject);
                            }else {
                                netWorkCallBack.onError(jsonObject.optString("showapi_res_error"));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            LogUtil.e(TAG, "exception:" + e.getMessage());
                        }
                    }
                }
            });
    }

    /**
     * 查询一听音乐的排行榜类型。可根据此类型得到榜行榜歌单。
     */
    public static void getYiTingRankList(final NetWorkCallBack netWorkCallBack){
        String url = "http://route.showapi.com/928-1";
        PostFormBuilder builder = OkHttpUtils.post().url(url);

        addSystemParams(builder);

        builder.build()
            .execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    LogUtil.l(TAG, "getRankList parseNetworkResponse response.boyd:"+response.body().string());
                    return response.body().string();
                }

                @Override
                public void onError(Request request, Exception e) {
                    LogUtil.e(TAG, "getRankList request:" + request + ", exception:" + e.getMessage());
                    if (netWorkCallBack != null){
                        netWorkCallBack.onError("服务器异常，稍候重试");
                    }
                }

                @Override
                public void onResponse(Object response) {
                    LogUtil.l(TAG, "getRankList response:" + response);
                    JSONObject jsonObject = null;
                    if (netWorkCallBack != null){
                        try{
                            jsonObject = new JSONObject(response.toString());
                            if (checkResult(jsonObject)){
                                netWorkCallBack.onResponse(jsonObject);
                            }else {
                                netWorkCallBack.onError(jsonObject.optString("showapi_res_error"));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            LogUtil.e(TAG, "exception:" + e.getMessage());
                        }
                    }
                }
            });
    }

    /**
     * 根据榜行榜id 获取榜行榜歌单页面地址。
     */
    public static void getYiTingRankDetailList(String id, final NetWorkCallBack netWorkCallBack){
        String url = "http://route.showapi.com/928-2";
        PostFormBuilder builder = OkHttpUtils.post().url(url)
                .addParams("id", id);

        addSystemParams(builder);

        builder.build()
            .execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    LogUtil.l(TAG, "getRankDetailList parseNetworkResponse response.boyd:"+response.body().string());
                    return response.body().string();
                }

                @Override
                public void onError(Request request, Exception e) {
                    LogUtil.e(TAG, "getRankDetailList request:" + request + ", exception:" + e.getMessage());
                    if (netWorkCallBack != null){
                        netWorkCallBack.onError("服务器异常，稍候重试");
                    }
                }

                @Override
                public void onResponse(Object response) {
                    LogUtil.l(TAG, "getRankDetailList response:" + response);
                    JSONObject jsonObject = null;
                    if (netWorkCallBack != null){
                        try{
                            jsonObject = new JSONObject(response.toString());
                            if (checkResult(jsonObject)){
                                netWorkCallBack.onResponse(jsonObject);
                            }else {
                                netWorkCallBack.onError(jsonObject.optString("showapi_res_error"));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            LogUtil.e(TAG, "exception:" + e.getMessage());
                        }
                    }
                }
            });
    }

    /**
     * 根据关键词查询歌曲列表。
     */
    public static void getYiTingSearchDetailList(String q, final NetWorkCallBack netWorkCallBack){
        String url = "http://route.showapi.com/928-3";
        PostFormBuilder builder = OkHttpUtils.post().url(url)
                .addParams("q", q);

        addSystemParams(builder);

        builder.build()
            .execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    LogUtil.l(TAG, "getSearchDetailList parseNetworkResponse response.boyd:"+response.body().string());
                    return response.body().string();
                }

                @Override
                public void onError(Request request, Exception e) {
                    LogUtil.e(TAG, "getSearchDetailList request:" + request + ", exception:" + e.getMessage());
                    if (netWorkCallBack != null){
                        netWorkCallBack.onError("服务器异常，稍候重试");
                    }
                }

                @Override
                public void onResponse(Object response) {
                    LogUtil.l(TAG, "getSearchDetailList response:" + response);
                    JSONObject jsonObject = null;
                    if (netWorkCallBack != null){
                        try{
                            jsonObject = new JSONObject(response.toString());
                            if (checkResult(jsonObject)){
                                netWorkCallBack.onResponse(jsonObject);
                            }else {
                                netWorkCallBack.onError(jsonObject.optString("showapi_res_error"));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            LogUtil.e(TAG, "exception:" + e.getMessage());
                        }
                    }
                }
            });
    }

    private static void addSystemParams(OkHttpRequestBuilder builder){
        builder.addParams("showapi_appid", "44940");
        builder.addParams("showapi_sign", "121b5eab89134b539fdf85b527ad30b9");
        builder.addParams("showapi_timestamp", "");
        builder.addParams("showapi_sign_method", "md5");
        builder.addParams("showapi_res_gzip", "1");
    }

    public static void startDownFile(String url, String directory, String fileName, final DownFileCallback callback){
        GetBuilder builder = OkHttpUtils.get()
                .url(url);
        addSystemParams(builder);

        builder.build().execute(new FileCallBack(directory, fileName)
        {
            @Override
            public void inProgress(float progress)
            {
                if (callback == null){
                    return;
                }
                callback.updateProgress((int) (100 * progress));
            }

            @Override
            public void onError(Request request, Exception e)
            {
                Log.e(TAG, "onError :" + e.getMessage());
            }

            @Override
            public void onResponse(File file)
            {
                if (callback == null){
                    return;
                }
                callback.onResponseFile(file);
            }
        });
    }

    private static boolean checkResult(JSONObject result){
        if (result == null){
            return false;
        }

        int code = result.optInt("showapi_res_code");
        if (code == 0){
            return true;
        }

        return false;
    }
}
