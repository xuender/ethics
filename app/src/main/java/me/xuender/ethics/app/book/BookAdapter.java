package me.xuender.ethics.app.book;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import me.xuender.ethics.app.K;

/**
 * Created by ender on 14-4-30.
 */
public class BookAdapter extends FragmentPagerAdapter {
    private List<BookFragment> fragments = new ArrayList<BookFragment>();
    private List<String> titles = new ArrayList<String>();

    public BookAdapter(FragmentManager fm, List<JSONArray> files) {
        super(fm);
        for (JSONArray file : files) {
            try {
                titles.add(file.getJSONObject(0).getString("title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            BookFragment bf = new BookFragment();
            Bundle bundle = new Bundle();
            bundle.putString(K.array.name(), file.toString());
            bf.setArguments(bundle);
            fragments.add(bf);
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
        return titles.get(position);
    }
}