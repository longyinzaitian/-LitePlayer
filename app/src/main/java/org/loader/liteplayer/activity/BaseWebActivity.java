package org.loader.liteplayer.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.loader.liteplayer.R;

import static android.R.attr.value;

/**
 * @author husyin
 * @date 2018/1/15
 */

public abstract class BaseWebActivity extends BaseActivity {
    protected WebView mWebView;
    private ViewGroup mRootView;
    private FrameLayout mWebViewContainer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view_layout;
    }

    @Override
    protected void bindView() {
        mWebViewContainer = findViewById(R.id.act_web_view_container);
        mRootView = findViewById(R.id.act_web_view_root);

        //不在xml中定义 Webview ，而是在需要的时候在Activity中创建，并且Context使用 getApplicationgContext()
        //防止内存泄漏的手段
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(params);
        mWebViewContainer.addView(mWebView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        mWebView.resumeTimers();

        setSetting();

        setWebClient();

        setChromeClient();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
        mWebView.pauseTimers();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()){
            mWebView.goBack();
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRootView != null){
            mRootView.removeView(mWebView);
        }

        if (mWebView != null){
            //在 Activity 销毁（ WebView ）的时候，先让 WebView 加载null内容，
            // 然后移除 WebView，再销毁 WebView，最后置空。
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);

            mWebView.destroyDrawingCache();
            mWebView.clearCache(true);
            mWebView.removeAllViewsInLayout();
            mWebView.removeAllViews();
            mWebView.clearAnimation();
            mWebView.clearFormData();
            mWebView.clearHistory();
            mWebView.destroy();
            mWebView = null;
        }
    }

    private void setSetting(){
        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

        //支持插件
        webSettings.setPluginsEnabled(true);

        //设置自适应屏幕，两者合用
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);

        //缩放操作
        //支持缩放，默认为true。是下面那个的前提。
        webSettings.setSupportZoom(true);
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setBuiltInZoomControls(true);
        //隐藏原生的缩放控件
        webSettings.setDisplayZoomControls(false);

        //其他细节操作
        //关闭webview中缓存
        /*
         //缓存模式如下：
         //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         //LOAD_DEFAULT:    （默认）根据cache-control决定是否从网络上取数据。
         //LOAD_NO_CACHE:   不使用缓存，只从网络获取数据.
         //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");

        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        //开启 database storage API 功能
        webSettings.setDatabaseEnabled(true);
        //开启 Application Caches 功能
        webSettings.setAppCacheEnabled(true);

        // 每个 Application 只调用一次
        // WebSettings.setAppCachePath()，WebSettings.setAppCacheMaxSize()
        String cacheDirPath = getExternalCacheDir().getAbsolutePath();
        //设置  Application Caches 缓存目录
        webSettings.setAppCachePath(cacheDirPath);

    }

    private void setWebClient(){
        //步骤3. 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应。
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }

            /***JS代码调用一定要在 onPageFinished（） 回调之后才能调用，否则不会调用。*/
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
            }

            // 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
            @Override
            public void onLoadResource(WebView webView, String s) {
                super.onLoadResource(webView, s);
            }

            //加载页面的服务器出现错误时（如404）调用。
            //App里面使用webview控件的时候遇到了诸如404这类的错误的时候，若也
            // 显示浏览器里面的那种错误提示页面就显得很丑陋了，那么这个时候我们的app就需要
            // 加载一个本地的错误提示页面，即webview如何加载一个本地的页面
            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                super.onReceivedError(webView, i, s, s1);
                switch(i)
                {
                    case 404:
                        mWebView.loadUrl("file:///android_assets/error_handle.html");
                        break;
                    default:
                        break;
                }
            }

            // webView默认是不处理https请求的，页面显示空白，需要进行如下设置
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();    //表示等待证书响应
                // handler.cancel();      //表示挂起连接，为默认方式
                // handler.handleMessage(null);    //可做其他处理
            }


        });
    }


    private void setChromeClient() {
        mWebView.setWebChromeClient(new WebChromeClient() {

            //获得网页的加载进度并显示
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                }
            }

            //获取Web页中的标题
            //每个网页的页面都有一个标题，比如www.baidu.com这个页面的标题即“百度一下，你就知道”，
            // 那么如何知道当前webview正在加载的页面的title并进行设置呢？
            @Override
            public void onReceivedTitle(WebView view, String title) {

            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result)  {
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
                return true;
            }


        });
    }

    /**
     对于Android调用JS代码的方法有2种：
     通过WebView的loadUrl（）
     通过WebView的evaluateJavascript（）

     loadUrl() -> 优点：方便简洁  缺点：获取返回值麻烦  使用场景：不需要获取返回值，对性能要求不高

     evaluateJavaScript() -> 优点：效率高 缺点：4.4以上
     */
    private void loadJs(){
        // 因为该方法在 Android 4.4 版本才可使用，所以使用时需进行版本判断
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mWebView.loadUrl("javascript:callJS()");

        } else {
            mWebView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //此处为 js 返回的结果
                }
            });
        }

    }

}
