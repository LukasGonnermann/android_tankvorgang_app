package com.example.tankauswertung;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    BottomNavigationView botNavView;
    private AppBarConfiguration mAppBarConfiguration;

    /**
     * ausgeführt, sobald die App gestartet wird
     * @param savedInstanceState —
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);  // Seitenmenü
        botNavView = findViewById(R.id.bot_nav_view);  // Tabmenü

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_timeline, R.id.navigation_forecast, R.id.navigation_stats)
                .setDrawerLayout(drawer)
                .build();

        // unsere eigenen Initialisierungsschritte
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(botNavView, navController);

        addMenuItemInNavMenuDrawer();
        setNavigationViewListener();
    }

    /**
     * fügt Elemente zum Seitenmenü hinzu
     */
    private void addMenuItemInNavMenuDrawer() {

        NavigationView navView = findViewById(R.id.nav_view);

        Menu menu = navView.getMenu();
        Menu garageMenu = menu.addSubMenu(0, 0, 0, R.string.garage);

        // fügt Button zum Hinzufügen eines Autos hinzu
        MenuItem itemAddCar = garageMenu.add(0, 1, 0, R.string.add_car);
        itemAddCar.setIcon(R.drawable.ic_baseline_add_24);

        // fügt Button für Einstellungen hinzu
        MenuItem itemSettings = menu.add(0, R.id.action_settings, 0, R.string.settings);
        itemSettings.setIcon(R.drawable.ic_baseline_settings_24);

        // beispielhaftes Hinzufügen von Elementen
        garageMenu.add(0, 2, 0, "Super Item1");
        garageMenu.add("Super Item2");
        garageMenu.add("Super Item3");
    }

    /**
     * setzt einen Listener für das Seitenmenü
     */
    private void setNavigationViewListener() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // --- ab hier nur noch onXYZ-Methoden (Listener)

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

    /**
     * schließt das Seitenmenü, wenn die Zurück-Taste gedrückt wird
     */
    @Override
    public void onBackPressed() {
        // Zurück-Button wurde gedrückt
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();  // ansonsten gib an ursprüngliche Funktion weiter
        }
    }

    /**
     * Behandelt das Drücken einzelner Elemente des Seitenmenüs
     *
     * @param item gedrücktes Element
     * @return true besagt, dass die Methode bis zum Ende ausgeführt wurde
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int item_id = item.getItemId();
        switch (item_id) {

            // Beispiel-Toast zum Hinzufügen eines Autos
            case 1: {
                Toast.makeText(getApplicationContext(), "Add Car", Toast.LENGTH_LONG).show();
                break;
            }
            // Beispiel-Toast zum Öffnen der Einstellungen
            case R.id.action_settings: {
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
                break;
            }
        }
        drawer.closeDrawer(GravityCompat.START);  // Schließen des Seitenmenüs nach Ausführung

        return true;
    }
}
