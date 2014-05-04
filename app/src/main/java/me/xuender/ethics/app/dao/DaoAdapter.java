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
    private static final int[] IDS = {R.array.d0, R.array.d1, R.array.d2, R.array.d3, R.array.d4,
            R.array.d5, R.array.d6, R.array.d7, R.array.d8, R.array.d9};

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