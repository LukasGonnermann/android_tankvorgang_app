package com.example.tankauswertung;

import com.example.tankauswertung.exceptions.FahrzeugWertException;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Test der einzelnen Statistik-Methoden
 * fuer Strecken, Treibstoff, CO2-Ausstoss und Tankkosten
 * in den unterschiedlichen Intervallen
 */
public class FahrzeugStatistikTest {
    Date heute = new Date();
    transient SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    /**
     * Test der Statistik-Methoden fuer Strecken
     * Lege ein Fahrzeug an und fuege Strecken hinzu, pruefe ob die Distanz in der Statistik korrekt erfasst wird
     */
    @Test
    public void testGetStreckenStatistik() {
        // Fahrzeug anlegen und Strecken hinzufuegen
        Fahrzeug f = new Fahrzeug("Testfahrzeug", false, 6, 7.5, 7, 27728, 80, 5, 45);
        f.streckeHinzufuegen(27748, Strecke.Streckentyp.KOMBINIERT, 75);
        f.streckeHinzufuegen(27768, Strecke.Streckentyp.AUSSERORTS, 68);
        f.streckeHinzufuegen(27773, Strecke.Streckentyp.INNERORTS, 52);

        double distanz = f.getKmStand() - 27728;

        // Woche
        // Verbrauch in Wochenstatistik pruefen
        LinkedHashMap ergebnisWoche = f.getWocheStreckenStatistik(0);

        assertEquals(ergebnisWoche.get(formatter.format(heute)), distanz);
        Set<String> keys = ergebnisWoche.keySet();
        for (String key : keys) {
            if (!key.equals(formatter.format(heute))) {
                assertEquals(ergebnisWoche.get(key), 0.0);
            }
        }

        // Monat
        // Verbrauch in Monatsstatistik pruefen
        LinkedHashMap ergebnisMonat = f.getMonatStreckenStatistik(0);


        // Jahr
        // Verbrauch in Jahresstatistik pruefen
        LinkedHashMap ergebnisJahr = f.getJahrStreckenStatistik(0);
    }

    /**
     * Test der Statistik-Methoden fuer verbrauchten Treibstoff
     * Lege ein Fahrzeug an und fuege Strecken hinzu, pruefe ob der verbrauchte Treibstoff in der Statistik korrekt erfasst wird
     */
    @Test
    public void testGetTreibstoffStatistik() {
        // Fahrzeug anlegen und Strecken hinzufuegen
        Fahrzeug f = new Fahrzeug("Testfahrzeug", false, 6, 7.5, 7, 27728, 80, 5, 45);
        f.streckeHinzufuegen(27748, Strecke.Streckentyp.KOMBINIERT, 75);
        f.streckeHinzufuegen(27768, Strecke.Streckentyp.AUSSERORTS, 68);
        f.streckeHinzufuegen(27773, Strecke.Streckentyp.INNERORTS, 52);

        // Verbrauch berechnen
        double verbrauch = 0;
        ArrayList<Strecke> strecken = f.getStrecken();
        for (Strecke item : strecken) {
            verbrauch += item.getVerbrauchterTreibstoff();
        }

        // Woche
        // Verbrauch in Wochenstatistik pruefen
        LinkedHashMap ergebnisWoche = f.getWocheTreibstoffStatistik(0);

        assertEquals(ergebnisWoche.get(formatter.format(heute)), verbrauch);
        Set<String> keys = ergebnisWoche.keySet();
        for (String key : keys) {
            if (!key.equals(formatter.format(heute))) {
                assertEquals(ergebnisWoche.get(key), 0.0);
            }
        }

        // Monat
        // Verbrauch in Monatsstatistik pruefen
        LinkedHashMap ergebnisMonat = f.getMonatTreibstoffStatistik(0);


        // Jahr
        // Verbrauch in Jahresstatistik pruefen
        LinkedHashMap ergebnisJahr = f.getJahrTreibstoffStatistik(0);
    }

