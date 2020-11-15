package com.example.tankauswertung;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class SettingsActivity extends AppCompatActivity {


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

        final Switch switchDarkMode = findViewById(R.id.switchDarkMode);

        switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked) {
                    MainActivity.steuereNachtDesign(1);
                } else {
                    MainActivity.steuereNachtDesign(2);
                }
            }
        });

//        --- dunno wofür das ist, deswegen auskommentiert
//        SettingsViewModel settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
//        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            }
//        });

    }

    /**
     * lässt den Zurück-Button funktionieren
     * @return -
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
