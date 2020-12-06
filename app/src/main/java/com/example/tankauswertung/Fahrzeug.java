package com.example.tankauswertung;

import com.example.tankauswertung.exceptions.FahrzeugWertException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
     * Getter für die gesamte Liste an Strecken und Tankvorgängen bzw. Timeline-Elementen der
     * Wrapperklasse Ereignis
     *
     * @return ArrayList<Ereignis>, Alle Ereignisse, sortiert nach Datum
     */
    public ArrayList<Ereignis> getEreignisse() {

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
                String beschreibung = Double.toString(aktuelleStrecke.getDistanz())
                        + ", " + aktuelleStrecke.getStreckentyp().toString();
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
                String beschreibung = Double.toString(aktuellerTankvorgang.getGetankteMenge())
                        + ", " + Double.toString(aktuellerTankvorgang.getPreis());
                ereignisse.add(new Ereignis(Ereignis.EreignisTyp.TANKVORGANG, i, datum, beschreibung));

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
     * Aktualisiert den tatsächlichen Verbrauch des Autos nach dem eintragen eier Strecke
     *
     * @param verbrauchteLiter double, Auf der Strecke verbrauchte Liter
     * @param streckendistanz  double, Distanz der Strecke
     * @param pStreckentyp     enum Strecke.Streckentyp, Typ der Strecke
     */
    public void verbrauchAktualisieren(double verbrauchteLiter, double streckendistanz, Strecke.Streckentyp pStreckentyp) {
        //Berechnung:
        double verbrauchDerStrecke = verbrauchteLiter / streckendistanz * 100;
        switch (pStreckentyp) {
            case INNERORTS:
                try {
                    setVerbrauchInnerorts(((strecken.size() + 1) * getVerbrauchInnerorts() + verbrauchDerStrecke) / (strecken.size() + 2));
                } catch (FahrzeugWertException e) {
                    e.printStackTrace();
                }
                break;
            case AUSSERORTS:
                try {
                    setVerbrauchAusserorts(((strecken.size() + 1) * getVerbrauchAusserorts() + verbrauchDerStrecke) / (strecken.size() + 2));
                } catch (FahrzeugWertException e) {
                    e.printStackTrace();
                }
                break;
            case KOMBINIERT:
                try {
                    setVerbrauchKombiniert(((strecken.size() + 1) * getVerbrauchKombiniert() + verbrauchDerStrecke) / (strecken.size() + 2));
                } catch (FahrzeugWertException e) {
                    e.printStackTrace();
                }
                break;
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
        verbrauchAktualisieren(verbrauchteLiter, distanz, pStreckentyp);
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
    }


    /**
     * Methode zum Hinzufuegen eines Tankvorgangs in die "tankvorgaenge"-ArrayList am Index 0 (Anfang der Liste)
     *
     * @param pGetankteMenge double, getankte Menge in Litern
     * @param pPreis         double, gezahlter Preis in Euro
     * @param pImg           String, Pfad zum Foto
     * @throws FahrzeugWertException via setTankstand, wenn der neue Tankstand groesser als die Tankgroesse ist
     */
    public void tankvorgangHinzufuegen(double pGetankteMenge, double pPreis, String pImg) throws FahrzeugWertException {
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
        double distanz;

        for (int j = 0; j < 7; j++) {
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
        double verbrauch;

        for (int j = 0; j < 7; j++) {
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
        double kosten;

        for (int j = 0; j < 7; j++) {
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
        double ausstoss;

        for (int j = 0; j < 7; j++) {
            ausstoss = 0;
            rueckgabe.put(formatter.format(heute), ausstoss);

            while (i < strecken.size()) {
                if (formatter.format(strecken.get(i).getZeitstempel()).equals(formatter.format(heute))) {
                    ausstoss += strecken.get(i).getCo2Ausstoss();
                    rueckgabe.replace(formatter.format(heute), ausstoss);
                    i++;
                } else if (strecken.get(i).getZeitstempel().after(heute)) {
                    i++;
                } else {
                    break;
                }
            }
            heute.setTime(heute.getTime() - 86400000);
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
        double summe;
        for (int i = 0; i < 4; i++) {
            summe = 0;
            LinkedHashMap<String, Double> eineWoche = getWocheTreibstoffStatistik(-i + (verschiebung * 4));
            for (double d : eineWoche.values()) {
                summe += d;
            }
            String firstDate = (String) eineWoche.keySet().stream().toArray()[6];
            rueckgabe.put(firstDate, summe);
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
        double summe;
        for (int i = 0; i < 4; i++) {
            summe = 0;
            LinkedHashMap<String, Double> eineWoche = getWocheStreckenStatistik(-i + (verschiebung * 4));
            for (double d : eineWoche.values()) {
                summe += d;
            }
            String firstDate = (String) eineWoche.keySet().stream().toArray()[6];
            rueckgabe.put(firstDate, summe);
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
        double summe;
        for (int i = 0; i < 4; i++) {
            summe = 0;
            LinkedHashMap<String, Double> eineWoche = getWocheTankkostenStatistik(-i + (verschiebung * 4));
            for (double d : eineWoche.values()) {
                summe += d;
            }
            String firstDate = (String) eineWoche.keySet().stream().toArray()[6];
            rueckgabe.put(firstDate, summe);
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
        double summe;
        for (int i = 0; i < 4; i++) {
            summe = 0;
            LinkedHashMap<String, Double> eineWoche = getWocheCO2Statistik(-i + (verschiebung * 4));
            for (double d : eineWoche.values()) {
                summe += d;
            }
            String firstDate = (String) eineWoche.keySet().stream().toArray()[6];
            rueckgabe.put(firstDate, summe);
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
        double summe;
        for (int i = 0; i < 12; i++) {
            summe = 0;
            LinkedHashMap<String, Double> einMonat = getMonatCO2Statistik(-i + (verschiebung * 12));
            for (double d : einMonat.values()) {
                summe += d;
            }
            String firstDate = (String) einMonat.keySet().stream().toArray()[3];
            rueckgabe.put(firstDate, summe);
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
        double summe;
        for (int i = 0; i < 12; i++) {
            summe = 0;
            LinkedHashMap<String, Double> einMonat = getMonatTankkostenStatistik(-i + (verschiebung * 12));
            for (double d : einMonat.values()) {
                summe += d;
            }
            String firstDate = (String) einMonat.keySet().stream().toArray()[3];
            rueckgabe.put(firstDate, summe);
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
        double summe;
        for (int i = 0; i < 12; i++) {
            summe = 0;
            LinkedHashMap<String, Double> einMonat = getMonatTreibstoffStatistik(-i + (verschiebung * 12));
            for (double d : einMonat.values()) {
                summe += d;
            }
            String firstDate = (String) einMonat.keySet().stream().toArray()[3];
            rueckgabe.put(firstDate, summe);
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
        double summe;
        for (int i = 0; i < 12; i++) {
            summe = 0;
            LinkedHashMap<String, Double> einMonat = getMonatStreckenStatistik(-i + (verschiebung * 12));
            for (double d : einMonat.values()) {
                summe += d;
            }
            String firstDate = (String) einMonat.keySet().stream().toArray()[3];
            rueckgabe.put(firstDate, summe);
        }
        return rueckgabe;
    }
}
