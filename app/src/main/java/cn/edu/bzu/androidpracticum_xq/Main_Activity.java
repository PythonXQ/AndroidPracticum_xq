package cn.edu.bzu.androidpracticum_xq;

import android.Manifest;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;

import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.bzu.androidpracticum_xq.entity.ResultBean;
import cn.edu.bzu.androidpracticum_xq.entity.WeatherToday;
import cn.edu.bzu.androidpracticum_xq.Controller.Liseview_SPQ;


public class Main_Activity extends AppCompatActivity {

    private View list, main, edit;//布局对象
    private ViewPager viewPager;
    private List<View> viewList;//存放布局
    private List<ResultBean> weatherlist;
    private List<ResultBean> weatherlist_edit;
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

    private TextView edit_temperature;
    private TextView edit_weather;
    private TextView edit_wind;
    private TextView edit_dressing_advice;
    private TextView edit_comfort_index;


    private TextView edit_temp;//当前温度
    private TextView edit_time;//更新时间
    private TextView edit_wind_direction;//风向
    private TextView edit_wind_strength;//风速
    private TextView edit_humidity;//湿度
    private TextView edit_city;
    private JSONObject result;



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mian);
        // map();//定位
        setPermission();//动态权限
        viewPager = findViewById(R.id.viewpage);
        //获取子布局
        LayoutInflater inflater = getLayoutInflater();
        main = inflater.inflate(R.layout.activity_main, null);
        list = inflater.inflate(R.layout.activity_future_list, null);
        edit = inflater.inflate(R.layout.activity_edit, null);


        ImageView imageView = findViewById(R.id.d_center);
        imageView.setBackgroundResource(R.drawable.tianqi1_1);
        ImageView imageView1 = findViewById(R.id.d_right);
        imageView1.setBackgroundResource(R.drawable.zixun);
        ImageView imageView2 = findViewById(R.id.d_left);
        imageView2.setBackgroundResource(R.drawable.qita1);

        //将子界面添加到List对象里
        viewList = new ArrayList<View>();
        viewList.add(edit);
        viewList.add(main);
        viewList.add(list);

        //PageView的适配器
        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {//获取子布局个数
                // TODO Auto-generated method stub
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position));
                if (position == 1) {
                    initD(); //初始化
                    map();//定位
                    shuaxin();//图片旋转
                }
                if (position == 2) {

                    ImageButton jz = list.findViewById(R.id.jz);
                    Animation anim = new RotateAnimation(0f, 2880f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    anim.setFillAfter(true); // 设置保持动画最后的状态
                    anim.setDuration(3000); // 设置动画时间
                    anim.setInterpolator(new AccelerateInterpolator()); // 设置插入器
                    anim.setFillAfter(true);// 设置旋转后停止
                    jz.startAnimation(anim);


                }
                return viewList.get(position);
            }
        };

        //调用适配器
        viewPager.setAdapter(pagerAdapter);
        //初始化显示界面
        viewPager.setCurrentItem(1);
    }

    //定位刷新按钮
    public void sx(View v) {
        map();//定位
        shuaxin();

    }


    public void left(View view) {

        Intent intent = new Intent(this, Left_Activity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    //导航栏中按钮
    public void center(View view) {
    }

    //导航栏右按钮
    public void right(View view) {

        Intent intent = new Intent(this, Right_Activity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    //搜索天气
    public void sousuo(View view) {
        initE();
        LinearLayout edit_lin = edit.findViewById(R.id.edit_lin);
        LinearLayout edit_lin1 = edit.findViewById(R.id.line1);
        LinearLayout edit_lin2 = edit.findViewById(R.id.line2);
        LinearLayout edit_add = edit.findViewById(R.id.linadd);
        TextView edit_text = edit.findViewById(R.id.edit_text);
        EditText edit_edit = edit.findViewById(R.id.edit_edit);
        String str = edit_edit.getText().toString();
        initDataEdit(str);
        edit_lin.setVisibility(View.GONE);
        edit_text.setText(str);
        edit_add.setVisibility(View.VISIBLE);
        edit_text.setVisibility(View.VISIBLE);
        edit_lin1.setVisibility(View.VISIBLE);
        edit_lin2.setVisibility(View.VISIBLE);
    }

    //添加城市
    public void tj(View view) {
        initE();
        LinearLayout edit_lin = edit.findViewById(R.id.edit_lin);
        LinearLayout edit_lin1 = edit.findViewById(R.id.line1);
        LinearLayout edit_lin2 = edit.findViewById(R.id.line2);
        LinearLayout edit_add = edit.findViewById(R.id.linadd);
        TextView edit_text = edit.findViewById(R.id.edit_text);
        EditText edit_edit = edit.findViewById(R.id.edit_edit);
        edit_edit.setText("");
        edit_add.setVisibility(View.GONE);
        edit_lin.setVisibility(View.VISIBLE);
        edit_text.setVisibility(View.INVISIBLE);
        edit_lin1.setVisibility(View.INVISIBLE);
        edit_lin2.setVisibility(View.INVISIBLE);

    }


    ////图片旋转
    private void shuaxin() {
        ImageButton sx = main.findViewById(R.id.sx);
        //图片旋转
        Animation anim = new RotateAnimation(0f, 2880f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true); // 设置保持动画最后的状态
        anim.setDuration(3000); // 设置动画时间
        anim.setInterpolator(new AccelerateInterpolator()); // 设置插入器
        anim.setFillAfter(true);// 设置旋转后停止
        sx.startAnimation(anim);
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

    //输入地址页面初始化控件
    private void initE() {

        edit_humidity = edit.findViewById(R.id.edit_humidity);
        edit_temp = edit.findViewById(R.id.edit_temp);
        edit_time = edit.findViewById(R.id.edit_time);
        edit_wind_direction = edit.findViewById(R.id.edit_wind_direction);
        edit_wind_strength = edit.findViewById(R.id.edit_wind_strength);

        edit_temperature = edit.findViewById(R.id.edit_temperature);
        edit_weather = edit.findViewById(R.id.edit_weather);
        edit_wind = edit.findViewById(R.id.edit_wind);
        edit_dressing_advice = edit.findViewById(R.id.edit_dressing_advice);
        edit_comfort_index = edit.findViewById(R.id.comfort_index);

    }

    //获取经纬度
    private void map() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            Location location;
            // location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                //location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Log.d("cs", "GPS定位为空现在使用网络定位");
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            Log.i("cs", "第一次获取" + String.valueOf(location.getLongitude()));
            Log.i("cs", "第一次获取" + String.valueOf(location.getLatitude()));
            MapData(location.getLongitude(), location.getLatitude());
        } catch (SecurityException e) {
            e.printStackTrace();
        }


        //实现实时更新天气状况
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;//位置权限判断
        }
        //实时更新地理位置，每隔20分钟一次
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1200000, 1, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("cs", "实时位置更新：" + String.valueOf(location.getLongitude()));
                Log.i("cs", "实时位置更新：" + String.valueOf(location.getLatitude()));
                MapData(location.getLongitude(), location.getLatitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        });
    }

    //逆地址解析
    private void MapData(double mp1, double mp2) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://apis.map.qq.com/ws/geocoder/v1/?location=" + mp2 + "," + mp1 + "&get_poi=1&key=BVFBZ-GONCF-E5ZJT-NMO5Y-SQR65-JDFCH";
        client.get(url, new JsonHttpResponseHandler() {
            //返回JSONObject对象|JSONOArray对象
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("cs", "定位地址发送成功");
                int status = 0;
                try {
                    status = response.getInt("status");
                    if (status == 0) {
                        JSONObject we = response.getJSONObject("result");
                        Log.i("cs", "当前位置：" + we.getString("address"));
                        JSONObject obj = we.getJSONObject("address_component");
                        Log.i("cs", "解析状态码：" + String.valueOf(status));
                        Log.i("cs", "解析信息" + String.valueOf(obj));
                        String city = obj.getString("city");
                        Log.i("cs", "当前城市：" + city);
                        text_city.setText(city);
                        initWeather(city);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    //定位解析天气
    private void initWeather(final String city) {
        // 创建客户端对象
        String url = "http://v.juhe.cn/weather/index?cityname=" + city + "&key=298d8853c0f623689fd2f8b66c335f9c";
        // String url = "http://v.juhe.cn/weather/index?cityname=滨州市&key=298d8853c0f623689fd2f8b66c335f9c";

        AsyncHttpClient client = new AsyncHttpClient();
        // Toast.makeText(this, "天气发送请求到服务器", Toast.LENGTH_LONG).show();
        client.get(url, new JsonHttpResponseHandler() {
            //返回JSONObject对象||JSONOArray对象
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // TODO Auto-generated method stub

                Log.i("cs", "天气信息" + String.valueOf(response));
                try {
                    Log.i("cs", "天气状态码：" + String.valueOf(response.getInt("resultcode")));
                    int resu = response.getInt("resultcode");
                    if (resu == 200) {
                        result = response.getJSONObject("result");
                        Log.i("cq1", String.valueOf(result));
                        Log.i("cq", String.valueOf(result));
                        //解析实时天气
                        JSONObject sk = result.getJSONObject("sk");
                        text_temp.setText(sk.getString("temp") + "°");
                        text_wind_direction.setText("风向：" + sk.getString("wind_direction"));
                        text_wind_strength.setText("风速：" + sk.getString("wind_strength"));
                        text_humidity.setText("湿度：" + sk.getString("humidity"));
                        text_time.setText(sk.getString("time") + " 更新");

                        //解析当天天气
                        JSONObject today = result.getJSONObject("today");
                        String strday = String.valueOf(today);
                        Log.i("cs", "today：" + strday);
                        Gson gson = new Gson();
                        WeatherToday weatherToday = gson.fromJson(strday, WeatherToday.class);

                        text_temperature.setText("今日温度：" + weatherToday.getTemperature());
                        text_weather.setText("今日天气：" + weatherToday.getWeather());
                        text_wind.setText("风向风速：" + weatherToday.getWind());
                        text_dressing_index.setText("穿衣指数：" + weatherToday.getDressing_index());
                        text_dressing_advice.setText("穿衣建议：" + weatherToday.getDressing_advice());
                        text_uv_index.setText("紫外线强度：" + weatherToday.getUv_index());
                        text_comfort_index.setText("舒适度指数：" + weatherToday.getComfort_index());
                        text_wash_index.setText("洗车指数：" + weatherToday.getWash_index());
                        text_travel_index.setText("旅游指数：" + weatherToday.getTravel_index());
                        text_exercise_index.setText("晨练指数：" + weatherToday.getExercise_index());
                        text_drying_index.setText("干燥指数：" + weatherToday.getDrying_index());

                        if (TextUtils.isEmpty(weatherToday.getDressing_advice())) {
                            text_dressing_advice.setVisibility(View.GONE);
                        }
                        if (TextUtils.isEmpty(weatherToday.getDressing_index())) {
                            text_dressing_index.setVisibility(View.GONE);
                        }
                        if (TextUtils.isEmpty(weatherToday.getUv_index())) {
                            text_uv_index.setVisibility(View.GONE);
                        }
                        if (TextUtils.isEmpty(weatherToday.getComfort_index())) {
                            text_comfort_index.setVisibility(View.GONE);
                        }
                        if (TextUtils.isEmpty(weatherToday.getWash_index())) {
                            text_wash_index.setVisibility(View.GONE);
                        }
                        if (TextUtils.isEmpty(weatherToday.getTravel_index())) {
                            text_travel_index.setVisibility(View.GONE);
                        }
                        if (TextUtils.isEmpty(weatherToday.getExercise_index())) {
                            text_exercise_index.setVisibility(View.GONE);
                        }
                        if (TextUtils.isEmpty(weatherToday.getDrying_index())) {
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
                        LinearLayout loading = list.findViewById(R.id.loading);
                        loading.setVerticalGravity(View.INVISIBLE);
                        lvNews.setAdapter(new Liseview_SPQ(weatherlist, Main_Activity.this));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //编辑解析天气
    private void initDataEdit(final String city) {
        String url = "http://v.juhe.cn/weather/index?cityname=" + city + "&key=298d8853c0f623689fd2f8b66c335f9c";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            //返回JSONObject对象||JSONOArray对象
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // TODO Auto-generated method stub
                try {
                    int resu = response.getInt("resultcode");
                    if (resu == 200) {
                        JSONObject result = response.getJSONObject("result");
                        //解析实时天气
                        JSONObject sk = result.getJSONObject("sk");
                        edit_temp.setText(sk.getString("temp") + "°");
                        edit_wind_direction.setText("风向：" + sk.getString("wind_direction"));
                        edit_wind_strength.setText("风速：" + sk.getString("wind_strength"));
                        edit_humidity.setText("湿度：" + sk.getString("humidity"));
                        edit_time.setText(sk.getString("time") + " 更新");
                        //解析当天天气
                        JSONObject today = result.getJSONObject("today");
                        String strday = String.valueOf(today);
                        Gson gson = new Gson();
                        WeatherToday weatherToday = gson.fromJson(strday, WeatherToday.class);
                        edit_temperature.setText("今日温度：" + weatherToday.getTemperature());
                        edit_weather.setText("今日天气：" + weatherToday.getWeather());
                        edit_wind.setText("风向风速：" + weatherToday.getWind());
                        edit_dressing_advice.setText("穿衣建议：" + weatherToday.getDressing_advice());
                        //因为有些数据的返回值为空，所以需要隐藏控件
                        if (TextUtils.isEmpty(weatherToday.getDressing_advice())) {
                            edit_dressing_advice.setVisibility(View.GONE);
                        }
                        //解析未来七天的天气
                        JSONObject future = result.getJSONObject("future");
                        DateFormat bf = new SimpleDateFormat("yyyyMMdd");
                        String format = bf.format(date);//格式化 bf.format(date);
                        System.out.println(format);
                        Log.i("cs", format);
                        int dateint = Integer.valueOf(format);
                        weatherlist_edit = new ArrayList<ResultBean>();
                        for (int i = 1; i <= 5; i++) {
                            dateint += 1;
                            String strdate = "day_" + dateint;
                            JSONObject a = future.getJSONObject(strdate);
                            String astr = String.valueOf(a);
                            ResultBean account = (ResultBean) new Gson().fromJson(astr, ResultBean.class);
                            weatherlist_edit.add(account);
                            ListView lvNews = edit.findViewById(R.id.lv_news);
                            lvNews.setAdapter(new Liseview_SPQ(weatherlist_edit, Main_Activity.this));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }


    //动态获取权限
    private void setPermission() {

        //访问网络
        if (ActivityCompat.checkSelfPermission(Main_Activity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Main_Activity.this, new String[]{Manifest.permission.INTERNET}, 66);
        }
        //<!-- 访问网络状态, 检测网络的可用性，需要网络运营商相关信息用于网络定位 -->
        if (ActivityCompat.checkSelfPermission(Main_Activity.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Main_Activity.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 66);
        }
        //<!-- 访问WiFi状态，需要WiFi信息用于网络定位 -->
        if (ActivityCompat.checkSelfPermission(Main_Activity.this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Main_Activity.this, new String[]{Manifest.permission.ACCESS_WIFI_STATE}, 66);
        }
        //<!-- 通过GPS得到精确位置 -->
        if (ActivityCompat.checkSelfPermission(Main_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Main_Activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 66);
        }
        //<!-- 通过网络得到粗略位置 -->
        if (ActivityCompat.checkSelfPermission(Main_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Main_Activity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 66);
        }
        //<!-- 访问手机当前状态, 需要某些信息用于网络定位 -->
        if (ActivityCompat.checkSelfPermission(Main_Activity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Main_Activity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 66);

        }
        //<!-- 访问网络的变化, 需要某些信息用于网络定位 -->
        if (ActivityCompat.checkSelfPermission(Main_Activity.this, Manifest.permission.CHANGE_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Main_Activity.this, new String[]{Manifest.permission.CHANGE_NETWORK_STATE}, 66);

        }
        //<!-- 修改WiFi状态，发起WiFi扫描, 需要WiFi信息用于网络定位 -->
        if (ActivityCompat.checkSelfPermission(Main_Activity.this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Main_Activity.this, new String[]{Manifest.permission.CHANGE_WIFI_STATE}, 66);

        }
    }



}
