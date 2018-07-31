package com.coop.projectnotes.projectnotes;

import java.util.List;

public class MainPresenter implements MainContract {

    public void addNote(){
        TestRepository.addNote();
    }

    public List<Note> getNotes(){
        return TestRepository.getNotes();
    }
}
