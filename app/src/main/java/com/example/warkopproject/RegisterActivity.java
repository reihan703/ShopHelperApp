package com.example.warkopproject;

import static android.text.TextUtils.isEmpty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.warkopproject.databinding.ActivityRegisterBinding;
import com.example.warkopproject.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;
    private static final String TAG = RegisterActivity.class.getSimpleName();

    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance("https://warkopproject-dfeab-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("user");

        fAuth = FirebaseAuth.getInstance();
        fStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                }else{
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        listener();

    }

    private void listener(){
        binding.haveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerValidation();
            }
        });
    }

    private void registerValidation(){
        String email = binding.inputEmail.getText().toString();
        String password = binding.inputPass.getText().toString();
        String name = binding.inputName.getText().toString();
        String nomorTelepon = binding.inputNomor.getText().toString();
        String alamatToko = binding.inputAlamatToko.getText().toString();
        String namaToko = binding.inputNamaToko.getText().toString();



        if(!isEmpty(email) && !isEmpty(name) && !isEmpty(namaToko)
                && !isEmpty(alamatToko) && !isEmpty(nomorTelepon)) {
            if(nomorTelepon.length() <12 || nomorTelepon.length() >13){
                Toast.makeText(getApplicationContext(),"Nomor Telepon Length 12 or 13", Toast.LENGTH_SHORT)
                        .show();
            }else{
                signUp(email, password, name, nomorTelepon, alamatToko, namaToko);
            }
        }

        else{
            Toast.makeText(getApplicationContext(),
                    "Fill all the correctly", Toast.LENGTH_SHORT)
                    .show();
        }
    }


    private void signUp(final String email, String password, String name, String nomorTelepon,
                        String alamatToko, String namaToko){
        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete" +
                                task.isSuccessful());
                        if (task.isSuccessful()){

                            FirebaseUser firebaseUser = fAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String keyUser = firebaseUser.getUid();

                            User user = new User(keyUser, email, name, nomorTelepon,
                                    alamatToko,namaToko);

                            databaseReference.child(keyUser).setValue(user).addOnCompleteListener(
                                    new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Register Success",
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    }
                                }
                            });

                        } else{
                            task.getException().printStackTrace();;
                            Snackbar.make(findViewById(R.id.btnSignup), "Register Failed",
                                    Snackbar.LENGTH_LONG).show();

                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fAuth.addAuthStateListener(fStateListener);
    }

    @Override
    protected  void onStop(){
        super.onStop();
        if(fStateListener != null){
            fAuth.removeAuthStateListener(fStateListener);
        }
    }

}