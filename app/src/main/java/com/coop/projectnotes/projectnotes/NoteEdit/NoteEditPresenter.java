package com.coop.projectnotes.projectnotes.NoteEdit;

import com.coop.projectnotes.projectnotes.data.LocalRepository;
import com.coop.projectnotes.projectnotes.data.Repository;

public class NoteEditPresenter implements NoteEditContract.Presenter {

    private final String TAG = "NoteEditPresenter";

    private Repository repository;
    private NoteEditContract.View view;

    public NoteEditPresenter(NoteEditContract.View view) {
        this.view = view;
        this.repository = LocalRepository.getInstance();
    }

}
