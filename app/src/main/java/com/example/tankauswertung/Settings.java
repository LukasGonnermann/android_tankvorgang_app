package com.example.tankauswertung;

import java.io.Serializable;

public class Settings implements Serializable {
    /**
     * Enum darkMode umfasst die möglichen Zustände der Einstellung zum Darkmode
     */
    public enum darkMode {AN, AUS, SYSTEM};
    /**
     * Variable, welche die Darkmodeeinstellung speichert
     */
    private darkMode darkModeSafe;

    /**
     * Konstruktor der Klasse, setzt Standardmaessig die Darkmodesettings auf SYSTEM
     */
    public Settings(){
        darkModeSafe = darkMode.SYSTEM;
    }

    /**
     * Getter fuer die Darkmodesettings
     * @return enum darkMode, Aktuelle Darkmodeeinstellung
     */
    public darkMode getDarkModeSafe() { return darkModeSafe; }

    /**
     * Setter fuer die Darkmodesetting Variable
     * @param darkModeSafe enum darkMode, Inputwert, auf welchen die Darkmodeeinstellung gesetzt wird
     */
    public void setDarkModeSafe(darkMode darkModeSafe) { this.darkModeSafe = darkModeSafe; }
}
