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

    public String getName() {
        return name;
    }

    public boolean isElektro() {
        return elektro;
    }

    public double getVerbrauchInnerorts() {
        return verbrauchInnerorts;
    }

    public double getVerbrauchAusserorts() {
        return verbrauchAusserorts;
    }

    public double getVerbrauchKombiniert() {
        return verbrauchKombiniert;
    }

    public double getKmStand() {
        return kmStand;
    }

    public int getTankstand() {
        return tankstand;
    }

    public double getCo2Ausstoss() {
        return co2Ausstoss;
    }


    public boolean setName(String name) {
        if (name != null && !name.isEmpty()) {
            return false;
        }
        this.name = name;
        return true;
    }

    public boolean setElektro(boolean elektro) {
        this.elektro = elektro;
        return true;
    }

    public boolean setVerbrauchInnerorts(double verbrauchInnerorts) {
        if (verbrauchInnerorts < 0) {
            return false;
        }
        this.verbrauchInnerorts = verbrauchInnerorts;
        return true;
    }

    public boolean setVerbrauchAusserorts(double verbrauchAusserorts) {
        if (verbrauchAusserorts < 0) {
            return false;
        }
        this.verbrauchAusserorts = verbrauchAusserorts;
        return true;
    }

    public boolean setVerbrauchKombiniert(double verbrauchKombiniert) {
        if (verbrauchKombiniert < 0) {
            return false;
        }
        this.verbrauchKombiniert = verbrauchKombiniert;
        return true;
    }

    public boolean setKmStand(double kmStand) {
        if (kmStand < 0) {
            return false;
        }
        this.kmStand = kmStand;
        return true;
    }

    public boolean setTankstand(int tankstand) {
        if (tankstand < 0 || tankstand > 100) {
            return false;
        }
        this.tankstand = tankstand;
        return true;
    }

    public boolean setCo2Ausstoss(double co2Ausstoss) {
        if (co2Ausstoss < 0) {
            return false;
        }
        this.co2Ausstoss = co2Ausstoss;
        return true;
    }


}
