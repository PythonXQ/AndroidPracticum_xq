package cn.edu.bzu.androidpracticum_xq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Left_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_main);
    }

    //导航栏左按钮
    public void left(View view) {

    }
    //导航栏中按钮
    public void center(View view) {
        Intent intent = new Intent(this,Main_Activity.class);
        startActivity(intent);

    }
    //导航栏右按钮
    public void right(View view) {
        Intent intent = new Intent(this,Right_Activity.class);
        startActivity(intent);
    }
}
