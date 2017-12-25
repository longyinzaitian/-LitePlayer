package org.loader.liteplayer.network;

import org.json.JSONObject;

/**
 * @author 15361
 * @date 2017/12/26.
 */
public interface NetWorkCallBack {
    /**
     * 回掉
     * @return
     */
    JSONObject onResponse();

    /**
     * 错误
     * @param errorMsg
     */
    void onError(String errorMsg);
}
