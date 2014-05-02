package me.xuender.ethics.app.sex;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-5-1.
 */
public class SexActivity extends ActionBarActivity {
    private LinearLayout layout;
    private final static int MONTH_NUM = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        layout = (LinearLayout) findViewById(R.id.layout);
        int start = 0;
        List<Day> days = getDays();
        LinearLayout row = null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        for (int m = 0; m < MONTH_NUM; m++) {
            if (row != null) {
                while (row.getChildCount() < 7) {
                    addView(row, true);
                }
            }
            TextView month = new TextView(this);
            month.setTextAppearance(this, android.R.style.TextAppearance_Medium);
            month.setGravity(Gravity.CENTER);
            month.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            month.setText(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1));
            layout.addView(month);
            int endDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int d = 0; d < endDay; d++) {
                if (d == 0 || calendar.get(Calendar.DAY_OF_WEEK) == 2) {
                    if (row != null) {
                        while (row.getChildCount() < 7) {
                            addView(row, false);
                        }
                    }
                    row = new LinearLayout(this);
                    row.setOrientation(LinearLayout.HORIZONTAL);
                    row.setWeightSum(7);
                    row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    row.setPadding(0, 5, 0, 5);
                    layout.addView(row);
                }
                TextView textView = new TextView(this);
                textView.setTextAppearance(this, android.R.style.TextAppearance_Small);
                Day day = days.remove(0);
                StringBuilder sb = new StringBuilder();
                sb.append(calendar.get(Calendar.DATE)).append('\n');
                sb.append(day.getTitle()).append('\n');
                sb.append(day.getGala());
                textView.setText(sb.toString());
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                if (day.isNo()) {
                    textView.setBackgroundColor(getResources().getColor(R.color.火));
                }
                if (day.isNow()) {
                    textView.setTextColor(getResources().getColor(R.color.木));
                }
                row.addView(textView);
                calendar.add(Calendar.DATE, 1);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }

    private List<Day> getDays() {
        List<Day> days = new ArrayList<Day>();
        Calendar calendar = Calendar.getInstance();
        Calendar now = (Calendar) calendar.clone();
        calendar.set(Calendar.DATE, 1);
        for (int m = 0; m < MONTH_NUM; m++) {
            int endDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int d = 0; d < endDay; d++) {
                days.add(getDay(calendar, calendar.equals(now)));
                calendar.add(Calendar.DATE, 1);
            }
        }
        for (int i = 0; i < days.size(); i++) {
            Day d = days.get(i);
            for (String j : new String[]{"春分", "秋分", "夏至", "冬至", "立春", "立夏", "立秋", "立冬"}) {
                if (d.getTitle().indexOf(j) >= 0) {
                    d.no();
                    if (i > 0) {
                        days.get(i - 1).no();
                    }
                }
            }
            for (String j : new String[]{"初一", "十五"}) {
                if (d.getName().indexOf(j) >= 0) {
                    d.no();
                }
            }
            for (String j : new String[]{"夏至", "冬至"}) {
                if (d.getTitle().indexOf(j) >= 0) {
                    int e = i + 36;
                    if (e > days.size()) {
                        e = days.size();
                    }
                    for (int f = i; f < e; f++) {
                        days.get(f).no();
                    }
                }
            }
        }
        return days;
    }

    private Day getDay(Calendar calendar, boolean now) {
        ChineseCalendar chineseCalendar = new ChineseCalendar(calendar);
        Day day = new Day(calendar.get(Calendar.DATE),
                chineseCalendar.getChinese(ChineseCalendar.CHINESE_TERM_OR_DATE),
                chineseCalendar.getChinese(ChineseCalendar.LUNAR_FESTIVAL),
                chineseCalendar.getChinese(ChineseCalendar.SOLAR_FESTIVAL));
        if (now) {
            day.now();
        }
        day.setName(chineseCalendar.getChinese(ChineseCalendar.CHINESE_DATE));
        return day;
    }

    private void addView(LinearLayout row, boolean end) {
        TextView textView = new TextView(this);
        textView.setTextAppearance(this, android.R.style.TextAppearance_Small);
        textView.setText("");
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        if (end) {
            row.addView(textView);
        } else {
            row.addView(textView, 0);
        }
    }
}