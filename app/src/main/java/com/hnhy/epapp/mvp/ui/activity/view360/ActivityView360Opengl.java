package com.hnhy.epapp.mvp.ui.activity.view360;

import android.os.Bundle;

import com.hnhy.epapp.R;
import com.hnhy.framework.frame.BaseActivity;
import com.zph.glpanorama.GLPanorama;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * OpenGL ES  实现全景图功能
 */
public class ActivityView360Opengl extends BaseActivity {


    @BindView(R.id.gl_panorama)
    GLPanorama mGlPanorama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view360__opengl);
        ButterKnife.bind(this);
        mGlPanorama.setGLPanorama(R.drawable.imggugong);
    }
}
