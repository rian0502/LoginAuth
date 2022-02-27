package com.belajar.loginauth;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.belajar.loginauth.API.APIclient;
import com.belajar.loginauth.Models.LoginRes;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText tfUsername;
    EditText tfPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.appBar)));
        tfUsername = findViewById(R.id.etUsername);
        tfPassword = findViewById(R.id.etPassword);
        Button login = findViewById(R.id.btLogin);
        login.setOnClickListener(view -> {
            if (tfUsername.getText().toString().equals("") || tfPassword.getText().toString().equals("")){
                Snackbar.make(view,"Isi Form dengan Lengkap",Snackbar.LENGTH_LONG).show();
            }else{
                login();
            }
        });
    }

    public void login(){
        Call<LoginRes> loginResCall = APIclient.getService().userLogin(tfUsername.getText().toString(), tfPassword.getText().toString());
        loginResCall.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(@NonNull Call<LoginRes> call, @NonNull Response<LoginRes> response) {
               try{
                   if(response.isSuccessful()){
                       LoginRes res = response.body();
                       if(res.getMessage().equals("berhasil")){
                           Toast.makeText(LoginActivity.this, "Berhasil Login", Toast.LENGTH_LONG).show();
                       }else{
                           Toast.makeText(LoginActivity.this, "Username / Password Salah", Toast.LENGTH_LONG).show();
                       }
                   }else{
                       Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_LONG).show();
                   }
               }catch (Exception e){
                   System.out.println(e.getMessage());
               }
            }

            @Override
            public void onFailure(@NonNull Call<LoginRes> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this,"Eror : "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }
}