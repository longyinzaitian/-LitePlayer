package org.loader.liteplayer.application;

import android.content.Intent;

import org.loader.liteplayer.pojo.RankList;
import org.loader.liteplayer.service.PlayService;

import java.util.List;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyinzaitian
 */
public class App extends BaseApplication {
    public static List<RankList> rankLists;
    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(context, PlayService.class));
    }



}
