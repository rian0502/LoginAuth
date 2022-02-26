package com.belajar.loginauth;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

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
        tfPassword = findViewById(R.id.inputPassword);
        Button login = findViewById(R.id.btLogin);
        login.setOnClickListener(view -> {
            if (tfUsername.getText().toString().equals("") || tfPassword.getText().toString().equals("")){
                Snackbar.make(view,"Isi Form dengan Lengkap",Snackbar.LENGTH_LONG).show();
            }else{
                if (tfUsername.getText().toString().equals("admin") || tfPassword.getText().toString().equals("admin")){
                    startActivity(
                            new Intent(LoginActivity.this, HomeActivity.class).
                                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                }else{
                    Snackbar.make(view,"Username dan Password tidak cocok",Snackbar.LENGTH_LONG).show();
                    tfPassword.setText("");
                    tfUsername.setText("");
                }
            }
        });
    }
}