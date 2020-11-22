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

import java.util.HashMap;
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
    ImageButton imageButtonElektro_info;

    Intent intent;

    Garage garage;
    boolean aendern = false;// gibt an, ob bei Fahrzeug ändern, die Default Werte geladen werden sollen

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
        imageButtonElektro_info = findViewById(R.id.imageButtonElektro_info);

        // zeigt den Zurück-Button an
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // --- CheckBox Listener, ob Elektroauto

        checkBoxElektro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // TODO
                TextView labelVerbrauchInnerortsTitel = findViewById(R.id.labelVerbrauchInnerortsTitel);
                TextView labelVerbrauchAusserortsTitel = findViewById(R.id.labelVerbrauchAusserortsTitel);
                TextView labelVerbrauchKombiniertTitel = findViewById(R.id.labelVerbrauchKombiniertTitel);
                TextView labelTankvolumen = findViewById(R.id.labelTankvolumen);
                TextView labelAktuellerTankstandTitel = findViewById(R.id.labelAktuellerTankstandTitel);

                //ist Elektro
                if (b) {
                    AlertDialog dialogElektroInfoBestaetigung = new AlertDialog.Builder(NewCarActivity.this)
                            .setTitle("Hinweis zur CO2-Emission von Elektro-Autos")
                            .setMessage("In dieser App werden Elektro-Autos mit 0 g/km Emissonen erfasst. Dennoch werden auch bei Stromerzeugung CO2-Emissionen verursacht.")
                            .setIcon(R.drawable.ic_baseline_info_24)

                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int whichButton) {
                                    dialogInterface.dismiss();
                                }
                            })

                            .create();

                    dialogElektroInfoBestaetigung.show();
                    imageButtonElektro_info.setVisibility(View.VISIBLE);
                    labelVerbrauchInnerortsTitel.setText(R.string.verbrauch_innerorts_kwh_100km);
                    labelVerbrauchAusserortsTitel.setText(R.string.verbrauch_au_erorts_kwh_100km);
                    labelVerbrauchKombiniertTitel.setText(R.string.verbrauch_kombiniert_kwh_100km);
                    labelTankvolumen.setText(R.string.tankvolumen_kwh);
                    labelAktuellerTankstandTitel.setText(R.string.tankstand_kwh);
                    //ist Verbrenner
                } else {
                    imageButtonElektro_info.setVisibility(View.INVISIBLE);
                    labelVerbrauchInnerortsTitel.setText(R.string.verbrauch_innerorts_l_100km);
                    labelVerbrauchAusserortsTitel.setText(R.string.verbrauch_au_erorts_l_100km);
                    labelVerbrauchKombiniertTitel.setText(R.string.verbrauch_kombiniert_l_100km);
                    labelTankvolumen.setText(R.string.tankvolumen_l);
                    labelAktuellerTankstandTitel.setText(R.string.tankstand_l);
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
                    editTextName.setError("Bitte geben Sie einen Fahrzeugnamen an");
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
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

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
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

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

        editTextVerbrauchInnerortsStand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(editTextVerbrauchInnerortsStand.getText())) {
                    editTextVerbrauchInnerortsStand.setError("Bitte geben Sie das Tankvolumen Ihres Fahrzeugs an");
                    korrekteEinzeleingaben.put("tankvolumen", false);
                } else {
                    seekBarVerbrauchInnerorts.setProgress((int) Double.parseDouble(editTextVerbrauchInnerortsStand.getText().toString()));
                    korrekteEinzeleingaben.put("tankvolumen", true);
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
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(editTextVerbrauchAusserortsStand.getText())) {
                    editTextVerbrauchAusserortsStand.setError("Bitte geben Sie das Tankvolumen Ihres Fahrzeugs an");
                    korrekteEinzeleingaben.put("tankvolumen", false);
                } else {
                    seekBarVerbrauchAusserorts.setProgress((int) Double.parseDouble(editTextVerbrauchAusserortsStand.getText().toString()));
                    korrekteEinzeleingaben.put("tankvolumen", true);
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
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(editTextVerbrauchKombiniertStand.getText())) {
                    editTextVerbrauchKombiniertStand.setError("Bitte geben Sie das Tankvolumen Ihres Fahrzeugs an");
                    korrekteEinzeleingaben.put("tankvolumen", false);
                } else {
                    seekBarVerbrauchKombiniert.setProgress((int) Double.parseDouble(editTextVerbrauchKombiniertStand.getText().toString()));
                    korrekteEinzeleingaben.put("tankvolumen", true);
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
                } else {
                    aendern = true;
                } // damit editTexts sich wieder der seekBar anpassen
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

        //----Button Listener
        imageButtonElektro_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialogElektroInfoBestaetigung = new AlertDialog.Builder(NewCarActivity.this)
                        .setTitle("Hinweis zur CO2-Emission von Elektro-Autos")
                        .setMessage("In dieser App werden Elektro-Autos mit 0 g/km Emissonen erfasst. Dennoch werden auch bei Stromerzeugung CO2-Emissionen verursacht.")
                        .setIcon(R.drawable.ic_baseline_info_24)

                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int whichButton) {
                                dialogInterface.dismiss();
                            }
                        })

                        .create();

                dialogElektroInfoBestaetigung.show();

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
            editTextCo2.setText(Double.toString(aktuellesFahrzeug.getCo2Ausstoss()));
            editTextKilometerstand.setText(Double.toString(aktuellesFahrzeug.getKmStand()));
            editTextTankvolumen.setText(Double.toString(aktuellesFahrzeug.getTankgroesse()));

            // Verbrauch dann zumindest auf 1/5-tel-Liter-Genauigkeit angeben können
            // dafür gerne auch max-Werte anpassen (50 Liter pro 100 km doch recht unrealistisch)
            /* Über editTexts daneben gelöst, da seekBar keine Bruchteile unterstützen
            seekBarVerbrauchInnerorts.setProgress((int) aktuellesFahrzeug.getVerbrauchInnerorts());
            seekBarVerbrauchAusserorts.setProgress((int) aktuellesFahrzeug.getVerbrauchAusserorts());
            seekBarVerbrauchKombiniert.setProgress((int) aktuellesFahrzeug.getVerbrauchKombiniert());
             */
            editTextVerbrauchInnerortsStand.setText(aktuellesFahrzeug.getVerbrauchInnerorts() + "");
            editTextVerbrauchAusserortsStand.setText(aktuellesFahrzeug.getVerbrauchAusserorts() + "");
            editTextVerbrauchKombiniertStand.setText(aktuellesFahrzeug.getVerbrauchKombiniert() + "");
            seekBarAktuellerTankstand.setProgress((int) aktuellesFahrzeug.getTankstand());
        }

        editTextName.requestFocus();  // zu Beginn Fokus auf Namensfeld
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
    private boolean fertigButtonGedrueckt() {

        boolean datenEingepflegt = false;  // Fahrzeugerstellung fehlerhaft oder nicht

        if (korrekteEingabe) {  // korrekte Eingaben getätigt

            // Parsing
            String name = editTextName.getText().toString();
            double co2 = Double.parseDouble(editTextCo2.getText().toString());
            double kilometerstand = Double.parseDouble(editTextKilometerstand.getText().toString());
            double tankvolumen = Double.parseDouble(editTextTankvolumen.getText().toString());
            /*Werte werden nun aus den editTexts gelesen
            int verbrauchInnerorts = seekBarVerbrauchInnerorts.getProgress();
            int verbrauchAusserorts = seekBarVerbrauchAusserorts.getProgress();
            int verbrauchKombiniert = seekBarVerbrauchKombiniert.getProgress();
            */
            double verbrauchInnerorts = Double.parseDouble(editTextVerbrauchInnerortsStand.getText().toString());
            double verbrauchAusserorts = Double.parseDouble(editTextVerbrauchAusserortsStand.getText().toString());
            double verbrauchKombiniert = Double.parseDouble(editTextVerbrauchKombiniertStand.getText().toString());

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
        setResult(Activity.RESULT_CANCELED, intent);
        onBackPressed();
        return true;
    }
}
