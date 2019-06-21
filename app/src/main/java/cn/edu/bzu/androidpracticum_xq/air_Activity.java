package cn.edu.bzu.androidpracticum_xq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class air_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_main);
    }

    public void sousuoair(View view) {

        LinearLayout linearLayout1 = findViewById(R.id.air_lin);
        LinearLayout linearLayout2 = findViewById(R.id.air_lin2);
        EditText editText = findViewById(R.id.air_edit);
        linearLayout1.setVisibility(View.GONE);

        String city = editText.getText().toString();
        init(city);
        linearLayout2.setVisibility(View.VISIBLE);
    }

    private void init(String city){
        String url = "http://web.juhe.cn:8080/environment/air/pm?city="+city+"&key=943d6c0b3fe85dc9fac60d146d7c16ae";
        AsyncHttpClient asyncHttpRequest = new AsyncHttpClient();
        asyncHttpRequest.get(url,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int code = response.getInt("resultcode");
                    if(code == 200){
                        JSONArray result = response.getJSONArray("result");
                        JSONObject re = (JSONObject) result.get(0);
                        Log.i("ca", String.valueOf(re));


                        TextView city = findViewById(R.id.air_city);
                        TextView PM25 = findViewById(R.id.air_PM25);
                        TextView AQI = findViewById(R.id.air_AQI);
                        TextView quality = findViewById(R.id.air_quality);
                        TextView PM10 = findViewById(R.id.air_PM10);
                        TextView CO = findViewById(R.id.air_CO);
                        TextView NO2 = findViewById(R.id.air_NO2);
                        TextView O3 = findViewById(R.id.air_O3);
                        TextView SO2 = findViewById(R.id.air_SO2);
                        TextView time = findViewById(R.id.air_time);




                        city.setText("城市："+re.getString("city"));
                        PM25.setText("PM2.5指数(ug/m3)："+re.getString("PM2.5"));
                        AQI.setText(  "空气质量指数："+re.getString("AQI"));
                        quality.setText("空气质量："+re.getString("quality"));
                        PM10.setText("PM10(ug/m3)："+re.getString("PM10"));
                        CO.setText("一氧化碳(mg/m3)："+re.getString("CO"));
                        NO2.setText("二氧化氮(ug/m3)："+re.getString("NO2"));
                        O3.setText(  "臭氧(ug/m3)："+re.getString("O3"));
                        SO2.setText("二氧化硫(ug/m3)："+re.getString("SO2"));
                        time.setText("更新时间："+re.getString("time"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }


    public void fanhui(View view) {
        Intent intent3 = new Intent(this,Left_Activity.class);
        startActivity(intent3);
        overridePendingTransition(0, 0);

    }
}
