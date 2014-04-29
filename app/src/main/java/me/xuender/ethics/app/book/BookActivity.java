package me.xuender.ethics.app.book;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Iterator;

import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-4-29.
 */
public class BookActivity extends ActionBarActivity {
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        setContentView(R.layout.activity_book);
        this.setTitle(bundle.getString("title", "未知名称"));
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setLogo(getResources().getDrawable(bundle.getInt("icon", R.drawable.type_five)));
        LinearLayout ll = (LinearLayout) findViewById(R.id.book);
        try {
            InputStream is = getAssets().open(bundle.getString("file", "three.json"));
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            JSONArray array = new JSONArray(new String(buffer, "UTF-8"));
            Log.d("读取", "three.json");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Log.d("title", obj.getString("title"));
                TextView title = new TextView(this);
                title.setText(obj.getString("title"));
                title.setTextAppearance(this, android.R.style.TextAppearance_Large);
                if (obj.has("details")) {
                    ll.addView(title);
                    JSONArray details = obj.getJSONArray("details");
                    for (int f = 0; f < details.length(); f++) {
                        JSONObject o = details.getJSONObject(f);
                        TextView dTitle = new TextView(this);
                        dTitle.setText(o.getString("title"));
                        dTitle.setPadding(30, 3, 3, 3);
                        dTitle.setTextAppearance(this, android.R.style.TextAppearance_Medium);

                        TextView text = new TextView(this);
                        text.setText(o.getString("text"));
                        text.setTextAppearance(this, android.R.style.TextAppearance_Small);
                        text.setPadding(5, 5, 5, 5);

                        LinearLayout line = new LinearLayout(this);
                        line.setOrientation(LinearLayout.HORIZONTAL);
                        line.addView(dTitle);
                        line.addView(text);
                        ll.addView(line);
                        //text.setPadding(5, 5, 5, 5);
                    }
                } else {
                    TextView text = new TextView(this);
                    text.setText(obj.getString("text"));
                    text.setTextAppearance(this, android.R.style.TextAppearance_Small);
                    text.setPadding(5, 5, 5, 5);
                    LinearLayout line = new LinearLayout(this);
                    line.setOrientation(LinearLayout.HORIZONTAL);
                    line.addView(title);
                    line.addView(text);
                    ll.addView(line);
                }
                if (obj.has("summary")) {
                    TextView summary = new TextView(this);
                    summary.setText(obj.getString("summary"));
                    summary.setTextAppearance(this, android.R.style.TextAppearance_Medium);
                    summary.setPadding(30, 3, 3, 3);
                    ll.addView(summary);
                }
            }
        } catch (Exception e) {
            Log.e("read", "错误", e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }
}