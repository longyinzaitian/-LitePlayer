package org.loader.liteplayer.application;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author longyinzaitian
 * @date 2018/1/5
 */

public class AppUtil {
    private Gson gson;
    private List<Activity> mActivities = new ArrayList<>();
    private static AppUtil mInstance;
    public static AppUtil getInstance() {
        if (mInstance == null) {
            mInstance = new AppUtil();
        }

        return mInstance;
    }


    public Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }

        return gson;
    }

    public void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    public void removeActivity(Activity activity) {
        mActivities.remove(activity);
    }

    public void removeAllActivity() {
        mActivities.clear();
    }

    public Activity getTopActivity() {
        if (mActivities != null && !mActivities.isEmpty()) {
            return mActivities.get(mActivities.size() -1);
        }

        return null;
    }
}
