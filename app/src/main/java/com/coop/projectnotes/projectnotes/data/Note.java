package com.coop.projectnotes.projectnotes.data;

import java.io.Serializable;
import java.util.UUID;

public class Note implements Serializable {

    //Перевел на аксессоры(Get) и мутаторы(set), вроде так правильно


    public Note()
    {
        this.id = UUID.randomUUID();
        this.header = "";
        this.content = "";
    }

    //Нужно для поиска заметки, на которую кликнули например
    //передавать ссылку на элемент массива arraylist и редактировать ее не лучшая идея
    private UUID id;
    public UUID getUUID() {
        return id;
    }

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


    //На главном экране при нажатии на + создается пустая заметка и отправляется на редактирование
    public static Note createEmptyNote(){
        return new Note();
    }

}
