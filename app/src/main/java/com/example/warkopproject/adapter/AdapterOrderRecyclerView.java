package com.example.warkopproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warkopproject.MainActivity;
import com.example.warkopproject.R;
import com.example.warkopproject.model.Order;

import java.util.ArrayList;

public class AdapterOrderRecyclerView extends RecyclerView.Adapter<AdapterOrderRecyclerView.ViewHolder> {

    private ArrayList<Order> orders;
    private Context context;
    FirebaseDataListener listener;

    public AdapterOrderRecyclerView(ArrayList<Order> barangs, Context ctx){
        orders = barangs;
        context = ctx;
        listener = (MainActivity)ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_order, parent,
                false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteData(orders.get(position),position);
            }
        });

        holder.tvHarga.setText(order.getHargaOrder().toString());
        holder.tvNama.setText(order.getNamaOrder());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHarga,tvNama, tvDel;

        ViewHolder(View v) {
            super(v);
            tvHarga = (TextView) v.findViewById(R.id.tv_hargaMenuOrder);
            tvNama = (TextView) v.findViewById(R.id.tv_namaMenuOrder);
            tvDel = (TextView) v.findViewById(R.id.delOrder);
        }
    }

    public interface FirebaseDataListener{
        void onDeleteData(Order order, int position);
    }
}
