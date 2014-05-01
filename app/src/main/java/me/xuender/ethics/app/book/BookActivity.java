package me.xuender.ethics.app.book;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.analytics.tracking.android.EasyTracker;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-4-29.
 */
public class BookActivity extends ActionBarActivity implements ActionBar.TabListener {
    private BookAdapter bookAdapter;
    private ViewPager viewPager;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        setContentView(R.layout.activity_book);
        this.setTitle(bundle.getString("title", "未知名称"));
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setLogo(getResources().getDrawable(bundle.getInt("icon", R.drawable.type_five)));
        String[] files = bundle.getString("file", "three.json").split(",");
        List<JSONArray> arrays = new ArrayList<JSONArray>();
        for (String file : files) {
            try {
                InputStream is = null;
                is = getAssets().open(file);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                JSONArray array = new JSONArray(new String(buffer, "UTF-8"));
                arrays.add(array);
            } catch (Exception e) {
                Log.e("read", "error", e);
            }
        }
        bookAdapter = new BookAdapter(getSupportFragmentManager(), arrays);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(bookAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        if (files.length > 1) {
            viewPager.setOffscreenPageLimit(5);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            for (int i = 0; i < bookAdapter.getCount(); i++) {
                actionBar.addTab(actionBar.newTab().setText(bookAdapter.getPageTitle(i))
                        .setTabListener(this));
            }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}