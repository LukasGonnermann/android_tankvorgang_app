package com.example.tankauswertung;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class InputParser {

    enum Validity {VALID, INVALID, UNDEFINED};
    NumberFormat numberFormat = NumberFormat.getInstance(Locale.GERMAN);
    Validity valid;

    public InputParser() {
        valid = Validity.UNDEFINED;
    }

    public boolean isValid() {
        return valid == Validity.VALID;
    }

    public double parse(String toParse) {
        if (toParse.isEmpty() || toParse.startsWith(",")
                || toParse.chars().filter(ch -> ch == ',').count() > 1 || toParse.length() > 16) {
            valid = Validity.INVALID;
            return -1;
        } else {
            try {
                double fertig = numberFormat.parse(toParse).doubleValue();
                valid = Validity.VALID;
                return fertig;
            } catch (ParseException e) {
                valid = Validity.INVALID;
                return -1;
            }
        }
    }
}
