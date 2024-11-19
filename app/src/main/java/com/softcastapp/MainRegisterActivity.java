package com.softcastapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class MainRegisterActivity extends AppCompatActivity {

    private EditText Nome, Email, Password;
    private Button btnRegister;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Nome = findViewById(R.id.nome_register);
        Email = findViewById(R.id.email_register);
        Password = findViewById(R.id.password_register);
        btnRegister = findViewById(R.id.btn_register);
        btnBack = findViewById(R.id.btn_back);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Nome.getText().toString().trim().isEmpty()) {
                    Nome.setError("Nome é obrigatório");
                    return; // Impede a execução do código abaixo
                }
                if (Email.getText().toString().trim().isEmpty()) {
                    Email.setError("E-mail é obrigatório");
                    return;
                }
                if (Password.getText().toString().trim().isEmpty()) {
                    Password.setError("Senha é obrigatória");
                    return;
                }

                // Se tudo estiver preenchido, prosseguir com o cadastro (aqui você pode simular)
                Toast.makeText(MainRegisterActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainRegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainRegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
