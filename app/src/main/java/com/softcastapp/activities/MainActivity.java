package com.softcastapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import androidx.appcompat.app.AppCompatActivity;

import com.softcastapp.R;
import com.softcastapp.models.UsuarioLogin;
import com.softcastapp.services.ApiService;
import com.softcastapp.services.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnRegister;
    private EditText email, password;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Retrofit
        apiService = RetrofitClient.getClient().create(ApiService.class);

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = email.getText().toString().trim();
                String passwordInput = password.getText().toString().trim();

                if (emailInput.isEmpty()) {
                    email.setError("E-mail é obrigatório");
                    return;
                }
                if (passwordInput.isEmpty()) {
                    password.setError("Senha é obrigatória");
                    return;
                }

                UsuarioLogin usuarioLogin = new UsuarioLogin();
                usuarioLogin.setEmail(emailInput);
                usuarioLogin.setSenha(passwordInput);

                // Fazer a chamada de login
                Call<Void> call = apiService.login(usuarioLogin);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();

                            Log Log = null;

                            String encodedEmail = emailInput;
                            try {
                                // Codificar apenas se necessário
                                if (!emailInput.contains("%40")) {
                                    encodedEmail = URLEncoder.encode(emailInput, "UTF-8");
                                }
                            } catch (UnsupportedEncodingException e) {
                            }


                            apiService.getUsuarioByEmail(encodedEmail).enqueue(new Callback<UsuarioLogin>() {
                                @Override
                                public void onResponse(Call<UsuarioLogin> call, Response<UsuarioLogin> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        UsuarioLogin usuarioLogin = response.body();
                                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                        intent.putExtra("USUARIO", usuarioLogin);
                                        startActivity(intent);
                                        Log.d("DEBUG", "Usuário logado: " + usuarioLogin.getId());
                                    } else {
                                        Log.e("DEBUG", "Erro na resposta da API: " + response.code());
                                    }
                                }

                                @Override
                                public void onFailure(Call<UsuarioLogin> call, Throwable t) {
                                }
                            });

                        } else {
                            Toast.makeText(MainActivity.this, "Credenciais inválidas. Tente novamente.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainRegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
