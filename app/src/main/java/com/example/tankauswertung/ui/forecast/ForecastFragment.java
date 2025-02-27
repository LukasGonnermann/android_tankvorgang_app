package com.example.tankauswertung.ui.forecast;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tankauswertung.Fahrzeug;
import com.example.tankauswertung.Garage;
import com.example.tankauswertung.InputParser;
import com.example.tankauswertung.MainActivity;
import com.example.tankauswertung.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;

public class ForecastFragment extends Fragment {

    private View root;

    boolean korrekteEingabe = true;
    boolean aendern = true;  // verhindere StackOverflow durch gegenseitiges Ändern der Slider

    Garage garage;
    Fahrzeug aktuellesFahrzeug;

    TextView labelKraftstoffverbrauchTitel;
    TextView labelKraftstoffkostenTitel;
    TextView labelAnzahlNoetigerTankvorgaengeTitel;
    TextView labelInnerortsWert;
    TextView labelAusserortsWert;
    TextView labelKombiniertWert;

    EditText editTextStreckenlaenge;
    SeekBar seekBarInnerorts;
    SeekBar seekBarAusserorts;
    SeekBar seekBarKombiniert;
    TextView labelKraftstoffverbrauchWert;
    TextView labelKraftstoffkostenWert;
    TextView labelAnzahlNoetigerTankvorgaengeWert;
    TextView labelCo2AusstossWert;

