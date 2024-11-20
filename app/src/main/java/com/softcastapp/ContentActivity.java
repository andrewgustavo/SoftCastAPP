package com.softcastapp;

import com.softcastapp.adapters.ContentAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.softcastapp.models.Conteudo;
import java.util.List;

public class ContentActivity extends AppCompatActivity {

    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        btnBack = findViewById(R.id.btn_return);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContentActivity.this, PlaylistActivity.class);
                intent.putExtra("playlist_title", getIntent().getStringExtra("playlist_title"));
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String playlistTitle = intent.getStringExtra("playlist_title");

        TextView playlistTitleTextView = findViewById(R.id.playlistTitle);
        playlistTitleTextView.setText(playlistTitle);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewContent);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Conteudo> contents = new ArrayList<>();
        contents.add(new Conteudo("Vídeo 1", "Descrição do Vídeo 1"));
        contents.add(new Conteudo("Vídeo 2", "Descrição do Vídeo 2"));
        contents.add(new Conteudo("Vídeo 3", "Descrição do Vídeo 3"));
        contents.add(new Conteudo("Vídeo 4", "Descrição do Vídeo 4"));
        contents.add(new Conteudo("Vídeo 5", "Descrição do Vídeo 5"));
        contents.add(new Conteudo("Vídeo 6", "Descrição do Vídeo 6"));
        contents.add(new Conteudo("Vídeo 7", "Descrição do Vídeo 7"));
        contents.add(new Conteudo("Vídeo 8", "Descrição do Vídeo 8"));

        ContentAdapter adapter = new ContentAdapter(this, contents);
        recyclerView.setAdapter(adapter);
    }
}