package com.belajar.loginauth;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.belajar.loginauth.API.APIclient;
import com.belajar.loginauth.Models.LoginReq;
import com.belajar.loginauth.Models.LoginRes;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        LoginReq loginReq = new LoginReq();
        loginReq.setUsername(tfUsername.getText().toString());
        loginReq.setPassword(tfPassword.getText().toString());
        Call<LoginRes> loginResCall = APIclient.getService().userLogin(loginReq);

        loginResCall.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                if(response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginRes> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Eror : "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }
}