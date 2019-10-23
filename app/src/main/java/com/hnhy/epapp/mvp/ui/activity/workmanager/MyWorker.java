package com.hnhy.epapp.mvp.ui.activity.workmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.hnhy.epapp.app.Constants;

/**
 * Created by guc on 2019/10/22.
 * 描述：
 */
public class MyWorker extends Worker {
    private static final String TAG = "MyWorker";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        // 获取输入参数
        String imageUriInput = getInputData().getString(Constants.KEY_IMAGE_URI);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Data outputData = new Data.Builder()
                .putString(Constants.KEY_IMAGE_URI, imageUriInput)
                .build();
        Log.e(TAG, "doWork" + imageUriInput);
        return Result.success(outputData);//返回输出数据
    }
}
