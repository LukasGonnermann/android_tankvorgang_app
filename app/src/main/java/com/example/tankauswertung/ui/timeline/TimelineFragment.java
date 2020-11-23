package com.example.tankauswertung.ui.timeline;

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

import com.example.tankauswertung.Garage;
import com.example.tankauswertung.MainActivity;
import com.example.tankauswertung.R;

public class TimelineFragment extends Fragment {

    Garage garage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_timeline, container, false);
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
            // TODO: error handling?
            return;
        }


    }
}