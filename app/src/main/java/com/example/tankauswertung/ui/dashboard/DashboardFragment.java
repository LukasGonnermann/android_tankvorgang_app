package com.example.tankauswertung.ui.dashboard;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;


import com.example.tankauswertung.Fahrzeug;
import com.example.tankauswertung.Garage;
import com.example.tankauswertung.MainActivity;

import com.example.tankauswertung.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    private View root;

    Garage garage;

    ScrollView scrollViewDashboard;

    TextView labelTankstand;
    TextView labelKilometerstand;
    TextView labelVerbrauch;
    TextView labelReichweite;
    TextView labelCo2;

    ProgressBar progressBarTankstand;
    TextView textViewGarageLeer;
    TextView textViewName;
    TextView textViewTankstand;
    TextView textViewKilometerstand;
    TextView textViewVerbrauch;
    TextView textViewReichweite;
    TextView textViewCo2;

    DecimalFormat dfProzent = new DecimalFormat("# '%'", new DecimalFormatSymbols(Locale.GERMAN));
    DecimalFormat dfKmStand = new DecimalFormat("# km", new DecimalFormatSymbols(Locale.GERMAN));
    DecimalFormat dfVerbrauch = new DecimalFormat("#.# l", new DecimalFormatSymbols(Locale.GERMAN));
    DecimalFormat dfVerbrauchElektro = new DecimalFormat("#.# kWh", new DecimalFormatSymbols(Locale.GERMAN));
    DecimalFormat dfReichweite = new DecimalFormat("#.# km", new DecimalFormatSymbols(Locale.GERMAN));
    DecimalFormat dfCo2 = new DecimalFormat("#.# kg", new DecimalFormatSymbols(Locale.GERMAN));

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    /**
     * immer ausgeführt, bevor das Fragment (der Tab) bedienbar ist (nach jedem Wechseln etc. etc.)
     */
    @Override
    public void onResume() {
        super.onResume();
        garage = MainActivity.getGarage();

        scrollViewDashboard = root.findViewById(R.id.scrollViewDashboard);
        textViewGarageLeer = root.findViewById(R.id.textViewGarageLeer);

        labelTankstand = root.findViewById(R.id.labelAktuellerTankstand);
        labelKilometerstand = root.findViewById(R.id.labelKilometerstand);
        labelVerbrauch = root.findViewById(R.id.labelVerbrauch);
        labelReichweite = root.findViewById(R.id.labelReichweite);
        labelCo2 = root.findViewById(R.id.labelCo2);

        progressBarTankstand = root.findViewById(R.id.progressBarTankstand);
        textViewName = root.findViewById(R.id.textViewName);
        textViewTankstand = root.findViewById(R.id.textViewAktuellerTankstand);
        textViewKilometerstand = root.findViewById(R.id.textViewKilometerstand);
        textViewVerbrauch = root.findViewById(R.id.textViewVerbrauch);
        textViewReichweite = root.findViewById(R.id.textViewReichweite);
        textViewCo2 = root.findViewById(R.id.textViewCo2);

        if (garage.isEmpty()) {

            // alles ausblenden, Placeholder einblenden
            scrollViewDashboard.setVisibility(View.GONE);
            textViewGarageLeer.setVisibility(View.VISIBLE);

        } else {

            Fahrzeug aktuellesFahrzeug = garage.getAusgewaehltesFahrzeug();

            // Label für Tankstand und Verbrauch bei Elektroauto ändern
            if (aktuellesFahrzeug.isElektro()) {
                labelTankstand.setText(R.string.tankstand_kwh);

                textViewVerbrauch.setText(dfVerbrauchElektro.format(aktuellesFahrzeug.getVerbrauchDurchschnittlich()));
            } else {
                textViewVerbrauch.setText(dfVerbrauch.format(aktuellesFahrzeug.getVerbrauchDurchschnittlich()));
            }

            // anzuzeigende Werte setzen

            textViewName.setText(aktuellesFahrzeug.getName());
            textViewTankstand.setText(dfProzent.format(aktuellesFahrzeug.getTankstand()));
            progressBarTankstand.setProgress((int) aktuellesFahrzeug.getTankstand());
            textViewKilometerstand.setText(dfKmStand.format(aktuellesFahrzeug.getKmStand()));

            double reichweite = aktuellesFahrzeug.getReichweite();
            if (reichweite == -1) {
                textViewReichweite.setText(R.string.idle);
            } else {
                textViewReichweite.setText(dfReichweite.format(reichweite));
            }

            if (!aktuellesFahrzeug.isElektro()) {
                textViewCo2.setText(dfCo2.format(aktuellesFahrzeug.getCo2AusstossGesamtKg()));
            }
        }
    }
}
