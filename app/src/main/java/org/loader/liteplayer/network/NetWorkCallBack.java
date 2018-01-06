package org.loader.liteplayer.network;

import org.json.JSONObject;

/**
 * @author longyinzaitian
 * @date 2017/12/26.
 */
public interface NetWorkCallBack {
    /**
     * 回掉
     * @param jsonObject  json
     * @return jsonObject
     */
    void onResponse(JSONObject jsonObject);

    /**
     * 错误
     * @param errorMsg String
     */
    void onError(String errorMsg);
}
