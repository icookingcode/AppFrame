package com.hnhy.epapp.mvp.ui.activity.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

/**
 * Created by guc on 2019/10/14.
 * 描述：观察者
 */
public class MyLifecycleObserver implements LifecycleObserver {
    private static final String TAG = "MyLifecycleObserver";

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        Log.e(TAG, "start: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void resume() {
        Log.e(TAG, "resume: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        Log.e(TAG, "stop: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void destroy() {
        Log.e(TAG, "destroy: ");
    }
}
