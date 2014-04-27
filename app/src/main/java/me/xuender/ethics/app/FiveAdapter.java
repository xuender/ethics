package me.xuender.ethics.app;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

/**
 * Created by ender on 14-4-27.
 */
public class FiveAdapter extends FragmentPagerAdapter {
    private FiveFragment fiveFragment;
    private ChartFragment chartFragment;
    private Context context;

    public void clean() {
        getFiveFragment().clean();
    }

    public ChartFragment getChartFragment() {
        if (chartFragment == null) {
            chartFragment = new ChartFragment();
        }
        return chartFragment;
    }

    public FiveFragment getFiveFragment() {
        if (fiveFragment == null) {
            fiveFragment = new FiveFragment();
        }
        return fiveFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return getFiveFragment();
            case 1:
                return getChartFragment();
        }
        return null;
    }

    public FiveAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return context.getString(R.string.five).toUpperCase(l);
            case 1:
                return context.getString(R.string.repent).toUpperCase(l);
        }
        return null;
    }
}