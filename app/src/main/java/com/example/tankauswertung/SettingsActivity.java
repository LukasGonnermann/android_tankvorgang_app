package com.example.tankauswertung;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class SettingsActivity extends AppCompatActivity {

    // Auswahlmoeglichkeiten im Menue
    private static final String[] items = {"Hell", "Dunkel", "Systemeinstellung folgen"};
    // Speicher der aktuell ausgewaehlten Option
    int selectedItem = 0;

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SettingsActivity.this,
                android.R.layout.simple_spinner_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(selectedItem, true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                MainActivity.steuereNachtDesign(position);
                selectedItem = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // wird nicht benoetigt, muss aber ueberschrieben werden
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
