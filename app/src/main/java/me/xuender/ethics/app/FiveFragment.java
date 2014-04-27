package me.xuender.ethics.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 五毒丸
 * Created by ender on 14-4-27.
 */
public class FiveFragment extends Fragment implements Button.OnClickListener {
    private List<ButtonItem> list = new ArrayList<ButtonItem>();
    private TextView textView;
    private SharedPreferences sp;
    private Set<String> dates;
    private View rootView;

    public void clean() {
        for (ButtonItem bi : list) {
            bi.clean();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_five, container, false);
            textView = (TextView) rootView.findViewById(R.id.textView);
            sp = getActivity().getSharedPreferences("count", Context.MODE_PRIVATE);
        }
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    void init() {
        list.clear();
        dates = sp.getStringSet("date", new HashSet<String>());
        list.add(new ButtonItem((Button) rootView.findViewById(R.id.怒), "怒", this, sp));
        list.add(new ButtonItem((Button) rootView.findViewById(R.id.恨), "恨", this, sp));
        list.add(new ButtonItem((Button) rootView.findViewById(R.id.怨), "怨", this, sp));
        list.add(new ButtonItem((Button) rootView.findViewById(R.id.恼), "恼", this, sp));
        list.add(new ButtonItem((Button) rootView.findViewById(R.id.烦), "烦", this, sp));
        list.add(new ButtonItem((Button) rootView.findViewById(R.id.忏悔), "忏悔", this, sp, true));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {
        int set = dates.size();
        Log.d("set size", String.valueOf(set));
//        SharedPreferences.Editor editor = sp.edit();
//        for (int i = 19; i < 26; i++) {
//            String d = "201404" + i;
//            for (String s : new String[]{"怒", "恨", "怨", "恼", "烦"}) {
//                editor.putInt(d + s, new Random().nextInt(6));
//            }
//            dates.add(d);
//        }
//        editor.putStringSet("date", dates);
//        editor.commit();
        for (ButtonItem bi : list) {
            if (bi.isButton(v)) {
                if (bi.click()) {
                    int count = 0;
                    for (ButtonItem i : list) {
                        count += i.count;
                    }
                    textView.setText(getResources().getText(R.string.fight).toString()
                            + (count * 10));
                } else {
                    dates.add(new SimpleDateFormat("yyyyMMdd").format(
                            new Date(System.currentTimeMillis())));
                    textView.setText(getResources().getText(R.string.title));
                }
                Log.d("点击", bi.button.getText().toString());
            }
        }
        Log.d("dates.size()", String.valueOf(dates.size()));
        if (set != dates.size()) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putStringSet("date", dates);
            editor.commit();
        }
    }
}