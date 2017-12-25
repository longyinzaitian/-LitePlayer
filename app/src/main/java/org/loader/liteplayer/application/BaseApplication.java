package org.loader.liteplayer.application;

import android.app.Application;
import android.content.Context;

/**
 * @author longyinzaitian
 * @date 2017/12/24
 */

public abstract class BaseApplication extends Application {
    protected static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }
}
