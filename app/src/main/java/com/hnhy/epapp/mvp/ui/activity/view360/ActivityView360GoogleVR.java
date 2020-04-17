package com.hnhy.epapp.mvp.ui.activity.view360;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.hnhy.epapp.R;
import com.hnhy.framework.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * google vr 实现
 * Google VR主页:https://developers.google.com/vr/
 * Google VR for Android github地址：https://github.com/googlevr/gvr-android-sdk
 */
public class ActivityView360GoogleVR extends AppCompatActivity {

    @BindView(R.id.gvr_panorama)
    VrPanoramaView mGvrPanorama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_360_view_google_vr);
        ButterKnife.bind(this);

        initVrPanoramaView();
    }

    //初始化VR图片
    private void initVrPanoramaView() {
        VrPanoramaView.Options paNormalOptions = new VrPanoramaView.Options();
        paNormalOptions.inputType = VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
//      mVrPanoramaView.setFullscreenButtonEnabled (false); //隐藏全屏模式按钮
        mGvrPanorama.setInfoButtonEnabled(false); //设置隐藏最左边信息的按钮
        mGvrPanorama.setStereoModeButtonEnabled(false); //设置隐藏立体模型的按钮
        mGvrPanorama.setEventListener(new ActivityEventListener()); //设置监听
        //加载本地的图片源
        mGvrPanorama.loadImageFromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.andes), paNormalOptions);
        //设置网络图片源
//        panoWidgetView.loadImageFromByteArray();
    }

    private static class ActivityEventListener extends VrPanoramaEventListener {
        private static final String TAG = "ActivityEventListener";

        @Override
        public void onLoadSuccess() {//图片加载成功
            Logger.e(TAG, "onLoadSuccess");
        }


        @Override
        public void onLoadError(String errorMessage) {//图片加载失败
            Logger.e(TAG, "onLoadError:" + errorMessage);
        }

        @Override
        public void onClick() {//当我们点击了VrPanoramaView 时候触发            super.onClick();
        }

        @Override
        public void onDisplayModeChanged(int newDisplayMode) {
            super.onDisplayModeChanged(newDisplayMode);
        }
    }
}
