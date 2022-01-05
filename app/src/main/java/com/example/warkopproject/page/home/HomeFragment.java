package com.example.warkopproject.page.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.warkopproject.R;
import com.example.warkopproject.adapter.AdapterBarangRecyclerView;
import com.example.warkopproject.databinding.FragmentHomeBinding;
import com.example.warkopproject.model.Barang;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Barang> daftarBarang;
    private FirebaseUser user;
    String uid;

    FragmentHomeBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        daftarBarang =new ArrayList<>();
        database = FirebaseDatabase
                .getInstance("https://warkopproject-dfeab-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference();

        database.child("stock_item").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot noteDataSnapshot : snapshot.getChildren()){
                    Barang barang = noteDataSnapshot.getValue(Barang.class);
                    barang.setKey(noteDataSnapshot.getKey());
                    daftarBarang.add(barang);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getDetails() + "" + error.getMessage());
            }
        });

        binding.ftAddStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),CreateStockActivity.class));
            }
        });
        rvView = (RecyclerView)view.findViewById(R.id.rvStockBarang);
        rvView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        rvView.setLayoutManager(layoutManager);
        adapter = new AdapterBarangRecyclerView(daftarBarang,getContext());
        rvView.setAdapter(adapter);

            // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}