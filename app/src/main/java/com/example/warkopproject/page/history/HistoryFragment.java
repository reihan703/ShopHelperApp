package com.example.warkopproject.page.history;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.warkopproject.R;
import com.example.warkopproject.adapter.AdapterBarangRecyclerView;
import com.example.warkopproject.adapter.AdapterHistoryRecyclerView;
import com.example.warkopproject.databinding.FragmentHistoryBinding;
import com.example.warkopproject.databinding.FragmentHomeBinding;
import com.example.warkopproject.model.Barang;
import com.example.warkopproject.model.History;
import com.example.warkopproject.page.home.CreateStockActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<History> histories;
    private FirebaseUser user;
    String uid;

    FragmentHistoryBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        histories =new ArrayList<>();
        database = FirebaseDatabase
                .getInstance("https://warkopproject-dfeab-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference();

        database.child("history").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot noteDataSnapshot : snapshot.getChildren()){
                    String nama = noteDataSnapshot.getKey();
                    History history = new History(nama);
                    histories.add(history);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getDetails() + "" + error.getMessage());
            }
        });

        rvView = (RecyclerView)view.findViewById(R.id.rvHistory);
        rvView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        rvView.setLayoutManager(layoutManager);
        adapter = new AdapterHistoryRecyclerView(histories,getContext());
        rvView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }
}