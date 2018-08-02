package com.coop.projectnotes.projectnotes.Main;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coop.projectnotes.projectnotes.Note;
import com.coop.projectnotes.projectnotes.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    RecyclerView notesView = null;
    NotesViewAdapter adapter;

    MainPresenter presenter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notesView = findViewById(R.id.notes);
        notesView.setLayoutManager(new StaggeredGridLayoutManager(2,
                                StaggeredGridLayoutManager.VERTICAL));

        presenter = new MainPresenter();

        adapter = new NotesViewAdapter(presenter.getNotes());
        notesView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });

    }

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


    public class NotesViewAdapter extends RecyclerView.Adapter<NotesViewAdapter.NotesViewHolder> {

        List<Note> notes;
        public NotesViewAdapter(List<Note> notes){
            this.notes = notes;
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
            holder.header.setText(notes.get(position).getHeader());
            holder.content.setText(notes.get(position).getContent());
        }

        @Override
        public int getItemCount() {
            return notes.size();
        }

        class NotesViewHolder extends RecyclerView.ViewHolder{

            CardView note;
            TextView header;
            TextView content;

            public NotesViewHolder(View itemView) {
                super(itemView);
                note = itemView.findViewById(R.id.note);
                header = itemView.findViewById(R.id.headerNote);
                content = itemView.findViewById(R.id.contentNote);
            }
        }
    }

}
