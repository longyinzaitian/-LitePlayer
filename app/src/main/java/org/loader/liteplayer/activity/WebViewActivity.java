package org.loader.liteplayer.activity;

import android.content.Intent;

import org.loader.liteplayer.utils.LogUtil;

/**
 * @author longyinzaitian
 * @date 2018/1/15
 */

public class WebViewActivity extends BaseWebActivity {
    private static final String TAG = "WebViewActivity";
    @Override
    protected void bindListener() {
        mWebView.setEnabled(true);
        mWebView.clearCache(true);
        mWebView.clearAnimation();
        mWebView.destroyDrawingCache();
        mWebView.removeAllViewsInLayout();
    }

    @Override
    protected void loadData() {
        Intent intent = getIntent();
        if (intent == null){
            return;
        }

        String url = intent.getStringExtra("url");
        LogUtil.l(TAG, "url:" + url);
        mWebView.loadUrl(url);
    }

    @Override
    protected void clearData() {

    }
}
