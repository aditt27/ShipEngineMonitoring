package com.adibu.shipmonitoring;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.adibu.shipmonitoring.model.GridModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainGridAdapter extends ArrayAdapter<GridModel> {

    Context context;
    public MainGridAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View gridItemView = convertView;
        if(gridItemView == null) {
            gridItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_grid_main, parent, false);
        }

        GridModel currentItem = getItem(position);

        TextView title = gridItemView.findViewById(R.id.grid_judul);
        title.setText(context.getString(currentItem.getJudul()));

        TextView status = gridItemView.findViewById(R.id.grid_status);
        status.setText(currentItem.getStatus());

        return gridItemView;
    }
}
