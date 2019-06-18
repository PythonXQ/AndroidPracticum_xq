package cn.edu.bzu.androidpracticum_xq;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bzu.androidpracticum_xq.Controller.WebviewController;

public class alert_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_);

        main();
    }
    public void fanhui(View view) {
        Intent intent3 = new Intent(this,Left_Activity.class);
        startActivity(intent3);
        overridePendingTransition(0, 0);
    }




    public void main(){
        ViewPager viewParent = findViewById(R.id.alert_viewpage);
        final List<View> viewList = new ArrayList<>();
        viewList.add(new WebviewController(alert_Activity.this).getView("http://www.help.bj.cn/weather/warning.html?id=101060101"));

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

                return viewList.get(position);
            }
        };

        //调用适配器
        viewParent.setAdapter(pagerAdapter);
        //初始化显示界面
        viewParent.setCurrentItem(0);
    }
}
