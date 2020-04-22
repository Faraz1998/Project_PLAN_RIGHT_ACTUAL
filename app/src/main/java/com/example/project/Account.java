package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Notes_file.Notes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Account extends AppCompatActivity {
    TextView logged;
    Button update_pass, update_email;
    FirebaseAuth FAuth;
    FirebaseUser FUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        logged = findViewById(R.id.loggedinas);
        update_pass = findViewById(R.id.updpass);

        FAuth = FirebaseAuth.getInstance();
        FUser = FirebaseAuth.getInstance().getCurrentUser();

        logged.setText("Logged in as: \n" + FUser.getEmail());//display user's email they are logged in with.


        update_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FAuth.sendPasswordResetEmail(FUser.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Account.this, "Password reset link sent to email address, ensure the email address is valid.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Account.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.account);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.account:
                        return true;

                        case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        return true;

                        case R.id.Notes:
                            startActivity(new Intent(getApplicationContext(), Notes.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.Reminder:
                        startActivity(new Intent(getApplicationContext(), Reminder.class));
                        overridePendingTransition(0, 0);
                        return true;
//
//                        case R.id.Notes:
//                            startActivity(new Intent(getApplicationContext(), Notes.class));
//                            overridePendingTransition(0, 0);
//                            return true;
                }
                return false;
            }
        });


    }

    public void Logout(View View) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}



