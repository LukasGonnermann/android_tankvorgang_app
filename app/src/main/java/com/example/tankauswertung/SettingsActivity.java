package com.example.tankauswertung;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.mikepenz.aboutlibraries.LibsBuilder;


public class SettingsActivity extends AppCompatActivity {

    // Auswahlmoeglichkeiten im Menue
    private static String[] items = {"Systemeinstellung folgen", "Hell", "Dunkel"};
    // Speicher der aktuell ausgewaehlten Option
    int selectedItem;
    
    Settings settings;

    /**
     * ausgeführt, sobald die Aktivität gestartet wird
     *
     * @param savedInstanceState —
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // zeigt den Zurück-Button an
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinner = findViewById(R.id.spinnerDarkMode);
        Button buttonLizenzen = findViewById(R.id.buttonLizenzen);

        // falls Android-Version < 9.0 (Pie), ist MODE_NIGHT_FOLLOW_SYSTEM nicht verfügbar
        if (Build.VERSION.SDK_INT < 28) {
            items = new String[]{"Hell", "Dunkel"};
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(SettingsActivity.this,
                android.R.layout.simple_spinner_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        settings = MainActivity.getSettings();

        switch (settings.getDarkModeStatus()) {
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM: {
                selectedItem = 0;
                break;
            }
            case AppCompatDelegate.MODE_NIGHT_NO: {
                selectedItem = 1;
                break;
            }
            case AppCompatDelegate.MODE_NIGHT_YES: {
                selectedItem = 2;
                break;
            }
        }

        // da MODE_NIGHT_FOLLOW_SYSTEM nicht verfügbar, siehe Aufbau der Arrays "items"
        if (Build.VERSION.SDK_INT < 28) {
            selectedItem--;
        }
        
        spinner.setSelection(selectedItem, true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int selectedItem, long l) {
                
                int darkModeStatus;

                switch (selectedItem) {
                    case 0:
                    default:
                        if (Build.VERSION.SDK_INT < 28) {
                            darkModeStatus = AppCompatDelegate.MODE_NIGHT_NO;
                        } else {
                            darkModeStatus = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
                        }
                        break;
                    case 1:
                        if (Build.VERSION.SDK_INT < 28) {
                            darkModeStatus = AppCompatDelegate.MODE_NIGHT_YES;
                        } else {
                            darkModeStatus = AppCompatDelegate.MODE_NIGHT_NO;
                        }
                        break;
                    case 2:
                        darkModeStatus = AppCompatDelegate.MODE_NIGHT_YES;
                        break;
                }

                MainActivity.steuereDarkMode(darkModeStatus);
                settings.setDarkModeStatus(darkModeStatus);
                settings.save(getApplicationContext());
                SettingsActivity.this.selectedItem = selectedItem;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // wird nicht benoetigt, muss aber ueberschrieben werden
            }
        });

        buttonLizenzen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LibsBuilder()
                        .withAboutIconShown(false)
                        .withAboutVersionShown(false)
                        .withActivityTitle("Lizenzen")
                        .start(getApplicationContext());
            }
        });
    }

    /**
     * Aktion fuer Zurueck-Button (Pfeil)
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
