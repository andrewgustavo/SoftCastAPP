package com.softcastapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewPlaylistActivity extends AppCompatActivity {

    private EditText editPlaylistName;
    private Button btnSavePlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_playlist);

        editPlaylistName = findViewById(R.id.edit_playlist_name);
        btnSavePlaylist = findViewById(R.id.btn_save_playlist);

        btnSavePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playlistName = editPlaylistName.getText().toString().trim();
                if (!playlistName.isEmpty()) {
                    Toast.makeText(NewPlaylistActivity.this, "Playlist '" + playlistName + "' criada!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(NewPlaylistActivity.this, "Por favor, insira um nome para a playlist.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
