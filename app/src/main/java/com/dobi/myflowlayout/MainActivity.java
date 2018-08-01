package com.dobi.myflowlayout;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

        /*
        ScrollView scrollView = new ScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        for (int i=0;i<=50;i++){
            TextView textView = new TextView(this);

            //设置随机颜色
            Random random = new Random();
            int red = random.nextInt(200)+20;//防止得到0和255
            int green = random.nextInt(200)+20;
            int blue = random.nextInt(200)+20;
            int color = Color.rgb(red,green,blue);

            //给text设置背景为drawable
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setCornerRadius(10);
            gradientDrawable.setColor(color);


            textView.setText("test  " + i);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(28);
            textView.setPadding(8,5,8,5);
            textView.setBackground(gradientDrawable);
            layout.addView(textView,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        scrollView.addView(layout);

        setContentView(scrollView);
        */


        ScrollView scrollView = new ScrollView(this);
        FlowLayout layout = new FlowLayout(this);
       // layout.setOrientation(LinearLayout.VERTICAL);
        for (int i=0;i<=50;i++){
            TextView textView = new TextView(this);

            //设置随机颜色
            Random random = new Random();
            int red = random.nextInt(200)+20;//防止得到0和255
            int green = random.nextInt(200)+20;
            int blue = random.nextInt(200)+20;
            int color = Color.rgb(red,green,blue);

            //给text设置背景为drawable
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setCornerRadius(10);
            gradientDrawable.setColor(color);


            textView.setText("test  " + i);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(28);
            textView.setPadding(8,5,8,5);
            textView.setBackground(gradientDrawable);
            layout.addView(textView,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        scrollView.addView(layout);

        setContentView(scrollView);
    }



}
