package me.xuender.ethics.app.sex;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import me.xuender.ethics.app.R;
import me.xuender.ethics.app.game.OnAddPoint;
import me.xuender.ethics.app.notes.Note;
import me.xuender.ethics.app.notes.NoteAdapter;

/**
 * Created by ender on 14-5-1.
 */
public class BirthdayFragment extends Fragment implements OnAddPoint, DialogInterface.OnClickListener,
        AdapterView.OnItemClickListener {
    private View rootView;
    private TextView textView;
    private NoteAdapter adapter;
    private ListView listView;
    private List<Note> tops;
    private boolean del = false;
    private AlertDialog alert;
    private EditText editText;
    private LinearLayout linearLayout;
    private DatePicker datePicker;
    private SharedPreferences notes;
    static final String KEY = "birthday";

    public void add() {
        showAlert();
    }

    private LinearLayout getLinearLayout() {
        if (linearLayout == null) {
            linearLayout = new LinearLayout(getActivity());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            editText = new EditText(getActivity());
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            linearLayout.addView(editText);
            datePicker = new DatePicker(getActivity());
            linearLayout.addView(datePicker);
        }
        return linearLayout;
    }

    private AlertDialog showAlert() {
        if (alert == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.binput);
            builder.setIcon(android.R.drawable.stat_sys_warning);
            builder.setView(getLinearLayout());
            builder.setPositiveButton(R.string.ok, this);
            builder.setNegativeButton(R.string.cancel, null);
            alert = builder.show();
        }
        editText.setText("");
        alert.show();
        return alert;
    }

    public void del() {
        del = true;
        Toast.makeText(getActivity(), getString(R.string.del_click), Toast.LENGTH_SHORT).show();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_note, container, false);
            textView = (TextView) rootView.findViewById(R.id.textView);
            textView.setText(R.string.birthday_summary);
            listView = (ListView) rootView.findViewById(R.id.listView);
            adapter = new NoteAdapter(rootView.getContext(), new ArrayList<Note>(), R.color.土,
                    getString(R.string.format));
            listView.setAdapter(adapter);
            notes = getActivity().getSharedPreferences("sex", Context.MODE_PRIVATE);
            if (tops != null) {
                adapter.addAll(tops);
            }
            listView.setOnItemClickListener(this);
            initData();
        }
        return rootView;
    }

    private void initData() {
        try {
            JSONArray array = new JSONArray(notes.getString(KEY, "[]"));
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void add(List<Note> notes) {
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note lhs, Note rhs) {
                return Long.valueOf(rhs.getCreate().getTime() - lhs.getCreate().getTime()).intValue();
            }
        });
        if (adapter == null) {
            tops = notes;
        } else {
            adapter.clear();
            adapter.addAll(notes);
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
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, datePicker.getYear());
            c.set(Calendar.MONTH, datePicker.getMonth());
            c.set(Calendar.DATE, datePicker.getDayOfMonth());
            note.setCreate(c.getTime());
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
        editor.putString(KEY, array.toString());
        editor.commit();
    }

    private int nowPosition;

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
        }
    }
}