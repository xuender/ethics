package me.xuender.ethics.app.notes;

import java.util.Date;

/**
 * Created by ender on 14-4-27.
 */
public class Note {
    private String title;
    private Date create;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }
}