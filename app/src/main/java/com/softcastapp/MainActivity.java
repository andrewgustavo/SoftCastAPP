package com.softcastapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        //btnLogin.setOnClickListener(new View.OnClickListener() {
           // @Override
            //public void onClick(View v) {
                // Lógica de login
                // Pode ser simulada por enquanto, navegando para a Dashboard
                //Intent intent = new Intent(MainActivity.this, DashboardPage.class);
               // startActivity(intent);
            //}
        //});

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar para a tela de cadastro
                Intent intent = new Intent(MainActivity.this, MainRegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
