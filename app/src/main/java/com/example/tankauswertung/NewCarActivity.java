package com.example.tankauswertung;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tankauswertung.exceptions.GarageVollException;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

// Aktivität zum Hinzufügen und zum Ändern (anderer Action-Code)

public class NewCarActivity extends AppCompatActivity {

    boolean korrekteEingabe = false;
    Map<String, Boolean> korrekteEinzeleingaben = new HashMap<String, Boolean>() {{
        put("name", false);
        put("co2", true);
        put("kilometerstand", true);
        put("tankvolumen", true);
        put("verbrauch_innerorts", true);
        put("verbrauch_au_erorts", true);
        put("verbrauch_kombiniert", true);

    }};

    // UI-Elemente
    EditText editTextName;
    EditText editTextCo2;
    EditText editTextKilometerstand;
    EditText editTextTankvolumen;
    EditText editTextVerbrauchInnerortsStand;
    EditText editTextVerbrauchAusserortsStand;
    EditText editTextVerbrauchKombiniertStand;
    SeekBar seekBarVerbrauchInnerorts;
    SeekBar seekBarVerbrauchAusserorts;
    SeekBar seekBarVerbrauchKombiniert;
    SeekBar seekBarAktuellerTankstand;
    CheckBox checkBoxElektro;
    ImageButton imageButtonElektroInfo;

    TextView labelCo2Ausstoss;
    TextView labelKilometerstand;
    TextView labelTankvolumen;
    TextView labelAktuellerTankstandTitel;
    TextView labelAktuellerTankstandStand;

    Intent intent;

    Garage garage;
    /*
    Die Werte werden wenn man auf Fertig drückt aus EditTexts gelesen,
    da die Seekbar geändert wird wenn editText geändert wird und anders herum,
    überschreiben die sich immer, bzw. wenn Seekbar geändert wird,
    fügt die dann im EditText Int ein, da sie nur das kann
     */
    boolean aendern = false;
    boolean hinweis_angezeigt = false;  // gibt an, ob der Hinweis zum E-Auto schon einmal angezeigt wurde

