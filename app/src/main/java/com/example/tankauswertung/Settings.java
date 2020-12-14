package com.example.tankauswertung;

import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Settings implements Serializable {

    /**
     * Variable, welche die Darkmodeeinstellung speichert
     */
    private int darkModeStatus;
    /**
     *
     */
    private final String settingsFileName = "settings.dat";

    /**
     * Konstruktor der Klasse, setzt Standardmaessig die Darkmodesettings auf SYSTEM
     */
    public Settings(){
        darkModeStatus = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
    }

    /**
     * Getter fuer die Darkmodesettings
     * @return enum DarkModeStatus, Aktuelle Darkmodeeinstellung
     */
    public int getDarkModeStatus() { return darkModeStatus; }

    /**
     * Setter fuer die Darkmodesetting Variable
     * @param darkModeStatus enum DarkModeStatus, Inputwert, auf welchen die Darkmodeeinstellung gesetzt wird
     */
    public void setDarkModeStatus(int darkModeStatus) { this.darkModeStatus = darkModeStatus; }

    public void load(Context context) {
        try {
            FileInputStream fis = context.openFileInput(settingsFileName);
            try {
                ObjectInputStream os = new ObjectInputStream(fis);
                Settings importedSettings = (Settings) os.readObject();

                this.darkModeStatus = importedSettings.getDarkModeStatus();
                // Bei Settings Erweiterungen hier Attribute hinzufügen

            } catch (IOException e) {
                System.err.println("An IO error occured");
            } catch (ClassNotFoundException e) {
                System.err.println("Object not found!");
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found:" + settingsFileName);
        }
    }

    public void save(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(settingsFileName, Context.MODE_PRIVATE);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(this);
            } catch (IOException e) {
                System.out.println("An IO Error has occured");
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Datei " + settingsFileName +" konnte nicht geoeffnet werden!");
            e.printStackTrace();
        }
    }
}
