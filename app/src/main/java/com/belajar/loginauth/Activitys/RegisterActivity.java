package com.belajar.loginauth.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
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

    private EditText username, password;
    private Button daftar;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();
        daftar = findViewById(R.id.btdaftar);
        username = findViewById(R.id.tfRegisUsername);
        password = findViewById(R.id.tfRegisPass);
        daftar.setVisibility(View.GONE);
        daftar.setOnClickListener(view -> {
          if(username.getText().toString().equals("") || password.getText().toString().equals("")){
              Toast.makeText(RegisterActivity.this, "Isi Form dengan Lengkap", Toast.LENGTH_SHORT).show();
          }else{
              registrasi();
              daftar.setEnabled(false);
              setContentView(R.layout.activity_progress);
          }
        });

        username.setOnTouchListener((view, motionEvent) -> {
            final int RIGTH = 2 ;
            if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                if (motionEvent.getRawX() >= (username.getRight() - username.getCompoundDrawables()[RIGTH].getBounds().width())){
                    if(username.getText().toString().equals("")){
                        Toast.makeText(RegisterActivity.this, "Username Harus di isi !", Toast.LENGTH_LONG).show();
                    }else{
                        findUser();
                    }
                    return true;
                }
            }
            return false;
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
                        finish();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<LoginRes> call, @NonNull Throwable t) {
                Toast.makeText(RegisterActivity.this, "Registrasi Gagal", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void findUser(){
        Call<LoginRes> loginResCall = APIclient.getService().findUser(username.getText().toString());
        loginResCall.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(@NonNull Call<LoginRes> call, @NonNull Response<LoginRes> response) {
                if (response.isSuccessful()){
                    LoginRes lr = response.body();
                    assert  lr != null;
                    if(lr.getMessage().equals("no")){
                        Toast.makeText(RegisterActivity.this, "Username dapat digunakan", Toast.LENGTH_LONG).show();
                        daftar.setVisibility(View.VISIBLE);
                        username.setEnabled(false);
                    }else{
                        Toast.makeText(RegisterActivity.this, "Username Telah Ada !",Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<LoginRes> call, @NonNull Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}