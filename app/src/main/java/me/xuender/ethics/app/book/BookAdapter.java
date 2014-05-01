package me.xuender.ethics.app.book;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-4-30.
 */
public class BookAdapter extends FragmentPagerAdapter {
    private List<BookFragment> fragments = new ArrayList<BookFragment>();

    public BookAdapter(FragmentManager fm, List<JSONArray> files) {
        super(fm);
        for (JSONArray file : files) {
            fragments.add(new BookFragment().setArray(file));
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
