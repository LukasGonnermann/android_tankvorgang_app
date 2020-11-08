package com.example.tankauswertung;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    NavigationView navigationView;
    BottomNavigationView botNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        botNavView = findViewById(R.id.bot_nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_timeline, R.id.navigation_forecast, R.id.navigation_stats)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(botNavView,navController);

        loadAppData();
    }


    private void loadAppData() {
        addGarageToNavigationDrawer();
    }

    private void addGarageToNavigationDrawer() {

        Menu mainMenu = navigationView.getMenu();
        Menu garageMenu = mainMenu.addSubMenu(0, 0, 0, R.string.garage);

        // add cars
        // itemID of itemFirstCar is 42 (i1)
        MenuItem itemFirstCar = garageMenu.add(0, 42, 0, "Auto 1");
        itemFirstCar.setIcon(R.drawable.ic_baseline_directions_car_24);
        itemFirstCar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                drawer.closeDrawer(GravityCompat.START);
                final TextView textView = findViewById(R.id.text_dashboard);
                textView.setText("Clicked Auto 1");
                return false;
            }
        });
        // ...

        // add button to add a car
        MenuItem itemAddCar = garageMenu.add(0, 0, 0, R.string.addCar);
        itemAddCar.setIcon(R.drawable.ic_baseline_add_24);

        // add settings button
        MenuItem itemSettings = mainMenu.add(0, R.id.action_settings, 0, R.string.settings);
        itemSettings.setIcon(R.drawable.ic_baseline_settings_24);

    }

    // --------------------------------------------- hooks

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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
