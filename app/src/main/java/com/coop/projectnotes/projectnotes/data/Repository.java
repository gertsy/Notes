package com.coop.projectnotes.projectnotes.data;

import com.coop.projectnotes.projectnotes.data.Note;

import java.util.List;
import java.util.UUID;

public interface Repository {

    // Repository сам по себе, он не должен знать от Presenter или View,
    // т.е. не хранить на них ссылок

    //Выдает список заметок
    List<Note> getNotes();

    //Добавить заметку в список заметок
    void addNote(Note note);

    //Удалить заметку по UUID
    void deleteNote(UUID noteId);

    //Поиск заметки по UUID
    Note getNote(UUID noteId);


    //Можно будет добавить такое, но пока не знаю есть ли смысл
    /*interface GetNoteCallback {
        void onTaskLoaded(Note note);
        void onDataNotAvailable();
    }*/
}
