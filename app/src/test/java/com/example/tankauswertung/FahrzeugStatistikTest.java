package com.example.tankauswertung;

import android.icu.text.SymbolTable;

import org.junit.Test;

import static org.junit.Assert.*;

public class FahrzeugStatistikTest {
    @Test
    public void testGetWocheStreckenStatistik() {
        Fahrzeug f = new Fahrzeug("Testfahrzeug", false, 6, 7.5, 7, 27728, 70, 5, 45);
        f.streckeHinzufuegen(27748, Strecke.Streckentyp.KOMBINIERT, 34);
        f.streckeHinzufuegen(27768, Strecke.Streckentyp.KOMBINIERT, 34);
        f.streckeHinzufuegen(27773, Strecke.Streckentyp.KOMBINIERT, 34);
        double[] ergebnis = f.getWocheStreckenStatistik(0);
        for (int i = 0; i < ergebnis.length; i++) {
            System.out.println(i + ": " + ergebnis[i] + "\n");
        }
        assertEquals(ergebnis[0],45,0);
    }
}
