package com.example.warkopproject.page.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.warkopproject.R;
import com.example.warkopproject.model.Barang;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateStockActivity extends AppCompatActivity {

    EditText namaBarang, stockBarang;
    Spinner kategori;
    DatabaseReference databaseReference;
    Button btAdd;
    private Barang barang = null;
    private FirebaseUser user;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_stock);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        namaBarang = findViewById(R.id.input_NamaBarang);
        stockBarang = findViewById(R.id.input_StockBarang);
        kategori = findViewById(R.id.spin_jenisBarang);
        btAdd = findViewById(R.id.buttonAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });


        databaseReference = FirebaseDatabase
                .getInstance("https://warkopproject-dfeab-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("stock_item");

        barang = (Barang) getIntent().getSerializableExtra("data");
        if(barang!=null){
            namaBarang.setText(barang.getNamaBarang());
            stockBarang.setText(barang.getStockBarang().toString());
        }
    }

    private void addItem(){
        if (barang != null) {
            btAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    barang.setNamaBarang(namaBarang.getText().toString());
                    barang.setKategoriBarang(kategori.getSelectedItem().toString());
                    barang.setStockBarang(Integer.parseInt(stockBarang.getText().toString()));

                    updateBarang(barang);
                }
            });
        }
        else{
            String itemName = namaBarang.getText().toString();
            Integer itemStock = Integer.parseInt(stockBarang.getText().toString());
            String itemCategory = kategori.getSelectedItem().toString();

            if(!TextUtils.isEmpty(itemName)||!TextUtils.isEmpty(itemCategory)||
                    !TextUtils.isEmpty(String.valueOf(itemStock))){

                String id = databaseReference.push().getKey();
                Barang barang = new Barang(itemName,itemCategory,itemStock);

                databaseReference.child(uid).child(id).setValue(barang).addOnSuccessListener(this,
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                namaBarang.setText("");
                                stockBarang.setText("");

                                Snackbar.make(findViewById(R.id.buttonAdd),"Data inserted",
                                        Snackbar.LENGTH_LONG).show();
                                CreateStockActivity.this.finish();
                            }
                        });
            }else{
                Toast.makeText(getApplicationContext(),"Fill all the field", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void updateBarang(Barang barang) {
        String id = barang.getKey();
        databaseReference.child(uid) //akses parent index, ibaratnya seperti nama tabel
                .child(id) //select barang berdasarkan key
                .setValue(barang) //set value barang yang baru
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_LONG)
                                .show();
                        CreateStockActivity.this.finish();

                    }
                });
    }
}