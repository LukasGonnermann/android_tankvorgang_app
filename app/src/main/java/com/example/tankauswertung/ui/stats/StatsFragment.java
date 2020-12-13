package com.example.tankauswertung.ui.stats;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.airbnb.paris.Paris;
import com.example.tankauswertung.Fahrzeug;
import com.example.tankauswertung.Garage;
import com.example.tankauswertung.MainActivity;
import com.example.tankauswertung.R;
import com.example.tankauswertung.ui.forecast.ForecastValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.stream.Stream;

public class StatsFragment extends Fragment {

    private final String setLabel = "";

    // UI-Elemente
    ImageButton imageButtonStrecken;
    ImageButton imageButtonTreibstoff;
    ImageButton imageButtonTankkosten;
    ImageButton imageButtonCO2;
    ImageButton buttonFrueher;
    ImageButton buttonSpaeter;
    Button buttonWoche;
    Button buttonMonat;
    Button buttonJahr;
    TextView textViewTitel;
    TextView textViewZeitraum;
    LinkedHashMap<String, Double> statistikdaten; //statistikdaten mit zugehoerigem Datum
    double[] dstatistikdaten; //Werte (Double-Teil) der Hashmap statitkdaten
    String[] daten;//zeitdaten der statistikwerte
    String zeitraumbeginn = "";
    String zeitraumende = "";
    private int titel;
    private BarChart diagramm;
    private int verschiebung = 0; //zeitliche Verschiebung der angezeigten Stats von aktueller Zeit
    private int zeitraum = 0; //ausgewaehlter Zeitraum: Woche=0 Monat=1 Jahr=2
    private int statistikart = 0; //ausgwaehlte Ereignisart: Strecken=0 Treibstoff=1 Tankkosten=2 Co2=3

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stats, container, false);
        diagramm = root.findViewById(R.id.fragment_verticalbarchart_chart);

        imageButtonStrecken = root.findViewById(R.id.imageButtonStrecken);
        imageButtonTreibstoff = root.findViewById(R.id.imageButtonTreibstoff);
        imageButtonTankkosten = root.findViewById(R.id.imageButtonTankkosten);
        imageButtonCO2 = root.findViewById(R.id.imageButtonCO2);

        buttonFrueher = root.findViewById(R.id.buttonFrueher);
        buttonSpaeter = root.findViewById(R.id.buttonSpaeter);
        buttonWoche = root.findViewById(R.id.buttonWoche);
        buttonMonat = root.findViewById(R.id.buttonMonat);
        buttonJahr = root.findViewById(R.id.buttonJahr);

        textViewTitel = root.findViewById(R.id.textViewTitel);
        textViewZeitraum = root.findViewById(R.id.textViewZeitraum);

        //setListener
        imageButtonStrecken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatistikart(0);
                baueDiagramm();
                setStatistikartButtonFarbe();
            }
        });
        imageButtonTreibstoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatistikart(1);
                baueDiagramm();
                setStatistikartButtonFarbe();
            }
        });
        imageButtonTankkosten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatistikart(2);
                baueDiagramm();
                setStatistikartButtonFarbe();
            }
        });
        imageButtonCO2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatistikart(3);
                baueDiagramm();
                setStatistikartButtonFarbe();
            }
        });
        buttonFrueher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVerschiebung(-1);
                baueDiagramm();
            }
        });
        buttonSpaeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVerschiebung(1);
                baueDiagramm();
            }
        });
        buttonWoche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setZeitraum(0);
                baueDiagramm();
                diagramm.fitScreen();
                setZeitraumButtonFarbe();
            }
        });
        buttonMonat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setZeitraum(1);
                baueDiagramm();
                diagramm.fitScreen();
                setZeitraumButtonFarbe();
            }
        });
        buttonJahr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setZeitraum(2);
                baueDiagramm();
                diagramm.fitScreen();
                setZeitraumButtonFarbe();

            }
        });
        setStatistikartButtonFarbe();
        setZeitraumButtonFarbe();
        baueDiagramm();
        return root;
    }

    /**
     * Baut das Diagramm: erstellt Diagrammdaten, gestaltet das Diagrammaussehen und erstellt endgueltig das Diagramm
     */
    private void baueDiagramm() {
        BarData data = erstelleDiagrammdaten();
        gestalteDiagrammAussehen();
        bereiteDiagrammdaten(data);

        textViewTitel.setText(titel);
        // laut Duden ohne Leerzeichen
        textViewZeitraum.setText(zeitraumbeginn + "–" + zeitraumende);
    }

    /**
     * Erstellt die Achsenbestriftung der X-Achse
     *
     * @return Achsenbestriftung der X-Achse
     */
    private String[] erstelleXachse_Beschriftung() {
        String[] x_beschriftung = new String[daten.length];
        zeitraumbeginn = daten[0];
        zeitraumende = daten[daten.length - 1];
        x_beschriftung = daten;
        Date dateZeitraumende = null; //letztes uebergebene Datum
        Date dateZeitraumbeginn = null;//erstes uebergebene Datum

        /*letztes einbezogene Datum des Zeitraums z.B 1 Woche nach dem letzten Datum bei Zeitraum Monat,
        da ja das Anfangsdatum des Zeitraums uebergeben wird*/
        Date dateEndeZeitraumende = null;

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            dateZeitraumende = formatter.parse(zeitraumende);
            dateZeitraumbeginn = formatter.parse(zeitraumbeginn);


        } catch (ParseException e) {
            System.out.println("EXCEPTION");
            e.printStackTrace();
        }

        int sechstage_ms = 518400000;
        //long dreiwochen_ms = Math.multiplyExact((long) 86400000, 7 * 3);


        switch (zeitraum) {
            case 0: //woche
                formatter = new SimpleDateFormat("dd.MM.");
                break;
            case 1://monat
                SimpleDateFormat formattermonat = new SimpleDateFormat("dd.MM.yyyy");
                dateZeitraumende.setTime(dateZeitraumende.getTime() + sechstage_ms);
                dateEndeZeitraumende = dateZeitraumende;
                zeitraumende = formattermonat.format(dateEndeZeitraumende);
                zeitraumbeginn = formattermonat.format(dateZeitraumbeginn);
                break;
            case 2://jahr
                formatter = new SimpleDateFormat("dd.MM.");

                //dateZeitraumende.setTime(dateZeitraumende.getTime() + dreiwochen_ms + sechstage_ms); //ein Tag weniger da letzter und erster Tag inklusiv ist
                dateEndeZeitraumende = dateZeitraumende;

                Calendar calZeitraumbeginn = Calendar.getInstance();
                calZeitraumbeginn.setTime(dateZeitraumbeginn);
                calZeitraumbeginn.set(Calendar.DAY_OF_MONTH, 1);
                dateZeitraumbeginn = calZeitraumbeginn.getTime();

                SimpleDateFormat formatterZeitraumende = new SimpleDateFormat("dd.MM.yyyy");
                zeitraumende = formatterZeitraumende.format(dateEndeZeitraumende);
                zeitraumbeginn = formatterZeitraumende.format(dateZeitraumbeginn);
                break;

        }

        // Spaeter-Button deaktivieren, wenn heute = Zeitraumende ist (Weil es keine Statistik fuer die Zukunft gibt)
        Calendar heute = Calendar.getInstance();
        Calendar calZeitraumende = Calendar.getInstance();
        calZeitraumende.setTime(dateZeitraumende);

        boolean spaeterButtonSichtbar =
                   heute.get(Calendar.DATE) != calZeitraumende.get(Calendar.DATE)
                || heute.get(Calendar.MONTH) != calZeitraumende.get(Calendar.MONTH)
                || heute.get(Calendar.YEAR) != calZeitraumende.get(Calendar.YEAR);

        if (spaeterButtonSichtbar) {
            buttonSpaeter.setVisibility(View.VISIBLE);
        } else {
            buttonSpaeter.setVisibility(View.INVISIBLE);
        }

        x_beschriftung = daten.clone();
        SimpleDateFormat formatterparse = new SimpleDateFormat("dd.MM.yyyy");
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez"};
        String sdatum;
        for (int i = 0; i < daten.length; i++) {
            try {
                Date ddatum = formatterparse.parse(x_beschriftung[i]);
                if (zeitraum == 2) {
                    Calendar calDatum = Calendar.getInstance();
                    calDatum.setTime(ddatum);
                    sdatum = monthNames[calDatum.get(Calendar.MONTH)];
                } else {
                    sdatum = formatter.format(ddatum);
                }
                x_beschriftung[i] = sdatum;

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return x_beschriftung;
    }

    /**
     * konfiguriert das Aussehen des Diagramms
     */
    private void gestalteDiagrammAussehen() {
        diagramm.getDescription().setEnabled(false);
        diagramm.setDrawValueAboveBar(true);
        diagramm.getLegend().setEnabled(false);
        //chart.getAxisRight().setEnabled(false);

        XAxis xAchse = diagramm.getXAxis();
        xAchse.setGranularity(1f);
        xAchse.setLabelCount(12);
        xAchse.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAchse.setValueFormatter(new IndexAxisValueFormatter(erstelleXachse_Beschriftung()));

        YAxis axeLinks = diagramm.getAxisLeft();
        axeLinks.setGranularity(1f);
        axeLinks.setAxisMinimum(0);

        YAxis axeRechts = diagramm.getAxisRight();
        axeRechts.setGranularity(1f);
        axeRechts.setAxisMinimum(0);
    }

    /**
     * Bereitet die Diagrammdaten aus den uebergebenen Werten auf
     *
     * @return Diagrammdaten, womit dann das Diagramm erstellt werden kann
     */
    private BarData erstelleDiagrammdaten() {

        Garage garage = MainActivity.getGarage();
        Fahrzeug aktuellesFahrzeug = garage.getAusgewaehltesFahrzeug();

        if (statistikdaten != null) {
            statistikdaten.clear();
        }
        switch (statistikart) {

            case 0: //Strecken
                titel = R.string.gefahrene_distanz_km;
                switch (zeitraum) {
                    case 0: //Woche in 7 Tagen
                        statistikdaten = aktuellesFahrzeug.getWocheStreckenStatistik(verschiebung);
                        //statistikdaten= new double[]{10, 20, 5, 10, 30, 1,6}; //Testdaten
                        break;
                    case 1: //Monat in 4 Monatsquartalen
                        statistikdaten = aktuellesFahrzeug.getMonatStreckenStatistik(verschiebung);
                        //statistikdaten= new double[]{40,50,80,10};//Testdaten
                        break;
                    case 2: //Jahr in 12 Monaten
                        statistikdaten = aktuellesFahrzeug.getJahrStreckenStatistik(verschiebung);
                        //statistikdaten= new double[]{1000,2200,100,0,122,100,4000,5000,10,1000,2000,200};//Testdaten
                        break;
                }

                break;
            case 1: // Treibstoffverbrauch
                if (aktuellesFahrzeug.isElektro()) {
                    titel = R.string.kraftstoffverbrauch_kwh;
                } else {
                    titel = R.string.kraftstoffverbrauch_l;
                }
                switch (zeitraum) {
                    case 0: //Woche in 7 Tagen
                        statistikdaten = aktuellesFahrzeug.getWocheTreibstoffStatistik(verschiebung);
                        break;
                    case 1://Monat in 4 Monatsquartalen
                        statistikdaten = aktuellesFahrzeug.getMonatTreibstoffStatistik(verschiebung);
                        break;
                    case 2://Jahr in 12 Monaten
                        statistikdaten = aktuellesFahrzeug.getJahrTreibstoffStatistik(verschiebung);
                        break;
                }

                break;
            case 2: //Tankkosten
                if (aktuellesFahrzeug.isElektro()) {
                    titel = R.string.kraftstoffkosten_euro_elektro;
                } else {
                    titel = R.string.kraftstoffkosten_euro;
                }
                switch (zeitraum) {
                    case 0: //Woche in 7 Tagen
                        statistikdaten = aktuellesFahrzeug.getWocheTankkostenStatistik(verschiebung);

                        break;
                    case 1://Monat in 4 Monatsquartalen
                        statistikdaten = aktuellesFahrzeug.getMonatTankkostenStatistik(verschiebung);

                        break;
                    case 2://Jahr in 12 Monaten
                        statistikdaten = aktuellesFahrzeug.getJahrTankkostenStatistik(verschiebung);

                        break;
                }

                break;
            case 3: //CO2
                titel = R.string.co2_ausstoss_g;
                switch (zeitraum) {
                    case 0://Woche in 7 Tagen
                        statistikdaten = aktuellesFahrzeug.getWocheCO2Statistik(verschiebung);
                        break;
                    case 1://Monat in 4 Monatsquartalen
                        statistikdaten = aktuellesFahrzeug.getMonatCO2Statistik(verschiebung);

                        break;
                    case 2://Jahr in 12 Monaten
                        statistikdaten = aktuellesFahrzeug.getJahrCO2Statistik(verschiebung);
                        break;
                }
                break;
        }

        Set<String> setKeys = statistikdaten.keySet();

        String[] zeitdaten = setKeys.toArray(new String[0]);
        daten = zeitdaten;

        Collection<Double> dvalues = statistikdaten.values();

        Double[] arrayValues = dvalues.toArray(new Double[0]);
        dstatistikdaten = Stream.of(arrayValues).mapToDouble(Double::doubleValue).toArray();


        dreheStatistikdaten();
        drehexAchsedaten();

        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < dstatistikdaten.length; i++) {
            float x = i;
            float y = (float) dstatistikdaten[i];
            values.add(new BarEntry(x, y));
        }


        BarDataSet set1 = new BarDataSet(values, setLabel); //obligatorisch aber nicht angezeigt
        set1.setValueFormatter(new ForecastValueFormatter());

        // Styling
        set1.setColors(
                ContextCompat.getColor(diagramm.getContext(), R.color.blau_1),
                ContextCompat.getColor(diagramm.getContext(), R.color.blau_1),
                ContextCompat.getColor(diagramm.getContext(), R.color.blau_1),
                ContextCompat.getColor(diagramm.getContext(), R.color.blau_1)
        );

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        return new BarData(dataSets);
    }

    /**
     * Fuegt die uebergebenen Daten dem Diagramm hinzu und zeichnet es
     *
     * @param data Erstellte Diagrammdaten
     */
    private void bereiteDiagrammdaten(BarData data) {
        data.setValueTextSize(12f);
        diagramm.setData(data);
        diagramm.invalidate();
    }

    /**
     * Setzt den ausgewaehlten Zeitraum (Woche, Monat, Jahr)
     *
     * @param pzeitraum Zeitraum, der ausgewaehlt und angezeigt werde soll
     */
    public void setZeitraum(int pzeitraum) {
        zeitraum = pzeitraum;
    }

    /**
     * Setzt die ausgewaehlte Verschiebung -1 ist eine Einheit (Woche, Monat, Jahr) frueher, +1 eine spaeter
     *
     * @param pverschiebung zeitliche Verschiebung von der aktuellen Ansicht um eine Einheit
     */
    public void setVerschiebung(int pverschiebung) {
        verschiebung += pverschiebung;
    }

    /**
     * Setzt die ausgewaehlte Statistikart (Strecken=0 Treibstoff=1 Tankkosten=2 CO2=3)
     *
     * @param pstatistikart Art der Statistik
     */
    public void setStatistikart(int pstatistikart) {
        statistikart = pstatistikart;
    }

    /**
     * Verwaltet die Hervorhebungen der Statistikartbuttons, je nach dem welche Statistikart ausgewaehlt wurde
     */
    public void setStatistikartButtonFarbe() {

        switch (statistikart) {
            case 0:
                Paris.style(imageButtonStrecken).apply(R.style.Theme_Tankauswertung_ImageButtonSelected);
                Paris.style(imageButtonTreibstoff).apply(R.style.Theme_Tankauswertung_ImageButton);
                Paris.style(imageButtonTankkosten).apply(R.style.Theme_Tankauswertung_ImageButton);
                Paris.style(imageButtonCO2).apply(R.style.Theme_Tankauswertung_ImageButton);
                break;
            case 1:
                Paris.style(imageButtonStrecken).apply(R.style.Theme_Tankauswertung_ImageButton);
                Paris.style(imageButtonTreibstoff).apply(R.style.Theme_Tankauswertung_ImageButtonSelected);
                Paris.style(imageButtonTankkosten).apply(R.style.Theme_Tankauswertung_ImageButton);
                Paris.style(imageButtonCO2).apply(R.style.Theme_Tankauswertung_ImageButton);
                break;
            case 2:
                Paris.style(imageButtonStrecken).apply(R.style.Theme_Tankauswertung_ImageButton);
                Paris.style(imageButtonTreibstoff).apply(R.style.Theme_Tankauswertung_ImageButton);
                Paris.style(imageButtonTankkosten).apply(R.style.Theme_Tankauswertung_ImageButtonSelected);
                Paris.style(imageButtonCO2).apply(R.style.Theme_Tankauswertung_ImageButton);
                break;
            case 3:
                Paris.style(imageButtonStrecken).apply(R.style.Theme_Tankauswertung_ImageButton);
                Paris.style(imageButtonTreibstoff).apply(R.style.Theme_Tankauswertung_ImageButton);
                Paris.style(imageButtonTankkosten).apply(R.style.Theme_Tankauswertung_ImageButton);
                Paris.style(imageButtonCO2).apply(R.style.Theme_Tankauswertung_ImageButtonSelected);
                break;
        }

    }

    /**
     * Verwaltet die Hervorhebungen der Zeitraumbuttons, je nach dem welcher Zeitraum ausgewaehlt wurde
     */
    public void setZeitraumButtonFarbe() {

        switch (zeitraum) {
            case 0:
                Paris.style(buttonWoche).apply(R.style.Theme_Tankauswertung_ButtonSelected);
                Paris.style(buttonMonat).apply(R.style.Theme_Tankauswertung_Button);
                Paris.style(buttonJahr).apply(R.style.Theme_Tankauswertung_Button);
                break;
            case 1:
                Paris.style(buttonWoche).apply(R.style.Theme_Tankauswertung_Button);
                Paris.style(buttonMonat).apply(R.style.Theme_Tankauswertung_ButtonSelected);
                Paris.style(buttonJahr).apply(R.style.Theme_Tankauswertung_Button);
                break;
            case 2:
                Paris.style(buttonWoche).apply(R.style.Theme_Tankauswertung_Button);
                Paris.style(buttonMonat).apply(R.style.Theme_Tankauswertung_Button);
                Paris.style(buttonJahr).apply(R.style.Theme_Tankauswertung_ButtonSelected);
                break;
        }

    }


    /**
     * Dreht das Array um, sodass der erste Eintrag genau dem aeltesten entspricht
     */
    private void dreheStatistikdaten() {
        for (int i = 0; i < dstatistikdaten.length / 2; i++) {
            double temp = dstatistikdaten[i];
            dstatistikdaten[i] = dstatistikdaten[dstatistikdaten.length - i - 1];
            dstatistikdaten[dstatistikdaten.length - i - 1] = temp;
        }
    }

    /**
     * Dreht das Array um, sodass der erste Eintrag genau dem aeltesten entspricht
     */
    private void drehexAchsedaten() {
        for (int i = 0; i < daten.length / 2; i++) {
            String temp = daten[i];
            daten[i] = daten[daten.length - i - 1];
            daten[daten.length - i - 1] = temp;
        }

    }
}