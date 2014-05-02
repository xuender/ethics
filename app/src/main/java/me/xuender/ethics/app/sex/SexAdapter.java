package me.xuender.ethics.app.sex;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.xuender.ethics.app.game.GameFragment;
import me.xuender.ethics.app.game.TopFragment;
import me.xuender.ethics.app.notes.Note;
import me.xuender.ethics.app.notes.NoteFragment;

/**
 * Created by ender on 14-5-1.
 */
public class SexAdapter extends FragmentPagerAdapter {
    private CalendarFragment calendarFragment;
    private BirthdayFragment birthdayFragment;

    public SexAdapter(FragmentManager fm) {
        super(fm);
        calendarFragment = new CalendarFragment();
        birthdayFragment = new BirthdayFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return calendarFragment;
            case 1:
                return birthdayFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
