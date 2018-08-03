package com.coop.projectnotes.projectnotes.NoteEdit;

import com.coop.projectnotes.projectnotes.data.Note;

import java.util.UUID;

public interface NoteEditContract {
    interface View {
        void fillWithData(Note note);
    }

    interface Presenter{
        void loadNote(UUID id);
    }
}
