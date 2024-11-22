package com.softcastapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.softcastapp.R;
import com.softcastapp.models.UsuarioRegister;
import com.softcastapp.services.RetrofitClient;
import com.softcastapp.services.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRegisterActivity extends AppCompatActivity {

    private EditText nome, email, password, passwordConfirm;
    private Button btnRegister;
    private Button btnBack;

    public boolean isEmailValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        return email.matches(emailPattern);
    }

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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainRegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nome.getText().toString().trim().isEmpty()) {
                    nome.setError("Nome é obrigatório");
                    return;
                }
                if (email.getText().toString().trim().isEmpty()) {
                    email.setError("E-mail é obrigatório");
                    return;
                }
                if (!isEmailValid(email.getText().toString())) {
                    Toast.makeText(MainRegisterActivity.this, "Por favor, insira um e-mail válido", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(MainRegisterActivity.this, "A senha deve ter pelo menos 6 caracteres.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().trim().isEmpty()) {
                    password.setError("Senha é obrigatória");
                    return;
                }
                if (passwordConfirm.getText().toString().trim().isEmpty()) {
                    passwordConfirm.setError("Senha é obrigatória");
                    return;
                }

                String passwordKey1 = password.getText().toString().trim();
                String passwordKey2 = passwordConfirm.getText().toString().trim();

                if (passwordKey1.equals(passwordKey2)) {
                    UsuarioRegister usuario = new UsuarioRegister(
                            nome.getText().toString(),
                            email.getText().toString(),
                            passwordKey1,
                            "2000-01-01"  // Use a data de nascimento adequada
                    );

                    // Enviar dados para a API com Retrofit
                    ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
                    Call<Void> call = apiService.registerUsuario(usuario);

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(MainRegisterActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainRegisterActivity.this, DashboardActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainRegisterActivity.this, "Erro ao registrar. Tente novamente.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(MainRegisterActivity.this, "Erro na conexão com o servidor.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(MainRegisterActivity.this, "As senhas não coincidem. Tente novamente.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
