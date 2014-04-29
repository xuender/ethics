package me.xuender.ethics.app.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-4-27.
 */
public class NoteAdapter extends ArrayAdapter<Note> {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private int textColor;

    public NoteAdapter(Context context, List<Note> tests, int textColor) {
        super(context, R.layout.note_list, tests);
        this.textColor = textColor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Note note = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_list, null);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setTextColor(textColor);
        title.setText(note.getTitle());
        TextView create = (TextView) convertView.findViewById(R.id.create);
        create.setText(sdf.format(note.getCreate()));
        return convertView;
    }
}