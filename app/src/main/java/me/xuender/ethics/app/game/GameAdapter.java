package me.xuender.ethics.app.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.xuender.ethics.app.K;

/**
 * Created by ender on 14-5-1.
 */
public class GameAdapter extends FragmentPagerAdapter {
    private GameFragment gameFragment;
    private TopFragment topFragment;

    public TopFragment getTopFragment() {
        return topFragment;
    }

    public GameAdapter(FragmentManager fm, boolean ext) {
        super(fm);
        topFragment = new TopFragment();
        gameFragment = new GameFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(K.ext.name(), ext);
        gameFragment.setArguments(bundle);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return gameFragment;
            case 1:
                return topFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
