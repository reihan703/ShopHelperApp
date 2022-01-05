package com.example.warkopproject.page.order;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.warkopproject.R;
import com.example.warkopproject.adapter.AdapterOrderRecyclerView;
import com.example.warkopproject.databinding.FragmentListBinding;
import com.example.warkopproject.databinding.FragmentStockBinding;
import com.example.warkopproject.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class OrderFragment extends Fragment {

    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Order> orders;
    private FirebaseUser user;
    String uid;

    FragmentStockBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        orders = new ArrayList<>();
        database = FirebaseDatabase
                .getInstance("https://warkopproject-dfeab-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference();

        database.child("order").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot noteDataSnapshot : snapshot.getChildren()){
                    Order order =noteDataSnapshot.getValue(Order.class);
                    order.setKey(noteDataSnapshot.getKey());
                    orders.add(order);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getDetails() + "" + error.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = s.format(new Date());

        binding = FragmentStockBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Order Cleared", Toast.LENGTH_SHORT);
                database.child("order").child(uid).removeValue();
                orders.clear();
            }

        });

        binding.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveRecord(database.child("order").child(uid),
                        database.child("history").child(uid)
                                .child(format));
                database.child("order").child(uid).removeValue();
                orders.clear();
            }
        });


        rvView = (RecyclerView)view.findViewById(R.id.rvOrder);
        rvView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        rvView.setLayoutManager(layoutManager);
        adapter = new AdapterOrderRecyclerView(orders,getContext());
        rvView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }

    private void moveRecord(DatabaseReference fromPath, final DatabaseReference toPath) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            Log.d(TAG, "Success!");
                        } else {
                            Log.d(TAG, "Copy failed!");
                            Toast.makeText(getContext(),"Failed to submit",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        fromPath.addListenerForSingleValueEvent(valueEventListener);
    }
}