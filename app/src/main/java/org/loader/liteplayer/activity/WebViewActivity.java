package org.loader.liteplayer.activity;

/**
 * @author husyin
 * @date 2018/1/15
 */

public class WebViewActivity extends BaseWebActivity {

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

    }

    @Override
    protected void clearData() {

    }
}
