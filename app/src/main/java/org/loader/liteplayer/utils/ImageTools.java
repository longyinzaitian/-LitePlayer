package org.loader.liteplayer.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.loader.liteplayer.application.App;
import org.loader.liteplayer.application.BaseApplication;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyinzaitian
 */
public class ImageTools {
    /**
     * 缩放图片
     * @param bmp Bitmap
     * @return Bitmap
     */
    public static Bitmap scaleBitmap(Bitmap bmp) {
        return scaleBitmap(bmp, (int) (MobileUtils.getScreenWidth() * 0.13));
    }
    
    /**
     * 缩放图片
     * @param bmp Bitmap
     * @param size int
     * @return Bitmap
     */
    public static Bitmap scaleBitmap(Bitmap bmp, int size) {
        return Bitmap.createScaledBitmap(bmp, size, size, true);
    }
    
    /**
     * 缩放资源图片
     * @param res int
     * @return Bitmap
     */
    public static Bitmap scaleBitmap(int res) {
        return scaleBitmap(BitmapFactory.decodeResource(
                BaseApplication.getContext().getResources(), res));
    }
}
