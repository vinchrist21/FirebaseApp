package com.uc.myfirebaseapss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        BottomNavigationView bottomnav = findViewById(R.id.bottom_nav);
        bottomnav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new FragmentSchedule()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.nav_schedule:
                    selectedFragment = new FragmentSchedule();
                    break;
                case R.id.nav_courses:
                    selectedFragment = new FragmentCourse();
                    break;
                case R.id.nav_account:
                    selectedFragment = new FragmentAccount();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment).commit();

            return true;
        }
    };
}