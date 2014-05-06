package me.xuender.ethics.app.notes;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.analytics.tracking.android.EasyTracker;

import me.xuender.ethics.app.K;
import me.xuender.ethics.app.R;

/**
 * 志纲
 * Created by ender on 14-4-27.
 */
public class ZhiActivity extends ActionBarActivity {
    private NoteFragment noteFragment;
    private static final String ZHI_KEY = "ZHI_ARRAY_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        noteFragment = new NoteFragment();
        Bundle bundle = new Bundle();
        bundle.putString(K.title.name(), getString(R.string.zhi_summary));
        bundle.putInt(K.input.name(), R.string.input);
        bundle.putString(K.key.name(), ZHI_KEY);
        bundle.putInt(K.color.name(), R.color.木);
        noteFragment.setArguments(bundle);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, noteFragment)
                    .commit();
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add:
                noteFragment.add();
                return true;
            case R.id.del:
                noteFragment.del();
                return true;
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}