package com.example.tankauswertung;

import java.io.Serializable;

public class Settings implements Serializable {
    /**
     * Enum DarkModeStatus umfasst die möglichen Zustände der Einstellung zum Darkmode
     */
    public enum DarkModeStatus {AN, AUS, SYSTEM};
    /**
     * Variable, welche die Darkmodeeinstellung speichert
     */
    private DarkModeStatus darkModeSafe;

    /**
     * Konstruktor der Klasse, setzt Standardmaessig die Darkmodesettings auf SYSTEM
     */
    public Settings(){
        darkModeSafe = DarkModeStatus.SYSTEM;
    }

    /**
     * Getter fuer die Darkmodesettings
     * @return enum DarkModeStatus, Aktuelle Darkmodeeinstellung
     */
    public DarkModeStatus getDarkModeSafe() { return darkModeSafe; }

    /**
     * Setter fuer die Darkmodesetting Variable
     * @param darkModeSafe enum DarkModeStatus, Inputwert, auf welchen die Darkmodeeinstellung gesetzt wird
     */
    public void setDarkModeSafe(DarkModeStatus darkModeSafe) { this.darkModeSafe = darkModeSafe; }
}
