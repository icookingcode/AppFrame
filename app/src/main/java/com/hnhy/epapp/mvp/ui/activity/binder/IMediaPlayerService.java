package com.hnhy.epapp.mvp.ui.activity.binder;

import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by guc on 2019/10/30.
 * 描述：Binder服务端
 */
public class IMediaPlayerService extends Binder {
    public static final int CODE_PLAY = 1001;
    public static final int CODE_STOP = 1002;
    private static final String TAG = "IMediaPlayerService";

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case CODE_PLAY:
                data.enforceInterface("IMediaPlayerService");
                String path = data.readString();
                play(path);
                reply.writeString("执行完返回的结果" + path);
                break;
            case CODE_STOP:
                stop();
                break;
        }
        return super.onTransact(code, data, reply, flags);
    }

    public void play(String path) {
        Log.e(TAG, "play: " + path);
    }

    public void stop() {
        Log.e(TAG, "stop: ");
    }
}
