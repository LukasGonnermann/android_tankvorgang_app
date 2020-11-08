package com.example.tankauswertung;

import java.util.Hashtable;

public class Garage {
    private int maxAnzFahrzeuge;
    private int anzFahrzeuge;
    private Hashtable<Integer, Fahrzeug> fahrzeuge;

    public Garage() {
        this.maxAnzFahrzeuge = 25;
        this.anzFahrzeuge = 0;
        this.fahrzeuge = new Hashtable<Integer, Fahrzeug>(25);

    }

    public Garage(int fahrzeugeAnz) {
        this.maxAnzFahrzeuge = fahrzeugeAnz;
        this.anzFahrzeuge = 0;
        this.fahrzeuge = new Hashtable<>(fahrzeugeAnz);
    }

    public void fahrzeugHinzufuegen(Fahrzeug neuAuto) {
        if (anzFahrzeuge <= maxAnzFahrzeuge) {
            fahrzeuge.put(anzFahrzeuge, neuAuto);
        }
    }

    public void fahrzeugHinzufuegen(String pName, boolean pElektro, double pVerbrauchAusserorts, double pVerbrauchInnerorts,
                                   double pVerbrauchKombiniert, double pKmStand, int pTankstand, double pCo2Ausstoss) {
        if (anzFahrzeuge <= maxAnzFahrzeuge) {
            Fahrzeug neuAuto = new Fahrzeug(pName, pElektro, pVerbrauchAusserorts, pVerbrauchInnerorts, pVerbrauchKombiniert,
                    pKmStand, pTankstand, pCo2Ausstoss);
            fahrzeuge.put(anzFahrzeuge, neuAuto);
        }
    }


}
