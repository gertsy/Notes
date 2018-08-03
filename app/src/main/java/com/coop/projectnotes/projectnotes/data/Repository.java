package com.coop.projectnotes.projectnotes.data;

import com.coop.projectnotes.projectnotes.data.Note;

import java.util.List;
import java.util.UUID;

public interface Repository {
    List<Note> getItems();
    void addNote(Note note);





    /*void deleteNote(UUID noteId);
    void getNote(UUID noteId, GetNoteCallback callback);

    interface GetNoteCallback {
        void onTaskLoaded(Note note);
        void onDataNotAvailable();
    }

    interface LoadTasksCallback {

        void onTasksLoaded(List<Task> tasks);

        void onDataNotAvailable();
    }

    void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback);

    */
}
