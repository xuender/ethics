package me.xuender.ethics.app.dao;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-5-5.
 */
public class DaoAdapter extends FragmentPagerAdapter {
    private List<DaoFragment> fragments = new ArrayList<DaoFragment>();
    private static final int[] IDS = {R.string.d0, R.string.d1, R.string.d2, R.string.d3, R.string.d4,
            R.string.d5, R.string.d6, R.string.d7, R.string.d8, R.string.d9};

    public DaoAdapter(FragmentManager fm, String[] daos) {
        super(fm);
        for (int i = 0; i < daos.length; i++) {
            fragments.add(new DaoFragment(daos[i], IDS[i]));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }
}