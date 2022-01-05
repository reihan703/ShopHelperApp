package com.example.warkopproject.page.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.warkopproject.R;
import com.example.warkopproject.databinding.ActivityCreateMenuBinding;
import com.example.warkopproject.model.MenuProduct;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateMenuActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    private MenuProduct menuProduct = null;
    private FirebaseUser user;
    String uid;
    private ArrayList<String> arrayList = new ArrayList<>();

    ActivityCreateMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        binding.btnAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMenu();
            }
        });

        databaseReference = FirebaseDatabase
                .getInstance("https://warkopproject-dfeab-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("menu");

        showProductSpinner();

        menuProduct = (MenuProduct) getIntent().getSerializableExtra("data");
        if(menuProduct!=null){
//            binding.spinNamaMenu.setText(menuProduct.getNamaMenu());

            binding.inputHargaMenu.setText(menuProduct.getHargaMenu().toString());
        }
    }

    private void showProductSpinner(){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot productName : snapshot.getChildren()){
                    arrayList.add(productName.child("namaBarang").getValue(String.class));
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                        (CreateMenuActivity.this,R.layout.style_spinner, arrayList);
                binding.spinNamaMenu.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Query query = FirebaseDatabase
                .getInstance("https://warkopproject-dfeab-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("stock_item").child(uid).orderByChild("kategoriBarang").equalTo("Product");
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    private void addMenu(){
        if(menuProduct != null){
            binding.btnAddMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuProduct.setNamaMenu(binding.spinNamaMenu.getSelectedItem().toString());
                    menuProduct.setKategoriMenu(binding.spinKategoriMenu.getSelectedItem().toString());
                    menuProduct.setHargaMenu(Integer.parseInt(binding.inputHargaMenu.getText().toString()));

                    updateMenu(menuProduct);
                }
            });
        }
        else{
            String namaMenu = binding.spinNamaMenu.getSelectedItem().toString();
            String kategoriMenu = binding.spinKategoriMenu.getSelectedItem().toString();
            Integer hargaMenu = Integer.parseInt(binding.inputHargaMenu.getText().toString());

            if(!TextUtils.isEmpty(namaMenu) || !TextUtils.isEmpty(String.valueOf(hargaMenu))
                                || !TextUtils.isEmpty((kategoriMenu))){
                String id = databaseReference.push().getKey();
                MenuProduct menuProduct = new MenuProduct(namaMenu, kategoriMenu, hargaMenu);

                databaseReference.child(uid).child(id).setValue(menuProduct).addOnSuccessListener(this,
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                binding.inputHargaMenu.setText("");

                                Snackbar.make(findViewById(R.id.btnAddMenu), "Data Inserted",
                                        Snackbar.LENGTH_LONG).show();
                                CreateMenuActivity.this.finish();
                            }
                        });
            } else{
                Toast.makeText(getApplicationContext(), "Fill All the Field",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateMenu(MenuProduct menuProduct){
        String id = menuProduct.getKey();
        databaseReference.child(uid).child(id).setValue(menuProduct)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG)
                                .show();
                        CreateMenuActivity.this.finish();
                    }
                });
    }
}