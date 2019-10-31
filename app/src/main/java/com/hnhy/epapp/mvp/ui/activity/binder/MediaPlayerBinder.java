package com.hnhy.epapp.mvp.ui.activity.binder;

import android.os.RemoteException;
import android.util.Log;

import com.hnhy.epapp.IMediaPlayerInterface;

/**
 * Created by guc on 2019/10/31.
 * 描述：
 */
public class MediaPlayerBinder extends IMediaPlayerInterface.Stub {
    private static final String TAG = "MediaPlayerBinder";

    @Override
    public void play(String path) throws RemoteException {
        Log.e(TAG, "play: " + path);
    }

    @Override
    public void stop() throws RemoteException {
        Log.e(TAG, "stop: ");
    }
}
