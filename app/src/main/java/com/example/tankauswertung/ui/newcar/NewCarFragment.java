package com.example.tankauswertung.ui.newcar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tankauswertung.R;

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

        //TODO:für alle EditText machen um später Fahrzeugobjekte in die entsprechenden Felder zu füllen
        //TODO:ButtonListener erstellen
        editTextName.setText("Gespeicherter Name"); //Test für Auto ändern später, da selbes Fragment verwendet wird


        newcarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}