package cn.edu.bzu.androidpracticum_xq.entity;

import org.json.JSONObject;

public class WeatherFuture {


    private String temperature;//": "28℃~36℃",
    private String weather;//": "晴转多云",
    private JSONObject weather_id;//": {

    private String wind;//": "南风3-4级",
    private String week;//": "星期一",
    private String date;//": "20140804"

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public JSONObject getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(JSONObject weather_id) {
        this.weather_id = weather_id;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
