package com.example.tankauswertung;

import com.example.tankauswertung.exceptions.GarageLeerException;
import com.example.tankauswertung.exceptions.GarageVollException;

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
    private Fahrzeug[] fahrzeuge; //Umgeschrieben in Array
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
    }

    /**
     * Erstellt ein neues Garage Objekt und legt die maximale Anzahl an Fahrzeugen in der Garage fest.
     *
     * @param fahrzeugeAnz maximale Anzahl an Fahrzeugen in der Garage
     */
    public Garage(int fahrzeugeAnz) {
        this.maxAnzFahrzeuge = fahrzeugeAnz;
        this.anzFahrzeuge = 0;
        //Initialisierung des Arrays?
    }

    /**
     * Gibt das momentan aktive ausgewaehltes Fahrzeug zurück
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
    public void fahrzeugHinzufuegen(Fahrzeug neuAuto) throws GarageVollException {
        if (anzFahrzeuge <= maxAnzFahrzeuge) {
            fahrzeuge[anzFahrzeuge] = neuAuto;
            this.setAusgewaehltesFahrzeug(neuAuto);
        }
        else {
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
        if (anzFahrzeuge <= maxAnzFahrzeuge) {
            Fahrzeug neuAuto = new Fahrzeug(pName, pElektro, pVerbrauchAusserorts, pVerbrauchInnerorts, pVerbrauchKombiniert,
                    pKmStand, pTankstand, pCo2Ausstoss);
            fahrzeuge[anzFahrzeuge] = neuAuto;
            anzFahrzeuge++;
        }
        else {
            throw new GarageVollException();
        }
    }

    public void fahrzeugLoeschen(int key) throws GarageLeerException {  //Nochmal durchdenken
        if(fahrzeuge[key]!=null) {
            for (int i = key; i < maxAnzFahrzeuge; i++) {
                fahrzeuge[i] = fahrzeuge[i + 1];
                if (fahrzeuge[i + 1] == null) {
                    break;
                }
            }
            anzFahrzeuge--;
        }
        else {
            throw new GarageLeerException();
        }
    }

    public void fahrzeugAuswaehlen(int key) {
        setAusgewaehltesFahrzeug(fahrzeuge[key]);
    }

    public boolean isEmpty() {
        return anzFahrzeuge == 0;
    }


/* (In die Fahrzeugklasse geschoben, um keinen Fahrzeugkey mitgeben zu müssen)
    public void fahrzeugAendern(String pName, boolean pElektro, double pVerbrauchAusserorts, double pVerbrauchInnerorts,
                                double pVerbrauchKombiniert, double pKmStand, int pTankstand, double pCo2Ausstoss) {
        ausgewaehltesFahrzeug.setName(pName);
        ausgewaehltesFahrzeug.setElektro(pElektro);
        ausgewaehltesFahrzeug.setVerbrauchInnerorts(pVerbrauchInnerorts);
        ausgewaehltesFahrzeug.setVerbrauchAusserorts(pVerbrauchAusserorts);
        ausgewaehltesFahrzeug.setVerbrauchKombiniert(pVerbrauchKombiniert);
        ausgewaehltesFahrzeug.setKmStand(pKmStand);
        ausgewaehltesFahrzeug.setTankstand(pTankstand);
        ausgewaehltesFahrzeug.setCo2Ausstoss(pCo2Ausstoss);
    }
*/

    /*

    garage.load();
    garage.save();   //Garagenobjekt speichern oder einzelne Fahrzeugobjekte speichern?
    garage.isEmpty();

    Get/Set Methode(-n) in Garage oder Fahrzeug?


     */


}
