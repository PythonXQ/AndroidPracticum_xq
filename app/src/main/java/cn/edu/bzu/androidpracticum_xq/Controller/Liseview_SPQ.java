package cn.edu.bzu.androidpracticum_xq.Controller;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;


import cn.edu.bzu.androidpracticum_xq.R;
import cn.edu.bzu.androidpracticum_xq.entity.ResultBean;

public class Liseview_SPQ extends BaseAdapter {

    private List<ResultBean> weatherlist;
    private Context context;
    private List<Integer> weatherDra_list;
    public Liseview_SPQ(List<ResultBean> weatherlist,Context context) {
        this.weatherlist = weatherlist;
        this.context = context;
        //天气图标添加
        weatherDra_list = new ArrayList<>();
        weatherDra_list.add(R.drawable.forecast_icon_clound);
        weatherDra_list.add(R.drawable.forecast_icon_daytimesnow);
        weatherDra_list.add(R.drawable.forecast_icon_dust);
        weatherDra_list.add(R.drawable.forecast_icon_fog);
        weatherDra_list.add(R.drawable.forecast_icon_freezing_rain);
        weatherDra_list.add(R.drawable.forecast_icon_hail);
        weatherDra_list.add(R.drawable.forecast_icon_haze);
        weatherDra_list.add(R.drawable.forecast_icon_heavyrain);
        weatherDra_list.add(R.drawable.forecast_icon_heavysnow);
        weatherDra_list.add(R.drawable.forecast_icon_moderaterain);
        weatherDra_list.add(R.drawable.forecast_icon_morderatesnow);
        weatherDra_list.add(R.drawable.forecast_icon_night);
        weatherDra_list.add(R.drawable.forecast_icon_nightcloundy);
        weatherDra_list.add(R.drawable.forecast_icon_nightshower);
        weatherDra_list.add(R.drawable.forecast_icon_nightsnow);
        weatherDra_list.add(R.drawable.forecast_icon_overcast);
        weatherDra_list.add(R.drawable.forecast_icon_partlyclound);
        weatherDra_list.add(R.drawable.forecast_icon_rain);
        weatherDra_list.add(R.drawable.forecast_icon_rainstorm);
        weatherDra_list.add(R.drawable.forecast_icon_sandstorm);
        weatherDra_list.add(R.drawable.forecast_icon_shower);
        weatherDra_list.add(R.drawable.forecast_icon_sleet);
        weatherDra_list.add(R.drawable.forecast_icon_snow);
        weatherDra_list.add(R.drawable.forecast_icon_snowstorm);
        weatherDra_list.add(R.drawable.forecast_icon_sunnny);
        weatherDra_list.add(R.drawable.forecast_icon_thundershower);

    }

    @Override
    public int getCount() {
        return weatherlist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context,R.layout.actity_list,null);
        ImageView img = view.findViewById(R.id.list_icon);

        TextView list_weather = view.findViewById(R.id.list_weather);
        TextView list_temperature = view.findViewById(R.id.list_temperature);
        TextView list_wind = view.findViewById(R.id.list_wind);
        TextView list_date = view.findViewById(R.id.list_date);
        TextView list_week = view.findViewById(R.id.list_week);

        ResultBean newsInfo = weatherlist.get(position);
        Log.i("cq",newsInfo.toString());
        img.setBackground(ContextCompat.getDrawable(context, weatherDra_list.get(position)));
        list_date.setText(newsInfo.getDate());
        list_temperature.setText(newsInfo.getTemperature());
        list_weather.setText(newsInfo.getWeather());
        list_wind.setText(newsInfo.getWind());
        list_week.setText(newsInfo.getWeek());

        return view;
    }
}
