package com.econetwork.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.econetwork.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter {

    private final ArrayList<NewsModelMainItem> dataSet;
    Context mContext;
    int total_types;



    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        ImageView image;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.text_news_header_item);
            this.image = (ImageView) itemView.findViewById(R.id.image_item_main_news);
        }
    }

    public NewsAdapter(ArrayList<NewsModelMainItem>data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item_news, parent, false);
        return new NewsAdapter.ImageTypeViewHolder(view);


    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        NewsModelMainItem object = dataSet.get(listPosition);
        if (object != null) {
            ((NewsAdapter.ImageTypeViewHolder) holder).txtType.setText(object.text);
            ((NewsAdapter.ImageTypeViewHolder) holder).image.setImageResource(object.data);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}