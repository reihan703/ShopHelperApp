package com.example.warkopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.warkopproject.databinding.AppBarMainBinding;

public class AppBarMain extends AppCompatActivity {

    AppBarMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AppBarMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }


}