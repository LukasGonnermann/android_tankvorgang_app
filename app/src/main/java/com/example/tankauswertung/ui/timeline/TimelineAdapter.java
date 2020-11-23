package com.example.tankauswertung.ui.timeline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tankauswertung.R;
import com.github.vipulasri.timelineview.TimelineView;

public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = View.inflate(context, R.layout.fragment_timeline, null);
        return new TimelineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class TimelineViewHolder extends RecyclerView.ViewHolder {

        TimelineView timelineView;
        AppCompatTextView title, description, date;

        public TimelineViewHolder(View itemView, int viewType) {
            super(itemView);
        }
    }
}
