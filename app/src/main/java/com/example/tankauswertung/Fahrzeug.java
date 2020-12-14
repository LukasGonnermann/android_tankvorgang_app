package com.example.tankauswertung;

import com.example.tankauswertung.exceptions.FahrzeugWertException;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

/**
 * Klasse Fahrzeuge
 * Anlegen und Bearbeiten von Fahrzeugen
 */
public class Fahrzeug implements Serializable {
    /**
     * Bezeichnung des Fahrzeugs
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
     * Speichert den bei Initialisierung eingegebenen Verbrauchswert
     */
    private double verbrauchInnerortsAnfangswert;
    /**
     * Angabe zum Verbrauch ausserorts
     */
    private double verbrauchAusserorts;

    /**
     * Speichert den bei Initialisierung eingegebenen Verbrauchswert
     */
    private double verbrauchAusserortsAnfangswert;

    /**
     * Angabe zum Verbrauch kombiniert
     */
    private double verbrauchKombiniert;

    /**
     * Speichert den bei Initialisierung eingegebenen Verbrauchswert
     */
    private double verbrauchKombiniertAnfangswert;

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
     * Variable, welche das Format der Zeitangabe festlegt.
     */
    transient SimpleDateFormat formatter;

    /**
     * Variabe, welche die aktuelle Systemzeit zum Zeitpunkt der Eingabe eines Fahrzeugs speichert.
     */
    Date zeitstempel;

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
        this.setVerbrauchAusserortsAnfangswert(pVerbrauchAusserorts);
        this.setVerbrauchInnerortsAnfangswert(pVerbrauchInnerorts);
        this.setVerbrauchKombiniertAnfangswert(pVerbrauchKombiniert);

        // Zeitsstempel auf aktuelles Datum und Uhrzeit setzen
        formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        zeitstempel = new Date();
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
     * Setter fuer Verbrauch Ausserorts Anfangswert
     *
     * @param verbrauchAusserortsAnfangswert double-Wert, der gesetzt werden soll
     */
    public void setVerbrauchAusserortsAnfangswert(double verbrauchAusserortsAnfangswert) {
        this.verbrauchAusserortsAnfangswert = verbrauchAusserortsAnfangswert;
    }

    /**
     * Setter fuer Verbrauch Innerorts Anfangswert
     *
     * @param verbrauchInnerortsAnfangswert double-Wert, der gesetzt werden soll
     */
    public void setVerbrauchInnerortsAnfangswert(double verbrauchInnerortsAnfangswert) {
        this.verbrauchInnerortsAnfangswert = verbrauchInnerortsAnfangswert;
    }

    /**
     * Setter fuer Verbrauch Kombiniert Anfangswert
     *
     * @param verbrauchKombiniertAnfangswert double-Wert, der gesetzt werden soll
     */
    public void setVerbrauchKombiniertAnfangswert(double verbrauchKombiniertAnfangswert) {
        this.verbrauchKombiniertAnfangswert = verbrauchKombiniertAnfangswert;
    }

    /**
     * Getter fuer den Anfangsverbrauchswert
     *
     * @return Gibt den Anfangsverbraucswert zurueck
     */
    public double getVerbrauchAusserortsAnfangswert() {
        return verbrauchAusserortsAnfangswert;
    }

    /**
     * Getter fuer den Anfangsverbrauchswert
     *
     * @return Gibt den Anfangsverbraucswert zurueck
     */
    public double getVerbrauchInnerortsAnfangswert() {
        return verbrauchInnerortsAnfangswert;
    }

