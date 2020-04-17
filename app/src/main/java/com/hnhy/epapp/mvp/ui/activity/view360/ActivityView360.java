package com.hnhy.epapp.mvp.ui.activity.view360;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hnhy.epapp.R;
import com.hnhy.framework.frame.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityView360 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view360);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_opengl, R.id.btn_google_vr, R.id.btn_js_web})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_opengl:
                startActivity(new Intent(this, ActivityView360Opengl.class));
                break;
            case R.id.btn_google_vr:
                startActivity(new Intent(this, ActivityView360GoogleVR.class));
                break;
            case R.id.btn_js_web:
                startActivity(new Intent(this, ActivityView360JsWeb.class));
                break;
        }
    }
}
