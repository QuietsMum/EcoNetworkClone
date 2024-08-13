package com.econetwork;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.econetwork.ui.home.Edu_model;
import com.econetwork.ui.profile.Profile_model_events;

import java.util.ArrayList;

public class Reportadapter extends ArrayAdapter {

//    private int[] icons = new int[]{};
    private int[] images = new int[]{};

    public Reportadapter(@NonNull Context context, int resource,  int[] images ) {
        super(context, resource);
        this.images = images;
        //this.icons = icons;

    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v;

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.profile_events_item, null);

        ImageView imageView = (ImageView) v.findViewById(R.id.profile_events_image);
      //  ImageView iconView = (ImageView) v.findViewById(R.id.profile_events_icon);
        imageView.setImageResource(images[position]);
    //    iconView.setImageResource(icons[position]);

        return v;

    }

}







//
// extends ArrayAdapter {
//
//    private ArrayList<Profile_model_events> dataSet;
//    private LayoutInflater mInflater;
//    private ItemClickListener mClickListener;
//
//
//    // data is passed into the constructor
//    public Reportadapter(Context context, ArrayList<Profile_model_events>dataSet) {
//        this.mInflater = LayoutInflater.from(context);
//        this.dataSet = dataSet;
//    }
//
//    // inflates the cell layout from xml when needed
//    @Override
//    @NonNull
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.profile_events_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    // binds the data to the TextView in each cell
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Profile_model_events object = dataSet.get(position);
//        holder.icons.setImageResource(object.icon);
//        holder.image2.setImageResource(object.data);
//    }
//
//    public int getCount() {
//        return dataSet.size();
//    }
//
//    public Object getItem(int position) {
//        return dataSet.get(position);
//    }
//
//
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        return null;
//    }
//
//
//    // stores and recycles views as they are scrolled off screen
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        ImageView icons;
//        ImageView image2;
//        ViewHolder(View itemView) {
//            super(itemView);
//            icons = itemView.findViewById(R.id.profile_events_icon);
//            image2 = itemView.findViewById(R.id.profile_events_image);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//        }
//    }
//
////    // convenience method for getting data at click position
////    String getItem(int id) {
////        return  dataSet[id];
////
////    }
//
//    // allows clicks events to be caught
//    void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }
//
//    // parent activity will implement this method to respond to click events
//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }
//}
//




// extends RecyclerView.Adapter<Reportadapter.ViewHolder> {
//
//    private final ArrayList<Profile_model_events> dataSet;
//    Context mContext;
//    int total_types;
//
//    private LayoutInflater mInflater;
//    private ItemClickListener mClickListener;
//
//    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {
//
//
//
//
//
//        public ImageTypeViewHolder(View itemView) {
//            super(itemView);
//
//            this.image = itemView.findViewById(R.id.profile_events_image);
//            this.image2 =  itemView.findViewById(R.id.profile_events_icon);
//        }
//    }
//
//    public Reportadapter(ArrayList<Profile_model_events>data, Context context) {
//        this.dataSet = data;
//        this.mContext = context;
//        total_types = dataSet.size();
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.profile_events_item, parent, false);
//        return new Reportadapter.ImageTypeViewHolder(view);
//
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, final int listPosition) {
//
//        Profile_model_events object = dataSet.get(listPosition);
//        if (object != null) {
//          holder.image2.setImageResource(object.icon);
//            ((Reportadapter.ImageTypeViewHolder) holder).image.setImageResource(object.data);
//        }
//    }
//
//    // stores and recycles views as they are scrolled off screen
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            image = itemView.findViewById(R.id.profile_events_image);
//            image2 = itemView.findViewById(R.id.profile_events_icon);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//        }
//    }
//
//    // allows clicks events to be caught
//    void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }
//
//    // parent activity will implement this method to respond to click events
//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return dataSet.size();
//    }
//}
//
//




//    public Reportadapter(Context c) {
//        mContext = c;
//    }
//
//    public int getCount() {
//        return mThumbIds.length;
//    }
//
//    public Object getItem(int position) {
//        return mThumbIds[position];
//    }
//
//
//    public long getItemId(int position) {
//        return position;
//    }
//
//
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView;
//        if (convertView == null) {
//            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
//            int pxWidth = displayMetrics.widthPixels;
//            //float dpWidth = pxWidth / displayMetrics.density;
//            int pxHeight = displayMetrics.heightPixels;
//            //float dpHeight = pxHeight / displayMetrics.density;
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(pxWidth/3-20, pxWidth/3));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(1, 1, 1, 1);
//        } else {
//            imageView = (ImageView) convertView;
//        }
//
//        imageView.setImageResource(mThumbIds[position]);
//        return imageView;
//    }
//
//
//    public	Integer[] mThumbIds = { R.drawable.almaty1, R.drawable.almaty2,
//            R.drawable.almaty3, R.drawable.almaty4, R.drawable.almaty5,
//            R.drawable.almaty6, R.drawable.almaty7, R.drawable.almaty8,
//            R.drawable.almaty9, R.drawable.almaty10, R.drawable.almaty11,
//            R.drawable.almaty12, R.drawable.almaty13, R.drawable.almaty14,
//            R.drawable.almaty15, R.drawable.almaty16, R.drawable.almaty17,
//            R.drawable.almaty18};
//}
