package com.example.tankauswertung;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class StreckeTest {

    /**
     * Tests der grundlegenden Funktionen der Strecken
     */
    @Test
    public void testStreckeBasic() {
        // Aktuelle Uhrzeit zum Vergleich speichern
        Date aktuelleZeit = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String aktuelleZeitString = formatter.format(aktuelleZeit);

        // Strecke anlegen
        Strecke S = new Strecke(24.37, Strecke.Streckentyp.INNERORTS, 34);
        // Werte mit Gettern pruefen
        assertEquals(24.37,S.getDistanz(),0);
        assertEquals(Strecke.Streckentyp.INNERORTS,S.getStreckentyp());
        assertEquals(34,S.getTankstand(),0);
        assertEquals(aktuelleZeitString,S.getZeitstempelAsString());

        // Strecke bearbeiten
        S.streckeBearbeiten(20.57, Strecke.Streckentyp.KOMBINIERT,20.5);
        // Werte mit Gettern pruefen
        assertEquals(20.57,S.getDistanz(),0);
        assertEquals(Strecke.Streckentyp.KOMBINIERT,S.getStreckentyp());
        assertEquals(20.5,S.getTankstand(),0);
        assertEquals(aktuelleZeitString,S.getZeitstempelAsString());

    }
}
