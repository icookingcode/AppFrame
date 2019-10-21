package com.hnhy.epapp.mvp.model.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.hnhy.epapp.BR;

/**
 * Created by guc on 2019/7/15.
 * 描述：用户类
 */
public class User extends BaseObservable {
    private String userName;

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.user);
    }
}
