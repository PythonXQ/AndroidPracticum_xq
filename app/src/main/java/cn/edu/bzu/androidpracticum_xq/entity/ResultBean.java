package cn.edu.bzu.androidpracticum_xq.entity;


public class ResultBean {

    private String temperature;
    private String weather;
    private Weather_id weather_id;
    private String wind;
    private String week;
    private String date;


    public ResultBean(String temperature, String weather, Weather_id weather_id, String wind, String week, String date) {
        this.temperature = temperature;
        this.weather = weather;
        this.wind = wind;
        this.week = week;
        this.date = date;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "temperature='" + temperature + '\'' +
                ", weather='" + weather + '\'' +
                ", weather_id=" + weather_id.getFa() +
                ", weather_id=" + weather_id.getFb() +
                ", wind='" + wind + '\'' +
                ", week='" + week + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
    public String getTemperature() {
        return temperature;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
    public String getWeather() {
        return weather;
    }

    public void setWeather_id(Weather_id weather_id) {
        this.weather_id = weather_id;
    }
    public Weather_id getWeather_id() {
        return weather_id;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
    public String getWind() {
        return wind;
    }

    public void setWeek(String week) {
        this.week = week;
    }
    public String getWeek() {
        return week;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }

}
