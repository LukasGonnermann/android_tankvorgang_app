package com.example.tankauswertung.ui.dashboard;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;


import com.example.tankauswertung.Fahrzeug;
import com.example.tankauswertung.Garage;
import com.example.tankauswertung.MainActivity;

import com.example.tankauswertung.R;

public class DashboardFragment extends Fragment {

    Garage garage;
    TextView textViewName;
    TextView textViewAktuellerTankstand;
    TextView textViewKilometerstand;
    TextView textViewVerbrauch;
    TextView textViewReichweite;
    TextView textViewCo2;

    TextView labelAktuellerTankstandTitel;
    TextView labelKilometerstand;
    TextView labelVerbrauch;
    TextView labelCo2;
    TextView labelReichweite;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        textViewName = root.findViewById(R.id.TextViewName);
        textViewVerbrauch = root.findViewById(R.id.TextViewVerbrauch);
        textViewReichweite = root.findViewById(R.id.TextViewReichweite);
        textViewCo2 = root.findViewById(R.id.TextViewCo2);
        textViewKilometerstand = root.findViewById(R.id.TextViewKilometerstand);
        textViewAktuellerTankstand = root.findViewById(R.id.TextViewAktuellerTankstand);

        labelVerbrauch = root.findViewById(R.id.labelVerbrauch);
        labelCo2 = root.findViewById(R.id.labelCo2);
        labelKilometerstand = root.findViewById(R.id.labelKilometerstand);
        labelReichweite = root.findViewById(R.id.labelReichweite);
        labelAktuellerTankstandTitel = root.findViewById(R.id.labelAktuellerTankstandTitel);

        return root;
    }

    /**
     * immer ausgeführt, bevor das Fragment (der Tab) bedienbar ist (nach jedem Wechseln etc. etc.)
     */
    @Override
    public void onResume() {
        super.onResume();

        garage = MainActivity.getGarage();

        if (garage.isEmpty()) {
            // Ausser Name alle Elemente ausblenden
            textViewName.setText(R.string.no_cars_in_garage);
            textViewVerbrauch.setVisibility(View.INVISIBLE);
            textViewReichweite.setVisibility(View.INVISIBLE);
            textViewCo2.setVisibility(View.INVISIBLE);
            textViewKilometerstand.setVisibility(View.INVISIBLE);
            textViewAktuellerTankstand.setVisibility(View.INVISIBLE);

            labelReichweite.setVisibility(View.INVISIBLE);
            labelVerbrauch.setVisibility(View.INVISIBLE);
            labelCo2.setVisibility(View.INVISIBLE);
            labelKilometerstand.setVisibility(View.INVISIBLE);
            labelAktuellerTankstandTitel.setVisibility(View.INVISIBLE);
        } else {
            // Alle Attributwerte abfragen und in den TextViews darstellen

            Fahrzeug ausgewaehltesFahrzeug = garage.getAusgewaehltesFahrzeug();

            String name = ausgewaehltesFahrzeug.getName();
            String verbrauch = String.valueOf(ausgewaehltesFahrzeug.getVerbrauchInnerorts());
            String co2 = String.valueOf(ausgewaehltesFahrzeug.getCo2Ausstoss());
            String kilometerstand = String.valueOf(ausgewaehltesFahrzeug.getKmStand());
          //  String reichweite = String.valueOf(ausgewaehltesFahrzeug.getReichweite());
            String aktuellerTankstand = String.valueOf(ausgewaehltesFahrzeug.getTankstand());

            textViewName.setText(name);
            textViewVerbrauch.setText(verbrauch);
            textViewCo2.setText(co2);
            textViewKilometerstand.setText(kilometerstand);
           // textViewReichweite.setText(reichweite);
            textViewAktuellerTankstand.setText(aktuellerTankstand);

        }
    }

}

