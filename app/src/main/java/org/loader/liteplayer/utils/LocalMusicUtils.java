package org.loader.liteplayer.utils;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import org.loader.liteplayer.application.App;
import org.loader.liteplayer.pojo.Music;

import java.util.ArrayList;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyinzaitian
 */
public class LocalMusicUtils {
	private static final String TAG = "LocalMusicUtils";

	/**
	 * 根据id获取歌曲uri
	 * @deprecated
	 * @param musicId int
	 * @return String
	 */
	public static String queryMusicById(int musicId) {
		String result = null;
		Cursor cursor = App.getContext().getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Audio.Media.DATA },
				MediaStore.Audio.Media._ID + "=?",
				new String[] { String.valueOf(musicId) }, null);

		for (cursor.moveToFirst(); !cursor.isAfterLast();) {
			result = cursor.getString(0);
			break;
		}

		cursor.close();
		return result;
	}

	/**
	 * 获取目录下的歌曲
	 */
	public static ArrayList<Music> queryMusic() {
		ArrayList<Music> results = new ArrayList<Music>();
		Cursor cursor = App.getContext().getContentResolver().query(
				MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
				null,null, null,
				MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

		// id title singer data time image
		if (cursor != null){
			addMedia(cursor, results);
		}

		cursor = App.getContext().getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				null,null, null,
				MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

		if(cursor==null){
			return results;
		}

		addMedia(cursor, results);
		cursor.close();
		L.l(TAG, "results.size="+results.size());

		return results;
	}
	
	private static void addMedia(Cursor cursor,ArrayList<Music> results){
		Music music;

		for (int i = 0; i<cursor.getCount(); i++){
			cursor.moveToNext();

			String uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
			//系统铃声过滤掉
			if (uri.startsWith("/system/")){
				L.l(TAG, "title:"+cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
				L.l(TAG, "artist:"+cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
				continue;
			}
			
			String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
			String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
			
			if(isRepeat(title, artist)){
				L.l(TAG, "title:" + title);
				L.l(TAG, "artist:" + artist);
				continue;
			}
			
			music = new Music();
			music.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
			music.setTitle(title);
			music.setArtist(artist);
			music.setUri(uri);
			L.l(TAG, "uri:"+music.getUri());
			music.setLength(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
			music.setImage(getAlbumImage(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))));
			results.add(music);
		}
	}
	
	/**
	 * 根据音乐名称和艺术家来判断是否重复包含了
	 * @param title String
	 * @param artist String
	 * @return boolean
	 */
	private static boolean isRepeat(String title, String artist) {
		for(Music music : MusicUtils.sMusicList) {
			if(title.equals(music.getTitle()) && artist.equals(music.getArtist())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据歌曲id获取图片
	 * @param albumId int
	 * @return String
	 */
	private static String getAlbumImage(int albumId) {
		String result = "";
		Cursor cursor = null;
		try {
			cursor = App.getContext().getContentResolver().query(
					Uri.parse("content://media/external/audio/albums/"
							+ albumId), new String[] { "album_art" }, null,
					null, null);

			for (cursor.moveToFirst(); !cursor.isAfterLast();) {
				result = cursor.getString(0);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}

		return result;
	}
}
