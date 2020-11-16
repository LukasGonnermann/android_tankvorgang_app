package com.example.tankauswertung;

import android.content.Context;

import com.example.tankauswertung.exceptions.GarageLeerException;
import com.example.tankauswertung.exceptions.GarageVollException;

import java.util.ArrayList;
import java.io.*;


/**
 * Garage Class verwaltet alle angelegten Fahrzeuge bzw. kann neue Autos hinzufuegen
 */
public class Garage {
    /**
     * Legt die maximale Anzahl an Fahrzeugen
     */
    private int maxAnzFahrzeuge;
    /**
     * Aktuelle Anzahl an Fahrzeugen in der Garage
     */
    private int anzFahrzeuge;
    /**
     * ArrayList zum speichern der Fahrzeuge
     */
    private ArrayList<Fahrzeug> fahrzeuge;
    /**
     * Momentan ausgewaehltes Fahrzeug im UI
     */
    private Fahrzeug ausgewaehltesFahrzeug;
    /**
     * Legt den Namen des Datei fest die beim Serialisierungsprozess benutzt wird (Methoden load, save)
     */
    private final String fileName = "fahrzeuge.dat";


    /**
     * Default Konstruktor Standard
     */
    public Garage() {
        this.maxAnzFahrzeuge = 25;
        this.anzFahrzeuge = 0;
        fahrzeuge = new ArrayList<>();
    }

    /**
     * Erstellt ein neues Garage Objekt und legt die maximale Anzahl an Fahrzeugen in der Garage fest.
     *
     * @param fahrzeugeAnz maximale Anzahl an Fahrzeugen in der Garage
     */
    public Garage(int fahrzeugeAnz) {
        this.maxAnzFahrzeuge = fahrzeugeAnz;
        this.anzFahrzeuge = 0;
        fahrzeuge = new ArrayList<>();
    }

    /**
     * Gibt die aktuelle Anzahl an Fahrzeugen in der Garage zurueck
     *
     * @return Anzahl Fahrzeuge als Integer
     */
    public int getAnzFahrzeuge() {
        return anzFahrzeuge;
    }

    /**
     * Gebe das Fahrzeug an einer bestimmten Position in der Liste zurueck
     *
     * @param id Position in der Liste
     * @return Fahrzeugobjekt an der gewuenschten Position
     */
    public Fahrzeug getFahrzeugById(int id) {
        if (id >= 0 && id < anzFahrzeuge) {
            return fahrzeuge.get(id);
        } else {
            return null;
            // exception hinzufuegen
        }
    }

    /**
     * Gibt das momentan aktive ausgewaehltes Fahrzeug zurück
     *
     * @return Fahrzeug Objekt
     */
    public Fahrzeug getAusgewaehltesFahrzeug() {
        return ausgewaehltesFahrzeug;
    }

    public void setAusgewaehltesFahrzeugById(int id) {
        if (id >= 0 && id < anzFahrzeuge) {
            this.ausgewaehltesFahrzeug = fahrzeuge.get(id);
        } else {
            // exception hinzufuegen
        }
    }

    public void setAusgewaehltesFahrzeug(Fahrzeug ausgewaehltesFahrzeug) {
        this.ausgewaehltesFahrzeug = ausgewaehltesFahrzeug;
    }

    /**
     * Fügt ein bestehendes Fahrzeug zur Garage hinzu
     *
     * @param neuAuto hinzuzufuegendes Fahrzeug
     */
    public void fahrzeugHinzufuegen(Fahrzeug neuAuto) throws GarageVollException {
        if (anzFahrzeuge < maxAnzFahrzeuge) {
            fahrzeuge.add(neuAuto);
            this.setAusgewaehltesFahrzeug(neuAuto);
            anzFahrzeuge++;
        } else {
            throw new GarageVollException();
        }
    }

