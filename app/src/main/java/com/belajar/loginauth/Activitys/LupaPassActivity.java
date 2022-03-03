package com.belajar.loginauth.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
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

public class LupaPassActivity extends AppCompatActivity {
    private EditText username, password, confpassword;
    private Button btkonfirm;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_pass);
        Objects.requireNonNull(getSupportActionBar()).hide();
        username = findViewById(R.id.tflupaUsername);
        password = findViewById(R.id.tflupasPass);
        confpassword = findViewById(R.id.tflupasPassConf);
        btkonfirm = findViewById(R.id.btLupaPass);
        password.setVisibility(View.GONE);
        confpassword.setVisibility(View.GONE);
        btkonfirm.setVisibility(View.GONE);
        username.setOnTouchListener((view, motionEvent) -> {
            final int RIGTH = 2;
            if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                if (motionEvent.getRawX() >= (username.getRight() - username.getCompoundDrawables()[RIGTH].getBounds().width())){
                    if(username.getText().toString().equals("")){
                        username.setError("Username Harus di Isi Terlebih dahulu");
                    }else{
                        findUser();
                    }
                    return true;
                }
            }
            return false;
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
                        Toast.makeText(LupaPassActivity.this, "Username Tidak di Temukan", Toast.LENGTH_LONG).show();
                        username.setError("Username tidak Terdaftar");
                    }else{
                        username.setVisibility(View.GONE);
                        password.setVisibility(View.VISIBLE);
                        confpassword.setVisibility(View.VISIBLE);
                        btkonfirm.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<LoginRes> call, @NonNull Throwable t) {
                Toast.makeText(LupaPassActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}