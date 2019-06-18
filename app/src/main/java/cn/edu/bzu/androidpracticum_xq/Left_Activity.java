package cn.edu.bzu.androidpracticum_xq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Left_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_main);


        ImageView imageView = findViewById(R.id.d_center);
        imageView.setBackgroundResource(R.drawable.tianqi1);
        ImageView imageView1 = findViewById(R.id.d_right);
        imageView1.setBackgroundResource(R.drawable.zixun);
        ImageView imageView2 = findViewById(R.id.d_left);
        imageView2.setBackgroundResource(R.drawable.qita1_1);
    }

    //导航栏左按钮
    public void left(View view) {

    }
    //导航栏中按钮
    public void center(View view) {
        Intent intent = new Intent(this,Main_Activity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);


    }
    //导航栏右按钮
    public void right(View view) {
        Intent intent = new Intent(this,Right_Activity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);

    }

    public void shui(View view) {
        Intent intent1 = new Intent(Left_Activity.this,water_Activity.class);
        startActivity(intent1);

        overridePendingTransition(0, 0);
    }

    public void kongqi(View view) {
        Intent intent2 = new Intent(Left_Activity.this,air_Activity.class);
        startActivity(intent2);
        overridePendingTransition(0, 0);
    }

    public void yujing(View view) {
        Intent intent3 = new Intent(Left_Activity.this,alert_Activity.class);
        startActivity(intent3);
        overridePendingTransition(0, 0);
    }

    public void buttonnull(View view) {
    }
}
