package me.xuender.ethics.app.dao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.xuender.ethics.app.K;
import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-5-5.
 */
public class DaoAdapter extends FragmentPagerAdapter {
    private List<DaoFragment> fragments = new ArrayList<DaoFragment>();
    private static final int[] IDS = {R.string.d0, R.string.d1, R.string.d2, R.string.d3, R.string.d4,
            R.string.d5, R.string.d6, R.string.d7, R.string.d8, R.string.d9};
    private String[] daos;

    public DaoAdapter(FragmentManager fm, String[] daos) {
        super(fm);
        this.daos = daos;
        for (int i = 0; i < daos.length; i++) {
            DaoFragment df = new DaoFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(K.num.name(), IDS[i]);
            df.setArguments(bundle);
            fragments.add(df);
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
        return daos[position];
    }
}