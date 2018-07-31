package com.coop.projectnotes.projectnotes;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

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
        holder.header.setText(notes.get(position).header);
        holder.content.setText(notes.get(position).content);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder{

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