    /**
     * Test der Statistik-Methoden fuer CO2-Ausstoss
     * Lege ein Fahrzeug an und fuege Strecken hinzu, pruefe ob der CO2-Ausstoss in der Statistik korrekt erfasst wird
     */
    @Test
    public void testGetCO2Statistik() {
        // Fahrzeug anlegen und Strecken hinzufuegen
        Fahrzeug f = new Fahrzeug("Testfahrzeug", false, 6, 7.5, 7, 27728, 80, 5, 45);
        f.streckeHinzufuegen(27748, Strecke.Streckentyp.KOMBINIERT, 75);
        f.streckeHinzufuegen(27768, Strecke.Streckentyp.AUSSERORTS, 68);
        f.streckeHinzufuegen(27773, Strecke.Streckentyp.INNERORTS, 52);

        // CO2-Ausstoss berechnen
        double ausstoss = 0;
        ArrayList<Strecke> strecken = f.getStrecken();
        for (Strecke item : strecken) {
            ausstoss += item.getCo2Ausstoss();
        }

        // Woche
        // Verbrauch in Wochenstatistik pruefen
        LinkedHashMap ergebnisWoche = f.getWocheCO2Statistik(0);

        assertEquals(ergebnisWoche.get(formatter.format(heute)), ausstoss);
        Set<String> keys = ergebnisWoche.keySet();
        for (String key : keys) {
            if (!key.equals(formatter.format(heute))) {
                assertEquals(ergebnisWoche.get(key), 0.0);
            }
        }

        // Monat
        // Verbrauch in Monatsstatistik pruefen
        LinkedHashMap ergebnisMonat = f.getMonatCO2Statistik(0);


        // Jahr
        // Verbrauch in Jahresstatistik pruefen
        LinkedHashMap ergebnisJahr = f.getJahrCO2Statistik(0);
    }

    /**
     * Test der Statistik-Methoden fuer Tankkosten
     * Lege ein Fahrzeug an und fuege Tankvorgaenge hinzu, pruefe ob die Tankkosten in der Statistik korrekt erfasst werden
     */
    @Test
    public void testGetTankkostenStatistik() {
        // Fahrzeug anlegen und Tankvorgaenge hinzufuegen
        Fahrzeug f = new Fahrzeug("Testfahrzeug", false, 6, 7.5, 7, 27728, 10, 5, 45);
        try {
            f.tankvorgangHinzufuegen(4, 8.45, "/DCIM/Camera/meinFoto1.jpg");
            f.tankvorgangHinzufuegen(22, 32.91, "/DCIM/Camera/meinFoto2.jpg");
            f.tankvorgangHinzufuegen(11, 24.45, "/DCIM/Camera/meinFoto3.jpg");
        } catch (FahrzeugWertException e) {
            e.printStackTrace();
        }

        // Tankkosten berechnen
        double kosten = 0;
        ArrayList<Tankvorgang> tankvorgaenge = f.getTankvorgaenge();
        for (Tankvorgang item : tankvorgaenge) {
            kosten += item.getPreis();
        }

        // Woche
        // Verbrauch in Wochenstatistik pruefen
        LinkedHashMap ergebnisWoche = f.getWocheTankkostenStatistik(0);

        assertEquals(ergebnisWoche.get(formatter.format(heute)), kosten);
        Set<String> keys = ergebnisWoche.keySet();
        for (String key : keys) {
            if (!key.equals(formatter.format(heute))) {
                assertEquals(ergebnisWoche.get(key), 0.0);
            }
        }

        // Monat
        // Verbrauch in Monatsstatistik pruefen
        LinkedHashMap ergebnisMonat = f.getMonatTankkostenStatistik(0);


        // Jahr
        // Verbrauch in Jahresstatistik pruefen
        LinkedHashMap ergebnisJahr = f.getJahrTankkostenStatistik(0);

    }
}
