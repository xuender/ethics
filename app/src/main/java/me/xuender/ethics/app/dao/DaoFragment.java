package me.xuender.ethics.app.dao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.xuender.ethics.app.K;
import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-5-5.
 */
public class DaoFragment extends Fragment {
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_dao, container, false);
            final TextView tv = (TextView) rootView.findViewById(R.id.dao);
            tv.setText(getArguments().getInt(K.num.name(), R.string.d0));
        }
        return rootView;
    }
}