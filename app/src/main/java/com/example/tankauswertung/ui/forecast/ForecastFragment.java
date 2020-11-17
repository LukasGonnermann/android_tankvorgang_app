package com.example.tankauswertung.ui.forecast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tankauswertung.R;

public class ForecastFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_forecast, container, false);
        final TextView textView = root.findViewById(R.id.text_forecast);
        textView.setText("Forecast");
        return root;
    }
}