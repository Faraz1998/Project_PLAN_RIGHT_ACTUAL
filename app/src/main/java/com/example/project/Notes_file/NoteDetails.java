package com.example.project.Notes_file;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import com.example.project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.NonNull;


import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class NoteDetails extends AppCompatActivity {
    Intent data;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        data = getIntent();
        TextView content = findViewById(R.id.noteDetailsContent);
        TextView title = findViewById(R.id.noteDetailsTitle);
        content.setMovementMethod(new ScrollingMovementMethod());

        content.setText(data.getStringExtra("content"));
        title.setText(data.getStringExtra("title"));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent send = new Intent(view.getContext(), EditNote.class);
                send.putExtra("title",data.getStringExtra("title"));
                send.putExtra("content",data.getStringExtra("content"));
                send.putExtra("noteId",data.getStringExtra("noteId"));
                startActivity(send);
            }
        });
    }

//back pressed
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}