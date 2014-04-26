package me.xuender.ethics.app;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ender on 14-4-26.
 */
class ButtonItem {
    Button button;
    private String title;
    int count = 0;
    private boolean 忏悔 = false;
    private SharedPreferences sp;
    private String key;
    private SharedPreferences.Editor editor;

    public String getTitle() {
        return title;
    }


    ButtonItem(Button button, String title, Button.OnClickListener onClickListener,
               SharedPreferences sp) {
        this.button = button;
        this.button.setOnClickListener(onClickListener);
        this.title = title;
        this.sp = sp;
        editor = sp.edit();
        key = new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis())) + title;
        count = sp.getInt(key, 0);
        showTitle();
    }

    void showTitle() {
        if (count == 0) {
            this.button.setText(title);
        } else {
            this.button.setText(this.title + " ( " + this.count + " )");
        }
    }

    ButtonItem(Button button, String title, Button.OnClickListener onClickListener,
               SharedPreferences sp, boolean 忏悔) {
        this(button, title, onClickListener, sp);
        this.忏悔 = 忏悔;
    }

    boolean isButton(View view) {
        return view == button;
    }

    boolean click() {
        if (忏悔) {
            return true;
        }
        count++;
        editor.putInt(key, count);
        editor.commit();
        showTitle();
        return false;
    }

    void clean() {
        count = 0;
        editor.putInt(key, count);
        editor.commit();
        showTitle();
    }
}