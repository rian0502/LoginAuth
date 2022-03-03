package com.belajar.loginauth.Activitys;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText tfUsername, tfPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        tfUsername = findViewById(R.id.etUsername);
        tfPassword = findViewById(R.id.etPassword);
        TextView tvPass = findViewById(R.id.tvLupaPass);
        Button login = findViewById(R.id.btLogin);
        Button daftar = findViewById(R.id.btRegister);
        login.setOnClickListener(this);
        daftar.setOnClickListener(this);
        tvPass.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btLogin){
            if (tfUsername.getText().toString().equals("") || tfPassword.getText().toString().equals("")){
                Snackbar.make(view,"Isi Form dengan Lengkap",Snackbar.LENGTH_LONG).show();
            }else{
                if (isNetworkOnline()){
                    login();
                }else{
                    Toast.makeText(LoginActivity.this, "Anda Sedang Offline", Toast.LENGTH_LONG).show();
                }
            }
        }else if(view.getId() == R.id.btRegister){
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

        }else if(view.getId() == R.id.tvLupaPass){
            startActivity(new Intent(LoginActivity.this, LupaPassActivity.class));
        }
    }
    private boolean isNetworkOnline(){
        ConnectivityManager connectivityManager = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
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
                           finish();
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