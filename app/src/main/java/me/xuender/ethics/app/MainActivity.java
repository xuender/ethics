package me.xuender.ethics.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import me.xuender.ethics.app.five.FiveActivity;
import me.xuender.ethics.app.notes.YiActivity;
import me.xuender.ethics.app.notes.ZhiActivity;

/**
 * 首页
 * Created by ender on 14-4-27.
 */
public class MainActivity extends Activity implements Button.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.five).setOnClickListener(this);
        findViewById(R.id.zhi).setOnClickListener(this);
        findViewById(R.id.yi).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        Log.d("id", String.valueOf(v.getId()));
        switch (v.getId()) {
            case R.id.five:
                intent.setClass(this, FiveActivity.class);
                startActivity(intent);
                break;
            case R.id.zhi:
                intent.setClass(this, ZhiActivity.class);
                startActivity(intent);
                break;
            case R.id.yi:
                intent.setClass(this, YiActivity.class);
                startActivity(intent);
                break;
        }
    }
}