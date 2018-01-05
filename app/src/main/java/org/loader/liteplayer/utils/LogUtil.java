package org.loader.liteplayer.utils;

import android.util.Log;

import org.loader.liteplayer.BuildConfig;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyinzaitian
 */
public class LogUtil {
    
    public static void l(String tag, String msg) {
        if (!BuildConfig.DEBUG){
            return;
        }

        Log.i(tag, msg);
    }

    public static void e(String tag, String msg){
        if (!BuildConfig.DEBUG){
            return;
        }

        Log.e(tag, msg);
    }
}
