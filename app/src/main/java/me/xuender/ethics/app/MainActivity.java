package me.xuender.ethics.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.analytics.tracking.android.EasyTracker;

import me.xuender.ethics.app.book.Book;
import me.xuender.ethics.app.book.BookActivity;
import me.xuender.ethics.app.five.FiveActivity;
import me.xuender.ethics.app.game.GameActivity;
import me.xuender.ethics.app.notes.YiActivity;
import me.xuender.ethics.app.notes.ZhiActivity;
import me.xuender.ethics.app.sex.SexActivity;

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
        findViewById(R.id.type_five).setOnClickListener(this);
        findViewById(R.id.five_ext).setOnClickListener(this);
        findViewById(R.id.type_four).setOnClickListener(this);
        findViewById(R.id.type_three).setOnClickListener(this);
        findViewById(R.id.game).setOnClickListener(this);
        findViewById(R.id.game_ext).setOnClickListener(this);
        findViewById(R.id.sex).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("id", String.valueOf(v.getId()));
        switch (v.getId()) {
            case R.id.five:
                runFive();
                break;
            case R.id.zhi:
                runZhi();
                break;
            case R.id.yi:
                runYi();
                break;
            case R.id.type_five:
                runBook(Book.五行);
                break;
            case R.id.type_four:
                runBook(Book.四大界);
                break;
            case R.id.type_three:
                runBook(Book.三界);
                break;
            case R.id.five_ext:
                runBook(Book.五行扩展);
                break;
            case R.id.game:
                runGame(false);
                break;
            case R.id.game_ext:
                runGame(true);
                break;
            case R.id.sex:
                runSex();
                break;
        }
    }

    private void runBook(Book book) {
        Intent intent = new Intent();
        intent.putExtra("title", book.name());
        intent.putExtra("file", book.getFile());
        intent.putExtra("icon", book.getIcon());
        intent.setClass(this, BookActivity.class);
        startActivity(intent);
    }

    private void runGame(boolean ext) {
        Intent intent = new Intent();
        intent.putExtra("ext", ext);
        intent.setClass(this, GameActivity.class);
        startActivity(intent);
    }

    private void runSex() {
        Intent intent = new Intent();
        intent.setClass(this, SexActivity.class);
        startActivity(intent);
    }

    private void runYi() {
        Intent intent = new Intent();
        intent.setClass(this, YiActivity.class);
        startActivity(intent);
    }

    private void runZhi() {
        Intent intent = new Intent();
        intent.setClass(this, ZhiActivity.class);
        startActivity(intent);
    }

    private void runFive() {
        Intent intent = new Intent();
        intent.setClass(this, FiveActivity.class);
        startActivity(intent);
    }
}