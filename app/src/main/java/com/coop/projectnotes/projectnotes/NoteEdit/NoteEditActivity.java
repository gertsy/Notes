package com.coop.projectnotes.projectnotes.NoteEdit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.coop.projectnotes.projectnotes.R;
import com.coop.projectnotes.projectnotes.data.Note;

import java.util.UUID;

public class NoteEditActivity extends AppCompatActivity implements NoteEditContract.View {

    private final String TAG = "NoteEditActivity";

    private NoteEditContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        presenter = new NoteEditPresenter(this);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            UUID id = (UUID) arguments.get("noteId");
            if (id != null) {
                presenter.loadNote(id);
            }
        }
    }

    @Override
    public void fillWithData(Note note) {
        //Показать эти данные
    }
}
