package com.example.tankauswertung;

import com.example.tankauswertung.exceptions.FahrzeugWertException;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FahrzeugTest {

    /**
     * Test fuer den Fahrzeug-Konstruktor und Getter
     * Erstelle Objekt Fahrzeug und pruefe alle Werte ueber Getter
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
     * Test fuer Setter der Fahrzeug-Attribute
     * Rufe alle Setter auf (moeglichst mit ungueltigen Werten) und pruefe auf true oder false
     */
    @Test
    public void testFahrzeugAendern() {
        Fahrzeug f = new Fahrzeug("Testfahrzeug", false, 6, 7.5, 7, 27728, 70, 5, 45);

        // Name
        Exception exception = assertThrows(FahrzeugWertException.class, () -> f.setName("      "));
        String expectedMessage = "Name kann nicht gesetzt werden, da ein ungueltiger Wert eingegeben wurde.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        // Verbrauch Innerorts
        exception = assertThrows(FahrzeugWertException.class, () -> f.setVerbrauchInnerorts(-5));
        expectedMessage = "Verbrauch Innerorts konnte nicht gesetzt werden, da ein negativer Wert eingegeben wurde.";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        // Verbrauch Ausserorts
        exception = assertThrows(FahrzeugWertException.class, () -> f.setVerbrauchAusserorts(-5));
        expectedMessage = "Verbrauch Ausserorts konnte nicht gesetzt werden, da ein negativer Wert eingegeben wurde.";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        // Verbrauch Innerorts
        exception = assertThrows(FahrzeugWertException.class, () -> f.setVerbrauchKombiniert(-5));
        expectedMessage = "Verbrauch kombiniert konnte nicht gesetzt werden, da ein negativer Wert eingegeben wurde.";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        // Kilometerstand
        exception = assertThrows(FahrzeugWertException.class, () -> f.setKmStand(-5));
        expectedMessage = "Kilometerstand konnte nicht gesetzt werden, da ein negativer Wert eingegeben wurde.";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        // Tankstand I
        exception = assertThrows(FahrzeugWertException.class, () -> f.setTankstand(-5));
        expectedMessage = "Tankstand konnte nicht gesetzt werden, da kein Wert zwischen 0 und 100 eingegeben wurde.";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        // Tankstand II
        exception = assertThrows(FahrzeugWertException.class, () -> f.setTankstand(101));
        expectedMessage = "Tankstand konnte nicht gesetzt werden, da kein Wert zwischen 0 und 100 eingegeben wurde.";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        // CO2-Ausstoss I
        exception = assertThrows(FahrzeugWertException.class, () -> f.setCo2Ausstoss(-5));
        expectedMessage = "CO2-Ausstoss konnte nicht gesetzt werden, da ein negativer Wert eingegeben wurde.";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        // CO2-Ausstoss II
        f.setElektro(true);
        exception = assertThrows(FahrzeugWertException.class, () -> f.setCo2Ausstoss(5));
        expectedMessage = "Bei einem Elektroauto kann kein CO2-Ausstoss groesser 0 eingegeben werden.";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        // Tankgroesse
        exception = assertThrows(FahrzeugWertException.class, () -> f.setTankgroesse(-5));
        expectedMessage = "Tankgroesse konnte nicht gesetzt werden, da ein negativer Wert eingegeben wurde.";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test der gespeicherten Strecken zu einem Fahrzeugobjekt
     * Strecken hinzufuegen, korrekte Werte und Reihenfolge pruefen
     */
    @Test
    public void testFahrzeugStrecken() {
        // Fahrzeug und Strecken anlegen
        Fahrzeug f = new Fahrzeug("Testfahrzeug", false, 6, 7.5, 7, 27728, 70, 5, 45);
        Strecke s1 = new Strecke(24.37, Strecke.Streckentyp.kombiniert, 34);
        Strecke s2 = new Strecke(0.85, Strecke.Streckentyp.innerorts, 12);
        Strecke s3 = new Strecke(85.4, Strecke.Streckentyp.ausserorts, 97);

        // Strecken hinzufuegen
        f.streckeHinzufuegen(s1.getDistanz(), s1.getStreckentyp(), s1.getTankstand());
        f.streckeHinzufuegen(s2.getDistanz(), s2.getStreckentyp(), s2.getTankstand());
        f.streckeHinzufuegen(s3.getDistanz(), s3.getStreckentyp(), s3.getTankstand());

        // Strecken in ArrayList pruefen
        ArrayList<Strecke> fahrzeugstrecken = f.getStrecken();

        // Strecke S3 an Index 0 der Liste
        assertEquals(fahrzeugstrecken.get(0).getDistanz(), s3.getDistanz(), 0);
        assertEquals(fahrzeugstrecken.get(0).getStreckentyp(), s3.getStreckentyp());
        assertEquals(fahrzeugstrecken.get(0).getTankstand(), s3.getTankstand(), 0);

        // Strecke S2 an Index 1 der Liste
        assertEquals(fahrzeugstrecken.get(1).getDistanz(), s2.getDistanz(), 0);
        assertEquals(fahrzeugstrecken.get(1).getStreckentyp(), s2.getStreckentyp());
        assertEquals(fahrzeugstrecken.get(1).getTankstand(), s2.getTankstand(), 0);

        // Strecke S1 an Index 2 der Liste
        assertEquals(fahrzeugstrecken.get(2).getDistanz(), s1.getDistanz(), 0);
        assertEquals(fahrzeugstrecken.get(2).getStreckentyp(), s1.getStreckentyp());
        assertEquals(fahrzeugstrecken.get(2).getTankstand(), s1.getTankstand(), 0);
    }

    /**
     * Test der gespeicherten Tankvorgaenge zu einem Fahrzeugobjekt
     * Tankvorgaenge hinzufuegen, korrekte Werte und Reihenfolge pruefen
     * Fehlerhaften Tankvorgang hinzufuegen (getankte Menge > Tankgroesse) und Exception pruefen
     */
    @Test
    public void testFahrzeugTankvorgaenge() {
        // Fahrzeug und Tankvorgaenge anlegen
        Fahrzeug f = new Fahrzeug("Testfahrzeug", false, 6, 7.5, 7, 27728, 70, 5, 45);
        Tankvorgang t1 = new Tankvorgang(32.78, 55.24, "/DCIM/Camera/meinFoto1.jpg");
        Tankvorgang t2 = new Tankvorgang(12.4, 27.74, "/DCIM/Camera/meinFoto2.jpg");
        Tankvorgang t3 = new Tankvorgang(45.0, 85.30, "/DCIM/Camera/meinFoto3.jpg");

        // Tankvorgaenge hinzufuegen
        try {
            f.tangvorgangHinzufuegen(t1.getGetankteMenge(), t1.getPreis(), t1.getImg());
            f.tangvorgangHinzufuegen(t2.getGetankteMenge(), t2.getPreis(), t2.getImg());
            f.tangvorgangHinzufuegen(t3.getGetankteMenge(), t3.getPreis(), t3.getImg());
        } catch (FahrzeugWertException e) {
            e.printStackTrace();
        }

        // Tankvorgaenge in ArrayList pruefen
        ArrayList<Tankvorgang> tankvorgaenge = f.getTankvorgaenge();

        // Tankvorgang t3 an Index 0 der Liste
        assertEquals(tankvorgaenge.get(0).getGetankteMenge(), t3.getGetankteMenge(), 0);
        assertEquals(tankvorgaenge.get(0).getPreis(), t3.getPreis(), 0);
        assertEquals(tankvorgaenge.get(0).getImg(), t3.getImg());

        // Tankvorgang t2 an Index 1 der Liste
        assertEquals(tankvorgaenge.get(1).getGetankteMenge(), t2.getGetankteMenge(), 0);
        assertEquals(tankvorgaenge.get(1).getPreis(), t2.getPreis(), 0);
        assertEquals(tankvorgaenge.get(1).getImg(), t2.getImg());

        // Tankvorgang t1 an Index 1 der Liste
        assertEquals(tankvorgaenge.get(2).getGetankteMenge(), t1.getGetankteMenge(), 0);
        assertEquals(tankvorgaenge.get(2).getPreis(), t1.getPreis(), 0);
        assertEquals(tankvorgaenge.get(2).getImg(), t1.getImg());

        // Fehlerhaften Tankvorgang anlegen (getankte Menge > Tankgroesse)
        Tankvorgang t4 = new Tankvorgang(45.01, 86.10, "/DCIM/Camera/meinFoto4.jpg");

        // Tankvorgang t4 hinzufuegen und Exception pruefen
        Exception exception = assertThrows(FahrzeugWertException.class, () -> f.tangvorgangHinzufuegen(t4.getGetankteMenge(), t4.getPreis(), t4.getImg()));
        String expectedMessage = "Die getankte Menge kann nicht groesser als die Tankgroesse des Fahrzeugs sein.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
