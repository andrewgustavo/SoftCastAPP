package com.softcastapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import android.widget.Toast;

import com.softcastapp.adapters.PlaylistAdapter;

import com.softcastapp.R;
import com.softcastapp.models.Playlist;
import com.softcastapp.models.UsuarioLogin;
import com.softcastapp.services.ApiService;
import com.softcastapp.services.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardActivity extends AppCompatActivity {

    private Button btnLogout, btnCreatePlayList;
    private static final int REQUEST_CREATE_PLAYLIST = 1;
    private static final int REQUEST_EDIT_PLAYLIST = 2;
    private void loadPlaylists() {
        Intent intent = getIntent();
        UsuarioLogin usuario = (UsuarioLogin) intent.getSerializableExtra("USUARIO");
        if (usuario != null) {
            fetchPlaylists(usuario.getId());
        } else {
            Toast.makeText(DashboardActivity.this, "Usuário não encontrado.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CREATE_PLAYLIST) {
                Log.d("DASHBOARD", "Nova playlist criada. Atualizando listas...");
                loadPlaylists();
            } else if (requestCode == REQUEST_EDIT_PLAYLIST) {
                Log.d("DASHBOARD", "Playlist editada. Atualizando listas...");
                loadPlaylists();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();
        UsuarioLogin usuario = (UsuarioLogin) intent.getSerializableExtra("USUARIO");

        int usuarioId = usuario != null ? usuario.getId() : 0;

        btnLogout = findViewById(R.id.btn_logout);
        btnCreatePlayList = findViewById(R.id.btn_create_playlist);

        btnLogout.setOnClickListener(v -> {
            Intent logoutIntent = new Intent(DashboardActivity.this, MainActivity.class);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logoutIntent);
            finish();
        });

        btnCreatePlayList.setOnClickListener(v -> {
            Intent createPlaylistIntent = new Intent(DashboardActivity.this, NewPlaylistActivity.class);
            createPlaylistIntent.putExtra("USUARIO_ID", usuarioId);
            startActivityForResult(createPlaylistIntent, REQUEST_CREATE_PLAYLIST); // Use o código de requisição definido
        });

        if (usuario != null) {
            fetchPlaylists(usuario.getId());  // Passa o ID do usuário para buscar playlists
        } else {
            Toast.makeText(DashboardActivity.this, "Usuário não encontrado.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchPlaylists(int usuarioId) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        apiService.getPlaylistsByUserId(usuarioId).enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Playlist> playlists = response.body();
                    displayPlaylists(playlists);

                    // Log individual para cada playlist
                    for (Playlist playlist : playlists) {
                        Log.d("DEBUG", "Playlist ID: " + playlist.getPlaylistID() + ", Nome: " + playlist.getNome());
                    }
                } else {
                    Toast.makeText(DashboardActivity.this, "Erro ao carregar playlists.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                // Trate o erro de falha na conexão
                Toast.makeText(DashboardActivity.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ERROR", "Falha ao carregar playlists", t);
            }
        });
    }

    private void displayPlaylists(List<Playlist> playlists) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_playlists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PlaylistAdapter adapter = new PlaylistAdapter(this, playlists);
        recyclerView.setAdapter(adapter);
    }
}