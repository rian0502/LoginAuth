package com.belajar.loginauth.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.belajar.loginauth.R;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle bundle= getIntent().getExtras();
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.appBar)));
        Button logOut = findViewById(R.id.btLogOut);
        TextView textView = findViewById(R.id.tvNamaUser);
        textView.setText(bundle.getString("akun-login"));
        logOut.setOnClickListener(view -> {
            Toast.makeText(HomeActivity.this, "Berhasil LogOut",Toast.LENGTH_LONG).show();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class).addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK
            ));
        });
    }
}