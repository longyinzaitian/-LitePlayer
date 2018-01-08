package org.loader.liteplayer.event;

import org.loader.liteplayer.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author longyinzaitian
 * @date 2017/12/18
 *
 * 事件处理中心，线程安全，可多线程处理事件
 *
 * 主要目的为解耦
 *
 * 当Event事件在全局或者多处使用时，建议使用使用该方法进行处理。
 * 如果事件仅仅在一处使用，建议使用回调更好。
 * 原因在于该事件监听全局有效，逻辑较重。
 */

public class EventCenter {
    private static final String TAG = "EventCenter";
    private static EventCenter mEventCenter;
    private ConcurrentHashMap<Class, List<IEventCallback>> map = new ConcurrentHashMap<>();

    public static EventCenter getInstance(){
        if (mEventCenter == null){
            synchronized (EventCenter.class){
                if (mEventCenter == null){
                    mEventCenter = new EventCenter();
                }
            }
        }

        return mEventCenter;
    }

    /**
     * 发送事件
     */
    public void postEvent(IEvent event){
        if (map.containsKey(event.getClass())){
            List<IEventCallback> list = map.get(event.getClass());
            for (IEventCallback callback : list){
                callback.eventCallback(event);
            }
        }
    }

    /**
     * 注册时间监听
     */
    public void registerIEvent(Class<? extends IEvent> clazz, IEventCallback callback){
        if (!map.containsKey(clazz)){
            List<IEventCallback> list = new ArrayList<>();
            list.add(callback);
            map.put(clazz, list);
        }else{
            map.get(clazz).add(callback);
        }
    }

    public void removeIEventCallback(Class<? extends IEvent> clazz, IEventCallback iEventCallback){
        if (!map.containsKey(clazz)){
            return;
        }

        List<IEventCallback> list = map.get(clazz);

        if (list.contains(iEventCallback)){
            list.remove(iEventCallback);
        }
    }

    public void removeAllEvent(){
        LogUtil.l(TAG , "removeAllEvent() map.size:" + map.size());
        if (map != null){
            map.clear();
        }

        mEventCenter = null;
    }
}
