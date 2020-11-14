package com.example.tankauswertung;

import com.example.tankauswertung.exceptions.FahrzeugWertException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasse Fahrzeuge
 * Anlegen und Bearbeiten von Fahrzeugen
 */
public class Fahrzeug implements Serializable {
    /**
     * Bezeichnung der Fahrzeugs
     */
    private String name;

    /**
     * Boolean, ob es sich um ein E-Auto handelt
     */
    private boolean elektro;

    /**
     * Angabe zum Verbrauch innerorts
     */
    private double verbrauchInnerorts;

    /**
     * Angabe zum Verbrauch ausserorts
     */
    private double verbrauchAusserorts;

    /**
     * Angabe zum Verbrauch kombiniert
     */
    private double verbrauchKombiniert;

    /**
     * Aktueller Kilometerstand
     */
    private double kmStand;

    /**
     * Aktueller Tankstand
     * Angabe in Prozent (0-100)
     */
    private double tankstand;

    /**
     * Herstellerangabe zum CO2-Ausstoss
     */
    private double co2Ausstoss;

    /**
     * Tankgroesse des Fahrzeugs in Litern
     */
    private double tankgroesse;

    /**
     * ArrayList zur Speicherung der Strecken fuer ein Fahrzeug
     */
    private ArrayList<Strecke> strecken;

    /**
     * ArrayList zur Speicherung der Tankvorgaenge fuer ein Fahrzeug
     */
    private ArrayList<Tankvorgang> tankvorgaenge;

    /**
     * Konstruktor fuer neues Fahrzeug, setzt alle Attribute auf Parameterwerte
     */
    public Fahrzeug(String pName, boolean pElektro, double pVerbrauchAusserorts, double pVerbrauchInnerorts,
                    double pVerbrauchKombiniert, double pKmStand, int pTankstand, double pCo2Ausstoss, double pTankgroesse) {
        try {
            this.setName(pName);
            this.setElektro(pElektro);
            this.setVerbrauchAusserorts(pVerbrauchAusserorts);
            this.setVerbrauchInnerorts(pVerbrauchInnerorts);
            this.setVerbrauchKombiniert(pVerbrauchKombiniert);
            this.setKmStand(pKmStand);
            this.setTankstand(pTankstand);
            this.setCo2Ausstoss(pCo2Ausstoss);
            this.setTankgroesse(pTankgroesse);
        } catch (FahrzeugWertException e) {
            e.printStackTrace();
            System.err.println("Fahrzeug konnte nicht angelegt werden, da ungueltige Werte eingegeben wurden.");
        }
        this.strecken = new ArrayList<>();
        this.tankvorgaenge = new ArrayList<>();
    }

    /**
     * Getter fuer den Namen
     *
     * @return Gibt die Bezeichnung des Fahrzeusg zurueck
     */
    public String getName() {
        return name;
    }

    /**
     * Getter fuer boolean Elektro
     *
     * @return Gibt zurueck ob es sich um ein E-Auto handelt
     */
    public boolean isElektro() {
        return elektro;
    }

    /**
     * Getter fuer Verbrauch Innerorts
     *
     * @return Gibt den Verbrauch innerorts als double zurueck
     */
    public double getVerbrauchInnerorts() {
        return verbrauchInnerorts;
    }

    /**
     * Getter fuer Verbrauch ausserorts
     *
     * @return Gibt den Verbrauch ausserorts als double zurueck
     */
    public double getVerbrauchAusserorts() {
        return verbrauchAusserorts;
    }

    /**
     * Getter fuer Verbrauch kombiniert
     *
     * @return Gibt den Verbrauch kombiniert als double zurueck
     */
    public double getVerbrauchKombiniert() {
        return verbrauchKombiniert;
    }

    /**
     * Getter fuer Kilometerstand
     *
     * @return Gibt den Kilometerstand als double zurueck
     */
    public double getKmStand() {
        return kmStand;
    }

    /**
     * Getter fuer den Tankstand
     *
     * @return Gibt den aktuellen Tankstand als int zurueck
     */
    public double getTankstand() {
        return tankstand;
    }

    /**
     * Getter fuer den CO2 Ausstoss
     *
     * @return Gibt den CO2-Ausstoss als double zurueck
     */
    public double getCo2Ausstoss() {
        return co2Ausstoss;
    }

    /**
     * Getter fuer die Tankgroesse des Fahrzeugs
     *
     * @return Tankgroesse in Litern
     */
    public double getTankgroesse() {
        return tankgroesse;
    }

    /**
     * Getter fuer das gesamte Strecken-Array
     *
     * @return ArrayList<Strecke>, Alle Strecken (Aktuellste Strecke auf Index 0)
     */
    public ArrayList<Strecke> getStrecken() {
        return strecken;
    }