    /**
     * Erstellt ein Fahrzeug ueber Parameter und fuegt dieses hinzu
     *
     * @param pName                Anzeigename des Fahrzeugs
     * @param pElektro             Ist ein Elektrofahrzeug?
     * @param pVerbrauchAusserorts Ausserorts Verbrauch des Fahrzeugs
     * @param pVerbrauchInnerorts  Innerorts Verbrauch des Fahrzeugs
     * @param pVerbrauchKombiniert Kombinierter Verbrauch des Fahrzeugs
     * @param pKmStand             Kilometerstand des Fahrzeugs
     * @param pTankstand           Tankstand des Fahrzeugs
     * @param pCo2Ausstoss         CO2 Ausstoss des Fahrzeugs
     */
    public void fahrzeugHinzufuegen(String pName, boolean pElektro, double pVerbrauchAusserorts, double pVerbrauchInnerorts,
                                    double pVerbrauchKombiniert, double pKmStand, int pTankstand, double pCo2Ausstoss, double pTankgroesse) throws GarageVollException {
        if (anzFahrzeuge < maxAnzFahrzeuge) {
            Fahrzeug neuAuto = new Fahrzeug(pName, pElektro, pVerbrauchAusserorts, pVerbrauchInnerorts, pVerbrauchKombiniert,
                    pKmStand, pTankstand, pCo2Ausstoss, pTankgroesse);
            fahrzeuge.add(neuAuto);
            anzFahrzeuge++;
        } else {
            throw new GarageVollException();
        }
    }

    /**
     * @param key Index des zu loeschenden Fahrzeugs in der ArrayList
     */
    public void fahrzeugLoeschen(int key) throws GarageLeerException {
        if (!fahrzeuge.isEmpty()) {
            if (fahrzeuge.get(key) != null) {
                fahrzeuge.remove(key);
                anzFahrzeuge--;
            }
        } else {
            throw new GarageLeerException();
        }
    }

    /**
     * Loescht ein Objekt anhand des Objekt selbst ohne den Key zu kennen.
     * @param delFahrzeug zu loeschendes Fahrzeug
     */
    public void fahrzeugLoeschen(Fahrzeug delFahrzeug) throws GarageLeerException {
        int index = this.fahrzeuge.indexOf(delFahrzeug);
        if (index != -1) {
            fahrzeugLoeschen(index);
        }

    }

    /**
     * @param key Index des Fahrzeugs in der ArrayList, welches an auswählen möchte
     */
    public void fahrzeugAuswaehlen(int key) {
        setAusgewaehltesFahrzeug(fahrzeuge.get(key));
    }

    /**
     * @return Gibt zurück, ob die Garage leer ist oder nicht
     */
    public boolean isEmpty() {
        return anzFahrzeuge == 0;
    }

    /**
     * Serialisiert ArrayList<Fahrzeuge> in File filename
     */
    public void save(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(this.fahrzeuge);
            } catch (IOException e) {
                System.out.println("An IO Error has occured");
                e.printStackTrace();
            }

        } catch (FileNotFoundException e ) {
            System.out.println("Datei " + fileName + " konnte nicht geoeffnet werden!");
            e.printStackTrace();
        }
    }

    /**
     * Laedt Fahrzeuge als ArrayList aus Fahrzeugen
     * Context uebergeben durch z.B. getApplicationContext(), getContext(), getBaseContext or this
     */
    public void load(Context context) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            try {
                ObjectInputStream os = new ObjectInputStream(fis);
                ArrayList<Fahrzeug> importedFahrzeuge = (ArrayList<Fahrzeug>) os.readObject();
                this.fahrzeuge = importedFahrzeuge;

            } catch (IOException e) {
                System.err.println("An IO error occured");
            } catch (ClassNotFoundException e) {
                System.err.println("Object not found!");
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found:" + fileName);
        }
    }

    public boolean contains(Fahrzeug fahrzeug) {
        return this.fahrzeuge.contains(fahrzeug);
    }

}
