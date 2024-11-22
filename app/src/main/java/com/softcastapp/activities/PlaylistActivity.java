package com.softcastapp.activities;

import com.softcastapp.R;
import com.softcastapp.adapters.PlaylistContentAdapter;
import com.softcastapp.models.Conteudo;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    private Button btnBack, btnAddContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        btnBack = findViewById(R.id.btn_return);
        btnAddContent = findViewById(R.id.btn_add_content);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaylistActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        btnAddContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaylistActivity.this, ContentActivity.class);
                intent.putExtra("playlist_title", getIntent().getStringExtra("playlist_title"));
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String playlistTitle = intent.getStringExtra("playlist_title");

        TextView playlistTitleTextView = findViewById(R.id.playlistTitle);
        playlistTitleTextView.setText(playlistTitle);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewPlaylistContents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Conteudo> contentsPlaylist = new ArrayList<>();
        contentsPlaylist.add(new Conteudo("Título 1", "Descrição 1"));
        contentsPlaylist.add(new Conteudo("Título 2", "Descrição 2"));
        contentsPlaylist.add(new Conteudo("Título 3", "Descrição 3"));

        PlaylistContentAdapter adapter = new PlaylistContentAdapter(this,contentsPlaylist);
        recyclerView.setAdapter(adapter);
    }
}