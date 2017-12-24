package org.loader.liteplayer.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.ViewGroup;

import org.loader.liteplayer.R;

/**
 * @author longyin
 * @date 2017年12月24日
 */
public class StatusBarCompat {
    private static final int INVALID_VAL = -1;
    private static final int COLOR_DEFAULT = R.color.lime_green;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void compat(Activity activity, @ColorRes int statusColor)
    {
        //当前手机版本为5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if (statusColor != INVALID_VAL){
                activity.getWindow().setStatusBarColor(activity.getResources().getColor(statusColor));
            }else{
                activity.getWindow().setStatusBarColor(activity.getResources().getColor(COLOR_DEFAULT));
            }

            return;
        }

        //当前手机版本为4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            int color = COLOR_DEFAULT;
            ViewGroup contentView = activity.findViewById(android.R.id.content);

            if (statusColor != INVALID_VAL){
                color = statusColor;
            }

            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(activity.getResources().getColor(color));
            contentView.addView(statusBarView, lp);
        }

    }

    public static void compat(Activity activity)
    {
        compat(activity, INVALID_VAL);
    }

    private static int getStatusBarHeight(Context context)
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0){
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }
}
