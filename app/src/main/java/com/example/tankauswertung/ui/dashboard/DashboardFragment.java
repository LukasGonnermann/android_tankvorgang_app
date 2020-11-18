package com.example.tankauswertung.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tankauswertung.Fahrzeug;
import com.example.tankauswertung.Garage;
import com.example.tankauswertung.MainActivity;
import com.example.tankauswertung.R;

public class DashboardFragment extends Fragment {

    Garage garage;
    TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        textView = root.findViewById(R.id.text_dashboard);
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
            textView.setText(R.string.no_cars_in_garage);
        } else {

            Fahrzeug ausgewaehltesFahrzeug = garage.getAusgewaehltesFahrzeug();
            String msg = ausgewaehltesFahrzeug.getName() + "\n"
                    + ausgewaehltesFahrzeug.getVerbrauchInnerorts() + "\n"
                    + ausgewaehltesFahrzeug.getVerbrauchAusserorts() + "\n"
                    + ausgewaehltesFahrzeug.getVerbrauchKombiniert() + "\n"
                    + ausgewaehltesFahrzeug.getCo2Ausstoss() + "\n"
                    + ausgewaehltesFahrzeug.getKmStand() + "\n"
                    + ausgewaehltesFahrzeug.getTankgroesse() + "\n"
                    + ausgewaehltesFahrzeug.getTankstand() + "\n";
            textView.setText(msg);  // nur ein Test
        }

    }
}
