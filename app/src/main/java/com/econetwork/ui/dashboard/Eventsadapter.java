package com.econetwork.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.econetwork.PartnersViewTypeAdapter;
import com.econetwork.R;
import com.econetwork.ui.home.Partners_model;

import java.util.ArrayList;

public class Eventsadapter extends RecyclerView.Adapter {

    private final ArrayList<EventsModelMainItem> dataSet;
    Context mContext;
    int total_types;
    private boolean fabStateVolume = false;


    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        ImageView image;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.text_event_header_item);
            this.image = (ImageView) itemView.findViewById(R.id.image_item_main_events);
        }
    }

    public Eventsadapter(ArrayList<EventsModelMainItem>data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item_adapter, parent, false);
        return new Eventsadapter.ImageTypeViewHolder(view);


    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        EventsModelMainItem object = dataSet.get(listPosition);
        if (object != null) {
            ((Eventsadapter.ImageTypeViewHolder) holder).txtType.setText(object.text);
            ((Eventsadapter.ImageTypeViewHolder) holder).image.setImageResource(object.data);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}