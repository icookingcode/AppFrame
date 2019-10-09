package com.hnhy.epapp.mvp.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.hnhy.epapp.R;
import com.hnhy.framework.logger.Logger;

/**
 * Created by guc on 2019/9/23.
 * 描述：
 */
public class MapView extends FrameLayout {
    private HorizontalScrollView mHScv;
    private NestedScrollView mScv;
    private Map4ZZ mMap;
    private int mWidth, mHeight;

    public MapView(@NonNull Context context) {
        this(context, null);
    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.view_map, this);
        initView();
    }

    private void initView() {
        mHScv = findViewById(R.id.h_scroll_view);
        mScv = findViewById(R.id.scroll_view);
        mMap = findViewById(R.id.map);
        this.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            mWidth = getWidth();
            mHeight = getHeight();
            Logger.e("MapView_addOnGlobalLayoutListener", "width:" + mWidth + " height:" + mHeight);
        });
    }

    /**
     * 待移动到的位置像素坐标
     *
     * @param x x px
     * @param y y px
     */
    public void moveToPosition(int x, int y) {
        x = (int) (x * mMap.getScale());
        y = (int) (y * mMap.getScale());
        x = x - mWidth / 2;
        y = y - mHeight / 2;
        mHScv.scrollTo(x, y);
        mScv.scrollTo(x, y);
    }
}
