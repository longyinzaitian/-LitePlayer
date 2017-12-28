package org.loader.liteplayer.engine;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.loader.liteplayer.utils.Constants;
import org.loader.liteplayer.utils.L;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 该类完成的功能：
 *  解析链接地址，是否是有效的歌曲文件下载地址
 *  如果是有效的下载地址，则返回歌曲的下载地址。
 *  如果不是有效的下载地址，则返回-1或null
 *  
 *  2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyinzaitian
 */

public class GetDownloadInfo {
    private static final int GET_MUSIC_SUCCESS = 5;
    private static final int GET_LRC_SUCCESS = 6;
    private static final int GET_MUSIC_FAILED = 7;
    private static final int GET_LRC_FAILED = 8;
    private static final String TAG = GetDownloadInfo.class.getSimpleName();

    private static GetDownloadInfo sInstance;
    private ExecutorService mThreadPool;
    private Handler mHandler;
    private OnDownloadGetListener mListener;

    /**
     * 单例类
     * 
     * @return GetDownloadInfo
     */
    public static GetDownloadInfo getInstance() {
        if (sInstance == null){
            sInstance = new GetDownloadInfo();
        }

        return sInstance;
    }

    @SuppressLint("HandlerLeak")
    private GetDownloadInfo() {
        /*
         * newCachedThreadPool()创建一个可根据需要创建新线程的线程池，
         * 但是在以前构造的线程可用时将重用它们。对于执行很多短期异步任务的程序而言
         * ， 这些线程池通常可提高程序性能。调用 execute
         * 将重用以前构造的线程（如果线程可用）。 如果现有线程没有可用的，
         * 则创建一个新线程并添加到池中。终止并从缓存中移除那些已有 60
         * 秒钟未被使用的线程。因此，长时间保持空闲的线程池不会使用任何资源。
         *  注意，可以使用 ThreadPoolExecutor
         * 构造方法创建具有类似属性但细节不同（例如超时参数）的线程池。
         */
        mThreadPool = Executors.newCachedThreadPool();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                case GET_MUSIC_SUCCESS:
                    if (mListener != null){
                        mListener.onMusic(msg.arg1, (String) msg.obj);
                    }
                    break;
                case GET_MUSIC_FAILED:
                    if (mListener != null){
                        mListener.onMusic(-1, null);
                    }
                    break;
                case GET_LRC_SUCCESS:
                    if (mListener != null){
                        mListener.onLrc(msg.arg1, (String) msg.obj);
                    }
                    break;
                case GET_LRC_FAILED:
                    if (mListener != null){
                        mListener.onLrc(-1, null);
                    }
                    break;
                default:
                    break;
                }
            }
        };
    }

    /**
     * 设置回调接口的对象的方法 该方法由外部调用，
     * 初始化该类中的OnDownloadGettedListener类对象mListener
     * 
     * @param l OnDownloadGetListener
     * @return GetDownloadInfo
     */
    public GetDownloadInfo setListener(OnDownloadGetListener l) {
        mListener = l;
        return this;
    }

    /**
     * 创建新的线程加入线程池执行任务
     * @param position int
     * @param url String
     */
    public void parse(final int position, final String url) {
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                L.l(TAG, "postion=" + position + ",url=" + url);
                String songId = getSongId(url);
                L.l(TAG, "解析出的歌曲ID =" + songId);
                getDownloadUrl(position, songId);
                getLrcUrl(position, url);
            }
        });
    }

    private String getSongId(String url) {
        String temp = url.replaceFirst("/song/", "");
        if (!temp.contains("/")){
            return temp;
        }

        return temp.substring(0, temp.indexOf("/"));
    }

    private void getLrcUrl(final int position, final String song) {
        String url = Constants.MUSIC_URL + song;
        Log.i(TAG, "下载地址=" + url);
    }

    private void getDownloadUrl(final int position, final String songId) {
        // http://music.baidu.com/song/241873/download?__o=%2Fsearch%2Fsong
        String url = Constants.MUSIC_URL + "/song/" + songId
                + "/download?__o=%2Fsearch%2Fsong";
    }

    /**
     * 回调接口 当完成歌曲文件和歌词文件的下载之后，回调响应的方法完成数据的更新
     */
    public interface OnDownloadGetListener {
        /**
         * 下载Mp3
         * @param position int
         * @param url String
         */
        void onMusic(int position, String url);

        /**
         * 下载歌词
         * @param position int
         * @param url String
         */
        void onLrc(int position, String url);
    }
}
