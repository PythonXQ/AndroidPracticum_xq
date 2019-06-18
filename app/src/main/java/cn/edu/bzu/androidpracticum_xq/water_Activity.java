package cn.edu.bzu.androidpracticum_xq;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpRequest;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class water_Activity extends AppCompatActivity {


    private String [] data = new String[]{ "安徽宿州杨庄" ,
            "四川广元清风峡" ,
            "河南永城黄口" ,
            "江苏连云港大兴桥" ,
            "安徽亳州颜集" ,
            "河南信阳淮滨水文站" ,
            "江苏泗洪大屈" ,
            "江苏徐州小红圈" ,
            "山东临沂涝沟桥" ,
            "安徽宿州泗县公路桥" ,
            "安徽阜阳徐庄" ,
            "安徽阜阳张大桥" ,
            "河南信阳蒋集水文站" ,
            "江苏徐州李集桥" ,
            "重庆朱沱" ,
            "安徽阜阳王家坝" ,
            "浙江湖州新塘港" ,
            "江苏盱眙" ,
            "江苏南京林山" ,
            "湖南岳阳城陵矶" ,
            "黑龙江肇源" ,
            "河南济源小浪底" ,
            "江苏邳苍" ,
            "安徽蚌埠蚌埠闸" ,
            "山西忻州万家寨" ,
            "湖南岳阳岳阳楼" ,
            "安徽淮北小王桥" ,
            "安徽合肥湖滨" ,
            "浙江杭州鸠坑口" ,
            "江苏苏州西山" ,
            "浙江嘉兴王江泾" ,
            "辽宁抚顺大伙房水库" ,
            "四川宜宾凉姜沟" ,
            "广西梧州界首" ,
            "安徽巢湖裕溪口" ,
            "江西九江蛤蟆石" ,
            "江苏无锡沙渚" ,
            "山东临沂清泉寺" ,
            "江苏扬州三江营" ,
            "宁夏吴忠新墩" ,
            "湖南长沙新港" ,
            "江苏宜兴兰山嘴" ,
            "四川泸州陀江二桥" ,
            "四川乐山岷江大桥" ,
            "内蒙古包头画匠营子" ,
            "广西南宁老口" ,
            "甘肃兰州新城桥" ,
            "山西运城河津大桥" ,
            "山东济南泺口" ,
            "湖北丹江口" ,
            "吉林长春松花江村" ,
            "河北张家口八号桥" ,
            "广西贵港石嘴" ,
            "广西凭祥平而关" ,
            "黑龙江同江" ,
            "吉林白城白沙滩" ,
            "福建福州白岩潭" ,
            "内蒙古乌海海渤湾" ,
            "河南周口沈丘闸" ,
            "江西南昌滁槎" ,
            "昆明观音山" ,
            "上海青浦急水港" ,
            "辽宁营口辽河公园" ,
            "河南驻马店班台" ,
            "河北石家庄港南水库" ,
            "北京门头沟沿河城" ,
            "四川攀枝花龙洞" ,
            "湖北宜昌南津关" ,
            "江西九江河西水厂" ,
            "山东枣庄台儿庄" ,
            "辽宁丹东鸭绿江桥" ,
            "辽宁辽阳汤河水库" ,
            "安徽界首七渡口" ,
            "黑龙江黑河" ,
            "山东聊城秤钩湾" ,
            "安徽淮南石头埠" ,
            "浙江嘉兴斜路港" ,
            "辽宁铁岭朱尔山" ,
            "安徽安庆皖河口" ,
            "吉林延边圈河" ,
            "河南周口鹿邑付桥闸" ,
            "河南南阳陶岔" ,
            "广东清远七星岗" ,
            "湖北武汉宗关" ,
            "昆明西苑隧道" ,
            "广东广州长州" ,
            "天津三岔口" ,
            "广西桂林阳朔" ,
            "辽宁盘锦兴安" ,
            "内蒙古呼伦贝尔黑山头" ,
            "黑龙江抚远乌苏镇" ,
            "云南红河州河口" ,
            "山东临沂重坊桥" ,
            "安徽滁州小柳巷" ,
            "北京密云古北口" ,
            "天津果河桥" ,
            "云南西双版纳橄榄坝" ,
            "广东中山横栏" ,
            "陕西渭南潼关吊桥" ,
            "辽河流域" ,
            "江苏扬州三江营" ,
            "湖北武汉宗关" ,
            "浙江杭州鸠坑口"};

    private int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_main);
    }

    public void fanhui(View view) {
        Intent intent3 = new Intent(this,Left_Activity.class);
        startActivity(intent3);
        overridePendingTransition(0, 0);
    }

    public void sousuowater(View view) {
        new AlertDialog.Builder(this)
                .setTitle("请选择检测点")
                .setIcon(R.drawable.suosuoda1)
                .setSingleChoiceItems(data, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        temp = i;
                        Toast.makeText(water_Activity.this,"你选择了"+data[i]+"监测点",Toast.LENGTH_LONG).show();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int a) {
                    water(data[temp]);
                    }
                })
                .setNegativeButton("取消",null)
                .show();

    }


    private void water(String city){

        LinearLayout linearLayout1 = findViewById(R.id.wat_lin);
        linearLayout1.setVisibility(View.GONE);
        LinearLayout linearLayout2 = findViewById(R.id.wat_lin2);

        String url = "http://web.juhe.cn:8080/environment/water/state?state="+city+"&key=606a62434e46199ac19ba2bd870eefdc";


        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(url,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               // super.onSuccess(statusCode, headers, response);


                try {
                    int code = response.getInt("resultcode");
                    if(code == 200){
                        JSONArray result = response.getJSONArray("result");
                        JSONObject re = (JSONObject) result.get(0);
                        Log.i("ca", String.valueOf(re));



                        TextView state = findViewById(R.id.w_state);
                        TextView ph = findViewById(R.id.w_ph);
                        TextView phquality = findViewById(R.id.w_phquality);
                        TextView oxygen = findViewById(R.id.w_oxygen);
                        TextView oxygenquality = findViewById(R.id.w_oxygenquality);
                        TextView nitrogen = findViewById(R.id.w_nitrogen);
                        TextView nitrogenquality = findViewById(R.id.w_nitrogenquality);
                        TextView permangan = findViewById(R.id.w_permangan);
                        TextView permanganquality = findViewById(R.id.w_permanganquality);
                        TextView section = findViewById(R.id.w_section);
                        TextView profile = findViewById(R.id.w_profile);
                        TextView belong = findViewById(R.id.w_belong);
                        TextView time = findViewById(R.id.w_time);





                        state.setText(	"监测站点："+re.getString("state"));
                        ph.setText(		"PH："+re.getString("ph"));
                        phquality.setText(	"PH水质类别："+re.getString("phquality"));
                        oxygen.setText(	"溶解氧："+re.getString("oxygen"));
                        oxygenquality.setText(	"溶解氧水质类别："+re.getString("oxygenquality"));
                        nitrogen.setText(	"氨氮："+re.getString("nitrogen"));
                        nitrogenquality.setText(	"氨氮水质类别："+re.getString("nitrogenquality"));
                        permangan.setText(	"高锰酸钾指数："+re.getString("permangan"));
                        permanganquality.setText("高锰酸钾指数水质类别："+re.getString("permanganquality"));
                        section.setText(	"断面属性："+re.getString("section"));
                        profile.setText(	"监测站点简介："+re.getString("profile"));
                        belong.setText(	"属于流域："+re.getString("belong"));
                        time.setText("更新时间："+re.getString("date")+"   "+re.getString("time"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        linearLayout2.setVisibility(View.VISIBLE);

    }
}
