package me.xuender.ethics.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends Activity implements Button.OnClickListener {
    private List<ButtonItem> list = new ArrayList<ButtonItem>();
    private TextView textView;
    private SharedPreferences sp;
    private Set<String> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        sp = this.getSharedPreferences("count", Context.MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    void init() {
        list.clear();
        dates = sp.getStringSet("date", new HashSet<String>());
        list.add(new ButtonItem((Button) findViewById(R.id.怒), "怒", this, sp));
        list.add(new ButtonItem((Button) findViewById(R.id.恨), "恨", this, sp));
        list.add(new ButtonItem((Button) findViewById(R.id.怨), "怨", this, sp));
        list.add(new ButtonItem((Button) findViewById(R.id.恼), "恼", this, sp));
        list.add(new ButtonItem((Button) findViewById(R.id.烦), "烦", this, sp));
        list.add(new ButtonItem((Button) findViewById(R.id.忏悔), "忏悔", this, sp, true));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clean) {
            for (ButtonItem bi : list) {
                bi.clean();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {
        int set = dates.size();
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
        if (set != dates.size()) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putStringSet("date", dates);
            editor.commit();
        }
    }
}