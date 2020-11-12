package com.example.tankauswertung;

import java.io.Serializable;

/**
 * Klasse Tankvorgang
 * Anlegen von Tankvorgaengen und Aendern des letzten Tankvorgangs
 */
public class Tankvorgang implements Serializable {

    /**
     * Die getankte Menge in Litern
     */
    private double getankteMenge;

    /**
     * Gesamtkosten des Tankvorgangs
     */
    private double preis;

    /**
     * Pfad zum Bild des Tankbelegs
     */
    private String img;

    /**
     * Konstruktor des Tankvorgangs, Setzt alle Attribute auf die Eingabewerte.
     */
    public Tankvorgang(double pGetankteMenge, double pPreis, String pImg) {
        getankteMenge = pGetankteMenge;
        preis = pPreis;
        img = pImg;
    }

    /**
     * Setter fuer getankte Menge in Litern
     *
     * @param getankteMenge double, getankte Menge in Litern
     */
    public void setGetankteMenge(double getankteMenge) {
        this.getankteMenge = getankteMenge;
    }

    /**
     * Setter fuer den Pfad zum Foto des Tankbelegs
     *
     * @param img String, Pfad zum Foto
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * Setter fuer die Kosten des Tankvorgangs
     *
     * @param preis double, Preis in Euro
     */
    public void setPreis(double preis) {
        this.preis = preis;
    }

    /**
     * Getter fuer die Tankmenge in Litern.
     *
     * @return double, Menge in Litern
     */
    public double getGetankteMenge() {
        return getankteMenge;
    }

    /**
     * Getter fuer den Preis des Tankvorgangs
     *
     * @return double, Preis in Euro
     */
    public double getPreis() {
        return preis;
    }

    /**
     * Getter fuer des Pfad des Tankbelegbildes
     *
     * @return String, Pfad zum Bild
     */
    public String getImg() {
        return img;
    }
}