    DecimalFormat dfTankvorgaenge = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.GERMAN));
    DecimalFormat dfVerbrauch = new DecimalFormat("#.## l", new DecimalFormatSymbols(Locale.GERMAN));
    DecimalFormat dfVerbrauchElektro = new DecimalFormat("#.## kWh", new DecimalFormatSymbols(Locale.GERMAN));
    DecimalFormat dfKosten = new DecimalFormat("0.00 €", new DecimalFormatSymbols(Locale.GERMAN));
    DecimalFormat dfCo2 = new DecimalFormat("#.## kg", new DecimalFormatSymbols(Locale.GERMAN));

    InputParser inputParser = new InputParser();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_forecast, container, false);
        return root;
    }

    /**
     * immer ausgeführt, bevor das Fragment (der Tab) bedienbar ist (nach jedem Wechseln etc. etc.)
     */
    @Override
    public void onResume() {
        super.onResume();

        // label elements
        labelKraftstoffverbrauchTitel = root.findViewById(R.id.labelKraftstoffverbrauchTitel);
        labelKraftstoffkostenTitel = root.findViewById(R.id.labelKraftstoffkostenTitel);
        labelAnzahlNoetigerTankvorgaengeTitel = root.findViewById(R.id.labelAnzahlNoetigerTankvorgaengeTitel);
        labelInnerortsWert = root.findViewById(R.id.labelKalkulationInnerortsWert);
        labelAusserortsWert = root.findViewById(R.id.labelKalkulationAusserortsWert);
        labelKombiniertWert = root.findViewById(R.id.labelKalkulationKombiniertWert);

        // input elements
        editTextStreckenlaenge = root.findViewById(R.id.editTextKalkulationStreckenlaenge);
        seekBarInnerorts = root.findViewById(R.id.seekBarKalkulationInnerorts);
        seekBarAusserorts = root.findViewById(R.id.seekBarKalkulationAusserorts);
        seekBarKombiniert = root.findViewById(R.id.seekBarKalkulationKombiniert);

        // output elements
        labelKraftstoffverbrauchWert = root.findViewById(R.id.labelKraftstoffverbrauchWert);
        labelKraftstoffkostenWert = root.findViewById(R.id.labelKraftstoffkostenWert);
        labelAnzahlNoetigerTankvorgaengeWert = root.findViewById(R.id.labelAnzahlNoetigerTankvorgaengeWert);
        labelCo2AusstossWert = root.findViewById(R.id.labelKalkulationCo2AusstossWert);

        garage = MainActivity.getGarage();
        aktuellesFahrzeug = garage.getAusgewaehltesFahrzeug();

        // Elektromodifikationen
        if (aktuellesFahrzeug.isElektro()) {
            labelKraftstoffverbrauchTitel.setText(R.string.energieverbrauch);
            labelKraftstoffkostenTitel.setText(R.string.stromkosten);
            labelAnzahlNoetigerTankvorgaengeTitel.setText(R.string.noetige_tankvorgaenge_elektro);
        }

        // --- input listener

        editTextStreckenlaenge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String string = editable.toString();
                double parsedDouble = inputParser.parse(string);

                korrekteEingabe = false;

                if (string.isEmpty()) {
                    editTextStreckenlaenge.setError("Bitte geben Sie eine Streckenlänge ein.");
                } else if (!inputParser.isValid()) {
                    editTextStreckenlaenge.setError("Bitte geben Sie einen gültigen Wert ein.");
                } else if (parsedDouble <= 0) {
                    editTextStreckenlaenge.setError("Bitte geben Sie eine Streckenlänge größer 0 km ein.");
                } else {
                    korrekteEingabe = true;
                }
                updateBerechneteWerte();
            }
        });

        seekBarInnerorts.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateBerechneteWerte();
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int w1, boolean b) {

                labelInnerortsWert.setText(String.valueOf(w1));

                int w3 = seekBarKombiniert.getProgress();

                // 1 geändert -> passe 2 an, falls möglich, ansonsten passe zusätzlich 3 an

                if (aendern) {
                    aendern = false;

                    if (100 - w1 - w3 >= 0) {
                        // 2 anpassen reicht
                        seekBarAusserorts.setProgress(100 - w1 - w3);
                    } else {
                        // 3 muss zusätzlich angepasst werden
                        seekBarAusserorts.setProgress(0);
                        seekBarKombiniert.setProgress(100 - w1);
                    }
                } else {
                    aendern = true;
                }
            }
        });

        seekBarAusserorts.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateBerechneteWerte();
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int w2, boolean b) {
                labelAusserortsWert.setText(String.valueOf(w2));

                int w1 = seekBarInnerorts.getProgress();

                // 2 geändert -> passe 3 an, falls möglich, ansonsten passe zusätzlich 1 an

                if (aendern) {
                    aendern = false;

                    if (100 - w2 - w1 >= 0) {
                        // 3 anpassen reicht
                        seekBarKombiniert.setProgress(100 - w2 - w1);
                    } else {
                        // 1 muss zusätzlich angepasst werden
                        seekBarKombiniert.setProgress(0);
                        seekBarInnerorts.setProgress(100 - w2);
                    }
                } else {
                    aendern = true;
                }
            }
        });

        seekBarKombiniert.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateBerechneteWerte();
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int w3, boolean b) {
                labelKombiniertWert.setText(String.valueOf(w3));

                int w2 = seekBarAusserorts.getProgress();

                // 3 geändert -> passe 1 an, falls möglich, ansonsten passe zusätzlich 2 an

                if (aendern) {
                    aendern = false;

                    if (100 - w3 - w2 >= 0) {
                        // 2 anpassen reicht
                        seekBarInnerorts.setProgress(100 - w3 - w2);
                    } else {
                        // 1 muss zusätzlich angepasst werden
                        seekBarInnerorts.setProgress(0);
                        seekBarAusserorts.setProgress(100 - w3);
                    }
                } else {
                    aendern = true;
                }
            }
        });

        updateBerechneteWerte();
    }


    /**
     * u. a. immer ausgeführt, sobald Werte der Eingabefelder geändert wurden
     */
    private void updateBerechneteWerte() {

        if (korrekteEingabe) {

            HashMap<String, Double> streckenprognose = aktuellesFahrzeug.getStreckenprognose(
                    inputParser.parse(editTextStreckenlaenge.getText().toString()),
                    seekBarInnerorts.getProgress(),
                    seekBarAusserorts.getProgress(),
                    seekBarKombiniert.getProgress()
            );

            double kraftstoffverbrauch = streckenprognose.get("kraftstoffverbrauch");
            double kraftstoffkosten = streckenprognose.get("kraftstoffkosten");
            int anzahlNoetigerTankvorgaenge = streckenprognose.get("anzahlNoetigerTankvorgaenge").intValue();
            double co2Ausstoss = streckenprognose.get("co2Ausstoss");

            if (!aktuellesFahrzeug.isElektro()) {
                labelCo2AusstossWert.setText(dfCo2.format(co2Ausstoss));
                labelKraftstoffverbrauchWert.setText(dfVerbrauch.format(kraftstoffverbrauch));
            } else {
                labelKraftstoffverbrauchWert.setText(dfVerbrauchElektro.format(kraftstoffverbrauch));
                labelCo2AusstossWert.setText(R.string.idle);
            }

            labelAnzahlNoetigerTankvorgaengeWert.setText(dfTankvorgaenge.format(anzahlNoetigerTankvorgaenge));

            if (kraftstoffkosten == -1) {
                labelKraftstoffkostenWert.setText(R.string.idle);
            } else {
                labelKraftstoffkostenWert.setText(dfKosten.format(kraftstoffkosten));
            }
        } else {
            labelKraftstoffverbrauchWert.setText(R.string.idle);
            labelKraftstoffkostenWert.setText(R.string.idle);
            labelAnzahlNoetigerTankvorgaengeWert.setText(R.string.idle);
            labelCo2AusstossWert.setText(R.string.idle);
        }
    }
}
