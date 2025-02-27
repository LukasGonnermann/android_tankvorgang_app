package com.example.tankauswertung;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tankauswertung.exceptions.FahrzeugWertException;
import com.example.tankauswertung.ui.timeline.TimelineFragment;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NewStreckeActivity extends AppCompatActivity {

    boolean korrekteEingabe = true;
    Map<String, Boolean> korrekteEinzeleingaben = new HashMap<String, Boolean>() {{
        put("kilometerstand", true);
        put("tankstand", true);
    }};

    // UI-Elemente
    EditText editTextKilometerstand;
    SeekBar seekBarAktuellerTankstand;
    RadioGroup radioGroupStreckenTyp;
    int radioButtonInnerortsId;
    int radioButtonAusserortsId;
    int radioButtonKombiniertId;
    TextView labelAktuellerTankstandTitel;

    Intent intent;
    Garage garage;

    InputParser inputParser = new InputParser();

    // Decimal Formatter
    DecimalFormat dfKmstandError = new DecimalFormat("###,###.#\u00A0km", new DecimalFormatSymbols(Locale.GERMAN));
    DecimalFormat dfKmstandEditText = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.GERMAN));

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

        editTextKilometerstand = findViewById(R.id.editTextStreckeHinzufuegenKilometerstand);
        seekBarAktuellerTankstand = findViewById(R.id.seekBarStreckeHinzufuegenAktuellerTankstand);
        radioGroupStreckenTyp = findViewById(R.id.radioGroupStreckentyp);
        radioButtonInnerortsId = findViewById(R.id.radioButtonInnerorts).getId();
        radioButtonAusserortsId = findViewById(R.id.radioButtonAusserorts).getId();
        radioButtonKombiniertId = findViewById(R.id.radioButtonKombiniert).getId();
        labelAktuellerTankstandTitel = findViewById(R.id.labelStreckeHinzufuegenAktuellerTankstandTitel);

        // zeigt den Zurück-Button an
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // --- EditText Listener (inkl. Fehlerüberprüfung)

        editTextKilometerstand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                Fahrzeug aktuellesFahrzeug = garage.getAusgewaehltesFahrzeug();
                double alterKilometerstand;

                if (intent.getAction().equals(TimelineFragment.ACTION_NEW_STRECKE)) {
                    alterKilometerstand = aktuellesFahrzeug.getKmStand();
                } else {  // beim Bearbeiten muss der alte Kilometerstand erst rückgerechnet werden
                    Strecke neuesteStrecke = aktuellesFahrzeug.getStrecken().get(0);
                    alterKilometerstand = aktuellesFahrzeug.getKmStand() - neuesteStrecke.getDistanz();
                }

                String string = editable.toString();
                double parsedDouble = inputParser.parse(string);

                korrekteEinzeleingaben.put("kilometerstand", false);

                if (string.isEmpty()) {
                    editTextKilometerstand.setError("Bitte geben Sie den aktuellen Kilometerstand Ihres Fahrzeugs ein.");

                } else if (!inputParser.isValid()) {
                    editTextKilometerstand.setError("Bitte geben Sie einen gültigen Wert ein.");

                } else if (parsedDouble < alterKilometerstand) {

                    editTextKilometerstand.setError(
                            "Bitte geben Sie einen Kilometerstand ein, der größer oder gleich dem " +
                            "letzten Kilometerstand Ihres Fahrzeugs (" +
                            dfKmstandError.format(alterKilometerstand) +  ") ist.");

                } else {
                    korrekteEinzeleingaben.put("kilometerstand", true);
                }
                updateKorrekteEingabe();
            }
        });

        seekBarAktuellerTankstand.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        // Label für Elektroauto ändern
        if (garage.getAusgewaehltesFahrzeug().isElektro()) {
            labelAktuellerTankstandTitel.setText(R.string.restladung_prozent);
        }

        Fahrzeug aktuellesFahrzeug = garage.getAusgewaehltesFahrzeug();

        // Maximum setzen: dafür alten Tankstand in Prozent berechnen
        double alterTankstandProzent;

        if (intent.getAction().equals(TimelineFragment.ACTION_NEW_STRECKE)) {
            alterTankstandProzent = aktuellesFahrzeug.getTankstand();
        } else {
            Strecke neuesteStrecke = aktuellesFahrzeug.getStrecken().get(0);
            alterTankstandProzent = (neuesteStrecke.getTankstand() + neuesteStrecke.getVerbrauchterTreibstoff())
                    / aktuellesFahrzeug.getTankgroesse() * 100;
        }
        seekBarAktuellerTankstand.setMax((int) alterTankstandProzent);

        // --- Default-Werte setzen

        if (intent.getAction().equals(TimelineFragment.ACTION_NEW_STRECKE)) {

            editTextKilometerstand.setText(dfKmstandEditText.format(
                    aktuellesFahrzeug.getKmStand()
            ));

            seekBarAktuellerTankstand.setProgress((int) aktuellesFahrzeug.getTankstand());

        } else if (intent.getAction().equals(TimelineFragment.ACTION_EDIT_STRECKE)) {

            setTitle(R.string.edit_strecke);  // Titel "Strecke bearbeiten" setzen

            Strecke neuesteStrecke = aktuellesFahrzeug.getStrecken().get(0);

            editTextKilometerstand.setText(dfKmstandEditText.format(
                    aktuellesFahrzeug.getKmStand()
            ));
            seekBarAktuellerTankstand.setProgress((int) (neuesteStrecke.getTankstand() / aktuellesFahrzeug.getTankgroesse() * 100));

                 // korrekten RadioButton abhaken
            if (neuesteStrecke.getStreckentyp().equals(Strecke.Streckentyp.INNERORTS)) {
                radioGroupStreckenTyp.check(radioButtonInnerortsId);
            } else if (neuesteStrecke.getStreckentyp().equals(Strecke.Streckentyp.AUSSERORTS)) {
                radioGroupStreckenTyp.check(radioButtonAusserortsId);
            } else if (neuesteStrecke.getStreckentyp().equals(Strecke.Streckentyp.KOMBINIERT)) {
                radioGroupStreckenTyp.check(radioButtonKombiniertId);
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
    private void fertigButtonGedrueckt() {

        if (korrekteEingabe) {  // korrekte Eingaben getätigt

            // Parsing
            double kilometerstand = inputParser.parse(editTextKilometerstand.getText().toString());
            int aktuellerTankstandProzent = seekBarAktuellerTankstand.getProgress();
            Strecke.Streckentyp streckentyp;

            // angekreuzten RadioButton sichern
            int checkedRadioButtonId = radioGroupStreckenTyp.getCheckedRadioButtonId();

            if (checkedRadioButtonId == radioButtonInnerortsId) {
                streckentyp = Strecke.Streckentyp.INNERORTS;
            } else if (checkedRadioButtonId == radioButtonAusserortsId) {
                streckentyp = Strecke.Streckentyp.AUSSERORTS;
            } else {
                streckentyp = Strecke.Streckentyp.KOMBINIERT;
            }

            if (intent.getAction().equals(TimelineFragment.ACTION_NEW_STRECKE)) {  // Strecke hinzufügen

                garage.getAusgewaehltesFahrzeug().streckeHinzufuegen(
                        kilometerstand, streckentyp, aktuellerTankstandProzent
                );

            } else if (intent.getAction().equals(TimelineFragment.ACTION_EDIT_STRECKE)) {  // Strecke bearbeiten

                Fahrzeug aktuellesFahrzeug = garage.getAusgewaehltesFahrzeug();
                Strecke neuesteStrecke = aktuellesFahrzeug.getStrecken().get(0);

                // da man nur eine Distanz setzen kann
                double alterKilometerstand = aktuellesFahrzeug.getKmStand() - neuesteStrecke.getDistanz();

                double aktuellerTankstand = aktuellerTankstandProzent / 100.0 * aktuellesFahrzeug.getTankgroesse();
                double alterTankstand = neuesteStrecke.getTankstand() + neuesteStrecke.getVerbrauchterTreibstoff();
                double distanz = kilometerstand - alterKilometerstand;

                try {
                    aktuellesFahrzeug.setKmStand(kilometerstand);
                    aktuellesFahrzeug.setTankstand(aktuellerTankstandProzent);
                } catch (FahrzeugWertException e) {
                    e.printStackTrace();
                }

                neuesteStrecke.streckeBearbeiten(
                        distanz, streckentyp, aktuellerTankstand

                );

                neuesteStrecke.setVerbrauchterTreibstoff(alterTankstand - aktuellerTankstand);
                aktuellesFahrzeug.verbrauchAktualisieren();
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
            fertigButtonGedrueckt();
            finish();
            return true;
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
