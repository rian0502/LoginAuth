package com.belajar.loginauth.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.belajar.loginauth.API.APIclient;
import com.belajar.loginauth.Models.LoginRes;
import com.belajar.loginauth.R;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Button daftar = findViewById(R.id.btdaftar);
        username = findViewById(R.id.tfRegisUsername);
        password = findViewById(R.id.tfRegisPass);
        daftar.setOnClickListener(view -> {
            registrasi();
            daftar.setEnabled(false);
            setContentView(R.layout.activity_progress);
        });
    }

    private void registrasi(){
        Call<LoginRes> loginResCall = APIclient.getService().createUser(username.getText().toString(), password.getText().toString());
        loginResCall.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(@NonNull Call<LoginRes> call, @NonNull Response<LoginRes> response) {
                if(response.isSuccessful()){
                    LoginRes lr = response.body();
                    assert lr != null;
                    if(lr.getMessage().equals("success")){
                        Toast.makeText(RegisterActivity.this, "Akun Berhasil di Buat",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<LoginRes> call, @NonNull Throwable t) {
                Toast.makeText(RegisterActivity.this, "Registrasi Gagal", Toast.LENGTH_LONG).show();
            }
        });
    }
}