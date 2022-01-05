package com.example.warkopproject.page.menu;

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

import com.example.warkopproject.adapter.AdapterMenuProductRecyclerView;
import com.example.warkopproject.databinding.FragmentListBinding;
import com.example.warkopproject.model.MenuProduct;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private DatabaseReference databaseReference;
    private RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<MenuProduct> arrayMenu;
    private FirebaseUser user;
    String uid;

    FragmentListBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        arrayMenu = new ArrayList<>();
        databaseReference = FirebaseDatabase
                .getInstance("https://warkopproject-dfeab-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference();

        databaseReference.child("menu").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot noteDataSnapshot : snapshot.getChildren()){
                    MenuProduct menuProduct = noteDataSnapshot.getValue(MenuProduct.class);
                    menuProduct.setKey(noteDataSnapshot.getKey());
                    arrayMenu.add(menuProduct);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getDetails() + "" + error.getMessage());
            }
        });

        binding.ftAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateMenuActivity.class));
            }
        });

        binding.rvMenuBarang.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(getContext());

        binding.rvMenuBarang.setLayoutManager(layoutManager);

        adapter = new AdapterMenuProductRecyclerView(arrayMenu, getContext());
        binding.rvMenuBarang.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

    }
}