package com.example.tankauswertung.ui.timeline;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tankauswertung.Fahrzeug;
import com.example.tankauswertung.Garage;
import com.example.tankauswertung.MainActivity;
import com.example.tankauswertung.R;
import com.example.tankauswertung.Strecke;
import com.example.tankauswertung.exceptions.FahrzeugWertException;
import com.github.vipulasri.timelineview.TimelineView;

public class TimelineFragment extends Fragment {

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_timeline, container, false);
        return root;
    }

    /**
     * immer ausgeführt, bevor das Fragment (der Tab) bedienbar ist (nach jedem Wechseln etc. etc.)
     */
    @Override
    public void onResume() {
        super.onResume();

        Garage garage = MainActivity.getGarage();
        Fahrzeug aktuellesFahrzeug = garage.getAusgewaehltesFahrzeug();

        // testweise
        aktuellesFahrzeug.streckeHinzufuegen(0, Strecke.Streckentyp.AUSSERORTS, 0);

        if (garage.isEmpty()) {
            // TODO: error handling?
            return;
        }

        if (aktuellesFahrzeug.getEreignisse().size() == 0) {
            // TODO: Text anzeigen, dass Timeline leer
        } else {

            RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(new TimelineAdapter(aktuellesFahrzeug.getEreignisse()));
        }

    }
}
