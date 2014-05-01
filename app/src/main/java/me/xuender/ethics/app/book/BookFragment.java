package me.xuender.ethics.app.book;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-4-30.
 */
public class BookFragment extends Fragment {
    private JSONArray array;
    private String title;
    private View rootView;

    public BookFragment setArray(JSONArray array) {
        this.array = array;
        try {
            title = array.getJSONObject(0).getString("title");
        } catch (JSONException e) {
            Log.e("read", "error", e);
        }
        return this;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreateView", "start");
        Log.d("rootView", String.valueOf(rootView));
        if (rootView == null) {
            Log.d("onCreateView", "null");
            rootView = inflater.inflate(R.layout.fragment_book, container, false);
            try {
                LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.book);
                Log.d("读取", "three.json");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    TextView title = new TextView(rootView.getContext());
                    title.setText(obj.getString("title"));
                    title.setPadding(5, 3, 3, 3);
                    title.setTextAppearance(rootView.getContext(),
                            android.R.style.TextAppearance_Large);

                    if (obj.has("text")) {
                        TextView text = new TextView(rootView.getContext());
                        text.setText(obj.getString("text"));
                        text.setTextAppearance(rootView.getContext(),
                                android.R.style.TextAppearance_Small);
                        text.setPadding(5, 5, 5, 5);
                        LinearLayout line = new LinearLayout(rootView.getContext());
                        line.setOrientation(LinearLayout.HORIZONTAL);
                        line.addView(title);
                        line.addView(text);
                        ll.addView(line);
                    } else {
                        ll.addView(title);
                    }
                    if (obj.has("details")) {
                        JSONArray details = obj.getJSONArray("details");
                        for (int f = 0; f < details.length(); f++) {
                            JSONObject o = details.getJSONObject(f);
                            TextView dTitle = new TextView(rootView.getContext());
                            dTitle.setText(o.getString("title"));
                            dTitle.setPadding(30, 3, 3, 3);
                            dTitle.setTextAppearance(rootView.getContext(),
                                    android.R.style.TextAppearance_Medium);

                            TextView text = new TextView(rootView.getContext());
                            text.setText(o.getString("text"));
                            text.setTextAppearance(rootView.getContext(),
                                    android.R.style.TextAppearance_Small);
                            text.setPadding(5, 5, 5, 5);

                            LinearLayout line = new LinearLayout(rootView.getContext());
                            line.setOrientation(LinearLayout.HORIZONTAL);
                            line.addView(dTitle);
                            line.addView(text);
                            ll.addView(line);
                            //text.setPadding(5, 5, 5, 5);
                        }
                    }
                    if (obj.has("summary")) {
                        TextView summary = new TextView(rootView.getContext());
                        summary.setText(obj.getString("summary"));
                        summary.setTextAppearance(rootView.getContext(),
                                android.R.style.TextAppearance_Medium);
                        summary.setPadding(30, 3, 3, 3);
                        ll.addView(summary);
                    }
                }
            } catch (Exception e) {
                Log.e("read", "错误", e);
            }
        }
        return rootView;
    }
}