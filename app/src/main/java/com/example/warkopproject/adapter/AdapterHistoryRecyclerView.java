package com.example.warkopproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warkopproject.R;
import com.example.warkopproject.model.History;
import com.example.warkopproject.page.history.DetailHistoryActivity;

import java.util.ArrayList;

public class AdapterHistoryRecyclerView extends
        RecyclerView.Adapter<AdapterHistoryRecyclerView.ViewHolder> {

    private ArrayList<History> histories;
    private Context context;

    public AdapterHistoryRecyclerView(ArrayList<History> histories, Context context) {
        this.histories = histories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_history, parent,
                false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History history = histories.get(position);
        holder.tanggal.setText(history.getDate());
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, DetailHistoryActivity.class)
                        .putExtra("name", histories.get(position).getDate()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tanggal, detail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggal = (TextView) itemView.findViewById(R.id.tv_dateAndTime);
            detail = (TextView) itemView.findViewById(R.id.detailHistory);
        }
    }
}
