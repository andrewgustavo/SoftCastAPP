package com.softcastapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.softcastapp.adapters.PlaylistAdapter;


public class DashboardActivity extends AppCompatActivity {

    private Button btnLogout, btnCreatePlayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnLogout = findViewById(R.id.btn_logout);
        btnCreatePlayList = findViewById(R.id.btn_create_playlist);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Volta para a tela principal (login)
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Encerra a atividade atual para impedir que o usuário volte com "voltar"
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view_playlists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> fakePlaylists = new ArrayList<>();
        fakePlaylists.add("Minha Playlist 1");
        fakePlaylists.add("Minha Playlist 2");
        fakePlaylists.add("Minha Playlist 3");

        PlaylistAdapter adapter = new PlaylistAdapter(fakePlaylists, this);
        recyclerView.setAdapter(adapter);

        btnCreatePlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, NewPlaylistActivity.class);
                startActivity(intent);
            }
        });
    }
}
