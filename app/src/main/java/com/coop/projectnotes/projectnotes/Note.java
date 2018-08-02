package com.coop.projectnotes.projectnotes;

import java.util.ArrayList;
import java.util.List;

public class Note {

    //Перевел на аксессоры(Get) и мутаторы(set), вроде так правильно

    private String header;
    private String content;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private Note(String header, String content){
        this.header = header;
        this.content = content;
    }

    public static Note createNote(String header, String content){
        return new Note(header, content);
    }

}
