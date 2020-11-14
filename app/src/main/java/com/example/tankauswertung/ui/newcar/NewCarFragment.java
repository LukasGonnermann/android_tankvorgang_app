package com.example.tankauswertung.ui.newcar;

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

import com.example.tankauswertung.R;

public class NewCarFragment extends Fragment {

    private NewCarViewModel newcarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        newcarViewModel =
                new ViewModelProvider(this).get(NewCarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_newcar, container, false);
        final TextView textView = root.findViewById(R.id.text_test);
        newcarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}