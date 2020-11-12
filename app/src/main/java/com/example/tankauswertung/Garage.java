package com.example.tankauswertung;

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
     * Array zum speichern der Fahrzeuge
     */
    private ArrayList<Fahrzeug> fahrzeuge; //Umgeschrieben in ArrayList
    /**
     * Momentan ausgewaehltes Fahrzeug im UI
     */
    private Fahrzeug ausgewaehltesFahrzeug;

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
     * Gibt das momentan aktive ausgewaehltes Fahrzeug zurück
     *
     * @return Fahrzeug Objekt
     */
    public Fahrzeug getAusgewaehltesFahrzeug() {
        return ausgewaehltesFahrzeug;
    }

    public void setAusgewaehltesFahrzeug(Fahrzeug ausgewaehltesFahrzeug) {
        this.ausgewaehltesFahrzeug = ausgewaehltesFahrzeug;
    }

    /**
     * Fügt ein bestehendes Fahrzeug zur Garage hinzu
     *
     * @param neuAuto hinzuzufuegendes Fahrzeug
     */
    public void fahrzeugHinzufuegen(Fahrzeug neuAuto)  throws GarageVollException {
        if (anzFahrzeuge <= maxAnzFahrzeuge) {
            fahrzeuge.add(neuAuto);
            this.setAusgewaehltesFahrzeug(neuAuto);
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
                                    double pVerbrauchKombiniert, double pKmStand, int pTankstand, double pCo2Ausstoss) throws GarageVollException {
        if (anzFahrzeuge < maxAnzFahrzeuge) {
            Fahrzeug neuAuto = new Fahrzeug(pName, pElektro, pVerbrauchAusserorts, pVerbrauchInnerorts, pVerbrauchKombiniert,
                    pKmStand, pTankstand, pCo2Ausstoss);
            fahrzeuge.add(neuAuto);
            anzFahrzeuge++;
        }
        else {
            throw new GarageVollException();
        }
    }

    /**
     *
     * @param key Index des zu loeschenden Fahrzeugs in der ArrayList
     */
    public void fahrzeugLoeschen(int key)  throws GarageLeerException {
        if (!fahrzeuge.isEmpty()) {
            if (fahrzeuge.get(key) != null) {
                fahrzeuge.remove(key);
                anzFahrzeuge--;
            } else {
                throw new GarageLeerException();
            }
        }
    }

    /**
     *
     * @param key Index des Fahrzeugs in der ArrayList, welches an auswählen möchte
     */
    public void fahrzeugAuswaehlen(int key) {
        setAusgewaehltesFahrzeug(fahrzeuge.get(key));
    }

    /**
     *
     * @return Gibt zurück, ob die Garage leer ist oder nicht
     */
    public boolean isEmpty() {
        return anzFahrzeuge == 0;
    }

    public void save() {
        FileOutputStream fs = null;
        ObjectOutputStream os = null;
        try {
            fs = new FileOutputStream("Fahrzeug.dat");
        } catch (FileNotFoundException e) {
            System.out.println("File kann nicht geoeffnet werden");
        }
        try {
            os = new ObjectOutputStream(fs);
        } catch (IOException e) {
            System.out.println("An IO Error Occured");
        }
        for (int i = 0 ; i < anzFahrzeuge; i++) {
            try {
                assert os != null;
                os.writeObject((Fahrzeug) fahrzeuge.get(i));
            } catch (IOException e) {
                System.out.println("An IO Error Occured");
            }
        }
        // Testing
    }

    /*

    garage.load();
    garage.save();   // Garagenobjekt speichern oder einzelne Fahrzeugobjekte speichern?

    
     */
}