    // Decimal Formatter
    DecimalFormat dfAllgemein = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.GERMAN));

    InputParser inputParser = new InputParser();

    /**
     * ausgeführt, sobald die Aktivität gestartet wird
     *
     * @param savedInstanceState —
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        supportInvalidateOptionsMenu();

        intent = getIntent();  // erhalte Intent vom Aufruf
        garage = MainActivity.getGarage();  // erhalte Garagenobjekt

        editTextName = findViewById(R.id.editTextName);
        editTextCo2 = findViewById(R.id.editTextCo2);
        editTextKilometerstand = findViewById(R.id.editTextKilometerstand);
        editTextTankvolumen = findViewById(R.id.editTextTankvolumen);
        editTextVerbrauchInnerortsStand = findViewById(R.id.editTextVerbrauchInnerortsStand);
        editTextVerbrauchAusserortsStand = findViewById(R.id.editTextVerbrauchAusserortsStand);
        editTextVerbrauchKombiniertStand = findViewById(R.id.editTextVerbrauchKombiniertStand);
        seekBarVerbrauchInnerorts = findViewById(R.id.seekBarVerbrauchInnerorts);
        seekBarVerbrauchAusserorts = findViewById(R.id.seekBarVerbrauchAusserorts);
        seekBarVerbrauchKombiniert = findViewById(R.id.seekBarVerbrauchKombiniert);
        seekBarAktuellerTankstand = findViewById(R.id.seekBarAktuellerTankstand);
        checkBoxElektro = findViewById(R.id.checkBoxElektro);
        imageButtonElektroInfo = findViewById(R.id.imageButtonElektroInfo);

        labelKilometerstand = findViewById(R.id.labelKilometerstand);
        labelTankvolumen = findViewById(R.id.labelTankvolumen);
        labelAktuellerTankstandTitel = findViewById(R.id.labelAktuellerTankstand);
        labelAktuellerTankstandStand = findViewById(R.id.labelAktuellerTankstandStand);
        labelCo2Ausstoss = findViewById(R.id.labelCo2Ausstoss);

        // zeigt den Zurück-Button an
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Dialog vorbauen (2 Benutzungen)
        AlertDialog dialogElektroInfoBestaetigung = new AlertDialog.Builder(NewCarActivity.this)
                .setTitle("Hinweis")
                .setMessage("In dieser App werden Elektrofahrzeuge mit 0 g/km " +
                        "CO2-Emissonen erfasst. Bitte beachten Sie, dass bei der " +
                        "notwendigen Stromerzeugung dennoch CO2-Emissionen verursacht " +
                        "werden.")
                .setIcon(R.drawable.ic_outline_info_24)

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whichButton) {
                        dialogInterface.dismiss();
                    }
                })

                .create();

        // --- CheckBox Listener, ob Elektroauto

        checkBoxElektro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TextView labelVerbrauchInnerortsTitel = findViewById(R.id.labelVerbrauchInnerortsTitel);
                TextView labelVerbrauchAusserortsTitel = findViewById(R.id.labelVerbrauchAusserortsTitel);
                TextView labelVerbrauchKombiniertTitel = findViewById(R.id.labelVerbrauchKombiniertTitel);
                TextView labelTankvolumen = findViewById(R.id.labelTankvolumen);
                TextView labelAktuellerTankstandTitel = findViewById(R.id.labelAktuellerTankstand);

                // ist Elektro
                if (b) {
                    // Hinweis-Dialog nur anzeigen, wenn er nicht schon einmal angezeigt wurde
                    if (!hinweis_angezeigt && !intent.getAction().equals(MainActivity.ACTION_EDIT_CAR)) {
                        dialogElektroInfoBestaetigung.show();
                    }
                    // ändere Maxwerte für die Verbrauchsslider zu 50
                    seekBarVerbrauchAusserorts.setMax(50);
                    seekBarVerbrauchInnerorts.setMax(50);
                    seekBarVerbrauchKombiniert.setMax(50);
                    editTextCo2.setText(R.string.zero_number);
                    hinweis_angezeigt = true;
                    labelVerbrauchInnerortsTitel.setText(R.string.verbrauch_innerorts_kwh_100km);
                    labelVerbrauchAusserortsTitel.setText(R.string.verbrauch_au_erorts_kwh_100km);
                    labelVerbrauchKombiniertTitel.setText(R.string.verbrauch_kombiniert_kwh_100km);
                    labelTankvolumen.setText(R.string.tankvolumen_kwh);
                    labelAktuellerTankstandTitel.setText(R.string.restladung_prozent);
                    editTextCo2.setEnabled(false);

                // ist Verbrenner
                } else {
                    seekBarVerbrauchAusserorts.setMax(30);
                    seekBarVerbrauchInnerorts.setMax(30);
                    seekBarVerbrauchKombiniert.setMax(30);
                    labelVerbrauchInnerortsTitel.setText(R.string.verbrauch_innerorts_l_100km);
                    labelVerbrauchAusserortsTitel.setText(R.string.verbrauch_au_erorts_l_100km);
                    labelVerbrauchKombiniertTitel.setText(R.string.verbrauch_kombiniert_l_100km);
                    labelTankvolumen.setText(R.string.tankvolumen_l);
                    labelAktuellerTankstandTitel.setText(R.string.tankstand_prozent);
                    editTextCo2.setEnabled(true);
                }
            }
        });

        // --- EditText Listener (inkl. Fehlerüberprüfung)

        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editTextName.getText())) {
                    editTextName.setError("Bitte geben Sie einen Fahrzeugnamen an.");
                    korrekteEinzeleingaben.put("name", false);
                } else {
                    korrekteEinzeleingaben.put("name", true);
                }
                updateKorrekteEingabe();
            }
        });

        editTextCo2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String string = editable.toString();
                double parsedDouble = inputParser.parse(string);

                korrekteEinzeleingaben.put("co2", false);

                if (string.isEmpty()) {
                    editTextCo2.setError("Bitte geben Sie den CO2-Ausstoß Ihres Fahrzeugs ein.");
                } else if (!inputParser.isValid()) {
                    editTextCo2.setError("Bitte geben Sie einen gültigen Wert ein.");
                } else {
                    korrekteEinzeleingaben.put("co2", true);
                }
                updateKorrekteEingabe();
            }
        });

        editTextKilometerstand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String string = editable.toString();
                double parsedDouble = inputParser.parse(string);

                korrekteEinzeleingaben.put("kilometerstand", false);

                if (string.isEmpty()) {
                    editTextKilometerstand.setError("Bitte geben Sie den aktuellen Kilometerstand Ihres Fahrzeugs ein.");
                } else if (!inputParser.isValid()) {
                    editTextKilometerstand.setError("Bitte geben Sie einen gültigen Wert ein.");
                } else {
                    korrekteEinzeleingaben.put("kilometerstand", true);
                }
                updateKorrekteEingabe();
            }
        });

        editTextTankvolumen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String string = editable.toString();
                double parsedDouble = inputParser.parse(string);

                korrekteEinzeleingaben.put("tankvolumen", false);

                if (string.isEmpty()) {

                    if (checkBoxElektro.isChecked()) {
                        editTextTankvolumen.setError("Bitte geben Sie die Akkukapazität Ihres Fahrzeugs ein.");
                    } else {
                        editTextTankvolumen.setError("Bitte geben Sie das Tankvolumen Ihres Fahrzeugs ein.");
                    }

                } else if (!inputParser.isValid()) {

                    editTextTankvolumen.setError("Bitte geben Sie einen gültigen Wert ein.");

                } else if (parsedDouble < 1) {

                    if (checkBoxElektro.isChecked()) {
                        editTextTankvolumen.setError("Bitte Sie eine Akkukapazität größer oder gleich 1 kWh ein.");
                    } else {
                        editTextTankvolumen.setError("Bitte Sie ein Tankvolumen größer oder gleich 1 Liter ein.");
                    }

                } else {
                    korrekteEinzeleingaben.put("tankvolumen", true);
                }
                updateKorrekteEingabe();
            }
        });

        editTextVerbrauchInnerortsStand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String string = editable.toString();
                double parsedDouble = inputParser.parse(string);
                int verbrauchStandInt = (int) parsedDouble;

                korrekteEinzeleingaben.put("verbrauch_innerorts", false);

                if (string.isEmpty()) {
                    editTextVerbrauchInnerortsStand.setError("Bitte geben Sie einen Verbrauch ein.");
                } else if (!inputParser.isValid()) {
                    editTextVerbrauchInnerortsStand.setError("Bitte geben Sie einen gültigen Wert ein.");
                } else if (verbrauchStandInt < seekBarVerbrauchInnerorts.getMin() || verbrauchStandInt > seekBarVerbrauchInnerorts.getMax()) {
                    editTextVerbrauchInnerortsStand.setError("Ihre Eingabe ist außerhalb des zulässigen Bereichs.");
                } else {
                    editTextVerbrauchInnerortsStand.setSelection(editable.length());
                    seekBarVerbrauchInnerorts.setProgress(verbrauchStandInt);
                    korrekteEinzeleingaben.put("verbrauch_innerorts", true);
                }
                updateKorrekteEingabe();
            }
        });

        editTextVerbrauchAusserortsStand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String string = editable.toString();
                double parsedDouble = inputParser.parse(string);
                int verbrauchStandInt = (int) parsedDouble;

                korrekteEinzeleingaben.put("verbrauch_au_erorts", false);

                if (string.isEmpty()) {
                    editTextVerbrauchAusserortsStand.setError("Bitte geben Sie einen Verbrauch ein.");
                } else if (!inputParser.isValid()) {
                    editTextVerbrauchAusserortsStand.setError("Bitte geben Sie einen gültigen Wert ein.");
                } else if (verbrauchStandInt < seekBarVerbrauchAusserorts.getMin() || verbrauchStandInt > seekBarVerbrauchAusserorts.getMax()) {
                    editTextVerbrauchAusserortsStand.setError("Ihre Eingabe ist außerhalb des zulässigen Bereichs.");
                } else {
                    editTextVerbrauchAusserortsStand.setSelection(editable.length());
                    seekBarVerbrauchAusserorts.setProgress(verbrauchStandInt);
                    korrekteEinzeleingaben.put("verbrauch_au_erorts", true);
                }
                updateKorrekteEingabe();
            }
        });

        editTextVerbrauchKombiniertStand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String string = editable.toString();
                double parsedDouble = inputParser.parse(string);
                int verbrauchStandInt = (int) parsedDouble;

                korrekteEinzeleingaben.put("verbrauch_kombiniert", false);

                if (string.isEmpty()) {
                    editTextVerbrauchKombiniertStand.setError("Bitte geben Sie einen Verbrauch ein.");
                } else if (!inputParser.isValid()) {
                    editTextVerbrauchKombiniertStand.setError("Bitte geben Sie einen gültigen Wert ein.");
                } else if (verbrauchStandInt < seekBarVerbrauchKombiniert.getMin() || verbrauchStandInt > seekBarVerbrauchKombiniert.getMax()) {
                    editTextVerbrauchKombiniertStand.setError("Ihre Eingabe ist außerhalb des zulässigen Bereichs.");
                } else {
                    editTextVerbrauchKombiniertStand.setSelection(editable.length());
                    seekBarVerbrauchKombiniert.setProgress(verbrauchStandInt);
                    korrekteEinzeleingaben.put("verbrauch_kombiniert", true);
                }

                if (!aendern) {
                    aendern = true;  // damit editTexts sich wieder der seekBar anpassen
                }
                updateKorrekteEingabe();
            }
        });

        // --- SeekBar Listener

        seekBarVerbrauchInnerorts.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                EditText progressLabel = findViewById(R.id.editTextVerbrauchInnerortsStand);
                if (aendern) {
                    progressLabel.setText(String.valueOf(i));
                }

                progressLabel.setError(null);
            }
        });

        seekBarVerbrauchAusserorts.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                EditText progressLabel = findViewById(R.id.editTextVerbrauchAusserortsStand);
                if (aendern) {
                    progressLabel.setText(String.valueOf(i));
                }

                progressLabel.setError(null);
            }
        });

        seekBarVerbrauchKombiniert.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                EditText progressLabel = findViewById(R.id.editTextVerbrauchKombiniertStand);
                if (aendern) {
                    progressLabel.setText(String.valueOf(i));
                }
                progressLabel.setError(null);
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
                TextView progressLabel = findViewById(R.id.labelAktuellerTankstandStand);
                progressLabel.setText(String.valueOf(i));
            }
        });

        // --- Button Listener
        imageButtonElektroInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogElektroInfoBestaetigung.show();
                hinweis_angezeigt = true;
            }
        });

        // --- Default-Werte setzen

        if (intent.getAction().equals(MainActivity.ACTION_NEW_CAR)) {

            aendern = true;
            editTextName.setText("");  // so wird der Listener (Überprüfung) zu Beginn ausgelöst
            editTextName.setError(null);  // so wird zwar der Haken ausgegraut, aber zu Beginn kein Fehler angezeigt (bessere UX!)

        } else if (intent.getAction().equals(MainActivity.ACTION_EDIT_CAR)) {
            aendern = false;
            setTitle(R.string.edit_car);  // Titel "Fahrzeug bearbeiten" setzen

            Fahrzeug aktuellesFahrzeug = garage.getAusgewaehltesFahrzeug();

            checkBoxElektro.setChecked(aktuellesFahrzeug.isElektro());
            editTextName.setText(aktuellesFahrzeug.getName());
            editTextCo2.setText(dfAllgemein.format(aktuellesFahrzeug.getCo2Ausstoss()));
            editTextKilometerstand.setText(dfAllgemein.format(aktuellesFahrzeug.getKmStand()));
            editTextTankvolumen.setText(dfAllgemein.format(aktuellesFahrzeug.getTankgroesse()));

            // Verbrauch dann zumindest auf 1/5-tel-Liter-Genauigkeit angeben können
            // dafür gerne auch max-Werte anpassen (50 Liter pro 100 km doch recht unrealistisch)
            /* Über editTexts daneben gelöst, da seekBar keine Bruchteile unterstützen
            seekBarVerbrauchInnerorts.setProgress((int) aktuellesFahrzeug.getVerbrauchInnerorts());
            seekBarVerbrauchAusserorts.setProgress((int) aktuellesFahrzeug.getVerbrauchAusserorts());
            seekBarVerbrauchKombiniert.setProgress((int) aktuellesFahrzeug.getVerbrauchKombiniert());
             */
            editTextVerbrauchInnerortsStand.setText(dfAllgemein.format(aktuellesFahrzeug.getVerbrauchInnerortsAnfangswert()));
            editTextVerbrauchAusserortsStand.setText(dfAllgemein.format(aktuellesFahrzeug.getVerbrauchAusserortsAnfangswert()));
            editTextVerbrauchKombiniertStand.setText(dfAllgemein.format(aktuellesFahrzeug.getVerbrauchKombiniertAnfangswert()));
            seekBarAktuellerTankstand.setProgress((int) aktuellesFahrzeug.getTankstand());

            // beim Ändern eines Fahrzeugs, welches schon Strecken/Tankvorgänge gespeichert hat,
            // sollen einige Parameter nicht mehr bearbeitbar sein
            if (!aktuellesFahrzeug.getStrecken().isEmpty() || !aktuellesFahrzeug.getTankvorgaenge().isEmpty()) {
                findViewById(R.id.groupElektro).setVisibility(View.GONE);
                labelCo2Ausstoss.setVisibility(View.GONE);
                editTextCo2.setVisibility(View.GONE);
                labelKilometerstand.setVisibility(View.GONE);
                editTextKilometerstand.setVisibility(View.GONE);
                labelTankvolumen.setVisibility(View.GONE);
                editTextTankvolumen.setVisibility(View.GONE);
                labelAktuellerTankstandTitel.setVisibility(View.GONE);
                labelAktuellerTankstandStand.setVisibility(View.GONE);
                seekBarAktuellerTankstand.setVisibility(View.GONE);
            }
        }

        if (intent.getAction().equals(MainActivity.ACTION_NEW_CAR)) {
            editTextName.requestFocus();  // zu Beginn Fokus auf Namensfeld
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
     * Backend, um Daten abzufangen und Fahrzeug zu ändern oder zu Garage hinzuzufügen
     */
    private void fertigButtonGedrueckt() {

        boolean datenEingepflegt = false;  // Fahrzeugerstellung fehlerhaft oder nicht

        if (korrekteEingabe) {  // korrekte Eingaben getätigt

            // Parsing
            String name = editTextName.getText().toString();
            double co2 = inputParser.parse(editTextCo2.getText().toString());
            double kilometerstand = inputParser.parse(editTextKilometerstand.getText().toString());
            double tankvolumen = inputParser.parse(editTextTankvolumen.getText().toString());
            /* Werte werden nun aus den editTexts gelesen
            int verbrauchInnerorts = seekBarVerbrauchInnerorts.getProgress();
            int verbrauchAusserorts = seekBarVerbrauchAusserorts.getProgress();
            int verbrauchKombiniert = seekBarVerbrauchKombiniert.getProgress();
            */
            double verbrauchInnerorts = inputParser.parse(editTextVerbrauchInnerortsStand.getText().toString());
            double verbrauchAusserorts = inputParser.parse(editTextVerbrauchAusserortsStand.getText().toString());
            double verbrauchKombiniert = inputParser.parse(editTextVerbrauchKombiniertStand.getText().toString());

            int aktuellerTankstand = seekBarAktuellerTankstand.getProgress();
            boolean ist_elektro = checkBoxElektro.isChecked();

            if (intent.getAction().equals(MainActivity.ACTION_NEW_CAR)) {  // neues Auto hinzufügen

                Fahrzeug neuesFahrzeug = new Fahrzeug(
                        name, ist_elektro,
                        verbrauchAusserorts, verbrauchInnerorts, verbrauchKombiniert,
                        kilometerstand, aktuellerTankstand, co2, tankvolumen
                );

                try {
                    garage.fahrzeugHinzufuegen(neuesFahrzeug);
                    datenEingepflegt = true;
                } catch (GarageVollException e) {
                    e.printStackTrace();
                    datenEingepflegt = false;
                }

            } else if (intent.getAction().equals(MainActivity.ACTION_EDIT_CAR)) {  // Auto bearbeiten

                Fahrzeug zuAenderndesFahrzeug = garage.getAusgewaehltesFahrzeug();

                zuAenderndesFahrzeug.fahrzeugAendern(
                        name, ist_elektro,
                        verbrauchAusserorts, verbrauchInnerorts, verbrauchKombiniert,
                        kilometerstand, aktuellerTankstand, co2, tankvolumen);

                datenEingepflegt = true;

            }
        }

        // result setzen

        if (korrekteEingabe && datenEingepflegt) {
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
        getMenuInflater().inflate(R.menu.menu_new_car, menu);
        menu.findItem(R.id.action_new_car_done).setEnabled(korrekteEingabe);  // disable or enable check mark
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
        if (item.getItemId() == R.id.action_new_car_done) {
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
