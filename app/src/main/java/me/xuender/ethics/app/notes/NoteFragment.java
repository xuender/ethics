package me.xuender.ethics.app.notes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-4-27.
 */
public class NoteFragment extends Fragment implements DialogInterface.OnClickListener, AdapterView.OnItemClickListener {
    private View rootView;
    private TextView textView;
    private SharedPreferences notes;
    private String title;
    private String key;
    private EditText editText;
    private AlertDialog alert;
    private NoteAdapter adapter;
    private List<Note> list = new ArrayList<Note>();
    private ListView listView;
    private int input;
    private OnSelectNote onSelectNote;

    public void setOnSelectNote(OnSelectNote onSelectNote) {
        this.onSelectNote = onSelectNote;
    }

    public NoteFragment(String title, int input, String key) {
        this.title = title;
        this.key = key;
        this.input = input;
    }

    public void add() {
        showAlert();
    }

    private AlertDialog showAlert() {
        if (alert == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(input));
            builder.setIcon(android.R.drawable.stat_sys_warning);
            builder.setView(editText);
            builder.setPositiveButton(R.string.ok, this);
            builder.setNegativeButton(R.string.cancel, null);
            alert = builder.show();
        }
        alert.show();
        return alert;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_note, container, false);
            textView = (TextView) rootView.findViewById(R.id.textView);
            textView.setText(title);
            editText = new EditText(container.getContext());
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            notes = getActivity().getSharedPreferences("notes", Context.MODE_PRIVATE);
            initData();
            listView = (ListView) rootView.findViewById(R.id.listView);
            adapter = new NoteAdapter(getActivity(), list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
        return rootView;
    }

    private void initData() {
        try {
            JSONArray array = new JSONArray(notes.getString(key, "[]"));
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Note note = new Note();
                note.setTitle(obj.getString("t"));
                note.setCreate(new Date(obj.getLong("c")));
                list.add(note);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.d("内容", editText.getText().toString());
        Note note = new Note();
        note.setTitle(editText.getText().toString());
        note.setCreate(new Date(System.currentTimeMillis()));
        adapter.add(note);
        SharedPreferences.Editor editor = notes.edit();
        JSONArray array = new JSONArray();
        for (int i = 0; i < adapter.getCount(); i++) {
            Note n = adapter.getItem(i);
            JSONObject obj = new JSONObject();
            try {
                obj.put("t", n.getTitle());
                obj.put("c", n.getCreate().getTime());
                array.put(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        editor.putString(key, array.toString());
        editor.commit();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (onSelectNote != null) {
            onSelectNote.select(list.get(position));
        }
    }
}