package com.hnhy.epapp.mvp.ui.activity.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.hnhy.epapp.R;
import com.hnhy.epapp.mvp.model.Api;
import com.hnhy.epapp.mvp.model.bean.City;
import com.hnhy.epapp.mvp.model.bean.Weather;
import com.hnhy.epapp.mvp.model.bean.WeatherData;
import com.hnhy.epapp.mvp.ui.widget.ToolBar;
import com.hnhy.epapp.system.MyRequestCallback;
import com.hnhy.framework.frame.BaseActivity;
import com.hnhy.framework.system.net.Response;
import com.hnhy.ui.ViewRichText;
import com.hnhy.ui.adapter.CommonRecycleAdapter;
import com.hnhy.ui.adapter.CommonViewHolder;
import com.hnhy.ui.dialogchooseinfo.MultiItemDivider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityWeatherReport extends BaseActivity {

    @BindView(R.id.toolbar)
    ToolBar mToolbar;
    @BindView(R.id.tv_city)
    TextView mTvCity;
    @BindView(R.id.vrt_1)
    ViewRichText mVrt1;
    @BindView(R.id.tv_tmp)
    ViewRichText mTvTmp;
    @BindView(R.id.tv_win_speed)
    ViewRichText mTvWinSpeed;
    @BindView(R.id.tv_update_time)
    ViewRichText mTvUpdateTime;
    @BindView(R.id.tv_air)
    ViewRichText mTvAir;
    @BindView(R.id.tv_air_tips)
    ViewRichText mTvAirTips;
    @BindView(R.id.rcv_hours)
    RecyclerView mRcvHours;

    private City mCity;

    public static void jump(Context context, City city) {
        Intent intent = new Intent(context, ActivityWeatherReport.class);
        intent.putExtra("city", city);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_report);
        ButterKnife.bind(this);
        mCity = getIntent().getParcelableExtra("city");
        if (mCity == null) return;
        getCityReport();
    }

    private void getCityReport() {
        Api.getInstance().getCityWeatherReport(this, getParams(), new MyRequestCallback<Weather>() {
            @Override
            public void onSuccess(Weather data, Response resp) {
                super.onSuccess(data, resp);
                loadWeatherData(data);
                showToast("天气获取成功");
            }

            @Override
            public void onFailure(Response resp) {
                super.onFailure(resp);
                showToast("天气获取失败");
            }
        });
    }

    private void loadWeatherData(Weather data) {
        if (data == null) return;
        mToolbar.setTitle(data.city + "-" + data.country);
        mTvCity.setText(data.city + "-" + data.country);
        mTvUpdateTime.setContent(data.update_time);
        if (data.data != null && data.data.size() > 0) {
            WeatherData weather = data.data.get(0);
            mVrt1.setContent(weather.day + " " + weather.week + " " + weather.wea);
            mTvTmp.setContent("当前温度：" + weather.tem + " 最高:" + weather.tem1 + " 最低:" + weather.tem2);
            mTvWinSpeed.setContent(weather.win + " " + weather.win_speed);
            mTvAir.setContent("空气质量/等级：" + weather.air + "/" + weather.air_level + " 湿度：" + weather.humidity);
            mTvAirTips.setContent(weather.air_tips);

            List<WeatherData.Hours> hours = weather.hours;
            loadHoursWeatherData(hours);
        }
    }

    private void loadHoursWeatherData(List<WeatherData.Hours> hours) {
        mRcvHours.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRcvHours.addItemDecoration(new MultiItemDivider(this, MultiItemDivider.HORIZONTAL_LIST, R.drawable.shape_divider_vtl));
        CommonRecycleAdapter<WeatherData.Hours> adapter = new CommonRecycleAdapter<WeatherData.Hours>(this, hours, R.layout.item_hour_weather) {
            @Override
            public void bindData(CommonViewHolder holder, WeatherData.Hours data, int position) {
                holder.setText(R.id.tv_content, data.toString());
            }
        };
        mRcvHours.setAdapter(adapter);
    }

    private Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("version", "v1");
        params.put("cityid", mCity.id);
        return params;
    }
}
