package com.example.warkopproject.page.profile;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.warkopproject.LoginActivity;
import com.example.warkopproject.MainActivity;
import com.example.warkopproject.R;
import com.example.warkopproject.databinding.ActivityMainBinding;
import com.example.warkopproject.databinding.FragmentProfileBinding;
import com.example.warkopproject.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private DatabaseReference databaseReference;
//    private RecyclerView.Adapter adapter;
//    RecyclerView.LayoutManager layoutManager;
//    private ArrayList<User> arrayUser;
    private FirebaseUser user;
    String uid;

    private FirebaseAuth fAuth;
    FragmentProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //LOGOUT ACTION HERE
        binding.profileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();

            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        databaseReference = FirebaseDatabase
                .getInstance("https://warkopproject-dfeab-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference();

//        ValueEventListener userListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                System.out.println(error.getDetails() + "" + error.getMessage());
//            }
//        };
//
//        databaseReference.addValueEventListener(userListener);

        databaseReference.child("user").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    User user = snapshot.getValue(User.class);
//
//                    binding.profileName.setText(user.getNamaToko());
//                    binding.profileEmail.setText(user.getEmail());
//                    binding.profilePhone.setText(user.getNomorTelepon());
//                    binding.profileToko.setText(user.getNamaToko());
//                    binding.profileAlamatToko.setText(user.getAlamatToko());
//                }
                User user = dataSnapshot.getValue(User.class);
                user.setKeyUser(dataSnapshot.getKey());

                binding.profileName.setText(user.getName());
                binding.profileEmail.setText(user.getEmail());
                binding.profilePhone.setText(user.getNomorTelepon());
                binding.profileToko.setText(user.getNamaToko());
                binding.profileAlamatToko.setText(user.getAlamatToko());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}