package cn.edu.bzu.androidpracticum_xq.Controller;

import android.content.Context;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.bzu.androidpracticum_xq.Main_Activity;
import cn.edu.bzu.androidpracticum_xq.R;
import cn.edu.bzu.androidpracticum_xq.entity.ResultBean;
import cn.edu.bzu.androidpracticum_xq.entity.WeatherToday;
import cn.edu.bzu.androidpracticum_xq.until.DingweiUntil;
import cn.edu.bzu.androidpracticum_xq.until.Weather;

public class Mian {
    private List<Integer> backDra_list;
    private TextView text_temperature;
    private TextView text_weather;
    private TextView text_wind;
    private TextView text_dressing_index;
    private TextView text_dressing_advice;
    private TextView text_uv_index;
    private TextView text_comfort_index;
    private TextView text_wash_index;
    private TextView text_travel_index;
    private TextView text_exercise_index;
    private TextView text_drying_index;

    private TextView text_temp;//当前温度
    private TextView text_time;//更新时间
    private TextView text_wind_direction;//风向
    private TextView text_wind_strength;//风速
    private TextView text_humidity;//湿度
    private TextView text_city;
    private Date date = new Date();//创建时间
    private LocationManager locationManager;
    private LinearLayout back;
    private View main,list;
    private Context context;
    private double mp1,mp2;
    private List<ResultBean> weatherlist;

    private Weather weather;
    private DingweiUntil dingweiUntil;

    private JSONObject result;
    public Mian() {

        this.context = context;
        this.mp1 = mp1;
        this.mp2 = mp2;



    }

