package com.example.tankauswertung;

import java.util.Hashtable;

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
     * Hashtable zum speichern der Fahrzeuge in der Garage (geringster Suchaufwand)
     */
    private Hashtable<Integer, Fahrzeug> fahrzeuge;
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
        this.fahrzeuge = new Hashtable<>(25);

    }

    public Garage(int fahrzeugeAnz) {
        this.maxAnzFahrzeuge = fahrzeugeAnz;
        this.anzFahrzeuge = 0;
        this.fahrzeuge = new Hashtable<>(fahrzeugeAnz);
    }

    public void fahrzeugHinzufuegen(Fahrzeug neuAuto) {
        if (anzFahrzeuge <= maxAnzFahrzeuge) {
            fahrzeuge.put(anzFahrzeuge, neuAuto); // TODO
        }
    }

    public void fahrzeugHinzufuegen(String pName, boolean pElektro, double pVerbrauchAusserorts, double pVerbrauchInnerorts,
                                   double pVerbrauchKombiniert, double pKmStand, int pTankstand, double pCo2Ausstoss) {
        if (anzFahrzeuge <= maxAnzFahrzeuge) {
            Fahrzeug neuAuto = new Fahrzeug(pName, pElektro, pVerbrauchAusserorts, pVerbrauchInnerorts, pVerbrauchKombiniert,
                    pKmStand, pTankstand, pCo2Ausstoss);
            fahrzeuge.put(anzFahrzeuge, neuAuto); // TODO
        }
    }


}
