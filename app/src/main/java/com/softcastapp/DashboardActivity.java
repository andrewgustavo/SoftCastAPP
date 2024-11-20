package com.softcastapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.softcastapp.adapters.PlaylistAdapter;
import com.softcastapp.models.Playlist;


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
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view_playlists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Playlist> listPlaylists = new ArrayList<>();
        listPlaylists.add(new Playlist("Minha Playlist 1","Exemplo 1"));
        listPlaylists.add(new Playlist("Minha Playlist 2","Exemplo 2"));
        listPlaylists.add(new Playlist("Minha Playlist 3","Exemplo 3"));

        PlaylistAdapter adapter = new PlaylistAdapter(this,listPlaylists);
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
