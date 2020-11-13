package com.example.tankauswertung.exceptions;

/**
 * Exception wird geworfen wenn die Garage voll ist.
 */
public class GarageVollException extends Exception {
    /**
     * Exception Message
     */
    private String exceptionMessage;

    /**
     * Default Exception Konstruktor, ruft Konstruktor von Exception auf und übergibt eine standard Exception Message
     */
    public GarageVollException() {
        super("Fahrzeug kann nicht hinzugefügt werden, Garage voll!");
        this.exceptionMessage = "Fahrzeug kann nicht hinzugefügt werden, Garage voll!";
    }

    /**
     * Parametrisierter Exception Konstruktor, ruft Konstruktor von Exception auf und übergibt eine custom Exception Message
     * @param exceptionMessage Übergebene custom Message
     */
    public GarageVollException(String exceptionMessage) {
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
