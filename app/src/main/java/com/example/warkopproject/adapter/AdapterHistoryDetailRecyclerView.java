package com.example.warkopproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warkopproject.R;
import com.example.warkopproject.model.Order;

import java.util.ArrayList;

public class AdapterHistoryDetailRecyclerView extends RecyclerView.Adapter<AdapterHistoryDetailRecyclerView.ViewHolder> {
    private ArrayList<Order> orders;
    private Context context;

    public AdapterHistoryDetailRecyclerView(ArrayList<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_order, parent,
                false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.tvHarga.setText(order.getHargaOrder().toString());
        holder.tvNama.setText(order.getNamaOrder());
        holder.tvDel.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHarga,tvNama, tvDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHarga = (TextView) itemView.findViewById(R.id.tv_hargaMenuOrder);
            tvNama = (TextView) itemView.findViewById(R.id.tv_namaMenuOrder);
            tvDel = (TextView) itemView.findViewById(R.id.delOrder);
        }
    }
}
