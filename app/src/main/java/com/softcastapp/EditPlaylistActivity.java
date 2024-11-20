package com.softcastapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditPlaylistActivity extends AppCompatActivity {

    private EditText editPlaylistName;
    private Button btnUpdatePlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_playlist);

        editPlaylistName = findViewById(R.id.edit_playlist_name);
        btnUpdatePlaylist = findViewById(R.id.btn_update_playlist);

        // Receber informações da playlist que será editada (simulada por enquanto)
        String playlistName = getIntent().getStringExtra("playlist_name");
        if (playlistName != null) {
            editPlaylistName.setText(playlistName);
        }

        btnUpdatePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedName = editPlaylistName.getText().toString().trim();
                if (!updatedName.isEmpty()) {
                    // Simular atualização de playlist
                    Toast.makeText(EditPlaylistActivity.this, "Playlist atualizada para: " + updatedName, Toast.LENGTH_SHORT).show();
                    finish(); // Voltar para a Dashboard após atualizar
                } else {
                    Toast.makeText(EditPlaylistActivity.this, "Por favor, insira um novo nome para a playlist.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
