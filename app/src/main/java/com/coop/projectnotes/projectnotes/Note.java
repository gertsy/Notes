package com.coop.projectnotes.projectnotes;

import java.util.ArrayList;
import java.util.List;

public class Note {
    int id = 1;

    String header = "Note" + String.valueOf(id);
    String content = "lorem ipsum dolores";

    private Note(String header, String content){
        this.header = header;
        this.content = content;
    }

    public static Note createNote(String header, String content){
        return new Note(header,content);
    }

}
