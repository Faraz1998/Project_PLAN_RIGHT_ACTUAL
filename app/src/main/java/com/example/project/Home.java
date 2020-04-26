package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.project.Notes_file.Notes;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Timer;
import java.util.TimerTask;

public class Home extends AppCompatActivity {
    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        vp = findViewById(R.id.SShow);
        Imageadapter adapter = new Imageadapter(this);//calls the image adapter
        vp.setAdapter(adapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new slidetimer(), 2000, 4000);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        return true;

                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), Account.class));
                        return true;

                    case R.id.Notes:
                        startActivity(new Intent(getApplicationContext(), Notes.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.Reminder:
                        startActivity(new Intent(getApplicationContext(), Reminder.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    public class slidetimer extends TimerTask {//time of transition

        @Override
        public void run() {
            Home.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (vp.getCurrentItem() == 0) { //image 1
                        vp.setCurrentItem(1);
                    } else if (vp.getCurrentItem() == 1) {//after image 2, transitions to 3
                        vp.setCurrentItem(2);
                    } else if (vp.getCurrentItem() == 2) {//after image 3, transitions back to 1
                        vp.setCurrentItem(1);
                    }
                }
            });
        }
    }
}
