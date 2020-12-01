
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tankauswertung.exceptions.FahrzeugWertException;
import com.example.tankauswertung.ui.timeline.TimelineFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;

public class NewTankvorgangActivity extends AppCompatActivity {

    boolean korrekteEingabe = true;
    Map<String, Boolean> korrekteEinzeleingaben = new HashMap<String, Boolean>() {{
        put("menge", true);
        put("preis", true);
        // Bild nicht zwingend notwendig
    }};

    // UI-Elemente
    EditText editTextGetankteMenge;
    EditText editTextPreis;
    ImageView imageViewTankvorgangBeleg;
    Button buttonTankvorgangBildAufnehmen;

    Intent intent;
    Garage garage;

    private static final int MY_CAMERA_PERMISSION_CODE = 69;
    private static final int CAMERA_REQUEST = 1889;
    private Bitmap tankvorgang_bild = null;
    private String tankvorgang_bild_path;

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
        buttonTankvorgangBildAufnehmen = findViewById(R.id.buttonTankvorgangBelegHinzufuegen);
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

                // TODO: getankte Menge überprüfen (neue Menge darf nicht > Tankvolumen sein)

                Editable kilometerStandText = editTextGetankteMenge.getText();

                if (TextUtils.isEmpty(kilometerStandText)) {

                    editTextGetankteMenge.setError("Bitte geben Sie die getankte Menge an");
                    korrekteEinzeleingaben.put("menge", false);

//                } else if (Double.parseDouble(kilometerStandText.toString()) < alterKilometerstand) {
//
//                    editTextKilometerstand.setError(
//                            "Bitte geben Sie einen Kilometerstand an, der größer oder gleich dem " +
//                                    "letzten Kilometerstand Ihres Fahrzeugs (" +
//                                    alterKilometerstand +  ") ist.");
//                    korrekteEinzeleingaben.put("menge", false);

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
                if (TextUtils.isEmpty(editTextPreis.getText())) {
                    editTextPreis.setError("Bitte geben Sie den Preis für den Tankvorgang an");
                    korrekteEinzeleingaben.put("menge", false);
                } else {
                    korrekteEinzeleingaben.put("menge", true);
                }
                updateKorrekteEingabe();
            }
        });

        // Label für Elektroauto ändern
        if (garage.getAusgewaehltesFahrzeug().isElektro()) {
            labelGetankteMengeTitel.setText(R.string.getankte_menge_kwh);
        }

        // --- Default-Werte setzen

        if (intent.getAction().equals(TimelineFragment.ACTION_EDIT_TANKVORGANG)) {

            setTitle(R.string.edit_tankvorgang);  // Titel "Tankvorgang bearbeiten" setzen
            Fahrzeug aktuellesFahrzeug = garage.getAusgewaehltesFahrzeug();
            Tankvorgang neuesterTankvorgang = aktuellesFahrzeug.getTankvorgaenge().get(0);

            editTextGetankteMenge.setText(Double.toString(neuesterTankvorgang.getGetankteMenge()));
            editTextPreis.setText(Double.toString(neuesterTankvorgang.getPreis()));

            // TODO: ImageView setzen
        }

        // --- OnClickListener für Bild-aufnehmen-Button
        buttonTankvorgangBildAufnehmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO geht auch ohne Permissions? Nachschauen!
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
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
            double getankteMenge = Double.parseDouble(editTextGetankteMenge.getText().toString());
            double preis = Double.parseDouble(editTextPreis.getText().toString());
            // TODO: Bild

            if (intent.getAction().equals(TimelineFragment.ACTION_NEW_TANKVORGANG)) {  // Tankvorgang hinzufügen

                try {
                    // TODO: Bild
                    garage.getAusgewaehltesFahrzeug().tangvorgangHinzufuegen(
                            getankteMenge, preis, ""
                    );
                } catch (FahrzeugWertException e) {
                    e.printStackTrace();
                }

            } else if (intent.getAction().equals(TimelineFragment.ACTION_EDIT_TANKVORGANG)) {  // Tankvorgang bearbeiten

                Fahrzeug aktuellesFahrzeug = garage.getAusgewaehltesFahrzeug();
                Tankvorgang neuesterTankvorgang = aktuellesFahrzeug.getTankvorgaenge().get(0);

                double alterTankstand = aktuellesFahrzeug.getTankstand() - neuesterTankvorgang.getGetankteMenge();
                double neuerTankstand = alterTankstand + getankteMenge;

                try {
                    aktuellesFahrzeug.setTankstand(neuerTankstand);
                } catch (FahrzeugWertException e) {
                    e.printStackTrace();
                }

                // TODO: Bild
                neuesterTankvorgang.tankvorgangBearbeiten(getankteMenge, preis, "");

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

    // --- Bild aufnehmen

    /**
     * Ueberprueft ob notwendigen permissions gegeben wurden um Bilder zu machen
     * !Debug if necessary!
     * @param requestCode  requestCode welcher von der methode uebergeben wurde die permissions angefragt hat
     * @param permissions  die angefragen permissions
     * @param grantResults enthaelt infos ob die permissions erteilt wurden
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Kleines Popup das Permissions erteilt wurden
                Toast.makeText(this, "Camera permission granted!", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                // Kleines Popup das Permissions nicht erteilt wurden
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Ergebnis des Camera aufrufs um ein Bild aufzunehmen
     *
     * @param requestCode Code der aufgerufenen Activity(Camera-> Bildaufnehmen)
     * @param resultCode  wie lief die activity
     * @param data        daten zu Bitmap convertierbar
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            // Kamera Bild anzeigen und speichern
            Bitmap pic = (Bitmap) data.getExtras().get("data");
            // Bitmap image anzeigen
            imageViewTankvorgangBeleg.setImageBitmap(pic);
            tankvorgang_bild = pic;
            imageViewTankvorgangBeleg.setImageBitmap(pic);
            imageViewTankvorgangBeleg.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Erstellt eine File um ein Bild in hoher Auflösung zu speichern
     * @return File
     */
    private File createImageFile() throws IOException {
        // Create image filename (Collisionfree)
        @SuppressLint("SimpleDateFormat")
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "JPEG_" + timestamp + "_";
        File storageDir = getFilesDir();
        File image;
        try {
            image = File.createTempFile(
                    fileName,
                    ".jpg",
                    storageDir
            );
        } catch (IOException e) {
            Toast.makeText(this ,"IO Exception has occured",Toast.LENGTH_LONG).show();
            throw new IOException();
        }
        tankvorgang_bild_path = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                // TODO wird in Button onClick geschrieben
            }
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
