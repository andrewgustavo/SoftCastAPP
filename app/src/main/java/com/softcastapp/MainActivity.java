package com.softcastapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView registerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Carrega o layout

        // Inicializando os componentes (Campos de E-mail, Senha, Botão e TextView)
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.btn_login);
        registerText = findViewById(R.id.register_text);

        // Ação do botão de Login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pegando os valores dos campos de texto
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validar se os campos estão preenchidos
                if (email.isEmpty() || password.isEmpty()) {
                    // Se algum campo estiver vazio, mostra um erro
                    Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Aqui você pode adicionar a lógica real de login (verificação de e-mail e senha)
                    // Como exemplo, apenas mostra um "Login bem-sucedido"
                    Toast.makeText(MainActivity.this, "Login bem-sucedido", Toast.LENGTH_SHORT).show();

                    // Navegar para a próxima Activity (exemplo de navegação, pode ser para a tela principal do app)
                    // Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    // startActivity(intent);
                }
            }
        });

        // Ação do link "Cadastrar-se"
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Exemplo de navegação para a tela de cadastro (vamos apenas mostrar um Toast por enquanto)
                Toast.makeText(MainActivity.this, "Tela de cadastro em breve", Toast.LENGTH_SHORT).show();

                // Você pode abrir uma nova Activity de cadastro quando estiver pronta:
                // Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                // startActivity(intent);
            }
        });
    }
}
