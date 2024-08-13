package com.econetwork;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toolbar;

import com.econetwork.ui.dashboard.DashboardFragment;
import com.econetwork.ui.home.Events_model;
import com.econetwork.ui.home.HomeFragment;
import com.econetwork.ui.notifications.NotificationsFragment;
import com.econetwork.ui.profile.ProfileFragment;
import com.econetwork.ui.shop.ShopFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_shop, R.id.navigation_profile)
                .build();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navHostFragment.getNavController(), appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navHostFragment.getNavController());
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));


        navView.setOnNavigationItemSelectedListener(this);
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.navigation_home):
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(), "HomeFragment").commit();
                item.setChecked(true);
                break;
            case (R.id.navigation_dashboard):
                FragmentManager fragmentManager2 = getSupportFragmentManager();
                fragmentManager2.beginTransaction().replace(R.id.nav_host_fragment, new DashboardFragment(), "HomeFragment").commit();
                item.setChecked(true);
                break;
            case (R.id.navigation_notifications):
                FragmentManager fragmentManager3 = getSupportFragmentManager();
                fragmentManager3.beginTransaction().replace(R.id.nav_host_fragment, new NotificationsFragment(), "HomeFragment").commit();
                item.setChecked(true);
                break;
            case (R.id.navigation_shop):
                FragmentManager fragmentManager4 = getSupportFragmentManager();
                fragmentManager4.beginTransaction().replace(R.id.nav_host_fragment, new ShopFragment(), "HomeFragment").commit();
                item.setChecked(true);
                break;
            case (R.id.navigation_profile):
                FragmentManager fragmentManager5 = getSupportFragmentManager();
                fragmentManager5.beginTransaction().replace(R.id.nav_host_fragment, new ProfileFragment(), "ProfileFragment").commit();
                item.setChecked(true);
                break;
        }


        return false;
    }

}
