package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Timer;

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

//                        case R.id.details:
//                            startActivity(new Intent(getApplicationContext(), Details.class));

                           //return true;

                        case R.id.account:
                            startActivity(new Intent(getApplicationContext(), Account.class));
                            return true;

//                        case R.id.about:
//                            startActivity(new Intent(getApplicationContext(), about.class));
//                            overridePendingTransition(0, 0);
//                            return true;
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

    }
