package org.loader.liteplayer.utils;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * 功能：
 * 实现viewpager的界面的切换动画
 * @author longyinzaitian
 */
public class PlayPageTransformer implements PageTransformer {

    @Override
    public void transformPage(@NonNull View view, float position) {
        //[-Infinity,-1) 左边看不见了
        if(position < -1) {
            view.setAlpha(0.0f);
            // [-1,0]左边向中间 或 中间向左边
        }else if(position <= 0) {
            view.setAlpha(1 + position);
            view.setTranslationX(MobileUtils.getScreenWidth() * (-position));
            // (0,1] 右边向中间 或 中间向右边
        }else if(position <= 1) {
            view.setAlpha(1 + position);
            // (1,+Infinity] 右边看不见了
        }else if(position > 1) {
            view.setAlpha(1.0f);
        }
    }
}
