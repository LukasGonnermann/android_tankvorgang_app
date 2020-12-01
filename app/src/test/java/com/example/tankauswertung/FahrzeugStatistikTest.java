package com.example.tankauswertung;

import com.example.tankauswertung.exceptions.FahrzeugWertException;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test der einzelnen Statistik-Methoden
 * fuer Strecken, Treibstoff, CO2-Ausstoss und Tankkosten
 * in den unterschiedlichen Intervallen
 */
public class FahrzeugStatistikTest {
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
        // Ergebnis pruefen: Strecke fuer heutigen Tag erfasst, Rest 0
        double[] ergebnisWoche = f.getWocheStreckenStatistik(0);
        assertEquals(ergebnisWoche[0], distanz, 0);
        for (int i = 1; i < ergebnisWoche.length; i++) {
            assertEquals(ergebnisWoche[i], 0, 0);
        }

        // Monat
        // Ergebnis pruefen: Strecke fuer aktuelle Woche erfasst, Rest 0
        double[] ergebnisMonat = f.getMonatStreckenStatistik(0);
        assertEquals(ergebnisMonat[0], distanz, 0);
        for (int i = 1; i < ergebnisMonat.length; i++) {
            assertEquals(ergebnisMonat[i], 0, 0);
        }

        // Jahr
        double[] ergebnisJahr = f.getJahrStreckenStatistik(0);
        assertEquals(ergebnisJahr[0], distanz, 0);
        for (int i = 1; i < ergebnisJahr.length; i++) {
            assertEquals(ergebnisJahr[i], 0, 0);
        }
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
        double[] ergebnisWoche = f.getWocheTreibstoffStatistik(0);
        assertEquals(ergebnisWoche[0], verbrauch, 0);
        for (int i = 1; i < ergebnisWoche.length; i++) {
            assertEquals(ergebnisWoche[i], 0, 0);
        }

        // Monat
        // Verbrauch in Monatsstatistik pruefen
        double[] ergebnisMonat = f.getMonatTreibstoffStatistik(0);
        assertEquals(ergebnisMonat[0], verbrauch, 0);
        for (int i = 1; i < ergebnisMonat.length; i++) {
            assertEquals(ergebnisMonat[i], 0, 0);
        }

        // Jahr
        double[] ergebnisJahr = f.getJahrTreibstoffStatistik(0);
        assertEquals(ergebnisJahr[0], verbrauch, 0);
        for (int i = 1; i < ergebnisJahr.length; i++) {
            assertEquals(ergebnisJahr[i], 0, 0);
        }
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
        // CO2 in Wochenstatistik pruefen
        double[] ergebnisWoche = f.getWocheCO2Statistik(0);
        assertEquals(ergebnisWoche[0], ausstoss, 0);
        for (int i = 1; i < ergebnisWoche.length; i++) {
            assertEquals(ergebnisWoche[i], 0, 0);
        }

        // Monat
        // CO2 in Monatsstatistik pruefen
        double[] ergebnisMonat = f.getMonatCO2Statistik(0);
        assertEquals(ergebnisMonat[0], ausstoss, 0);
        for (int i = 1; i < ergebnisMonat.length; i++) {
            assertEquals(ergebnisMonat[i], 0, 0);
        }

        // Jahr
        // CO2 in Jahresstatistik pruefen
        double[] ergebnisJahr = f.getJahrCO2Statistik(0);
        assertEquals(ergebnisJahr[0], ausstoss, 0);
        for (int i = 1; i < ergebnisJahr.length; i++) {
            assertEquals(ergebnisJahr[i], 0, 0);
        }
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
        // Tankkosten in Wochenstatistik pruefen
        double[] ergebnisWoche = f.getWocheTankkostenStatistik(0);
        assertEquals(ergebnisWoche[0], kosten, 0);
        for (int i = 1; i < ergebnisWoche.length; i++) {
            assertEquals(ergebnisWoche[i], 0, 0);
        }

        // Monat
        // Tankkosten in Monatsstatistik pruefen
        double[] ergebnisMonat = f.getMonatTankkostenStatistik(0);
        assertEquals(ergebnisMonat[0], kosten, 0);
        for (int i = 1; i < ergebnisMonat.length; i++) {
            assertEquals(ergebnisMonat[i], 0, 0);
        }

        // Jahr
        double[] ergebnisJahr = f.getJahrTankkostenStatistik(0);
        assertEquals(ergebnisJahr[0], kosten, 0);
        for (int i = 1; i < ergebnisJahr.length; i++) {
            assertEquals(ergebnisJahr[i], 0, 0);
        }
    }
}
