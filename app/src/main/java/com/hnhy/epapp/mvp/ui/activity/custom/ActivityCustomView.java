package com.hnhy.epapp.mvp.ui.activity.custom;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.hnhy.epapp.R;
import com.hnhy.epapp.mvp.ui.widget.CircleView;
import com.hnhy.epapp.mvp.ui.widget.MapView;
import com.hnhy.epapp.mvp.ui.widget.MyPieChartView;
import com.hnhy.epapp.mvp.ui.widget.ToolBar;
import com.hnhy.epapp.mvp.ui.widget.coffee.CoffeeDrawable;
import com.hnhy.framework.frame.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by guc on 2019/9/20.
 * 描述：自定义view展示
 */
public class ActivityCustomView extends BaseActivity {
    @BindView(R.id.pie_view)
    MyPieChartView mPieView;
    @BindView(R.id.circle_view)
    CircleView mCircleView;
    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.toolbar)
    ToolBar mToolbar;
    @BindView(R.id.iv_coffee)
    ImageView mIvCoffee;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mToolbar.setOnRightClickedListener(() -> {
            startActivity(new Intent(this, ActivityCustomMap.class));
        });
        List<MyPieChartView.PieData> mPieDatas = new ArrayList<>();
        mPieDatas.add(new MyPieChartView.PieData(5, "考勤", getResources().getColor(R.color.colorScoreRed)));
        mPieDatas.add(new MyPieChartView.PieData(10, "基础信息", getResources().getColor(R.color.colorScoreOrange)));
        mPieDatas.add(new MyPieChartView.PieData(25, "治安防范", getResources().getColor(R.color.colorScoreYellow)));
        mPieDatas.add(new MyPieChartView.PieData(20, "案件办理", getResources().getColor(R.color.colorScoreGreenLight)));
        mPieDatas.add(new MyPieChartView.PieData(10, "隐患盘查", getResources().getColor(R.color.colorScoreGreen)));
        mPieView.setDatas(mPieDatas);

        mCircleView.setOnClickListener((CircleView.Position position) -> {
            switch (position) {
                case INNER:
                    showToast("点击了内圆");
                    break;
                case MIDDLE:
                    showToast("点击了中间圆");
                    break;
                case OUTER:
                    showToast("点击了外圆");
                    break;
            }
        });
        CoffeeDrawable drawable = CoffeeDrawable.create(mIvCoffee, 50);
        drawable.setProgress(1);
        new Handler().postDelayed(() -> {
            mMapView.moveToPosition(2000, 620);
            drawable.start();
        }, 1000);


    }

    @OnClick(R.id.iv_smile)
    public void onViewClicked(View view) {
        ImageView imageView = (ImageView) view;
        AnimatedVectorDrawable smileDrawable = (AnimatedVectorDrawable) imageView.getDrawable();
        smileDrawable.start();
    }
}
