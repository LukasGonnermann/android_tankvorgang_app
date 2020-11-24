package com.example.tankauswertung.ui.timeline;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tankauswertung.Ereignis;
import com.example.tankauswertung.Garage;
import com.example.tankauswertung.MainActivity;
import com.example.tankauswertung.R;
import com.github.vipulasri.timelineview.TimelineView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Ereignis> ereignisse;

    /**
     * zur Übergabe der Ereignisliste
     * @param ereignisse Ereignisliste
     */
    public TimelineAdapter(ArrayList<Ereignis> ereignisse) {
        super();
        this.ereignisse = ereignisse;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = View.inflate(context, R.layout.item_timeline, null);
        return new TimelineViewHolder(view, viewType);
    }

    /**
     * setzt die entsprechenden Attribute des Elements der Timeline an der Stelle position
     * @param holder Wrapperklasse für die Daten eines Timeline-Elements
     * @param position Position des aktuellen Elements der Timeline
     */
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        Ereignis ereignis = ereignisse.get(position);

        TimelineViewHolder timelineViewHolder = (TimelineViewHolder) holder;
        timelineViewHolder.textViewTimelineDatum.setText(ereignis.getDatumAsString());
        timelineViewHolder.textViewTimelineBeschreibung.setText(ereignis.getBeschreibung());

        if (ereignis.getEreignisTyp() == Ereignis.EreignisTyp.STRECKE) {
            setMarker(timelineViewHolder, R.drawable.ic_baseline_navigation_24);
        } else {
            setMarker(timelineViewHolder, R.drawable.ic_baseline_local_gas_station_24);
        }
    }

    @Override
    public int getItemCount() {
        return MainActivity.getGarage().getAusgewaehltesFahrzeug().getEreignisse().size();
    }

    public void setMarker(TimelineViewHolder timelineViewHolder, int drawableId) {
        TimelineView timelineView = timelineViewHolder.getTimelineView();
        Context context = timelineView.getContext();

        Drawable drawable = ResourcesCompat.getDrawable(
                context.getResources(),
                drawableId,
                context.getTheme()
        );
        timelineView.setMarker(drawable);
    }


    /**
     * data wrapper class
     */
    public static class TimelineViewHolder extends RecyclerView.ViewHolder {

        // TODO: Klasse Ereignis hier integrieren --> vereinfacht VIELES
        // in der Klasse Fahrzeug könnte dann die getEreignisse entsprechend umgeschrieben werden

        TimelineView timelineView;
        CardView cardView;
        TextView textViewTimelineDatum, textViewTimelineBeschreibung;

        public TimelineViewHolder(View itemView, int viewType) {
            super(itemView);
            timelineView = itemView.findViewById(R.id.timeline);
            cardView = itemView.findViewById(R.id.cardViewTimeline);
            textViewTimelineDatum = itemView.findViewById(R.id.textViewTimelineDatum);
            textViewTimelineBeschreibung = itemView.findViewById(R.id.textViewTimelineBeschreibung);
            timelineView.initLine(viewType);
        }

        public TimelineView getTimelineView() {
            return timelineView;
        }
    }
}