    /**
     * Getter fuer das gesamte Tankvorgaenge-Array
     *
     * @return ArrayList<Tankvorgang>, Alle Tankvorgaenge (Aktuellster Tankvorgang auf Index 0)
     */
    public ArrayList<Tankvorgang> getTankvorgaenge() {
        return tankvorgaenge;
    }

    /**
     * Setter fuer den Namen
     *
     * @param name Name der gesetzt werden soll
     * @return Gibt true zurueck, falls der Name gesetzt werden konnte.
     * @throws FahrzeugWertException Wenn der Parameter leer ist.
     */
    public boolean setName(String name) throws FahrzeugWertException {
        if (name != null && name.trim().length() == 0) {
            throw new FahrzeugWertException("Name kann nicht gesetzt werden, da ein ungueltiger Wert eingegeben wurde.");
        }
        this.name = name;
        return true;
    }

    /**
     * Setter fuer boolean Elektro
     *
     * @param elektro Boolean, der gesetzt werden soll
     * @return Gibt true zurueck, wenn das Attribut gesetzt wurde
     */
    public boolean setElektro(boolean elektro) {
        this.elektro = elektro;
        return true;
    }

    /**
     * Setter fuer Verbrauch innerorts
     *
     * @param verbrauchInnerorts double-Wert, der gesetzt werden soll
     * @return Gibt true zurueck, wenn der Wert gesetzt wurde.
     * @throws FahrzeugWertException Wenn der Parameter kleiner null ist
     */
    public boolean setVerbrauchInnerorts(double verbrauchInnerorts) throws FahrzeugWertException {
        if (verbrauchInnerorts < 0) {
            throw new FahrzeugWertException("Verbrauch Innerorts konnte nicht gesetzt werden, da ein negativer Wert eingegeben wurde.");
        }
        this.verbrauchInnerorts = verbrauchInnerorts;
        return true;
    }

    /**
     * Setter fuer Verbrauch ausserorts
     *
     * @param verbrauchAusserorts double-Wert, der gesetzt werden soll
     * @return Gibt true zurueck, wenn der Wert gesetzt wurde.
     * @throws FahrzeugWertException Wenn der Parameter kleiner null ist
     */
    public boolean setVerbrauchAusserorts(double verbrauchAusserorts) throws FahrzeugWertException {
        if (verbrauchAusserorts < 0) {
            throw new FahrzeugWertException("Verbrauch Ausserorts konnte nicht gesetzt werden, da ein negativer Wert eingegeben wurde.");
        }
        this.verbrauchAusserorts = verbrauchAusserorts;
        return true;
    }

    /**
     * Setter fuer Verbrauch kombiniert
     *
     * @param verbrauchKombiniert double-Wert, der gesetzt werden soll
     * @return Gibt true zurueck, wenn der Wert gesetzt wurde.
     * @throws FahrzeugWertException Wenn der Parameter kleiner null ist
     */
    public boolean setVerbrauchKombiniert(double verbrauchKombiniert) throws FahrzeugWertException {
        if (verbrauchKombiniert < 0) {
            throw new FahrzeugWertException("Verbrauch kombiniert konnte nicht gesetzt werden, da ein negativer Wert eingegeben wurde.");
        }
        this.verbrauchKombiniert = verbrauchKombiniert;
        return true;
    }

    /**
     * Setter fuer Kilometerstand
     *
     * @param kmStand double-Wert, der gesetzt werden soll
     * @return Gibt true zurueck, wenn der Wert gesetzt wurde.
     * @throws FahrzeugWertException Wenn der Parameter kleiner null ist
     */
    public boolean setKmStand(double kmStand) throws FahrzeugWertException {
        if (kmStand < 0) {
            throw new FahrzeugWertException("Kilometerstand konnte nicht gesetzt werden, da ein negativer Wert eingegeben wurde.");
        }
        this.kmStand = kmStand;
        return true;
    }

    /**
     * Setter fuer den Tankstand
     *
     * @param tankstand Integer-Wert, der gesetzt werden soll (Prozentzahl)
     * @return Gibt true zurueck, wenn der Wert gesetzt wurde.
     * @throws FahrzeugWertException Wenn der Parameter kleiner null oder groesser 100 ist
     */
    public boolean setTankstand(double tankstand) throws FahrzeugWertException {
        if (tankstand < 0 || tankstand > 100) {
            throw new FahrzeugWertException("Tankstand konnte nicht gesetzt werden, da kein Wert zwischen 0 und 100 eingegeben wurde.");
        }
        this.tankstand = tankstand;
        return true;
    }

