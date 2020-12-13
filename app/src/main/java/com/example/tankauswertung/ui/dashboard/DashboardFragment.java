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
                labelVerbrauch.setText(R.string.durchschnitt_verbrauch_kwh_100km);
            }

            // anzuzeigende Werte setzen

            textViewName.setText(aktuellesFahrzeug.getName());
            textViewTankstand.setText(String.valueOf(aktuellesFahrzeug.getTankstand()));
            progressBarTankstand.setProgress((int) aktuellesFahrzeug.getTankstand());
            textViewKilometerstand.setText(String.valueOf(aktuellesFahrzeug.getKmStand()));

            textViewVerbrauch.setText(String.valueOf(aktuellesFahrzeug.getVerbrauchDurchschnittlich()));

            double reichweite = aktuellesFahrzeug.getReichweite();
            if (reichweite == -1) {
                textViewReichweite.setText(R.string.idle);
            } else {
                textViewReichweite.setText(String.valueOf(reichweite));
            }

            if (!aktuellesFahrzeug.isElektro()) {
                textViewCo2.setText(String.valueOf(aktuellesFahrzeug.getCo2AusstossGesamtKg()));
            }
        }
    }
}
