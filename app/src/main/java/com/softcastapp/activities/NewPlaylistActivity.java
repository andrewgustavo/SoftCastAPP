package com.softcastapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.softcastapp.R;
import com.softcastapp.models.Playlist;
import com.softcastapp.models.UsuarioLogin;
import com.softcastapp.services.ApiService;
import com.softcastapp.services.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPlaylistActivity extends AppCompatActivity {

    private EditText editPlaylistName;
    private Button btnSavePlaylist;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_playlist);

        apiService = RetrofitClient.getClient().create(ApiService.class);


        editPlaylistName = findViewById(R.id.edit_playlist_name);
        btnSavePlaylist = findViewById(R.id.btn_save_playlist);

        // Recupera o ID do usuário da Intent
        int usuarioID = getIntent().getIntExtra("USUARIO_ID", 0);

        btnSavePlaylist.setOnClickListener(v -> {
            String playlistName = editPlaylistName.getText().toString().trim();

            if (playlistName.isEmpty()) {
                Toast.makeText(NewPlaylistActivity.this, "Por favor, insira um nome para a playlist.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (usuarioID != 0) {
                createPlaylist(playlistName, usuarioID);
            } else {
                Toast.makeText(NewPlaylistActivity.this, "Usuário não encontrado.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createPlaylist(String playlistName, int usuarioID) {
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Cria um novo objeto Playlist com os dados necessários
        Playlist newPlaylist = new Playlist(playlistName, usuarioID); // Supondo que Playlist tenha um construtor assim

        // Envia a requisição com o objeto no corpo
        apiService.createPlaylist(newPlaylist).enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(NewPlaylistActivity.this, "Playlist criada com sucesso!", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish(); // Fecha a tela após salvar
                } else {
                    Toast.makeText(NewPlaylistActivity.this, "Erro ao criar playlist.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                Toast.makeText(NewPlaylistActivity.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}