    /**
     * Setter fuer den CO2-Ausstoss
     *
     * @param co2Ausstoss double-Wert, der gesetzt werden soll
     * @return Gibt true zurueck, wenn der Wert gesetzt wurde.
     * @throws FahrzeugWertException Wenn der Parameter kleiner null ist
     */
    public boolean setCo2Ausstoss(double co2Ausstoss) throws FahrzeugWertException {
        if (co2Ausstoss < 0) {
            throw new FahrzeugWertException("CO2-Ausstoss konnte nicht gesetzt werden, da ein negativer Wert eingegeben wurde.");
        }
        if (this.elektro == true && co2Ausstoss != 0) {
            throw new FahrzeugWertException("Bei einem Elektroauto kann kein CO2-Ausstoss groesser 0 eingegeben werden.");
        }
        this.co2Ausstoss = co2Ausstoss;
        return true;
    }

    /**
     * Setter fuer die Tankgroesse des Fahrzeugs
     *
     * @param tankgroesse double, Tangroesse in Litern
     * @return Gibt true zurueck, wenn der Wert gesetzt wurde.
     * @throws FahrzeugWertException Wenn der Parameter kleiner null ist
     */
    public boolean setTankgroesse(double tankgroesse) throws FahrzeugWertException {
        if (tankgroesse < 0) {
            throw new FahrzeugWertException("Tankgroesse konnte nicht gesetzt werden, da ein negativer Wert eingegeben wurde.");
        }
        this.tankgroesse = tankgroesse;
        return true;
    }


    /**
     * @param pName                String, Name des Autos, der gesetzt werden soll
     * @param pElektro             boolean, True, wenn es sich um ein Elektroauto handelt
     * @param pVerbrauchAusserorts double, Verbrauch des Autos Ausserorts, der gesetzt werden soll
     * @param pVerbrauchInnerorts  double, Verbrauch des Autos Innerorts, der gesetzt werden soll
     * @param pVerbrauchKombiniert double, kombinierter Verbrauch des Autos, der gesetzt werden soll
     * @param pKmStand             double, aktueller Kilometerstand des Autos, der gesetzt werden soll
     * @param pTankstand           double, aktueller Tankstand des Autos, der gesetzt werden soll
     * @param pCo2Ausstoss         double, C02-Ausstoss des Autos, der gesetzt werden soll
     * @param pTankgroesse         double, Tankgroesse des Autos in Litern
     */
    public void fahrzeugAendern(String pName, boolean pElektro, double pVerbrauchAusserorts, double pVerbrauchInnerorts,
                                double pVerbrauchKombiniert, double pKmStand, double pTankstand, double pCo2Ausstoss, double pTankgroesse) {
        try {
            this.setName(pName);
            this.setElektro(pElektro);
            this.setVerbrauchInnerorts(pVerbrauchInnerorts);
            this.setVerbrauchAusserorts(pVerbrauchAusserorts);
            this.setVerbrauchKombiniert(pVerbrauchKombiniert);
            this.setKmStand(pKmStand);
            this.setTankstand(pTankstand);
            this.setCo2Ausstoss(pCo2Ausstoss);
            this.setTankgroesse(pTankgroesse);
        } catch (FahrzeugWertException e) {
            e.printStackTrace();
            System.err.println("Fahrzeug konnte nicht geaendert werden, ungueltige Werte wurden eingegeben.");
        }
    }

    /**
     * Methode zum Hinzufuegen einer gefahrenen Strecke in die "Strecken"-ArrayList am Index 0 (Anfang der Liste)
     *
     * @param pDistanz     double, Distanz in Kilometern
     * @param pStreckentyp Enum Streckentyp, Streckentyp, welcher eingeben wird (Innerorts, Ausserorts, kombiniert)
     * @param pTankstand   double, Tankstand nach dem Fahren der Strecke
     */
    public void streckeHinzufuegen(double pDistanz, Strecke.Streckentyp pStreckentyp, double pTankstand) {
        strecken.add(0, new Strecke(pDistanz, pStreckentyp, pTankstand));
    }


    /**
     * Methode zum Hinzufuegen eines Tankvorgangs in die "tankvorgaenge"-ArrayList am Index 0 (Anfang der Liste)
     *
     * @param pGetankteMenge double, getankte Menge in Litern
     * @param pPreis         double, gezahlter Preis in Euro
     * @param pImg           String, Pfad zum Foto
     */
    public void tangvorgangHinzufuegen(double pGetankteMenge, double pPreis, String pImg) {
        tankvorgaenge.add(0, new Tankvorgang(pGetankteMenge, pPreis, pImg));
    }


}
