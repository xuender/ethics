package me.xuender.ethics.app.call;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.preference.PreferenceManager;
import android.util.Log;

import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-5-3.
 */
public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("启动叫性了...", "..................");
        SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(context, intent.getIntExtra("sound", R.raw.bell), 1);
        soundPool.play(1, 1, 1, 0, 0, 1);
        soundPool.play(1, 1, 1, 0, 0, 1);
        soundPool.play(1, 1, 1, 0, 0, 1);
    }
}