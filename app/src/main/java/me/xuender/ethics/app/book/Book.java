package me.xuender.ethics.app.book;

import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-4-29.
 */
public enum Book {
    三界("three.json", R.drawable.type_three), 五行("three.json", R.drawable.type_five),
    四大界("three.json", R.drawable.type_four);
    private String file;
    private int icon;

    private Book(String file, int icon) {
        this.file = file;
        this.icon = icon;
    }

    public String getFile() {
        return file;
    }

    public int getIcon() {
        return icon;
    }
}
