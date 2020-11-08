package com.example.tankauswertung;

public class Tankvorgang {
    private double getankteMenge;
    private double preis;
    private String img;

    public Tankvorgang(double pGetankteMenge, double pPreis, String pImg) {
        getankteMenge = pGetankteMenge;
        preis = pPreis;
        img = pImg;
    }

    public void setGetankteMenge(double getankteMenge) {
        this.getankteMenge = getankteMenge;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public double getGetankteMenge() {
        return getankteMenge;
    }

    public double getPreis() {
        return preis;
    }

    public String getImg() {
        return img;
    }
}
