package com.example.tankauswertung;

import org.junit.Test;

import static org.junit.Assert.*;

public class FahrzeugTest {

    /**
     * Test fuer den Fahrzeug-Konstruktor
     * Erstelle Objekt Fahrzeug und pruefe alle Werte
     */
    @Test
    public void testFahrzeugKonstruktor() {
        Fahrzeug f = new Fahrzeug("Testfahrzeug", false, 6, 7.5, 7, 27728, 70, 5, 45);
        assertEquals("Testfahrzeug", f.getName());
        assertFalse(f.isElektro());
        assertEquals(6, f.getVerbrauchAusserorts(), 0);
        assertEquals(7.5, f.getVerbrauchInnerorts(), 0);
        assertEquals(7, f.getVerbrauchKombiniert(), 0);
        assertEquals(27728, f.getKmStand(), 0);
        assertEquals(70, f.getTankstand(), 0);
        assertEquals(5, f.getCo2Ausstoss(), 0);
        assertEquals(45, f.getTankgroesse(), 0);
    }

    /**
     * Test fuer die Fahrzeug-Setter
     * Rufe alle Setter auf (moeglichst mit ungueltigen Werten) und pruefe auf true oder false
     */
    @Test
    public void testFahrzeugSetter() {
        Fahrzeug f = new Fahrzeug("Testfahrzeug", false, 6, 7.5, 7, 27728, 70, 5, 45);
        assertFalse(f.setName(""));
        assertFalse(f.setName("       "));
        assertTrue(f.setElektro(true));
        assertFalse(f.setVerbrauchAusserorts(-5));
        assertFalse(f.setVerbrauchInnerorts(-5));
        assertFalse(f.setVerbrauchKombiniert(-5));
        assertFalse(f.setKmStand(-5));
        assertFalse(f.setTankstand(120));
        assertFalse(f.setTankstand(-5));
        assertFalse(f.setCo2Ausstoss(-5));
    }


}
