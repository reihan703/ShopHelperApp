package com.example.warkopproject.page.history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.warkopproject.R;
import com.example.warkopproject.adapter.AdapterHistoryDetailRecyclerView;
import com.example.warkopproject.adapter.AdapterOrderRecyclerView;
import com.example.warkopproject.model.History;
import com.example.warkopproject.model.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailHistoryActivity extends AppCompatActivity {

    private DatabaseReference database;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseUser user;
    String uid;
    private ArrayList<Order> orders;

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);

        orders=new ArrayList<>();
        rv = (RecyclerView) findViewById(R.id.rvHistoryDetail);
        rv.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new AdapterHistoryDetailRecyclerView(orders,this);
        rv.setAdapter(adapter);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        String nama = getIntent().getStringExtra("name");
        System.out.println(nama);

        database = FirebaseDatabase
                .getInstance("https://warkopproject-dfeab-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference();

        database.child("history").child(uid).child(nama).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot noteDataSnapshot : snapshot.getChildren()){
                    Order order =noteDataSnapshot.getValue(Order.class);
//                    String nama = noteDataSnapshot.child("namaOrder").getValue(String.class);
//                    String harga = noteDataSnapshot.child("hargaOrder").getValue(String.class);
//                    Order order = new Order(nama,Integer.parseInt(harga));
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
}