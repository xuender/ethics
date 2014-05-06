package me.xuender.ethics.app;

import android.app.Application;

import me.xuender.ethics.app.notes.OnSelectNote;

/**
 * Created by ender on 14-4-27.
 */
public class EthicsApplication extends Application {
    private OnSelectNote onSelectNote;

    public OnSelectNote getOnSelectNote() {
        return onSelectNote;
    }

    public void setOnSelectNote(OnSelectNote onSelectNote) {
        this.onSelectNote = onSelectNote;
    }
}
