package org.loader.liteplayer.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.loader.liteplayer.application.App;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyinzaitian
 */
public class SpUtils {
	public static void put(final String key, final Object value) {
		SharedPreferences sp = App.getContext().getSharedPreferences(Constants.SP_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		
		if(value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		}else if(value instanceof Float) {
			editor.putFloat(key, (Float) value);
		}else if(value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		}else if(value instanceof Long) {
			editor.putLong(key, (Long) value);
		}else {
			editor.putString(key, (String) value);
		}
		
		editor.apply();
	}
	
	public static Object get(String key, Object defaultObject) {
		SharedPreferences sp = App.getContext().getSharedPreferences(Constants.SP_NAME,
				Context.MODE_PRIVATE);

		if (defaultObject instanceof String) {
			return sp.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer) {
			return sp.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean) {
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float) {
			return sp.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long) {
			return sp.getLong(key, (Long) defaultObject);
		}

		return defaultObject;
	}
	
	/**
	 * 移除某个key值已经对应的值
	 * @param context Context
	 * @param key String
	 */
	public static void remove(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(Constants.SP_NAME,
				Context.MODE_PRIVATE);
		sp.edit().remove(key).apply();
	}

	/**
	 * 清除所有数据
	 * @param context Context
	 */
	public static void clear(Context context) {
		SharedPreferences sp = context.getSharedPreferences(Constants.SP_NAME,
				Context.MODE_PRIVATE);
		sp.edit().clear().apply();
	}
}
