package org.loader.liteplayer.network;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;
import org.loader.liteplayer.utils.LogUtil;

import java.io.IOException;

/**
 * @author longyinzaitian
 * @date 2018/3/17.
 */
public abstract class BaseCallback extends Callback {
    private static final String TAG = "http:BaseCallback";
    @Override
    public Object parseNetworkResponse(Response response) throws IOException {
        try {
            String result = response.body().string();
            LogUtil.l(TAG, "result:" + result);
            JSONObject json = new JSONObject(result);
            json = json.optJSONObject("response");
            onResult(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onError(Request request, Exception e) {
        onFail(request, e);
    }

    @Override
    public void onResponse(Object response) {
        LogUtil.l(TAG, "response:" + response);
    }

    public abstract void onResult(JSONObject json);

    /**
     * 失败
     * @param request 请求
     * @param e 异常
     */
    public abstract void onFail(Request request, Exception e);
}
