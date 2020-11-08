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

    /**
     * Erstellt ein neues Garage Objekt und legt die maximale Anzahl an Fahrzeugen in der Garage fest.
     * @param fahrzeugeAnz maximale Anzahl an Fahrzeugen in der Garage
     */
    public Garage(int fahrzeugeAnz) {
        this.maxAnzFahrzeuge = fahrzeugeAnz;
        this.anzFahrzeuge = 0;
        this.fahrzeuge = new Hashtable<>(fahrzeugeAnz);
    }

    /**
     * Fügt ein bestehendes Fahrzeug zur Garage hinzu
     * @param neuAuto hinzuzufuegendes Fahrzeug
     */
    public void fahrzeugHinzufuegen(Fahrzeug neuAuto) {
        if (anzFahrzeuge <= maxAnzFahrzeuge) {
            fahrzeuge.put(anzFahrzeuge, neuAuto); // TODO
        }
    }

    /**
     * Erstellt ein Fahrzeug ueber Parameter und fuegt dieses hinzu
     * @param pName Anzeigename des Fahrzeugs
     * @param pElektro Ist ein Elektrofahrzeug?
     * @param pVerbrauchAusserorts Ausserorts Verbrauch des Fahrzeugs
     * @param pVerbrauchInnerorts Innerorts Verbrauch des Fahrzeugs
     * @param pVerbrauchKombiniert Kombinierter Verbrauch des Fahrzeugs
     * @param pKmStand Kilometerstand des Fahrzeugs
     * @param pTankstand Tankstand des Fahrzeugs
     * @param pCo2Ausstoss CO2 Ausstoss des Fahrzeugs
     */
    public void fahrzeugHinzufuegen(String pName, boolean pElektro, double pVerbrauchAusserorts, double pVerbrauchInnerorts,
                                   double pVerbrauchKombiniert, double pKmStand, int pTankstand, double pCo2Ausstoss) {
        if (anzFahrzeuge <= maxAnzFahrzeuge) {
            Fahrzeug neuAuto = new Fahrzeug(pName, pElektro, pVerbrauchAusserorts, pVerbrauchInnerorts, pVerbrauchKombiniert,
                    pKmStand, pTankstand, pCo2Ausstoss);
            fahrzeuge.put(anzFahrzeuge, neuAuto); // TODO
        }
    }


}
