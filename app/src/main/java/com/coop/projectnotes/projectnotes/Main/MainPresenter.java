package com.coop.projectnotes.projectnotes.Main;

import com.coop.projectnotes.projectnotes.Main.MainContract;
import com.coop.projectnotes.projectnotes.Note;
import com.coop.projectnotes.projectnotes.TestRepository;

import java.util.List;

public class MainPresenter implements MainContract {

    public void addNote(){
        TestRepository.addNote();
    }

    public List<Note> getNotes(){
        return TestRepository.getNotes();
    }
}
