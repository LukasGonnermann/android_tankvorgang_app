package com.example.tankauswertung.exceptions;

/**
 * Exception, wenn eine ungueltige ID der ArrayList fahrzeuge in der Garage angefordert wird
 */
public class GarageNullPointerException extends Exception {

    /**
     * Exception Message
     */
    private String exceptionMessage;

    /**
     * Default Exception Konstruktor, ruft Konstruktor von Exception auf und uebergibt eine standard Exception Message
     */
    public GarageNullPointerException() {
        super("An der angeforderten Position befindet sich kein Fahrzeug in der Garage.");
        this.exceptionMessage = "An der angeforderten Position befindet sich kein Fahrzeug in der Garage.";
    }

    /**
     * Parametrisierter Exception Konstruktor, ruft Konstruktor von Exception auf und uebergibt eine custom Exception Message
     *
     * @param exceptionMessage Uebergebene custom Message
     */
    public GarageNullPointerException(String exceptionMessage) {
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
