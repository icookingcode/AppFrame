package com.hnhy.epapp.mvp.model.bean;

import java.util.List;

/**
 * Created by guc on 2019/7/25.
 * 描述：天气
 */
public class Weather {
    public String cityid;
    public String update_time;
    public String city;
    public String cityEn;
    public String country;
    public String countryEn;
    public List<WeatherData> data;

}
