package com.econetwork;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.econetwork.ui.home.Events_model;

import java.util.ArrayList;

public class MultiViewTypeAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private final ArrayList<Events_model> dataSet;
    Context mContext;
    int total_types;
    private boolean fabStateVolume = false;

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, EventActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }


    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        ImageView image;
        private Context context;
        public ImageTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.image = (ImageView) itemView.findViewById(R.id.background);
        }

//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(context, EventActivity.class);
//            context.startActivity(intent);
//        }
    }

    public MultiViewTypeAdapter(ArrayList<Events_model>data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagetype_viewpager, parent, false);
        view.setOnClickListener(this);
        return new ImageTypeViewHolder(view);


    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        Events_model object = dataSet.get(listPosition);
        if (object != null) {
       ((ImageTypeViewHolder) holder).txtType.setText(object.text);
      ((ImageTypeViewHolder) holder).image.setImageResource(object.data);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}