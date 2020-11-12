package com.example.tankauswertung;

/**
 * Klasse fuer die gefahrenen Strecken
 *
 * Anlegen und Bearbeiten von Strecken
 */
public class Strecke {

    /**
     * Distanz der Strecke in Kilometern
     */
    private double distanz;

    /**
     * Auswahlmöglichkeiten fuer den Streckentyp
     */
    enum Streckentyp {innerorts, kombiniert, ausserorts} //Umgeändert von int in enum

    /**
     * Tankstand, welcher nach der gefahrenen Strecke übrig ist
     */
    private double tankstand;

    /**
     * Variable um den Streckentyp abzuspeichern
     */
    Streckentyp streckentyp;

    /**
     * Konstruktor fuer die Klasse Strecke
     * Setzt alle Variablen auf den Wert des Eingabeparameters
     */
    public Strecke(double pDistanz, Streckentyp pStreckentyp, double pTankstand) {
        distanz = pDistanz;
        streckentyp = pStreckentyp;
        tankstand = pTankstand;
    }

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
