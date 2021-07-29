package com.zzy.glide;

import android.os.Bundle;

import com.zzy.handler.R;

import androidx.appcompat.app.AppCompatActivity;

// 实现简单的Handler
// 模拟调用在测试类 ActivityThread 中
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}