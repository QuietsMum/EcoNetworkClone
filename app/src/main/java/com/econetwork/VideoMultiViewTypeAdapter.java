package com.econetwork;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.econetwork.ui.home.Edu_model;

import java.util.ArrayList;

public class VideoMultiViewTypeAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private final ArrayList<Edu_model> dataSet;
    Context mContext;
    int total_types;
    private boolean fabStateVolume = false;

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, VideoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }


    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        //TextView txtTime;
        ImageView image;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.video_name);
            //this.txtTime = (TextView) itemView.findViewById(R.id.vide_lenght);
            this.image = (ImageView) itemView.findViewById(R.id.background_video);
        }
    }

    public VideoMultiViewTypeAdapter(ArrayList<Edu_model>data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videotype_viewpager, parent, false);
        view.setOnClickListener(this);
        return new VideoMultiViewTypeAdapter.ImageTypeViewHolder(view);


    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        Edu_model object = dataSet.get(listPosition);
        if (object != null) {
            ((VideoMultiViewTypeAdapter.ImageTypeViewHolder) holder).txtType.setText(object.text);
            ((VideoMultiViewTypeAdapter.ImageTypeViewHolder) holder).image.setImageResource(object.data);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}