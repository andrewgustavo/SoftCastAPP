package com.softcastapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.softcastapp.adapters.ContentAdapter;
import com.softcastapp.models.Conteudo;
import java.util.List;

public class ContentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContentAdapter adapter;
    private List<Conteudo> listaDeConteudos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        // Obter o título da playlist da Intent
        Intent intent = getIntent();
        String playlistTitle = intent.getStringExtra("playlist_title");

        // Exibir o título da playlist na tela
        TextView playlistTitleTextView = findViewById(R.id.playlistTitle);
        playlistTitleTextView.setText(playlistTitle);

        // Inicializar o RecyclerView
        recyclerView = findViewById(R.id.recyclerViewPlaylistContents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Criar a lista de conteúdos fictícia (exemplo)
        listaDeConteudos = createSampleContents();

        // Configurar o adapter
        adapter = new ContentAdapter(this, listaDeConteudos);
        recyclerView.setAdapter(adapter);
    }
    private List<Conteudo> createSampleContents() {
        List<Conteudo> contents = new ArrayList<>();
        contents.add(new Conteudo("Vídeo 1", "Descrição do Vídeo 1"));
        contents.add(new Conteudo("Vídeo 2", "Descrição do Vídeo 2"));
        contents.add(new Conteudo("Vídeo 3", "Descrição do Vídeo 3"));
        return contents;
    }
}