package org.loader.liteplayer.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import org.loader.liteplayer.application.AppUtil;
import org.loader.liteplayer.event.EventCenter;
import org.loader.liteplayer.event.PublishProgressEvent;
import org.loader.liteplayer.event.SongPlayChangeEvent;
import org.loader.liteplayer.service.PlayService;
import org.loader.liteplayer.utils.LogUtil;
import org.loader.liteplayer.utils.StatusBarCompat;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyinzaitian
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected PlayService mPlayService;
    private final String TAG = BaseActivity.class.getSimpleName();
    
    private ServiceConnection mPlayServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.l(TAG, "play--->onServiceDisconnected");
            mPlayService = null;
        }
        
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayService = ((PlayService.PlayBinder) service).getService();
            mPlayService.setOnMusicEventListener(mMusicEventListener);
        }
    };
    
    /**
     * 音乐播放服务回调接口的实现类
     */
    private PlayService.OnMusicEventListener mMusicEventListener = 
            new PlayService.OnMusicEventListener() {
        @Override
        public void onPublish(int progress) {
            EventCenter.getInstance().postEvent(new PublishProgressEvent(progress));
        }

        @Override
        public void onChange(int position) {
            EventCenter.getInstance().postEvent(new SongPlayChangeEvent(position));
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
        AppUtil.getInstance().addActivity(this);
        StatusBarCompat.compat(this);
        setContentView(getLayoutId());
        bindView();
        bindListener();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        allowBindService();
    }

    @Override
    protected void onDestroy() {
        AppUtil.getInstance().removeActivity(this);
        super.onDestroy();
        clearData();
    }

    /**
     * 获取布局文件
     * @return int
     */
    protected abstract @LayoutRes int getLayoutId();

    /**
     * 绑定view
     */
    protected abstract void bindView();

    /**
     * 初始化监听器
     */
    protected abstract void bindListener();

    /**
     * 拉取数据
     */
    protected abstract void loadData();

    /**
     * 做一些清理工作
     */
    protected abstract void clearData();
}
