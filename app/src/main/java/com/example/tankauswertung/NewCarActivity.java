package com.example.tankauswertung;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.appcompat.widget.Toolbar;

import com.example.tankauswertung.exceptions.GarageVollException;

import java.util.HashMap;
import java.util.Map;

public class NewCarActivity extends AppCompatActivity {

    boolean korrekteEingabe = false;
    Map<String, Boolean> korrekteEinzeleingaben = new HashMap<String, Boolean>() {{
        put("name", false);
        put("co2", true);
        put("kilometerstand", true);
        put("tankvolumen", true);
    }};

    // UI-Elemente
    EditText editTextName;
    EditText editTextCo2;
    EditText editTextKilometerstand;
    EditText editTextTankvolumen;
    SeekBar seekBarVerbrauchInnerorts;
    SeekBar seekBarVerbrauchAusserorts;
    SeekBar seekBarVerbrauchKombiniert;
    SeekBar seekBarAktuellerTankstand;

    Intent returnIntent = new Intent();

    /**
     * ausgeführt, sobald die Aktivität gestartet wird
     *
     * @param savedInstanceState —
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        supportInvalidateOptionsMenu();

        editTextName = findViewById(R.id.editTextName);
        editTextCo2 = findViewById(R.id.editTextCo2);
        editTextKilometerstand = findViewById(R.id.editTextKilometerstand);
        editTextTankvolumen = findViewById(R.id.editTextTankvolumen);
        seekBarVerbrauchInnerorts = findViewById(R.id.seekBarVerbrauchInnerorts);
        seekBarVerbrauchAusserorts = findViewById(R.id.seekBarVerbrauchAusserorts);
        seekBarVerbrauchKombiniert = findViewById(R.id.seekBarVerbrauchKombiniert);
        seekBarAktuellerTankstand = findViewById(R.id.seekBarAktuellerTankstand);

        // zeigt den Zurück-Button an
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // --- EditText Listener (inkl. Fehlerüberprüfung)

        editTextName.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editTextName.getText())) {
                    editTextName.setError("Bitte geben Sie einen Fahrzeugnamen an");
                    korrekteEinzeleingaben.put("name", false);
                    updateKorrekteEingabe();
                } else {
                    korrekteEinzeleingaben.put("name", true);
                }
                updateKorrekteEingabe();
            }
        });

        editTextCo2.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editTextCo2.getText())) {
                    editTextCo2.setError("Bitte geben Sie einen CO2-Ausstoß an");
                    korrekteEinzeleingaben.put("co2", false);
                } else {
                    korrekteEinzeleingaben.put("co2", true);
                }
                updateKorrekteEingabe();
            }
        });

        editTextKilometerstand.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editTextKilometerstand.getText())) {
                    editTextKilometerstand.setError("Bitte geben Sie den aktuellen Kilometerstand Ihres Fahrzeugs an");
                    korrekteEinzeleingaben.put("kilometerstand", false);
                } else {
                    korrekteEinzeleingaben.put("kilometerstand", true);
                }
                updateKorrekteEingabe();
            }
        });

        editTextTankvolumen.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editTextTankvolumen.getText())) {
                    editTextTankvolumen.setError("Bitte geben Sie das Tankvolumen Ihres Fahrzeugs an");
                    korrekteEinzeleingaben.put("tankvolumen", false);
                } else {
                    korrekteEinzeleingaben.put("tankvolumen", true);
                }
                updateKorrekteEingabe();
            }
        });

        editTextName.setText("");  // so wird der Listener zu Beginn ausgelöst
        editTextName.setError(null);  // so wird zwar der Haken ausgegraut, aber zu Beginn kein Fehler angezeigt (bessere UX!)

        // --- SeekBar Listener

        seekBarVerbrauchInnerorts.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView progressLabel = findViewById(R.id.labelVerbrauchInnerortsStand);
                progressLabel.setText(String.valueOf(i));
            }
        });

        seekBarVerbrauchAusserorts.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView progressLabel = findViewById(R.id.labelVerbrauchAusserortsStand);
                progressLabel.setText(String.valueOf(i));
            }
        });

        seekBarVerbrauchKombiniert.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView progressLabel = findViewById(R.id.labelVerbrauchKombiniertStand);
                progressLabel.setText(String.valueOf(i));
            }
        });

        seekBarAktuellerTankstand.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView progressLabel = findViewById(R.id.labelAktuellerTankstandStand);
                progressLabel.setText(String.valueOf(i));
            }
        });

        editTextName.requestFocus();  // zu Beginn Fokus auf Namensfeld
    }

    /**
     * updated die Variable korrekte Eingabe auf Basis der Korrektheit der Einzeleingaben
     */
    private void updateKorrekteEingabe() {
        korrekteEingabe = true;  // alles gültig
        for (Map.Entry<String, Boolean> einzeleingabe : korrekteEinzeleingaben.entrySet()) {
            if (!einzeleingabe.getValue()) {  // eine falsche Eingabe -> alles ungültig
                korrekteEingabe = false;
                break;
            }
        }
        invalidateOptionsMenu();
    }

