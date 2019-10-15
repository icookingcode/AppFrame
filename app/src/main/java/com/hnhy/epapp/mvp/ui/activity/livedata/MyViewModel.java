package com.hnhy.epapp.mvp.ui.activity.livedata;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.hnhy.epapp.mvp.model.bean.User;

/**
 * Created by guc on 2019/10/14.
 * 描述：LiveData
 * 1、Custom extends LiveData
 * 2、Activity or Fragment implements Observer
 * 3、observe(this,this);//liveData 绑定
 */
public class MyViewModel extends ViewModel {
    //创建一个String 的 LiveData
    private MutableLiveData<String> currentName;
    private MutableLiveData<User> currentUser;

    public MutableLiveData<String> getCurrentName() {
        if (currentName == null) {
            currentName = new MutableLiveData<String>();
        }
        return currentName;
    }

    public MutableLiveData<User> getCurrentUser() {
        if (currentUser == null) {
            currentUser = new MutableLiveData<User>();
        }
        return currentUser;
    }

}
