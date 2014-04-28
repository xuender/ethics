package me.xuender.ethics.app.notes;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-4-27.
 */
public class YiAdapter extends FragmentPagerAdapter implements OnSelectNote {
    private NoteFragment userFragment;
    private Context context;
    private int count = 1;
    private Note nowNote;
    private OnSetTab onSetTab;
    private NoteFragment noteFragment;

    public void setOnSetTab(OnSetTab onSetTab) {
        this.onSetTab = onSetTab;
    }

    public NoteFragment getNoteFragment() {
        if (noteFragment == null) {
            noteFragment = new NoteFragment(nowNote.getTitle(), R.string.inputGood,
                    nowNote.getTitle(), context);
        }
        return noteFragment;
    }

    public NoteFragment getUserFragment() {
        if (userFragment == null) {
            userFragment = new NoteFragment(context.getString(R.string.yi_summary),
                    R.string.inputName, "_users", context);
            userFragment.setOnSelectNote(this);
        }
        return userFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return getUserFragment();
            case 1:
                return getNoteFragment();
        }
        return null;
    }

    public YiAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return context.getString(R.string.googMan).toUpperCase(l);
            case 1:
                return nowNote.getTitle().toUpperCase(l);
        }
        return null;
    }

    @Override
    public void select(Note note) {
        nowNote = note;
        if (count == 1) {
            count = 2;
            this.notifyDataSetChanged();
        }
        onSetTab.setTab(note.getTitle());
    }
}