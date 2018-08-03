package com.coop.projectnotes.projectnotes.Main;

import android.content.Intent;
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
import android.widget.TextView;

import com.coop.projectnotes.projectnotes.NoteEdit.NoteEditActivity;
import com.coop.projectnotes.projectnotes.data.Note;
import com.coop.projectnotes.projectnotes.R;
import com.coop.projectnotes.projectnotes.useful.RecyclerViewEmptySupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private final String TAG = "MainActivity";

    RecyclerViewEmptySupport notesView = null;
    private MainContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notesView = findViewById(R.id.notes);
        notesView.setLayoutManager(new StaggeredGridLayoutManager(2,
                                StaggeredGridLayoutManager.VERTICAL));


        View emptyView = findViewById(R.id.main_empty_view);
        notesView.setEmptyView(emptyView);




        notesView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });

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
                addNote();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    void addNote(){
        presenter.addNote();
        adapter.notifyDataSetChanged();
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

}
