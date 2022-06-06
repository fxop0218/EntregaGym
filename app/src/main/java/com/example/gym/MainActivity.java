package com.example.gym;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    MainFragment mainFragment = new MainFragment();
    CalendarFragment calendarFragment = new CalendarFragment();
    TiendaFragment tiendaFragment = new TiendaFragment();
    UserFragment userFragment = new UserFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navegation = findViewById(R.id.bottom_navigation);
        navegation.setOnItemSelectedListener(mOnNavegationItemSelectedListener);
        loadFragment(mainFragment);
    }

    private final BottomNavigationView.OnItemSelectedListener mOnNavegationItemSelectedListener = new BottomNavigationView.OnItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.page_home:
                    loadFragment(mainFragment);
                    return true;

                case R.id.page_calendar:
                    loadFragment(calendarFragment);
                    return true;

                case R.id.page_tienda:
                    loadFragment(tiendaFragment);
                    return true;

                case R.id.page_user:
                    loadFragment(userFragment);
                    return true;
            }
            return false;

        }
    };

    public void loadFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}