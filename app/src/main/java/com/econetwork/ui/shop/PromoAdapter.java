package com.econetwork.ui.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.econetwork.R;
import com.econetwork.ui.dashboard.EventsModelMainItem;
import com.econetwork.ui.dashboard.Eventsadapter;

import java.util.ArrayList;

public class PromoAdapter extends RecyclerView.Adapter {

    private final ArrayList<PromoModelMain> dataSet;
    Context mContext;
    int total_types;
    private boolean fabStateVolume = false;


    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        ImageView image;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);

//            this.txtType = (TextView) itemView.findViewById(R.id.text_event_header_item);
            this.image = (ImageView) itemView.findViewById(R.id.image_item_main_promo);
        }
    }

    public PromoAdapter(ArrayList<PromoModelMain>data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promo_item, parent, false);
        return new PromoAdapter.ImageTypeViewHolder(view);


    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        PromoModelMain object = dataSet.get(listPosition);

        if (object != null) {
            ((PromoAdapter.ImageTypeViewHolder) holder).image.setImageResource(object.data);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}