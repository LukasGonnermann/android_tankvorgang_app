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
    private final String fileName = "Fahrzeuge.dat";


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

    /**
     * Speichert Fahrzeuglistenelemente in einer Datei (in Attributen von Garage angegeben)
     */
    public void save() {
        FileOutputStream fs = null;
        ObjectOutputStream os = null;

        // Initialisiere Streams
        try {
            fs = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            System.err.println("Can't Open File: " + fileName);
            e.printStackTrace();
        }
        try {
            os = new ObjectOutputStream(fs);
        } catch (IOException e) {
            System.err.println("An IO Error Occured");
            e.printStackTrace();
        }

        // schreibe alle Fahrzeuge
        if (os != null && fs != null) { // TODO noetig?
            for (int i = 0 ; i < anzFahrzeuge; i++) {
                try {
                    os.writeObject((Fahrzeug) fahrzeuge.get(i));
                } catch (IOException e) {
                    System.err.println("An IO Error Occured");
                    e.printStackTrace();
                }
            }
        }

        // TODO Testing
    }

    /**
     * Laedt Fahrzeug Objekte aus einer Datein(in Attributen von Garage angegeben) und ersetzt aktuelle Fahrzeugliste mit geladener Fahrzeugliste
     */
    public void load() {
        FileInputStream fs = null;
        ObjectInputStream os = null;
        ArrayList<Fahrzeug> importedFahrzeuge = new ArrayList<>();

        // Initialisiere Streams
        try {
            fs = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            System.err.println("Can't Open File: " + fileName);
            e.printStackTrace();
        }
        try {
            os = new ObjectInputStream(fs);
        } catch (IOException e) {
            System.err.println("An IO Error Occured");
            e.printStackTrace();
        }

        // Lesen der Fahrzeuge und speichern in importedFahrzeuge
        if (os != null && fs != null) { // TODO noetig?
            // TODO EOF Schleife
            try {
                Fahrzeug fileFahrzeug = (Fahrzeug) os.readObject();
                importedFahrzeuge.add(fileFahrzeug);
            } catch (ClassNotFoundException e) {
                System.err.println("Class Not Found Exception");
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("An IO Error Occured");
                e.printStackTrace();
            }
        }
        // Alle Fahrzeuge überschreiben
        this.fahrzeuge = importedFahrzeuge;
    }

    /*

    garage.load();
    garage.save();   // Garagenobjekt speichern oder einzelne Fahrzeugobjekte speichern?

    
     */
}
