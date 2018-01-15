package org.loader.liteplayer.application;

import android.app.Application;
import android.content.Context;

import com.tencent.smtt.sdk.QbSdk;

import org.loader.liteplayer.utils.LogUtil;

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
        QbSdk.initX5Environment(context, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                LogUtil.l("BaseApplication", "Qbsdk -> onCoreInitFinished ");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                LogUtil.l("BaseApplication", "Qbsdk -> onViewInitFinished b:" + b);
            }
        });
    }

    public static Context getContext(){
        return context;
    }
}
