package org.loader.liteplayer.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import org.loader.liteplayer.service.DownloadService;
import org.loader.liteplayer.service.PlayService;
import org.loader.liteplayer.utils.L;
import org.loader.liteplayer.utils.StatusBarCompat;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyin
 */
public abstract class BaseActivity extends AppCompatActivity {
	protected PlayService mPlayService;
	protected DownloadService mDownloadService;
	private final String TAG = BaseActivity.class.getSimpleName();
	
	private ServiceConnection mPlayServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			L.l(TAG, "play--->onServiceDisconnected");
			mPlayService = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mPlayService = ((PlayService.PlayBinder) service).getService();
			mPlayService.setOnMusicEventListener(mMusicEventListener);
		}
	};
	
	private ServiceConnection mDownloadServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			L.l(TAG, "download--->onServiceDisconnected");
			mDownloadService = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mDownloadService = ((DownloadService.DownloadBinder) service).getService();
		}
	};
	
	/**
	 * 音乐播放服务回调接口的实现类
	 */
	private PlayService.OnMusicEventListener mMusicEventListener = 
			new PlayService.OnMusicEventListener() {
		@Override
		public void onPublish(int progress) {
		}

		@Override
		public void onChange(int position) {
		}
	};
	
	/**
	 * Fragment的view加载完成后回调
	 * 
	 * 注意：
	 * allowBindService()使用绑定的方式启动歌曲播放的服务
	 * allowUnbindService()方法解除绑定
	 * 
	 * 在SplashActivity.java中使用startService()方法启动过该音乐播放服务了
	 * 那么大家需要注意的事，该服务不会因为调用allowUnbindService()方法解除绑定
	 * 而停止。
	 */
	public void allowBindService() {
		getApplicationContext().bindService(new Intent(this, PlayService.class),
				mPlayServiceConnection,
				Context.BIND_AUTO_CREATE);
	}
	
	/**
	 * fragment的view消失后回调
	 */
	public void allowUnbindService() {
		getApplicationContext().unbindService(mPlayServiceConnection);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StatusBarCompat.compat(this);
		//绑定下载服务
		bindService(new Intent(this, DownloadService.class),
				mDownloadServiceConnection,
				Context.BIND_AUTO_CREATE);

	}

	@Override
	protected void onDestroy() {
		unbindService(mDownloadServiceConnection);
		super.onDestroy();
	}

	public DownloadService getDownloadService() {
		return mDownloadService;
	}
}
