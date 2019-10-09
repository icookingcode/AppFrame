package com.hnhy.epapp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hnhy.epapp.R;
import com.hnhy.epapp.mvp.ui.activity.bluetooth.ActivityBluetoothDevices;
import com.hnhy.epapp.mvp.ui.activity.custom.ActivityCustomView;
import com.hnhy.epapp.mvp.ui.activity.vpn.ActivityVpnDemo;
import com.hnhy.epapp.mvp.ui.activity.weather.ActivityCities;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityFunctionSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_select);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_weather_report, R.id.btn_bluetooth_usage, R.id.btn_custom_view, R.id.btn_vpn_demo})
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
        }
    }
}
