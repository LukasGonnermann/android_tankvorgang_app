package com.example.tankauswertung.exceptions;

/**
 * Exception wird geworfen wenn die Garage leer ist.
 */
public class GarageLeerException extends Exception {
    /**
     * Exception Message
     */
    private String exceptionMessage;

    /**
     * Default Exception Konstruktor, ruft Konstruktor von Exception auf und übergibt eine standard Exception Message
     */
    public GarageLeerException() {
        super("Fahrzeug kann nicht geloescht werden, Garage leer!");
        this.exceptionMessage = "Fahrzeug kann nicht geloescht werden, Garage leer!";
    }

    /**
     * Parametrisierter Exception Konstruktor, ruft Konstruktor von Exception auf und übergibt eine custom Exception Message
     * @param exceptionMessage Übergebene custom Message
     */
    public GarageLeerException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
    }

    /**
     * Gibt die Exception Message zurück zur Darstellung bzw. Weiterverarbeitung
     * @return Exception Message Value
     */
    public String getExceptionMessage() {
        return this.exceptionMessage;
    }
}
