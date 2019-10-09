package com.hnhy.epapp.mvp.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hnhy.epapp.util.AssersUtil;
import com.hnhy.framework.logger.Logger;

/**
 * Created by guc on 2019/9/23.
 * 描述：郑州地图
 */
public class Map4ZZ extends View {
    private Paint mPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private int mWidthMap, mHeightMap;
    private int mWidth, mHeight;
    private float mScale = 0.4f;

    public Map4ZZ(Context context) {
        this(context, null);
    }

    public Map4ZZ(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Map4ZZ(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    public float getScale() {
        return mScale;
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mBitmap = AssersUtil.getBitmapFromAssets(getContext(), "zz_map.png");
        if (mBitmap != null) {
            mWidthMap = mBitmap.getWidth();
            mHeightMap = mBitmap.getHeight();
            Logger.e("Map4ZZ_Bitmap", "width:" + mWidthMap + " height:" + mHeightMap);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = resolveSize((int) (mWidthMap * mScale), widthMeasureSpec);
        mHeight = resolveSize((int) (mHeightMap * mScale), widthMeasureSpec);
        Logger.e("Map4ZZ_onMeasure", "width:" + mWidth + " height:" + mHeight);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCanvas = canvas;
        if (mBitmap != null) {
//            mCanvas.drawBitmap(mBitmap,0,0,mPaint);
            Matrix matrix = new Matrix();
            matrix.setScale(mScale, mScale);
            mCanvas.drawBitmap(mBitmap, matrix, mPaint);
        }
    }
}
