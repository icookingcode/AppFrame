package com.hnhy.epapp.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hnhy.epapp.R;
import com.hnhy.framework.logger.Logger;

/**
 * Created by guc on 2019/9/20.
 * 描述：自定义圆形View
 * <p>
 * 自定义View步骤
 * 自定义属性的声明与获取
 * 重写测量阶段相关方法（onMeasure()）
 * 重写布局阶段相关方法（onLayout()（仅ViewGroup需要重写））
 * 重写绘制阶段相关方法（onDraw()绘制主体、dispatchDraw()绘制子View和onDrawForeground() 绘制前景）
 * onTouchEvent()
 * onInterceptTouchEvent()（仅ViewGroup有此方法）
 */
public class CircleView extends View {
    private final float mRadius;
    private final int mOuterCircleColor, mMiddleCircleColor, mInnerCircleColor;//外圆，中间，内圆颜色
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private Position mClickPostion;//点击位置
    private OnClickListener mClickListener;//点击监听
    private float mStartX, mStartY, mEndX, mEndY;
    private boolean isClickedEvent = false;//是否时点击事件

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //在 View 构造函数中获取自定义 View 属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mRadius = typedArray.getDimension(R.styleable.CircleView_circle_radius, getResources().getDimension(R.dimen.avatar_size));
        mOuterCircleColor = typedArray.getColor(R.styleable.CircleView_outer_circle_color, getResources().getColor(R.color.purple_500));
        mMiddleCircleColor = typedArray.getColor(R.styleable.CircleView_middle_circle_color, getResources().getColor(R.color.purple_500));
        mInnerCircleColor = typedArray.getColor(R.styleable.CircleView_inner_circle_color, getResources().getColor(R.color.purple_500));
        typedArray.recycle();
        initPaint();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.mClickListener = listener;
    }

    //由于不需要自定义View的尺寸，所以，不用重写该方法。
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //2.1 根据 View 特点或业务需求计算出 View 的尺寸
        mWidth = (int) (mRadius * 2);
        mHeight = (int) (mRadius * 2);

        //2.2 通过 resolveSize() 方法修正结果
        mWidth = resolveSize(mWidth, widthMeasureSpec);
        mHeight = resolveSize(mHeight, heightMeasureSpec);

        //2.3 通过 setMeasuredDimension() 保存 View 的期望尺寸（通过 setMeasuredDimension() 告知父 View 的期望尺寸）
        setMeasuredDimension(mWidth, mHeight);
    }
    //由于没有子View需要布局，所以，不用重写该方法。

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    //重写绘制阶段相关方法（onDraw()绘制主体、dispatchDraw()绘制子View和onDrawForeground()绘制前景）
    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(mOuterCircleColor);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        mPaint.setColor(mMiddleCircleColor);
        canvas.drawCircle(mRadius, mRadius, mRadius * 2 / 3, mPaint);
        mPaint.setColor(mInnerCircleColor);
        canvas.drawCircle(mRadius, mRadius, mRadius / 3, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
                isClickedEvent = true;
                mStartX = event.getX();
                mStartY = event.getY();
                getClickPostion(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE://移动
                mEndX = event.getX();
                mEndY = event.getY();
                float dltX = Math.abs(mEndX - mStartX);
                float dltY = Math.abs(mEndY - mStartY);
                Logger.e("MotionEvent.ACTION_MOVE", dltX + " " + dltY);
                if (dltX > 10 || dltY > 10) isClickedEvent = false;
                break;
            case MotionEvent.ACTION_UP://抬起
                if (isClickedEvent && mClickListener != null)
                    mClickListener.onClicked(mClickPostion);
                break;
        }
        return true;//自己处理触摸事件
    }

    /**
     * 获取点击位置
     */
    private void getClickPostion(float x, float y) {
        double distance2 = Math.pow(Math.abs(x - mRadius), 2) + Math.pow(Math.abs(y - mRadius), 2);
        double distance = Math.sqrt(distance2);
        if (distance < mRadius / 3) {
            mClickPostion = Position.INNER;
        } else if (distance >= mRadius / 3 && distance < mRadius * 2 / 3) {
            mClickPostion = Position.MIDDLE;
        } else {
            mClickPostion = Position.OUTER;
        }

    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
    }

    public enum Position {
        INNER, OUTER, MIDDLE
    }

    public interface OnClickListener {
        void onClicked(Position position);
    }
}
