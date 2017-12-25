package org.loader.liteplayer.engine;

import android.os.Handler;
import android.os.Message;

import org.loader.liteplayer.pojo.SearchResult;
import org.loader.liteplayer.utils.Constants;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.ParserConfigurationException;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyinzaitian
 */
public class SearchMusic {
	/**http://music.baidu.com/top/new/?pst=shouyeTop*/
	private static final int SIZE = 20;
	/**"/search/song";*/
	private static final String URL = Constants.MUSIC_URL + "/top/new/?pst=shouyeTop";
	private static SearchMusic sInstance;
	private OnSearchResultListener mListener;
	
    private ExecutorService mThreadPool;

	public synchronized static SearchMusic getInstance() {
		if (sInstance == null) {
			try {
				sInstance = new SearchMusic();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
		}
		return sInstance;
	}

	private SearchMusic() throws ParserConfigurationException {
		mThreadPool = Executors.newSingleThreadExecutor();
	}

	public SearchMusic setListener(OnSearchResultListener l) {
		mListener = l;
		return this;
	}

	public void search(final String key, final int page) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Constants.SUCCESS:
					if(mListener != null) mListener.onSearchResult((ArrayList<SearchResult>)msg.obj);
					break;
				case Constants.FAILED:
					if(mListener != null){
						mListener.onSearchResult(null);
					}
					break;
				default:
					break;
				}
			}
		};
		
		mThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				ArrayList<SearchResult> results = getMusicList(key, page);
				if(results == null) {
					handler.sendEmptyMessage(Constants.FAILED);
					return;
				}
				
				handler.obtainMessage(Constants.SUCCESS, results).sendToTarget();
			}
		});
	}
	
	private ArrayList<SearchResult> getMusicList(final String key, final int page){
		return null;
	}

	public interface OnSearchResultListener {
		/**
		 * 结果回掉
		 * @param results ArrayList<SearchResult>
		 */
		void onSearchResult(ArrayList<SearchResult> results);
	}
}
