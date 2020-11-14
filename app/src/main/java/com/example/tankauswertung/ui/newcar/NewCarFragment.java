package com.example.tankauswertung.ui.newcar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.tankauswertung.Fahrzeug;
import com.example.tankauswertung.Garage;
import com.example.tankauswertung.MainActivity;
import com.example.tankauswertung.R;
import com.example.tankauswertung.exceptions.GarageVollException;

public class NewCarFragment extends Fragment {

    private NewCarViewModel newcarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        newcarViewModel =
                new ViewModelProvider(this).get(NewCarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_newcar, container, false);

        EditText editTextName = root.findViewById(R.id.editTextName);
        EditText editTextVerbrauchInnerorts = root.findViewById(R.id.editTextVerbrauchInnerorts);
        EditText editTextVerbrauchAusserorts = root.findViewById(R.id.editTextVerbrauchAusserorts);
        EditText editTextNameVerbrauchKombi = root.findViewById(R.id.editTextVerbrauchKombi);
        EditText editTextKilometerstand = root.findViewById(R.id.editTextKilometerstand);
        EditText editTextTankstand = root.findViewById(R.id.editTextTankstand);
        EditText editTextTankvolumen = root.findViewById(R.id.editTextTankvolumen);
        EditText editTextCO2 = root.findViewById(R.id.editTextCO2);
        Button buttonAbbrechen = root.findViewById(R.id.buttonAbbrechen);
        Button buttonFahrzeugAnlegen = root.findViewById(R.id.buttonFahzeugAnlegen);

        buttonFahrzeugAnlegen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: prüfen ob Felder leer sind bzw. alternativ leere mit 0 oder "" etc. übernehmen
                String name = editTextName.getText().toString();
                double verbrauch_innerorts = Double.parseDouble(editTextVerbrauchInnerorts.getText().toString());
                double verbrauch_ausserorts = Double.parseDouble(editTextVerbrauchAusserorts.getText().toString());
                double verbrauch_kombi = Double.parseDouble(editTextNameVerbrauchKombi.getText().toString());
                double km_stand = Double.parseDouble(editTextKilometerstand.getText().toString());
                int tankstand = Integer.parseInt(editTextTankstand.getText().toString());
                double tankvolumen = Double.parseDouble(editTextTankvolumen.getText().toString());
                double co2 = Double.parseDouble(editTextCO2.getText().toString());

                Fahrzeug neues_fahrzeug = new Fahrzeug(name, false, verbrauch_ausserorts, verbrauch_innerorts
                        , verbrauch_kombi, km_stand, tankstand, co2, tankvolumen);

                Garage garage = MainActivity.getGarage();
                try {
                    garage.fahrzeugHinzufuegen(neues_fahrzeug);
                    Toast.makeText(getActivity(), "Fahrzeug wurde erfolgreich erstellt", Toast.LENGTH_LONG).show();
                } catch (GarageVollException e) {
                    Toast.makeText(getActivity(), "Fahrzeug konnte nicht erstellt werden, Garage ist voll!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.navigation_dashboard);
            }
        });
        buttonAbbrechen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.navigation_dashboard);
            }
        });

        editTextName.setText("Gespeicherter Name"); //Test für Auto ändern später, da selbes Fragment verwendet wird


        newcarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

}