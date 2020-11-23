package com.example.tankauswertung;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class TankvorgangTest {

    /**
     * Tests der grundlegenden Funktionen der Tankvorgänge
     */
    @Test
    public void testTankvorgangBasic() {
        // Aktuelle Uhrzeit zum Vergleich speichern
        Date aktuelleZeit = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String aktuelleZeitString = formatter.format(aktuelleZeit);

        // Tankvorgang anlegen
        Tankvorgang T = new Tankvorgang(32.78, 55.24, "/DCIM/Camera/meinFoto.jpg");
        // Werte mit Gettern pruefen
        assertEquals(32.78, T.getGetankteMenge(), 0);
        assertEquals(55.24, T.getPreis(), 0);
        assertEquals("/DCIM/Camera/meinFoto.jpg", T.getImg());
        assertEquals(aktuelleZeitString, T.getZeitstempelAsString());

        // Tankvorgang bearbeiten
        T.tankvorgangBearbeiten(34.78, 50.24, "/DCIM/Camera/meinFoto2.jpg");
        // Werte mit Gettern pruefen
        assertEquals(34.78, T.getGetankteMenge(), 0);
        assertEquals(50.24, T.getPreis(), 0);
        assertEquals("/DCIM/Camera/meinFoto2.jpg", T.getImg());
        assertEquals(aktuelleZeitString, T.getZeitstempelAsString());
    }
}
