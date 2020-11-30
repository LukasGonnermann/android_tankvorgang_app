package com.example.tankauswertung;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tankauswertung.ui.timeline.TimelineFragment;

import java.util.HashMap;
import java.util.Map;

public class NewStreckeActivity extends AppCompatActivity {

    boolean korrekteEingabe = true;
    Map<String, Boolean> korrekteEinzeleingaben = new HashMap<String, Boolean>() {{
        put("kilometerstand", true);
    }};

    // UI-Elemente
    EditText editTextStreckeHinzufuegenKilometerstand;
    SeekBar seekBarStreckeHinzufuegenAktuellerTankstand;
    RadioButton radioButtonInnerorts;
    RadioButton radioButtonAusserorts;
    RadioButton radioButtonKombiniert;

    Intent intent;
    Garage garage;

    /**
     * ausgeführt, sobald die Aktivität gestartet wird
     *
     * @param savedInstanceState —
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_strecke);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        supportInvalidateOptionsMenu();

        intent = getIntent();  // erhalte Intent vom Aufruf
        garage = MainActivity.getGarage();  // erhalte Garagenobjekt

        editTextStreckeHinzufuegenKilometerstand = findViewById(R.id.editTextStreckeHinzufuegenKilometerstand);
        seekBarStreckeHinzufuegenAktuellerTankstand = findViewById(R.id.seekBarStreckeHinzufuegenAktuellerTankstand);
        radioButtonInnerorts = findViewById(R.id.radioButtonInnerorts);
        radioButtonAusserorts = findViewById(R.id.radioButtonAusserorts);
        radioButtonKombiniert = findViewById(R.id.radioButtonKombiniert);

        // zeigt den Zurück-Button an
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // --- EditText Listener (inkl. Fehlerüberprüfung)

        editTextStreckeHinzufuegenKilometerstand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                Fahrzeug aktuellesFahrzeug = garage.getAusgewaehltesFahrzeug();
                Editable kilometerStandText = editTextStreckeHinzufuegenKilometerstand.getText();

                if (TextUtils.isEmpty(kilometerStandText)) {

                    editTextStreckeHinzufuegenKilometerstand.setError("Bitte geben Sie den aktuellen Kilometerstand Ihres Fahrzeugs an");
                    korrekteEinzeleingaben.put("kilometerstand", false);

                } else if (Double.parseDouble(kilometerStandText.toString()) < aktuellesFahrzeug.getKmStand()) {

                    editTextStreckeHinzufuegenKilometerstand.setError(
                            "Bitte geben Sie einen Kilometerstand an, der größer oder gleich dem " +
                            "letzten Kilometerstand Ihres Fahrzeugs (" +
                            aktuellesFahrzeug.getKmStand() +  ") ist.");
                    korrekteEinzeleingaben.put("kilometerstand", false);

                } else {
                    korrekteEinzeleingaben.put("kilometerstand", true);
                }
                updateKorrekteEingabe();
            }
        });

        seekBarStreckeHinzufuegenAktuellerTankstand.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView progressLabel = findViewById(R.id.labelStreckeHinzufuegenAktuellerTankstandStand);
                progressLabel.setText(String.valueOf(i));
            }
        });

        // --- Default-Werte setzen

         if (intent.getAction().equals(TimelineFragment.ACTION_EDIT_STRECKE)) {

             setTitle(R.string.edit_strecke);  // Titel "Strecke bearbeiten" setzen
             Fahrzeug aktuellesFahrzeug = garage.getAusgewaehltesFahrzeug();
             Strecke neuesteStrecke = aktuellesFahrzeug.getStrecken().get(0);

             editTextStreckeHinzufuegenKilometerstand.setText(Double.toString(
                     aktuellesFahrzeug.getKmStand() - neuesteStrecke.getDistanz()
             ));
             seekBarStreckeHinzufuegenAktuellerTankstand.setProgress((int) neuesteStrecke.getTankstand());

             // RadioButtons korrekt abhaken
             radioButtonInnerorts.setChecked(false);
             radioButtonAusserorts.setChecked(false);
             radioButtonKombiniert.setChecked(false);

             if (neuesteStrecke.getStreckentyp().equals(Strecke.Streckentyp.INNERORTS)) {
                 radioButtonInnerorts.setChecked(true);
             } else if (neuesteStrecke.getStreckentyp().equals(Strecke.Streckentyp.AUSSERORTS)) {
                 radioButtonAusserorts.setChecked(true);
             } else if (neuesteStrecke.getStreckentyp().equals(Strecke.Streckentyp.KOMBINIERT)) {
                 radioButtonKombiniert.setChecked(true);
             }
        }
    }

    /**
     * aktualisiert die Variable korrekteEingabe auf Basis der Korrektheit der Einzeleingaben
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
     * Backend, um Daten abzufangen und Strecke zu ändern oder zur Chronik hinzuzufügen
     */
    private boolean fertigButtonGedrueckt() {

        if (korrekteEingabe) {  // korrekte Eingaben getätigt

            // Parsing
            double kilometerstand = Double.parseDouble(editTextStreckeHinzufuegenKilometerstand.getText().toString());
            int aktuellerTankstand = seekBarStreckeHinzufuegenAktuellerTankstand.getProgress();

            Strecke.Streckentyp streckentyp;
            if (radioButtonInnerorts.isChecked()) {
                streckentyp = Strecke.Streckentyp.INNERORTS;
            } else if (radioButtonAusserorts.isChecked()) {
                streckentyp = Strecke.Streckentyp.AUSSERORTS;
            } else {
                streckentyp = Strecke.Streckentyp.KOMBINIERT;
            }

            if (intent.getAction().equals(TimelineFragment.ACTION_NEW_STRECKE)) {  // neue Strecke hinzufügen

                garage.getAusgewaehltesFahrzeug().streckeHinzufuegen(
                        kilometerstand, streckentyp, aktuellerTankstand
                );

            } else if (intent.getAction().equals(TimelineFragment.ACTION_EDIT_STRECKE)) {  // Strecke bearbeiten

                Fahrzeug aktuellesFahrzeug = garage.getAusgewaehltesFahrzeug();

                aktuellesFahrzeug.getStrecken().get(0).streckeBearbeiten(
                        kilometerstand, streckentyp, aktuellerTankstand
                );

            }
        }

        // result setzen

        if (korrekteEingabe) {
            // Garage und damit auch Strecke speichern
            garage.save(getApplicationContext());
            setResult(Activity.RESULT_OK, intent);
        } else {
            setResult(Activity.RESULT_CANCELED, intent);
        }
        return true;
    }

    /**
     * erstellt das Menü (den Fertig-Button) in der ActionBar
     *
     * @param menu Menü
     * @return -
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_strecke, menu);
        menu.findItem(R.id.action_new_strecke_done).setEnabled(korrekteEingabe);  // disable or enable check mark
        return true;
    }

    /**
     * lässt den Fertig-Button funktionieren
     *
     * @param item geklicktes MenuItem
     * @return -
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_strecke_done) {
            boolean hatFunktioniert = fertigButtonGedrueckt();
            if (hatFunktioniert) {
                setResult(Activity.RESULT_OK, intent);
                finish();
                return true;
            }
        }
        setResult(Activity.RESULT_CANCELED, intent);
        return false;
    }

    /**
     * lässt den Zurück-Button funktionieren
     *
     * @return -
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED, intent);
        super.onBackPressed();
    }
}