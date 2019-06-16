package cn.edu.bzu.androidpracticum_xq.until;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class DingweiUntil {
    String city;

    //逆地址解析
    public String MapData(double mp1, double mp2) {
        // 创建客户端对象
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://apis.map.qq.com/ws/geocoder/v1/?location=" + mp2 + "," + mp1 + "&get_poi=1&key=BVFBZ-GONCF-E5ZJT-NMO5Y-SQR65-JDFCH";
        //Toast.makeText(this, "定位发送请求到服务器", Toast.LENGTH_LONG).show();
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
                        city = obj.getString("city");
                        Log.i("cs", "当前城市：" + city);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        return city;
    }
}
