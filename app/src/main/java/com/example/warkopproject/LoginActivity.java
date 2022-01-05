package com.example.warkopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warkopproject.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;
    private static final String TAG = LoginActivity.class.getSimpleName();

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fAuth = FirebaseAuth.getInstance();
//        if(!TextUtils.isEmpty(binding.inputEmail.getText())||!TextUtils.isEmpty(binding.inputPass.getText())){
//            binding.btnLogin.setEnabled(true);
//        }
//        else{
//            binding.btnLogin.setEnabled(false);
//        }

        listener();
    }

    private void listener(){
        binding.dontHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.inputEmail.getText().toString();
                String password = binding.inputPass.getText().toString();
                if(!email.isEmpty() || !password.isEmpty()) {
                    login(email, password);
                }else{
                    Toast.makeText(LoginActivity.this, "Login Failed\n",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void login(String email, String password){

        fAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete" +
                                task.isSuccessful());
                        if (!task.isSuccessful()){
                            Log.w(TAG, "signInWithEmail:failed",
                                    task.getException());
                                    Toast.makeText(LoginActivity.this, "Login Failed\n",
                                            Toast.LENGTH_SHORT).show();
                        }
                        else if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Success\n" +
                                    "Email : " + email, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });

//        if (email.equals("asd")&& password.equals("123")){
//            startActivity(new Intent(LoginActivity.this,MainActivity.class));
//            Toast.makeText(getApplicationContext(),"Login Successfully", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            Toast.makeText(getApplicationContext(),"Wrong Email or Pass", Toast.LENGTH_SHORT).show();
//        }
    }

}