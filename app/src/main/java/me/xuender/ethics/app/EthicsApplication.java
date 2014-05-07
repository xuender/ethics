package me.xuender.ethics.app;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

import me.xuender.ethics.app.game.OnAddPoint;
import me.xuender.ethics.app.notes.OnSelectNote;

/**
 * Created by ender on 14-4-27.
 */
public class EthicsApplication extends Application {
    private OnSelectNote onSelectNote;
    private Map<String, OnAddPoint> map = new HashMap<String, OnAddPoint>();

    public OnAddPoint getOnAddPoint(String key) {
        return map.get(key);
    }

    public void putOnAddPoint(String key, OnAddPoint onAddPoint) {
        map.put(key, onAddPoint);
    }

    public OnSelectNote getOnSelectNote() {
        return onSelectNote;
    }

    public void setOnSelectNote(OnSelectNote onSelectNote) {
        this.onSelectNote = onSelectNote;
    }
}
