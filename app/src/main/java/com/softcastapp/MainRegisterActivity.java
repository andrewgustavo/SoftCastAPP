package com.softcastapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class MainRegisterActivity extends AppCompatActivity {

    private EditText nome, email, password, passwordConfirm;
    private Button btnRegister;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nome = findViewById(R.id.nome_register);
        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
        passwordConfirm = findViewById(R.id.passwordConfirm_register);
        btnRegister = findViewById(R.id.btn_register);
        btnBack = findViewById(R.id.btn_back);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nome.getText().toString().trim().isEmpty()) {
                    nome.setError("Nome é obrigatório");
                    //return; // Impede a execução do código abaixo
                }
                if (email.getText().toString().trim().isEmpty()) {
                    email.setError("E-mail é obrigatório");
                    //return;
                }
                if (password.length() < 6) {
                    Toast.makeText(MainRegisterActivity.this, "A senha deve ter pelo menos 6 caracteres.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().trim().isEmpty()) {
                    password.setError("Senha é obrigatória");
                    //return;
                }
                if (passwordConfirm.getText().toString().trim().isEmpty()) {
                    passwordConfirm.setError("Senha é obrigatória");
                    return;
                }

                String passwordKey1 = password.getText().toString().trim();
                String passwordKey2 = passwordConfirm.getText().toString().trim();

                // Valida se as senhas são iguais
                if (passwordKey1.equals(passwordKey2)) {
                    Toast.makeText(MainRegisterActivity.this, "Cadastro concluído com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainRegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Senhas não coincidem, exibe um erro
                    Toast.makeText(MainRegisterActivity.this, "As senhas não coincidem. Tente novamente.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
