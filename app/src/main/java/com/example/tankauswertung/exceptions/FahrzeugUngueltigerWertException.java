package com.example.tankauswertung.exceptions;

/**
 * Exception, wenn im Setter fuer ein Fahrzeug-Attribut ein ungueltiger Wert gesetzt wird
 */
public class FahrzeugUngueltigerWertException extends Exception {
    /**
     * Exception Message
     */
    private String exceptionMessage;

    /**
     * Default Exception Konstruktor, ruft Konstruktor von Exception auf und übergibt eine standard Exception Message
     */
    public FahrzeugUngueltigerWertException() {
        super("Der eingegebene Wert ist ungueltig.");
        this.exceptionMessage = "Der eingegebene Wert ist ungueltig.";
    }


    /**
     * Parametrisierter Exception Konstruktor, ruft Konstruktor von Exception auf und übergibt eine custom Exception Message
     *
     * @param exceptionMessage Übergebene custom Message
     */
    public FahrzeugUngueltigerWertException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
    }

    /**
     * Gibt die Exception Message zurück zur Darstellung bzw. Weiterverarbeitung
     *
     * @return Exception Message Value
     */
    public String getExceptionMessage() {
        return this.exceptionMessage;
    }
}
