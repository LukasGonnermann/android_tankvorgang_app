package com.example.tankauswertung.ui.timeline;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
//import com.github.vipulasri.timelineview.TimelineView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TimelineFragment extends Fragment {

    private View root;
    private boolean fabOpen = false;

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

        // keine Fahrzeuge vorhanden, Fall sollte nicht auftreten
        if (garage.isEmpty()) {
            return;
        }

        TextView textView = root.findViewById(R.id.textViewTimelineLeer);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

        // FABs
        FloatingActionButton floatingActionButton = root.findViewById(R.id.floatingActionButton);
        FloatingActionButton floatingActionButtonStrecke = root.findViewById(R.id.floatingActionButtonStrecke);
        FloatingActionButton floatingActionButtonTankvorgang = root.findViewById(R.id.floatingActionButtonTankvorgang);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabOpen) {
                    floatingActionButtonStrecke.animate().translationY(0);
                    floatingActionButtonTankvorgang.animate().translationY(0);
                    fabOpen = false;
                } else {
                    floatingActionButtonStrecke.animate().translationY(-getResources().getDimension(R.dimen.herausfahrenFab1));
                    floatingActionButtonTankvorgang.animate().translationY(-getResources().getDimension(R.dimen.herausfahrenFab2));
                    fabOpen = true;
                }
            }
        });


        // testweise
        aktuellesFahrzeug.streckeHinzufuegen(0, Strecke.Streckentyp.AUSSERORTS, 0);

        if (aktuellesFahrzeug.getEreignisse().size() == 0) {
            textView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        } else {

            textView.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(new TimelineAdapter(aktuellesFahrzeug.getEreignisse()));
        }

    }
}
