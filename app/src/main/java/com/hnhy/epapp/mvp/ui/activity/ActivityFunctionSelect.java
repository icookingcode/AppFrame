package com.hnhy.epapp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hnhy.epapp.R;
import com.hnhy.epapp.mvp.ui.activity.bluetooth.ActivityBluetoothDevices;
import com.hnhy.epapp.mvp.ui.activity.custom.ActivityCustomView;
import com.hnhy.epapp.mvp.ui.activity.databind.ActivityDataBindDemo;
import com.hnhy.epapp.mvp.ui.activity.lifecycle.ActivityLifecycleDemo;
import com.hnhy.epapp.mvp.ui.activity.loader.ActivityLoaderDemo;
import com.hnhy.epapp.mvp.ui.activity.vpn.ActivityVpnDemo;
import com.hnhy.epapp.mvp.ui.activity.weather.ActivityCities;
import com.hnhy.framework.frame.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityFunctionSelect extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_select);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_weather_report, R.id.btn_bluetooth_usage, R.id.btn_custom_view, R.id.btn_vpn_demo, R.id.btn_fragment, R.id.btn_interact, R.id.btn_loader, R.id.btn_data_binding, R.id.btn_lifecycle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_weather_report:
                startActivity(new Intent(this, ActivityCities.class));
                break;
            case R.id.btn_bluetooth_usage:
                startActivity(new Intent(this, ActivityBluetoothDevices.class));
                break;
            case R.id.btn_custom_view:
                startActivity(new Intent(this, ActivityCustomView.class));
                break;
            case R.id.btn_vpn_demo:
                startActivity(new Intent(this, ActivityVpnDemo.class));
                break;
            case R.id.btn_fragment:
                startActivity(new Intent(this, ActivityFragmentDemo.class));
                break;
            case R.id.btn_interact:
                startActivity(new Intent(this, ActivityInteract.class));
                break;
            case R.id.btn_loader:
                startActivity(new Intent(this, ActivityLoaderDemo.class));
                break;
            case R.id.btn_data_binding:
                startActivity(new Intent(this, ActivityDataBindDemo.class));
                break;
            case R.id.btn_lifecycle:
                startActivity(new Intent(this, ActivityLifecycleDemo.class));
                break;
        }
    }

}
