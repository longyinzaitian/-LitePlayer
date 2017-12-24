package org.loader.liteplayer.application;

import android.content.Intent;

import org.loader.liteplayer.service.DownloadService;
import org.loader.liteplayer.service.PlayService;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyin
 */
public class App extends BaseApplication {
	
	@Override
	public void onCreate() {
		super.onCreate();
		startService(new Intent(context, PlayService.class));
		startService(new Intent(context, DownloadService.class));
	}



}