    /**
     * Getter fuer den Anfangsverbrauchswert
     *
     * @return Gibt den Anfangsverbraucswert zurueck
     */
    public double getVerbrauchKombiniertAnfangswert() {
        return verbrauchKombiniertAnfangswert;
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
     * @return Gibt den aktuellen Tankstand als int zurueck, in Prozent
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
     * Getter für die gesamte Liste an Strecken und Tankvorgängen bzw. Timeline-Elementen der
     * Wrapperklasse Ereignis
     *
     * @return ArrayList<Ereignis>, Alle Ereignisse, sortiert nach Datum
     */
    public ArrayList<Ereignis> getEreignisse() {
        DecimalFormat dfLiter = new DecimalFormat("#.# l", new DecimalFormatSymbols(Locale.GERMAN));
        DecimalFormat dfPreis = new DecimalFormat("#.## €", new DecimalFormatSymbols(Locale.GERMAN));
        DecimalFormat dfDistanz = new DecimalFormat("#.# km", new DecimalFormatSymbols(Locale.GERMAN));

        ArrayList<Ereignis> ereignisse = new ArrayList<>();
        int i = 0, j = 0;
        Strecke aktuelleStrecke;
        Tankvorgang aktuellerTankvorgang;
        boolean keineStreckeMehr = i >= strecken.size();
        boolean keinTankvorgangMehr = j >= tankvorgaenge.size();

        if (!keineStreckeMehr) {
            aktuelleStrecke = strecken.get(0);
        } else {
            aktuelleStrecke = null;
        }

        if (!keinTankvorgangMehr) {
            aktuellerTankvorgang = tankvorgaenge.get(0);
        } else {
            aktuellerTankvorgang = null;
        }

        // Merge-Operation
        // solange nicht noch mindestens eine Strecke oder ein Tankvorgang vorhanden
        while (!(keineStreckeMehr && keinTankvorgangMehr)) {

            if (keinTankvorgangMehr || (aktuelleStrecke != null && aktuelleStrecke.getZeitstempel()
                    .compareTo(aktuellerTankvorgang.getZeitstempel()) > 0)) {

                // aktuelles Streckenelement ist aktueller
                // oder kein Tankvorgangelement mehr vorhanden
                Date datum = aktuelleStrecke.getZeitstempel();
                String strDistanz = dfDistanz.format(aktuelleStrecke.getDistanz());

                String strStreckentyp = aktuelleStrecke.getStreckentyp().toString().toLowerCase();
                // ß verwenden
                if (strStreckentyp.equals("ausserorts")) {
                    strStreckentyp = "außerorts";
                }

                String beschreibung = strDistanz + ", " + strStreckentyp;
                ereignisse.add(new Ereignis(Ereignis.EreignisTyp.STRECKE, i, datum, beschreibung));

                // i (Strecken) weiter iterieren
                if (i + 1 >= strecken.size()) {
                    keineStreckeMehr = true;
                    aktuelleStrecke = null;
                    // Flag wird gesetzt, Index i bleibt aber trotzdem gleich, damit noch accessible
                } else {
                    i++;
                    aktuelleStrecke = strecken.get(i);
                }

            } else {
                // keineStreckeMehr || (aktuelleStrecke != null && aktuelleStrecke.getZeitstempel().compareTo(aktuellerTankvorgang.getZeitstempel())) <= 0
                Date datum = aktuellerTankvorgang.getZeitstempel();
                String strGetankteMenge = dfLiter.format(aktuellerTankvorgang.getGetankteMenge());
                String strPreis = dfPreis.format(aktuellerTankvorgang.getPreis());
                String beschreibung = strGetankteMenge + ", " + strPreis;
                ereignisse.add(new Ereignis(Ereignis.EreignisTyp.TANKVORGANG, j, datum, beschreibung));

                // j (Tankvorgänge) weiter iterieren
                if (j + 1 >= tankvorgaenge.size()) {
                    keinTankvorgangMehr = true;
                    aktuellerTankvorgang = null;
                } else {
                    j++;
                    aktuellerTankvorgang = tankvorgaenge.get(j);
                }
            }
        }
        return ereignisse;
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
        if (this.elektro && co2Ausstoss != 0) {
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
     * Aendern eines bestehenden Fahrzeugs
     * Ruft alle Setter mit den Parameterwerten auf
     *
     * @param pName                           String, Name des Autos, der gesetzt werden soll
     * @param pElektro                        boolean, True, wenn es sich um ein Elektroauto handelt
     * @param pVerbrauchAusserortsAnfangswert double, Verbrauch des Autos Ausserorts, der gesetzt werden soll
     * @param pVerbrauchInnerortsAnfangswert  double, Verbrauch des Autos Innerorts, der gesetzt werden soll
     * @param pVerbrauchKombiniertAnfangswert double, kombinierter Verbrauch des Autos, der gesetzt werden soll
     * @param pKmStand                        double, aktueller Kilometerstand des Autos, der gesetzt werden soll
     * @param pTankstand                      double, aktueller Tankstand des Autos, der gesetzt werden soll
     * @param pCo2Ausstoss                    double, C02-Ausstoss des Autos, der gesetzt werden soll
     * @param pTankgroesse                    double, Tankgroesse des Autos in Litern
     */
    public void fahrzeugAendern(String pName, boolean pElektro, double pVerbrauchAusserortsAnfangswert, double pVerbrauchInnerortsAnfangswert,
                                double pVerbrauchKombiniertAnfangswert, double pKmStand, double pTankstand, double pCo2Ausstoss, double pTankgroesse) {
        try {
            this.setName(pName);
            this.setElektro(pElektro);
            this.setVerbrauchInnerortsAnfangswert(pVerbrauchInnerortsAnfangswert);
            this.setVerbrauchAusserortsAnfangswert(pVerbrauchAusserortsAnfangswert);
            this.setVerbrauchKombiniertAnfangswert(pVerbrauchKombiniertAnfangswert);
            this.setKmStand(pKmStand);
            this.setTankstand(pTankstand);
            this.setCo2Ausstoss(pCo2Ausstoss);
            this.setTankgroesse(pTankgroesse);
        } catch (FahrzeugWertException e) {
            e.printStackTrace();
            System.err.println("Fahrzeug konnte nicht geaendert werden, ungueltige Werte wurden eingegeben.");
        }
        verbrauchAktualisieren();
    }

    /**
     * Aktualisiert den tatsächlichen Verbrauch des Autos, indem alle Strecken analysiert werden.
     */
    public void verbrauchAktualisieren() {

        // da Anfangswert als eigene Strecke gewertet wird
        int anzahlStreckenInnerorts = 1;
        int anzahlStreckenAusserorts = 1;
        int anzahlStreckenKombiniert = 1;

        double verbrauchterTreibstoffInnerorts = 0;
        double verbrauchterTreibstoffAusserorts = 0;
        double verbrauchterTreibstoffKombiniert = 0;
        double distanzInnerorts = 0;
        double distanzAusserorts = 0;
        double distanzKombiniert = 0;

        double verbrauchswertInnerorts, verbrauchswertAusserorts, verbrauchswertKombiniert;

        for (int i = 0; i < this.strecken.size(); i++) {

            Strecke strecke = strecken.get(i);

            switch (strecke.getStreckentyp()) {
                case INNERORTS:
                    verbrauchterTreibstoffInnerorts += strecke.getVerbrauchterTreibstoff();
                    distanzInnerorts += strecke.getDistanz();
                    anzahlStreckenInnerorts++;
                    break;
                case AUSSERORTS:
                    verbrauchterTreibstoffAusserorts += strecke.getVerbrauchterTreibstoff();
                    distanzAusserorts += strecke.getDistanz();
                    anzahlStreckenAusserorts++;
                    break;
                case KOMBINIERT:
                    verbrauchterTreibstoffKombiniert += strecke.getVerbrauchterTreibstoff();
                    distanzKombiniert += strecke.getDistanz();
                    anzahlStreckenKombiniert++;
                    break;
            }
        }

        if (distanzInnerorts == 0) {
            verbrauchswertInnerorts = getVerbrauchInnerortsAnfangswert();
        } else {
            double ungewichtet = verbrauchterTreibstoffInnerorts / distanzInnerorts * 100;
            verbrauchswertInnerorts
                    = getVerbrauchInnerortsAnfangswert() / anzahlStreckenInnerorts                  // Gewichtung mit 1/n
                    + ungewichtet / anzahlStreckenInnerorts * (anzahlStreckenInnerorts - 1);     // Gewichtung mit (n-1)/n
        }

        if (distanzAusserorts == 0) {
            verbrauchswertAusserorts = getVerbrauchAusserortsAnfangswert();
        } else {
            double ungewichtet = verbrauchterTreibstoffAusserorts / distanzAusserorts * 100;
            verbrauchswertAusserorts
                    = getVerbrauchAusserortsAnfangswert() / anzahlStreckenAusserorts                // Gewichtung mit 1/n
                    + ungewichtet / anzahlStreckenAusserorts * (anzahlStreckenAusserorts - 1);      // Gewichtung mit (n-1)/n
        }

        if (distanzKombiniert == 0) {
            verbrauchswertKombiniert = getVerbrauchKombiniertAnfangswert();
        } else {
            double ungewichtet = verbrauchterTreibstoffKombiniert / distanzKombiniert * 100;
            verbrauchswertKombiniert
                    = getVerbrauchKombiniertAnfangswert() / anzahlStreckenKombiniert                // Gewichtung mit 1/n
                    + ungewichtet / anzahlStreckenKombiniert * (anzahlStreckenKombiniert - 1);      // Gewichtung mit (n-1)/n
        }

        try {
            this.setVerbrauchInnerorts(verbrauchswertInnerorts);
            this.setVerbrauchAusserorts(verbrauchswertAusserorts);
            this.setVerbrauchKombiniert(verbrauchswertKombiniert);
        } catch (FahrzeugWertException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode zum Hinzufuegen einer gefahrenen Strecke in die "Strecken"-ArrayList am Index 0 (Anfang der Liste)
     *
     * @param pKmStand     double, Neuer Kilometerstand des Autos
     * @param pStreckentyp Enum Streckentyp, Streckentyp, welcher eingeben wird (Innerorts, Ausserorts, kombiniert)
     * @param pTankstand   double, Tankstand nach dem Fahren der Strecke in Prozent
     */
    public void streckeHinzufuegen(double pKmStand, Strecke.Streckentyp pStreckentyp, double pTankstand) {
        //Distanz der Strecke, neuer Tankstand und neuer Kilometerstand:
        double distanz = pKmStand - this.getKmStand();
        double verbrauchteLiter = (this.getTankstand() - pTankstand) / 100 * this.getTankgroesse();
        double neuerTankstandInLitern = pTankstand / 100 * this.getTankgroesse();
        try {
            this.setKmStand(pKmStand);
        } catch (FahrzeugWertException e) {
            e.printStackTrace();
        }
        try {
            this.setTankstand(pTankstand);
        } catch (FahrzeugWertException e) {
            e.printStackTrace();
        }

        //CO2-Ausstoss der Strecke berechnen:
        double co2AusstossDerStrecke = distanz * this.getCo2Ausstoss();
        strecken.add(0, new Strecke(distanz, pStreckentyp, neuerTankstandInLitern, co2AusstossDerStrecke, verbrauchteLiter));
        verbrauchAktualisieren();
    }

    /**
     * Methode zum Hinzufuegen eines Tankvorgangs in die "tankvorgaenge"-ArrayList am Index 0 (Anfang der Liste)
     *
     * @param pGetankteMenge double, getankte Menge in Litern
     * @param pPreis         double, gezahlter Preis in Euro
     * @param pImg           String, Pfad zum Foto
     * @throws FahrzeugWertException via setTankstand, wenn der neue Tankstand groesser als die Tankgroesse ist
     */
    public void tankvorgangHinzufuegen(double pGetankteMenge, double pPreis, String pImg) throws
            FahrzeugWertException {
        tankvorgaenge.add(0, new Tankvorgang(pGetankteMenge, pPreis, pImg));
        this.setTankstand(this.getTankstand() + (pGetankteMenge * 100 / this.getTankgroesse()));
    }

    /**
     * Methode zum Abfragen der gefahrenen Strecken in der letzten Woche (Statistik)
     *
     * @param verschiebung int, "-1" wuerde die Statistik eine Woche in die Vergangenheit bringen
     * @return LinkedHashMap mit den einzelnen Tagen als Key und der zugehoerigen Distanz als Value
     */
    public LinkedHashMap<String, Double> getWocheStreckenStatistik(int verschiebung) {
        LinkedHashMap<String, Double> rueckgabe = new LinkedHashMap<>();
        long delta = Math.multiplyExact((long) verschiebung, 604800000);
        Date heute = new Date();
        heute.setTime(heute.getTime() + delta);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        int i = 0;
        int j = 0;
        double distanz;

        while (j < 7 && heute.after(this.getZeitstempel())) {
            distanz = 0;
            rueckgabe.put(formatter.format(heute), distanz);

            while (i < strecken.size()) {
                if (formatter.format(strecken.get(i).getZeitstempel()).equals(formatter.format(heute))) {
                    distanz += strecken.get(i).getDistanz();
                    rueckgabe.replace(formatter.format(heute), distanz);
                    i++;
                } else if (strecken.get(i).getZeitstempel().after(heute)) {
                    i++;
                } else {
                    break;
                }
            }
            heute.setTime(heute.getTime() - 86400000);
            j++;
        }
        return rueckgabe;
    }

    /**
     * Methode zum Abfragen des verfahrenen Treibstoffs in der letzten Woche (Statistik)
     *
     * @param verschiebung int, "-1" wuerde die Statistik eine Woche in die Vergangenheit bringen
     * @return LinkedHashMap mit den einzelnen Tagen als Key und dem zugehoerigen Verbrauchswert als Value
     */
    public LinkedHashMap<String, Double> getWocheTreibstoffStatistik(int verschiebung) {
        LinkedHashMap<String, Double> rueckgabe = new LinkedHashMap<>();
        long delta = Math.multiplyExact((long) verschiebung, 604800000);
        Date heute = new Date();
        heute.setTime(heute.getTime() + delta);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        int i = 0;
        int j = 0;
        double verbrauch;

        while (j < 7 && heute.after(this.getZeitstempel())) {
            verbrauch = 0;
            rueckgabe.put(formatter.format(heute), verbrauch);

            while (i < strecken.size()) {
                if (formatter.format(strecken.get(i).getZeitstempel()).equals(formatter.format(heute))) {
                    verbrauch += strecken.get(i).getVerbrauchterTreibstoff();
                    rueckgabe.replace(formatter.format(heute), verbrauch);
                    i++;
                } else if (strecken.get(i).getZeitstempel().after(heute)) {
                    i++;
                } else {
                    break;
                }
            }
            heute.setTime(heute.getTime() - 86400000);
            j++;
        }
        return rueckgabe;
    }

    /**
     * Methode zum Abfragen der Tankkosten in der letzten Woche (Statistik)
     *
     * @param verschiebung int, "-1" wuerde die Statistik eine Woche in die Vergangenheit bringen
     * @return LinkedHashMap mit den einzelnen Tagen als Key und den zugehoerigen Tankkosten als Value
     */
    public LinkedHashMap<String, Double> getWocheTankkostenStatistik(int verschiebung) {
        LinkedHashMap<String, Double> rueckgabe = new LinkedHashMap<>();
        long delta = Math.multiplyExact((long) verschiebung, 604800000);
        Date heute = new Date();
        heute.setTime(heute.getTime() + delta);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        int i = 0;
        int j = 0;
        double kosten;

        while (j < 7 && heute.after(this.getZeitstempel())) {
            kosten = 0;
            rueckgabe.put(formatter.format(heute), kosten);

            while (i < tankvorgaenge.size()) {
                if (formatter.format(tankvorgaenge.get(i).getZeitstempel()).equals(formatter.format(heute))) {
                    kosten += tankvorgaenge.get(i).getPreis();
                    rueckgabe.replace(formatter.format(heute), kosten);
                    i++;
                } else if (tankvorgaenge.get(i).getZeitstempel().after(heute)) {
                    i++;
                } else {
                    break;
                }
            }
            heute.setTime(heute.getTime() - 86400000);
            j++;
        }
        return rueckgabe;
    }

    /**
     * Methode zum Abfragen des CO2-Ausstosses der letzten Woche (Statistik)
     *
     * @param verschiebung int, "-1" wuerde die Statistik eine Woche in die Vergangenheit bringen
     * @return LinkedHashMap mit den einzelnen Tagen als Key und dem zugehoerigen CO2-Ausstoss als Value
     */
    public LinkedHashMap<String, Double> getWocheCO2Statistik(int verschiebung) {
        LinkedHashMap<String, Double> rueckgabe = new LinkedHashMap<>();
        long delta = Math.multiplyExact((long) verschiebung, 604800000);
        Date heute = new Date();
        heute.setTime(heute.getTime() + delta);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        int i = 0;
        int j = 0;
        double ausstoss;

        while (j < 7 && heute.after(this.getZeitstempel())) {
            ausstoss = 0;
            rueckgabe.put(formatter.format(heute), ausstoss);

            while (i < strecken.size()) {
                if (formatter.format(strecken.get(i).getZeitstempel()).equals(formatter.format(heute))) {
                    ausstoss += strecken.get(i).getCo2Ausstoss() / 1000;
                    rueckgabe.replace(formatter.format(heute), ausstoss);
                    i++;
                } else if (strecken.get(i).getZeitstempel().after(heute)) {
                    i++;
                } else {
                    break;
                }
            }
            heute.setTime(heute.getTime() - 86400000);
            j++;
        }
        return rueckgabe;
    }

    /**
     * Methode zum Abfragen des verfahrenen Treibstoffs (Statistik)
     *
     * @param verschiebung int, Verschiebung des ausgewählten Zeitfensters um den Zeitrahmen (verschiebung=-1 ==> Ein Monat vorher)
     * @return LinkedHashMap mit dem ersten Tag der Woche als Key und dem zugehoerigen Verbrauchswert als Value
     */
    public LinkedHashMap<String, Double> getMonatTreibstoffStatistik(int verschiebung) {
        LinkedHashMap<String, Double> rueckgabe = new LinkedHashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Calendar heute = Calendar.getInstance();
        Calendar vergleich = Calendar.getInstance();
        Calendar fzZeitstempel = Calendar.getInstance();
        fzZeitstempel.setTime(this.getZeitstempel());
        // Uhrzeit auf 0 setzen, damit spaeter nur das Datum verglichen wird
        fzZeitstempel.set(Calendar.HOUR_OF_DAY, 0);
        fzZeitstempel.set(Calendar.MINUTE, 0);
        fzZeitstempel.set(Calendar.SECOND, 0);
        fzZeitstempel.set(Calendar.MILLISECOND, 0);

        // Verschiebung des Enddatums
        if (verschiebung != 0) {
            heute.add(Calendar.MONTH, verschiebung);
            heute.add(Calendar.WEEK_OF_MONTH, heute.getActualMaximum(Calendar.WEEK_OF_MONTH) - heute.get(Calendar.WEEK_OF_MONTH));
        }

        heute.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        if (heute.before(fzZeitstempel)) {
            heute.setTime(fzZeitstempel.getTime());
        }

        int ersteWoche;
        if (heute.get(Calendar.MONTH) == Calendar.JANUARY) {
            ersteWoche = 0;
        } else {
            Calendar ersterTag = Calendar.getInstance();
            ersterTag.setTime(heute.getTime());
            ersterTag.set(Calendar.DAY_OF_MONTH, 1);
            ersteWoche = ersterTag.get(Calendar.WEEK_OF_YEAR);
        }

        int letzteWoche = heute.get(Calendar.WEEK_OF_YEAR);
        int anzahlWochen = letzteWoche - ersteWoche + 1;

        int i = 0; // Strecken-Index
        int j = 0; // Wochen-Index

        while (j < anzahlWochen && (heute.after(fzZeitstempel) || heute.equals(fzZeitstempel))) {
            double summeVerbrauch = 0;
            String datum = String.valueOf(formatter.format(heute.getTime()));
            rueckgabe.put(datum, summeVerbrauch);

            while (i < strecken.size()) {
                vergleich.setTime(strecken.get(i).getZeitstempel());
                if (vergleich.get(Calendar.WEEK_OF_MONTH) == heute.get(Calendar.WEEK_OF_MONTH) && vergleich.get(Calendar.MONTH) == heute.get(Calendar.MONTH) && vergleich.get(Calendar.YEAR) == heute.get(Calendar.YEAR)) {
                    summeVerbrauch += strecken.get(i).getVerbrauchterTreibstoff();
                    rueckgabe.replace(datum, summeVerbrauch);
                    i++;
                } else if (vergleich.after(heute)) {
                    i++;
                } else {
                    break;
                }
            }

            heute.add(Calendar.WEEK_OF_MONTH, -1);
            if (heute.before(fzZeitstempel) && heute.get(Calendar.WEEK_OF_MONTH) == fzZeitstempel.get(Calendar.WEEK_OF_MONTH)) {
                heute.setTime(fzZeitstempel.getTime());
            }
            j++;
        }
        return rueckgabe;
    }

    /**
     * Methode zum Abfragen der zurueckgelegten Strecken (Statistik)
     *
     * @param verschiebung int, Verschiebung des ausgewählten Zeitfensters um den Zeitrahmen (verschiebung=-1 ==> Ein Monat vorher)
     * @return LinkedHashMap mit dem ersten Tag der Woche als Key und der zugehoerigen Distanz als Value
     */
    public LinkedHashMap<String, Double> getMonatStreckenStatistik(int verschiebung) {
        LinkedHashMap<String, Double> rueckgabe = new LinkedHashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Calendar heute = Calendar.getInstance();
        Calendar vergleich = Calendar.getInstance();
        Calendar fzZeitstempel = Calendar.getInstance();
        fzZeitstempel.setTime(this.getZeitstempel());
        // Uhrzeit auf 0 setzen, damit spaeter nur das Datum verglichen wird
        fzZeitstempel.set(Calendar.HOUR_OF_DAY, 0);
        fzZeitstempel.set(Calendar.MINUTE, 0);
        fzZeitstempel.set(Calendar.SECOND, 0);
        fzZeitstempel.set(Calendar.MILLISECOND, 0);

        // Verschiebung des Enddatums
        if (verschiebung != 0) {
            heute.add(Calendar.MONTH, verschiebung);
            heute.add(Calendar.WEEK_OF_MONTH, heute.getActualMaximum(Calendar.WEEK_OF_MONTH) - heute.get(Calendar.WEEK_OF_MONTH));
        }

        heute.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        if (heute.before(fzZeitstempel)) {
            heute.setTime(fzZeitstempel.getTime());
        }

        int ersteWoche;
        if (heute.get(Calendar.MONTH) == Calendar.JANUARY) {
            ersteWoche = 0;
        } else {
            Calendar ersterTag = Calendar.getInstance();
            ersterTag.setTime(heute.getTime());
            ersterTag.set(Calendar.DAY_OF_MONTH, 1);
            ersteWoche = ersterTag.get(Calendar.WEEK_OF_YEAR);
        }

        int letzteWoche = heute.get(Calendar.WEEK_OF_YEAR);
        int anzahlWochen = letzteWoche - ersteWoche + 1;

        int i = 0; // Strecken-Index
        int j = 0; // Wochen-Index

        while (j < anzahlWochen && (heute.after(fzZeitstempel) || heute.equals(fzZeitstempel))) {
            double summeDistanz = 0;
            String datum = String.valueOf(formatter.format(heute.getTime()));
            rueckgabe.put(datum, summeDistanz);

            while (i < strecken.size()) {
                vergleich.setTime(strecken.get(i).getZeitstempel());
                if (vergleich.get(Calendar.WEEK_OF_MONTH) == heute.get(Calendar.WEEK_OF_MONTH) && vergleich.get(Calendar.MONTH) == heute.get(Calendar.MONTH) && vergleich.get(Calendar.YEAR) == heute.get(Calendar.YEAR)) {
                    summeDistanz += strecken.get(i).getDistanz();
                    rueckgabe.replace(datum, summeDistanz);
                    i++;
                } else if (vergleich.after(heute)) {
                    i++;
                } else {
                    break;
                }
            }

            heute.add(Calendar.WEEK_OF_MONTH, -1);
            if (heute.before(fzZeitstempel) && heute.get(Calendar.WEEK_OF_MONTH) == fzZeitstempel.get(Calendar.WEEK_OF_MONTH)) {
                heute.setTime(fzZeitstempel.getTime());
            }
            j++;
        }
        return rueckgabe;
    }

    /**
     * Methode zum Abfragen der Tankkosten (Statistik)
     *
     * @param verschiebung int, Verschiebung des ausgewählten Zeitfensters um den Zeitrahmen (verschiebung=-1 ==> Ein Monat vorher)
     * @return LinkedHashMap mit dem ersten Tag der Woche als Key und den zugehoerigen Tankkosten als Value
     */
    public LinkedHashMap<String, Double> getMonatTankkostenStatistik(int verschiebung) {
        LinkedHashMap<String, Double> rueckgabe = new LinkedHashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Calendar heute = Calendar.getInstance();
        Calendar vergleich = Calendar.getInstance();
        Calendar fzZeitstempel = Calendar.getInstance();
        fzZeitstempel.setTime(this.getZeitstempel());
        // Uhrzeit auf 0 setzen, damit spaeter nur das Datum verglichen wird
        fzZeitstempel.set(Calendar.HOUR_OF_DAY, 0);
        fzZeitstempel.set(Calendar.MINUTE, 0);
        fzZeitstempel.set(Calendar.SECOND, 0);
        fzZeitstempel.set(Calendar.MILLISECOND, 0);

        // Verschiebung des Enddatums
        if (verschiebung != 0) {
            heute.add(Calendar.MONTH, verschiebung);
            heute.add(Calendar.WEEK_OF_MONTH, heute.getActualMaximum(Calendar.WEEK_OF_MONTH) - heute.get(Calendar.WEEK_OF_MONTH));
        }

        heute.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        if (heute.before(fzZeitstempel)) {
            heute.setTime(fzZeitstempel.getTime());
        }

        int ersteWoche;
        if (heute.get(Calendar.MONTH) == Calendar.JANUARY) {
            ersteWoche = 0;
        } else {
            Calendar ersterTag = Calendar.getInstance();
            ersterTag.setTime(heute.getTime());
            ersterTag.set(Calendar.DAY_OF_MONTH, 1);
            ersteWoche = ersterTag.get(Calendar.WEEK_OF_YEAR);
        }

        int letzteWoche = heute.get(Calendar.WEEK_OF_YEAR);
        int anzahlWochen = letzteWoche - ersteWoche + 1;

        int i = 0; // Strecken-Index
        int j = 0; // Wochen-Index

        while (j < anzahlWochen && (heute.after(fzZeitstempel) || heute.equals(fzZeitstempel))) {
            double summeKosten = 0;
            String datum = String.valueOf(formatter.format(heute.getTime()));
            rueckgabe.put(datum, summeKosten);

            while (i < tankvorgaenge.size()) {
                vergleich.setTime(tankvorgaenge.get(i).getZeitstempel());
                if (vergleich.get(Calendar.WEEK_OF_MONTH) == heute.get(Calendar.WEEK_OF_MONTH) && vergleich.get(Calendar.MONTH) == heute.get(Calendar.MONTH) && vergleich.get(Calendar.YEAR) == heute.get(Calendar.YEAR)) {
                    summeKosten += tankvorgaenge.get(i).getPreis();
                    rueckgabe.replace(datum, summeKosten);
                    i++;
                } else if (vergleich.after(heute)) {
                    i++;
                } else {
                    break;
                }
            }

            heute.add(Calendar.WEEK_OF_MONTH, -1);
            if (heute.before(fzZeitstempel) && heute.get(Calendar.WEEK_OF_MONTH) == fzZeitstempel.get(Calendar.WEEK_OF_MONTH)) {
                heute.setTime(fzZeitstempel.getTime());
            }
            j++;
        }
        return rueckgabe;
    }

    /**
     * Methode zum Abfragen des CO2-Ausstosses (Statistik)
     *
     * @param verschiebung int, Verschiebung des ausgewählten Zeitfensters um den Zeitrahmen (verschiebung=-1 ==> Ein Monat vorher)
     * @return LinkedHashMap mit dem ersten Tag der Woche als Key und dem zugehoerigen CO2-Ausstoss als Value
     */
    public LinkedHashMap<String, Double> getMonatCO2Statistik(int verschiebung) {
        LinkedHashMap<String, Double> rueckgabe = new LinkedHashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Calendar heute = Calendar.getInstance();
        Calendar vergleich = Calendar.getInstance();
        Calendar fzZeitstempel = Calendar.getInstance();
        fzZeitstempel.setTime(this.getZeitstempel());
        // Uhrzeit auf 0 setzen, damit spaeter nur das Datum verglichen wird
        fzZeitstempel.set(Calendar.HOUR_OF_DAY, 0);
        fzZeitstempel.set(Calendar.MINUTE, 0);
        fzZeitstempel.set(Calendar.SECOND, 0);
        fzZeitstempel.set(Calendar.MILLISECOND, 0);

        // Verschiebung des Enddatums
        if (verschiebung != 0) {
            heute.add(Calendar.MONTH, verschiebung);
            heute.add(Calendar.WEEK_OF_MONTH, heute.getActualMaximum(Calendar.WEEK_OF_MONTH) - heute.get(Calendar.WEEK_OF_MONTH));
        }

        heute.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        if (heute.before(fzZeitstempel)) {
            heute.setTime(fzZeitstempel.getTime());
        }

        int ersteWoche;
        if (heute.get(Calendar.MONTH) == Calendar.JANUARY) {
            ersteWoche = 0;
        } else {
            Calendar ersterTag = Calendar.getInstance();
            ersterTag.setTime(heute.getTime());
            ersterTag.set(Calendar.DAY_OF_MONTH, 1);
            ersteWoche = ersterTag.get(Calendar.WEEK_OF_YEAR);
        }

        int letzteWoche = heute.get(Calendar.WEEK_OF_YEAR);
        int anzahlWochen = letzteWoche - ersteWoche + 1;

        int i = 0; // Strecken-Index
        int j = 0; // Wochen-Index

        while (j < anzahlWochen && (heute.after(fzZeitstempel) || heute.equals(fzZeitstempel))) {
            double summeAusstoss = 0;
            String datum = String.valueOf(formatter.format(heute.getTime()));
            rueckgabe.put(datum, summeAusstoss);

            while (i < strecken.size()) {
                vergleich.setTime(strecken.get(i).getZeitstempel());
                if (vergleich.get(Calendar.WEEK_OF_MONTH) == heute.get(Calendar.WEEK_OF_MONTH) && vergleich.get(Calendar.MONTH) == heute.get(Calendar.MONTH) && vergleich.get(Calendar.YEAR) == heute.get(Calendar.YEAR)) {
                    summeAusstoss += strecken.get(i).getCo2Ausstoss() / 1000;
                    rueckgabe.replace(datum, summeAusstoss);
                    i++;
                } else if (vergleich.after(heute)) {
                    i++;
                } else {
                    break;
                }
            }

            heute.add(Calendar.WEEK_OF_MONTH, -1);
            if (heute.before(fzZeitstempel) && heute.get(Calendar.WEEK_OF_MONTH) == fzZeitstempel.get(Calendar.WEEK_OF_MONTH)) {
                heute.setTime(fzZeitstempel.getTime());
            }
            j++;
        }
        return rueckgabe;
    }

    /**
     * Methode zum Abfragen des CO2-Ausstosses (Statistik)
     *
     * @param verschiebung int, Verschiebung des ausgewählten Zeitfensters um den Zeitrahmen (verschiebung=-1 ==> Ein Jahr vorher)
     * @return LinkedHashMap mit dem ersten Tag des Monats als Key und dem zugehoerigen CO2-Ausstoss als Value
     */
    public LinkedHashMap<String, Double> getJahrCO2Statistik(int verschiebung) {
        LinkedHashMap<String, Double> rueckgabe = new LinkedHashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Calendar heute = Calendar.getInstance();
        Calendar vergleich = Calendar.getInstance();
        Calendar fzZeitstempel = Calendar.getInstance();
        fzZeitstempel.setTime(this.getZeitstempel());
        // Uhrzeit auf 0 setzen, damit spaeter nur das Datum verglichen wird
        fzZeitstempel.set(Calendar.HOUR_OF_DAY, 0);
        fzZeitstempel.set(Calendar.MINUTE, 0);
        fzZeitstempel.set(Calendar.SECOND, 0);
        fzZeitstempel.set(Calendar.MILLISECOND, 0);

        // Verschiebung des Enddatums
        if (verschiebung != 0) {
            heute.add(Calendar.YEAR, verschiebung);
            heute.set(Calendar.DAY_OF_MONTH, heute.getActualMaximum(Calendar.DAY_OF_MONTH));

            if (heute.before(fzZeitstempel)) {
                heute.setTime(fzZeitstempel.getTime());
            }
        }

        int i = 0; // Strecken-Index
        int j = 0; // Monats-Index

        while (j < 12 && (heute.after(fzZeitstempel) || heute.equals(fzZeitstempel))) {
            double summeAusstoss = 0;
            String datum = String.valueOf(formatter.format(heute.getTime()));
            rueckgabe.put(datum, summeAusstoss);

            while (i < strecken.size()) {
                vergleich.setTime(strecken.get(i).getZeitstempel());
                if (vergleich.get(Calendar.MONTH) == heute.get(Calendar.MONTH) && vergleich.get(Calendar.YEAR) == heute.get(Calendar.YEAR)) {
                    summeAusstoss += strecken.get(i).getCo2Ausstoss() / 1000;
                    rueckgabe.replace(datum, summeAusstoss);
                    i++;
                } else if (vergleich.after(heute)) {
                    i++;
                } else {
                    break;
                }
            }
            heute.add(Calendar.MONTH, -1);
            if (heute.before(fzZeitstempel) && heute.get(Calendar.MONTH) == fzZeitstempel.get(Calendar.MONTH)) {
                heute.setTime(fzZeitstempel.getTime());
            }
            j++;
        }

        return rueckgabe;
    }

    /**
     * Methode zum Abfragen der Tankkosten (Statistik)
     *
     * @param verschiebung int, Verschiebung des ausgewählten Zeitfensters um den Zeitrahmen (verschiebung=-1 ==> Ein Jahr vorher)
     * @return LinkedHashMap mit dem ersten Tag des Monats als Key und den zugehoerigen Tankkosten als Value
     */
    public LinkedHashMap<String, Double> getJahrTankkostenStatistik(int verschiebung) {
        LinkedHashMap<String, Double> rueckgabe = new LinkedHashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Calendar heute = Calendar.getInstance();
        Calendar vergleich = Calendar.getInstance();
        Calendar fzZeitstempel = Calendar.getInstance();
        fzZeitstempel.setTime(this.getZeitstempel());
        // Uhrzeit auf 0 setzen, damit spaeter nur das Datum verglichen wird
        fzZeitstempel.set(Calendar.HOUR_OF_DAY, 0);
        fzZeitstempel.set(Calendar.MINUTE, 0);
        fzZeitstempel.set(Calendar.SECOND, 0);
        fzZeitstempel.set(Calendar.MILLISECOND, 0);

        // Verschiebung des Enddatums
        if (verschiebung != 0) {
            heute.add(Calendar.YEAR, verschiebung);
            heute.set(Calendar.DAY_OF_MONTH, heute.getActualMaximum(Calendar.DAY_OF_MONTH));

            if (heute.before(fzZeitstempel)) {
                heute.setTime(fzZeitstempel.getTime());
            }
        }

        int i = 0; // Strecken-Index
        int j = 0; // Monats-Index

        while (j < 12 && (heute.after(fzZeitstempel) || heute.equals(fzZeitstempel))) {
            double summeTankkosten = 0;
            String datum = String.valueOf(formatter.format(heute.getTime()));
            rueckgabe.put(datum, summeTankkosten);

            while (i < tankvorgaenge.size()) {
                vergleich.setTime(tankvorgaenge.get(i).getZeitstempel());
                if (vergleich.get(Calendar.MONTH) == heute.get(Calendar.MONTH) && vergleich.get(Calendar.YEAR) == heute.get(Calendar.YEAR)) {
                    summeTankkosten += tankvorgaenge.get(i).getPreis();
                    rueckgabe.replace(datum, summeTankkosten);
                    i++;
                } else if (vergleich.after(heute)) {
                    i++;
                } else {
                    break;
                }
            }
            heute.add(Calendar.MONTH, -1);
            if (heute.before(fzZeitstempel) && heute.get(Calendar.MONTH) == fzZeitstempel.get(Calendar.MONTH)) {
                heute.setTime(fzZeitstempel.getTime());
            }
            j++;
        }

        return rueckgabe;
    }

    /**
     * Methode zum Abfragen des Treibstoffsverbrauchs (Statistik)
     *
     * @param verschiebung int, Verschiebung des ausgewählten Zeitfensters um den Zeitrahmen (verschiebung=-1 ==> Ein Jahr vorher)
     * @return LinkedHashMap mit dem ersten Tag des Monats als Key und dem zugehoerigen Verbrauchswert als Value
     */
    public LinkedHashMap<String, Double> getJahrTreibstoffStatistik(int verschiebung) {
        LinkedHashMap<String, Double> rueckgabe = new LinkedHashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Calendar heute = Calendar.getInstance();
        Calendar vergleich = Calendar.getInstance();
        Calendar fzZeitstempel = Calendar.getInstance();
        fzZeitstempel.setTime(this.getZeitstempel());
        // Uhrzeit auf 0 setzen, damit spaeter nur das Datum verglichen wird
        fzZeitstempel.set(Calendar.HOUR_OF_DAY, 0);
        fzZeitstempel.set(Calendar.MINUTE, 0);
        fzZeitstempel.set(Calendar.SECOND, 0);
        fzZeitstempel.set(Calendar.MILLISECOND, 0);

        // Verschiebung des Enddatums
        if (verschiebung != 0) {
            heute.add(Calendar.YEAR, verschiebung);
            heute.set(Calendar.DAY_OF_MONTH, heute.getActualMaximum(Calendar.DAY_OF_MONTH));

            if (heute.before(fzZeitstempel)) {
                heute.setTime(fzZeitstempel.getTime());
            }
        }

        int i = 0; // Strecken-Index
        int j = 0; // Monats-Index

        while (j < 12 && (heute.after(fzZeitstempel) || heute.equals(fzZeitstempel))) {
            double summeVerbrauch = 0;
            String datum = String.valueOf(formatter.format(heute.getTime()));
            rueckgabe.put(datum, summeVerbrauch);

            while (i < strecken.size()) {
                vergleich.setTime(strecken.get(i).getZeitstempel());
                if (vergleich.get(Calendar.MONTH) == heute.get(Calendar.MONTH) && vergleich.get(Calendar.YEAR) == heute.get(Calendar.YEAR)) {
                    summeVerbrauch += strecken.get(i).getVerbrauchterTreibstoff();
                    rueckgabe.replace(datum, summeVerbrauch);
                    i++;
                } else if (vergleich.after(heute)) {
                    i++;
                } else {
                    break;
                }
            }
            heute.add(Calendar.MONTH, -1);
            if (heute.before(fzZeitstempel) && heute.get(Calendar.MONTH) == fzZeitstempel.get(Calendar.MONTH)) {
                heute.setTime(fzZeitstempel.getTime());
            }
            j++;
        }

        return rueckgabe;
    }

    /**
     * Methode zum Abfragen der Strecken innerhalb eines Jahres (Statistik)
     *
     * @param verschiebung int, Verschiebung des ausgewählten Zeitfensters um den Zeitrahmen (verschiebung=-1 ==> Ein Jahr vorher)
     * @return LinkedHashMap mit dem ersten Tag des Monats als Key und der zugehoerigen Distanz als Value
     */
    public LinkedHashMap<String, Double> getJahrStreckenStatistik(int verschiebung) {
        LinkedHashMap<String, Double> rueckgabe = new LinkedHashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Calendar heute = Calendar.getInstance();
        Calendar vergleich = Calendar.getInstance();
        Calendar fzZeitstempel = Calendar.getInstance();
        fzZeitstempel.setTime(this.getZeitstempel());
        // Uhrzeit auf 0 setzen, damit spaeter nur das Datum verglichen wird
        fzZeitstempel.set(Calendar.HOUR_OF_DAY, 0);
        fzZeitstempel.set(Calendar.MINUTE, 0);
        fzZeitstempel.set(Calendar.SECOND, 0);
        fzZeitstempel.set(Calendar.MILLISECOND, 0);

        // Verschiebung des Enddatums
        if (verschiebung != 0) {
            heute.add(Calendar.YEAR, verschiebung);
            heute.set(Calendar.DAY_OF_MONTH, heute.getActualMaximum(Calendar.DAY_OF_MONTH));

            if (heute.before(fzZeitstempel)) {
                heute.setTime(fzZeitstempel.getTime());
            }
        }

        int i = 0; // Strecken-Index
        int j = 0; // Monats-Index

        while (j < 12 && (heute.after(fzZeitstempel) || heute.equals(fzZeitstempel))) {
            double summeDistanz = 0;
            String datum = String.valueOf(formatter.format(heute.getTime()));
            rueckgabe.put(datum, summeDistanz);

            while (i < strecken.size()) {
                vergleich.setTime(strecken.get(i).getZeitstempel());
                if (vergleich.get(Calendar.MONTH) == heute.get(Calendar.MONTH) && vergleich.get(Calendar.YEAR) == heute.get(Calendar.YEAR)) {
                    summeDistanz += strecken.get(i).getDistanz();
                    rueckgabe.replace(datum, summeDistanz);
                    i++;
                } else if (vergleich.after(heute)) {
                    i++;
                } else {
                    break;
                }
            }
            heute.add(Calendar.MONTH, -1);
            if (heute.before(fzZeitstempel) && heute.get(Calendar.MONTH) == fzZeitstempel.get(Calendar.MONTH)) {
                heute.setTime(fzZeitstempel.getTime());
            }
            j++;
        }

        return rueckgabe;
    }

    /**
     * Methode zum Anfragen der Streckenprognose für das aktuelle Fahrzeug
     *
     * @return in der Reihenfolge: Kraftstoffverbrauch, Kraftstoffkosten, Anzahl der nötigen Tankvorgänge, CO2-Ausstoß
     */
    public HashMap<String, Double> getStreckenprognose(double streckenlaenge,
                                                       double prozentInnerorts, double prozentAusserorts, double prozentKombiniert) {

        // Kraftstoffverbrauch
        double kraftstoffverbrauch = (
                prozentInnerorts / 100.0 * getVerbrauchInnerorts() +
                        prozentAusserorts / 100.0 * getVerbrauchAusserorts() +
                        prozentKombiniert / 100 * getVerbrauchKombiniert()
        ) / 100 * streckenlaenge;

        // Kraftstoffkosten
        double kraftstoffkosten;
        if (tankvorgaenge.isEmpty()) {
            kraftstoffkosten = -1;  // soll "—" ausgeben
        } else {
            kraftstoffkosten = kraftstoffverbrauch * getDurchschnittlicheTankkostenProLiter();
        }

        // Anzahl nötiger Tankvorgänge
        double anzahlNoetigerTankvorgaenge;
        double tankstandLiter = tankstand / 100 * tankgroesse;
        if (tankstandLiter > kraftstoffverbrauch) {
            anzahlNoetigerTankvorgaenge = 0;
        } else {
            anzahlNoetigerTankvorgaenge = Math.ceil((kraftstoffverbrauch - tankstandLiter) / getTankgroesse());
        }

        // CO2-Ausstoß
        double co2Ausstoss = streckenlaenge * getCo2Ausstoss() / 1000;  // in kg

        // Rückgabe
        HashMap<String, Double> rueckgabe = new HashMap<>();
        rueckgabe.put("kraftstoffverbrauch", kraftstoffverbrauch);
        rueckgabe.put("kraftstoffkosten", kraftstoffkosten);
        rueckgabe.put("anzahlNoetigerTankvorgaenge", anzahlNoetigerTankvorgaenge);
        rueckgabe.put("co2Ausstoss", co2Ausstoss);
        return rueckgabe;
    }

    private double getDurchschnittlicheTankkostenProLiter() {

        double summeGetankteMenge = 0;
        double summeGezahlteBetraege = 0;

        for (Tankvorgang tankvorgang : tankvorgaenge) {
            summeGetankteMenge += tankvorgang.getGetankteMenge();
            summeGezahlteBetraege += tankvorgang.getPreis();
        }

        double durchschnittlicheTankkostenProLiter = summeGezahlteBetraege / summeGetankteMenge;

        if (Double.isInfinite(durchschnittlicheTankkostenProLiter)) {  // Division durch 0
            return -1;
        } else {
            return durchschnittlicheTankkostenProLiter;
        }
    }

    /**
     * für das Dashboard
     *
     * @return durchschnittlicher Verbrauch pro 100 km
     */
    public double getVerbrauchDurchschnittlich() {
        return (getVerbrauchInnerorts() + getVerbrauchAusserorts() + getVerbrauchKombiniert()) / 3;
    }

    /**
     * für das Dashboard
     *
     * @return geschätzte verbleibende Reichweite auf Basis der durchschnittlichen Verbrauchs
     */
    public double getReichweite() {

        double tankstandLiter = getTankstand() / 100 * getTankgroesse();
        double verbrauchDurchschnittlich = getVerbrauchDurchschnittlich();

        if (verbrauchDurchschnittlich == 0) {
            return -1;
        } else {
            return tankstandLiter / verbrauchDurchschnittlich * 100;
        }
    }

    /**
     * für das Dashboard
     *
     * @return bisher insgesamt ausgestoßene Emissionen in kg
     */
    public double getCo2AusstossGesamtKg() {
        return getCo2Ausstoss() * getKmStand() / 1000;
    }
}
