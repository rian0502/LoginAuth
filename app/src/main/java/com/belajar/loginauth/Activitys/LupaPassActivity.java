package com.belajar.loginauth.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.belajar.loginauth.R;

import java.util.Objects;

public class LupaPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_pass);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}