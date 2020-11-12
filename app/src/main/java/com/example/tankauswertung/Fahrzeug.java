package com.example.tankauswertung;

/**
 * Klasse Fahrzeuge
 * Anlegen und Bearbeiten von Fahrzeugen
 */
public class Fahrzeug {
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
     * Konstruktor fuer neues Fahrzeug, setzt alle Attribute auf Parameterwerte
     */
    public Fahrzeug(String pName, boolean pElektro, double pVerbrauchAusserorts, double pVerbrauchInnerorts,
                    double pVerbrauchKombiniert, double pKmStand, int pTankstand, double pCo2Ausstoss) {
        this.name = pName;
        this.elektro = pElektro;
        this.verbrauchInnerorts = pVerbrauchInnerorts;
        this.verbrauchAusserorts = pVerbrauchAusserorts;
        this.verbrauchKombiniert = pVerbrauchKombiniert;
        this.kmStand = pKmStand;
        this.tankstand = pTankstand;
        this.co2Ausstoss = pCo2Ausstoss;
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
     * Setter fuer den Namen
     *
     * @param name Name der gesetzt werden soll
     * @return Gibt true zurueck, falls der Nae gesetzt werden konnte. False, wenn der Parameter leer ist
     */
    public boolean setName(String name) {
        if (name != null && !name.isEmpty()) {
            return false;
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
     * @return Gibt true zurueck, wenn der Wert gesetzt wurde. False, wenn der Parameter kleiner null ist
     */
    public boolean setVerbrauchInnerorts(double verbrauchInnerorts) {
        if (verbrauchInnerorts < 0) {
            return false;
        }
        this.verbrauchInnerorts = verbrauchInnerorts;
        return true;
    }

    /**
     * Setter fuer Verbrauch ausserorts
     *
     * @param verbrauchAusserorts double-Wert, der gesetzt werden soll
     * @return Gibt true zurueck, wenn der Wert gesetzt wurde. False, wenn der Parameter kleiner null ist
     */
    public boolean setVerbrauchAusserorts(double verbrauchAusserorts) {
        if (verbrauchAusserorts < 0) {
            return false;
        }
        this.verbrauchAusserorts = verbrauchAusserorts;
        return true;
    }

    /**
     * Setter fuer Verbrauch kombiniert
     *
     * @param verbrauchKombiniert double-Wert, der gesetzt werden soll
     * @return Gibt true zurueck, wenn der Wert gesetzt wurde. False, wenn der Parameter kleiner null ist
     */
    public boolean setVerbrauchKombiniert(double verbrauchKombiniert) {
        if (verbrauchKombiniert < 0) {
            return false;
        }
        this.verbrauchKombiniert = verbrauchKombiniert;
        return true;
    }

    /**
     * Setter fuer Kilometerstand
     *
     * @param kmStand double-Wert, der gesetzt werden soll
     * @return Gibt true zurueck, wenn der Wert gesetzt wurde. False, wenn der Parameter kleiner null ist
     */
    public boolean setKmStand(double kmStand) {
        if (kmStand < 0) {
            return false;
        }
        this.kmStand = kmStand;
        return true;
    }

    /**
     * Setter fuer den Kilometerstand
     *
     * @param tankstand Integer-Wert, der gesetzt werden soll (Prozentzahl)
     * @return Gibt true zurueck, wenn der Wert gesetzt wurde. False, wenn der Parameter kleiner null oder groesser 100 ist
     */
    public boolean setTankstand(double tankstand) {
        if (tankstand < 0 || tankstand > 100) {
            return false;
        }
        this.tankstand = tankstand;
        return true;
    }

    /**
     * Setter fuer den CO2-Ausstoss
     *
     * @param co2Ausstoss double-Wert, der gesetzt werden soll
     * @return Gibt true zurueck, wenn der Wert gesetzt wurde. False, wenn der Parameter kleiner null ist
     */
    public boolean setCo2Ausstoss(double co2Ausstoss) {
        if (co2Ausstoss < 0) {
            return false;
        }
        this.co2Ausstoss = co2Ausstoss;
        return true;
    }

    /**
     *
     * @param pName String, Name des Autos, der gesetzt werden soll
     * @param pElektro boolean, True, wenn es sich um ein Elektroauto handelt
     * @param pVerbrauchAusserorts double, Verbrauch des Autos Ausserorts, der gesetzt werden soll
     * @param pVerbrauchInnerorts double, Verbrauch des Autos Innerorts, der gesetzt werden soll
     * @param pVerbrauchKombiniert double, kombinierter Verbrauch des Autos, der gesetzt werden soll
     * @param pKmStand double, aktueller Kilometerstand des Autos, der gesetzt werden soll
     * @param pTankstand double, aktueller Tankstand des Autos, der gesetzt werden soll
     * @param pCo2Ausstoss doule, C02-Ausstoss des Autos, der gesetzt werden soll
     */
    public void fahrzeugAendern(String pName, boolean pElektro, double pVerbrauchAusserorts, double pVerbrauchInnerorts,
                                double pVerbrauchKombiniert, double pKmStand, double pTankstand, double pCo2Ausstoss) {
        this.setName(pName);
        this.setElektro(pElektro);
        this.setVerbrauchInnerorts(pVerbrauchInnerorts);
        this.setVerbrauchAusserorts(pVerbrauchAusserorts);
        this.setVerbrauchKombiniert(pVerbrauchKombiniert);
        this.setKmStand(pKmStand);
        this.setTankstand(pTankstand);
        this.setCo2Ausstoss(pCo2Ausstoss);
    }


}
