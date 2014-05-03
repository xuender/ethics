package me.xuender.ethics.app.call;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-5-3.
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class CallReceiver extends BroadcastReceiver implements SoundPool.OnLoadCompleteListener {
    private int number = 3;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("启动", "叫性");
        SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        soundPool.setOnLoadCompleteListener(this);
        soundPool.load(context, intent.getIntExtra(CallActivity.S_KEY, R.raw.bell), 1);
        number = intent.getIntExtra(CallActivity.N_KEY, 3);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        //播放 2+1 次
        soundPool.play(sampleId, 1, 1, 0, number - 1, 1);
    }
}