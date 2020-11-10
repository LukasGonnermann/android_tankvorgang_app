package com.example.tankauswertung;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import static android.view.Menu.NONE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    BottomNavigationView botNavView;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view); //Navigation View (Side Menu)
        botNavView = findViewById(R.id.bot_nav_view); //bottom Menu (Tabs)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_timeline, R.id.navigation_forecast, R.id.navigation_stats)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(botNavView, navController);

        addMenuItemInNavMenuDrawer();
        setNavigationViewListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        //Back-Button is pressed
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int item_id = item.getItemId();
        switch (item_id) {

            //add a car
            case 1: {
                Toast.makeText(getApplicationContext(), "Add Car", Toast.LENGTH_LONG).show();

                break;
            }
            //open settings
            case R.id.action_settings: {
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
                break;
            }
        }
        drawer.closeDrawer(GravityCompat.START); //close navigation drawer

        return true;
    }

    private void addMenuItemInNavMenuDrawer() {
        //add items zu navigation menu
        NavigationView navView = findViewById(R.id.nav_view);

        Menu menu = navView.getMenu();
        Menu garageMenu = menu.addSubMenu(0, 0, 0, R.string.garage);

        // add button to add a car
        MenuItem itemAddCar = garageMenu.add(0, 1, 0, R.string.addCar);
        itemAddCar.setIcon(R.drawable.ic_baseline_add_24);

        // add settings button
        MenuItem itemSettings = menu.add(0, R.id.action_settings, 0, R.string.settings);
        itemSettings.setIcon(R.drawable.ic_baseline_settings_24);

        //example adding of items
        garageMenu.add(2, 1, NONE, "Super Item1");
        garageMenu.add("Super Item2");
        garageMenu.add("Super Item3");
        garageMenu.add("Super Item2");
        garageMenu.add("Super Item3");
        garageMenu.add("Super Item2");
        garageMenu.add("Super Item3");
        garageMenu.add("Super Item2");
        garageMenu.add("Super Item3");
        garageMenu.add("Super Item2");
        garageMenu.add("Super Item3");
        garageMenu.add("Super Item2");
        garageMenu.add("Super Item3");


    }

    private void setNavigationViewListener() {
        //initialize item listener of navigation view
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}

