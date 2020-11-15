package com.example.tankauswertung;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    BottomNavigationView botNavView;
    static Garage garage;
    private AppBarConfiguration mAppBarConfiguration;

    // Konstanten
    final int LAUNCH_NEW_CAR = 2;
    final int LAUNCH_SETTINGS = 3;

    /**
     * ausgeführt, sobald die App gestartet wird
     *
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

        ladeUi();

    }

    // --- static Methoden

    /**
     * Gibt die aktuelle Garage zurück
     *
     * @return aktuelle Garage
     */
    public static Garage getGarage() {
        return garage;
    }

    /**
     * Steuert das Nachtdesign
     *
     * @param aktivieren übergibt den ausgwählten Designmodus
     */
    public static void steuereNachtDesign(int aktivieren) {
        switch (aktivieren) {
            // Tagdesign
            case 0: {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            }
            // Nachtdesign
            case 1: {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            }
            // System-Design erstmal nicht im Design implementiert, da switch verwendet (siehe fragment_settings)
            case 2: {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            }
        }

    }

    // --- non-static Methoden

    /**
     * lädt Garage und entsprechende UI-Elemente
     */
    private void ladeUi() {
        // lade Garage
        garage = new Garage();
        garage.load();


        erstelleSeitenmenue();
        aktualisiereGarageInSeitenmenue();
        aktuellesFahrzeugGewechselt();
    }

    private void aktuellesFahrzeugGewechselt() {
        // TODO: aktualisiere UI-Elemente, die Fahrzeugdaten darstellen, da Auto im Seitenmenü angeklickt wurde
    }

    /**
     * erstellt Grundstruktur des Seitenmenüs
     */
    private void erstelleSeitenmenue() {

        // Prototyp der Methode Menu.add:
        // add(int groupId, int itemId, int order, CharSequence title)

        NavigationView navView = findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        SubMenu garageMenu = menu.addSubMenu(0, 0, 0, R.string.garage);

        // --- Einstellungen
        MenuItem einstellungenButton = menu.add(0, R.id.navigation_settings, 1, R.string.title_settings);
        einstellungenButton.setIcon(R.drawable.ic_baseline_settings_24);
        einstellungenButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivityForResult(intent, LAUNCH_SETTINGS);
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);  // Schließen des Seitenmenüs nach Öffnen der Aktivität
                return true;
            }
        });
    }

    /**
     * setzt die MenuItems mit den Autonamen neu
     */
    private void aktualisiereGarageInSeitenmenue() {

        NavigationView navView = findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();

        MenuItem garageMenuItem = menu.getItem(0);
        SubMenu garageMenu = garageMenuItem.getSubMenu();
        garageMenu.clear();  // erst einmal alle bereits vorhandenen Autos entfernen

        // --- Auto Hinzufügen
        // fügt Button für ein Item hinzu um ein neues Auto anzulegen
        MenuItem autoHinzufuegenButton = garageMenu.add(0, R.id.navigation_new_car, 99, R.string.title_new_car);
        autoHinzufuegenButton.setIcon(R.drawable.ic_baseline_add_24);
        autoHinzufuegenButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getApplicationContext(), NewCarActivity.class);
                startActivityForResult(intent, LAUNCH_NEW_CAR);
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);  // Schließen des Seitenmenüs nach Öffnen der Aktivität
                return true;
            }
        });

        // --- Autos
        // falls Garage nicht leer, mit Fahrzeugen füllen
        if (!garage.isEmpty()) {

            int anzahlFahrzeuge = garage.getAnzFahrzeuge();
            for (int id = 0; id < anzahlFahrzeuge; id++) {

                Fahrzeug aktuellesFahrzeug = garage.getFahrzeugById(id);
                MenuItem neuesAuto = garageMenu.add(0, id, 0, aktuellesFahrzeug.getName());
                neuesAuto.setIcon(R.drawable.ic_baseline_directions_car_24);

                int autoId = id;  // explizit gesetzt, da ansonsten aus innerer Klasse kein Zugriff

                neuesAuto.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // TODO: Auto wurde angeklickt -> in Dashboard laden
                        garage.setAusgewaehltesFahrzeugById(autoId);
                        aktuellesFahrzeugGewechselt();
                        return false;
                    }
                });
            }
        }
    }

    // --- ab hier nur noch Listener (onXYZ-Methoden)

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
     * hier bekommen wir unsere Ergebnisse, nachdem eine Aktivität geschlossen wurde
     * @param requestCode Code, mit dem die Aktivität aufgerufen wurde (z. B. LAUNCH_NEW_CAR)
     *                    (siehe auch https://stackoverflow.com/a/10407371)
     * @param resultCode Ergebniscode
     * @param data zurückgegebener Intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LAUNCH_NEW_CAR) {
            if (resultCode == Activity.RESULT_OK) {
                aktualisiereGarageInSeitenmenue();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // TODO: alte Funktion, nicht mehr noc
    // TODO: default muss in die individuellen OnClickListener übertragen werden (zeile 110) und anschließend gelöscht inkl. implements

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

            default: {
                // --- Testausgabe
                String msg = "Auto " + item_id + " gedrückt";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                // ---

                break;
            }
        }
        drawer.closeDrawer(GravityCompat.START);  // Schließen des Seitenmenüs nach Ausführung

        return true;
    }
}
