package com.econetwork.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.econetwork.R;
import java.util.ArrayList;

public class Events_main_item_adapter extends RecyclerView.Adapter {

    private final ArrayList<Events_item_small> dataSet;
    Context mContext;
    int total_types;

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {
        TextView txtType;
        public ImageTypeViewHolder(View itemView) {
            super(itemView);
            this.txtType = itemView.findViewById(R.id.list_events_text_small);
        }
    }

    public Events_main_item_adapter(ArrayList<Events_item_small>data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item_small, parent, false);
        return new Events_main_item_adapter.ImageTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {
        Events_item_small object = dataSet.get(listPosition);
        if (object != null) {
            ((Events_main_item_adapter.ImageTypeViewHolder) holder).txtType.setText(object.text);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}