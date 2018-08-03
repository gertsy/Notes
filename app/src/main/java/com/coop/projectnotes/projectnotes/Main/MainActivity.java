package com.coop.projectnotes.projectnotes.Main;

import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.coop.projectnotes.projectnotes.NoteEdit.NoteEditActivity;
import com.coop.projectnotes.projectnotes.data.Note;
import com.coop.projectnotes.projectnotes.R;
import com.coop.projectnotes.projectnotes.useful.FabToolbar;
import com.coop.projectnotes.projectnotes.useful.RecyclerViewEmptySupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    //Идеи
    //Покрасить заметки в разные цвета
    //Анимировать заметки
    //Мигаюющие заметки, означающие крайнюю важность записи

    private final String TAG = "MainActivity";

    RecyclerViewEmptySupport notesView = null;
    private MainContract.Presenter presenter;


    //Элементы меню
    FloatingActionButton fab;
    FrameLayout bottomMenu;
    boolean toolbarStatusOpen = false;
    //Кнопки
    View buttonNewNote;
    View buttonNewPhoto;
    View buttonNewRecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);

        //Нижнее меню
        buttonNewNote = findViewById(R.id.main_new_note);
        buttonNewPhoto = findViewById(R.id.main_new_photo);
        buttonNewRecord = findViewById(R.id.main_new_record);
        fab = findViewById(R.id.main_fab);
        bottomMenu = findViewById(R.id.main_bottom_menu);
        //
        Toolbar toolbar = findViewById(R.id.toolbar);
        notesView = findViewById(R.id.notes);
        View emptyView = findViewById(R.id.main_empty_view);







        setSupportActionBar(toolbar);
        notesView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        notesView.setEmptyView(emptyView);





        setListeners();



        notesView.addItemDecoration(new SpacesItemDecoration(8));
        notesView.setAdapter(adapter);

        //addNote();


    }

    void setListeners()
    {
        //
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!toolbarStatusOpen)
                {
                    FabToolbar.with(fab).setListener(new FabToolbar.OnFabMenuListener() {
                        @Override
                        public void onStartTransform() {
                            //
                        }

                        @Override
                        public void onEndTransform() {
                            //
                            toolbarStatusOpen = true;
                        }
                    }).transformTo(bottomMenu);
                }
            }
        });

        notesView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if(toolbarStatusOpen)
                {
                    FabToolbar.with(fab).transformFrom(bottomMenu);
                    toolbarStatusOpen = false;
                }
            }
        });

        //Кнопка добавления заметки
        buttonNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addNote();
            }
        });

        //Кнопка добавления заметки с фоткой

        //Кнопка добавления заметки с звуковой записью
    }

    NotesViewAdapter adapter = new NotesViewAdapter(new ArrayList<Note>(), new NoteItemListener() {

        @Override
        public void onNoteClick(Note clickedNote) {
            presenter.clickNote(clickedNote);
        }
    });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                presenter.addNote();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void showNotes(List<Note> notes) {
        adapter.replaceData(notes);
    }



    @Override
    public void showNoteEditUi(UUID noteId) {
        Intent intent = new Intent(MainActivity.this, NoteEditActivity.class);
        intent.putExtra("noteId", noteId);
        startActivity(intent);
    }


    public class NotesViewAdapter extends RecyclerView.Adapter<NotesViewAdapter.NotesViewHolder> {

        List<Note> notes;
        private NoteItemListener listener;

        public NotesViewAdapter(List<Note> notes, NoteItemListener listener){
            setList(notes);
            this.listener = listener;
        }

        @NonNull
        @Override
        public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.note_item,parent,false);
            return new NotesViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
            Note select = notes.get(position);

            holder.header.setText(select.getHeader());
            holder.content.setText(select.getContent());
        }

        @Override
        public int getItemCount() {
            return (notes != null ? notes.size() : 0);
        }

        public void replaceData(List<Note> notes) {
            setList(notes);
            notifyDataSetChanged();
        }

        private void setList(List<Note> notes) {
            this.notes = notes;
        }

        class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
            CardView note;
            TextView header;
            TextView content;

            public NotesViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                itemView.setLongClickable(true);
                itemView.setOnLongClickListener(this);
                note = itemView.findViewById(R.id.noteItemCardView);
                header = itemView.findViewById(R.id.noteItemHeader);
                content = itemView.findViewById(R.id.noteItemContent);
            }

            @Override
            public void onClick(View v) {
                int adapterPosition = getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onNoteClick(notes.get(adapterPosition));
                }
            }

            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        }
    }

    //callback, передадим его в адаптер для получения кликов
    public interface NoteItemListener {
        void onNoteClick(Note clickedNote);
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration
    {
        private int space;
        SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
        {
            super.getItemOffsets(outRect, view, parent, state);

            outRect.left = space / 2;
            outRect.right = space / 2;
            outRect.top = space / 2;


            /*if(settings.isOneColumnMode())
            {
                outRect.right = space / 2;
                outRect.left = space / 2;
                outRect.top = space / 4;
                outRect.bottom = space / 4;
            }*/
        }
    }

}