    public void Main(Context context,JSONObject result){
        main = View.inflate(context,R.layout.activity_main,null);
        getHour();
        initD();
        shuaxin();
        //解析实时天气
        back();
        JSONObject sk = null;
        try {
            sk = result.getJSONObject("sk");
            text_temp.setText(sk.getString("temp") + "°");
            text_wind_direction.setText("风向：" + sk.getString("wind_direction"));
            text_wind_strength.setText("风速：" + sk.getString("wind_strength"));
            text_humidity.setText("湿度：" + sk.getString("humidity"));
            text_time.setText(sk.getString("time")+" 更新");

            //解析当天天气
            JSONObject today = result.getJSONObject("today");
            String strday = String.valueOf(today);
            Log.i("cs","today："+strday);
            Gson gson = new Gson();
            WeatherToday weatherToday = gson.fromJson(strday, WeatherToday.class);

            text_temperature.setText("今日温度："+weatherToday.getTemperature());
            text_weather.setText("今日天气："+weatherToday.getWeather());
            text_wind.setText("风向风速："+weatherToday.getWind());
            text_dressing_index.setText("穿衣指数："+weatherToday.getDressing_index());
            text_dressing_advice.setText("穿衣建议："+weatherToday.getDressing_advice());
            text_uv_index.setText("紫外线强度："+weatherToday.getUv_index());
            text_comfort_index.setText("舒适度指数："+weatherToday.getComfort_index());
            text_wash_index.setText("洗车指数："+weatherToday.getWash_index());
            text_travel_index.setText("旅游指数："+weatherToday.getTravel_index());
            text_exercise_index.setText("晨练指数："+weatherToday.getExercise_index());
            text_drying_index.setText("干燥指数："+weatherToday.getDrying_index());

            if(TextUtils.isEmpty(weatherToday.getDressing_advice())){
                text_dressing_advice.setVisibility(View.GONE);
            }
            if(TextUtils.isEmpty(weatherToday.getDressing_index())){
                text_dressing_index.setVisibility(View.GONE);
            }
            if(TextUtils.isEmpty(weatherToday.getUv_index())){
                text_uv_index.setVisibility(View.GONE);
            }
            if(TextUtils.isEmpty(weatherToday.getComfort_index())){
                text_comfort_index.setVisibility(View.GONE);
            }
            if(TextUtils.isEmpty(weatherToday.getWash_index())){
                text_wash_index.setVisibility(View.GONE);
            }
            if(TextUtils.isEmpty(weatherToday.getTravel_index())){
                text_travel_index.setVisibility(View.GONE);
            }
            if(TextUtils.isEmpty(weatherToday.getExercise_index())){
                text_exercise_index.setVisibility(View.GONE);
            }
            if(TextUtils.isEmpty(weatherToday.getDrying_index())){
                text_drying_index.setVisibility(View.GONE);
            }
            //解析未来七天的天气
            JSONObject future = result.getJSONObject("future");
            DateFormat bf = new SimpleDateFormat("yyyyMMdd");
            String format = bf.format(date);//格式化 bf.format(date);
            System.out.println(format);
            Log.i("cs", format);

            int dateint = Integer.valueOf(format);

            weatherlist = new ArrayList<ResultBean>();
            for (int i = 1; i <= 6; i++) {
                dateint += 1;
                Log.i("cs", String.valueOf(dateint));
                String strdate = "day_" + dateint;
                Log.i("cs", strdate);
                JSONObject a = future.getJSONObject(strdate);
                Log.i("cs", String.valueOf(a));
                String astr = String.valueOf(a);
                ResultBean account = new Gson().fromJson(astr, ResultBean.class);

                Log.i("cs", account.toString());
                weatherlist.add(account);
            }

            ListView lvNews = list.findViewById(R.id.lv_weather);
            LinearLayout  loading = list.findViewById(R.id.loading);
            loading.setVerticalGravity(View.INVISIBLE);
            lvNews.setAdapter(new Liseview_SPQ(weatherlist,context));
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    //定位页面初始化控件
    public void initD() {
        back = main.findViewById(R.id.back);
        text_city = main.findViewById(R.id.city);
        text_humidity = main.findViewById(R.id.text_humidity);
        text_temp = main.findViewById(R.id.text_temp);
        text_time = main.findViewById(R.id.text_time);
        text_wind_direction = main.findViewById(R.id.text_wind_direction);
        text_wind_strength = main.findViewById(R.id.text_wind_strength);

        text_temperature = main.findViewById(R.id.text_temperature);
        text_weather = main.findViewById(R.id.text_weather);
        text_wind = main.findViewById(R.id.text_wind);
        text_dressing_index = main.findViewById(R.id.text_dressing_index);
        text_dressing_advice = main.findViewById(R.id.text_dressing_advice);
        text_uv_index = main.findViewById(R.id.text_uv_index);
        text_comfort_index = main.findViewById(R.id.comfort_index);
        text_wash_index = main.findViewById(R.id.text_wash_index);
        text_travel_index = main.findViewById(R.id.text_travel_index);
        text_exercise_index = main.findViewById(R.id.text_exercise_index);
        text_drying_index = main.findViewById(R.id.text_drying_index);

    }

    //随机定位改变背景
    private void getHour() {
        int x=1+(int)(Math.random()*14);
        Log.i("cs", String.valueOf(x));
        back.setBackgroundDrawable(ContextCompat.getDrawable(context, backDra_list.get(x-1)));
    }

        ////图片旋转
    private void shuaxin(){
        ImageButton sx = main.findViewById(R.id.sx);
        //图片旋转
        Animation anim =new RotateAnimation(0f, 2880f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true); // 设置保持动画最后的状态
        anim.setDuration(3000); // 设置动画时间
        anim.setInterpolator(new AccelerateInterpolator()); // 设置插入器
        anim.setFillAfter(true);// 设置旋转后停止
        sx.startAnimation(anim);
    }



    private void back(){
        //背景添加
        backDra_list = new ArrayList<Integer>();
        backDra_list.add(R.drawable.previe_default_themes_1);
        backDra_list.add(R.drawable.previe_default_themes_2);
        backDra_list.add(R.drawable.previe_default_themes_3);
        backDra_list.add(R.drawable.previe_default_themes_4);
        backDra_list.add(R.drawable.previe_default_themes_5);
        backDra_list.add(R.drawable.previe_default_themes_6);
        backDra_list.add(R.drawable.previe_default_themes_7);
        backDra_list.add(R.drawable.previe_default_themes_8);
        backDra_list.add(R.drawable.previe_default_themes_9);
        backDra_list.add(R.drawable.previe_default_themes_10);
        backDra_list.add(R.drawable.previe_default_themes_11);
        backDra_list.add(R.drawable.previe_default_themes_12);
        backDra_list.add(R.drawable.previe_default_themes_13);
        backDra_list.add(R.drawable.previe_default_themes_14);
    }


}
