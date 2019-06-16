package cn.edu.bzu.androidpracticum_xq.until;


import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;





public class Weather {

    JSONObject result;

    DingweiUntil dingweiUntil;
    //定位解析天气
    public JSONObject initData(String city) {


        // 创建客户端对象
        String url = "http://v.juhe.cn/weather/index?cityname=" + city + "&key=298d8853c0f623689fd2f8b66c335f9c";
        // String url = "http://v.juhe.cn/weather/index?cityname=滨州市&key=298d8853c0f623689fd2f8b66c335f9c";

        AsyncHttpClient client = new AsyncHttpClient();

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
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
        return result;
    }



}
