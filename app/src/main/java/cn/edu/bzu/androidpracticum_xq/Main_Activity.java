package cn.edu.bzu.androidpracticum_xq;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Main_Activity extends AppCompatActivity{

    private View list,main,edit;//布局对象
    private ViewPager viewPager;
    private List<View> viewList;//存放布局
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mian);

        viewPager = findViewById(R.id.viewpage);
        //获取子布局
        LayoutInflater inflater = getLayoutInflater();
        main = inflater.inflate(R.layout.activity_main,null);
        list = inflater.inflate(R.layout.future_list,null);
        edit = inflater.inflate(R.layout.edit_list,null);

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


                return viewList.get(position);
            }
        };


        //调用适配器
        viewPager.setAdapter(pagerAdapter);


        viewPager.setCurrentItem(1);
    }


}
