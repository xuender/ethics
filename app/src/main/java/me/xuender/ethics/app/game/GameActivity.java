package me.xuender.ethics.app.game;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.google.analytics.tracking.android.EasyTracker;

import me.xuender.ethics.app.EthicsApplication;
import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-5-1.
 */
public class GameActivity extends ActionBarActivity implements ActionBar.TabListener {
    private GameAdapter gameAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle bundle = this.getIntent().getExtras();
        boolean ext = bundle.getBoolean("ext");
        if (ext) {
            setTitle(R.string.game_ext);
        }
        gameAdapter = new GameAdapter(getSupportFragmentManager(), ext);
        ((EthicsApplication) getApplication()).putOnAddPoint(String.valueOf(ext),
                gameAdapter.getTopFragment());

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(gameAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        if (ext) {
            actionBar.addTab(actionBar.newTab().setText(R.string.game_ext).setTabListener(this));
        } else {
            actionBar.addTab(actionBar.newTab().setText(R.string.game).setTabListener(this));
        }
        actionBar.addTab(actionBar.newTab().setText(R.string.top).setTabListener(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
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
    public void onTabSelected(ActionBar.Tab tab,
                              android.support.v4.app.FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab,
                                android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab,
                                android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }
}
