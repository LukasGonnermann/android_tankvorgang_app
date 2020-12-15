package com.example.tankauswertung;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;
import android.content.pm.PackageManager;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.example.tankauswertung.exceptions.FahrzeugWertException;
import com.example.tankauswertung.ui.timeline.TimelineFragment;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NewTankvorgangActivity extends AppCompatActivity {

    boolean korrekteEingabe = true;
    Map<String, Boolean> korrekteEinzeleingaben = new HashMap<String, Boolean>() {{
        put("menge", true);
        put("preis", true);
    }};

    // UI-Elemente
    EditText editTextGetankteMenge;
    EditText editTextPreis;
    ImageView imageViewTankvorgangBeleg;
    ImageButton buttonTankvorgangBildAufnehmen;
    ImageButton buttonTankvorgangBildLoeschen;

    Intent intent;
    Garage garage;

    InputParser inputParser = new InputParser();

    // Decimal Formatter
    DecimalFormat dfGeladeneMengeErrorLiter = new DecimalFormat("#.##\u00A0l", new DecimalFormatSymbols(Locale.GERMAN));
    DecimalFormat dfGeladeneMengeErrorKwh = new DecimalFormat("#.##\u00A0kWh", new DecimalFormatSymbols(Locale.GERMAN));
    DecimalFormat dfGeladeneMenge = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.GERMAN));
    DecimalFormat dfPreis = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.GERMAN));

    private static final int READ_WRITE_PERMISSION_CODE = 200;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String tankvorgangBildPath = null;
    private String createImageFilePath = null;

    /**
     * ausgeführt, sobald die Aktivität gestartet wird
     *
     * @param savedInstanceState —
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tankvorgang);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        supportInvalidateOptionsMenu();

        intent = getIntent();  // erhalte Intent vom Aufruf
        garage = MainActivity.getGarage();  // erhalte Garagenobjekt

        editTextGetankteMenge = findViewById(R.id.editTextGetankteMenge);
        editTextPreis = findViewById(R.id.editTextPreis);
        imageViewTankvorgangBeleg = findViewById(R.id.imageViewTankvorgangBeleg);
        buttonTankvorgangBildAufnehmen = findViewById(R.id.buttonTankvorgangBildHinzufuegen);
        buttonTankvorgangBildLoeschen = findViewById(R.id.buttonTankvorgangBildLoeschen);
        TextView labelGetankteMengeTitel = findViewById(R.id.labelGetankteMengeTitel);

        // zeigt den Zurück-Button an
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // --- EditText Listener (inkl. Fehlerüberprüfung)
        editTextGetankteMenge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                Fahrzeug aktuellesFahrzeug = garage.getAusgewaehltesFahrzeug();
                double alterTankstand;

                if (intent.getAction().equals(TimelineFragment.ACTION_NEW_TANKVORGANG)) {
                    alterTankstand = aktuellesFahrzeug.getTankstand() / 100 * aktuellesFahrzeug.getTankgroesse();
                } else {
                    Tankvorgang neuesterTankvorgang = aktuellesFahrzeug.getTankvorgaenge().get(0);
                    alterTankstand = aktuellesFahrzeug.getTankstand() / 100 * aktuellesFahrzeug.getTankgroesse() -
                        neuesterTankvorgang.getGetankteMenge();
                }
                double altesRestvolumen = aktuellesFahrzeug.getTankgroesse() - alterTankstand;

                String string = editable.toString();
                double parsedDouble = inputParser.parse(string);
                korrekteEinzeleingaben.put("menge", false);

                if (string.isEmpty()) {

                    if (!aktuellesFahrzeug.isElektro()) {
                        editTextGetankteMenge.setError("Bitte geben Sie die getankte Menge ein.");
                    } else {
                        editTextGetankteMenge.setError("Bitte geben Sie die geladene Menge ein.");
                    }

                } else if (!inputParser.isValid()) {
                    editTextGetankteMenge.setError("Bitte geben Sie einen gültigen Wert ein.");

                } else if (parsedDouble > altesRestvolumen) {

                    if (!aktuellesFahrzeug.isElektro()) {
                        editTextGetankteMenge.setError(
                                "Bitte geben Sie eine Tankmenge ein, die kleiner als das restliche " +
                                        "Volumen Ihres Tanks (" +
                                        dfGeladeneMengeErrorLiter.format(altesRestvolumen) + ") ist.");
                    } else {
                        editTextGetankteMenge.setError(
                                "Bitte geben Sie eine Lademenge ein, die kleiner als die " +
                                        "restliche Kapazität Ihres Akkus (" +
                                        dfGeladeneMengeErrorKwh.format(altesRestvolumen) + ") ist.");
                    }

                } else {
                    korrekteEinzeleingaben.put("menge", true);
                }
                updateKorrekteEingabe();
            }
        });

        editTextPreis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                Fahrzeug aktuellesFahrzeug = garage.getAusgewaehltesFahrzeug();

                String string = editable.toString();
                double parsedDouble = inputParser.parse(string);
                korrekteEinzeleingaben.put("preis", false);

                if (string.isEmpty()) {

                    if (!aktuellesFahrzeug.isElektro()) {
                        editTextPreis.setError("Bitte geben Sie den Preis für den Tankvorgang ein.");
                    } else {
                        editTextPreis.setError("Bitte geben Sie den Preis für den Ladevorgang ein.");
                    }

                } else if (!inputParser.isValid()) {
                    editTextPreis.setError("Bitte geben Sie einen gültigen Wert ein..");

                } else {
                    korrekteEinzeleingaben.put("preis", true);
                }
                updateKorrekteEingabe();
            }
        });

        // Label für Elektroauto ändern
        if (garage.getAusgewaehltesFahrzeug().isElektro()) {
            labelGetankteMengeTitel.setText(R.string.getankte_menge_kwh);
            setTitle(getString(R.string.new_tankvorgang_elektro));
        }

        // --- Default-Werte setzen
        if (intent.getAction().equals(TimelineFragment.ACTION_EDIT_TANKVORGANG)) {
            if (!garage.getAusgewaehltesFahrzeug().isElektro()) {
                setTitle(R.string.edit_tankvorgang);  // Titel "Tankvorgang bearbeiten" setzen
            } else {
                setTitle(getString(R.string.edit_tankvorgang_elektro));  // Titel "Ladevorgang bearbeiten" setzen
            }

            Fahrzeug aktuellesFahrzeug = garage.getAusgewaehltesFahrzeug();
            Tankvorgang neuesterTankvorgang = aktuellesFahrzeug.getTankvorgaenge().get(0);
            tankvorgangBildPath = neuesterTankvorgang.getImg();
            // Falls kein Bild aufgenommen wurde wird kein Bild angezeigt
            if (tankvorgangBildPath != null) {
                Uri imageURI = Uri.fromFile(new File(tankvorgangBildPath));
                this.imageViewTankvorgangBeleg.setImageURI(imageURI);
                this.imageViewTankvorgangBeleg.setVisibility(View.VISIBLE);
                this.buttonTankvorgangBildLoeschen.setVisibility(View.VISIBLE);
            }
            else {
                this.imageViewTankvorgangBeleg.setVisibility(View.GONE);
                this.buttonTankvorgangBildLoeschen.setVisibility(View.GONE);
            }

            editTextGetankteMenge.setText(dfGeladeneMenge.format(neuesterTankvorgang.getGetankteMenge()));
            editTextPreis.setText(dfPreis.format(neuesterTankvorgang.getPreis()));
        }

        // --- OnClickListener für Bild-aufnehmen-Button
        buttonTankvorgangBildAufnehmen.setOnClickListener(v -> dispatchTakePictureIntent());

        // --- OnClickListener für Bild-löschen-Button
        buttonTankvorgangBildLoeschen.setOnClickListener(v -> {
            File f = new File(tankvorgangBildPath);
            if (f.delete()) {
                imageViewTankvorgangBeleg.setImageURI(null);
                imageViewTankvorgangBeleg.setVisibility(View.GONE);
                buttonTankvorgangBildLoeschen.setVisibility(View.GONE);
                this.tankvorgangBildPath = null;
            }
            else {
                Toast.makeText(this, "Bild konnte nicht gelöscht werden", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        // check permissions
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    Toast.makeText(this, "Erstellen der Bilddatei fehlgeschlagen", Toast.LENGTH_LONG).show();
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            } else {
                Toast.makeText(this, "Aktivität konnte nicht resolved werden", Toast.LENGTH_LONG).show();
            }
        } else {
            // Dialog welcher Permissions beschreibt (wird nicht immer ausgeführt)
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Berechtigungen zum Schreiben/Lesen von Bildern benötigt", Toast.LENGTH_LONG).show();
            }
            // Get RW permissions
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, READ_WRITE_PERMISSION_CODE);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        createImageFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // (Wenn existiert) Existierendes Bild löschen
            if (tankvorgangBildPath != null) {
                File f = new File(tankvorgangBildPath);
                if (!f.delete()) {
                    Toast.makeText(this, "Bild konnte nicht gelöscht werden", Toast.LENGTH_LONG).show();
                }
            }
            tankvorgangBildPath = createImageFilePath;
            Uri imageURI = Uri.fromFile(new File(tankvorgangBildPath));
            imageViewTankvorgangBeleg.setImageURI(imageURI);
            imageViewTankvorgangBeleg.setVisibility(View.VISIBLE);
            buttonTankvorgangBildLoeschen.setVisibility(View.VISIBLE);
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
            double getankteMenge = inputParser.parse(editTextGetankteMenge.getText().toString());
            double preis = inputParser.parse(editTextPreis.getText().toString());
            String bildPfad = this.tankvorgangBildPath;

            if (intent.getAction().equals(TimelineFragment.ACTION_NEW_TANKVORGANG)) {  // Tankvorgang hinzufügen

                try {
                    garage.getAusgewaehltesFahrzeug().tankvorgangHinzufuegen(
                            getankteMenge, preis, bildPfad
                    );
                } catch (FahrzeugWertException e) {
                    e.printStackTrace();
                }

            } else if (intent.getAction().equals(TimelineFragment.ACTION_EDIT_TANKVORGANG)) {  // Tankvorgang bearbeiten

                Fahrzeug aktuellesFahrzeug = garage.getAusgewaehltesFahrzeug();
                Tankvorgang neuesterTankvorgang = aktuellesFahrzeug.getTankvorgaenge().get(0);

                double alterTankstand = aktuellesFahrzeug.getTankstand() / 100 * aktuellesFahrzeug.getTankgroesse() - neuesterTankvorgang.getGetankteMenge();
                double neuerTankstand = alterTankstand + getankteMenge;

                try {
                    aktuellesFahrzeug.setTankstand(neuerTankstand / aktuellesFahrzeug.getTankgroesse() * 100);
                } catch (FahrzeugWertException e) {
                    e.printStackTrace();
                }
                neuesterTankvorgang.tankvorgangBearbeiten(getankteMenge, preis, bildPfad);
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
        getMenuInflater().inflate(R.menu.menu_new_tankvorgang, menu);
        menu.findItem(R.id.action_new_tankvorgang_done).setEnabled(korrekteEingabe);  // disable or enable check mark
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
        if (item.getItemId() == R.id.action_new_tankvorgang_done) {
            fertigButtonGedrueckt();
            setResult(Activity.RESULT_OK, intent);
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
