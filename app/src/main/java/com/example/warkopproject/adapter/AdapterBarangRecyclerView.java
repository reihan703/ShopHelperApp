package com.example.warkopproject.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warkopproject.page.home.CreateStockActivity;
import com.example.warkopproject.MainActivity;
import com.example.warkopproject.R;
import com.example.warkopproject.model.Barang;

import java.util.ArrayList;

public class AdapterBarangRecyclerView extends
        RecyclerView.Adapter<AdapterBarangRecyclerView.ViewHolder> {

    private ArrayList<Barang> daftarBarang;
    private Context context;
    FirebaseDataListener listener;

    public AdapterBarangRecyclerView(ArrayList<Barang> barangs, Context ctx){
        daftarBarang = barangs;
        context = ctx;
        listener = (MainActivity)ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.stock_barang, parent,
                false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Barang barang = daftarBarang.get(position);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.barang_dialog_view);
                dialog.show();

                Button editButton = (Button)
                        dialog.findViewById(R.id.bt_edit_data);
                Button delButton = (Button)
                        dialog.findViewById(R.id.bt_delete_data);

                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        context.startActivity(new Intent(context, CreateStockActivity.class)
                                .putExtra("data", daftarBarang.get(position)));
                    }
                });

                delButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        listener.onDeleteData(daftarBarang.get(position),position);
                    }
                });
            }
        });
        holder.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        holder.tvTitle.setText(barang.getNamaBarang());
        holder.tvStock.setText(barang.getStockBarang().toString());
        holder.tvCategory.setText(barang.getKategoriBarang());

//        holder.tvTitle.setText(daftarBarang.get(position).getNamaBarang());
    }

    @Override
    public int getItemCount() {
        return daftarBarang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvCategory,tvStock, edit;

        ViewHolder(View v) {
            super(v);
            tvCategory = (TextView) v.findViewById(R.id.tv_barangCategory);
            tvTitle = (TextView) v.findViewById(R.id.tv_namabarang);
            tvStock = (TextView) v.findViewById(R.id.tv_stockBarangCard);
            edit = (TextView) v.findViewById(R.id.editButton);
        }
    }

    public interface FirebaseDataListener{
        void onDeleteData(Barang barang, int position);
    }
}
