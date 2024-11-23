package com.softcastapp.activities;

import com.softcastapp.R;
import com.softcastapp.adapters.PlaylistContentAdapter;
import com.softcastapp.models.Conteudo;
import com.softcastapp.models.UsuarioLogin;

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

        // Recuperando o ID da Playlist da Intent
        Intent playlistTntent = getIntent();
        int playlistId = playlistTntent.getIntExtra("PLAYLIST_ID", -1);  // Usando a chave correta

        // Pegando o objeto UsuarioLogin da Intent
        Intent intent = getIntent();
        UsuarioLogin usuarioLogin = (UsuarioLogin) intent.getSerializableExtra("USUARIO");

        btnBack = findViewById(R.id.btn_return);
        btnAddContent = findViewById(R.id.btn_add_content);

        btnBack.setOnClickListener(v -> {
            Intent dashboardIntent = new Intent(PlaylistActivity.this, DashboardActivity.class);
            // Garante que DashboardActivity será exibida, removendo outras atividades da pilha
            dashboardIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(dashboardIntent);
            finish();
        });

        btnAddContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criar a intent para ir para a ContentActivity
                Intent intent = new Intent(PlaylistActivity.this, ContentActivity.class);

                // Passando o título da playlist
                intent.putExtra("playlist_title", getIntent().getStringExtra("playlist_title"));

                // Passando o ID da Playlist
                intent.putExtra("playlistId", playlistId);  // Usando o valor que já pegamos

                startActivity(intent);
            }
        });

        Intent backIntent = getIntent();
        String playlistTitle = intent.getStringExtra("playlist_title");

        TextView playlistTitleTextView = findViewById(R.id.playlistTitle);
        playlistTitleTextView.setText(playlistTitle);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewPlaylistContents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Conteudo> contentsPlaylist = new ArrayList<>();
        contentsPlaylist.add(new Conteudo(1, "Descrição 1","teste","teste","teste","teste"));
        contentsPlaylist.add(new Conteudo(2, "Descrição 2","teste","teste","teste","teste"));
        contentsPlaylist.add(new Conteudo(3, "Descrição 3","teste","teste","teste","teste"));

        PlaylistContentAdapter adapter = new PlaylistContentAdapter(this,contentsPlaylist);
        recyclerView.setAdapter(adapter);
    }
}