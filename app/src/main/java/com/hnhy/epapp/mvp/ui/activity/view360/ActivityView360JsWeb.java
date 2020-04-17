package com.hnhy.epapp.mvp.ui.activity.view360;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.hnhy.epapp.R;
import com.hnhy.framework.frame.BaseActivity;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Three.js（利用前端姿势）WebView混合开发
 * 1.Three.js是JavaScript编写的WebGL第三方库。提供了非常多的3D显示功能。
 * 2.使用腾讯的X5内核的WebView
 */
public class ActivityView360JsWeb extends BaseActivity {
    @BindView(R.id.webview)
    WebView mWebview;
    private String url = "file:///android_asset/view.html";
    private String url2 = "https://www.baidu.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view360_js_web);
        ButterKnife.bind(this);
        initView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        mWebview.loadUrl(url);
        WebSettings webSettings = mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
    }
}
