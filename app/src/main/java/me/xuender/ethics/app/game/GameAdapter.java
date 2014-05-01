package me.xuender.ethics.app.game;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ender on 14-5-1.
 */
public class GameAdapter extends FragmentPagerAdapter {
    private GameFragment gameFragment;
    private TopFragment topFragment;

    public GameAdapter(FragmentManager fm) {
        super(fm);
        topFragment = new TopFragment();
        gameFragment = new GameFragment();
        gameFragment.setOnAddPoint(topFragment);
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
