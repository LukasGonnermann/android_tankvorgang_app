package com.example.tankauswertung;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

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
     * Variable, welche das Format der Zeitangabe festlegt.
     */
    SimpleDateFormat formatter;

    /**
     * Variabe, welche die aktuelle Systemzeit zum Zeitpunkt der Eingabe einer Strecke speichert.
     */
    Date zeitstempel;

    /**
     * Konstruktor des Tankvorgangs, Setzt alle Attribute auf die Eingabewerte.
     */
    public Tankvorgang(double pGetankteMenge, double pPreis, String pImg) {
        getankteMenge = pGetankteMenge;
        preis = pPreis;
        img = pImg;
        formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        zeitstempel = new Date();
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

    /**
     * Getter fuer den Zeitstempel
     *
     * @return Date zeitstempel, Der Zeitpunkt der Abspeicherung.
     */
    public Date getZeitstempel() {
        return zeitstempel;
    }

    /**
     * Methode zum erlangen des Zeitstempels als String
     *
     * @return String format(this.zeitstempel), Ein zum String konvertiertes Date-Objekt.
     */
    public String getZeitstempelAsString() {
        return formatter.format(this.zeitstempel);
    }

    /**
     * Setter fuer alle Variablen eines Tankvorgangs
     *
     * @param pGetankteMenge double, getankte Menge in Litern
     * @param pPreis double, Preis in Euro
     * @param pImg String, Pfad zum Foto des Tankbelegs
     */
    public void tankvorgangBearbeiten(double pGetankteMenge, double pPreis, String pImg) {
        this.setGetankteMenge(pGetankteMenge);
        this.setPreis(pPreis);
        this.setImg(pImg);
    }
}
