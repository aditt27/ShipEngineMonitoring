package com.adibu.shipmonitoring.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adibu.shipmonitoring.R;
import com.adibu.shipmonitoring.model.WarningModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WarningAdapter extends RecyclerView.Adapter<WarningAdapter.MyViewHolder> {

    private Context mContext ;
    private List<WarningModel> mData ;

    public WarningAdapter(Context mContext, List<WarningModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_warning,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.categories.setText(mData.get(position).getCategories());
        holder.title.setText(mData.get(position).getTitle());
        holder.message.setText(mData.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView categories;
        TextView message;

        public MyViewHolder(View itemView) {
            super(itemView);
            categories = itemView.findViewById(R.id.item_warning_categories);
            title = itemView.findViewById(R.id.item_warning_title) ;
            message = itemView.findViewById(R.id.item_warning_message);
        }
    }
}
