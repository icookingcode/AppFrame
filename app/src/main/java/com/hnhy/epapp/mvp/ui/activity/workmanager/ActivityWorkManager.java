package com.hnhy.epapp.mvp.ui.activity.workmanager;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.hnhy.epapp.R;
import com.hnhy.epapp.app.Constants;
import com.hnhy.framework.frame.BaseActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * WorkManager使用
 */
public class ActivityWorkManager extends BaseActivity implements LifecycleOwner {
    private static final String TAG = "ActivityWorkManager";
    @BindView(R.id.tv_show)
    TextView mTvShow;
    private LifecycleRegistry mLifecycleRegistry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_manager);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mLifecycleRegistry = new LifecycleRegistry(this);
    }

    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    @OnClick(R.id.btn_start_worker)
    public void onViewClicked() {
        createWorker();
    }

    private void createWorker() {
        Data imageData = new Data.Builder()
                .putString(Constants.KEY_IMAGE_URI, Constants.IMAGE_URI)
                .build();
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setInitialDelay(10, TimeUnit.SECONDS)//延时启动
                .setInputData(imageData)//设置输入参数
                .addTag("request")//设置Tag
                .build();
        Log.e(TAG, "createWorker: " + oneTimeWorkRequest.getId());
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 3, TimeUnit.SECONDS).build();
        WorkManager.getInstance().enqueue(oneTimeWorkRequest);
        WorkManager.getInstance().getWorkInfosByTagLiveData("request").observe(this, (List<WorkInfo> infos) -> {
            if (infos != null) {
                for (WorkInfo workInfo : infos) {
                    if (workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                        Data data = workInfo.getOutputData();
                        mTvShow.setText("成功返回的数据 " + Constants.KEY_IMAGE_URI + ":" + data.getString(Constants.KEY_IMAGE_URI));
                        Log.e(TAG, "createWorker: " + infos.size() + workInfo.toString());
                        WorkManager.getInstance().cancelWorkById(workInfo.getId());
                    } else {
                        Log.e(TAG, "createWorker: " + infos.size() + workInfo.toString());
                    }
                }
            }

        });
    }
}
