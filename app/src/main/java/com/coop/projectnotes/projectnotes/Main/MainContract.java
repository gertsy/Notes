package com.coop.projectnotes.projectnotes.Main;

import com.coop.projectnotes.projectnotes.data.Note;

import java.util.List;
import java.util.UUID;

public interface MainContract {

    interface View {
        //Presenter прислал заметки которые необходимо добавить в адаптер
        void showNotes(List<Note> notes);
        //Просьба запустить новую Activity и передать ей UUID
        //Для поиска заметки, которую необходимо отредактировать
        void showNoteEditUi(UUID noteId);
    }

    interface Presenter {
        //Добавить новую заметку
        void addNote();
        //обновить данные
        void loadNotes();
        //Кликнули на заметку
        void clickNote(Note item);
    }
}
