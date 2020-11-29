package com.example.tankauswertung;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewTankvorgangActivity extends Activity {
    private Button buttonTankvorgangBildAufnehmen;
    private ImageView imageViewTankvorgangThumbnail;
    private static final int MY_CAMERA_PERMISSION_CODE = 69;
    private static final int CAMERA_REQUEST = 1888;
    private Bitmap tankvorgang_bild = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tankvorgang);

        this.buttonTankvorgangBildAufnehmen = findViewById(R.id.buttonTankvorgangBildAufnehmen);
        this.imageViewTankvorgangThumbnail = findViewById(R.id.imageViewTankvorgangThumbnail);

        buttonTankvorgangBildAufnehmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Request Runtime Permission
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
    }

    /**
     * Ueberprueft ob notwendigen permissions gegeben wurden um Bilder zu machen
     * @param requestCode   requestCode welcher von der methode uebergeben wurde die permissions angefragt hat
     * @param permissions   die angefragen permissions
     * @param grantResults  enthaelt infos ob die permissions erteilt wurden
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE ) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Kleines Popup das Permissions erteilt wurden
                Toast.makeText(this,"Camera permission granted!", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else  {
                // Kleines Popup das Permissions nicht erteilt wurden
                Toast.makeText(this,"Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Ergebnis des Camera aufrufs um ein Bild aufzunehmen
     * @param requestCode   Code der aufgerufenen Activity(Camera-> Bildaufnehmen)
     * @param resultCode    wie lief die activity
     * @param data          daten zu Bitmap convertierbar
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            // Kamera Bild anzeigen und speichern
            Bitmap pic = (Bitmap) data.getExtras().get("data");
            // Bitmap image anzeigen
            imageViewTankvorgangThumbnail.setImageBitmap(pic);
            tankvorgang_bild = pic;
        }
    }
}
