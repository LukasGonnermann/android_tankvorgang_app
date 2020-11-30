package com.example.tankauswertung.ui.timeline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tankauswertung.Fahrzeug;
import com.example.tankauswertung.Garage;
import com.example.tankauswertung.MainActivity;
import com.example.tankauswertung.NewCarActivity;
import com.example.tankauswertung.NewStreckeActivity;
import com.example.tankauswertung.R;
import com.example.tankauswertung.Strecke;
import com.example.tankauswertung.exceptions.FahrzeugWertException;
//import com.github.vipulasri.timelineview.TimelineView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TimelineFragment extends Fragment {

    private View root;
    private boolean fabOpen = false;

    final int LAUNCH_NEW_STRECKE_EDIT_STRECKE = 4;
    final int LAUNCH_NEW_TANKVORGANG_EDIT_TANKVORGANG = 5;

    // Activity action codes ("What should be done in the activity?")
    // Wir nutzen die gleiche Aktivität zum Hinzufügen und Bearbeiten eines Autos, deshalb müssen
    // wir mit intent.setAction(...) dazwischen differenzieren.
    // (Request-Codes kann man leider in einer Aktivität nicht gut abfragen.)
    public final static String ACTION_NEW_STRECKE = "new_strecke";
    public final static String ACTION_EDIT_STRECKE = "edit_strecke";
    public final static String ACTION_NEW_TANKVORGANG = "new_tankvorgang";
    public final static String ACTION_EDIT_TANKVORGANG = "edit_tankvorgang";

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

        floatingActionButtonStrecke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewStreckeActivity.class);
                intent.setAction(ACTION_NEW_STRECKE);
                startActivityForResult(intent, LAUNCH_NEW_STRECKE_EDIT_STRECKE);
            }
        });

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

    /**
     * hier bekommen wir unsere Ergebnisse, nachdem eine Aktivität geschlossen wurde
     * @param requestCode Code, mit dem die Aktivität aufgerufen wurde (z. B. LAUNCH_NEW_CAR)
     *                    (siehe auch https://stackoverflow.com/a/10407371)
     * @param resultCode Ergebniscode
     * @param intent zurückgegebener Intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // Auto hinzugefügt oder geändert
        if (requestCode == LAUNCH_NEW_STRECKE_EDIT_STRECKE) {

                if (intent.getAction().equals(ACTION_NEW_STRECKE)) {  // Strecke hinzugefügt
                if (resultCode == Activity.RESULT_OK) {
                    // TODO: testweise
                    Toast toast = Toast.makeText(getContext(), "hinzugefügt", Toast.LENGTH_LONG);
                    toast.show();
                }
            } else if (intent.getAction().equals(ACTION_EDIT_STRECKE)) {  // Auto geändert
                if (resultCode == Activity.RESULT_OK) {
                    Toast toast = Toast.makeText(getContext(), "geändert", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }
}
