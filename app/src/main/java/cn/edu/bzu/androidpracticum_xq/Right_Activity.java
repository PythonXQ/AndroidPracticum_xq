package cn.edu.bzu.androidpracticum_xq;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bzu.androidpracticum_xq.Controller.WebviewController;

public class Right_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_main);
        main();
    }
    //导航栏左按钮
    public void left(View view) {
        Intent intent = new Intent(this,Left_Activity.class);
        startActivity(intent);
    }
    //导航栏中按钮
    public void center(View view) {
        Intent intent = new Intent(this,Main_Activity.class);
        startActivity(intent);

    }
    //导航栏右按钮
    public void right(View view) {

    }


    public void main(){
        ViewPager viewParent = findViewById(R.id.webviewpage);
        final List<View> viewList = new ArrayList<>();
        viewList.add(new WebviewController(Right_Activity.this).getView("http://cms.hxky.cn/wap/jkxz/"));

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
