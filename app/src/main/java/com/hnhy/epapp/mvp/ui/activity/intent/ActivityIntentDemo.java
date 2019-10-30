package com.hnhy.epapp.mvp.ui.activity.intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.hnhy.epapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityIntentDemo extends AppCompatActivity {
    private static final String ACTION = "com.hnhy.epapp.mvp.ui.activity.intent.ActivityHidden";

    @BindView(R.id.btn_open)
    Button mBtnOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_demo);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_open)
    public void onViewClicked() {
        startActivityInHidden();
    }

    private void startActivityInHidden() {
        Intent intent = new Intent();
        intent.setAction(ACTION);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }
}
