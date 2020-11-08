package com.example.tankauswertung;

public class Fahrzeug {
    private String name;
    private boolean elektro;
    private double verbrauchInnerorts;
    private double verbrauchAusserorts;
    private double verbrauchKombiniert;
    private double kmStand;
    private int tankstand; //0-100 [%]
    private double co2Ausstoss;

    public Fahrzeug(String nameIn, boolean elektroIn, double verbrauchAusserortIn, double verbrauchInnerortsIn,
                    double verbrauchKombiniertIn, double kmStandIn, int tankstandIn, double co2AusstossIn) {
        this.name = nameIn;
        this.elektro = elektroIn;
        this.verbrauchInnerorts = verbrauchInnerortsIn;
        this.verbrauchAusserorts = verbrauchAusserortIn;
        this.verbrauchKombiniert = verbrauchKombiniertIn;
        this.kmStand = kmStandIn;
        this.tankstand = tankstandIn;
        this.co2Ausstoss = co2AusstossIn;
    }


}
