package com.econetwork.ui.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.econetwork.R;
import com.econetwork.ui.task.TaskAdapterImages;
import com.econetwork.ui.task.TaskModelImages;

import java.util.ArrayList;

public class EventAdapterImages extends RecyclerView.Adapter {

    private final ArrayList<EventModelImages> dataSet;
    Context mContext;
    int total_types;
    private boolean fabStateVolume = false;


    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        //        TextView txtType;
        ImageView image;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);

//            this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.image = (ImageView) itemView.findViewById(R.id.images_event);
        }
    }

    public EventAdapterImages(ArrayList<EventModelImages>data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item_image, parent, false);
        return new EventAdapterImages.ImageTypeViewHolder(view);


    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        EventModelImages object = dataSet.get(listPosition);
        if (object != null) {
            ((EventAdapterImages.ImageTypeViewHolder) holder).image.setImageResource(object.data);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}