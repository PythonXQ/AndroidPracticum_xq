package cn.edu.bzu.androidpracticum_xq;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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


public class MainActivity extends AppCompatActivity {


    private TextView text_city;
    private Date date = new Date();//创建时间
    private LocationManager locationManager;
    private LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sss


        //hideActionBar();
       // setFullScreen();

        //sss

        init(); //初始化
        setPermission();//动态权限
       // map();//定位
        //initData("滨州市");
        getHour();//根据时间换背景
    }



    /**
     * hide action bar
     */
    private void hideActionBar() {
        // Hide UI
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    /**
     * set the activity display in full screen
     */
    private void setFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }



    private void getHour() {
        if (date.getHours() > 6 && date.getHours() < 18) {
            back.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.previe_default_themes_2));
        } else {
            back.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.previe_default_themes_9));
        }
    }

    //初始化控件
    public void init() {

        back = findViewById(R.id.back);
        text_city = findViewById(R.id.city);

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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
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
        // 创建客户端对象
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://apis.map.qq.com/ws/geocoder/v1/?location=" + mp2 + "," + mp1 + "&get_poi=1&key=BVFBZ-GONCF-E5ZJT-NMO5Y-SQR65-JDFCH";
        Toast.makeText(this, "定位发送请求到服务器", Toast.LENGTH_LONG).show();
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
                        initData(city);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        });
    }


    //解析天气
    private void initData(final String city) {
        // 创建客户端对象
        String url = "http://v.juhe.cn/weather/index?cityname=" + city + "&key=298d8853c0f623689fd2f8b66c335f9c";
        // String url = "http://v.juhe.cn/weather/index?cityname=滨州市&key=298d8853c0f623689fd2f8b66c335f9c";

        AsyncHttpClient client = new AsyncHttpClient();
        Toast.makeText(this, "天气发送请求到服务器", Toast.LENGTH_LONG).show();
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
                        JSONObject result = response.getJSONObject("result");
                        JSONObject sk = result.getJSONObject("sk");
//                        text1.setText("温度：" + sk.getString("temp") + "℃");
//                        text2.setText("风向：" + sk.getString("wind_direction"));
//                        text3.setText("风速：" + sk.getString("wind_strength"));
//                        text4.setText("湿度：" + sk.getString("humidity"));
//                        text5.setText("时间：" + sk.getString("time"));

                        //解析未来七天的天气
                        JSONObject future = result.getJSONObject("future");
                        DateFormat bf = new SimpleDateFormat("yyyyMMdd");
                        String format = bf.format(date);//格式化 bf.format(date);
                        System.out.println(format);
                        Log.i("cs", format);

                        int dateint = Integer.valueOf(format);

                        List<ResultBean> weatherlist = new ArrayList<ResultBean>();
                        for (int i = 1; i <= 6; i++) {
                            dateint += 1;
                            Log.i("cs", String.valueOf(dateint));
                            String strdate = "day_" + dateint;
                            Log.i("cs", strdate);
                            JSONObject a = future.getJSONObject(strdate);
                            Log.i("cs", String.valueOf(a));
                            String astr = String.valueOf(a);
                            ResultBean account = (ResultBean) new Gson().fromJson(astr, ResultBean.class);

                            Log.i("cs", account.toString());
                            weatherlist.add(account);


                        }

                        for (int i = 0; i < weatherlist.size(); i++) {
                            Log.i("cs", String.valueOf(weatherlist.get(i)));
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
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 66);
        }
        //<!-- 访问网络状态, 检测网络的可用性，需要网络运营商相关信息用于网络定位 -->
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 66);
        }
        //<!-- 访问WiFi状态，需要WiFi信息用于网络定位 -->
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_WIFI_STATE}, 66);
        }
        //<!-- 通过GPS得到精确位置 -->
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 66);
        }
        //<!-- 通过网络得到粗略位置 -->
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 66);
        }
        //<!-- 访问手机当前状态, 需要某些信息用于网络定位 -->
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 66);

        }
        //<!-- 访问网络的变化, 需要某些信息用于网络定位 -->
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CHANGE_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CHANGE_NETWORK_STATE}, 66);

        }
        //<!-- 修改WiFi状态，发起WiFi扫描, 需要WiFi信息用于网络定位 -->
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CHANGE_WIFI_STATE}, 66);

        }
    }


}

