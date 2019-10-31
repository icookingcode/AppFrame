package com.hnhy.epapp.mvp.ui.activity.binder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.hnhy.epapp.IMediaPlayerInterface;
import com.hnhy.epapp.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * binder客户端
 */
public class BinderDemoActivity extends AppCompatActivity {
    private static final String TAG = "BinderDemoActivity";
    private IBinder mRemote;
    private IMediaPlayerInterface mMediaPlayerBinder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemote = service;
            Log.e(TAG, "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected: ");
        }
    };
    private ServiceConnection conn2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected: " + 2);
            mMediaPlayerBinder = IMediaPlayerInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected: " + 2);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_demo);
        ButterKnife.bind(this);
        Intent intent = new Intent("com.hnhy.epapp.mvp.ui.activity.binder.MyService");
        intent.setPackage("com.hnhy.epapp");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        Intent intent2 = new Intent("com.hnhy.epapp.mvp.ui.activity.binder.MyAidlService");
        intent2.setPackage("com.hnhy.epapp");
        bindService(intent2, conn2, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        unbindService(conn2);
        super.onDestroy();
    }

    @OnClick({R.id.btn_play, R.id.btn_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                try {
                    play();
                    mMediaPlayerBinder.play("Aidl.play");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_stop:
                try {
                    stop();
                    mMediaPlayerBinder.stop();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void stop() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        mRemote.transact(IMediaPlayerService.CODE_STOP, data, reply, 0);
        reply.recycle();
        data.recycle();
    }

    private void play() throws RemoteException {
        String path = "/sdcard/media.xxx.mp4";
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        data.writeInterfaceToken("IMediaPlayerService");
        data.writeString(path);
        mRemote.transact(IMediaPlayerService.CODE_PLAY, data, reply, 0);
        IBinder binder = reply.readStrongBinder();
        String re = reply.readString();
        if (re != null)
            reply.recycle();
        data.recycle();
    }
}
