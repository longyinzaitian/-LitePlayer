package org.loader.liteplayer.network;

import android.app.Activity;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;
import org.loader.liteplayer.application.AppUtil;
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

            final JSONObject responseJson = json.optJSONObject("response");
            Activity activity = AppUtil.getInstance().getTopActivity();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onResult(responseJson);
                    }
                });
            }
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onError(final Request request, final Exception e) {
        Activity activity = AppUtil.getInstance().getTopActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onFail(request, e);
                }
            });
        }
    }

    @Override
    public void onResponse(Object response) {
        LogUtil.l(TAG, "response:" + response);
    }

    /**
     * 返回结果
     * @param json json
     */
    public abstract void onResult(JSONObject json);

    /**
     * 失败
     * @param request 请求
     * @param e 异常
     */
    public abstract void onFail(Request request, Exception e);
}
