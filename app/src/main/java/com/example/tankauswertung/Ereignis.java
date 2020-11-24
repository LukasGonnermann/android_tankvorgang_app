package com.example.tankauswertung;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Wrapperklasse für Datensatz eines Timeline-Elements
 */
public class Ereignis {

    public static enum EreignisTyp {STRECKE, TANKVORGANG};

    private final EreignisTyp ereignisTyp;
    private final int index;  // Index im Strecken- oder Tankvorgang-Array
    private final Date zeitstempel;
    private final String beschreibung;

    public Ereignis(EreignisTyp ereignisTyp, int index, Date datum, String beschreibung) {
        this.ereignisTyp = ereignisTyp;
        this.index = index;
        this.zeitstempel = datum;
        this.beschreibung = beschreibung;
    }

    public EreignisTyp getEreignisTyp() {
        return ereignisTyp;
    }

    public int getIndex() {
        return index;
    }

    public Date getZeitstempel() {
        return zeitstempel;
    }

    public String getDatumAsString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return formatter.format(this.zeitstempel);
    }

    public String getBeschreibung() {
        return beschreibung;
    }
}
