package me.xuender.ethics.app.game;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.xuender.ethics.app.EthicsApplication;
import me.xuender.ethics.app.R;
import me.xuender.ethics.app.notes.Note;
import me.xuender.ethics.app.notes.NoteAdapter;

/**
 * Created by ender on 14-5-1.
 */
public class TopFragment extends Fragment implements OnAddPoint {
    private View rootView;
    private TextView textView;
    private NoteAdapter adapter;
    private ListView listView;
    private List<Note> tops;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_note, container, false);
            textView = (TextView) rootView.findViewById(R.id.textView);
            textView.setText(R.string.top_summary);
            listView = (ListView) rootView.findViewById(R.id.listView);
            adapter = new NoteAdapter(rootView.getContext(), new ArrayList<Note>(), R.color.火);
            listView.setAdapter(adapter);
            if (tops != null) {
                adapter.addAll(tops);
            }
        }
        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void add(List<Note> notes) {
        if (adapter == null) {
            tops = notes;
        } else {
            adapter.clear();
            adapter.addAll(notes);
        }
    }
}