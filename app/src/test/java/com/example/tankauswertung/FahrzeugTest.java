package com.example.tankauswertung;

import com.example.tankauswertung.exceptions.FahrzeugWertException;

import org.junit.Test;

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


}
