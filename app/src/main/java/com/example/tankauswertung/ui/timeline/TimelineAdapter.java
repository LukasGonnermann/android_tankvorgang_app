package com.example.tankauswertung.ui.timeline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tankauswertung.Ereignis;
import com.example.tankauswertung.MainActivity;
import com.example.tankauswertung.NewStreckeActivity;
import com.example.tankauswertung.NewTankvorgangActivity;
import com.example.tankauswertung.R;
import com.example.tankauswertung.Tankvorgang;
import com.github.vipulasri.timelineview.TimelineView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Ereignis> ereignisse;
    private final Context context;

    /**
     * zur Übergabe der Ereignisliste
     * @param ereignisse Ereignisliste
     */
    public TimelineAdapter(ArrayList<Ereignis> ereignisse, Context context) {
        super();
        this.ereignisse = ereignisse;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_timeline, parent, false);
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
            setMarker(timelineViewHolder, R.drawable.marker_strecke);
        } else {
            setMarker(timelineViewHolder, R.drawable.marker_tankvorgang);
        }

        timelineViewHolder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Bild anzeigen lassen, falls Ereignis ein Tankvorgang ist
                if (ereignis.getEreignisTyp() == Ereignis.EreignisTyp.TANKVORGANG) {
                    Tankvorgang t = MainActivity.getGarage().getAusgewaehltesFahrzeug().getTankvorgaenge().get(ereignis.getIndex());
                    String img_path = t.getImg();
                    if (img_path != null) {
                        File img_file = new File(img_path);
                        Uri fileProviderImageUri = FileProvider.getUriForFile(context, "com.example.android.fileprovider",img_file);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setDataAndType(fileProviderImageUri, "image/*");
                        context.startActivity(intent);
                    }
                    else {
                        Toast.makeText(context, "Kein Bild für diesen Tankvorgang hinterlegt", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        // nur aktuellstes Ereignis soll bearbeitet werden können
        if (position == 0) {

            timelineViewHolder.imageButtonEreignisBearbeiten.setVisibility(View.VISIBLE);

            timelineViewHolder.imageButtonEreignisBearbeiten.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ereignis.getEreignisTyp() == Ereignis.EreignisTyp.STRECKE) {
                        Intent intent = new Intent(context, NewStreckeActivity.class);
                        intent.setAction(TimelineFragment.ACTION_EDIT_STRECKE);
                        ((Activity) context).startActivityForResult(
                                intent, TimelineFragment.LAUNCH_NEW_STRECKE_EDIT_STRECKE
                        );
                    } else {
                        Intent intent = new Intent(context, NewTankvorgangActivity.class);
                        intent.setAction(TimelineFragment.ACTION_EDIT_TANKVORGANG);
                        ((Activity) context).startActivityForResult(
                                intent, TimelineFragment.LAUNCH_NEW_TANKVORGANG_EDIT_TANKVORGANG
                        );
                    }
                }
            });
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

        private final TimelineView timelineView;
        private final CardView cardView;
        private final TextView textViewTimelineDatum;
        private final TextView textViewTimelineBeschreibung;
        private final ImageButton imageButtonEreignisBearbeiten;

        public TimelineViewHolder(View itemView, int viewType) {
            super(itemView);
            timelineView = itemView.findViewById(R.id.timeline);
            cardView = itemView.findViewById(R.id.cardViewTimeline);
            textViewTimelineDatum = itemView.findViewById(R.id.textViewTimelineDatum);
            textViewTimelineBeschreibung = itemView.findViewById(R.id.textViewTimelineBeschreibung);
            imageButtonEreignisBearbeiten = itemView.findViewById(R.id.imageButtonEreignisBearbeiten);
            timelineView.initLine(viewType);
        }

        public TimelineView getTimelineView() {
            return timelineView;
        }

        public CardView getCardView() {
            return cardView;
        }
    }

}
