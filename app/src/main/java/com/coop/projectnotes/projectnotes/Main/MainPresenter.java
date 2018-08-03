package com.coop.projectnotes.projectnotes.Main;

import android.annotation.SuppressLint;

import com.coop.projectnotes.projectnotes.data.LocalRepository;
import com.coop.projectnotes.projectnotes.data.Note;
import com.coop.projectnotes.projectnotes.data.Repository;

import static android.support.v4.util.Preconditions.checkNotNull;

public class MainPresenter implements MainContract.Presenter {

    private final String TAG = "MainPresenter";

    private Repository repository;
    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.repository = LocalRepository.getInstance();
        //Первичная загрузка заметок
        loadNotes();
    }

    public void addNote(){
        //Создаем новую заметку
        Note temp = new Note();
        //Просим Repository ее добавить
        repository.addNote(temp);
        //Просим View открыть Activity для редактирования новой заметки
        view.showNoteEditUi(temp.getUUID());
    }

    //Запрошено обновление данных
    @Override
    public void loadNotes() {
        //Забираем у Repository данные
        //Просим View обновить данные о заметках
        view.showNotes(repository.getItems());
    }

    //Кликнули на заметку
    @Override
    public void clickNote(Note item) {
        //Просим View открыть Activity для редактирования заметки
        view.showNoteEditUi(item.getUUID());
    }


}
