package org.loader.liteplayer.network;

import android.app.Activity;

import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;
import org.loader.liteplayer.application.AppUtil;
import org.loader.liteplayer.utils.LogUtil;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author longyinzaitian
 * @date 2018/3/17.
 */
public abstract class BaseCallback extends Callback {
    private static final String TAG = "http:BaseCallback";

    @Override
    public Object parseNetworkResponse(Response response, int id) throws Exception {
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
    public void onError(Call call, final Exception e, int id) {
        Activity activity = AppUtil.getInstance().getTopActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onFail(null, e);
                }
            });
        }
    }

    @Override
    public void onResponse(Object response, int id) {
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
