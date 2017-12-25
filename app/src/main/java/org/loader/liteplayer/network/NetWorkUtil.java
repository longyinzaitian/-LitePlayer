package org.loader.liteplayer.network;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;
import org.loader.liteplayer.utils.L;

import java.io.IOException;

/**
 * @author longyinzaitian
 * @date 2017/12/26.
 */
public class NetWorkUtil {
    private static final String TAG = "NetWorkUtil";

    public static void getHotSongRank(String topid){
        String url = "http://route.showapi.com/213-4";
        PostFormBuilder builder = OkHttpUtils.post().url(url)
                /**榜行榜id
                    3=欧美
                    4=流行榜
                    5=内地
                    6=港台
                    16=韩国
                    17=日本
                    26=热歌
                    27=新歌
                    28=网络歌曲
                    32=音乐人
                    36=K歌金曲*/
                .addParams("topid", topid);

        addSystemParams(builder);

        builder.build()
        .execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                L.l(TAG, "getHotSongRank parseNetworkResponse response:"+response);
                L.l(TAG, "getHotSongRank parseNetworkResponse response.body:"+response.body().string());
                return null;
            }

            @Override
            public void onError(Request request, Exception e) {
                L.l(TAG, "getHotSongRank request:" + request + ", exception:" + e.getMessage());
            }

            @Override
            public void onResponse(Object response) {
                L.l(TAG, "getHotSongRank response:" + response);
            }
        });
    }

    public static void getSongLrcById(String musicid){
        String url = "http://route.showapi.com/213-2";
        PostFormBuilder builder = OkHttpUtils.post().url(url)
                .addParams("musicid", musicid);

        addSystemParams(builder);

        builder.build()
            .execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    L.l(TAG, "getSongLrcById parseNetworkResponse response:"+response);
                    L.l(TAG, "getSongLrcById parseNetworkResponse response.body:"+response.body().string());
                    return null;
                }

                @Override
                public void onError(Request request, Exception e) {
                    L.l(TAG, "getSongLrcById request:" + request + ", exception:" + e.getMessage());
                }

                @Override
                public void onResponse(Object response) {
                    L.l(TAG, "getSongLrcById response:" + response);
                }
            });
    }

    public static void SearchSongByKeyword(String keyword, String page){
        String url = "http://route.showapi.com/213-1";
        PostFormBuilder builder = OkHttpUtils.post().url(url)
                .addParams("keyword", keyword)
                .addParams("page", page);

        addSystemParams(builder);

        builder.build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        L.l(TAG, "SearchSongByKeyword parseNetworkResponse response:"+response);
                        L.l(TAG, "SearchSongByKeyword parseNetworkResponse response.boyd:"+response.body().string());
                        return null;
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        L.l(TAG, "SearchSongByKeyword request:" + request + ", exception:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Object response) {
                        L.l(TAG, "SearchSongByKeyword response:" + response);
                    }
                });
    }



    private static void addSystemParams(PostFormBuilder builder){
        builder.addParams("showapi_appid", "44940");
        builder.addParams("showapi_sign", "121b5eab89134b539fdf85b527ad30b9");
        builder.addParams("showapi_timestamp", "");
        builder.addParams("showapi_sign_method", "md5");
        builder.addParams("showapi_res_gzip", "1");
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
