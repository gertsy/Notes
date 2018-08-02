package com.coop.projectnotes.projectnotes;

import java.util.ArrayList;
import java.util.List;

public class TestRepository implements Repository {

    //todo перетащить методы в интерфейс

    static List<Note> notes = new ArrayList<>();
    static int count = 1;

    public static void loadNotes(int number){
        for(int i=0;i<number;i++)
            addNote();
    }
    public static void addNote(){
        notes.add(Note.createNote(String.format("Note"+String.valueOf(count)),"blablabla"));
        count++;
    }
    public static List<Note> getNotes(){
        if(notes.size()<1)
            loadNotes(10);
        return notes;
    }




    @Override
    public List<Note> getItems() {
        return null;
    }
}
