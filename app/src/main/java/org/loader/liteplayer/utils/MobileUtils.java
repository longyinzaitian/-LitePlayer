package org.loader.liteplayer.utils;

import org.loader.liteplayer.application.App;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyinzaitian
 */
public class MobileUtils {
    /**
     * 隐藏输入法软键盘
     * @param view attachview
     */
    public static void hideInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) App.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null){
            return;
        }

        if(imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) App.getContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null){
            return 0;
        }

        DisplayMetrics dm = new DisplayMetrics();
        Display display = wm.getDefaultDisplay();

        if (display == null){
            return 0;
        }

        display.getMetrics(dm);

        return dm.widthPixels;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenHeight(){
        WindowManager wm = (WindowManager) App.getContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null){
            return 0;
        }

        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
}
