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

import com.example.tankauswertung.R;

public class TimelineFragment extends Fragment {

    private TimelineViewModel timelineViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        timelineViewModel =
                new ViewModelProvider(this).get(TimelineViewModel.class);
        View root = inflater.inflate(R.layout.fragment_timeline, container, false);
        final TextView textView = root.findViewById(R.id.text_timeline);
        timelineViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}