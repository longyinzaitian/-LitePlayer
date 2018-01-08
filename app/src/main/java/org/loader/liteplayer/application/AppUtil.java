package org.loader.liteplayer.application;

import com.google.gson.Gson;

/**
 * @author longyinzaitian
 * @date 2018/1/5
 */

public class AppUtil {
    private static Gson gson;

    public static Gson getGson(){
        if (gson == null){
            gson = new Gson();
        }

        return gson;
    }


}
