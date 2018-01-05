package org.loader.liteplayer.network;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;
import org.loader.liteplayer.utils.LogUtil;

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
                LogUtil.l(TAG, "getHotSongRank parseNetworkResponse response.body:"+response.body().string());
                return response.body().string();
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
