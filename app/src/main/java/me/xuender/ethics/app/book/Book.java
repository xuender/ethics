package me.xuender.ethics.app.book;

import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-4-29.
 */
public enum Book {
    三界("three.json", R.drawable.type_three),
    五行("mu.json,huo.json,tu.json,jin.json,shui.json", R.drawable.type_five),
    五行扩展("ext_mu.json,ext_huo.json,ext_tu.json,ext_jin.json,ext_shui.json", R.drawable.type_five),
    四大界("four.json", R.drawable.type_four);
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