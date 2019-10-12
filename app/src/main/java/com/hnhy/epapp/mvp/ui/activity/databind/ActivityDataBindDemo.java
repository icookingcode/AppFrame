package com.hnhy.epapp.mvp.ui.activity.databind;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hnhy.epapp.R;
import com.hnhy.epapp.databinding.ActivityDataBindDemoBinding;
import com.hnhy.epapp.util.AssersUtil;

public class ActivityDataBindDemo extends AppCompatActivity {
    private AdapterCityDataBinding mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindDemoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_bind_demo);
//        User user = new User();
//        user.userName = "谷超超";
//        binding.setUser(user);
        initRcv();
    }

    private void initRcv() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AdapterCityDataBinding(AssersUtil.getCities(this), this);
        recyclerView.setAdapter(mAdapter);
    }
}
