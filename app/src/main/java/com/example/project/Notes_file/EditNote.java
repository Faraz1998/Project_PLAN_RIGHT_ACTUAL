package com.example.project.Notes_file;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.project.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class EditNote extends AppCompatActivity {
    Intent data;
    EditText editNoteTitle,editNoteContent;
    FirebaseFirestore fStore;
    ProgressBar spinner;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        fStore = fStore.getInstance();
        spinner = findViewById(R.id.progressBar2);
        user = FirebaseAuth.getInstance().getCurrentUser();
        data = getIntent();
        editNoteContent = findViewById(R.id.editNoteContent);
        editNoteTitle = findViewById(R.id.editNoteTitle);
        String noteTitle = data.getStringExtra("title");
        String noteContent = data.getStringExtra("content");

        editNoteTitle.setText(noteTitle);
        editNoteContent.setText(noteContent);

        FloatingActionButton fab = findViewById(R.id.saveEditedNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nTitle = editNoteTitle.getText().toString();
                String nContent = editNoteContent.getText().toString();
                if(nTitle.isEmpty() || nContent.isEmpty()){
                    Toast.makeText(EditNote.this, "Cannot Save note with Empty Field.", Toast.LENGTH_SHORT).show();
                    return;
                }
                spinner.setVisibility(View.VISIBLE);
                // save note

                //saves new edit of note to Firestore
                DocumentReference docref = fStore.collection("notes").document(user.getUid()).collection("myNotes").document(data.getStringExtra("noteId"));
                Map<String,Object> note = new HashMap<>();
                note.put("title",nTitle);
                note.put("content",nContent);

                docref.update(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditNote.this, "Note Saved.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Notes.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditNote.this, "Error, Try again.", Toast.LENGTH_SHORT).show();
                        spinner.setVisibility(View.VISIBLE);
                    }
                });


            }
        });
    }
}