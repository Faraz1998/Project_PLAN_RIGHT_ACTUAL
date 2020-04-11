package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.project.Notes_file.Notes;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.home);

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch(menuItem.getItemId())
                    { case R.id.home:
                        return true;

                        case R.id.account:
                            startActivity(new Intent(getApplicationContext(), Account.class));
                            return true;

                        case R.id.Notes:
                            startActivity(new Intent(getApplicationContext(), Notes.class));
                            overridePendingTransition(0, 0);
                            return true;
                       }
                    return false;
                }
            });
        }

    }
