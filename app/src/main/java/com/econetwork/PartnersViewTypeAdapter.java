package com.econetwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.econetwork.ui.home.Events_model;
import com.econetwork.ui.home.Partners_model;

import java.util.ArrayList;

public class PartnersViewTypeAdapter extends RecyclerView.Adapter {

    private final ArrayList<Partners_model> dataSet;
    Context mContext;
    int total_types;
    private boolean fabStateVolume = false;


    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        ImageView image;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.image = (ImageView) itemView.findViewById(R.id.background);
        }
    }

    public PartnersViewTypeAdapter(ArrayList<Partners_model>data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.partnerstype_viewpager, parent, false);
        return new PartnersViewTypeAdapter.ImageTypeViewHolder(view);


    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        Partners_model object = dataSet.get(listPosition);
        if (object != null) {
            ((PartnersViewTypeAdapter.ImageTypeViewHolder) holder).txtType.setText(object.text);
            ((PartnersViewTypeAdapter.ImageTypeViewHolder) holder).image.setImageResource(object.data);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}