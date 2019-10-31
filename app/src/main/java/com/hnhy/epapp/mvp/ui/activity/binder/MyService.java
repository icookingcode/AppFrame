package com.hnhy.epapp.mvp.ui.activity.binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by guc on 2019/10/31.
 * 描述：
 */
public class MyService extends Service {
    private static final String TAG = "MyService";
    private Binder mBinder = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new IMediaPlayerService();
        Log.e(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: ");
        return mBinder;
    }
}
