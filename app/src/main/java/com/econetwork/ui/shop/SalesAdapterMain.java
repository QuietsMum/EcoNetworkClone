package com.econetwork.ui.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.econetwork.PartnersViewTypeAdapter;
import com.econetwork.R;
import com.econetwork.ui.dashboard.Eventsadapter;
import com.econetwork.ui.home.Partners_model;

import java.util.ArrayList;

public class SalesAdapterMain extends RecyclerView.Adapter {

    private final ArrayList<SalesModelMain> dataSet;
    Context mContext;
    int total_types;
    private boolean fabStateVolume = false;


    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        ImageView image;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.type_sales_main);
            this.image = (ImageView) itemView.findViewById(R.id.background_sales_main);
        }
    }

    public SalesAdapterMain(ArrayList<SalesModelMain>data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_item_main, parent, false);
        return new SalesAdapterMain.ImageTypeViewHolder(view);


    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        SalesModelMain object = dataSet.get(listPosition);
        if (object != null) {
            ((SalesAdapterMain.ImageTypeViewHolder) holder).txtType.setText(object.text);
            ((SalesAdapterMain.ImageTypeViewHolder) holder).image.setImageResource(object.data);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}