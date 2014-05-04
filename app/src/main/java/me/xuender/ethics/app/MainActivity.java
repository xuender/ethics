package me.xuender.ethics.app;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import me.xuender.ethics.app.book.Book;
import me.xuender.ethics.app.book.BookActivity;
import me.xuender.ethics.app.call.CallActivity;
import me.xuender.ethics.app.five.FiveActivity;
import me.xuender.ethics.app.game.GameActivity;
import me.xuender.ethics.app.notes.YiActivity;
import me.xuender.ethics.app.notes.ZhiActivity;
import me.xuender.ethics.app.settings.SettingsActivity;
import me.xuender.ethics.app.sex.SexActivity;

/**
 * 首页
 * Created by ender on 14-4-27.
 */
public class MainActivity extends ActionBarActivity implements Button.OnClickListener {
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
        findViewById(R.id.call).setOnClickListener(this);
        sendVer();
    }

    private void sendVer() {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            EasyTracker.getInstance(this).send(MapBuilder
                    .createEvent("mainAction",     // Event category (required)
                            "ver",  // Event action (required)
                            packInfo.versionName,   // Event label
                            null).build());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        intent.setClass(this, SettingsActivity.class);
        startActivity(intent);
        return true;
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
            case R.id.call:
                runCall();
                break;
        }
    }

    private void runCall() {
        Intent intent = new Intent();
        intent.setClass(this, CallActivity.class);
        startActivity(intent);
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