package com.hnhy.epapp.mvp.ui.activity.livedata;

import android.arch.lifecycle.ViewModel;

import java.util.HashMap;

/**
 * Created by guc on 2019/10/14.
 * 描述：
 */
public class ViewModelProviders {
    private static ViewModelProviders mInstance;
    private HashMap<String, ViewModel> mViewModelPool;

    private ViewModelProviders() {
        mViewModelPool = new HashMap<>();
    }

    public static ViewModelProviders of(Object tag) {
        if (mInstance == null) {
            synchronized (ViewModelProviders.class) {
                mInstance = new ViewModelProviders();
            }
        }
        return mInstance;
    }

    public <T extends ViewModel> T get(Class<T> className) {
        if (className == null) {
            return null;
        }
        T model = (T) mViewModelPool.get(className.getName());
        if (model == null) {
            try {
                model = className.newInstance();
                mViewModelPool.put(className.getName(), model);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return model;
    }
}
