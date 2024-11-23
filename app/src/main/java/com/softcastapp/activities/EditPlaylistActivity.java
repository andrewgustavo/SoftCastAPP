package com.softcastapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.softcastapp.R;
import com.softcastapp.models.Playlist;
import com.softcastapp.services.ApiService;
import com.softcastapp.services.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPlaylistActivity extends AppCompatActivity {

    private EditText editPlaylistName;
    private Button btnUpdatePlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_playlist);

        editPlaylistName = findViewById(R.id.edit_playlist_name);
        btnUpdatePlaylist = findViewById(R.id.btn_update_playlist);

        String playlistName = getIntent().getStringExtra("playlist_title");
        final int playlistId = getIntent().getIntExtra("PLAYLIST_ID", -1);

        if (playlistName != null) {
            editPlaylistName.setText(playlistName);
        }

        btnUpdatePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedName = editPlaylistName.getText().toString().trim();
                if (!updatedName.isEmpty()) {
                    Playlist updatedPlaylist = new Playlist();
                    updatedPlaylist.setPlaylistID(playlistId);
                    updatedPlaylist.setNome(updatedName);
                    updatePlaylistInApi(playlistId, updatedPlaylist);
                } else {
                    Toast.makeText(EditPlaylistActivity.this, "Por favor, insira um novo nome para a playlist.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatePlaylistInApi(int playlistId, Playlist updatedPlaylist) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        apiService.editPlaylist(playlistId, updatedPlaylist).enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditPlaylistActivity.this, "Playlist atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("UPDATED_PLAYLIST_ID", playlistId); // Retorna o ID da playlist editada
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Log.e("EDIT_PLAYLIST", "Erro ao atualizar: " + response.code());
                    Toast.makeText(EditPlaylistActivity.this, "Erro ao atualizar a playlist.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                Log.e("EDIT_PLAYLIST_ERROR", "Falha na conexão: " + t.getMessage(), t);
                Toast.makeText(EditPlaylistActivity.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

