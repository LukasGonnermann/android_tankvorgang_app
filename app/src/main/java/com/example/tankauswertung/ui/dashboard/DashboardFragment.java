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
    TextView textViewVerbrauchInnerortsStand;
    TextView textViewVerbrauchAusserortsStand;
    TextView textViewVerbrauchKombiniertStand;
    TextView textViewco2;
    TextView textViewKilometerstand;
    TextView textViewTankvolumen;
    TextView textViewAktuellerTankstand;

    CheckBox viewElektro;

    TextView labelVerbrauchInnerortsTitel;
    TextView labelVerbrauchAusserortsTitel;
    TextView labelVerbrauchKombiniertTitel;
    TextView labelCo2Ausstoss;
    TextView labelKilometerstand;
    TextView labelTankvolumen;
    TextView labelAktuellerTankstandTitel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        textViewName = root.findViewById(R.id.TextViewName);
        textViewVerbrauchInnerortsStand = root.findViewById(R.id.TextViewVerbrauchInnerortsStand);
        textViewVerbrauchAusserortsStand = root.findViewById(R.id.TextViewVerbrauchAusserortsStand);
        textViewVerbrauchKombiniertStand = root.findViewById(R.id.TextViewVerbrauchKombiniertStand);
        textViewco2 = root.findViewById(R.id.TextViewCo2);
        textViewKilometerstand = root.findViewById(R.id.TextViewKilometerstand);
        textViewTankvolumen = root.findViewById(R.id.TextViewTankvolumen);
        textViewAktuellerTankstand = root.findViewById(R.id.TextViewAktuellerTankstandStand);

        viewElektro = root.findViewById(R.id.ViewElektro);

        labelVerbrauchInnerortsTitel = root.findViewById(R.id.labelVerbrauchInnerortsTitel);
        labelVerbrauchAusserortsTitel = root.findViewById(R.id.labelVerbrauchAusserortsTitel);
        labelVerbrauchKombiniertTitel = root.findViewById(R.id.labelVerbrauchKombiniertTitel);
        labelCo2Ausstoss = root.findViewById(R.id.labelCo2Ausstoss);
        labelKilometerstand = root.findViewById(R.id.labelKilometerstand);
        labelTankvolumen = root.findViewById(R.id.labelTankvolumen);
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
            textViewVerbrauchInnerortsStand.setVisibility(View.INVISIBLE);
            textViewVerbrauchAusserortsStand.setVisibility(View.INVISIBLE);
            textViewVerbrauchKombiniertStand.setVisibility(View.INVISIBLE);
            textViewco2.setVisibility(View.INVISIBLE);
            textViewKilometerstand.setVisibility(View.INVISIBLE);
            textViewTankvolumen.setVisibility(View.INVISIBLE);
            textViewAktuellerTankstand.setVisibility(View.INVISIBLE);
            viewElektro.setVisibility(View.INVISIBLE);
            labelVerbrauchInnerortsTitel.setVisibility(View.INVISIBLE);
            labelVerbrauchAusserortsTitel.setVisibility(View.INVISIBLE);
            labelVerbrauchKombiniertTitel.setVisibility(View.INVISIBLE);
            labelCo2Ausstoss.setVisibility(View.INVISIBLE);
            labelKilometerstand.setVisibility(View.INVISIBLE);
            labelTankvolumen.setVisibility(View.INVISIBLE);
            labelAktuellerTankstandTitel.setVisibility(View.INVISIBLE);
        } else {
            // Alle Attributwerte abfragen und in den TextViews darstellen

            Fahrzeug ausgewaehltesFahrzeug = garage.getAusgewaehltesFahrzeug();

            String name = ausgewaehltesFahrzeug.getName();
            boolean isElektro = ausgewaehltesFahrzeug.isElektro();
            String verbrauchInnerorts = String.valueOf(ausgewaehltesFahrzeug.getVerbrauchInnerorts());
            String verbrauchAusserorts = String.valueOf(ausgewaehltesFahrzeug.getVerbrauchAusserorts());
            String verbrauchKombiniert = String.valueOf(ausgewaehltesFahrzeug.getVerbrauchKombiniert());
            String co2 = String.valueOf(ausgewaehltesFahrzeug.getCo2Ausstoss());
            String kilometerstand = String.valueOf(ausgewaehltesFahrzeug.getKmStand());
            String tankvolumen = String.valueOf(ausgewaehltesFahrzeug.getTankgroesse());
            String aktuellerTankstand = String.valueOf(ausgewaehltesFahrzeug.getTankstand());

            textViewName.setText(name);
            viewElektro.setChecked(isElektro);
            textViewVerbrauchInnerortsStand.setText(verbrauchInnerorts);
            textViewVerbrauchAusserortsStand.setText(verbrauchAusserorts);
            textViewVerbrauchKombiniertStand.setText(verbrauchKombiniert);
            textViewco2.setText(co2);
            textViewKilometerstand.setText(kilometerstand);
            textViewTankvolumen.setText(tankvolumen);
            textViewAktuellerTankstand.setText(aktuellerTankstand);

            if (isElektro) {
                // Labels fuer E-Auto anpassen
                labelVerbrauchInnerortsTitel.setText(R.string.verbrauch_innerorts_kwh_100km);
                labelVerbrauchAusserortsTitel.setText(R.string.verbrauch_au_erorts_kwh_100km);
                labelVerbrauchKombiniertTitel.setText(R.string.verbrauch_kombiniert_kwh_100km);
                labelTankvolumen.setText(R.string.tankvolumen_kwh);
                labelAktuellerTankstandTitel.setText(R.string.tankstand_kwh);
            }
        }

    }


}
