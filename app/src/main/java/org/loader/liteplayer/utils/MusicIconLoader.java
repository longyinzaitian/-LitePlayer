package org.loader.liteplayer.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import java.io.File;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyinzaitian
 */
public class MusicIconLoader {
	private static final String TAG = "MusicIconLoader";
	private static MusicIconLoader sInstance;

	private LruCache<String, Bitmap> mCache;

	/**
	 *  获取MusicIconLoader的实例
	 * @return MusicIconLoader
	 */
	public synchronized static MusicIconLoader getInstance() {
		if (sInstance == null) {
			sInstance = new MusicIconLoader();
		}
		return sInstance;
	}

	/**
	 * 构造方法， 初始化LruCache
	 */
	private MusicIconLoader() {
		int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
		mCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}
		};
	}

	/**
	 * 根据路径获取图片
	 * @param uri String
	 * @return Bitmap
	 */
	public Bitmap load(final String uri) {
		if (TextUtils.isEmpty(uri)){
			return null;
		}

		final String key = Encrypt.md5(uri);
		Bitmap bmp = getFromCache(key);

		if (bmp != null) {
			return bmp;
		}

		if (!new File(uri).exists()){
			L.l(TAG, "file:"+uri+" is not exist");
			return null;
		}

		bmp = BitmapFactory.decodeFile(uri);
		addToCache(key, bmp);

		return bmp;
	}

	/**
	 * 从内存中获取图片
	 * @param key String
	 * @return Bitmap
	 */
	private Bitmap getFromCache(final String key) {
		return mCache.get(key);
	}

	/**
	 * 将图片缓存到内存中
	 * @param key String
	 * @param bmp Bitmap
	 */
	private void addToCache(final String key, final Bitmap bmp) {
		if (getFromCache(key) == null && key != null && bmp != null){
			mCache.put(key, bmp);
		}
	}
}