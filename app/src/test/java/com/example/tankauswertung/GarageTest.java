package com.example.tankauswertung;

import com.example.tankauswertung.exceptions.GarageLeerException;
import com.example.tankauswertung.exceptions.GarageVollException;

import org.junit.Test;

import static org.junit.Assert.*;

public class GarageTest {

    /**
     * Pruefen der GarageVollException
     * Garage mit maxAnzFahrzeugen 5 wird angelegt und mit 5 Fahrzeuen gefuellt. Anschließend sechstes Fahrzeug hinzufuegen.
     */
    @Test
    public void checkGarageVollException() {
        int max = 5;
        Garage G = new Garage(max);
        Fahrzeug f = new Fahrzeug("Testfahrzeug", false, 6, 7.5, 7, 27728, 70, 5, 45);

        for (int i = 0; i < max; i++) {
            try {
                G.fahrzeugHinzufuegen(f);
            } catch (GarageVollException e) {
                e.printStackTrace();
            }
        }

        Exception exception = assertThrows(GarageVollException.class,() -> G.fahrzeugHinzufuegen(f));
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

        Exception exception = assertThrows(GarageLeerException.class,() -> G.fahrzeugLoeschen(0));
        String expectedMessage = "Fahrzeug kann nicht geloescht werden, Garage leer!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
