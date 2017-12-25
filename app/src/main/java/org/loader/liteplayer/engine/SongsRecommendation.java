package org.loader.liteplayer.engine;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import org.loader.liteplayer.pojo.SearchResult;
import org.loader.liteplayer.utils.Constants;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 2015年8月15日 15:54:43
 * 博文地址：http://blog.csdn.net/u010156024
 * 该类完成功能： 有URL链接解析出推荐的歌曲列表
 * @author longyinzaitian
 *
 * // http://music.baidu.com/top/new/?pst=shouyeTop
 */
public class SongsRecommendation {
	private static final String URL = Constants.MUSIC_URL
			+ "/top/new/?pst=shouyeTop";
	private static SongsRecommendation sInstance;
	/**
	 * 回调接口，传递数据给Activity或者Fragment
	 * 非常好用的数据传递方式
	 */
	private static OnRecommendationListener mListener;

	private ExecutorService mThreadPool;

	public static SongsRecommendation getInstance() {
		if (sInstance == null){
			sInstance = new SongsRecommendation();
		}

		return sInstance;
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constants.SUCCESS:
				if (mListener != null){
					mListener.onRecommend((ArrayList<SearchResult>) msg.obj);
				}
				break;

			case Constants.FAILED:
				if (mListener != null){
					mListener.onRecommend(null);
				}
				break;
			default:
				break;
			}
		}
	};
	
	@SuppressLint("HandlerLeak")
	private SongsRecommendation() {
		// 创建单线程池
		mThreadPool = Executors.newSingleThreadExecutor();
	}

	/**
	 * 设置回调接口OnRecommendationListener类的对象mListener
	 * 
	 * @param l OnRecommendationListener
	 * @return SongsRecommendation
	 */
	public SongsRecommendation setListener(OnRecommendationListener l) {
		mListener = l;
		return this;
	}
	/**
	 * 真正执行网页解析的方法
	 * 线程池中开启新的线程执行解析，解析完成之后发送消息
	 * 将结果传递到主线程中
	 */
	public void get() {
		mThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				ArrayList<SearchResult> result = getMusicList();
				if (result == null) {
					mHandler.sendEmptyMessage(Constants.FAILED);
					return;
				}
				mHandler.obtainMessage(Constants.SUCCESS, result)
						.sendToTarget();
			}
		});
	}

	/**
	 * 从网络获取歌曲列表
	 * @return ArrayList<SearchResult>
	 */
	private ArrayList<SearchResult> getMusicList() {
		return null;
	}

	/**
	 * 清理工作
	 */
	public void cleanWork(){
		if (mHandler != null){
			mHandler.removeCallbacksAndMessages(null);
			mHandler = null;
		}

		sInstance = null;
	}

	/**
	 * 回调接口 获取数据之后，通过该接口设置数据传递
	 */
	public interface OnRecommendationListener {
		/**
		 * 获取推荐歌曲列表
		 * @param results ArrayList<SearchResult>
		 */
		void onRecommend(ArrayList<SearchResult> results);
	}
}
