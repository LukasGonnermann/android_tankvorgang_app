package com.example.tankauswertung;

import com.example.tankauswertung.exceptions.GarageLeerException;
import com.example.tankauswertung.exceptions.GarageNullPointerException;
import com.example.tankauswertung.exceptions.GarageVollException;

import org.junit.Test;

import static org.junit.Assert.*;

public class GarageTest {

    /**
     * Test der grundlegenden Funktionen der Garage
     */
    @Test
    public void testGarageBasics() {
        // leere Garage anlegen
        Garage G = new Garage(15);
        assertTrue(G.isEmpty());

        // Drei Testfahrzeuge einfuegen
        Fahrzeug f1 = new Fahrzeug("Testfahrzeug1", false, 6, 7.5, 7, 27728, 70, 5, 45);
        Fahrzeug f2 = new Fahrzeug("Testfahrzeug2", false, 6, 7.5, 7, 27728, 70, 5, 45);
        Fahrzeug f3 = new Fahrzeug("Testfahrzeug3", false, 6, 7.5, 7, 27728, 70, 5, 45);
        try {
            G.fahrzeugHinzufuegen(f1);
            G.fahrzeugHinzufuegen(f2);
            G.fahrzeugHinzufuegen(f3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(3, G.getAnzFahrzeuge(), 0);
        assertFalse(G.isEmpty());

        // Fahrzeug f2 auswaehlen und pruefen
        try {
            G.setAusgewaehltesFahrzeugById(1);
        } catch (GarageNullPointerException e) {
            e.printStackTrace();
        }
        assertEquals(f2, G.getAusgewaehltesFahrzeug());
        assertEquals("Testfahrzeug2", G.getAusgewaehltesFahrzeug().getName());

        // Fahrzeug f2 loeschen und pruefen
        try {
            G.fahrzeugLoeschen(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(2, G.getAnzFahrzeuge(), 0);

        // Fahrzeug nach ID auswaehlen
        try {
            assertEquals(f3, G.getFahrzeugById(1));
        } catch (GarageNullPointerException e) {
            e.printStackTrace();
        }
        assertEquals(2, G.getAnzFahrzeuge(), 0);

        // alle Fahrzeuge wieder loeschen und pruefen
        try {
            G.fahrzeugLoeschen(1);
            G.fahrzeugLoeschen(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(0, G.getAnzFahrzeuge(), 0);
        assertTrue(G.isEmpty());

    }

    /**
     * Pruefen der GarageVollException
     * Garage mit maxAnzFahrzeugen 5 wird angelegt und mit 5 Fahrzeuen gefuellt. Anschließend sechstes Fahrzeug hinzufuegen.
     */
    @Test
    public void checkGarageVollException() {
        int max = 5; // maximale Anzahl an Fahrzeugen
        Garage G = new Garage(max);
        Fahrzeug f = new Fahrzeug("Testfahrzeug", false, 6, 7.5, 7, 27728, 70, 5, 45);

        // Garage mit 5 Fahrzeugen fuellen
        for (int i = 0; i < max; i++) {
            try {
                G.fahrzeugHinzufuegen(f);
            } catch (GarageVollException e) {
                e.printStackTrace();
            }
        }

        // Sechstes Fahrzeug hinzufuegen
        Exception exception = assertThrows(GarageVollException.class, () -> G.fahrzeugHinzufuegen(f));
        String expectedMessage = "Fahrzeug kann nicht hinzugefügt werden, Garage voll!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Pruefen der GarageLeerException
     * Fahrzeug aus leerer Garage loeschen
     */
    @Test
    public void checkGarageLeerException() {
        Garage G = new Garage();

        Exception exception = assertThrows(GarageLeerException.class, () -> G.fahrzeugLoeschen(0));
        String expectedMessage = "Fahrzeug kann nicht geloescht werden, Garage leer!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Pruefen der GarageNullPointerException
     * Gueltige und ungueltige IDs aus der Garage anfordern
     */
    @Test
    public void checkGarageNullPointerException() {
        // Garage und Fahrzeug anlegen
        Garage G = new Garage();
        Fahrzeug f = new Fahrzeug("Testfahrzeug", false, 6, 7.5, 7, 27728, 70, 5, 45);

        // Fahrzeug in Garage
        try {
            G.fahrzeugHinzufuegen(f);
        } catch (GarageVollException e) {
            e.printStackTrace();
        }

        // gueltiges Fahrzeug auswaehlen
        try {
            Fahrzeug f2 = G.getFahrzeugById(0);
            assertEquals(f, f2);
        } catch (GarageNullPointerException e) {
            e.printStackTrace();
        }

        // ungeltiges Fahrzeug auswaehlen
        Exception exception = assertThrows(GarageNullPointerException.class, () -> G.getFahrzeugById(1));
        String expectedMessage = "An der angeforderten Position befindet sich kein Fahrzeug in der Garage!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
