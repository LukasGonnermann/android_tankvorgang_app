package com.example.tankauswertung;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class InputParser {
    NumberFormat numberFormat = NumberFormat.getInstance(Locale.GERMAN);
    enum Valid {VALID, INVALID, UNDEFINED};
    Valid valid;

    public InputParser() {
        valid = Valid.UNDEFINED;
    }

    public boolean isValid() {
        return valid == Valid.VALID;
    }

    public double parse(String toParse) {
        if(toParse.isEmpty() || toParse.startsWith(",") || toParse.endsWith(",")
                || toParse.chars().filter(ch -> ch == ',').count() > 1 || toParse.length() > 16) {
            valid = Valid.INVALID;
            return -1;
        }
        else {
            try {
                double fertig = numberFormat.parse(toParse).doubleValue();
                valid = Valid.VALID;
                return fertig;
            } catch (ParseException e) {
                valid = Valid.INVALID;
                return -1;
            }
        }
    }
}
