package me.xuender.ethics.app.game;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import me.xuender.ethics.app.R;
import me.xuender.ethics.app.book.Question;
import me.xuender.ethics.app.notes.Note;
import me.xuender.ethics.app.notes.NoteAdapter;

/**
 * Created by ender on 14-5-1.
 */
public class GameFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private TextView textView;
    private SoundPool soundPool;
    private List<Question> list = new ArrayList<Question>();
    private int point = 0;
    private int count = 0;
    private Question question;
    private SharedPreferences sp;
    private OnAddPoint onAddPoint;

    public void setOnAddPoint(OnAddPoint onAddPoint) {
        this.onAddPoint = onAddPoint;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_five, container, false);
            textView = (TextView) rootView.findViewById(R.id.textView);
            sp = getActivity().getSharedPreferences("game", Context.MODE_PRIVATE);

            声音初始化();
            init();
            onAddPoint.add(tops);
        }
        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    void init() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(R.id.怒, "mu.json");
        map.put(R.id.恨, "huo.json");
        map.put(R.id.怨, "tu.json");
        map.put(R.id.恼, "jin.json");
        map.put(R.id.烦, "shui.json");
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            try {
                InputStream is = null;
                is = getActivity().getAssets().open(entry.getValue());
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                JSONArray array = new JSONArray(new String(buffer, "UTF-8"));
                readArray(entry, array);
            } catch (Exception e) {
                Log.e("read", "error", e);
            }
        }
        Button b1 = (Button) rootView.findViewById(R.id.怒);
        b1.setText(R.string.mu);
        b1.setOnClickListener(this);

        Button b2 = (Button) rootView.findViewById(R.id.恨);
        b2.setText(R.string.huo);
        b2.setOnClickListener(this);

        Button b3 = (Button) rootView.findViewById(R.id.怨);
        b3.setText(R.string.tu);
        b3.setOnClickListener(this);

        Button b4 = (Button) rootView.findViewById(R.id.恼);
        b4.setText(R.string.jin);
        b4.setOnClickListener(this);

        Button b5 = (Button) rootView.findViewById(R.id.烦);
        b5.setText(R.string.shui);
        b5.setOnClickListener(this);

        Button b6 = (Button) rootView.findViewById(R.id.忏悔);
        b6.setText(R.string.pass);
        b6.setOnClickListener(this);
        try {
            JSONArray array = new JSONArray(sp.getString("top", "[]"));
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Note note = new Note();
                note.setTitle(obj.getString("t"));
                note.setCreate(new Date(obj.getLong("c")));
                tops.add(note);
            }
            onAddPoint.add(tops);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        show();
    }

    private void readArray(Map.Entry<Integer, String> entry, JSONArray array) throws JSONException {
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String title = obj.getString("title");
            if (obj.has("text")) {
                for (String text : obj.getString("text").split("，")) {
                    list.add(getQuestion(title + "\n" + text, entry.getKey()));
                }
            }
            if (obj.has("details")) {
                readArray(entry, obj.getJSONArray("details"));
            }
        }
    }

    private Question getQuestion(String title, int id) {
        Question q = new Question();
        q.title = title.replaceAll("。", "").replaceAll("金|木|水|火|土", "_");
        q.xing = id;
        return q;
    }

    private void 声音初始化() {
        soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(getActivity(), R.raw.play, 1);
    }

    private boolean ok = true;
    private List<Note> tops = new ArrayList<Note>();

    @Override
    public void onClick(View v) {
        if (count >= 20) {
            count = 0;
            point = 0;
            show();
            return;
        }
        boolean end = question.xing == v.getId();
        if (end) {
            if (ok) {
                point += 5;
            }
        } else {
            showEnd();
            ok = false;
            soundPool.play(1, 1, 1, 0, 0, 1);
            return;
        }
        ok = true;
        count++;
        if (count < 20) {
            show();
        } else {
            textView.setText(getString(R.string.end) + "\n" + point);
            save();
        }
    }

    private void save() {
        Note note = new Note();
        note.setTitle(String.valueOf(point));
        note.setCreate(new Date(System.currentTimeMillis()));
        tops.add(note);
        Collections.sort(tops, new Comparator<Note>() {
            @Override
            public int compare(Note lhs, Note rhs) {
                return rhs.getTitle().compareTo(lhs.getTitle());
            }
        });
        while (tops.size() > 10) {
            tops.remove(10);
        }
        onAddPoint.add(tops);
        SharedPreferences.Editor editor = sp.edit();
        JSONArray array = new JSONArray();
        for (Note n : tops) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("t", n.getTitle());
                obj.put("c", n.getCreate().getTime());
                array.put(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        editor.putString("top", array.toString());
        editor.commit();
    }

    private Integer[] ids = new Integer[]{R.id.怒, R.id.恨, R.id.怨, R.id.恼, R.id.烦};

    private void showEnd() {
        for (int id : ids) {
            if (question.xing != id) {
                Button b = (Button) rootView.findViewById(id);
                b.setTextAppearance(rootView.getContext(),
                        android.R.style.TextAppearance_Small);
                b.setTextColor(Color.BLACK);
            } else {
                Button b = (Button) rootView.findViewById(id);
                b.setTextAppearance(rootView.getContext(),
                        android.R.style.TextAppearance_Large);
                b.setTextColor(Color.WHITE);
            }
        }
    }

    private void show() {
        for (int id : ids) {
            Button b = (Button) rootView.findViewById(id);
            b.setTextAppearance(rootView.getContext(),
                    android.R.style.TextAppearance_Medium);
            b.setTextColor(Color.WHITE);
        }
        Random r = new Random(System.currentTimeMillis());
        int i = r.nextInt(list.size());
        question = list.get(i);
        list.remove(i);
        textView.setText(question.title);
    }
}