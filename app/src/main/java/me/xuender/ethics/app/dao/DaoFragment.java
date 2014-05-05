package me.xuender.ethics.app.dao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-5-5.
 */
public class DaoFragment extends Fragment {
    private String title;
    private View rootView;
    private int num;

    public DaoFragment(String title, int num) {
        this.title = title;
        this.num = num;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_dao, container, false);
            final TextView tv = (TextView) rootView.findViewById(R.id.dao);
            tv.setText(getString(num));
        }
        return rootView;
    }

    public String getTitle() {
        return title;
    }
}