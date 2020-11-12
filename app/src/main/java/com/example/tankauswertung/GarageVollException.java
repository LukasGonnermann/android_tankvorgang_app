package com.example.tankauswertung;

public class GarageVollException extends Exception{
    private String exceptionMessage = "Fahrzeug kann nicht hinzugefügt werden, Garage voll!";

    GarageVollException() {
        super("Fahrzeug kann nicht hinzugefügt werden, Garage voll!");
    }

    GarageVollException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return this.exceptionMessage;
    }
}
