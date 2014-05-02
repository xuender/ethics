package me.xuender.ethics.app.sex;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-5-2.
 */
public class CalendarFragment extends Fragment {
    private View rootView;
    private LinearLayout layout;
    private final static int MONTH_NUM = 3;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_sex, container, false);
            context = rootView.getContext();
            layout = (LinearLayout) rootView.findViewById(R.id.layout);
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
                TextView month = new TextView(context);
                month.setTextAppearance(context, android.R.style.TextAppearance_Medium);
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
                        row = new LinearLayout(context);
                        row.setOrientation(LinearLayout.HORIZONTAL);
                        row.setWeightSum(7);
                        row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                        row.setPadding(0, 3, 0, 3);
                        layout.addView(row);
                    }
                    TextView textView = new TextView(context);
                    textView.setTextAppearance(context, R.style.day);
                    Day day = days.remove(0);
                    StringBuilder sb = new StringBuilder();
                    sb.append(calendar.get(Calendar.DATE)).append('\n');
                    sb.append(day.getTitle()).append('\n');
                    sb.append(day.getGala());
                    textView.setText(sb.toString());
                    textView.setGravity(Gravity.CENTER);
                    textView.setLayoutParams(new LinearLayout.LayoutParams(0,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                    textView.setPadding(0, 3, 0, 3);
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
        return rootView;
    }

    private String getKey(int m, int d) {
        StringBuilder sb = new StringBuilder();
        if (m < 10) {
            sb.append('0');
        }
        sb.append(m);
        if (d < 10) {
            sb.append('0');
        }
        sb.append(d);
        return sb.toString();
    }

    private List<Day> getDays() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
        try {
            SharedPreferences notes = context.getSharedPreferences("sex", Context.MODE_PRIVATE);
            JSONArray array = new JSONArray(notes.getString(BirthdayFragment.KEY, "[]"));
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Date date = new Date(obj.getLong("c"));
                ChineseCalendar.addS(sdf.format(date), getString(R.string.yang) + obj.getString("t"));
                ChineseCalendar chineseCalendar = new ChineseCalendar(date);
                //chineseCalendar.computeChineseFields();
                ChineseCalendar.addL(getKey(chineseCalendar.get(ChineseCalendar.CHINESE_MONTH),
                                chineseCalendar.get(ChineseCalendar.CHINESE_DATE)),
                        getString(R.string.yin) + obj.getString("t")
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
            for (String j : new String[]{"农", "公"}) {
                if (d.getGala().indexOf(j) >= 0) {
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
        TextView textView = new TextView(context);
        textView.setTextAppearance(context, android.R.style.TextAppearance_Small);
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