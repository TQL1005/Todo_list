package com.example.newapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DesAdapter extends ArrayAdapter {
    private Context ctx;
    private int mResources;
    public DesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Destination> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.mResources = resource;
    }

    @SuppressLint({"DefaultLocale", "ViewHolder"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        convertView = layoutInflater.inflate(mResources,parent,false);
        TextView des = convertView.findViewById(R.id.destination);
        TextView desc = convertView.findViewById(R.id.description);
        TextView price = convertView.findViewById(R.id.price);

        Destination des1 = (Destination) getItem(position);
        des.setText(String.format("%s", des1.getDestinations()));
        desc.setText(String.format("Description =  %s", des1.getDescriptions()));
        price.setText(String.format("price = %.2f", des1.getPrice()));
        return convertView;
    }
}
