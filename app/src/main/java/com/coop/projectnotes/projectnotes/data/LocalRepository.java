package com.coop.projectnotes.projectnotes.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LocalRepository implements Repository {

    private static class SingletonHelper {
        private static final LocalRepository Instance = new LocalRepository();
    }
    public static LocalRepository getInstance() {
        return SingletonHelper.Instance;
    }



    //todo: Итак, разберись где и как хранить наши файлы. Пока используй сериализацию для текста.
    //Для каждой заметки надо будет создавать папку, в которой будем хранить картинки, звук, еще что-то
    //У каждой заметки есть UUID, его будем использовать для названий папки
    //todo: надо как-то на эмуляторе настроить внешнее хранилище, на телефоне у меня вроде все работало нормально в старом приложении
    //тут инфа https://developer.android.com/training/data-storage/files
    //Использовать конечно нужно external, а не internal

    //Если написал тут какой-нибудь метод, добавляй в Repository, все методы только там
    //Можно будет переписать на LinkedHashMap/TreeMap что-бы быстрее находить по id


    private LocalRepository()
    {
        degugDataCreate();
    }

    private final String NOTES_FILENAME = "notes.json";
    private final String TAG = "LocalRepository";

    List<Note> notes;

    @Override
    public List<Note> getNotes() {
        return notes;
    }

    @Override
    public void addNote(Note note) {
        //
    }

    @Override
    public void deleteNote(UUID noteId) {
        //
    }

    @Override
    public Note getNote(UUID noteId) {
        return null;
    }


    private void degugDataCreate()
    {
        notes = new ArrayList<>();

        Note temp = new Note();
        temp.setHeader("Заметка 0");
        temp.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        notes.add(temp);

        Note temp1 = new Note();
        temp1.setHeader("Заметка 1");
        temp1.setContent("Pellentesque iaculis vehicula augue, vel pulvinar nunc vestibulum vel.");
        notes.add(temp1);

        Note temp2 = new Note();
        temp2.setHeader("Заметка 2");
        temp2.setContent("Pellentesque dictum, magna et consequat facilisis, est purus facilisis erat, sed blandit est risus pulvinar nisl.");
        notes.add(temp2);

        Note temp3 = new Note();
        temp3.setHeader("Заметка 3");
        temp3.setContent("Morbi eget nunc et metus");
        notes.add(temp3);

        Note temp4 = new Note();
        temp4.setHeader("Заметка 4");
        temp4.setContent("Nullam congue vestibulum justo non dignissim. Pellentesque suscipit felis sed vehicula hendrerit. " +
                "Etiam eu viverra purus. In risus sapien, bibendum ac lacinia sed, facilisis aliquet neque.");
        notes.add(temp4);

        Note temp5 = new Note();
        temp5.setHeader("Заметка 5");
        temp5.setContent("Cras imperdiet est sit amet est auctor, in malesuada ante cursus. Integer vitae varius ligula.");
        notes.add(temp5);
    }



    //Так я раньше сереализовал и сохранял в файл
    //ObjectOutputStream сам из объекта делает json файл и читает обратно, изи

    /*@SuppressWarnings("unchecked")
    private void getLocallyStoredData()
    {
        File sdcard = Environment.getExternalStorageDirectory();
        try {
            //FileInputStream fileIn = context.openFileInput(NOTES_FILENAME);
            //FileInputStream fileIn = new FileInputStream(sdcard); //new File("/sdcard/save_object.bin")
            FileInputStream fileIn = new FileInputStream(new File(sdcard.getPath() + NOTES_FILENAME));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            notes = (ArrayList<Note>) in.readObject();
            in.close();
            fileIn.close();
        }
        catch (Exception e) {
            notes = new ArrayList<Note>();
            degugDataCreate();
            Log.d(TAG, e.getMessage());
        }
    }*/

    /*private void saveToFile()
    {
        File sdcard = Environment.getExternalStorageDirectory();
        try {
            //FileOutputStream fileOut = context.openFileOutput(NOTES_FILENAME, Context.MODE_PRIVATE);
            FileOutputStream fileOut = new FileOutputStream(new File(sdcard.getPath() + NOTES_FILENAME));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(notes);
            out.close();
            fileOut.close();
        }
        catch (FileNotFoundException e) {
            Log.d(TAG, e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }

    }*/

}
