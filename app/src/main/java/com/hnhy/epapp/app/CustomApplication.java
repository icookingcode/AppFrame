package com.hnhy.epapp.app;


import com.hnhy.epapp.BuildConfig;
import com.hnhy.framework.Configuration;
import com.hnhy.framework.Engine;
import com.hnhy.framework.frame.BaseApplication;
import com.hnhy.framework.frame.SystemManager;
import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by guc on 2019/7/15.
 * 描述：程序入口
 */
public class CustomApplication extends BaseApplication {

    //临时处理 待优化
    private String FILE_DIR_APK;
    private String FILE_DIR_LOG;

    public static final String DB_NAME = "hykj_app_data.db";


    @Override
    public void onCreate() {
        super.onCreate();
        init();
        Configuration configuration = new Configuration.Builder()
                .setModel(BuildConfig.MODEL)
                .setUrlApiRelease(Profile.URL_API_RELEASE)
                .setUrlApiBeta(Profile.URL_API_BETA)
                .setUrlApiDebug(Profile.URL_API_DEBUG)
                .setUrlUpgradeRelease(Profile.URL_GRADLE_RELEASE)
                .setUrlUpgradeDebug(Profile.URL_GRADLE_DEBUG)
                .setLogNativeDir(FILE_DIR_LOG)
                .setUpgradeApkDir(FILE_DIR_APK)
                .build();
        Engine.createInstance(this, configuration);
        SystemManager.getInstance();
        initX5();
    }

    private void initX5() {
        QbSdk.PreInitCallback callback = new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
//x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }

            @Override
            public void onViewInitFinished(boolean b) {

            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), callback);
    }

    private void init() {
        FILE_DIR_APK = getExternalFilesDir("apk").getAbsolutePath() + "/";
        FILE_DIR_LOG = getExternalCacheDir().getAbsolutePath() + "/";

    }
}
