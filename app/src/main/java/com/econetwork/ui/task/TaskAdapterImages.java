package com.econetwork.ui.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.econetwork.MultiViewTypeAdapter;
import com.econetwork.R;
import com.econetwork.ui.home.Events_model;

import java.util.ArrayList;

public class TaskAdapterImages extends RecyclerView.Adapter {

    private final ArrayList<TaskModelImages> dataSet;
    Context mContext;
    int total_types;
    private boolean fabStateVolume = false;


    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

//        TextView txtType;
        ImageView image;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);

//            this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.image = (ImageView) itemView.findViewById(R.id.images_task);
        }
    }

    public TaskAdapterImages(ArrayList<TaskModelImages>data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_image, parent, false);
        return new TaskAdapterImages.ImageTypeViewHolder(view);


    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        TaskModelImages object = dataSet.get(listPosition);
        if (object != null) {
            ((TaskAdapterImages.ImageTypeViewHolder) holder).image.setImageResource(object.data);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}