package com.belajar.loginauth.Activitys;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.belajar.loginauth.API.APIclient;
import com.belajar.loginauth.Models.LoginRes;
import com.belajar.loginauth.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText tfUsername;
    EditText tfPassword;
    TextView tvPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        tfUsername = findViewById(R.id.etUsername);
        tfPassword = findViewById(R.id.etPassword);
        tvPass = findViewById(R.id.tvLupaPass);
        Button login = findViewById(R.id.btLogin);
        Button daftar = findViewById(R.id.btRegister);
        login.setOnClickListener(view -> {
            if (tfUsername.getText().toString().equals("") || tfPassword.getText().toString().equals("")){
                Snackbar.make(view,"Isi Form dengan Lengkap",Snackbar.LENGTH_LONG).show();
            }else{
                login();
            }
        });
        daftar.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
        tvPass.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, LupaPassActivity.class)));
    }

    private void login(){
        Call<LoginRes> loginResCall = APIclient.getService().userLogin(tfUsername.getText().toString(), tfPassword.getText().toString());
        loginResCall.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(@NonNull Call<LoginRes> call, @NonNull Response<LoginRes> response) {
               try{
                   if(response.isSuccessful()){
                       LoginRes res = response.body();
                       assert res != null;
                       if(res.getMessage().equals("berhasil")){
                           Toast.makeText(LoginActivity.this, "Berhasil Login", Toast.LENGTH_LONG).show();
                           startActivity(new Intent(LoginActivity.this, HomeActivity.class)
                                   .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                                   .putExtra("akun-login", tfUsername.getText().toString())
                           );
                       }else{
                           tfUsername.getText().clear();
                           tfPassword.getText().clear();
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