    /**
     * Backend, um Fahrzeug hinzuzufügen
     */
    private boolean fahrzeugHinzufuegen() {

        boolean fahrzeugErstellt = false;  // Fahrzeugerstellung fehlerhaft oder nicht
        Garage garage = MainActivity.getGarage();

        if (korrekteEingabe) {  // korrekte Eingaben getätigt

            // Parsing
            String name = editTextName.getText().toString();
            double co2 = Double.parseDouble(editTextCo2.getText().toString());
            double kilometerstand = Double.parseDouble(editTextKilometerstand.getText().toString());
            double tankvolumen = Double.parseDouble(editTextTankvolumen.getText().toString());
            int verbrauchInnerorts = seekBarVerbrauchInnerorts.getProgress();
            int verbrauchAusserorts = seekBarVerbrauchAusserorts.getProgress();
            int verbrauchKombiniert = seekBarVerbrauchKombiniert.getProgress();
            int aktuellerTankstand = seekBarAktuellerTankstand.getProgress();

            Fahrzeug neuesFahrzeug = new Fahrzeug(
                    name, false, verbrauchAusserorts, verbrauchInnerorts, verbrauchKombiniert,
                    kilometerstand, aktuellerTankstand, co2, tankvolumen
            );

            try {
                garage.fahrzeugHinzufuegen(neuesFahrzeug);
                Toast.makeText(this, "Fahrzeug wurde erfolgreich erstellt", Toast.LENGTH_LONG).show();
                fahrzeugErstellt = true;
            } catch (GarageVollException e) {
                Toast.makeText(this, "Fahrzeug konnte nicht erstellt werden, Garage ist voll!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
                fahrzeugErstellt = false;
            }
        }

        // result setzen

        if (korrekteEingabe && fahrzeugErstellt) {
            garage.save(getApplicationContext());
            setResult(Activity.RESULT_OK, returnIntent);
        } else {
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }

        return true;
    }

    /**
     * erstellt das Menü (den Fertig-Button) in der ActionBar
     * @param menu Menü
     * @return -
     */
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_car, menu);
        menu.findItem(R.id.action_new_car_done).setEnabled(korrekteEingabe);  // disable or enable check mark
        return true;
    }

    /**
     * lässt den Fertig-Button funktionieren
     * @param item geklicktes MenuItem
     * @return -
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_car_done) {
            boolean hatFunktioniert = fahrzeugHinzufuegen();
            if (hatFunktioniert) {
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                return true;
            }
        }
        setResult(Activity.RESULT_CANCELED, returnIntent);
        return false;
    }

    /**
     * lässt den Zurück-Button funktionieren
     * @return -
     */
    @Override
    public boolean onSupportNavigateUp() {
        setResult(Activity.RESULT_CANCELED, returnIntent);
        onBackPressed();
        return true;
    }
}
