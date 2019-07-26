package com.hnhy.epapp.mvp.model.bean;

import java.util.List;

/**
 * Created by guc on 2019/7/25.
 * 描述：天气详情
 */
public class WeatherData {
    public String day;
    public String date;
    public String date_nl;//农历
    public String week;
    public String wea;//天气
    public String air;//空气质量
    public String humidity;//湿度
    public String air_level;//空气质量等级
    public String air_tips;//提示语
    public Alarm alarm;
    public String tem;//当前温度
    public String tem1;//白天温度
    public String tem2;//晚上温度
    public List<String> win;//风向
    public String win_speed;//风速
    public List<Hours> hours;//小时预报
    public List<Index> index;//指示信息


    /**
     * 预警信息
     */
    public static class Alarm {
        public String alarm_type;//预警类型
        public String alarm_level;//预警等级
        public String alarm_content;//预警内容
    }

    /**
     * 小时预报
     * {
     * "day": "08日08时",
     * "wea": "晴",
     * "tem": "-3℃",
     * "win": "北风",
     * "win_speed": "5-6级"
     * }
     */
    public static class Hours {
        public String day;
        public String wea;
        public String tem;
        public String win;
        public String win_speed;

        @Override
        public String toString() {
            return day + "\n" + wea + "\n" + tem + "\n" + win + win_speed + "\n";
        }
    }

    /**
     * 指示信息
     * {
     * "title": "紫外线指数",
     * "level": "弱",
     * "desc": "辐射较弱，涂擦SPF12-15、PA+护肤品。"
     * }
     */
    public static class Index {
        public String title;
        public String level;
        public String desc;
    }
}


