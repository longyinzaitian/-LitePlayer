package org.loader.liteplayer.network;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

/**
 * @author longyinzaitian
 * @date 2017/12/26.
 */
public class NetWorkUtil {
    private static final String TAG = "NetWorkUtil";
    private static final  String HTTP_PRE = "http://api.moefou.org/";

    public static void getWikisItem(String type, int page, final NetWorkCallBack callBack) {
        String url = HTTP_PRE + "wikis.json";
        OkHttpUtils.get().url(url)
                .addParams("page", String.valueOf(page))
                .addParams("wiki_type", type)
                .addParams("api_key", "896637e2604e9dc3dd0b1b0d8d6c12fd05aacc21e")
                .build()
                .execute(new BaseCallback() {
                    @Override
                    public void onResult(JSONObject json) {
                        if (callBack == null) {
                            return;
                        }
                        callBack.onResponse(json);
                    }

                    @Override
                    public void onFail(Request request, Exception e) {
                    }
                });
    }
}
