package me.xuender.ethics.app.notes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import me.xuender.ethics.app.EthicsApplication;
import me.xuender.ethics.app.K;
import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-4-27.
 */
public class NoteFragment extends Fragment implements DialogInterface.OnClickListener,
        AdapterView.OnItemClickListener {
    private View rootView;
    private TextView textView;
    private SharedPreferences notes;
    private String title;
    private String key;
    private EditText editText;
    private AlertDialog alert;
    private NoteAdapter adapter;
    private ListView listView;
    private int input;
    private OnSelectNote onSelectNote;
    private boolean del = false;
    private int nowPosition;

    public void add() {
        showAlert();
    }

    public void del() {
        del = true;
        Toast.makeText(getActivity(), getString(R.string.del_click), Toast.LENGTH_SHORT).show();
    }

    private AlertDialog showAlert() {
        if (alert == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getActivity().getString(input));
            builder.setIcon(android.R.drawable.stat_sys_warning);
            builder.setView(getEditText());
            builder.setPositiveButton(R.string.ok, this);
            builder.setNegativeButton(R.string.cancel, null);
            alert = builder.show();
        }
        editText.setText("");
        alert.show();
        return alert;
    }

    public void setTitle(String title) {
        this.title = title;
        this.key = title;
        textView.setText(title);
        adapter.clear();
        initData();
    }

    private EditText getEditText() {
        if (editText == null) {
            editText = new EditText(getActivity());
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        return editText;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_note, container, false);
            title = getArguments().getString(K.title.name());
            textView = (TextView) rootView.findViewById(R.id.textView);
            textView.setText(title);
            notes = getActivity().getSharedPreferences("notes", Context.MODE_PRIVATE);
            listView = (ListView) rootView.findViewById(R.id.listView);
            adapter = new NoteAdapter(getActivity(), new ArrayList<Note>(),
                    getArguments().getInt(K.color.name(), R.color.木));
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
            input = getArguments().getInt(K.input.name());
            key = getArguments().getString(K.key.name());
            onSelectNote = ((EthicsApplication) getActivity().getApplication()).getOnSelectNote();
            initData();
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
                adapter.add(note);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (del) {
            del = false;
            adapter.remove(adapter.getItem(nowPosition));
            saveNote();
        } else {
            Note note = new Note();
            note.setTitle(editText.getText().toString());
            note.setCreate(new Date(System.currentTimeMillis()));
            adapter.add(note);
            saveNote();
        }
    }

    private void saveNote() {
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
        Log.d("view", String.valueOf(view));
        Log.d("view", String.valueOf(id));
        if (del) {
            nowPosition = position;
            new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.del))
                    .setMessage(getString(R.string.del_ask))
                    .setIcon(android.R.drawable.ic_menu_help)
                    .setPositiveButton(android.R.string.yes, this)
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            del = false;
                        }
                    }).show();
        } else {
            if (onSelectNote != null) {
                onSelectNote.select(adapter.getItem(position));
            }
        }
    }
}