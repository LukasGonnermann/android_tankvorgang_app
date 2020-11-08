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

    public double getCo2Ausstoss() {
        return co2Ausstoss;
    }

    public double getKmStand() {
        return kmStand;
    }

    public double getVerbrauchAusserorts() {
        return verbrauchAusserorts;
    }

    public double getVerbrauchInnerorts() {
        return verbrauchInnerorts;
    }

    public double getVerbrauchKombiniert() {
        return verbrauchKombiniert;
    }

    public int getTankstand() {
        return tankstand;
    }

    public String getName() {
        return name;
    }

    public void setCo2Ausstoss(double co2Ausstoss) {
        this.co2Ausstoss = co2Ausstoss;
    }

    public void setElektro(boolean elektro) {
        this.elektro = elektro;
    }

    public void setKmStand(double kmStand) {
        this.kmStand = kmStand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTankstand(int tankstand) {
        this.tankstand = tankstand;
    }

    public void setVerbrauchAusserorts(double verbrauchAusserorts) {
        this.verbrauchAusserorts = verbrauchAusserorts;
    }

    public void setVerbrauchInnerorts(double verbrauchInnerorts) {
        this.verbrauchInnerorts = verbrauchInnerorts;
    }

    public void setVerbrauchKombiniert(double verbrauchKombiniert) {
        this.verbrauchKombiniert = verbrauchKombiniert;
    }

    public boolean isElektro() {
        return elektro;
    }
}
