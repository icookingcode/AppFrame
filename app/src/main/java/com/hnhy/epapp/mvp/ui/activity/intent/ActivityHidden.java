package com.hnhy.epapp.mvp.ui.activity.intent;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hnhy.epapp.R;

public class ActivityHidden extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden);
        parseData();
    }

    private void parseData() {
        Uri data = getIntent().getData();
        if (data != null) {
            String scheme = data.getScheme();
            String host = data.getHost();
            int port = data.getPort();
            String path = data.getPath();
            String query = data.getQuery();
            String message = data.getQueryParameter("message");
            Log.e(getClass().getSimpleName(), "scheme:" + scheme);
            Log.e(getClass().getSimpleName(), "host:" + host);
            Log.e(getClass().getSimpleName(), "port:" + port);
            Log.e(getClass().getSimpleName(), "path:" + path);
            Log.e(getClass().getSimpleName(), "query:" + query);
        }
    }
}
