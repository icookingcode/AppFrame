package com.hnhy.epapp.mvp.ui.activity.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hnhy.epapp.R;
import com.hnhy.epapp.databinding.LifecycleBinding;
import com.hnhy.epapp.mvp.model.bean.User;
import com.hnhy.epapp.mvp.ui.activity.livedata.MyViewModel;
import com.hnhy.epapp.mvp.ui.activity.livedata.ViewModelProviders;

import java.util.Random;

/**
 * 处理生命周期
 * LifecycleOwner
 * LifecycleRegistry
 * LifecycleObserver
 */
public class ActivityLifecycleDemo extends AppCompatActivity implements LifecycleOwner, Observer<String> {
    private LifecycleRegistry mLifecycleRegistry;
    private MyViewModel myViewModel;
    private LifecycleBinding binding;
    private TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lifecycle_demo);
        tvName = findViewById(R.id.tv_name);
        binding.setTitle("处理生命周期");
        mLifecycleRegistry = new LifecycleRegistry(this);
//        myViewModel = new MyViewModel();
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        myViewModel.getCurrentName().observe(this, this);//liveData 绑定
        myViewModel.getCurrentUser().observe(this, (user) -> {
            binding.setUser(user);
        });//liveData 绑定
        mLifecycleRegistry.addObserver(new MyLifecycleObserver());
        new Handler().postDelayed(() -> {
            myViewModel.getCurrentName().postValue("Hello 谷超超" + new Random().nextInt());
            User user = new User();
            user.userName = "谷超超 - " + new Random().nextInt(100);
            myViewModel.getCurrentUser().postValue(user);
        }, 3000);
    }

    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    @Override
    public void onChanged(@Nullable String s) {
        binding.setName(s);
    }

}
