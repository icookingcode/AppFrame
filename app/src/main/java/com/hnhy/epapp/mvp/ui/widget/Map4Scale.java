package com.hnhy.epapp.mvp.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.hnhy.epapp.util.AssersUtil;
import com.hnhy.framework.logger.Logger;

/**
 * Created by guc on 2019/9/23.
 * 描述：可缩放地图
 */
public class Map4Scale extends View {
    private static int MAX_X, MAX_Y;
    private Paint mPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private int mWidthMap, mHeightMap;
    private int mWidth, mHeight;
    private float mScale = 1f;
    private float mStartX, mStartY, mEndX, mEndY, mPreX, mPreY;
    private int mDltX, mDltY;
    private int mCurrentX, mCurrentY;//地图左上角坐标
    private ScaleGestureDetector mScaleGestureDetector;

    public Map4Scale(Context context) {
        this(context, null);
    }

    public Map4Scale(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Map4Scale(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initScaleGestureDetector();
    }

    private void initScaleGestureDetector() {
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float factor = detector.getScaleFactor();
                //如果为nan或者无强大，则无效
                if (Float.isNaN(factor) || Float.isInfinite(factor)) {
                    return false;
                }
                Logger.e("Map4Scale_initScaleGestureDetector", "focusX :" + detector.getFocusX() + " focusY :" + detector.getFocusY() + "scale :" + detector.getScaleFactor());
                mDltX = (int) (mCurrentX * (1 - factor));
                mDltY = (int) (mCurrentY * (1 - factor));
                Logger.e("Map4Scale_changed", "mDltX :" + mDltX + " mDltY :" + mDltY + " mCurrentX:" + mCurrentX + " Y:" + mCurrentY);
                mScale *= detector.getScaleFactor();
                Logger.e("Map4Scale_currentScale", "currentScale :" + mScale);
                invalidate();
                return true;
            }
        });
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
            Logger.e("Map4Scale_Bitmap", "width:" + mWidthMap + " height:" + mHeightMap);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = widthSize < dp2px(250) ? dp2px(250) : widthSize;
        mHeight = heightSize < dp2px(200) ? dp2px(200) : heightSize;
        Logger.e("Map4Scale_onMeasure", "width:" + mWidth + " height:" + mHeight);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCanvas = canvas;
        if (mBitmap != null) {
            Matrix matrix = new Matrix();
            matrix.setScale(mScale, mScale);
            MAX_X = (int) (mWidthMap * mScale - mWidth);
            MAX_Y = (int) (mHeightMap * mScale - mHeight);
            if (MAX_X < 0) MAX_X = 0;
            if (MAX_Y < 0) MAX_Y = 0;
            mCanvas.drawBitmap(mBitmap, matrix, mPaint);
            mCurrentX -= mDltX;
            mCurrentY -= mDltY;
            if (mCurrentX < 0) {//禁止越界
                mDltX = 0;
                mCurrentX = 0;
            }
            if (mCurrentY < 0) {//禁止越界
                mDltY = 0;
                mCurrentY = 0;
            }
            if (mCurrentX > MAX_X) {//禁止越界
                mDltX = 0;
                mCurrentX = MAX_X;
            }
            if (mCurrentY > MAX_Y) {//禁止越界
                mDltY = 0;
                mCurrentY = MAX_Y;
            }
            this.scrollBy(-mDltX, -mDltY);
            if (mCurrentX == MAX_X) this.scrollTo(MAX_X, mCurrentY);
            if (mCurrentY == MAX_Y) this.scrollTo(mCurrentX, MAX_Y);
            if (mCurrentX == 0) this.scrollTo(0, mCurrentY);
            if (mCurrentY == 0) this.scrollTo(mCurrentX, 0);
            mDltX = 0;
            mDltY = 0;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            mScaleGestureDetector.onTouchEvent(event);
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
                mStartX = event.getX();
                mStartY = event.getY();
                mPreX = mStartX;
                mPreY = mStartY;
                break;
            case MotionEvent.ACTION_MOVE://移动
                mEndX = event.getX();
                mEndY = event.getY();
                mDltX = (int) (mEndX - mPreX);
                mDltY = (int) (mEndY - mPreY);
                mPreX = mEndX;
                mPreY = mEndY;
                Logger.e("Map4Scale_MotionEvent.ACTION_MOVE", mDltX + " " + mDltY);
                if (Math.abs(mDltX) > 5 || Math.abs(mDltY) > 5)
                    this.invalidate();
                break;
            case MotionEvent.ACTION_UP://抬起
                break;
        }
        return true;//自己处理触摸事件
    }

    private int dp2px(final float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
