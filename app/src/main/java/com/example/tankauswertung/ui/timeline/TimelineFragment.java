package com.example.tankauswertung.ui.timeline;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tankauswertung.Fahrzeug;
import com.example.tankauswertung.Garage;
import com.example.tankauswertung.MainActivity;
import com.example.tankauswertung.NewStreckeActivity;
import com.example.tankauswertung.NewTankvorgangActivity;
import com.example.tankauswertung.R;
//import com.github.vipulasri.timelineview.TimelineView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TimelineFragment extends Fragment {

    private View root;
    private boolean fabOpen = false;

    private static final double DELTA_TANKSTAND_PROZENT = 0.01;

    public static final int LAUNCH_NEW_STRECKE_EDIT_STRECKE = 4;
    public static final int LAUNCH_NEW_TANKVORGANG_EDIT_TANKVORGANG = 5;

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

                if (aktuellesFahrzeug.getTankstand() < DELTA_TANKSTAND_PROZENT) {  // Tank leer / so gut wie leer

                    int title;
                    int msg;

                    if (aktuellesFahrzeug.isElektro()) {
                        title = R.string.tank_leer_title_elektro;
                        msg = R.string.tank_leer_msg_elektro;
                    } else {
                        title = R.string.tank_leer_title;
                        msg = R.string.tank_leer_msg;
                    }

                    AlertDialog dialogTankFastLeer = new AlertDialog.Builder(getContext())
                            .setTitle(title)
                            .setMessage(msg)
                            .setIcon(R.drawable.ic_baseline_local_gas_station_24)

                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int whichButton) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .create();
                    dialogTankFastLeer.show();

                } else {
                    Intent intent = new Intent(getContext(), NewStreckeActivity.class);
                    intent.setAction(ACTION_NEW_STRECKE);
                    startActivityForResult(intent, LAUNCH_NEW_STRECKE_EDIT_STRECKE);
                }
            }
        });

        floatingActionButtonTankvorgang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int title;
                int msg;

                if (aktuellesFahrzeug.isElektro()) {
                    title = R.string.tank_voll_title_elektro;
                    msg = R.string.tank_voll_msg_elektro;
                } else {
                    title = R.string.tank_voll_title;
                    msg = R.string.tank_voll_msg;
                }

                // Tank voll, kleineres Delta (0.001) damit voll auch wirklich voll bedeutet
                if (100 - aktuellesFahrzeug.getTankstand() < DELTA_TANKSTAND_PROZENT) {

                    AlertDialog dialogTankVoll = new AlertDialog.Builder(getContext())
                            .setTitle(title)
                            .setMessage(msg)
                            .setIcon(R.drawable.ic_baseline_local_gas_station_24)

                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int whichButton) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .create();
                    dialogTankVoll.show();

                } else {
                    Intent intent = new Intent(getContext(), NewTankvorgangActivity.class);
                    intent.setAction(ACTION_NEW_TANKVORGANG);
                    startActivityForResult(intent, LAUNCH_NEW_TANKVORGANG_EDIT_TANKVORGANG);
                }
            }
        });

        // Placeholder einblenden, falls keine Strecken oder Tankvorgänge vorhanden
        if (aktuellesFahrzeug.getEreignisse().size() == 0) {
            if (aktuellesFahrzeug.isElektro()) {
                textView.setText(R.string.ereignisseLeerElektro);
            }
            textView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        } else {

            textView.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(new TimelineAdapter(aktuellesFahrzeug.getEreignisse(), getContext()));
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

        // Hinweis: ACTION_EDIT_STRECKE kann nicht abgefangen werden, da von TimelineAdapter aus
        //          aufgerufen

        if (requestCode == LAUNCH_NEW_STRECKE_EDIT_STRECKE) {

            if (intent.getAction().equals(ACTION_NEW_STRECKE)) {
                if (resultCode == Activity.RESULT_OK) {
                    // alles ok
                }
            }

        } else if (requestCode == LAUNCH_NEW_TANKVORGANG_EDIT_TANKVORGANG) {

            if (intent.getAction().equals(ACTION_NEW_TANKVORGANG)) {
                if (resultCode == Activity.RESULT_OK) {
                    // alles ok
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }
}
