package com.example.tankauswertung;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

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

import com.example.tankauswertung.exceptions.GarageLeerException;
import com.example.tankauswertung.exceptions.GarageNullPointerException;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView botNavView;
    NavController navController;
    private AppBarConfiguration mAppBarConfiguration;
    static Garage garage;
    static Settings settings;

    // Activity request codes ("Which activity should be invoked?")
    final int LAUNCH_NEW_CAR_EDIT_CAR = 2;
    final int LAUNCH_SETTINGS = 3;

    // Activity action codes ("What should be done in the activity?")
    // Wir nutzen die gleiche Aktivität zum Hinzufügen und Bearbeiten eines Autos, deshalb müssen
    // wir mit intent.setAction(...) dazwischen differenzieren.
    // (Request-Codes kann man leider in einer Aktivität nicht gut abfragen.)
    final static String ACTION_NEW_CAR = "new_car";
    final static String ACTION_EDIT_CAR = "edit_car";

    // eine Liste an Möglichkeiten, wer die Methode "garageGeaendert()" aufgerufen hat
    private enum GarageGeaendertCaller {
        ACTIVITY_START, FAHRZEUG_HINZUGEFUEGT, FAHRZEUG_GELOESCHT, FAHRZEUG_GEAENDERT
    }
    boolean activityStarted = false;

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

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);  // Seitenmenü
        botNavView = findViewById(R.id.bot_nav_view);  // Tabmenü

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_timeline, R.id.navigation_forecast, R.id.navigation_stats)
                .setOpenableLayout(drawerLayout)
                .build();

        // unsere eigenen Initialisierungsschritte
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(botNavView, navController);

        // lade Garage
        // da getGarage() static ist, darf kein neues Garage-Objekt erstellt werden, wenn schon eines erstellt wurde
        if (!activityStarted) {
            garage = new Garage();
            garage.load(getApplicationContext());
            settings = new Settings();
            settings.load(getApplicationContext());

            steuereDarkMode(settings.getDarkModeStatus());

            activityStarted = true;
            ladeUi();
        }
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
     * Gibt die aktuellen Einstellungen zurück
     *
     * @return aktuelle Einstellungen
     */
    public static Settings getSettings() {
        return settings;
    }

    /**
     * Steuert das Nachtdesign
     *
     * @param darkModeStatus übergibt den ausgwählten Designmodus
     */
    public static void steuereDarkMode(int darkModeStatus) {
        AppCompatDelegate.setDefaultNightMode(darkModeStatus);
    }

    // --- non-static Methoden

    /**
     * lädt Garage und entsprechende UI-Elemente
     */
    private void ladeUi() {
        // lade Garage
        garageGeaendert(GarageGeaendertCaller.ACTIVITY_START);
    }

    /**
     * es hat eine Änderung an der Garage gegeben
     */
    private void garageGeaendert(GarageGeaendertCaller caller) {

        if (caller != GarageGeaendertCaller.ACTIVITY_START) {
            garage.save(getApplicationContext());
        }

        if (garage.isEmpty()) {
            botNavView.setVisibility(View.INVISIBLE);
            navController.navigate(R.id.navigation_dashboard);

        } else {

            if (caller == GarageGeaendertCaller.ACTIVITY_START
             || caller == GarageGeaendertCaller.FAHRZEUG_GELOESCHT) {  // bei Start der App und nach Löschen immer erstes Fahrzeug nehmen

                try {
                    garage.setAusgewaehltesFahrzeugById(0);
                } catch (GarageNullPointerException e) {
                    e.printStackTrace();
                }
                navController.navigate(R.id.navigation_dashboard);

            } else if (caller == GarageGeaendertCaller.FAHRZEUG_HINZUGEFUEGT) {  // nach Hinzufügen immer das neueste/letzte Fahrzeug nehmen

                try {
                    garage.setAusgewaehltesFahrzeugById(garage.getAnzFahrzeuge() - 1);
                } catch (GarageNullPointerException e) {
                    e.printStackTrace();
                }

            }
            // keine Änderung, falls nur Fahrzeugdaten geändert

            botNavView.setVisibility(View.VISIBLE);
        }

        aktualisiereSeitenmenue();
        invalidateOptionsMenu();  // Drei-Punkte-Menü auf jeden Fall aktualisieren
        refresh();
    }

    /**
     * setzt die MenuItems mit den Autonamen neu
     * NICHT AUFRUFEN, stattdessen garageWurdeGeaendert(), um alle UI-Elemente zu aktualisieren
     */
    private void aktualisiereSeitenmenue() {

        NavigationView navView = findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        menu.clear();  // erst einmal alle bereits vorhandenen Items entfernen

        menu.addSubMenu("");  // Dummy für Linie

        // SubMenu für Garage hinzufügen
        SubMenu garageMenu = menu.addSubMenu(0, 0, 0, R.string.garage);

        // --- Auto Hinzufügen
        // fügt Button für ein Item hinzu um ein neues Auto anzulegen
        // Prototyp der Methode Menu.add:
        // add(int groupId, int itemId, int order, CharSequence title)
        MenuItem autoHinzufuegenButton = garageMenu.add(0, R.id.navigation_new_car, 99, R.string.title_new_car);
        autoHinzufuegenButton.setIcon(R.drawable.ic_baseline_add_24);
        autoHinzufuegenButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getApplicationContext(), NewCarActivity.class);
                intent.setAction(ACTION_NEW_CAR);
                startActivityForResult(intent, LAUNCH_NEW_CAR_EDIT_CAR);
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);  // Schließen des Seitenmenüs nach Öffnen der Aktivität
                return true;
            }
        });

        // --- Autos
        // falls Garage nicht leer, Menü mit Fahrzeugen füllen
        if (!garage.isEmpty()) {

            int anzahlFahrzeuge = garage.getAnzFahrzeuge();
            for (int id = 0; id < anzahlFahrzeuge; id++) {

                Fahrzeug aktuellesFahrzeug = null;
                try {
                    aktuellesFahrzeug = garage.getFahrzeugById(id);
                } catch (GarageNullPointerException e) {
                    e.printStackTrace();
                }
                MenuItem neuesAuto = garageMenu.add(0, id, 0, aktuellesFahrzeug.getName());
                neuesAuto.setIcon(R.drawable.ic_baseline_directions_car_24);

                int autoId = id;  // explizit gesetzt, da ansonsten aus innerer Klasse kein Zugriff

                neuesAuto.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        try {
                            garage.setAusgewaehltesFahrzeugById(autoId);
                        } catch (GarageNullPointerException e) {
                            e.printStackTrace();
                        }

                        refresh();
                        DrawerLayout drawer = findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);  // Schließen des Seitenmenüs

                        return true;
                    }
                });
            }
        }

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
     * lädt das aktuelle Fragment neu (behebt den Bug nach dem Hinzufügen eines Fahrzeugs etc.)
     */
    public void refresh() {
        onResume();
        navController.navigate(navController.getCurrentDestination().getId());
    }

    // --- ab hier nur noch Listener (onXYZ-Methoden)

    /**
     * Wird aufgerufen beim Start und wenn invalidateOptionsMenu() aufgerufen wird.
     * lässt die Bearbeitung des Drei-Punkte-Menüs zu
     * @param menu Menü
     * @return -
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // Prototyp der Methode Menu.add:
        // add(int groupId, int itemId, int order, CharSequence title)

        menu.clear();

        MenuItem editButton = menu.add(0, R.id.action_edit_car, 0, R.string.edit_car);
        MenuItem removeButton = menu.add(0, R.id.action_remove_car, 1, R.string.remove_car);

        editButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        removeButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        editButton.setIcon(R.drawable.ic_baseline_edit_24);
        removeButton.setIcon(R.drawable.ic_baseline_delete_24);

        // falls keine Fahrzeuge da, Bearbeiten-Button nicht anzeigen
        editButton.setVisible(!garage.isEmpty());

        // falls keine Fahrzeuge da, soll auch keins löschbar sein
        removeButton.setVisible(!garage.isEmpty());

        // OnClickListener setzen
        editButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(getApplicationContext(), NewCarActivity.class);
                intent.setAction(ACTION_EDIT_CAR);
                startActivityForResult(intent, LAUNCH_NEW_CAR_EDIT_CAR);
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);  // Schließen des Seitenmenüs nach Öffnen der Aktivität
                return true;
            }
        });

        removeButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // noch mal nachfragen, bevor wir löschen
                String name = garage.getAusgewaehltesFahrzeug().getName();
                AlertDialog dialogEntfernBestaetigung = new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.remove_car)
                        .setMessage("Sind Sie sicher, dass Sie das Fahrzeug \"" + name + "\" entfernen wollen?")
                        .setIcon(R.drawable.ic_baseline_warning_24)

                        .setPositiveButton("Entfernen", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int whichButton) {
                                try {
                                    garage.fahrzeugLoeschen(garage.getAusgewaehltesFahrzeug());
                                } catch (GarageLeerException e) {
                                    e.printStackTrace();
                                }

                                garageGeaendert(GarageGeaendertCaller.FAHRZEUG_GELOESCHT);
                                dialogInterface.dismiss();

                            }
                        })

                        .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })

                        .create();

                dialogEntfernBestaetigung.show();

                return true;
            }
        });

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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();  // ansonsten gib an ursprüngliche Funktion weiter
        }
    }

    /**
     * hier bekommen wir unsere Ergebnisse, nachdem eine Aktivität geschlossen wurde
     * @param requestCode Code, mit dem die Aktivität aufgerufen wurde (z. B. LAUNCH_NEW_CAR)
     *                    (siehe auch https://stackoverflow.com/a/10407371)
     * @param resultCode Ergebniscode
     * @param intent zurückgegebener Intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);

        // Auto hinzugefügt oder geändert
        if (requestCode == LAUNCH_NEW_CAR_EDIT_CAR) {

            if (intent.getAction().equals(ACTION_NEW_CAR)) {  // Auto hinzugefügt
                if (resultCode == Activity.RESULT_OK) {
                    garageGeaendert(GarageGeaendertCaller.FAHRZEUG_HINZUGEFUEGT);
                }
            } else if (intent.getAction().equals(ACTION_EDIT_CAR)) {  // Auto geändert
                if (resultCode == Activity.RESULT_OK) {
                    garageGeaendert(GarageGeaendertCaller.FAHRZEUG_GEAENDERT);
                }
            }

        } else if (requestCode == LAUNCH_SETTINGS) {
            refresh();
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // dynamisch geregelt, nicht mehr gebraucht
        return true;
    }
}
