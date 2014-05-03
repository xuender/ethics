package me.xuender.ethics.app.call;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import com.google.analytics.tracking.android.EasyTracker;

import java.util.Calendar;
import java.util.TimeZone;

import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-5-3.
 */
public class CallActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener,
        TimePicker.OnTimeChangedListener, CompoundButton.OnCheckedChangeListener {
    private SharedPreferences prefs;
    private TimePicker timePicker;
    private Switch aSwitch;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private boolean enabled;
    private int hour, minute, select;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        hour = prefs.getInt("hour", 5);
        timePicker.setCurrentHour(hour);
        minute = prefs.getInt("minute", 21);
        timePicker.setCurrentMinute(minute);
        timePicker.setOnTimeChangedListener(this);
        aSwitch = (Switch) findViewById(R.id.switch1);
        enabled = prefs.getBoolean("enabled", false);
        aSwitch.setChecked(enabled);
        aSwitch.setOnCheckedChangeListener(this);
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                new String[]{"钟声", "佛号", "耳光"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setVisibility(View.VISIBLE);
        select = prefs.getInt("spinner", 0);
        spinner.setSelection(select);
        spinner.setOnItemSelectedListener(this);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        select = position;
        save();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        hour = hourOfDay;
        this.minute = minute;
        save();
    }

    private static final int CALL_ID = 0;
    private static final int[] SOUNDS = {R.raw.bell, R.raw.bell, R.raw.play};

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        enabled = isChecked;
        save();
    }

    private void reg() {
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent("me.xuender.ethics.app.call");
        intent.setClass(this, CallReceiver.class);
        // 取消定时
        PendingIntent pendIntent = PendingIntent.getBroadcast(this, CALL_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(pendIntent);
        if (enabled) {
            // 定时
            intent.putExtra("sound", SOUNDS[select]);
            Calendar c = Calendar.getInstance();
            c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            c.set(Calendar.HOUR, hour);
            c.set(Calendar.MINUTE, minute);
            if (c.getTimeInMillis() < System.currentTimeMillis()) {
                c.add(Calendar.DATE, 1);
            }
            PendingIntent pi = PendingIntent.getBroadcast(this, CALL_ID, intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
        }
    }

    private void save() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("enabled", enabled);
        if (enabled) {
            editor.putInt("hour", hour);
            editor.putInt("minute", this.minute);
            editor.putInt("spinner", select);
        }
        editor.commit();
        reg();
    }
}