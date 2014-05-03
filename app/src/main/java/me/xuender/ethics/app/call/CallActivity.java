package me.xuender.ethics.app.call;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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
public class CallActivity extends ActionBarActivity implements TimePicker.OnTimeChangedListener,
        CompoundButton.OnCheckedChangeListener {
    private SharedPreferences prefs;
    private TimePicker timePicker;
    private Switch aSwitch;
    private Spinner soundSpinner, numberSpinner;
    private boolean enabled;
    private int hour, minute, sound, number;
    private final static String H_KEY = "hour";
    private final static String M_KEY = "minute";
    final static String S_KEY = "playSound";
    private final static String E_KEY = "enabled";
    final static String N_KEY = "playNumber";

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        hour = prefs.getInt(H_KEY, 5);
        Log.d("hour", String.valueOf(hour));
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(hour);
        minute = prefs.getInt(M_KEY, 21);
        timePicker.setCurrentMinute(minute);
        timePicker.setOnTimeChangedListener(this);
        aSwitch = (Switch) findViewById(R.id.switch1);
        enabled = prefs.getBoolean(E_KEY, false);
        aSwitch.setChecked(enabled);
        aSwitch.setOnCheckedChangeListener(this);

        soundSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                new String[]{getString(R.string.s1), getString(R.string.s2)});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        soundSpinner.setAdapter(adapter);
        soundSpinner.setVisibility(View.VISIBLE);
        sound = prefs.getInt(S_KEY, 0);
        soundSpinner.setSelection(sound);
        soundSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sound = position;
                save();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        numberSpinner = (Spinner) findViewById(R.id.num);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                new String[]{getString(R.string.n1), getString(R.string.n2), getString(R.string.n3)});
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberSpinner.setAdapter(adapter2);
        numberSpinner.setVisibility(View.VISIBLE);
        number = prefs.getInt(N_KEY, 0);
        numberSpinner.setSelection(number);
        numberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                number = position;
                save();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        hour = hourOfDay;
        this.minute = minute;
        Log.d("H+M", hourOfDay + ":" + minute);
        save();
    }

    private static final int CALL_ID = 0;
    private static final int[] SOUNDS = {R.raw.bell, R.raw.play};

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        enabled = isChecked;
        save();
    }

    private void reg() {
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, CallReceiver.class);
        intent.setAction("me.xuender.ethics.app.call");
        // 取消定时
        PendingIntent pendIntent = PendingIntent.getBroadcast(this, CALL_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(pendIntent);
        if (enabled) {
            // 定时
            Log.d("SOUNDS sound", String.valueOf(sound));
            intent.putExtra(S_KEY, SOUNDS[sound]);
            intent.putExtra(N_KEY, (number + 1) * 3);
            Calendar c = Calendar.getInstance();
            c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minute);
            if (c.getTimeInMillis() < System.currentTimeMillis()) {
                c.add(Calendar.DATE, 1);
            }
            PendingIntent pi = PendingIntent.getBroadcast(this, CALL_ID, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 1000 * 60 * 60 * 24, pi);
            Log.d("System", String.valueOf(System.currentTimeMillis()));
            Log.d("getTimeInMillis", String.valueOf(c.getTimeInMillis()));
            Log.d("setup", "RTC_WAKEUP");
        }
    }

    private void save() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(E_KEY, enabled);
        if (enabled) {
            editor.putInt(H_KEY, hour);
            editor.putInt(M_KEY, minute);
            editor.putInt(S_KEY, sound);
            editor.putInt(N_KEY, number);
        }
        editor.commit();
        reg();
    }
}