package com.example.tankauswertung.ui.forecast;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import java.text.DecimalFormat;

/**
 * Erbende Klasse von ValueFormatter
 * Spezialisierung des gewuenschten Dezimal-Formats der Saeulendiagramme
 */
public class ForecastValueFormatter extends ValueFormatter {

    /**
     * Format-Vorlage fuer Dezimalzahlen
     * Zwei Nachkommastellen, falls diese ungleich 0 sind
     */
    private final DecimalFormat df = new DecimalFormat("#.##");

    /**
     * Ueberschreiben der Methode zur Beschriftung der Saeulen
     * @return Gibt den Wert in gewuenschtem Format zurueck
     */
    @Override
    public String getBarLabel(BarEntry barEntry) {
        return df.format(barEntry.getY());
    }
}
