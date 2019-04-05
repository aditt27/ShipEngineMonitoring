package com.adibu.shipmonitoring;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adibu.shipmonitoring.model.GaugeModel;
import com.github.anastr.speedviewlib.SpeedView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class GaugeAdapter extends RecyclerView.Adapter<GaugeAdapter.MyViewHolder> {

    private Context mContext ;
    private List<GaugeModel> mData ;


    public GaugeAdapter(Context mContext, List<GaugeModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_gauge,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.title.setText(mData.get(position).getTitle());
        holder.gauge.setMinSpeed(mData.get(position).getMin());
        holder.gauge.setMaxSpeed(mData.get(position).getMax());
        holder.gauge.speedTo(mData.get(position).getCurrent());
        holder.gauge.setUnit(mData.get(position).getUnit());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        SpeedView gauge;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.gauge_title) ;
            gauge = itemView.findViewById(R.id.gauge);
        }
    }
}