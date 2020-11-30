package com.example.tankauswertung;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Klasse fuer die gefahrenen Strecken
 *
 * Anlegen und Bearbeiten von Strecken
 */
public class Strecke implements Serializable {

    /**
     * Distanz der Strecke in Kilometern
     */
    private double distanz;

    /**
     * Auswahlmöglichkeiten fuer den Streckentyp
     */
    public enum Streckentyp {INNERORTS, KOMBINIERT, AUSSERORTS} //Umgeändert von int in enum

    /**
     * Tankstand, welcher nach der gefahrenen Strecke übrig ist
     */
    private double tankstand;

    /**
     * Verbrauchte Menge an Treibstoff der auf der Srecke in Litern
     */
    private double verbrauchterTreibstoff;
    /**
     * Variable um den Streckentyp abzuspeichern
     */
    Streckentyp streckentyp;

    /**
     * Variable, welche das Format der Zeitangabe festlegt.
     */
    transient SimpleDateFormat formatter;

    /**
     * Variabe, welche die aktuelle Systemzeit zum Zeitpunkt der Eingabe einer Strecke speichert.
     */
    Date zeitstempel;

    /**
     * Variable die den CO2-Aussstoss der Strecke abspeichert.
     */
    double co2Ausstoss;

    /**
     * Konstruktor fuer die Klasse Strecke
     * Setzt alle Variablen auf den Wert des Eingabeparameters
     */
    public Strecke(double pDistanz, Streckentyp pStreckentyp, double pTankstand, double pCo2Ausstoss, double pVerbrauchterTreibstoff) {
        distanz = pDistanz;
        streckentyp = pStreckentyp;
        tankstand = pTankstand;
        formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        zeitstempel = new Date();
        co2Ausstoss = pCo2Ausstoss;
        verbrauchterTreibstoff = pVerbrauchterTreibstoff;
    }

    /**
     * Getter fuer den CO2-Ausstoss
     *
     * @return double, CO2-Ausstoss des Fahrzeugs bei der gefahrenen Strecke
     */
    public double getCo2Ausstoss() { return co2Ausstoss; }

    /**
     * Setter fuer den CO2-Ausstoss
     *
     * @param co2Ausstoss, double CO2-Ausstoss-Input
     */
    public void setCo2Ausstoss(double co2Ausstoss) { this.co2Ausstoss = co2Ausstoss; }

    /**
     * Getter fuer den Tankstand
     *
     * @return double, Tankstand nach der zurueckgelegten Strecke
     */
    public double getTankstand() {
        return tankstand;
    }

    /**
     * Getter fuer die zurueckgelegte Distanz
     *
     * @return double, zurueckgelegte Strecke in Kilometern
     */
    public double getDistanz() {
        return distanz;
    }

    /**
     * Getter fuer den gefahrenen Streckentyp
     *
     * @return Enum Streckentyp, Der gefahrene Streckentyp (Innerorts, Ausserorts, kombiniert)
     */
    public Streckentyp getStreckentyp() {
        return streckentyp;
    }

    /**
     * Getter fuer den Zeitstempel
     *
     * @return Date zeitstempel, Der Zeitpunkt der Abspeicherung.
     */
    public Date getZeitstempel() {
        return zeitstempel;
    }

    /**
     * Methode zum erlangen des Zeitstempels als String
     *
     * @return String format(this.zeitstempel), Ein zum String konvertiertes Date-Objekt.
     */
    public String getZeitstempelAsString() {
        return formatter.format(this.zeitstempel);
    }

    /**
     * Setter fuer den Tankstand nach der Strecke
     *
     * @param tankstand double, Tankstand in Litern
     */
    public void setTankstand(double tankstand) {
            this.tankstand = tankstand;
    }

    /**
     * Setter fuer die Streckendistanz
     *
     * @param distanz double, Distanz in Kilometer
     */
    public void setDistanz(double distanz) {
        this.distanz = distanz;
    }

    /**
     * Setter fuer den Streckentyp
     *
     * @param pStreckentyp Enum Streckentyp, Streckentyp, welcher ausgewählt wird (Innerorts, Ausserorts, kombiniert)
     */
    public void setStreckentyp(Streckentyp pStreckentyp) {
        this.streckentyp = pStreckentyp;
    }

    /**
     * Getter fuer den verbrauchten Treibstoff auf der Strecke
     *
     * @return double, Treibstoff, welcher verbraucht worden ist in Litern
     */
    public double getVerbrauchterTreibstoff() { return verbrauchterTreibstoff; }

    /**
     * Setter fuer den Treibstoffverbrauch auf der Strecke in Litern
     *
     * @param verbrauchterTreibstoff double, Treibstoff in Litern
     */
    public void setVerbrauchterTreibstoff(double verbrauchterTreibstoff) { this.verbrauchterTreibstoff = verbrauchterTreibstoff; }

    /**
     * Setter fuer alle Variablen einer Strecke
     *
     * @param pDistanz double, Distanz in Kilometern
     * @param pStreckentyp Enum Streckentyp, Art der Strecke (Innerorts, Ausserorts, kombiniert)
     * @param pTankstand double, Tankstand nach der Strecke in Litern.
     */
    public void streckeBearbeiten(double pDistanz, Streckentyp pStreckentyp, double pTankstand) {
        this.setDistanz(pDistanz);
        this.setStreckentyp(pStreckentyp);
        this.setTankstand(pTankstand);
    }
}
