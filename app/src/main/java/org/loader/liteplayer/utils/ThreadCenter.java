package org.loader.liteplayer.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author longyinzaitian
 * @date 2017/12/25
 */

public class ThreadCenter {
    private static ThreadCenter mInstance;
    private ThreadPoolExecutor mThreadPoolExecutor;
    private Handler mhandler = null;

    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;
    public static ThreadCenter getInstance(){
        if (mInstance == null){
            synchronized (ThreadCenter.class){
                if (mInstance == null){
                    mInstance = new ThreadCenter();
                }
            }
        }

        return mInstance;
    }

    private ThreadCenter(){
        this.corePoolSize = Runtime.getRuntime().availableProcessors();
        this.maximumPoolSize = corePoolSize * 2 + 1;
        this.keepAliveTime = 1000 * 30;
    }

    private ThreadPoolExecutor initExecutor() {
        if(mThreadPoolExecutor == null) {
            synchronized(ThreadCenter.class) {
                if(mThreadPoolExecutor == null) {

                    TimeUnit unit =  TimeUnit.MILLISECONDS;
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
                    LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();

                    mThreadPoolExecutor = new ThreadPoolExecutor(
                            //核心线程数
                            corePoolSize,
                            //最大线程数
                            maximumPoolSize,
                            //保持时间
                            keepAliveTime,
                            //保持时间对应的单位
                            unit,
                            //线程工厂
                            workQueue,
                            threadFactory,
                            //异常捕获器
                            handler);
                }
            }
        }
        return mThreadPoolExecutor;
    }

    /**执行任务*/
    public void executeTask(Runnable r) {
        initExecutor();
        mThreadPoolExecutor.execute(r);
    }

    /**
     * 主线程执行任务
     * @param r
     */
    public void executeUITask(Runnable r){
        if (mhandler == null){
            mhandler = new Handler(Looper.getMainLooper());
        }

        mhandler.post(r);
    }

    /**
     * 在主线程，延迟执行
     */
    public void executeUITaskDelay(Runnable r, long delay){
        if (mhandler == null){
            mhandler = new Handler(Looper.getMainLooper());
        }

        mhandler.postDelayed(r, delay);
    }

    /**提交任务*/
    public <T> Future<T> commitTask(Callable<T> r) {
        initExecutor();
        return mThreadPoolExecutor.submit(r);
    }

    /**删除任务*/
    public void removeTask(Runnable r) {
        initExecutor();
        mThreadPoolExecutor.remove(r);
    }

    public void stop(){
        initExecutor();
        if (mThreadPoolExecutor != null && !mThreadPoolExecutor.isShutdown()){
            mThreadPoolExecutor.shutdown();
            mThreadPoolExecutor = null;
        }

        if (mhandler != null){
            mhandler.removeCallbacksAndMessages(null);
        }
    }

}
